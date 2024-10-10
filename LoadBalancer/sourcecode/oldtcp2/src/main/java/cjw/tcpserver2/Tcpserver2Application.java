package cjw.tcpserver2;

import cjw.tcpserver2.service.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Tcpserver2Application {

	public static void main(String[] args) {
		SpringApplication.run(Tcpserver2Application.class, args);
		// 별도 스레드에서 TCP 서버를 시작
		new Thread(() -> {
			TcpServer tcpServer = new TcpServer(9002);
			tcpServer.start();
		}).start();
	}

}
