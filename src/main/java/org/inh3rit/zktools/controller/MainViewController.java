package org.inh3rit.zktools.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.inh3rit.zktools.Application;
import org.inh3rit.zktools.client.ZKClient;
import org.inh3rit.zktools.utils.ZKUtils;
import org.inh3rit.zktools.views.AddNodeView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description:
 * @Author: ytxu3
 * @Date: 15:20 2019/6/24
 */
@FXMLController
public class MainViewController {

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

    @Autowired
    private AddNodeController addNodeController;

    private Image rootIconImg, leafIconImg;

    private ZooKeeper zk;

    private Map<String, List> dirMap;

    public static String newNodeParentValue;


    public void initialize() {
        rootIconImg = new Image(getClass().getResourceAsStream("/images/directory.png"));
        leafIconImg = new Image(getClass().getResourceAsStream("/images/file.png"));
    }

    @FXML
    private void handleConnect() throws Exception {
        String url = urlTxt.getCharacters().toString();
        if (!checkUrl(url)) {
            // TODO 提示
            return;
        }
        zk = ZKClient.getClient(url);

        initDirs();
    }

    public void refresh() throws Exception {
        initDirs();
    }

    private void initDirs() throws Exception {
        dirMap = ZKUtils.getAllChildren(zk, "/");

        Node rootIcon = new ImageView(rootIconImg);
        TreeItem<String> rootItem = new TreeItem<>(dirMap.keySet().toArray()[0].toString(), rootIcon);
        rootItem.setExpanded(true);
        addItems(rootItem, dirMap.get(rootItem.getValue()));
        rootTree.setRoot(rootItem);
    }

    public String getFullPath(TreeItem item) {
        String value = item.getValue().toString();
        TreeItem parent = item.getParent();
        if ("/".equals(value)) {
            return "/";
        } else if ("/".equals(parent.getValue().toString())) {
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


    @FXML
    private void handleDisconnect() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            // TODO 断开失败
        }
        rootTree.setRoot(null);
        dirMap.clear();
        clearValue();
    }

    private void clearValue() {
        nodeName.setText(null);
        nodeValue.setText(null);
        node_ctime.setText(null);
        node_dataLength.setText(null);
        node_mtime.setText(null);
        node_ephemeralOwner.setText(null);
        node_cZxid.setText(null);
        node_numChildren.setText(null);
        node_mZxid.setText(null);
        node_aversion.setText(null);
        node_pZxid.setText(null);
        node_cversion.setText(null);
        node_version.setText(null);
    }

    /**
     * 添加鼠标右击菜单事件:添加节点,删除节点
     */
    @FXML
    private void handleAddNode() {
        TreeItem selectedItem = (TreeItem) rootTree.getSelectionModel().getSelectedItem();
        newNodeParentValue = selectedItem.getValue().toString();
        Stage addNodeStage = addNodeController.getStage();
        if (addNodeStage == null) {
            Application.showView(AddNodeView.class, Modality.APPLICATION_MODAL);
        } else {
            addNodeController.getParentNodeName().setText(newNodeParentValue);
            addNodeController.getNewNodeName().setText("");
            addNodeController.getNewNodeContent().setText("");
            addNodeStage.showAndWait();
        }

    }

    /**
     * 添加鼠标左键单击事件,查看节点信息
     *
     * @param event
     */
    @FXML
    private void handleGetNodeInfo(MouseEvent event) {
        if (1 < event.getClickCount())
            return;
        TreeItem selectedItem = (TreeItem) rootTree.getSelectionModel().getSelectedItem();
        if (selectedItem == null)
            return;
        String fullPath = getFullPath(selectedItem);
        nodeName.setText(selectedItem.getValue().toString());

        if ("/".equals(fullPath))
            return;

        try {
            Optional<String> value = Optional.of(ZKUtils.getData(zk, fullPath));
            nodeValue.setText(value.orElse(""));
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
    }

    public TextField getUrlTxt() {
        return urlTxt;
    }

    public TreeView getRootTree() {
        return rootTree;
    }

    @FXML
    private void handleDeleteNode(ActionEvent event) throws Exception {
        ZooKeeper zooKeeper = ZKClient.getClient(urlTxt.getText());
        zooKeeper.delete(getFullPath((TreeItem) rootTree.getSelectionModel().getSelectedItem()), 0);
        refresh();
    }
}
