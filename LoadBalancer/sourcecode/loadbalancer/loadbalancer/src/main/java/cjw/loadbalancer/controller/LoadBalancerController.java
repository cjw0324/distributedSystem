package cjw.loadbalancer.controller;



import cjw.loadbalancer.service.LoadBalancer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/loadbalancer") //localhost:8080/loadbalancer
public class LoadBalancerController {

    @Autowired
    private LoadBalancer loadBalancer;

    // /loadbalancer/register 경로로 들어오는 POST 요청을 처리
    @PostMapping("/register") //localhost:8080/loadbalancer/register
    public ResponseEntity<String> handleServerRequest(@RequestBody String jsonRequest, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr(); // 클라이언트 IP 주소
        int clientPort = request.getRemotePort();  // 클라이언트 포트

        String response = loadBalancer.handleServerRequest(jsonRequest, clientIp);

        log.info("client server ip : {}, port : {}", clientIp, clientPort);

        return ResponseEntity.ok(response);
    }

}
