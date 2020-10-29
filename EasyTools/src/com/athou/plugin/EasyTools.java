package com.athou.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.sun.java.swing.action.ActionManager;

/**
 * Created by cai on 2017/3/30.
 */
public class EasyTools extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        EasyToolsUI easyToolsUI = new EasyToolsUI();
        easyToolsUI.setVisible(true);
        ActionManager.getInstance().getAction()
    }
}
