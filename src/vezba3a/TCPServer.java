package vezba3a;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread{
    private int port;
    ServerSocket socket;
    public TCPServer(int port) {
        this.port = port;
    }
    @Override
    public void run() {
        try {
            this.socket=new ServerSocket(this.port);
        System.out.println("server started at port: "+this.port);
        Socket clientSocket=null;
        while (true){
            clientSocket=socket.accept();
            System.out.println("client connected");
            Worker worker=new Worker(clientSocket);
            worker.start();
        }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        TCPServer server=new TCPServer(8080);
        server.start();
    }
}
