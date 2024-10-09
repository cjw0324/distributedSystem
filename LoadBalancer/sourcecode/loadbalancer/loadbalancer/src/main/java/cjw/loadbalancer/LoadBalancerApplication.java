package cjw.loadbalancer;

import cjw.loadbalancer.service.UdpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoadBalancerApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoadBalancerApplication.class, args);
		// UDP 서버를 별도의 스레드에서 실행
		new Thread(() -> {
			UdpServer udpServer = new UdpServer();
			udpServer.start();
		}).start();
	}
}
