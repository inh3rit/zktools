package org.inh3rit.zktools.views;

import de.felixroske.jfxsupport.SplashScreen;
import javafx.scene.Parent;

/**
 * @Description:
 * @Author: ytxu3
 * @Date: 10:24 2019-10-03
 */
public class SplashView extends SplashScreen {

    /**
     * 指定启动画面
     * @return
     */
    @Override
    public String getImagePath() {
        return "/images/start.png";
    }

    @Override
    public Parent getParent() {
        return super.getParent();
    }
}
