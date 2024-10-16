package cjw.loadbalancer.service;

import cjw.loadbalancer.repository.ServerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class LoadBalancingService {
    private final ServerRepository serverRepository = ServerRepository.getInstance();
    private int currentIndex = 0; // 라운드 로빈 인덱스

    public String handleSendingMessage(String message) {
        Set<String> servers = serverRepository.findAll();

        if (servers.isEmpty()) {
            return "No servers available";
        }

        // Set을 List로 변환하여 인덱스를 기반으로 순회
        List<String> serverList = new ArrayList<>(servers);

        // 현재 인덱스에 해당하는 서버 선택
        String server = serverList.get(currentIndex);
        currentIndex = (currentIndex + 1) % serverList.size(); // 인덱스 업데이트

        String[] serverInfo = server.split(":");
        String hostIp = serverInfo[0];
        int port = Integer.parseInt(serverInfo[1]);
        String protocol = serverInfo[2];

        boolean success = sendMessageToServer(hostIp, port, protocol, message);
        return success ? "Message sent successfully" : "Failed to send message";
    }

    private boolean sendMessageToServer(String hostIp, int port, String protocol, String message) {
        if (protocol.equals("tcp")) {
            // TCP 또는 HTTP 통신을 사용하여 메시지 전송
            try (Socket socket = new Socket(hostIp, port);
                 OutputStream outputStream = socket.getOutputStream();
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(outputStream, true)) {

                out.println(message);
                out.flush();
                return true;
            } catch (Exception e) {
                log.error("Failed to send message to {}:{}", hostIp, port, e);
                return false;
            }
        } else if (protocol.equals("api")) {
            try {
                System.out.println(sendApi(hostIp, port, message));
                return true;
            } catch (Exception e) {
                log.error("Failed to send message to {}:{}", hostIp, port, e);
                return false;
            }
        }
        else if (protocol.equals("udp")) {
            // UDP 통신을 사용하여 메시지 전송
            try (DatagramSocket socket = new DatagramSocket()) {
                byte[] buffer = message.getBytes();
                InetAddress address = InetAddress.getByName(hostIp);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

                socket.send(packet);
                return true;
            } catch (Exception e) {
                log.error("Failed to send message to {}:{}", hostIp, port, e);
                return false;
            }
        }
        return false;
    }

    public String sendApi(String hostIp, int port, String message) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + hostIp + ":" + Integer.toString(port) + "/api/message";
        log.info("send message to = {}", url);

        return restTemplate.postForEntity(url, message, String.class).toString();
    }
}
