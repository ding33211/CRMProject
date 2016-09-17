package com.soubu.crmproject.utils;

import com.soubu.crmproject.model.EmployeeParams;

import java.util.Comparator;

/**
 * Created by lakers on 16/9/16.
 */
public class PinyinComparator implements Comparator<EmployeeParams> {

    public int compare(EmployeeParams o1, EmployeeParams o2) {
        String o1Letter = CharacterParser.getInstance().getSelling(o1.getName());
        String o2Letter = CharacterParser.getInstance().getSelling(o2.getName());
        return o1Letter.compareTo(o2Letter);
    }
}
