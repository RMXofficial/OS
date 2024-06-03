package vezba3b;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
    private Socket clientSocket;
    private int[] messageCount;

    public Worker(Socket clientSocket, int[] messageCount) {
        this.clientSocket = clientSocket;
        this.messageCount = messageCount;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            if (!"login".equalsIgnoreCase(bufferedReader.readLine())) {
                bufferedWriter.write("conncetion closed\n");
                bufferedWriter.flush();
                clientSocket.close();
                return;
            }
            bufferedWriter.write("logged in\n");
            bufferedWriter.flush();
            while (bufferedReader.readLine() != null) {
                Lock lock = new ReentrantLock();
                lock.lock();
                messageCount[0]++;
                lock.unlock();
                if ("logout".equalsIgnoreCase(bufferedReader.readLine())) {
                    bufferedWriter.write("logged out\n");
                    bufferedWriter.flush();
                    break;
                } else {
                    bufferedWriter.write("echo:" + bufferedReader.readLine() + "\n");
                    bufferedWriter.flush();
                }
            }
            clientSocket.close();
            System.out.println("client disconnected: "+clientSocket.getInetAddress());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
