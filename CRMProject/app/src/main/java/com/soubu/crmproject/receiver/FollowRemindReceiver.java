package com.soubu.crmproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.soubu.crmproject.model.Contants;
import com.soubu.crmproject.utils.ShowWidgetUtil;

/**
 * Created by dingsigang on 16-9-27.
 */
public class FollowRemindReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(Contants.EXTRA_REMIND_TITLE);
        String message = intent.getStringExtra(Contants.EXTRA_REMIND_MESSAGE);
        Log.e("xxxxxxxxxx" ,title + message);
        ShowWidgetUtil.showShort(title + message);
    }
}
