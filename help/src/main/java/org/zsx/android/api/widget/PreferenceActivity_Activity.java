package org.zsx.android.api.widget;

import org.zsx.android.api.R;
import org.zsx.android.api._BasePreferencesActivity;

import android.os.Bundle;

public class PreferenceActivity_Activity extends _BasePreferencesActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.xml_preference);
	}
}
