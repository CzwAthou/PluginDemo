package com.athou.plugin.translate.net;

import java.util.ArrayList;

/**
 * Created by athou on 2017/3/14.
 */
public class TranslateBean {
    private String query;
    private int errorCode;
    private ArrayList<String> translation;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ArrayList<String> getTranslation() {
        return translation;
    }

    public void setTranslation(ArrayList<String> translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "TranslateBean{" +
                "query='" + query + '\'' +
                ", errorCode=" + errorCode +
                ", translation=" + translation +
                '}';
    }
}
