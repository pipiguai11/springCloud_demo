package com.lhw.rpc_starter.register.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;

/**
 * @author ：linhw
 * @date ：22.2.25 16:34
 * @description：zookeeper服务观察者事件
 * @modified By：
 */
@Slf4j
public class ZookeeperWatcher implements Watcher {

    //异步锁
    private CountDownLatch countDownLatch;

    //标识信息
    private String msg;

    public ZookeeperWatcher(CountDownLatch countDownLatch, String msg){
        this.countDownLatch = countDownLatch;
        this.msg = msg;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info(msg + ": watcher监听事件：{" + watchedEvent + "}");
        countDownLatch.countDown();
    }
}
