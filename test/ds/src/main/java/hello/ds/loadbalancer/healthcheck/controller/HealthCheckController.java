package hello.ds.loadbalancer.healthcheck.controller;

import hello.ds.loadbalancer.healthcheck.HealthCheckMessageDto.MessageDto;
import hello.ds.loadbalancer.healthcheck.service.HealthCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/health")
public class HealthCheckController {
//    @GetMapping
    public MessageDto sendHealthCheckMsgTest(@RequestParam("cmd") String cmd) {
        return new MessageDto(cmd);
    }

    @GetMapping
    public String sendHealthCheckMsg(@RequestParam("cmd") String cmd) {
        HealthCheck healthCheck = new HealthCheck();
        boolean healthCheckResult = healthCheck.sendMessage();
        if (healthCheckResult) {
            log.info("health check success");
        } else {
            log.info("health check failed");
        }
        return "ok";
    }

}
