package vezba3a;

import java.io.*;
import java.net.Socket;

public class Worker extends Thread{
    private Socket clientSocket;
    public Worker(Socket clientSocket) {
        this.clientSocket=clientSocket;
    }
    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String result=getResult(bufferedReader.readLine().split("\\s")[1].replaceFirst("/",""));
            bufferedWriter.write("HTTP/1.1 200 OK\n\n");
            bufferedWriter.write(result);
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String getResult(String line) {
        String []values=line.split("");
        switch (values[1]){
            case "+": return line + " = "+(Integer.parseInt(values[0])+Integer.parseInt(values[2]));
            case "-": return line + " = "+(Integer.parseInt(values[0])-Integer.parseInt(values[2]));
            case "*": return line + " = "+(Integer.parseInt(values[0])*Integer.parseInt(values[2]));
            case "/": return line + " = "+(Integer.parseInt(values[0])/Integer.parseInt(values[2]));
        }
        return "";
    }
}
