package com.github.shawnliang.register.server;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Description :   .
 *  心跳监视器
 * @author : Phoebe
 * @date : Created in 2021/7/4
 */
public class HeartBeatMessuredRate {

    private static HeartBeatMessuredRate instance
            = new HeartBeatMessuredRate();

    private static long lastMessuredTimeStamp = 0L;

    private static long messuredCount = 0L;

    private static Daemon daemon = new Daemon();;

    public static HeartBeatMessuredRate getInstance() {
        daemon.start();
        return instance;
    }

    public synchronized void increase() {
        if (System.currentTimeMillis() - lastMessuredTimeStamp > 60 * 1000) {
            lastMessuredTimeStamp = System.currentTimeMillis();
        }

        messuredCount++;
    }

    public synchronized long getHeartBeatCount() {
        return messuredCount;
    }

    private static class Daemon extends Thread {

        @Override
        public void run() {
            while (true) {
                synchronized (HeartBeatMessuredRate.class) {
                    if (System.currentTimeMillis() - lastMessuredTimeStamp > 60 * 1000) {
                        lastMessuredTimeStamp = System.currentTimeMillis();
                        messuredCount = 0;
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
