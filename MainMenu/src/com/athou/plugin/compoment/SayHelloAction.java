package com.athou.plugin.compoment;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;

/**
 * the first method that creates Action with implements ApplicationComponent
 * Created by cai on 2017/3/13.
 */
public class SayHelloAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Application application = ApplicationManager.getApplication();
        MyComponent myComponent = application.getComponent(MyComponent.class);
        myComponent.sayHello();
    }
}
