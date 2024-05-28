package lab3a;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Поврзан со серверот. Внесете 'login' за најава:");

            // Испраќање на login порака
            String userInput = stdIn.readLine();
            out.println(userInput);
            System.out.println("Одговор од серверот: " + in.readLine());

            // Испраќање на други пораки
            while (true) {
                System.out.print("Внесете порака (или 'logout' за одјава): ");
                userInput = stdIn.readLine();
                out.println(userInput);

                String response = in.readLine();
                System.out.println("Одговор од серверот: " + response);

                if ("logged out".equalsIgnoreCase(response)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
