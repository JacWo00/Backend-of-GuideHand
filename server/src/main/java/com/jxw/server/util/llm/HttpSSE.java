package com.jxw.server.util.llm;

import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HttpSSE {

    public String sseInvoke(String content, String sid) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.get("application/json");
        JSONObject reqBody = new JSONObject();
        try {
            reqBody.put("content", content);
            reqBody.put("bot_app_key", botAppKey);
            reqBody.put("visitor_biz_id", visitorBizId);
            reqBody.put("session_id", sid);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Failed to create request body";
        }

        Request request = new Request.Builder()
                .url(sseUrl)
                .post(RequestBody.create(reqBody.toString(), mediaType))
                .build();

        AtomicReference<String> result = new AtomicReference<>("error");
        CountDownLatch latch = new CountDownLatch(1);

        EventSourceListener listener = new EventSourceListener() {
            @Override
            public void onOpen(EventSource eventSource, Response response) {
                System.out.println("SSE connection opened");
            }

            @Override
            public void onClosed(EventSource eventSource) {
                System.out.println("SSE connection closed");
                latch.countDown();
            }

            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                if (type.equals("reply")) {
                    try {
                        JSONObject dataObj = new JSONObject(data);
                        JSONObject payload = dataObj.getJSONObject("payload");
                        if (payload.length() > 0) {
                            boolean isFromSelf = payload.getBoolean("is_from_self");
                            boolean isFinal = payload.getBoolean("is_final");
                            String content = payload.getString("content");
                            if (isFromSelf) {
                                System.out.println("is_from_self, event: " + type + ", content: " + content);
                            } else if (isFinal) {
                                result.set("is_final, event: " + type + ", content: " + content);
                                latch.countDown(); // 仅在接收到 is_final 事件时释放等待
                            }
                        }
                    } catch (JSONException e) {
                        result.set("Error: JSON parsing failed");
                        latch.countDown();
                    }
                } else {
                    System.out.println("Received SSE event: " + type);
                }
            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response) {
                System.err.println("Error occurred: " + t.getMessage());
                result.set("Error: " + t.getMessage());
                latch.countDown();
            }
        };

        EventSource.Factory factory = EventSources.createFactory(client);
        EventSource eventSource = factory.newEventSource(request, listener);

        try {
            latch.await(); // 等待特定事件处理完成
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            eventSource.cancel(); // 关闭事件源
        }

        return result.get();
    }

    private final String sseUrl = "https://wss.lke.cloud.tencent.com/v1/qbot/chat/sse"; // SSE 服务器的 URL
    private static String botAppKey = "TqBrEwPQ"; // 机器人密钥
    private static String visitorBizId = "202403180001"; // 访客 ID
}