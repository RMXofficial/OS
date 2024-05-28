package lab3a;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        try {
            // 1. Сокет за испраќање и примање на UDP пораки
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");

            // 2. Испраќање на login порака до серверот
            String loginMessage = "login";
            byte[] loginBuffer = loginMessage.getBytes();
            DatagramPacket loginPacket = new DatagramPacket(loginBuffer, loginBuffer.length, serverAddress, 12345);
            socket.send(loginPacket);

            // 3. Примање на одговор од серверот за login порака
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            String loginResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Одговор од серверот: " + loginResponse);

            // 4. Испраќање на други пораки до серверот
            String[] messages = {"Hello", "World", "logout"};
            for (String msg : messages) {
                byte[] sendBuffer = msg.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 12345);
                socket.send(sendPacket);

                // 5. Примање на одговор од серверот за секоја испратена порака
                socket.receive(receivePacket);
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Одговор од серверот: " + response);
            }

            // 6. Затворање на сокетот
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
