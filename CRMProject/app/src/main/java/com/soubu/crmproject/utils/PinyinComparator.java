package com.soubu.crmproject.utils;

import com.soubu.crmproject.base.greendao.Staff;

import java.util.Comparator;

/**
 * Created by lakers on 16/9/16.
 */
public class PinyinComparator implements Comparator<Staff> {

    public int compare(Staff o1, Staff o2) {
        String o1Letter = CharacterParser.getInstance().getSelling(o1.getUsername());
        String o2Letter = CharacterParser.getInstance().getSelling(o2.getUsername());
        return o1Letter.compareTo(o2Letter);
    }
}
