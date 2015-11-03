package org.zsx.android.api.widget;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class AlertDialog_Activity extends _BaseActivity implements Button.OnClickListener {
	private Button mShowDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_alertdialog);
		mShowDialog = (Button) findViewById(R.id.global_btn1);
		mShowDialog.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// setView() 从R.layout.布局文件加载对话框
		// setItems() 创建普通列表对话框
		// setMultiChoiceItems()创建多选对话框
		// setSingleChoiceItems()创建单选列表对话框
		// setAdapter()创建根据listAdapter提供的列表对话框
		new AlertDialog.Builder(this)
		/* 标题 */
		.setTitle("标题")
		/* 图标 */
		.setIcon(android.R.drawable.ic_dialog_info)
		/* 信息 */
		.setMessage("消息")
		/* 不能取消 */
		.setCancelable(false)
		/* 按钮 */
		.setNegativeButton("按钮一", null).setNeutralButton("按钮二", null).setPositiveButton("按钮三", new AlertDialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(AlertDialog_Activity.this, "点击了按钮三", Toast.LENGTH_LONG).show();
			}
		}).create().show();
		/** 自定义View 取消 需要调用Builder的cancel()或者dismiss() */
	}
}
