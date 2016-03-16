package com.tools;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import java.io.File;

/**
 * @author zsx
 * @date 2013-12-27 上午11:48:23
 * @description 需要显示的数据必须存放在Assets目录下面<br/>
 * <ul>
 * assets目录下存放项目相关文件
 * <li>src</li>
 * <li>xml</li>
 * <li>layout</li>
 * <li>drawable</li>
 * <li>anmi</li>
 * <ul>
 */
public class Lib_Class_ShowCodeUtil {
    private int[] xmlID;
    private Class<?>[] clsArr;
    private String[] filePaths;
    private final int javaMenuStartID = 0;
    private int javaMenuEndID = javaMenuStartID;
    private final int xmlMenuStartID = 0;
    private int xmlMenuEndID = xmlMenuStartID;
    private final int fileMenuStartID = 0;
    private int fileMenuEndID = fileMenuStartID;
    private final int javaGroupID = 1;
    private final int xmlGroupID = 2;
    private final int fileGroupID = 3;

    public void setShowJava(Class<?>... clsArr) {
        this.clsArr = clsArr;
    }

    public Class<?>[] getShowJava() {
        return clsArr;
    }

    public int[] getShowXml() {
        return xmlID;
    }

    public void setShowXML(int... xmlSource) {
        this.xmlID = xmlSource;
    }

    public String[] getShowFile() {
        return filePaths;
    }

    public void setShowFile(String... filePath) {
        filePaths = filePath;
    }

    public void _onOptionsItemSelected(Context context, MenuItem item) {
        switch (item.getGroupId()) {
            case javaGroupID:
                if (item.getItemId() >= javaMenuStartID && item.getItemId() <= javaMenuEndID) {
                    Class<?> cls = clsArr[item.getItemId()];
                    String fileName = "java/" + cls.getName().replace(".", "/") + ".java";
                    Intent in = new Intent(context, Lib_Class_ShowCodeResultActivity.class);
                    in.putExtra(Lib_Class_ShowCodeResultActivity.RM_EXTRA_SHOW_CODE_FILE_KEY, fileName);
                    context.startActivity(in);
                }
                break;
            case xmlGroupID:
                if (item.getItemId() >= xmlMenuStartID && item.getItemId() <= xmlMenuEndID) {
                    int resourceID = xmlID[item.getItemId()];
                    String fileName = "res/" + context.getResources().getResourceTypeName(resourceID) + File.separator
                            + context.getResources().getResourceEntryName(resourceID) + ".xml";
                    Intent in = new Intent(context, Lib_Class_ShowCodeResultActivity.class);
                    in.putExtra(Lib_Class_ShowCodeResultActivity.RM_EXTRA_SHOW_CODE_FILE_KEY, fileName);
                    context.startActivity(in);
                }
                break;
            case fileGroupID:
                if (item.getItemId() >= fileMenuStartID && item.getItemId() <= fileMenuEndID) {
                    Intent in = new Intent(context, Lib_Class_ShowCodeResultActivity.class);
                    in.putExtra(Lib_Class_ShowCodeResultActivity.RM_EXTRA_SHOW_CODE_FILE_KEY, filePaths[fileMenuEndID - item.getItemId() - 1]);
                    context.startActivity(in);
                }
                break;
            default:
                break;
        }

    }

    public void _onCreateOptionsMenu(Context context, Menu menu) {
        if (clsArr != null) {
            SubMenu javaSubMenu = menu.addSubMenu(javaGroupID, 0x0811, 0, "java");
            for (int i = 0; i < clsArr.length; i++) {
                javaSubMenu.add(javaGroupID, javaMenuEndID++, 0, clsArr[i].getSimpleName());
            }
        }
        if (xmlID != null) {
            SubMenu javaSubMenu = menu.addSubMenu(xmlGroupID, 0x0821, 0, "xml");
            for (int i = 0; i < xmlID.length; i++) {
                javaSubMenu.add(xmlGroupID, xmlMenuEndID++, 0, context.getResources().getResourceEntryName(xmlID[i]));
            }
        }
        if (filePaths != null) {
            SubMenu javaSubMenu = menu.addSubMenu(fileGroupID, 0x0831, 0, "特殊文件");
            for (int i = 0; i < filePaths.length; i++) {
                javaSubMenu.add(fileGroupID, fileMenuEndID++, 0, filePaths[i]);
            }
        }
    }
}
