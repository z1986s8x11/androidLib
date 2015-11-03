package org.zsx.android.api.widget;

import java.util.List;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class SearchView_Activity extends _BaseActivity implements
		OnQueryTextListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_searchview);
		SearchView searchSV = (SearchView) findViewById(R.id.act_widget_current_view);
		searchSV.setOnQueryTextListener(this);
		// searchSV.setSubmitButtonEnabled(false);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (searchManager != null) {
			List<SearchableInfo> searchables = searchManager
					.getSearchablesInGlobalSearch();
			// Try to use the "applications" global search provider
			SearchableInfo info = searchManager
					.getSearchableInfo(getComponentName());
			for (SearchableInfo inf : searchables) {
				if (inf.getSuggestAuthority() != null
						&& inf.getSuggestAuthority().startsWith("applications")) {
					info = inf;
				}
			}
			searchSV.setSearchableInfo(info);
		}
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		Toast.makeText(this, String.valueOf(query), Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return true;
	}
}
