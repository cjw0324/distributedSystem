import service.UdpServer;

public class Main {
    public static void main(String[] args) {
        System.out.println("UDP SERVER START");
        new Thread(() -> {
            UdpServer udpServer = new UdpServer(9999);
            udpServer.start();
        }).start();
    }
}