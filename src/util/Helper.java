package util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    public static Class<?> getFieldClass(Class<?> cls, int nrField) {
        Field[] names = cls.getDeclaredFields();
        for (int i = 0; i < names.length; i++) {
            if (i == nrField) {
                return names[i].getType();
            }
        }
        return String.class;
    }

    public static Object getFieldValue(Class<?> type, Object object,  int nrField){
        Object value = "~?~";
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(getFieldName(type, nrField), type);
            Method method = propertyDescriptor.getReadMethod();
            value = method.invoke(object);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void setFieldValue(Class<?> type, Object obj, Object aValue, int nrField) {
        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(getFieldName(type, nrField), type);
            Method method = propertyDescriptor.getWriteMethod();
            method.invoke(obj, aValue);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
