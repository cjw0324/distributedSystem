import service.UdpServer;

public class Main {
    public static void main(String[] args) {
        String loadBalancerHost = "localhost"; // 로드 밸런서 호스트
        int loadBalancerPort = 8080; // 로드 밸런서 포트

        UdpServer udpServer = new UdpServer(loadBalancerHost, loadBalancerPort);
        udpServer.startConsole();
    }
}