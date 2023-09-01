package com.liu.nyxs.dataStructure.link;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;

public class SinglyLinkedList implements Iterable<Integer> {

    // 头部节点
    private Node head;



    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            Node curr = head;
            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public Integer next() {
                int value = curr.value;
                curr = curr.next;
                return value;
            }
        };
    }



    // 节点类
    private static class Node {
        int value;
        Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    /**
     * 向头部插入
     */
    public void addFirst(int value) {
        this.head = new Node(value, this.head);

    }


    /**
     * 向尾部插入
     */
    public void addLast(int value) {
        Node lastNode = findLast();
        if (lastNode == null){
            addFirst(value);
        }else {
            lastNode.next = new Node(value, null);
        }


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


    /**
     * 查找最后一个元素
     */
    public Node findLast(){
        Node curr = this.head;
        if (curr == null){
            return null;

        }
        while (curr.next != null){
            curr = curr.next;

        }
        return curr;

    }


    /**
     * 根据索引获取节点
     */
    private Node findNode(int index) {
        int i = 0;
        for (Node curr = this.head; curr != null; curr = curr.next, i++) {
            if (i == index) {
                return curr;
            }
        }
        return null;
    }


    private IllegalArgumentException illegalIndex(int index) {
        return new IllegalArgumentException(String.format("index [%d] 不合法%n", index));
    }

    /**
     * 根据索引获取
     */
    public int get(int index) {
        Node node = findNode(index);
        if (node != null) {
            return node.value;
        }
        throw illegalIndex(index);
    }


    /**
     * 根据索引删除节点
     */
    public void remove(int index) {
        if (index == 0) {
            if (this.head != null) {
                this.head = this.head.next;
                return;
            } else {
                throw illegalIndex(index);
            }
        }
        Node prev = findNode(index - 1);
        Node curr;
        if (prev != null && (curr = prev.next) != null) {
            // 上一个节点和被删除的节点不为空
            prev.next = curr.next;
        } else {
            throw illegalIndex(index);
        }
    }



}