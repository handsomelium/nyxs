package com.liu.nyxs.dataStructure.link;

import java.util.function.Consumer;

public class SinglyLinkedList {

    // 头部节点
    private Node head;

    // 节点类
    private static class Node {
        int value;
        Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public void addFirst(int value) {
        this.head = new Node(value, this.head);
    }


    public void loop(Consumer<Integer> consumer) {
        Node curr = this.head;
        while (curr != null) {
            // 做一些事
            consumer.accept(curr.value);
            curr = curr.next;
        }
    }


    public void loop2(Consumer<Integer> consumer) {
        for (Node curr = this.head; curr != null; curr = curr.next) {
            consumer.accept(curr.value);
        }
    }



}