package com.tl.distributedlock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * @author tanglei
 */
public class ZkDistributedLockl extends ZkAbstractTemplateLock {

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
                //如果临时节点删了的话就重新抢占资源
                if(countDownLatch!=null){
                    countDownLatch.countDown();
                }
            }
        };

        zkClient.subscribeDataChanges(path,iZkDataListener);

        if (zkClient.exists(path)){
            // 如果临时 节点 存在 就让他一直等下去
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
