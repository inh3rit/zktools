package org.inh3rit.zktools;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import org.inh3rit.zktools.views.MainUI;
import org.inh3rit.zktools.views.SplashView;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setMinHeight(820);
        stage.setMinWidth(1120);
        super.start(stage);
        stage.setTitle("zktools");
    }

    public static void main(String[] args) {
        launch(Application.class, MainUI.class, new SplashView(), args);
    }

    // 重启
    public void relaunch() {
        Platform.runLater(() -> {
            // 关闭窗口
            getStage().close();
            try {
                this.stop();
                this.init();
                this.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
