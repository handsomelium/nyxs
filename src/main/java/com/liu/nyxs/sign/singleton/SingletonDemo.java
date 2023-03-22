package com.liu.nyxs.sign.singleton;

/**
 * @Author lium
 * @Date 2022/4/11
 * @Description
 */
public class SingletonDemo {

    private volatile static SingletonDemo singleton = null;


    public SingletonDemo(){}



    public static SingletonDemo getSingleton(){
        if (singleton == null){

            synchronized (SingletonDemo.class){
                if (singleton == null){
                    singleton = new SingletonDemo();
                }

            }
        }
        return singleton;
    }


}
