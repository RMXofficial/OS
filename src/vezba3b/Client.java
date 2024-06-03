package vezba3b;

import java.net.Socket;
import java.io.*;

public class Client extends Thread {
    private String serverAdress;
    private int serverPort;

    public Client(String serverAdress, int serverPort) {
        this.serverAdress = serverAdress;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(serverAdress, serverPort);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("connected to server. Type 'login' to login");
            bufferedWriter.write(in.readLine() + "\n");
            bufferedWriter.flush();
            System.out.println("server response: " + bufferedReader.readLine());

            while (true) {
                System.out.println("enter message or type 'logout' to log out: ");
                bufferedWriter.write(in.readLine() + "\n");
                bufferedWriter.flush();
                String response = bufferedReader.readLine();
                System.out.println("server response: " + response);
                if ("logged out".equalsIgnoreCase(bufferedReader.readLine())) {
                break;
                }
            }

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 6000);
        client.start();
    }
}