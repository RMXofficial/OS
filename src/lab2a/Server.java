package lab2a;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private int port;

    public Server(int port) {
        this.port = port;
    }
    @Override
    public void run() {
        System.out.println("server starting");
        ServerSocket serverSocket = null;
        try {
            serverSocket=new ServerSocket(this.port);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("server started");
        System.out.println("server listening on port " + this.port);
        while (true){
            Socket socket=null;
            try {
                //accept metodot e blokiracki
                socket=serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("server accepted");
            new Worker(socket).start();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(7000);
        server.start();
    }
}
