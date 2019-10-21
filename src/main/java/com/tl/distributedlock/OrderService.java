package com.tl.distributedlock;



/**
 * @author tanglei
 */
public class OrderService {

    OrderNumGenerateUtil orderNumGenerateUtil = new OrderNumGenerateUtil();
    ZkLock zkLock = new ZkDistributedLockl();
    public void getOrderNumber(){
        zkLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+" 订单编号: \t "+orderNumGenerateUtil.getOrderNumber());
        }finally {
            zkLock.unlock();
        }
    }

    public static void main(String[] args){

        OrderService orderService = new OrderService();

        for (int i = 1; i <=10 ; i++) {
            new Thread(()->{
                new OrderService().getOrderNumber();
            },String.valueOf(i)).start();
        }
    }



}
