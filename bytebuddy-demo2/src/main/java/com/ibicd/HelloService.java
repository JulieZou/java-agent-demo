package com.ibicd;

public class HelloService {


    public String say(){
        System.out.println("===hello world1====");
        return "hello world1";
    }


    public String say2(){
        System.out.println("===hello world2====");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world2";
    }

}
