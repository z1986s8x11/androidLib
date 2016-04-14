package org.zsx.android.api.io;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zsx.debug.LogUtil;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class OpenFileStrem_Activity extends _BaseActivity {
	private String filename = "zsx";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.io_openfilestrem);
		// 默认会写入/data/data/<包名>/files目录下
		// Context还有 getDir()创建或者获取对应子目录 getFilesDir() 获取数据文件绝对路径
		// fileList() 拿到该文件下所有文件名 deleteFile(); 删除对应文件
		Button read = (Button) findViewById(R.id.global_btn1);
		Button write = (Button) findViewById(R.id.global_btn2);
		final TextView t = (TextView) findViewById(R.id.global_textview1);
		final EditText edit = (EditText) findViewById(R.id.global_edittext1);
		read.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					FileInputStream in = openFileInput(filename);
					byte[] buff = new byte[1024];
					StringBuilder s = new StringBuilder();
					int size = 0;
					while ((size = in.read(buff)) != -1) {
						s.append(new String(buff, 0, size));
					}
					t.setText(s.toString());
				} catch (FileNotFoundException e) {
					LogUtil.e(this, "FileNotFoundException");
				} catch (IOException e) {
					LogUtil.e(this, "IOException");
				}

			}
		});
		write.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// MODE_PRIVATE 文件只能被当前文件读写
				// MODE_APPEND 以追加方式打开该文件,应用程序可以想该文件写入东西
				// MODE_WORLD_READABLE 该文件的内容可以被其他程序读
				// MODE_WORLD_WRITEABLE 该文件的内容可以被其他程序读写
				try {
					FileOutputStream out = openFileOutput(filename, Context.MODE_PRIVATE);
					PrintStream ps = new PrintStream(out);
					ps.println(edit.getText().toString());
					ps.close();
				} catch (FileNotFoundException e) {
					LogUtil.e(this, "FileNotFoundException");
				}
			}
		});
	}
}
