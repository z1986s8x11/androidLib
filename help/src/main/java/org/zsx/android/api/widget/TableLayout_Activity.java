package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;

public class TableLayout_Activity extends _BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_tablelayout);
		final TableLayout table = (TableLayout) findViewById(R.id.global_tablelayout);
		findViewById(R.id.global_btn1).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						table.setColumnCollapsed(0, !table.isColumnCollapsed(0));
						((Button) v).setText(table.isColumnCollapsed(0) ? "显示"
								: "隐藏");
					}
				});
	}

}
