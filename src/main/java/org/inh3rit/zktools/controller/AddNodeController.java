package org.inh3rit.zktools.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.inh3rit.zktools.Application;
import org.inh3rit.zktools.views.AddNodeView;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: ytxu3
 * @Date: 19:09 2019-10-05
 */
@FXMLController
public class AddNodeController {

    @Autowired
    private AddNodeView addNodeView;

    @FXML
    private GridPane addNodePane;

    @FXML
    private TextField parentNodeName, newNodeName;

    @FXML
    private TextArea newNodeContent;

    private Stage stage;

    public void initialize() {
        parentNodeName.setText(MainViewController.newNodeParentValue);
        Platform.runLater(() -> {
            stage = (Stage) addNodePane.getScene().getWindow();
        });
    }

    public Stage getStage() {
        return stage;
    }

    public TextField getParentNodeName() {
        return parentNodeName;
    }
}
