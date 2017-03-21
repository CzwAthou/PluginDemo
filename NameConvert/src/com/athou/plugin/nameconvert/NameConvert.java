package com.athou.plugin.nameconvert;

import com.athou.plugin.nameconvert.dialog.SelectDialog;
import com.athou.plugin.nameconvert.dialog.SelectTextCallBack;
import com.athou.plugin.nameconvert.net.HttpRequest;
import com.athou.plugin.nameconvert.net.TranslateBean;
import com.athou.plugin.nameconvert.utils.StringTypeUtil;
import com.google.gson.Gson;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.http.util.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by athou on 2017/3/14.
 */
public class NameConvert extends AnAction {

    private Editor mEditor;
    private Project mProject;
    private String mUrl = "http://fanyi.youdao.com/openapi.do";

    @Override
    public void actionPerformed(AnActionEvent e) {
        mEditor = e.getData(PlatformDataKeys.EDITOR);
        mProject = e.getData(PlatformDataKeys.PROJECT);
        String selectText = mEditor.getSelectionModel().getSelectedText();
        if (!TextUtils.isEmpty(selectText)) {
            int type = StringTypeUtil.getStringType(selectText);
            switch (type) {
                case StringTypeUtil.ONLY_HAS_CHINESE:
                    requestTranslate(selectText);
                    break;
                case StringTypeUtil.ONLY_HAS_ENGLISH:
                    showChooseDialog(selectText);
                    break;
                case StringTypeUtil.BOTH_CH_ENG:
                    Messages.showMessageDialog("不支持中英混合", "Information", Messages.getInformationIcon());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 中文请求翻译
     *
     * @param text
     */
    private void requestTranslate(String text) {
        String params = null;
        try {
            params = "keyfrom=testtranlate1rrrr&key=1257547986&type=data&doctype=json&version=1.1&only=translate&q=" + URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = HttpRequest.sendGet(mUrl, params);
        System.out.println("http-result:" + result);
        if (!TextUtils.isEmpty(result)) {
            TranslateBean mRes = new Gson().fromJson(result, TranslateBean.class);
            if (mRes != null) {
                if (mRes.getErrorCode() == 0) {
                    showChooseDialog(mRes.getTranslation().get(0));
                } else {
                    showErrorMsg(mRes.getErrorCode());
                }
            } else {
                //Messages.showMessageDialog("解析错误", "Warnning", Messages.getWarningIcon());
            }
        } else {
            //Messages.showMessageDialog("联网失败", "Error", Messages.getErrorIcon());
        }
    }

    private void changeSelectText(String text) {
        Document document = mEditor.getDocument();
        SelectionModel selectionModel = mEditor.getSelectionModel();

        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                document.replaceString(start, end, text);
            }
        };
        WriteCommandAction.runWriteCommandAction(mProject, runnable);
        selectionModel.removeSelection();
    }

    private void showChooseDialog(String selectText) {
        VisualPosition startPos = mEditor.getSelectionModel().getSelectionStartPosition();
        VisualPosition endPos = mEditor.getSelectionModel().getSelectionEndPosition();
        SelectDialog dialog = new SelectDialog(selectText, startPos, endPos, new SelectTextCallBack() {
            @Override
            public void onSelectText(String text) {
                changeSelectText(text);
            }
        });
        dialog.setVisible(true);
    }

    private void showErrorMsg(int errorcode) {
        String msg;
        switch (errorcode) {
            case 20:
                msg = "要翻译的文本过长";
                break;
            case 30:
                msg = "无法进行有效的翻译";
                break;
            case 40:
                msg = "不支持的语言类型";
                break;
            case 50:
                msg = "无效的key";
                break;
            case 60:
                msg = "无词典结果";
                break;
            default:
                msg = "未知错误";
                break;
        }
        Messages.showMessageDialog(msg, "Information", Messages.getInformationIcon());
    }
}
