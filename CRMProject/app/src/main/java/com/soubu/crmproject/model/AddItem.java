package com.soubu.crmproject.model;

import android.text.InputType;

import java.util.Date;

/**
 * Created by dingsigang on 16-8-22.
 */
public class AddItem {
    int titleRes;
    //默认输入type
    int editTextType = InputType.TYPE_CLASS_TEXT;
    int itemType;
    String content;
    Date date;
    int arrayRes;
    int webArrayRes;

    public int getArrayRes() {
        return arrayRes;
    }

    public void setArrayRes(int arrayRes) {
        this.arrayRes = arrayRes;
    }

    public int getWebArrayRes() {
        return webArrayRes;
    }

    public void setWebArrayRes(int webArrayRes) {
        this.webArrayRes = webArrayRes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public int getEditTextType() {
        return editTextType;
    }

    public void setEditTextType(int editTextType) {
        this.editTextType = editTextType;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
