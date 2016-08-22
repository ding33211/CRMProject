package com.soubu.crmproject.model;

import android.text.InputType;

/**
 * Created by dingsigang on 16-8-22.
 */
public class AddItem {
    int titleRes;
    //默认输入type
    int editTextType = InputType.TYPE_CLASS_TEXT;
    int itemType;

    String content;

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
