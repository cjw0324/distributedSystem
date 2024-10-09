package cjw.loadbalancer.controller;

import cjw.loadbalancer.service.LoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
