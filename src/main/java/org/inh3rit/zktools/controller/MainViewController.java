package org.inh3rit.zktools.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

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

    public void initialize() {
    }

    @FXML
    private void handleConnect() {
        Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("/pics/directory.png")));
        TreeItem<String> rootItem = new TreeItem<>("Zookeeper", rootIcon);
        rootItem.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<>("Message" + i);
            rootItem.getChildren().add(item);
        }
        rootTree.setRoot(rootItem);
    }


}
