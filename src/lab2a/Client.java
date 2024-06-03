package lab2a;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
    private int serverPort;
    public Client(int serverPort) {
        this.serverPort = serverPort;
    }
    @Override
    public void run() {
        Socket socket = null;
        BufferedWriter writer=null;
        BufferedReader reader=null;
        try {
            socket=new Socket(InetAddress.getLocalHost(),serverPort);
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("GET / HTTP/1.1\n");
            writer.write("Host: lab2a.lab2a.com");
            writer.write("\n");
            writer.flush();
            String line;
            while ((line=reader.readLine())!=null){
                System.out.println("client received "+ line);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client(7000);
        client.start();
    }
}
