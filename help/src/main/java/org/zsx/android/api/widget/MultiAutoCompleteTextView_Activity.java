package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

public class MultiAutoCompleteTextView_Activity extends _BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_multiautocompletetextview);
		MultiAutoCompleteTextView autoTV = (MultiAutoCompleteTextView) findViewById(R.id.act_widget_current_view);
		autoTV.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,
				android.R.id.text1, getResources().getStringArray(
						R.array.activity_list)));
		autoTV.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	}
}
