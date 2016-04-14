package org.zsx.android.api.design;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.ImageView;

import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/13 15:27
 */
public class NavigationView_Activity extends _BaseActivity {
    private DrawerLayout drawerLayout;
    private ImageView imageView;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_design_navigationview);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        imageView = (ImageView) findViewById(R.id.image);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        //navigationView选中Item处理,为了代码整齐些，就放在一个函数里了
        handNavigationView();
    }

    private void handNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //用于辨别此前是否已有选中条目
            MenuItem preMenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //首先将选中条目变为选中状态 即checked为true,后关闭Drawer，以前选中的Item需要变为未选中状态
                if (preMenuItem != null)
                    preMenuItem.setChecked(false);
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                preMenuItem = menuItem;

                //不同item对应不同图片
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item1:
                        imageView.setImageResource(android.R.drawable.ic_dialog_alert);
                        break;
                    case R.id.navigation_item2:
                        imageView.setImageResource(android.R.drawable.ic_dialog_dialer);
                        break;
                    case R.id.navigation_item3:
                        imageView.setImageResource(android.R.drawable.ic_dialog_email);
                        break;
                    case R.id.navigation_sub_item1:
                        imageView.setImageResource(android.R.drawable.ic_dialog_info);
                        break;
                    case R.id.navigation_sub_item2:
                        imageView.setImageResource(android.R.drawable.ic_dialog_map);
                        break;
                    case R.id.navigation_sub_item3:
                        imageView.setImageResource(android.R.drawable.ic_popup_disk_full);
                        break;
                }
                return true;
            }
        });
    }
}
