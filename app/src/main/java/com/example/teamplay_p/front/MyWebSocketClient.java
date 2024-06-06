package com.example.teamplay_p.front;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MyWebSocketClient {
    private static final String WS_URL = "ws://172.30.1.78:4000/ws"; // 서버 웹소켓 URL
    private OkHttpClient client;
    private WebSocket webSocket;
    private Gson gson;
    private OnMessageReceivedListener onMessageReceivedListener;

    public MyWebSocketClient() {
        client = new OkHttpClient();
        gson = new Gson();
        Request request = new Request.Builder().url(WS_URL).build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                // WebSocket 연결이 열리면 호출됩니다.
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                // 서버에서 텍스트 메시지를 받으면 호출됩니다.
                if (onMessageReceivedListener != null) {
                    onMessageReceivedListener.onMessageReceived(text);
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                // 서버에서 바이트 메시지를 받으면 호출됩니다.
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                // WebSocket 연결이 닫히기 직전에 호출됩니다.
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                // 연결이 실패하면 호출됩니다.
            }
        });
    }

    public void setOnMessageReceivedListener(OnMessageReceivedListener listener) {
        this.onMessageReceivedListener = listener;
    }

    public void sendMessage(String message) {
        webSocket.send(message);
    }

    public void closeConnection() {
        webSocket.close(1000, "Goodbye!");
    }

    public interface OnMessageReceivedListener {
        void onMessageReceived(String message);
    }
}