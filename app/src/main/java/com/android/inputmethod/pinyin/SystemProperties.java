package com.android.inputmethod.pinyin;

import android.annotation.SuppressLint;

import java.lang.reflect.Method;

class SystemProperties {
    private static volatile Method set = null;
    private static volatile Method get = null;

    public static void set(String prop, String value) {
        try {
            if (null == set) {
                synchronized (SystemProperties.class) {
                    if (null == set) {
                        @SuppressLint("PrivateApi")
                        Class<?> cls = Class.forName("android.os.SystemProperties");
                        set = cls.getDeclaredMethod("set", String.class, String.class);
                    }
                }
            }
            set.invoke(null, prop, value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static String get(String prop) {
        return get(prop, "");
    }

    public static String get(String prop, String defaultvalue) {
        String value = defaultvalue;
        try {
            if (null == get) {
                synchronized (SystemProperties.class) {
                    if (null == get) {
                        @SuppressLint("PrivateApi")
                        Class<?> cls = Class.forName("android.os.SystemProperties");
                        get = cls.getDeclaredMethod("get", String.class, String.class);
                    }
                }
            }
            value = (String) (get.invoke(null, new Object[]{prop, defaultvalue}));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return value;
    }
}
