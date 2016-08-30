package com.soubu.crmproject.utils;

import android.content.Context;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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


    public static String dateToYYYY_MM_DD(Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }




    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     *
     * @param strSrc 要加密的字符串
     * @param encName  加密类型
     * @return
     */
    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
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


    public static String hmacsha256(HttpUrl url, String macKey){
        try{
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = macKey.getBytes("UTF-8");
            String macData = "";
            if(url.queryParameterNames().size() > 1){
                List<String> list = new ArrayList<>(url.queryParameterNames());
                Collections.sort(list);
                for(String a : list){
                    macData += a + "=" + url.queryParameter(a) + "&";
                }
                macData = macData.substring(0, macData.length() - 1);
            } else {
                macData = url.encodedQuery();
            }
            byte[] dataBytes = macData.getBytes("UTF-8");
            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");

            mac.init(secret);
            byte[] doFinal = mac.doFinal(dataBytes);
//            byte[] hexB = new Hex().encode(doFinal);
            return bytes2Hex(doFinal);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
