import healthcheck.HealthCheckHandler;
import service.UdpServer;

public class Main {
    public static void main(String[] args) {
        int port = 9011;

        String loadBalancerHost = "localhost"; // 로드 밸런서 호스트
        int loadBalancerPort = 8080; // 로드 밸런서 포트

        UdpServer udpServer = new UdpServer(loadBalancerHost, loadBalancerPort);
        Thread udpServerThread = new Thread(() -> {
            udpServer.startConsole(port);
        });

        udpServerThread.start();

        System.out.println("UdpServer is running in a separate thread.");

        HealthCheckHandler healthCheckHandler = new HealthCheckHandler(port);
        healthCheckHandler.start();
    }
}