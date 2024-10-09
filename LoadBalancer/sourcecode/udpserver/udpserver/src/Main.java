import service.UdpServer;

public class Main {
    public static void main(String[] args) {
        int port = 8888; // 사용할 포트를 설정
        UdpServer udpServer = new UdpServer(port);
        udpServer.start();
    }
}