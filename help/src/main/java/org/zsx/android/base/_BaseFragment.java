package org.zsx.android.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zsx.debug.Lib_SourceCodeHelper;

public class _BaseFragment extends Fragment {
    private Lib_SourceCodeHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        helper._onOptionsItemSelected(getActivity(), item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        helper = new Lib_SourceCodeHelper(this.getClass());
        helper._onCreateOptionsMenu(getActivity(), menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
