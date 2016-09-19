package com.soubu.crmproject.server;

import com.soubu.crmproject.R;
import com.soubu.crmproject.utils.ShowWidgetUtil;

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
                break;
            case HttpURLConnection.HTTP_FORBIDDEN:
                ShowWidgetUtil.showLong(R.string.error_403_message);
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
