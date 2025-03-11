package com.jxw.server.controller;

import com.jxw.server.exception.SessionExpiredException;
import com.jxw.server.exception.TrainingStopException;
import com.jxw.server.log.Log;
import com.jxw.server.request.IdOnlyRequest;
import com.jxw.server.request.SettingRequest;
import com.jxw.server.request.SingleShootDetectionResponse;
import com.jxw.server.response.ApiResponse;
import com.jxw.server.security.AuthToken;
import com.jxw.server.session.UserSession;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;
import static com.jxw.server.util.File.generateUniqueFileName;
import static com.jxw.server.util.File.getFileExtension;
import static org.springframework.beans.MethodInvocationException.ERROR_CODE;

@RestController
@RequestMapping("/detection")
public class DetectionController {
    private static final Logger logger = LoggerFactory.getLogger(DetectionController.class);

    private final ConcurrentMap<String, UserSession> userSessions = new ConcurrentHashMap<>();

    private static final Set<String> VALID_THEMES = Set.of("middle shoot", "three point");
    private static final Set<String> VALID_METHODS = Set.of("single", "multiple");

    private final Executor asyncTaskExecutor;
    private final Executor ioExecutor;

    @Value("${tempStorageRoot}")
    private String tempStorageRoot;

    @Value("${cleanup.interval}")
    private long cleanupInterval;

    @Value("${cleanup.expire-time}")
    private long expireTime;

    // 构造函数注入（推荐）
    @Autowired
    public DetectionController(
            @Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor,
            @Qualifier("ioExecutor") Executor ioExecutor
    ) {
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.ioExecutor = ioExecutor;
    }

    @Log
    @AuthToken
    @PostMapping("/setting")
    public ResponseEntity<ApiResponse> setting(@RequestBody SettingRequest request) {
        validateRequest(request);

        String userId = request.getUserId();
        String trainingMethod = request.getTrainingMethod();
        String theme = request.getTheme();

        // 原子化创建会话
        userSessions.compute(userId, (k, v) ->
                (v == null || v.isExpired())
                        ? createNewSession(userId, theme, trainingMethod)
                        : v.refreshSession()
        );
        logger.debug("Session created for {} - {} {}", userId, theme, trainingMethod);
        return ResponseEntity.ok(ApiResponse.success());
    }

    private UserSession createNewSession(String userId, String theme, String method) {
        return new UserSession(userId, theme, method);
    }

    private void validateRequest(SettingRequest request) {
        if (!VALID_THEMES.contains(request.getTheme())) {
            logger.warn("Invalid theme: {}", request.getTheme());
            throw new InvalidParameterException("Invalid theme");
        }
        if (!VALID_METHODS.contains(request.getTrainingMethod())) {
            logger.warn("Invalid method: {}", request.getTrainingMethod());
            throw new InvalidParameterException("Invalid training method");
        }
    }

    @Log
    @AuthToken
    @PostMapping("/uploadImages")
    @Async
    public SingleShootDetectionResponse uploadImages(
            @RequestParam("userId") String userId,
            @RequestParam("images") MultipartFile[] images) {

        String INVALID_PARAM_CODE = "400";

        // 1. 参数校验前置
        if (images == null || images.length == 0) {
            logger.warn("Empty images from user: {}", userId);
            return new SingleShootDetectionResponse(INVALID_PARAM_CODE, "Empty images", 0);
        }

        // 2. 原子化会话获取（包含过期检查）
        UserSession userSession = userSessions.compute(userId, (k, v) -> {
            if (v == null || v.isExpired()) {
                logger.warn("Session expired for user: {}", userId);
                throw new SessionExpiredException("Session expired");
            }
            return v.refreshSession();
        });

        // 3. 异步处理流水线
        CompletableFuture.supplyAsync(() -> {
            String res="";
            try {
                // 4. 创建唯一工作目录
                Path tempDir = createUserWorkspace(userId);
                logger.debug("Processing {} images for user: {}", images.length, userId);

                // 5. 异步保存图片
                List<CompletableFuture<Path>> saveFutures = new ArrayList<>();
                for (MultipartFile file : images) {
                    if (file.isEmpty()) continue;

                    CompletableFuture<Path> saveFuture = saveImageAsync(userSession, file, tempDir)
                            .exceptionally(e -> {
                                logger.error("Image saving failed: {}", file.getOriginalFilename(), e);
                                return null;
                            });
                    saveFutures.add(saveFuture);
                }

                // 等待所有图片保存完成
                CompletableFuture<Void> allSaves = CompletableFuture.allOf(
                        saveFutures.toArray(new CompletableFuture[0])
                );
                allSaves.join(); // 阻塞，直到所有保存任务完成

                List<Path> savedPaths = saveFutures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                // 如果没有保存成功的图片，返回错误
                if (savedPaths.isEmpty()) {
                    return new SingleShootDetectionResponse(ERROR_CODE, "No images saved successfully", 0);
                }

                // 5. 处理图片
                for(Path savedPath : savedPaths) {
                    // res = userSession.analysisImages(savedPath.toString());
                    res = "goal";

                    if ("goal".equals(res)) {
                        return new SingleShootDetectionResponse("200", "goal", 1);
                    }
                    else if ("miss".equals(res)) {
                        return new SingleShootDetectionResponse("200", "miss", 1);
                    }
                    else if("error".equals(res)) {
                        return new SingleShootDetectionResponse(ERROR_CODE, "Image processing failed", 0);
                    }
                }
                return new SingleShootDetectionResponse("200", "unknown", 1);
            } catch (Exception e) {
                logger.error("Processing failed for user: {} | Error: {}", userId, e.getMessage(), e);
                return new SingleShootDetectionResponse(ERROR_CODE, e.getMessage(), 0);
            } finally {
                // cleanTempResources(userId); // 8. 资源清理
            }
        }, asyncTaskExecutor);
        return new SingleShootDetectionResponse("200", "unknown", 1);
    }

