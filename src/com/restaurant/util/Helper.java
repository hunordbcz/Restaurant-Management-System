package com.restaurant.util;

import com.restaurant.bll.MenuItem;

import java.lang.reflect.Field;

public class Helper {
    private Helper(){

    }

    public static String getFieldName(Class<?> cls, int nrField) {
        Field[] names = cls.getDeclaredFields();
        for (int i = 0; i < names.length; i++) {
            if (i == nrField) {
                return names[i].getName();
            }
        }
        return null;
    }

    public static Class<?> getColumnClass(Class<?> cls, int nrField) {
        Field[] names = cls.getDeclaredFields();
        for (int i = 0; i < names.length; i++) {
            if (i == nrField) {
                return names[i].getType();
            }
        }
        return String.class;
    }
}
