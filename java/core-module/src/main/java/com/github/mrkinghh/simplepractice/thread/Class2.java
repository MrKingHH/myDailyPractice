package com.github.mrkinghh.simplepractice.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;

/**
 * @author: SamuelKing
 * @date: 2019/11/3
 */
public class Class2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();

        //做十次  从0加到100 的求和运算
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = threadPool.submit(() -> {
                int sum = 0;

                for (int j=0; j<=100; j++) {
                    sum+=j;
                }

                return sum;
            });
            futureList.add(future);
        }

        threadPool.shutdown();

        for (Future<Integer> future : futureList) {
            System.out.println(future.get());
        }

    }

}
