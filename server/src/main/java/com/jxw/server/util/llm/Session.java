package com.jxw.server.util.llm;

import java.util.UUID;

public class Session {
    public static String getSessionId() {
        // 生成一个 UUID
        UUID newUuid = UUID.randomUUID();
        // 将 UUID 转换为字符串
       return newUuid.toString();
    }

    public static String getRequestId() {
        // 生成 24 个字节的随机数据
        byte[] randomBytes = new byte[24];
        new java.util.Random().nextBytes(randomBytes);

        // 将随机数据转换为十六进制字符串
        StringBuilder sb = new StringBuilder();
        for (byte b : randomBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}