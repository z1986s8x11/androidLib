package org.zsx.android.api.widget;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import java.util.HashSet;
import java.util.Set;

public class ExpandableListView_Activity extends _BaseActivity implements ExpandableListView.OnChildClickListener {
    private String[][] child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_expandablelistview);
        ExpandableListView mExpandableListView = (ExpandableListView) findViewById(R.id.act_widget_current_view);
        Set<String> java = new HashSet<String>();
        Set<String> txt = new HashSet<String>();
        Set<String> xml = new HashSet<String>();
        String[] parent = new String[]{"java", "xml", "txt"};
        child = new String[][]{java.toArray(new String[0]), xml.toArray(new String[0]), txt.toArray(new String[0])};
        mExpandableListView.setAdapter(new Adapter(parent, child));
        mExpandableListView.setOnChildClickListener(this);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        _showToast(String.valueOf(groupPosition) + ":" + String.valueOf(childPosition));
        return true;
    }

    class Adapter implements ExpandableListAdapter {
        private String[] parent;
        private String[][] child;

        public Adapter(String[] parent, String[][] child) {
            this.parent = parent;
            this.child = child;
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void onGroupExpanded(int groupPosition) {

        }

        @Override
        public void onGroupCollapsed(int groupPosition) {

        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup p) {
            TextView t = new TextView(ExpandableListView_Activity.this);
            t.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 64));
            t.setText(parent[groupPosition]);
            t.setPadding(64, 0, 0, 0);
            return t;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public int getGroupCount() {
            return parent.length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return parent[groupPosition];
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            return groupId;
        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return child[groupPosition].length;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView text = new TextView(ExpandableListView_Activity.this);
            text.setPadding(36, 0, 0, 0);
            text.setText(child[groupPosition][childPosition].toString());
            return text;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child[groupPosition][childPosition];
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }
    }
}
