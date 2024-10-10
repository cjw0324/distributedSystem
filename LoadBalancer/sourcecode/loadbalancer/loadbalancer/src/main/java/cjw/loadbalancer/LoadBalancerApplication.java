package cjw.loadbalancer;

import cjw.loadbalancer.healthcheck.HealthCheck;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoadBalancerApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoadBalancerApplication.class, args);

		HealthCheck healthCheck = new HealthCheck();
		healthCheck.start();
	}
}
