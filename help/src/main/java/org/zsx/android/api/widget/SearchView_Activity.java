package org.zsx.android.api.widget;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import java.util.List;

public class SearchView_Activity extends _BaseActivity {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
            _showToast("不支持2.3");
            finish();
            return;
        }
        setContentView(R.layout.widget_searchview);
        SearchView searchSV = (SearchView) findViewById(R.id.act_widget_current_view);
        searchSV.setOnQueryTextListener(new OnQueryTextListener(this));
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


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private class OnQueryTextListener implements SearchView.OnQueryTextListener {
        private Context context;

        public OnQueryTextListener(Context context) {
            this.context = context;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(context, String.valueOf(query), Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }

    }
}
