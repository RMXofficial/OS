package lab3a;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Worker extends Thread {
    private Socket clientSocket;
    private AtomicInteger messageCount;

    public Worker(Socket socket, AtomicInteger messageCount) {
        this.clientSocket = socket;
        this.messageCount = messageCount;
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
