package com.tl.distributed;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * @author tanglei
 */
public abstract class ZkAbstractTemplateLock implements ZkLock {
    public final  static String ZKSERVER = "192.168.43.166:2181";

    public static final int TIME_OUT = 1 * 1000;

    ZkClient zkClient = new ZkClient(ZKSERVER,TIME_OUT);

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
    //模板设计模式,固定化的流程升级到父类定死规范,但是,具体落地实现方法下放给子类各自实现

    public abstract boolean tryLock();
    public abstract void waitLock();


    @Override
    public void unlock() {
        if(zkClient!=null){
            //相当于在zkClient 执行 quit命令
            zkClient.close();
        }
        System.out.println(Thread.currentThread().getName()+"\t 释放锁成功");
        System.out.println();
        System.out.println();
    }





}
