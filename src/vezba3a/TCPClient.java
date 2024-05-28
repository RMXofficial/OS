package vezba3a;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient extends Thread{
    private String serverName;
    private int serverePort;

    public TCPClient(int serverePort, String serverName) {
        this.serverePort = serverePort;
        this.serverName = serverName;
    }
    @Override
    public void run(){
        try {
        Socket socket=new Socket(InetAddress.getByName(serverName),this.serverePort);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        //bufferedReader.lines().forEach(line  -> System.out.println(line));
        bufferedWriter.write("GET / HTTP/1.1\n");
        bufferedWriter.write("Host: localhost: \n\n");
        bufferedWriter.flush();
        bufferedReader.readLine();
        bufferedReader.readLine();
        System.out.println(bufferedReader.readLine());
        bufferedWriter.close();
        bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        TCPClient client = new TCPClient(8080,"serverr");
        client.start();
    }
}
