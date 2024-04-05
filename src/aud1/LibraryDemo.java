package aud1;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

public class LibraryDemo {
    public static void main(String[] args) throws InterruptedException {
        List<Member> members=new ArrayList<>();
        SemaphoreBiblioteka biblioteka=new SemaphoreBiblioteka(10);
        for (int i = 0; i < 10; i++) {
            Member member=new Member("Member "+i,biblioteka);
            members.add(member);
        }
        for (Member member:members) {
            member.start();
        }
        for (Member member:members){
            member.join(2000);
        }
        System.out.println("succesfully");
    }
}
class Member extends Thread{
    private String name;
    private SemaphoreBiblioteka library;
    public Member(String name, SemaphoreBiblioteka library){
        this.name=name;
        this.library=library;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("Member "+i+" returns book");
            try {
                library.returnbook("Book "+i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i < 2; i++) {
            System.out.println("Member "+i+" borrows book");
            try {
                library.borrowbook();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
