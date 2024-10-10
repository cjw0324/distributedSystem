package cjw.apiserver2;

import cjw.apiserver2.service.ApiServerRegistrar;
import cjw.apiserver2.service.HealthCheckHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Apiserver2Application {

	public static void main(String[] args) {
		SpringApplication.run(Apiserver2Application.class, args);
		// 로드 밸런서 URL 설정
		String loadBalancerUrl = "http://localhost:8080";

		// ApiServerRegistrar 인스턴스 생성 및 실행
		ApiServerRegistrar registrar = new ApiServerRegistrar(loadBalancerUrl);
		new Thread(registrar::startConsole).start();

		// HealthCheckHandler 인스턴스 생성 및 실행
		HealthCheckHandler healthCheckHandler = new HealthCheckHandler(8084);
		new Thread(healthCheckHandler::start).start();



		System.out.println("API Server is running with HealthCheck and command handling enabled.");
	}

}
