package org.inh3rit.zktools.utils;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: PC-Tony
 * @Date: 21:26 2019/9/29
 */
public class ZKUtils {

    public static ZooKeeper getZK(String connectString) throws Exception {
        return getZK(connectString, 3000);
    }

    public static ZooKeeper getZK(String connectString, int sessionTimeout) throws Exception {
        return getZK(connectString, sessionTimeout, null);
    }

    public static ZooKeeper getZK(String connectString, int sessionTimeout, Watcher watcher) throws Exception {
        try {
            return new ZooKeeper(connectString, sessionTimeout, watcher);
        } catch (IOException e) {
            throw new Exception("获取zookeeper客户端失败,请检查连接信息!");
        }
    }

    public static List<String> getChildren(ZooKeeper zk, String path) throws Exception {
        try {
            return zk.getChildren(path, false);
        } catch (KeeperException |InterruptedException e) {
            throw new Exception("获取子节点目录失败!");
        }
    }


}
