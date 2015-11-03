package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class AutoCompleteTextView_Activity extends _BaseActivity implements OnItemClickListener {
	private String[] arr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_autocompletetextview);
		AutoCompleteTextView autoText = (AutoCompleteTextView) findViewById(R.id.act_widget_current_view);
		arr = getResources().getStringArray(R.array.activity_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arr);
		autoText.setAdapter(adapter);
		autoText.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg1 instanceof TextView) {
			TextView t = (TextView) arg1;
			Toast.makeText(this, arr[arg2] + t.getText(), Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, arr[arg2], Toast.LENGTH_SHORT).show();
		}
	}

}
