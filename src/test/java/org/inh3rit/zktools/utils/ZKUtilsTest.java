package org.inh3rit.zktools.utils;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @Description:
 * @Author: PC-Tony
 * @Date: 21:37 2019/9/29
 */
public class ZKUtilsTest {

    @Test
    public void getZK() throws Exception {
        ZooKeeper zk = ZKUtils.getZK("47.106.143.175:2181");
//        Stat stat = zk.exists("/dir3", false);
//        System.out.println(stat);
        List<String> children = zk.getChildren("/", false);
        System.out.println(children);
    }

    @Test
    public void getAllChildren() throws Exception {
        ZooKeeper zk = ZKUtils.getZK("47.106.143.175:2181");
        Map<String, List> allChildren = ZKUtils.getAllChildren(zk, "/");
        System.out.println(JSON.toJSONString(allChildren));
    }

    @Test
    public void testSplit() {
        Optional<String> first = Arrays.stream("d/r/s/a/x".split("/")).findAny();
        System.out.println(first.get());

    }
}
