package lab3a;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TCPServer {
    private static AtomicInteger messageCount = new AtomicInteger(0); // Бројач за примени пораки

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("TCP серверот е стартуван на портата 12345...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент поврзан: " + clientSocket.getInetAddress());

                // Креирање на нова нишка за секој клиент
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Внатрешна класа за обработка на клиенти
    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            ) {
                String message = in.readLine();

                // Проверка дали првата порака е login
                if (!"login".equalsIgnoreCase(message)) {
                    out.println("Конекцијата е затворена бидејќи првата порака не беше 'login'");
                    clientSocket.close();
                    return;
                }

                out.println("logged in");

                // Обработка на пораки од клиентот
                while ((message = in.readLine()) != null) {
                    messageCount.incrementAndGet(); // Зголемување на бројачот на примени пораки

                    if ("logout".equalsIgnoreCase(message)) {
                        out.println("logged out");
                        break;
                    } else {
                        out.println("echo: " + message);
                    }
                }

                clientSocket.close();
                System.out.println("Клиентот се одјави: " + clientSocket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
