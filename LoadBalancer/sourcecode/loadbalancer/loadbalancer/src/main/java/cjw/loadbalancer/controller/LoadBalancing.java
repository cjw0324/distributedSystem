package cjw.loadbalancer.controller;

import cjw.loadbalancer.service.LoadBalancingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/send-message2server") //localhost:8080/send-message2server
public class LoadBalancing {

    private final LoadBalancingService loadBalancingService = new LoadBalancingService();

    @PostMapping
    public ResponseEntity<String> handleServerRequest(@RequestBody String jsonRequest, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr(); // 클라이언트 IP 주소
        log.info("Received request from IP: {}", clientIp);

        // 메시지를 라운드 로빈 방식으로 전송
        String result = loadBalancingService.handleSendingMessage(jsonRequest);

        return ResponseEntity.ok(result);
    }
}
