package com.jxw.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class File {
    // 获取文件扩展名（安全处理）
    public static String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return ""; // 无扩展名或未知类型
        }
        String ext = filename.substring(filename.lastIndexOf("."));
        // 防止路径遍历攻击
        ext = ext.replaceAll("[^a-zA-Z0-9.]", "");
        return ext.toLowerCase();
    }

    // 生成唯一文件名（带时间戳和UUID）
    public static String generateUniqueFileName(String extension) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return "image_" + timeStamp + "_" + uuid + extension;
    }
}
