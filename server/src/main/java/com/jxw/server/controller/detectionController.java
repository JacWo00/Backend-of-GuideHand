package com.jxw.server.controller;

import com.jxw.server.entity.User;
import com.jxw.server.request.SingleShootDetectionResponse;
import com.jxw.server.session.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/detection")
public class detectionController {
    private static final Logger logger = LoggerFactory.getLogger(detectionController.class);
    private final ConcurrentHashMap<String, UserSession> userSessionConcurrentHashMap = new ConcurrentHashMap<>();

    @PostMapping("/setting")
    public String setting(@RequestParam("userId") String userId,@RequestParam("trainingMethod") String trainingMethod,@RequestParam("theme") String theme){
        if(trainingMethod.equals("single")){
            if(theme.equals("middle shoot")){
                userSessionConcurrentHashMap.put(userId,new UserSession(userId,"middle shoot","single"));
                return "success";
            }

            else if(theme.equals("three point")){
                userSessionConcurrentHashMap.put(userId,new UserSession(userId,"three point","single"));
                return "success";
            }

            else{
                throw new RuntimeException("theme error");
            }

        }

        else if(trainingMethod.equals("multiple")){
            if(theme.equals("middle shoot")){
                userSessionConcurrentHashMap.put(userId,new UserSession(userId,"middle shoot","multiple"));
                return "success";
            }

            else if(theme.equals("three point")){
                userSessionConcurrentHashMap.put(userId,new UserSession(userId,"three point","multiple"));
                return "success";
            }

            else{
                throw new RuntimeException("theme error");
            }

        }
        else{
            throw new RuntimeException("trainingMethod error");

        }

    }

    @PostMapping("/uploadImages")
    public SingleShootDetectionResponse uploadImages(@RequestParam("userId") String userId,@RequestParam("images") MultipartFile[] images){
        try{
            if (images == null || images.length == 0) {
                return new SingleShootDetectionResponse("400","error",0);
            }

            UserSession userSession = userSessionConcurrentHashMap.get(userId);

            // 保存图片到临时目录（可选）
            String tempDir = "src/main/resources/temp/"+userId;
            File tempDirPath = new File(tempDir);
            if (!tempDirPath.exists()) {
                tempDirPath.mkdirs();
                logger.info("Created temp directory: {}", tempDir);
            }

            for (int i = 0; i < images.length; i++) {
                MultipartFile file = images[i];
                if (!file.isEmpty()) {
                    try {
                        String fileName = tempDir + "image_" + i;
                        file.transferTo(new File(fileName));  // 保存图片到临时目录
                        logger.info("Saved image {} to {}", file.getOriginalFilename(), fileName);
                        String res = userSession.analysisImages(fileName);
                        if(res.equals("error")){
                            logger.error("Result of image {} is {}", file.getOriginalFilename(), res);
                            return new SingleShootDetectionResponse("400","error",0);
                        }
                        logger.info("Result of image {} is {}", file.getOriginalFilename(), res);
                        return new SingleShootDetectionResponse("200",res,1);
                        } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                logger.error("Image {} is empty", file.getOriginalFilename());
            }
            return new SingleShootDetectionResponse("200","unknown",1);
        }catch(Exception e){
            return new SingleShootDetectionResponse("400","error",0);
        }
    }

    @PostMapping("/stopTraining")
    public String stopTraining(@RequestParam("userId") String userId){
        try{
            UserSession userSession=userSessionConcurrentHashMap.get(userId);
            userSession.stopTraining();
            userSessionConcurrentHashMap.remove(userId);
            return "success";
        }catch(Exception e){
            return "error";
        }
    }
}
