package com.zsx.tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/7 18:08
 */
public class P_ScanData {
    public Map<String, P_ScanData> map;
    public String name;
    public String path;

    public void add(String packName) {
        P_ScanData rootItem = this;
        String[] keys = packName.split("\\.");
        for (int i = 0; i < keys.length; i++) {
            P_ScanData item = new P_ScanData();
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
            rootItem = item;
        }
    }

    public boolean isDir() {
        return map != null;
    }

    public P_ScanData[] list() {
        Collection<P_ScanData> c = map.values();
        return c.toArray(new P_ScanData[]{});
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
