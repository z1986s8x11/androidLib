package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class GridView_Activity extends _BaseActivity implements GridView.OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_gridview);
		GridView mGridView = (GridView) findViewById(R.id.act_widget_current_view);
		String[] sts = new String[] { "java", "php", "sql", "plsql", "html", "javascript", "css", "android", "c++", "xml" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sts);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TextView t = (TextView) arg1;
		Toast.makeText(this, t.getText().toString(), Toast.LENGTH_SHORT).show();
	}
}
