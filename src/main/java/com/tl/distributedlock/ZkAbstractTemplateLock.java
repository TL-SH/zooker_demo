package com.tl.distributedlock;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * @author tanglei
 */
public abstract class ZkAbstractTemplateLock implements ZkLock{

    public final static String ZKSERVICE = "192.168.43.166:2181";
    public final static int TIME_OUT = 10 * 1000;

    ZkClient zkClient = new ZkClient(ZKSERVICE,TIME_OUT);

    protected String path = "/myZkLock";

    protected CountDownLatch countDownLatch = null;

    @Override
    public void lock() {
        if(tryLock()){
            System.out.println(Thread.currentThread().getName()+"\t 抢占锁成功");
        }else {
            waitLock();
            lock();
        }
    }

    public abstract boolean tryLock();
    public abstract void waitLock();



    @Override
    public void unlock() {
        if(zkClient!=null){
            zkClient.close();
        }
        System.out.println(Thread.currentThread().getName()+"\t 释放锁成功");
        System.out.println();
        System.out.println();
    }
}
