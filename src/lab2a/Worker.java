package lab2a;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Worker extends Thread{
    private Socket socket;

    public Worker(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run(){
        BufferedReader reader=null;
        BufferedWriter writer=null;
        try {
            reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //bufferedReader.lines().forEach(line  -> System.out.println(line));
            writer.write("HTTP/1.1 200 OK\n");
            writer.write("Content-Type: text/html\n\n");
            writer.write("hello world");
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                reader.close();
                writer.close();
                this.socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //TODO treba da se prave WebRequest a jas ke pravam samo gore
    }
}
