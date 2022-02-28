package com.lhw.rpc_starter;

import com.lhw.rpc_starter.util.ZookeeperUtil;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

//@SpringBootTest
class RpcStarterApplicationTests {

    @Test
    void contextLoads() {

//        String nodePath = "/lhw/test/ahhaha";
//        String[] nodePaths = nodePath.split("/");
//        System.out.println(Arrays.toString(nodePaths));

        ZooKeeper zooKeeper = ZookeeperUtil.connect("127.0.0.1:2181",5000);
        ZookeeperUtil.createNode(zooKeeper,"/services/com.lhw.test/testName","123456789");
        System.out.println(ZookeeperUtil.queryData(zooKeeper, "/services/com.lhw.test/testName"));

    }

}
