package cjw.loadbalancer.service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
    private final String serverAddress;
    private final int serverPort;

    public UdpClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public String sendAndReceive(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            // 메시지를 UDP 서버로 전송
            byte[] sendData = message.getBytes();
            InetAddress serverInetAddress = InetAddress.getByName(serverAddress);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverInetAddress, serverPort);
            socket.send(sendPacket);

            // UDP 서버로부터 응답 수신
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            // 응답 메시지를 문자열로 변환
            return new String(receivePacket.getData(), 0, receivePacket.getLength());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error communicating with UDP server";
        }
    }
}
