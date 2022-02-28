package com.lhw.rpc_starter.util;

import com.lhw.rpc_starter.register.zookeeper.ZookeeperWatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @author ：linhw
 * @date ：22.2.25 16:45
 * @description：zookeeper工具类
 * @modified By：
 */
@Slf4j
public class ZookeeperUtil {

    /**
     * 建立zookeeper连接
     * @return
     */
    public static ZooKeeper connect(String addr, int timeout){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = null;
        try {
            log.info("准备建立zookeeper连接");
            zooKeeper = new ZooKeeper(addr, timeout, new ZookeeperWatcher(countDownLatch, "建立连接"));
            countDownLatch.await();
            log.info("zookeeper连接建立成功");
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return zooKeeper;
    }

    /**
     * 重新建立zookeeper连接
     * @return
     */
    public static ZooKeeper reConnect(String addr, int timeout){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = null;
        try {
            log.info("准备重新建立zookeeper连接");
            zooKeeper = new ZooKeeper(addr, timeout, new ZookeeperWatcher(countDownLatch, "建立连接"));
            countDownLatch.await();
            log.info("zookeeper连接重新建立成功");
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return zooKeeper;
    }

    /**
     * 节点注册
     * @param zooKeeper
     * @param nodePath
     * @param nodeData
     */
    public static void createNode(ZooKeeper zooKeeper, String nodePath, String nodeData){
        log.info("准备创建节点：{} ，数据为：{}" , nodePath, nodeData);
        try {
            //创建一个完全开放，且客户端断开连接之后不会删除的节点
            String result = zooKeeper.create(nodePath, nodeData.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            log.info("节点创建完毕， 结果为： {}" , result);
        }catch (KeeperException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void cascadeCreateNode(ZooKeeper zooKeeper, String nodePath, String nodeData){
        String[] nodes = nodePath.split("/");
        int index = 0;
        StringBuilder tempPath = new StringBuilder();
        while (++index < nodes.length){
            if (index == nodeData.length() - 1){
                tempPath.append(nodes[index]);
            }else {
                tempPath.append("/").append(nodes[index]);
            }

            if (Objects.isNull(queryStat(zooKeeper, tempPath.toString()))){
                createNode(zooKeeper, tempPath.toString(), nodeData);
            }
        }
    }

    /**
     * 查询节点结构状态信息
     * @param zooKeeper
     * @param nodePath
     * @return
     */
    public static Stat queryStat(ZooKeeper zooKeeper, String nodePath){
        log.info("准备查询【{}】的节点", nodePath);
        Stat stat = null;
        try {
            //查询给定路径节点的状态，同时可以设置一个监听器
            stat = zooKeeper.exists(nodePath, false);
            if (null != stat){
                log.info("节点查询成功，路径为：【{}】, 版本【{}】",nodePath, stat.getVersion());
            }
        }catch (KeeperException | InterruptedException e){
            e.printStackTrace();
        }
        return stat;
    }

    public static String queryData(ZooKeeper zooKeeper, String nodePath){
        String result = "";
        try {
            //查询指定路径节点的信息
            result = new String(zooKeeper.getData(nodePath, false, queryStat(zooKeeper,nodePath)));
            log.info("节点的数据为：【{}】", result);
        }catch (KeeperException | InterruptedException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改节点
     * @param zookeeper
     * @param nodePath
     * @param nodeData
     * @return
     */
    public static Stat update(ZooKeeper zookeeper,String nodePath,String nodeData){
        //修改节点前先查询该节点信息
        Stat stat = queryStat(zookeeper, nodePath);
        try {
            log.info("准备修改节点，path：{}，data：{}，原version：{}", nodePath, nodeData, stat.getVersion());
            Stat newStat = zookeeper.setData(nodePath, nodeData.getBytes(), stat.getVersion());
            //修改节点值有两种方法，上面是第一种，还有一种可以使用回调函数及参数传递，与上面方法名称相同。
            //zookeeper.setData(path, data, version, cb, ctx);
            log.info("完成修改节点，path：{}，data：{}，现version：{}", nodePath, nodeData, newStat.getVersion());
        }catch (KeeperException | InterruptedException e){
            e.printStackTrace();
        }
        return stat;
    }

    public static void delete(ZooKeeper zooKeeper,String nodePath){
        //删除节点前先查询该节点信息
        Stat stat = queryStat(zooKeeper, nodePath);
        try {
            log.info("准备删除节点，path：{}，原version：{}", nodePath, stat.getVersion());
            zooKeeper.delete(nodePath, stat.getVersion());
            //修改节点值有两种方法，上面是第一种，还有一种可以使用回调函数及参数传递，与上面方法名称相同。
            //zooKeeper.delete(path, version, cb, ctx);
            log.info("完成删除节点，path：{}", nodePath);
        }catch (KeeperException | InterruptedException e){
            e.printStackTrace();
        }
    }

}
