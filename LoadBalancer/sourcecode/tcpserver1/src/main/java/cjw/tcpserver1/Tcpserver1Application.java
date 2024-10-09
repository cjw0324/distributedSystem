package cjw.tcpserver1;

import cjw.tcpserver1.service.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Tcpserver1Application {

	public static void main(String[] args) {
		SpringApplication.run(Tcpserver1Application.class, args);
		// 별도 스레드에서 TCP 서버를 시작
		new Thread(() -> {
			TcpServer tcpServer = new TcpServer(9001);
			tcpServer.start();
		}).start();
	}

}
