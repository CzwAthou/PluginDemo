package com.athou.plugin.compoment;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by cai on 2017/3/13.
 */
public class MyComponent implements ApplicationComponent {

    public MyComponent() {
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "com.athou.plugin.compoment.MyComponent";
    }

    public void sayHello() {
        Messages.showMessageDialog("Hello World!", "Test", Messages.getInformationIcon());
    }
}
