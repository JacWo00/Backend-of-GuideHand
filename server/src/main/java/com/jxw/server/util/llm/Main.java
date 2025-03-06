package com.jxw.server.util.llm;

public class Main {

    public static void main(String[] args) {
        String sid = Session.getSessionId();
        System.out.println("session id: " + sid);
//        HttpSSE sseClient = new HttpSSE();
//        sseClient.sseInvoke("请问你知道我的名字吗？", "7011b06d-5452-4cc1-b0a9-9ee7ad85f78f");
        String reqId = Session.getRequestId();
        System.out.println("request id: " + reqId);
        MyWebSocket webSocketClient = new MyWebSocket();
        webSocketClient.webSocketInvoke("你是谁？",sid, reqId);
    }
}