package zsx.com.test.ui.widget;

import android.os.Bundle;
import android.view.View;

import com.zsx.widget.slidingmenu.SlidingMenu;

import zsx.com.test.R;
import zsx.com.test.base._BaseFragmentActivity;

/**
 * Created by zhusx on 2015/8/7.
 */
public class SlidingMenuActivity extends _BaseFragmentActivity implements View.OnClickListener {
    private SlidingMenu mSlidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_slidingmenu);
        mSlidingMenu = new SlidingMenu(this, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setMenu(R.layout.activity_download_file);
        mSlidingMenu.setBehindWidth(400);
        findViewById(R.id.btn_toggle).setOnClickListener(this);


//        mSlidingMenu.setMode(SlidingMenu.LEFT);//设置左滑菜单
//        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置滑动的屏幕范围，该设置为全屏区域都可以滑动
//        mSlidingMenu.setShadowDrawable(R.drawable.shadow);//设置阴影图片
//        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);//设置阴影图片的宽度
//        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//SlidingMenu划出时主页面显示的剩余宽度
//        mSlidingMenu.setBehindWidth(400);//设置SlidingMenu菜单的宽度
//        mSlidingMenu.setFadeDegree(0.35f);//SlidingMenu滑动时的渐变程度
//        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//使SlidingMenu附加在Activity上
//        mSlidingMenu.setMenu(R.layout.menu_layout);//设置menu的布局文件
//        mSlidingMenu.toggle();//动态判断自动关闭或开启SlidingMenu
//        mSlidingMenu.showMenu();//显示SlidingMenu
//        mSlidingMenu.showContent();//显示内容
//        mSlidingMenu.setOnOpenListener(onOpenListener);//监听slidingmenu打开
//        mSlidingMenu.setOnOpenedListener(onOpenedlistener);监听slidingmenu打开后
//        mSlidingMenu.OnCloseListener(OnClosedListener);//监听slidingmenu关闭时事件
//        mSlidingMenu.OnClosedListener(OnClosedListener);//监听slidingmenu关闭后事件          左右都可以划出SlidingMenu菜单只需要设置
//        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//属性，然后设置右侧菜单的布局文件
//        mSlidingMenu.setSecondaryMenu(R.layout.menu_fram2);//设置右侧菜单
//        mSlidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);//右侧菜单的阴影图片
    }

    @Override
    public void onClick(View v) {
        mSlidingMenu.toggle();
    }
}
