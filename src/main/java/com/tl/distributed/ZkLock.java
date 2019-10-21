package com.tl.distributed;

/**
 * @author tanglei
 */
public interface ZkLock {

    public void lock();

    public void unlock();

}
