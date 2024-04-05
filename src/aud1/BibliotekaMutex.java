package aud1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BibliotekaMutex {
    List<String> books = new ArrayList<>();
    public static Lock lock = new ReentrantLock();
    int capacity;

    public BibliotekaMutex(int capacity) {
        this.capacity = capacity;
    }

    public void Return(String book) {

        while (true) {
            lock.lock();
            if (books.size() < capacity) {
                books.add(book);
                lock.unlock();
                break;
            }
            lock.unlock();
        }
    }

    public String Borrow() {
        String book = "";
        while (true) {
            lock.lock();
            if (books.size() > 0) {
                book = books.remove(0);
                lock.unlock();
                break;
            }
            lock.unlock();
        }
        return book;
    }
}
