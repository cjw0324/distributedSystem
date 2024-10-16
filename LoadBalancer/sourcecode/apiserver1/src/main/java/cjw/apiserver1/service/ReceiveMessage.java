package cjw.apiserver1.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ReceiveMessage {
    @PostMapping("/message")
    public String receiveMessage(@RequestBody String message) {
        System.out.println(message);
        return message;
    }

    @PostMapping("/healthcheck")
    public String healthCheck(@RequestBody HealthDto healthCheckRequest) {
        System.out.println("Received HealthCheck request: " + healthCheckRequest.getCmd());
        return "{\"ack\":\""+healthCheckRequest.getCmd()+"\"}";
    }

    @Data
    public static class HealthDto{
        private String cmd;
    }
}
