package org.inh3rit.zktools.controller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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

    public void initialize() {
        propPane.setPrefHeight(mainPane.getPrefHeight() / 10);
        mainPane.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            mainPane.setPrefHeight(newValue.intValue() / 10);
        });
    }


}
