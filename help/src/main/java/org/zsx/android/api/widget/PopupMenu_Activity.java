package org.zsx.android.api.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

public class PopupMenu_Activity extends _BaseActivity implements
        OnClickListener {
    PopupMenu mPopupMenu;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
            _showToast("不支持2.3");
            finish();
            return;
        }
        setContentView(R.layout.widget_popupmenu);
        Button showPopupBtn = (Button) findViewById(R.id.global_btn1);
        showPopupBtn.setOnClickListener(this);
        mPopupMenu = new PopupMenu(this, showPopupBtn);
        getMenuInflater().inflate(R.menu.popup_menu, mPopupMenu.getMenu());
        mPopupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(this));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_btn1:
                mPopupMenu.show();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private class OnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private Context context;

        public OnMenuItemClickListener(Context context) {
            this.context = context;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Toast.makeText(context, "Clicked popup menu item " + item.getTitle(),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
