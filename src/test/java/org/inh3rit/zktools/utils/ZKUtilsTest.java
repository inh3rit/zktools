package org.inh3rit.zktools.utils;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.List;
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
        Stat stat = zk.exists("/dir3", false);
        System.out.println(stat);
    }
}
