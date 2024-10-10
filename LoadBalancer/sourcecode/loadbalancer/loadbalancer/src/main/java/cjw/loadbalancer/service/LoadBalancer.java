package cjw.loadbalancer.service;
import cjw.loadbalancer.repository.ServerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class LoadBalancer {

    ServerRepository serverRepository = ServerRepository.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper(); //json 파싱 내부 java 클래스

    public String handleServerRequest(String jsonRequest, String clientIp) {
        try {
            // 요청을 JSON 형식으로 파싱
            Map<String, Object> requestMap = objectMapper.readValue(jsonRequest, Map.class);
            String cmd = (String) requestMap.get("cmd");
            String protocol = (String) requestMap.get("protocol");
            int port = (int) requestMap.get("port");
            int myport = (int) requestMap.get("myport");

            // 등록 명령어와 프로토콜 검사
            if (port != 80) {
                return createResponse("failed", "Unsupported protocol or port");
            }

            String serverKey = clientIp + ":" + myport + ":" + protocol;
            //127.0.0.1:8081:api
            //127.0.0.1:9001:tcp
            //127.0.0.1:8081:tcp

            // "register" 명령 처리
            if ("register".equalsIgnoreCase(cmd)) {
                if (serverRepository.save(serverKey)) {
                    return createResponse("successful", null);
                } else {
                    return createResponse("failed", "Server is already registered");
                }
            }
            // "unregister" 명령 처리
            else if ("unregister".equalsIgnoreCase(cmd)) {
                if (serverRepository.remove(serverKey)) {
                    return createResponse("successful", null);
                } else {
                    return createResponse("failed", "Server is not registered");
                }
            } else {
                return createResponse("failed", "Invalid command");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return createResponse("failed", "Error processing request: " + e.getMessage());
        }
    }

    private String createResponse(String status, String message) {
        // 응답 메시지 생성
        if (message == null) {
            return String.format("{\"ack\":\"%s\"}", status);
        } else {
            return String.format("{\"ack\":\"%s\",\"msg\":\"%s\"}", status, message);
        }
    }
}
