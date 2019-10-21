package com.tl.distributed;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * @author tanglei
 */
public class ZkDistributedLock extends ZkAbstractTemplateLock {

    @Override
    public boolean tryLock() {
        try {
            zkClient.createEphemeral(path);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void waitLock() {
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                if (countDownLatch!=null){
                    countDownLatch.countDown();
                }
            }
        };
        zkClient.subscribeDataChanges(path,iZkDataListener);
        if(zkClient.exists(path)){

            //不能干任何事情,必须等待path也即临时节点被删除后才能继续向下运行
            //让一些线程阻塞直到另一些线程完成一系列操作后才被唤醒。倒计时锁
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        zkClient.unsubscribeDataChanges(path,iZkDataListener);
    }
}
