package org.inh3rit.zktools.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.inh3rit.zktools.client.ZKClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: ytxu3
 * @Date: 19:09 2019-10-05
 */
@FXMLController
public class AddNodeController {

    @FXML
    private AnchorPane addNodePane;

    @FXML
    private TextField parentNodeName, newNodeName;

    @FXML
    private TextArea newNodeContent;

    private Stage stage;

    @Autowired
    private MainUIController mainViewController;

    public void initialize() {
        parentNodeName.setText(mainViewController.getNewNodeParentValue());
        Platform.runLater(() -> stage = (Stage) addNodePane.getScene().getWindow());
    }

    @FXML
    private void handleAddNode(MouseEvent event) throws Exception {
        ZooKeeper zooKeeper = ZKClient.getClient(mainViewController.getUrlTxt().getText());
        String parentPath = mainViewController.getFullPath((TreeItem) mainViewController.getRootTree().getSelectionModel().getSelectedItem());
        String path = parentPath.equals("/") ? "/" + newNodeName.getText() : parentPath + "/" + newNodeName.getText();
        byte[] content = newNodeContent.getText().getBytes();
        zooKeeper.create(path, content, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        mainViewController.refresh();
        stage.close();
    }

    @FXML
    private void handleCancel(MouseEvent event) {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public TextField getParentNodeName() {
        return parentNodeName;
    }

    public TextField getNewNodeName() {
        return newNodeName;
    }

    public TextArea getNewNodeContent() {
        return newNodeContent;
    }
}
