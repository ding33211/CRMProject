package com.soubu.crmproject.utils;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.HttpUrl;

/**
 * 用于各种转换的工具
 * Created by dingsigang on 16-8-2.
 */
public class ConvertUtil {
    /**
     * sp转px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * MD5加密
     */
    public static String encryptMD5(String data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return new BigInteger(md5.digest(data.getBytes())).toString(16);
    }

    /**
     * SHA加密
     */
    public static String encryptSHA(String data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        return new BigInteger(sha.digest(data.getBytes())).toString(32);
    }


    public static String dateToYYYY_MM_DD(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String dateToYYYY_MM_DD_HH_mm(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return formatter.format(date);
    }

    public static String dateToYYYY_MM_DD_EEEE(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd  EEEE");
        return formatter.format(date);
    }

    public static String dateToHH_mm(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(date);
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des.toUpperCase();
    }


    public static String hmacsha256(HttpUrl url, String macKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = macKey.getBytes("UTF-8");
            String macData = "";
            if (url != null && url.queryParameterNames().size() > 1) {
                List<String> list = new ArrayList<>(url.queryParameterNames());
                Collections.sort(list);
                for (String a : list) {
                    macData += a + "=" + url.queryParameter(a) + "&";
                }
                macData = macData.substring(0, macData.length() - 1);
            } else {
                macData = url.encodedQuery() == null ? "" : url.encodedQuery();
            }
            byte[] dataBytes = macData.getBytes("UTF-8");
            Log.e("xxxxxxxxxxxxxxx", "data   :   " + macData + "      key   :   " + macKey);
            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);
            byte[] doFinal = mac.doFinal(dataBytes);
            Log.e("xxxxxxxxxxxxxxx", "result   :   " + bytes2Hex(doFinal));

            return bytes2Hex(doFinal);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String hmacsha256(Map<String, String> map, String macKey) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = macKey.getBytes("UTF-8");
            String macData = "";
            if (map != null && map.size() > 0) {
                List<String> list = new ArrayList<>(map.keySet());
                Collections.sort(list);
                for (String a : list) {
                    macData += a + "=" + map.get(a) + "&";
                }
                macData = macData.substring(0, macData.length() - 1);
            }
            byte[] dataBytes = macData.getBytes("UTF-8");
            Log.e("xxxxxxxxxxxxxxx", "data   :   " + macData + "      key   :   " + macKey);
            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);
            byte[] doFinal = mac.doFinal(dataBytes);
//            byte[] hexB = new Hex().encode(doFinal);
            Log.e("xxxxxxxxxxxxxxx", "result   :   " + bytes2Hex(doFinal));

            return bytes2Hex(doFinal);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
