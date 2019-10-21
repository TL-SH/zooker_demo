package com.tl.distributed;

/**
 * @author tanglei
 */
public class OrderService {
    OrderNumGenerateUtil orderNumGenerateUtil = new OrderNumGenerateUtil();

    //Lock lock = new ReentrantLock();
    ZkLock zkLock = new ZkDistributedLock();

    public void getOrderNumber(){
        zkLock.lock();
        try {
            System.out.println("订单编号 :    \t"+orderNumGenerateUtil.getOrderNumber());
        }finally {
            zkLock.unlock();
        }
    }


    public static void main(String[] args) {
        for (int i = 1; i <=20 ; i++) {
            new Thread(()->{
                new OrderService().getOrderNumber();
            },String.valueOf(i)).start();
        }
    }

}
