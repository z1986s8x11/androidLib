package org.zsx.android.api.widget;

import java.util.List;

import org.zsx.android.api.R;
import org.zsx.android.api._BasePreferencesActivity;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Button;

public class PreferenceHeader_Activity extends _BasePreferencesActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (hasHeaders()) {
			Button button = new Button(this);
			button.setText("List Footer");
			setListFooter(button);
		}
	}

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.xml_preference_header, target);
	}

	public static class Prefs1Fragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// PreferenceManager.setDefaultValues(getActivity(),
			// R.xml.advanced_preferences, false);
			addPreferencesFromResource(R.xml.xml_preference);
		}
	}
}
