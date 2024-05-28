package lab3a;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) {
        try {
            // 1. Сокет за примање на UDP пораки на портата 12345
            DatagramSocket socket = new DatagramSocket(12345);
            byte[] receiveBuffer = new byte[1024];
            System.out.println("UDP серверот е стартуван...");

            while (true) {
                // 2. Примање на порака од клиентот
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                // 3. Претворање на примена порака во String
                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Порака од клиентот: " + receivedMessage);

                String responseMessage;
                // 4. Логика за одговарање на пораката од клиентот
                if (receivedMessage.equalsIgnoreCase("login")) {
                    responseMessage = "logged in";
                } else if (receivedMessage.equalsIgnoreCase("logout")) {
                    responseMessage = "logged out";
                } else {
                    responseMessage = "echo-" + receivedMessage;
                }

                // 5. Подготовка на одговорот за клиентот
                byte[] sendBuffer = responseMessage.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);

                // 6. Испраќање на одговорот на клиентот
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
