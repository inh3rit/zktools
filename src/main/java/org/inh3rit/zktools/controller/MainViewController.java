package org.inh3rit.zktools.controller;

import com.sun.javafx.scene.control.skin.LabeledText;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
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

    @FXML
    private TextArea nodeName;

    @FXML
    private TextArea nodeValue;

    @FXML
    private TextField node_ctime, node_dataLength, node_mtime, node_ephemeralOwner, node_cZxid,
            node_numChildren, node_mZxid, node_aversion, node_pZxid, node_cversion, node_version;

    private Image rootIconImg, leafIconImg;

    private ZooKeeper zk;

    public void initialize() {
        rootIconImg = new Image(getClass().getResourceAsStream("/pics/directory.png"));
        leafIconImg = new Image(getClass().getResourceAsStream("/pics/file.png"));
    }

    @FXML
    private void handleConnect() throws Exception {
        String url = urlTxt.getCharacters().toString();
        if (!checkUrl(url)) {
            // TODO 提示
            return;
        }
        zk = ZKUtils.getZK(url);

        initDirs(url);
    }

    private void initDirs(String url) throws Exception {
        Map<String, List> dirs = ZKUtils.getAllChildren(zk, "/");

        Node rootIcon = new ImageView(rootIconImg);
        TreeItem<String> rootItem = new TreeItem<>(dirs.keySet().toArray()[0].toString(), rootIcon);
        rootItem.setExpanded(true);
        addItems(rootItem, dirs.get(rootItem.getValue()));
        rootTree.setRoot(rootItem);
        rootTree.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println(1);
            if (1 < event.getClickCount())
                return;
            System.out.println(2);
            TreeItem selectedItem = (TreeItem) ((TreeView) event.getSource()).getSelectionModel().getSelectedItem();
            String fullPath = getFullPath(selectedItem);
            nodeName.setText(selectedItem.getValue().toString());
            try {
                nodeValue.setText(ZKUtils.getData(zk, fullPath));
                Stat stat = ZKUtils.getStat(zk, fullPath);
                node_ctime.setText(String.valueOf(stat.getCtime()));
                node_dataLength.setText(String.valueOf(stat.getDataLength()));
                node_mtime.setText(String.valueOf(stat.getMtime()));
                node_ephemeralOwner.setText(String.valueOf(stat.getEphemeralOwner()));
                node_cZxid.setText(String.valueOf(stat.getCzxid()));
                node_numChildren.setText(String.valueOf(stat.getNumChildren()));
                node_mZxid.setText(String.valueOf(stat.getMzxid()));
                node_aversion.setText(String.valueOf(stat.getAversion()));
                node_pZxid.setText(String.valueOf(stat.getPzxid()));
                node_cversion.setText(String.valueOf(stat.getCversion()));
                node_version.setText(String.valueOf(stat.getVersion()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private String getFullPath(TreeItem item) {
        String value = item.getValue().toString();
        TreeItem parent = item.getParent();
        if ("/".equals(parent.getValue().toString())) {
            return "/" + value;
        } else {
            return getFullPath(parent) + "/" + value;
        }
    }

    private void addItems(TreeItem treeItem, List list) {
        for (Object o : list) {
            Map<String, List> map = (Map<String, List>) o;
            String dir = map.keySet().toArray()[0].toString();
            String[] dirs = dir.split("/");
            String dirName = dirs[dirs.length - 1];
            List subList = map.get(dir);
            Image icon = 0 == subList.size() ? leafIconImg : rootIconImg;
            Node leafIcon = new ImageView(icon);
            TreeItem<String> item = new TreeItem<>(dirName, leafIcon);
            addItems(item, subList);
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
