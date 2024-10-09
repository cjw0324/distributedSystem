package service;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpServer {
    private final int port;

    public UdpServer(int port) {
        this.port = port;
    }

    public void start() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            byte[] buffer = new byte[1024];
            System.out.println("UDP Server is running on port " + port);

            while (true) {
                // 클라이언트로부터의 데이터그램 패킷을 수신
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received UDP message: " + received);

                // 응답 메시지 생성
                String response = "Hello from UDP Server!";
                byte[] responseData = response.getBytes();

                // 클라이언트로 응답 전송
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                socket.send(responsePacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
