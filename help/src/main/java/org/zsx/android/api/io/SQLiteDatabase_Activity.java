package org.zsx.android.api.io;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteDatabase_Activity extends _BaseActivity implements
        Button.OnClickListener {
    SQLiteDatabase db;
    EditText mEditText;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.io_sqlitedatabase);
        /*
		 * 在tool下有sqlite3.exe工具 .database查看数据库 .tables查看数据库中的表
		 * .help查看sqlite3支持的命令
		 */

        db = SQLiteDatabase.openOrCreateDatabase(new File(getExternalCacheDir(), "zsx.db3"), null);
        Button insert = (Button) findViewById(R.id.global_btn1);
        mEditText = (EditText) findViewById(R.id.global_edittext1);
        mListView = (ListView) findViewById(R.id.global_listview);
        insert.setOnClickListener(this);
    }

    /**
     * query(table, columns, selection, selectionArgs, groupBy, having, orderBy,
     * limit) table 表名 columns 字段名 如 "id , name ,age" selection where条件 如
     * "id=1 and age=19" selectionArgs 匹配selection中的 ? groupBy 分组 having 分组过滤
     * orderBy 排序 limit 分页
     */
    @Override
    public void onClick(View v) {
        try {
            // 开始事务
            db.beginTransaction();
            String str = mEditText.getText().toString();
            if (str == null || "".equals(str)) {
                db.delete("zsx_table", null, null);
            } else {
                ContentValues cv = new ContentValues();
                cv.put("name", str);
                db.insert("zsx_table", "name", cv);
            }
            Cursor cursor = db.rawQuery("select name,_id from zsx_table", null);
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            while (cursor.moveToNext()) {
                Map<String, String> m = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    m.put("name", cursor.getString(0));
                    m.put("id", cursor.getString(1));
                }
                list.add(m);
            }
            // android.R.id.text1 系统layout的id
            SimpleAdapter simple = new SimpleAdapter(this, list,
                    android.R.layout.simple_list_item_2, new String[]{"name",
                    "id"}, new int[]{android.R.id.text1,
                    android.R.id.text2});
            mListView.setAdapter(simple);
            // 调用该方法表示事务完成成功,否则db.endTransaction()方法将回滚事务
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            db.endTransaction();
            db.execSQL("create table zsx_table(_id integer primary key autoincrement,name varchar(50))");
            Toast.makeText(this, "由于没有创建表,现在创建成功请重新输入", Toast.LENGTH_SHORT)
                    .show();
            db.beginTransaction();
        } finally {
            // 由事务的标志决定是提交事务还是回滚事务
            db.endTransaction();
        }

    }

    @Override
    protected void onDestroy() {
        if (db != null && db.isOpen()) {
            db.close();
        }
        super.onDestroy();
    }

}
