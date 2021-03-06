package com.aynu.project.highconcurrentproject;


import org.junit.jupiter.api.Test;

public class TestRestTemplate {




    @Test
    public void testThread(){
        Thread thread=new Thread();

        thread.start();

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable");
            }
        };

        runnable.run();

        new Thread(runnable).start();

        MyThread myThread=new MyThread();
        myThread.start();


        MyRunable myRunable=new MyRunable();
        myRunable.run();

    }

    class MyThread extends Thread{

    }

    class MyRunable implements Runnable{
        @Override
        public void run() {
            System.out.println("这是我自治的线程");
        }
    }

    @Test
    public void testThreadInfomation(){
        Thread thread=new Thread();
        System.out.println(thread.getName());
        System.out.println(thread.getThreadGroup());
    }
}
