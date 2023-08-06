package com.liu.nyxs.dataStructure.link;



public class Test {

    public static void main(String[] args) {

        SinglyLinkedList sl = new SinglyLinkedList();
        sl.addFirst(1);
        sl.addFirst(2);
        sl.addFirst(3);
        sl.addFirst(4);
        sl.loop(System.out::println);

        sl.loop2(System.out::println);

    }
}
