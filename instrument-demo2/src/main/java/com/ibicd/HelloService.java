package com.ibicd;

public class HelloService {


    public String say(){
        System.out.println("===hello world====");
        return "hello world";
    }


    @Julie
    public String say2(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world";
    }

}
