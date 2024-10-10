package cjw.tcpserver1.service;

import org.springframework.web.client.RestTemplate;

public class TcpServerRegistrar {

    public void register(String loadBalancerUrl) {
        String jsonRequest = "{\"cmd\":\"register\",\"protocol\":\"tcp\",\"port\":80}";
        RestTemplate restTemplate = new RestTemplate();

        try {
            String response = restTemplate.postForObject(loadBalancerUrl, jsonRequest, String.class);
            System.out.println("Registration response from LoadBalancer: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to register with LoadBalancer");
        }
    }
}
