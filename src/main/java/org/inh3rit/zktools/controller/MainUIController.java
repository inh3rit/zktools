package org.inh3rit.zktools.controller;

import com.jfoenix.controls.JFXTabPane;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.inh3rit.zktools.Application;
import org.inh3rit.zktools.client.ZKClient;
import org.inh3rit.zktools.utils.DateUtils;
import org.inh3rit.zktools.utils.JSONUtils;
import org.inh3rit.zktools.utils.Utils;
import org.inh3rit.zktools.utils.ZKUtils;
import org.inh3rit.zktools.views.AddNode;
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
public class MainUIController {

    @FXML
    private TreeView rootTree;

    @FXML
    private TextField urlTxt;

    @FXML
    private TextField rootPathTxt;

    @FXML
    private TextArea nodeName;

    @FXML
    private TextArea nodeValue;

    @FXML
    private JFXTabPane contentPane;

    @FXML
    private CheckBox urlDecodeCheckBox, dubboCheckBox;

    @FXML
    private TextField node_ctime, node_dataLength, node_mtime, node_ephemeralOwner, node_cZxid,
            node_numChildren, node_mZxid, node_aversion, node_pZxid, node_cversion, node_version;

    @Autowired
    private AddNodeController addNodeController;

    private Image rootIconImg, leafIconImg;

    private ZooKeeper zk;

    private Map<String, List> dirMap;

    private String newNodeParentValue;

    private String rootPath;

    private String zkRootPath = "/";

    // nodeValue原值
    private String value;

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
        rootPath = StringUtils.isEmpty(rootPathTxt.getText()) ? "/" : rootPathTxt.getText();
        dirMap = ZKUtils.getAllChildren(zk, rootPath);

        Node rootIcon = new ImageView(rootIconImg);
        TreeItem<String> rootItem = new TreeItem<>(dirMap.keySet().toArray()[0].toString(), rootIcon);
        rootItem.setExpanded(true);
        addItems(rootItem, dirMap.get(rootItem.getValue()));
        rootTree.setRoot(rootItem);
    }

    public String getFullPath(TreeItem item) {
        String value = item.getValue().toString();
        TreeItem parent = item.getParent();
        if (rootPath.equals(value)) {
            return value;
        } else if (rootPath.equals(parent.getValue().toString())) {
            return rootPath.equals(zkRootPath) ? rootPath + value : rootPath + "/" + value;
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
            Application.showView(AddNode.class, Modality.APPLICATION_MODAL);
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

        if ("/".equals(fullPath))
            return;

        try {
            String nodeName = selectedItem.getValue().toString();
            String nodeValue = Optional.of(ZKUtils.getData(zk, fullPath)).orElse("");
            this.value = nodeValue;
            if (urlDecodeCheckBox.isSelected()) {
                nodeValue = Utils.decode(nodeValue);
            }
            if (dubboCheckBox.isSelected()) {
                nodeValue = JSONUtils.jsonFormat(nodeValue);
            }
            this.nodeName.setText(nodeName);
            this.nodeValue.setText(nodeValue);
            Stat stat = ZKUtils.getStat(zk, fullPath);
            node_ctime.setText(DateUtils.format(stat.getCtime() / 1000));
            node_dataLength.setText(String.valueOf(stat.getDataLength()));
            node_mtime.setText(DateUtils.format(stat.getCtime() / 1000));
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

    @FXML
    private void handleDeleteNode(ActionEvent event) throws Exception {
        ZooKeeper zooKeeper = ZKClient.getClient(urlTxt.getText());
        // set version to -1(latest version)
        zooKeeper.delete(getFullPath((TreeItem) rootTree.getSelectionModel().getSelectedItem()), -1);
        refresh();
    }

    @FXML
    private void handleUpdate(MouseEvent event) {
        TreeItem selectedItem = (TreeItem) rootTree.getSelectionModel().getSelectedItem();
        if (selectedItem == null)
            return;
        String fullPath = getFullPath(selectedItem);

        if ("/".equals(fullPath))
            return;

        try {
            String newValue = this.nodeValue.getText();
            zk.setData(fullPath, newValue.getBytes(), -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDubbo(MouseEvent event) {
        // 如果在节点值tab
        if (this.contentPane.getSelectionModel().getSelectedIndex() == 1) {
            if (dubboCheckBox.isSelected()) {
                this.nodeValue.setText(JSONUtils.jsonFormat(this.nodeValue.getText()));
            } else {
                this.nodeValue.setText(value);
            }
        }
    }

    @FXML
    private void handleUrlDecode(MouseEvent event) {
        // 如果在节点值tab
        if (this.contentPane.getSelectionModel().getSelectedIndex() == 1) {
            if (urlDecodeCheckBox.isSelected()) {
                this.nodeValue.setText(Utils.decode(this.nodeValue.getText()));
            } else {
                this.nodeValue.setText(value);
            }
        }
    }

    public TextField getUrlTxt() {
        return urlTxt;
    }

    public TreeView getRootTree() {
        return rootTree;
    }

    public String getNewNodeParentValue() {
        return newNodeParentValue;
    }
}
