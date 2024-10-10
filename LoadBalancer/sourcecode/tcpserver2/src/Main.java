import healthcheck.HealthCheckHandler;
import loadbalance.TcpServer;

public class Main {
    public static void main(String[] args) {
//        String loadBalancerHost = "localhost"; // 로드 밸런서 호스트
//        int loadBalancerPort = 8080; // 로드 밸런서 포트
//
//
//        TcpServer tcpServer = new TcpServer(loadBalancerHost, loadBalancerPort);
//        tcpServer.startConsole();
        int port = 9002; // HealthCheck 포트 설정

        String loadBalancerHost = "localhost"; // 로드 밸런서 호스트
        int loadBalancerPort = 8080; // 로드 밸런서 포트

        TcpServer tcpServer = new TcpServer(loadBalancerHost, loadBalancerPort);

        // 새로운 스레드를 만들어서 startConsole()을 실행
        Thread tcpServerThread = new Thread(() -> {
            tcpServer.startConsole(port);
        });

        // 스레드 시작
        tcpServerThread.start();

        System.out.println("TcpServer is running in a separate thread.");

        HealthCheckHandler healthCheckHandler = new HealthCheckHandler(port);
        healthCheckHandler.start();
    }
}
