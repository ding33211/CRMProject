/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.soubu.crmproject.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.BaseActivity;
import com.soubu.crmproject.base.greendao.Contact;
import com.soubu.crmproject.base.greendao.ContactDao;
import com.soubu.crmproject.base.greendao.DBHelper;
import com.soubu.crmproject.server.RetrofitRequest;

import java.util.ArrayList;
import java.util.List;

import static com.soubu.crmproject.CrmApplication.getContext;

public class ShowWidgetUtil {

    public static Context sContext;


    private ShowWidgetUtil() {
    }


    public static void register(Context context) {
        sContext = context.getApplicationContext();
    }


    private static void check() {
        if (sContext == null) {
            throw new NullPointerException(
                    "Must initial call ShowWidgetUtil.register(Context context) in your " +
                            "<? " +
                            "extends Application class>");
        }
    }


    public static void showShort(int resId) {
        check();
        Toast.makeText(sContext, resId, Toast.LENGTH_SHORT).show();
    }


    public static void showShort(String message) {
        check();
        Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(int resId) {
        check();
        Toast.makeText(sContext, resId, Toast.LENGTH_LONG).show();
    }


    public static void showLong(String message) {
        check();
        Toast.makeText(sContext, message, Toast.LENGTH_LONG).show();
    }


    public static void showLongX2(String message) {
        showLong(message);
        showLong(message);
    }


    public static void showLongX2(int resId) {
        showLong(resId);
        showLong(resId);
    }


    public static void showLongX3(int resId) {
        showLong(resId);
        showLong(resId);
        showLong(resId);
    }


    public static void showLongX3(String message) {
        showLong(message);
        showLong(message);
        showLong(message);
    }


    public static void showMultiItemDialog(Activity activity, int titleRes, int arrayRes, boolean multiChoice, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(arrayRes, listener);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


//    public static void showSingleChoiceDialog(Activity activity, int titleRes, String[] items, DialogInterface.OnClickListener singleListener,
//                                              DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener){
//        AlertDialog dialog = new AlertDialog.Builder(activity).setTitle(titleRes)
//                .setSingleChoiceItems(items, -1, singleListener).setPositiveButton(R.string.confirm, confirmListener)
//                .setNegativeButton(R.string.cancel, cancelListener).create();
//        dialog.show();
//    }

    public static void showCustomerPhoneDialog(final BaseActivity activity, String customerId){
        ContactDao contactDao = DBHelper.getInstance(getContext()).getContactDao();
        List<Contact> list = contactDao.queryBuilder().where(ContactDao.Properties.Customer.eq(customerId))
                .orderDesc(ContactDao.Properties.TouchedAt).list();
        final List<DialogItem> phoneList = new ArrayList<>();
        for (Contact contact : list){
            if(!TextUtils.isEmpty(contact.getMobile())){
                DialogItem item = new DialogItem();
                item.name = contact.getName();
                item.value = contact.getMobile();
                item.id = contact.getContact_id();
                phoneList.add(item);
            }
            if(!TextUtils.isEmpty(contact.getPhone())){
                DialogItem item = new DialogItem();
                item.name = contact.getName();
                item.value = contact.getPhone();
                item.id = contact.getContact_id();
                phoneList.add(item);
            }
        }
        if (phoneList.size() > 1) {
            final String[] items = new String[phoneList.size()];
            for (int i = 0; i < phoneList.size(); i++) {
                items[i] = (TextUtils.isEmpty(phoneList.get(i).name) ? "" : phoneList.get(i).name + " :  ") + phoneList.get(i).value;
            }
            AlertDialog dialog = new AlertDialog.Builder(activity).setTitle(R.string.please_choose_phone)
                    .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mIndex = which;
                        }
                    }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(mIndex == -1){
                                ShowWidgetUtil.showShort(R.string.please_choose_phone);
                                return;
                            }
                            if (!TextUtils.isEmpty(phoneList.get(0).id)) {
                                RetrofitRequest.getInstance().touchContact(phoneList.get(mIndex).id);
                            }
                            activity.callSomeOne(phoneList.get(mIndex).value);
//                                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + mPhoneList.get(mIndex).value));
//                                        startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setCancelable(false).create();
            dialog.show();
        } else if (phoneList.size() == 1) {
            if (!TextUtils.isEmpty(phoneList.get(0).id)) {
                RetrofitRequest.getInstance().touchContact(phoneList.get(0).id);
            }
            activity.callSomeOne(phoneList.get(0).value);
//                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + mPhoneList.get(0).value));
//                        startActivity(intent);
        } else {
            ShowWidgetUtil.showLong(R.string.no_contact);
        }
    }

    static int mIndex = -1;

    static class DialogItem {
        String name;
        String value;
        String id;
    }
}
