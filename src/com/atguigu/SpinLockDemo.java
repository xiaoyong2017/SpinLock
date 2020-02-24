package com.atguigu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 实现一个自旋锁
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "come in......");
        while (!atomicReference.compareAndSet(null, thread)) {
        }
    }

    public void myUnLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "come out......");
        atomicReference.compareAndSet(thread, null);
    }

    public static void main(String args[])  throws Exception{
        List<String> list = new ArrayList<>();
        SpinLockDemo demo = new SpinLockDemo();
       /* new Thread(() -> {
            demo.myLock();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            demo.myUnLock();
        }, "AA").start();
        Thread.sleep(100);
        new Thread(() -> {
            demo.myLock();
            demo.myUnLock();
        }, "BB").start();
*/
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                demo.myLock();
                try {
                    list.add(UUID.randomUUID().toString().replace("-", "").substring(0, 6));
                    System.out.println(list);
                }finally {
                    demo.myUnLock();
                }
            }, String.valueOf(i)).start();
        }


    }


}
