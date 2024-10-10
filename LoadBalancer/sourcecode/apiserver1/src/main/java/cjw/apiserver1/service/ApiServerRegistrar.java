package cjw.apiserver1.service;

import org.springframework.web.client.RestTemplate;
import java.util.Scanner;

public class ApiServerRegistrar {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String loadBalancerUrl;


    public ApiServerRegistrar(String loadBalancerUrl) {
        this.loadBalancerUrl = loadBalancerUrl;

    }

    public void startConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 to register or 2 to unregister the server. Type 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Exiting...");
                break;
            } else if ("1".equals(input)) {
                // Register request
                String jsonRequest = String.format(
                        "{\"cmd\":\"register\",\"protocol\":\"api\",\"port\":81}");
                String response = sendRequest(jsonRequest);
                System.out.println("Response from LoadBalancer: " + response);
            } else if ("2".equals(input)) {
                // Unregister request
                String jsonRequest = String.format(
                        "{\"cmd\":\"unregister\",\"protocol\":\"api\",\"port\":81}");
                String response = sendRequest(jsonRequest);
                System.out.println("Response from LoadBalancer: " + response);
            } else {
                System.out.println("Invalid input. Please enter '1' to register, '2' to unregister, or 'exit' to quit.");
            }
        }

        scanner.close();
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
