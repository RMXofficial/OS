package vezba3b;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private static int[] messageCount=new int[1];
    private int port;

    public Server(int port) {
        this.port = port;
    }
    @Override
    public void run(){
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            System.out.println("TCP server started on port "+port);
            while(true){
                Socket clientSocket=serverSocket.accept();
                System.out.println("client connected: "+clientSocket.getInetAddress());
                new Worker(clientSocket,messageCount).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Server server=new Server(6000);
        server.start();
    }
}
