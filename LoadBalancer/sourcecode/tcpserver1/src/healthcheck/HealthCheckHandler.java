package healthcheck;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

public class HealthCheckHandler {
    private final int port; // HealthCheck 포트

    public HealthCheckHandler(int port) {
        this.port = port;
    }

    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("HealthCheckHandler is running on port " + port);

                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                        // HealthCheck 요청 읽기
                        String request = in.readLine();
                        System.out.println("Received HealthCheck request: " + request);

                        // 요청 처리 및 응답 생성
                        String response = handleRequest(request);

                        // 요청에 대한 응답 전송
                        out.println(response);
                        System.out.println("Sent HealthCheck response: " + response);

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
        JSONParser parser = new JSONParser();
        try {
            // 요청을 JSON으로 파싱
            JSONObject jsonRequest = (JSONObject) parser.parse(new StringReader(request));
            String cmd = (String) jsonRequest.get("cmd");

            // "cmd" 값이 "hello"이면, "ack" 값도 "hello"로 응답
            JSONObject jsonResponse = new JSONObject();
            if ("hello".equals(cmd)) {
                jsonResponse.put("ack", "hello");
            } else {
                // 알 수 없는 명령어에 대한 응답
                jsonResponse.put("ack", "failed");
                jsonResponse.put("msg", "Unknown command");
            }
            return convertToJsonString(jsonResponse);

        } catch (ParseException e) {
            e.printStackTrace();
            // JSON 파싱 오류에 대한 응답
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("ack", "failed");
            jsonResponse.put("msg", "Invalid JSON format");
            return convertToJsonString(jsonResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // JSONObject를 수동으로 JSON 문자열로 변환
    private String convertToJsonString(JSONObject jsonObject) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Object key : jsonObject.keySet()) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            sb.append("\"").append(key).append("\":\"").append(jsonObject.get(key)).append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

}
