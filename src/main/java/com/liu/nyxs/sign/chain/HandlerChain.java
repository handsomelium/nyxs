package com.liu.nyxs.sign.chain;

import cn.hutool.http.Header;

public class HandlerChain {

    private Handler head;

    private Handler tail;


    public void addHandler(Handler handler){
        if (head == null && tail == null){
            head = handler;
            tail = handler;
            return;
        }
        tail.next = handler;
        tail = handler;

    }

    public void doHandler(){
        Handler p = head;
        while (p != null){
            p.handle();
            p = p.next;

        }
    }

    public static void main(String[] args) {
        HandlerChain handlerChain = new HandlerChain();
        handlerChain.addHandler(new AHandler());
        handlerChain.addHandler(new BHandler());
        handlerChain.doHandler();

    }



}
