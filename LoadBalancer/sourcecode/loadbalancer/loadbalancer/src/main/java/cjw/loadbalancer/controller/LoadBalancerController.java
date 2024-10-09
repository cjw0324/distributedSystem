package cjw.loadbalancer.controller;

import cjw.loadbalancer.service.LoadBalancer;
import cjw.loadbalancer.service.UdpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/loadbalancer")
public class LoadBalancerController {

    @Autowired
    private LoadBalancer loadBalancer;
    private final RestTemplate restTemplate = new RestTemplate();

    // API 서버에 요청 전달
    @GetMapping("/api")
    public ResponseEntity<String> handleApiRequest() {
        String serverUrl = loadBalancer.getApiServer() + "/api-endpoint";
        return restTemplate.getForEntity(serverUrl, String.class);
    }

    // TCP 서버와의 통신
    @GetMapping("/tcp")
    public ResponseEntity<String> handleTcpRequest() {
        String serverAddress = loadBalancer.getTcpServer();
        // TCP 통신 로직을 구현합니다. 예를 들어, Socket을 사용해 연결합니다.
        return ResponseEntity.ok("TCP request handled by " + serverAddress);
    }

    private final UdpClient udpClient = new UdpClient("localhost", 8888); // UDP 서버 주소와 포트 설정

    // /loadbalancer/udp 경로로 들어오는 POST 요청을 처리
    @GetMapping("/udp")
    public ResponseEntity<String> handleUdpRequest() {
        // UDP 서버로 메시지 전달 및 응답 수신
        String response = udpClient.sendAndReceive("load balancer connect");
        return ResponseEntity.ok(response);
    }
}
