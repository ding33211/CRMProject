package com.soubu.crmproject.delegate;

import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.model.FollowParams;
import com.soubu.crmproject.utils.SearchUtil;
import com.soubu.crmproject.utils.ShowWidgetUtil;
import com.soubu.crmproject.view.activity.AddFollowActivity;

/**
 * Created by dingsigang on 16-8-30.
 */
public class AddFollowActivityDelegate extends AppDelegate {
    private int mType;
    private int mFrom;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_add_follow;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        if(mType == AddFollowActivity.TYPE_RECORD){
            ((TextView)get(R.id.tv_expected_contract_label)).setText(R.string.related_contact);
        } else {
            get(R.id.rl_state).setVisibility(View.GONE);
            ((TextView)get(R.id.tv_follow_time_label)).setText(R.string.expected_follow_time);
            get(R.id.ll_remind).setVisibility(View.VISIBLE);
        }
    }

    public void giveTextViewString(int textViewRes, String text){
        ((TextView)get(textViewRes)).setText(text);
    }


    public void pressRemind(){
        get(R.id.sc_remind).performClick();
    }

    public void pressTransfer(){
        get(R.id.sc_transfer).performClick();
    }

    public void setTypeAndFrom(int type){
        mType = type;
    }

    public boolean isTransferChecked(){
        return ((SwitchCompat)get(R.id.sc_transfer)).isChecked();
    }

    public boolean isRemind(){
        return ((SwitchCompat)get(R.id.sc_remind)).isChecked();
    }


    public boolean verify(FollowParams followParams){
        String title = ((EditText)get(R.id.et_title)).getText().toString();
        if(TextUtils.isEmpty(title)){
            ShowWidgetUtil.showLong(R.string.title_empty_error);
            return false;
        } else {
            followParams.setTitle(title);
        }
        String content = ((EditText)get(R.id.et_content)).getText().toString();
        if(TextUtils.isEmpty(content)){
            ShowWidgetUtil.showLong(R.string.content_empty_error);
            return false;
        } else {
            followParams.setContent(content);
        }
        if(TextUtils.isEmpty(((TextView)get(R.id.tv_state)).getText())){
            ShowWidgetUtil.showLong(R.string.title_empty_error);
            return false;
        }
        if(TextUtils.equals(((TextView)get(R.id.tv_follow_method)).getText(), getActivity().getResources().getString(R.string.required_choose))){
            ShowWidgetUtil.showLong(R.string.follow_method_empty_error);
            return false;
        }
        if(TextUtils.equals(((TextView)get(R.id.tv_follow_time)).getText(), getActivity().getResources().getString(R.string.required_choose))){
            ShowWidgetUtil.showLong(R.string.follow_time_empty_error);
            return false;
        }
        if(isRemind() && TextUtils.equals(((TextView)get(R.id.tv_remind_time)).getText(), getActivity().getResources().getString(R.string.required_choose))){
            ShowWidgetUtil.showLong(R.string.remind_time_empty_error);
            return false;
        }
        return true;
    }

    @Override
    public boolean ifNeedEventBus() {
        return true;
    }
}
