package aud1;

import java.util.ArrayList;
import java.util.List;

public class Biblioteka {
    List<String> books = new ArrayList<>();
    int capacity;

    public Biblioteka(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void returnBook(String book) throws InterruptedException {
        while (books.size() == capacity)
            wait();
            books.add(book);
            notifyAll();
    }

    public synchronized String borrowBook() throws InterruptedException {
        String book = "";
        while (books.size() == 0)
            wait();
            book = books.remove(0);
            notifyAll();
        return book;
    }
}
