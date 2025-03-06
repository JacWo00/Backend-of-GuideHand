package com.jxw.server.util.llm;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Arrays;

import static java.util.Collections.singletonMap;

@Service
public class MyWebSocket {
    private final String webSocketUrl = "wss://wss.lke.cloud.tencent.com"; // WebSocket服务器的URL
    private final String path = "/v1/qbot/chat/conn/"; // 服务路径

    public String webSocketInvoke(String content, String sid, String reqId) {
        Token getToken = new Token();
        String token = getToken.getWsToken();
        System.out.println("Token: " + token);

        AtomicReference<String> result = new AtomicReference<>("error"); // 用于存储最终结果
        CountDownLatch latch = new CountDownLatch(1); // 用于同步等待事件处理完成

        try {
            IO.Options options = IO.Options.builder()
                    .setQuery("EIO=4")
                    .setPath(path)
                    .setTransports(new String[]{WebSocket.NAME})
                    .setAuth(singletonMap("token", token))
                    .setTimeout(2000)
                    .build();

            Socket socket = IO.socket(webSocketUrl, options);

            // 监听连接成功事件
            socket.on(Socket.EVENT_CONNECT, args1 -> System.out.println("Connected: " + Arrays.toString(args1)));

            // 监听连接错误事件
            socket.on(Socket.EVENT_CONNECT_ERROR, args2 -> {
                System.out.println("Connection error: " + Arrays.toString(args2));
                // 重新获取token并尝试重新连接
                options.auth.put("token", getToken.getWsToken());
                socket.disconnect(); // 断开当前连接
                socket.io().reconnection(); // 使用新的token重新连接
            });

            // 监听断开事件
            socket.on(Socket.EVENT_DISCONNECT, args3 -> System.out.println("Disconnected: " + Arrays.toString(args3)));

            // 监听reply事件
            socket.on("reply", args4 -> {
                if (args4.length > 0) {
                    JSONObject reply = (JSONObject) args4[0];
                    try {
                        JSONObject payload = reply.getJSONObject("payload");
                        boolean isFromSelf = payload.getBoolean("is_from_self");
                        boolean isFinal = payload.getBoolean("is_final");
                        String respContent = payload.getString("content");
                        if (isFromSelf) {
                            result.set("Received reply, is_from_self, content: " + respContent);
                        } else if (isFinal) {
                            result.set("Received reply, is_final, content: " + respContent);
                            latch.countDown(); // 事件处理完成，释放等待
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Received reply, but args4.length <= 0");
                    result.set("error");
                }

            });

            // 监听error事件
            socket.on("error", args5 -> {
                System.out.println("Received error: " + Arrays.toString(args5));
                latch.countDown(); // 出错时也释放等待
            });

            // 连接WebSocket
            socket.connect();

            // 构造发送的消息
            JSONObject payload = new JSONObject();
            payload.put("request_id", reqId);
            payload.put("content", content);
            payload.put("session_id", sid);
            JSONObject data = new JSONObject();
            data.put("payload", payload);

            // 发送消息
            socket.emit("send", data);

            // 等待事件处理完成
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.get(); // 返回最终结果
    }
}