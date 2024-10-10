package cjw.loadbalancer.healthcheck;

import cjw.loadbalancer.repository.ServerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
                    String host = serverInfo[0];
                    int port = Integer.parseInt(serverInfo[1]);
                    String protocol = serverInfo[2];

                    for (String s : serverInfo) {
                        System.out.println(s);
                    }


                    boolean isHealthy = checkServerHealth(host, port, protocol);
                    if (!isHealthy) {
                        // HealthCheck 실패 시 서버 목록에서 제거
                        System.out.println("Removing " + host + ":" + port + " from registered servers.");
                        iterator.remove();
                        serverRepository.remove(host + ":" + port+":"+protocol);
                    }
                }

            } catch (InterruptedException e) {
//                e.printStackTrace();
                log.info("disconnect");
            }
        }
    }

    private boolean checkServerHealth(String host, int port, String protocol) {
        if (protocol.equals("tcp") || protocol.equals("api")) {
            try (Socket socket = new Socket(host, port);
                 OutputStream outputStream = socket.getOutputStream();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(outputStream, true)) {

                // HealthCheck 요청 전송
                String healthCheckMessage = "{\"cmd\":\"hello\"}";
                out.println(healthCheckMessage);
                out.flush();

                // 응답 수신
                String response = in.readLine();
                System.out.println("Received response from " + host + ":" + port + " - " + response);

                // 응답 검증
                return response != null && response.equals("{\"ack\":\"hello\"}");
            } catch (Exception e) {
                // HealthCheck 실패 시
                System.out.println("HealthCheck failed for " + host + ":" + port);
//                e.printStackTrace();
                log.info("disconnect");
                return false;
            }
        }  else if (protocol.equals("udp")) {
            // // ip,port 로 udp 통신으로 String healthCheckMessage = "{\"cmd\":\"hello\"}"; healthCheckMessage 보내기
            // UDP 통신을 사용하여 HealthCheck 수행
            try (DatagramSocket socket = new DatagramSocket()) {
                String healthCheckMessage = "{\"cmd\":\"hello\"}";
                byte[] buffer = healthCheckMessage.getBytes();
                InetAddress address = InetAddress.getByName(host);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

                // 요청 전송
                socket.send(packet);

                // 응답 수신
                byte[] responseBuffer = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                socket.setSoTimeout(3000); // 3초 타임아웃 설정
                socket.receive(responsePacket);

                String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
                System.out.println("Received response from " + host + ":" + port + " - " + response);

                // 응답 검증
                return response.equals("{\"ack\":\"hello\"}");
            } catch (Exception e) {
                // HealthCheck 실패 시
                System.out.println("HealthCheck failed for " + host + ":" + port);
//                e.printStackTrace();
                log.info("disconnect");
                return false;
            }
        }
        else{
            return false;
        }
    }


}
