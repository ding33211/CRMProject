package com.soubu.crmproject.server;

import android.content.Context;
import android.content.Intent;

import com.soubu.crmproject.CrmApplication;
import com.soubu.crmproject.R;
import com.soubu.crmproject.utils.ShowWidgetUtil;
import com.soubu.crmproject.utils.UserManager;
import com.soubu.crmproject.view.activity.LoginActivity;

import java.net.HttpURLConnection;

/**
 * Created by dingsigang on 16-9-18.
 */
public class ServerErrorUtil {
    public static void handleServerError(int errorCode) {
        switch (errorCode) {
            case HttpURLConnection.HTTP_BAD_REQUEST:
                ShowWidgetUtil.showLong(R.string.error_400_message);
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                ShowWidgetUtil.showLong(R.string.error_401_message);
//                Intent intent = new Intent();
//                intent.setComponent()
//                CrmApplication.getContext().startActivity();
                break;
            case HttpURLConnection.HTTP_FORBIDDEN:
                ShowWidgetUtil.showLong(R.string.error_403_message);
                Context nowContext = CrmApplication.getContext().getNowContext();
                CrmApplication.getContext().finishAllActivity();
                UserManager.clearUser();
                Intent intent = new Intent(nowContext, LoginActivity.class);
                nowContext.startActivity(intent);
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                ShowWidgetUtil.showLong(R.string.error_404_message);
                break;
            case 422:  //请求参数错误
                ShowWidgetUtil.showLong(R.string.error_422_message);
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                ShowWidgetUtil.showLong(R.string.error_500_message);
                break;
        }
        return;
    }
}
