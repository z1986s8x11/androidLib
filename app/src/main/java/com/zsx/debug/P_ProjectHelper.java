package com.zsx.debug;

import android.content.Context;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import dalvik.system.DexFile;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/7 18:08
 */
public class P_ProjectHelper {
    public Map<String, P_ProjectHelper> map;
    public static Set<String> classNameSet = new HashSet<>();
    public String name;
    public String path;
    public static P_ProjectHelper scanData;

    private P_ProjectHelper() {
    }

    public static P_ProjectHelper getInstance() {
        if (scanData == null) {
            scanData = new P_ProjectHelper();
        }
        return scanData;
    }


    public P_ProjectHelper _init(Context context) {
        initClassesFromPackage(context);
        return scanData;
    }


    public void add(String packName) {
        P_ProjectHelper rootItem = this;
        String[] keys = packName.split("\\.");
        String path = "";
        for (int i = 0; i < keys.length; i++) {
            P_ProjectHelper item = new P_ProjectHelper();
            path += "." + keys[i];
            item.name = keys[i];
            if (i == keys.length - 1) {
                item.path = packName;
            }
            if (rootItem.map == null) {
                rootItem.map = new HashMap<>();
            }
            if (rootItem.map.containsKey(keys[i])) {
                rootItem = rootItem.map.get(keys[i]);
                continue;
            }
            rootItem.map.put(keys[i], item);
            item.path = path.substring(1, path.length());
            rootItem = item;
        }
    }

    private P_ProjectHelper initClassesFromPackage(Context context) {
        if (map != null) {
            map.clear();
        }
        if (!classNameSet.isEmpty()) {
            Iterator<String> it = classNameSet.iterator();
            while (it.hasNext()) {
                scanData.add(it.next());
            }
            return scanData;
        }
        String packageName = context.getPackageName();
        try {
            DexFile df = new DexFile(context.getPackageCodePath());
            Enumeration<String> entries = df.entries();
            while (entries.hasMoreElements()) {
                String className = entries.nextElement();
                if (className.contains(packageName)) {
                    if (!className.contains("$")) {
                        if (className.endsWith("_Activity") || className.endsWith("_Fragment")) {
                            scanData.add(className);
                            classNameSet.add(className);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scanData;
    }

    public P_ProjectHelper get(String packName) {
        String[] keys = packName.trim().split("\\.");
        Map<String, P_ProjectHelper> maps = this.map;
        for (int i = 0; i < keys.length; i++) {
            if (i == keys.length - 1) {
                return maps.get(keys[i]);
            }
            if (maps != null) {
                maps = maps.get(keys[i]).map;
            }
        }
        return null;
    }

    public boolean isDir() {
        return map != null;
    }

    public P_ProjectHelper[] list() {
        if (map == null) {
            return new P_ProjectHelper[]{};
        }
        Collection<P_ProjectHelper> c = map.values();
        return c.toArray(new P_ProjectHelper[]{});
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("name:" + name + "\tpath:" + path + "\tisDir:"
                + String.valueOf(isDir()));
        sb.append("\n");
        if (map != null) {
            for (String key : map.keySet()) {
                sb.append(map.get(key));
            }
        }
        return sb.toString();
    }
}
