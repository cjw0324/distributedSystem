package cjw.apiserver2.service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiServerRegistrar {
    private final RestTemplate restTemplate = new RestTemplate();
    private String loadBalancerUrl = "http://localhost:8080";
    @Value("${server.port}")
    private String port;

    @GetMapping("/connect")
    public String register(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action"); //action=register
        log.info("api server request : {}", action);
        String jsonRequest;
        if (action.equals("register")) {
            jsonRequest = String.format(
                    "{\"cmd\":\"register\",\"protocol\":\"api\",\"port\":" + port + "}");
        } else if (action.equals("unregister")) {
            jsonRequest = String.format(
                    "{\"cmd\":\"unregister\",\"protocol\":\"api\",\"port\":" + port + "}");
        } else {
            return "잘못된 요청 입니다.";
        }

        log.info("jsonRequest : {}", jsonRequest);
        String ackMessage = sendRequest(jsonRequest);
        System.out.println("Response from LoadBalancer: " + ackMessage);
        return "Response from LoadBalancer: " + ackMessage;
    }

    private String sendRequest(String jsonRequest) {
        try {
            return restTemplate.postForObject(loadBalancerUrl + "/loadbalancer/register", jsonRequest, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"ack\":\"failed\",\"msg\":\"Failed to communicate with LoadBalancer\"}";
        }
    }
}
