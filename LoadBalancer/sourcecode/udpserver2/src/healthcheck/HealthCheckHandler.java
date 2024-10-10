package healthcheck;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HealthCheckHandler {
    private final int port; // HealthCheck 포트

    public HealthCheckHandler(int port) {
        this.port = port;
    }

    public void start() {
        new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket(port)) {
                System.out.println("HealthCheckHandler (UDP) is running on port " + port);

                while (true) {
                    try {
                        // 요청 수신
                        byte[] buffer = new byte[1024];
                        DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                        socket.receive(requestPacket);

                        String request = new String(requestPacket.getData(), 0, requestPacket.getLength());
                        System.out.println("Received HealthCheck request: " + request);

                        // 요청 처리 및 응답 생성
                        String response = handleRequest(request);

                        // 응답 전송
                        byte[] responseBytes = response.getBytes();
                        InetAddress clientAddress = requestPacket.getAddress();
                        int clientPort = requestPacket.getPort();
                        DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, clientAddress, clientPort);
                        socket.send(responsePacket);
                        System.out.println("Sent HealthCheck response: " + response);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String handleRequest(String request) {
        // 요청이 "hello"이면, "ack" 값도 "hello"로 응답
        if ("{\"cmd\":\"hello\"}".equals(request)) {
            return "{\"ack\":\"hello\"}";
        } else {
            // 알 수 없는 명령어에 대한 응답
            return "{\"ack\":\"failed\",\"msg\":\"Unknown command\"}";
        }
    }

    public static void main(String[] args) {
        int port = 80; // HealthCheck 포트 설정
        HealthCheckHandler healthCheckHandler = new HealthCheckHandler(port);
        healthCheckHandler.start();
    }
}

