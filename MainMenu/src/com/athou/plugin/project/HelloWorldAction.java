package com.athou.plugin.project;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.TextRange;

/**
 * the second method that creates Action with extends AnAction
 * Created by cai on 2017/3/13.
 */
public class HelloWorldAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        String userName = askForName(project);
        sayHello(project, userName);
    }

    private String askForName(Project project) {
        return Messages.showInputDialog(project,
                "What is your name?", "Input Your Name",
                Messages.getQuestionIcon(), "abcdefg", new InputValidator() {
                    @Override
                    public boolean checkInput(String s) {
                        return s.length() > 0;
                    }

                    @Override
                    public boolean canClose(String s) {
                        return  s.length() > 0;
                    }
                }, new TextRange(0 ,2));
    }

    private void sayHello(Project project, String userName) {
        Messages.showMessageDialog(project,
                String.format("Hello, %s!\n Welcome to PubEditor.", userName), "Information",
                Messages.getInformationIcon());
    }
}
