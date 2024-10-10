import loadbalance.UdpServer;
import service.UdpSocketConnection;

public class Main {
    public static void main(String[] args) {

        int port = 85; // UDP 통신 포트 설정

        String loadBalancerHost = "localhost"; // 로드 밸런서 호스트
        int loadBalancerPort = 8080; // 로드 밸런서 포트

        UdpServer udpServer = new UdpServer(loadBalancerHost, loadBalancerPort);

        // 새로운 스레드를 만들어서 startConsole()을 실행
        Thread udpServerThread = new Thread(udpServer::startConsole);

        // 스레드 시작
        udpServerThread.start();

        UdpSocketConnection udpSocketConnection = new UdpSocketConnection(port);
        udpSocketConnection.start();

        System.out.println("UdpServer is running in a separate thread.");
    }
}
