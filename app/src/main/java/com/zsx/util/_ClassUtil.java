package com.zsx.util;

import android.content.Context;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

public class _ClassUtil {
    /**
     * 调用实例化对象 private 的方法
     *
     * @param instanceClass 实例化的对象
     * @param methodName    需要调用的对象的方法名
     * @param params        参数集
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object invoke(Object instanceClass, String methodName,
                                Object... params) throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Method m = null;
        Class<?>[] paramTypes = null;
        if (params == null || params.length == 0) {
            paramTypes = null;
        } else {
            paramTypes = new Class<?>[params.length];
        }
        if (paramTypes != null) {
            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                if (param instanceof Integer) {
                    paramTypes[i] = Integer.TYPE;
                } else if (param instanceof Boolean) {
                    paramTypes[i] = Boolean.TYPE;
                } else if (param instanceof Character) {
                    paramTypes[i] = Character.TYPE;
                } else if (param instanceof Byte) {
                    paramTypes[i] = Byte.TYPE;
                } else if (param instanceof Float) {
                    paramTypes[i] = Float.TYPE;
                } else if (param instanceof Long) {
                    paramTypes[i] = Long.TYPE;
                } else {
                    paramTypes[i] = params[i].getClass();
                }
            }
        }
        m = instanceClass.getClass().getDeclaredMethod(methodName,
                paramTypes);
        m.setAccessible(true);
        return m.invoke(instanceClass, params);
    }

    public static void set(Object instanceClass, String filedName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = instanceClass.getClass().getDeclaredField(filedName);
        field.setAccessible(true);
        field.set(instanceClass, value);
    }

    public static Object get(Object instanceClass, String filedName) throws NoSuchFieldException, IllegalAccessException {
        Field field = instanceClass.getClass().getDeclaredField(filedName);
        field.setAccessible(true);
        return field.get(instanceClass);
    }

    private static List<String> getClassesFromPackage(Context context, String packageName) {
        ArrayList<String> classes = new ArrayList<String>();
        try {
            DexFile df = new DexFile(context.getPackageCodePath());
            Enumeration<String> entries = df.entries();
            while (entries.hasMoreElements()) {
                String className = entries.nextElement();
                if (className.contains(packageName)) {
                    //过滤掉内部类
                    if (!className.contains("$")) {
                        classes.add(className);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static List<String> getClassesFromPackage(Context context) {
        return getClassesFromPackage(context, context.getPackageName());
    }
}
