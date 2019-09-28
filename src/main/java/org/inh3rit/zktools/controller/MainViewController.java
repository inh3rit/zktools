package org.inh3rit.zktools.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField urlTxt;

    public void initialize() {
    }

    @FXML
    private void handleConnect() {
        String url = urlTxt.getCharacters().toString();
        if (!checkUrl(url)) {
            // TODO 提示
            return;
        }




        Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("/pics/directory.png")));
        TreeItem<String> rootItem = new TreeItem<>("Zookeeper", rootIcon);
        rootItem.setExpanded(true);
        TreeItem<String> item1 = new TreeItem<>("Message1");
        TreeItem<String> item2 = new TreeItem<>("Message1");
        item1.getChildren().add(item2);
        rootItem.getChildren().add(item1);
        rootTree.setRoot(rootItem);
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
