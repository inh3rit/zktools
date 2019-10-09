package org.inh3rit.zktools.utils;

import com.alibaba.fastjson.JSONObject;
import javafx.scene.layout.HBox;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: PC-Tony
 * @Date: 21:26 2019/9/29
 */
public class ZKUtils {

    public static ZooKeeper getZK(String connectString) throws Exception {
        return getZK(connectString, 30000);
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
        } catch (KeeperException | InterruptedException e) {
            throw new Exception("获取子节点目录失败!");
        }
    }

    public static Stat getStat(ZooKeeper zk, String path) throws Exception {
        try {
            return zk.exists(path, false);
        } catch (KeeperException | InterruptedException e) {
            throw new Exception("获取节点属性失败!");
        }
    }

    public static String getData(ZooKeeper zk, String path) throws Exception {
        try {
            return new String(zk.getData(path, false, null));
        } catch (KeeperException | InterruptedException e) {
            throw new Exception("获取节点值失败!");
        }
    }

    public static Map<String, List> getAllChildren(ZooKeeper zk, String path) throws Exception {
        Map<String, List> childrenMap = new HashMap<>();
        List<String> children;
        try {
            children = zk.getChildren(path, false);
        } catch (KeeperException | InterruptedException e) {
            throw new Exception("获取子节点目录失败!");
        }
        if (0 == children.size()) {
            childrenMap.put(path, children);
            return childrenMap;
        } else {
            List list = new ArrayList();
            for (String child : children) {
                // 父节点是根目录会出现"//"的情况
                String childPath = "/".equals(path) ? path + child : path + "/" + child;
                list.add(getAllChildren(zk, childPath));
            }
            childrenMap.put(path, list);
            return childrenMap;
        }
    }


}
