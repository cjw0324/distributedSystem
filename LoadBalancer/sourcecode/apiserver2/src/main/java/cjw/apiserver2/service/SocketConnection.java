package cjw.apiserver2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class SocketConnection {

    ObjectMapper objectMapper = new ObjectMapper();
    private final int port; // HealthCheck 포트

    public SocketConnection(int port) {
        this.port = port;
    }

    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                        //
                        String request = in.readLine();
                        Map<String, String> map = objectMapper.readValue(request, Map.class);
                        if (map.containsKey("cmd")) {
                            // 헬스 체크
                            System.out.println("Received HealthCheck request: " + request);

                            String response = handleRequest(request);

                            // 요청에 대한 응답 (요청 문자열 그대로 반환)
                            out.println(response);
                            System.out.println("Sent HealthCheck response: " + response);
                        } else { //로드밸런싱 된 메시지 수신
                            System.out.println(request);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String handleRequest(String request) {
        try {
            // 요청을 JSON으로 파싱
            JsonNode jsonRequest = objectMapper.readTree(request);
            String cmd = jsonRequest.get("cmd").asText();

            // "cmd" 값이 "hello"이면, "ack" 값도 "hello"로 응답
            if ("hello".equals(cmd)) {
                JsonNode jsonResponse = objectMapper.createObjectNode().put("ack", "hello");
                return objectMapper.writeValueAsString(jsonResponse);
            } else {
                // 알 수 없는 명령어에 대한 응답
                JsonNode jsonResponse = objectMapper.createObjectNode()
                        .put("ack", "failed")
                        .put("msg", "Unknown command");
                return objectMapper.writeValueAsString(jsonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // JSON 파싱 오류에 대한 응답
            JsonNode jsonResponse = objectMapper.createObjectNode()
                    .put("ack", "failed")
                    .put("msg", "Invalid JSON format");
            try {
                return objectMapper.writeValueAsString(jsonResponse);
            } catch (Exception ex) {
                ex.printStackTrace();
                return "{\"ack\":\"failed\",\"msg\":\"Internal server error\"}";
            }
        }
    }
}
