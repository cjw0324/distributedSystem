package cjw.loadbalancer.healthcheck;

import cjw.loadbalancer.repository.ServerRepository;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Iterator;
@Slf4j
public class HealthCheck {
    ServerRepository serverRepository = ServerRepository.getInstance();

    public void start() {
        new Thread(this::performHealthCheck).start();
    }

    private void performHealthCheck() {
        while (true) {
            try {
                // 10초마다 HealthCheck 수행
                Thread.sleep(3000);
                System.out.println("Performing HealthCheck...");

                // 등록된 서버들에 대해 HealthCheck 실행
                Iterator<String> iterator = serverRepository.findAll().iterator();
                while (iterator.hasNext()) {
                    String server = iterator.next();
                    String[] serverInfo = server.split(":");
                    String hostIp = serverInfo[0];
                    int port = Integer.parseInt(serverInfo[1]);
                    String protocol = serverInfo[2];

                    for (String s : serverInfo) {
                        System.out.println(s);
                    }

                    boolean isHealthy = checkServerHealth(hostIp, port, protocol);
                    if (!isHealthy) {
                        // HealthCheck 실패 시 서버 목록에서 제거
                        System.out.println("Removing " + hostIp + ":" + port + " from registered servers.");
                        iterator.remove();
                        serverRepository.remove(hostIp + ":" + port+":"+protocol);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                log.info("disconnect");
            }
        }
    }

    private boolean checkServerHealth(String hostIp, int port, String protocol) {
        if (protocol.equals("tcp") || protocol.equals("api")) {
            try (Socket socket = new Socket(hostIp, port);
                 OutputStream outputStream = socket.getOutputStream();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(outputStream, true)) {

                // HealthCheck 요청 전송
                String healthCheckMessage = "{\"cmd\":\"hello\"}";
                out.println(healthCheckMessage);
                out.flush();

                // 응답 수신
                String response = in.readLine();
                System.out.println("Received response from " + hostIp + ":" + port + " - " + response);

                // 응답 검증
                return response != null && response.equals("{\"ack\":\"hello\"}");
            } catch (Exception e) {
                // HealthCheck 실패 시
                System.out.println("HealthCheck failed for " + hostIp + ":" + port);
                e.printStackTrace();
                log.info("disconnect");
                return false;
            }
        }  else if (protocol.equals("udp")) {
            // // ip,port 로 udp 통신으로 String healthCheckMessage = "{\"cmd\":\"hello\"}"; healthCheckMessage 보내기
            // UDP 통신을 사용하여 HealthCheck 수행
            try (DatagramSocket socket = new DatagramSocket()) {
                String healthCheckMessage = "{\"cmd\":\"hello\"}";
                byte[] buffer = healthCheckMessage.getBytes();
                InetAddress address = InetAddress.getByName(hostIp);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

                // 요청 전송
                socket.send(packet);

                // 응답 수신
                byte[] responseBuffer = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.setSoTimeout(3000); // 3초 타임아웃 설정
                socket.receive(responsePacket);

                String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
                System.out.println("Received response from " + hostIp + ":" + port + " - " + response);

                // 응답 검증
                return response.equals("{\"ack\":\"hello\"}");
            } catch (Exception e) {
                // HealthCheck 실패 시
                System.out.println("HealthCheck failed for " + hostIp + ":" + port);
                e.printStackTrace();
                log.info("disconnect");
                return false;
            }
        }
        else{
            return false;
        }
    }
}
