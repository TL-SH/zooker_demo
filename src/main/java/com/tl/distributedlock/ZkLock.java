package com.tl.distributedlock;

/**
 * @author tanglei
 */
public interface ZkLock {
    public void lock();
    public void unlock();
}
