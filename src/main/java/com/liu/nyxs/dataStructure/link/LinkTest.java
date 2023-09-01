package com.liu.nyxs.dataStructure.link;


import com.liu.nyxs.NyxsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = NyxsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LinkTest {

    @Test
    public void test1(){
        SinglyLinkedList sl = new SinglyLinkedList();
        sl.addFirst(1);
        sl.addFirst(2);
        sl.addFirst(3);
        sl.addFirst(4);
        // sl.loop(System.out::println);

        // sl.loop2(System.out::println);

        sl.forEach(System.out::println);

    }


    @Test
    public void test2(){
        SinglyLinkedList sl = new SinglyLinkedList();
        sl.addLast(1);
        sl.addLast(2);
        sl.addLast(3);
        sl.addLast(4);
        sl.forEach(System.out::println);

    }

    /**
     * 单向链表，带哨兵
     */
    @Test
    public void test3(){
        SinglyLinkedListSentinel slls = new SinglyLinkedListSentinel();
        slls.addLast(1);
        slls.addLast(2);
        slls.addLast(3);
        slls.addLast(4);
        slls.loop(System.out::println);

    }




}
