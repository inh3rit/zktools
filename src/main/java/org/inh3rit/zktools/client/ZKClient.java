package org.inh3rit.zktools.client;

import org.apache.zookeeper.ZooKeeper;
import org.inh3rit.zktools.utils.ZKUtils;

/**
 * @Description:
 * @Author: PC-Tony
 * @Date: 21:48 2019/10/8
 */
public class ZKClient {
    private static ZooKeeper zooKeeper;
    private static String oldUrl;

    public static ZooKeeper getClient(String url) throws Exception {
        if (zooKeeper != null && zooKeeper.getState().isAlive() && oldUrl.equals(url)) {
            return zooKeeper;
        } else {
            oldUrl = url;
            zooKeeper = ZKUtils.getZK(url);
            return zooKeeper;
        }
    }
}
