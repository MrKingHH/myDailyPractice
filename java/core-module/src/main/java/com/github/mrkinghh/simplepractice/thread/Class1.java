package com.github.mrkinghh.simplepractice.thread;

/**
 * @author: SamuelKing
 * @date: 2019/10/26
 */
public class Class1 implements Runnable {

    private Thread thread;
    private String threadName;

    Class1(String threadName) {
        this.threadName = threadName;
        System.out.println("Creating thread:" + threadName);
    }


    @Override
    public void run() {
        System.out.println("Running thread:" + threadName);
        try {
            for (int i = 1; i <= 4; i++) {
                System.out.println("Printing thread:" + threadName + "," + i);

                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }

        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start() {
        System.out.println("Starting thread:" + threadName);
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    public void join() throws InterruptedException {
        thread.join();
    }
}