    private void cleanTempResources(String userId) {
        try {
            // 1. 构建用户临时目录路径
            Path userTempDir = Paths.get(tempStorageRoot, userId);

            // 2. 检查目录是否存在
            if (Files.exists(userTempDir)) {
                // 3. 递归删除目录及其内容
                Files.walkFileTree(userTempDir, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        try {
                            Files.delete(file);
                            logger.debug("Deleted file: {}", file);
                        } catch (IOException e) {
                            logger.warn("Failed to delete file: {} | Error: {}", file, e.getMessage());
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        try {
                            Files.delete(dir);
                            logger.debug("Deleted directory: {}", dir);
                        } catch (IOException e) {
                            logger.warn("Failed to delete directory: {} | Error: {}", dir, e.getMessage());
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                logger.debug("User directory does not exist: {}", userTempDir);
            }
        } catch (IOException e) {
            logger.error("Failed to clean resources for user: {} | Error: {}", userId, e.getMessage(), e);
        }
    }

    // 异步保存图片
    private CompletableFuture<Path> saveImageAsync(UserSession session, MultipartFile file, Path tempDir) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // 生成唯一文件名（线程安全）
                String fileName = generateUniqueName(file.getOriginalFilename());
                Path targetPath = tempDir.resolve(fileName);

                // 使用NIO非阻塞写入
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                logger.trace("Saved image: {}", targetPath);
                return targetPath;

            }catch (IOException e) {
                logger.error("Image processing error: {}", e.getMessage());
                throw new RuntimeException("Failed to save file", e);
            }
        }, ioExecutor);
    }

    private String generateUniqueName(String originalFilename) {
        String fileExtension = getFileExtension(originalFilename);
        return generateUniqueFileName(fileExtension);
    }

    // 创建工作目录（原子化操作）
    private Path createUserWorkspace(String userId) throws IOException {
        Path path = Paths.get(tempStorageRoot, userId);
        synchronized (userId.intern()) { // 用户级锁防止目录竞争
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                logger.debug("Created workspace: {}", path);
            }
        }
        return path;
    }

    // 资源清理（异步执行）
    @Scheduled(fixedDelayString = "${cleanup.interval}")
    public void scheduledCleanup() {
        File tempRoot = new File(tempStorageRoot);
        if (tempRoot.exists()) {
            Arrays.stream(Objects.requireNonNull(tempRoot.listFiles()))
                    .filter(f -> f.lastModified() < System.currentTimeMillis() - expireTime) // 清理1小时前文件
                    .parallel()
                    .forEach(f -> {
                        try {
                            FileUtils.deleteDirectory(f);
                            logger.debug("Cleaned directory: {}", f.getAbsolutePath());
                        } catch (IOException e) {
                            logger.warn("Cleanup failed: {}", f.getAbsolutePath());
                        }
                    });
        }
    }

    @PostMapping("/stopTraining")
    public ResponseEntity<ApiResponse> stopTraining(@RequestBody IdOnlyRequest request) {
        String userId = request.getUserId();
        try {
            // 原子化移除会话
            UserSession session = userSessions.remove(userId);
            if (session != null) {
                try {
                    session.stopTraining();
                    logger.info("Training stopped for user: {}", userId);
                    return ResponseEntity.ok(ApiResponse.success());
                } catch (Exception e) {
                    logger.error("Error stopping training for user: {}", userId, e);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
                }
            } else {
                logger.debug("No active session for user: {}", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("No active session for the user"));
            }
        } catch (Exception e) {
            logger.error("System error stopping training for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }
}