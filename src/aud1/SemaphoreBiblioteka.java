package aud1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreBiblioteka {
    List<String> books =new ArrayList<>();
    int capacity;
    Semaphore coridinator = new Semaphore(1);
    Semaphore returnbook = new Semaphore(10);
    Semaphore borrowbook= new Semaphore(10);

    public SemaphoreBiblioteka(int capacity){
        this.capacity=capacity;
    }
    public void returnbook(String book) throws InterruptedException {
        returnbook.acquire();
        coridinator.acquire();
        while (books.size()==capacity){
            coridinator.release();
            Thread.sleep(1000);
            coridinator.acquire();
        }
        books.add(book);
        coridinator.release();
        returnbook.release();
    }
    public String borrowbook() throws InterruptedException {
        String book="";
        borrowbook.acquire();
        coridinator.acquire();
        while (books.size()==0){
            coridinator.release();
            Thread.sleep(1000);
            coridinator.acquire();
        }
        book=books.remove(0);
        coridinator.release();
        borrowbook.release();
        return book;
    }
}
