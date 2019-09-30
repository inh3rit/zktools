package org.inh3rit.zktools.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.apache.zookeeper.ZooKeeper;
import org.inh3rit.zktools.utils.ZKUtils;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: ytxu3
 * @Date: 15:20 2019/6/24
 */
@FXMLController
public class MainViewController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private FlowPane propPane;

    @FXML
    private TreeView rootTree;

    @FXML
    private TextField urlTxt;

    public void initialize() {
    }

    @FXML
    private void handleConnect() throws Exception {
        String url = urlTxt.getCharacters().toString();
        if (!checkUrl(url)) {
            // TODO 提示
            return;
        }

        ZooKeeper zk = ZKUtils.getZK(url);
        Map<String, List> dirs = ZKUtils.getAllChildren(zk, "/");

        Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("/pics/directory.png")));
        TreeItem<String> rootItem = new TreeItem<>(dirs.keySet().toArray()[0].toString(), rootIcon);
        rootItem.setExpanded(true);
        addItems(rootItem, dirs.get(rootItem.getValue()));
        rootTree.setRoot(rootItem);
    }

    private void addItems(TreeItem treeItem, List list) {
        for (Object o : list) {
            Map<String, List> map = (Map<String, List>) o;
            String dir = map.keySet().toArray()[0].toString();
            String[] dirs = dir.split("/");
            String dirName = dirs[dirs.length - 1];
            TreeItem<String> item = new TreeItem<>(dirName);
            addItems(item, map.get(dir));
            treeItem.getChildren().add(item);
        }
    }

    private boolean checkUrl(String url) {
        if (!url.contains(":"))
            return false;
        String[] configs = url.split(":");
        if (configs.length != 2)
            return false;
        // TODO check ip and port

        return true;
    }


}
