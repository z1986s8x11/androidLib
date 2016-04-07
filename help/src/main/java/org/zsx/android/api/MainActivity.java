package org.zsx.android.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.zsx.android.api.activity.Activity_Activity;
import org.zsx.android.api.activity.WallpaperActivity_Activity;
import org.zsx.android.api.animator.AlphaAnimation_Activity;
import org.zsx.android.api.animator.AnimatorSet_Activity;
import org.zsx.android.api.animator.Animator_Activity;
import org.zsx.android.api.animator.FrameAnimation_Activity;
import org.zsx.android.api.animator.Keyframe_Activity;
import org.zsx.android.api.animator.LayoutAnimation_Activity;
import org.zsx.android.api.animator.LayoutTransition_Activity;
import org.zsx.android.api.animator.ObjectAnimator_Activity;
import org.zsx.android.api.animator.RotateAnimation_Activity;
import org.zsx.android.api.animator.ScaleAnimation_Activity;
import org.zsx.android.api.animator.TransitionSet_Activity;
import org.zsx.android.api.animator.TranslateAnimation_Activity;
import org.zsx.android.api.animator.ValueAnimator_Activity;
import org.zsx.android.api.animator.ViewAnimator_Activity;
import org.zsx.android.api.animator.ViewPropertyAnimator_Activity;
import org.zsx.android.api.device.Configuration_Activity;
import org.zsx.android.api.device.SoftInputModes_Activity;
import org.zsx.android.api.device.WallpaperManager_Activity;
import org.zsx.android.api.drawable.ShapeDrawable_Activity;
import org.zsx.android.api.fragment.DialogFragment_Activity;
import org.zsx.android.api.fragment.Fragment_Activity;
import org.zsx.android.api.graphice.Animation_Activity;
import org.zsx.android.api.graphice.Arcs_Activity;
import org.zsx.android.api.graphice.Movie_Activity;
import org.zsx.android.api.io.OpenFileStrem_Activity;
import org.zsx.android.api.io.SQLiteDatabase_Activity;
import org.zsx.android.api.io.SharedPreference_Activity;
import org.zsx.android.api.media.AsyncPlayer_Activity;
import org.zsx.android.api.media.AudioRecord_Activity;
import org.zsx.android.api.media.Camera_Activity;
import org.zsx.android.api.media.MediaPlayer_Activity;
import org.zsx.android.api.media.MediaRecorder_Activity;
import org.zsx.android.api.media.SoundPool_Activity;
import org.zsx.android.api.parse.Attrs_Activity;
import org.zsx.android.api.parse.XmlResourceParser_Activity;
import org.zsx.android.api.sensor.SensorLight_Activity;
import org.zsx.android.api.service.AlarmManager_Activity;
import org.zsx.android.api.service.Clipboard_Activity;
import org.zsx.android.api.service.NfcManager_Activity;
import org.zsx.android.api.service.Notification_Activity;
import org.zsx.android.api.service.SensorManager_Activity;
import org.zsx.android.api.service.Service_Activity;
import org.zsx.android.api.service.SmsManager_Activity;
import org.zsx.android.api.service.Telephony_Activity;
import org.zsx.android.api.service.WindowManager_Activity;
import org.zsx.android.api.util.AccountManager_Activity;
import org.zsx.android.api.util.AsyncTask_Activity;
import org.zsx.android.api.util.BroadcastReceiver_Activity;
import org.zsx.android.api.util.CountDownTimer_Activity;
import org.zsx.android.api.util.Environment_Activity;
import org.zsx.android.api.util.Handler_Activity;
import org.zsx.android.api.util.ID_Activity;
import org.zsx.android.api.util.Intent_Activity;
import org.zsx.android.api.util.LauncherActivity_Activity;
import org.zsx.android.api.util.QRCode_Activity;
import org.zsx.android.api.util.SystemDrawable_Activity;
import org.zsx.android.api.util.VelocityTracker_Activity;
import org.zsx.android.api.util.ViewDragHelper_Activity;
import org.zsx.android.api.view.CustomFullView_Activity;
import org.zsx.android.api.view.Secure_Activity;
import org.zsx.android.api.widget.AlertDialog_Activity;
import org.zsx.android.api.widget.AnalogClock_Activity;
import org.zsx.android.api.widget.AutoCompleteTextView_Activity;
import org.zsx.android.api.widget.Button_Activity;
import org.zsx.android.api.widget.CheckBox_Activity;
import org.zsx.android.api.widget.CheckedTextView_Activity;
import org.zsx.android.api.widget.Chronometer_Activity;
import org.zsx.android.api.widget.ContextMenu_Activity;
import org.zsx.android.api.widget.DatePickerDialog_Activity;
import org.zsx.android.api.widget.DatePicker_Activity;
import org.zsx.android.api.widget.DigitalClock_Activity;
import org.zsx.android.api.widget.EditText_Activity;
import org.zsx.android.api.widget.ExpandableListView_Activity;
import org.zsx.android.api.widget.Gallery_Activity;
import org.zsx.android.api.widget.GridLayout_Activity;
import org.zsx.android.api.widget.GridView_Activity;
import org.zsx.android.api.widget.HorizontalScrollView_Activity;
import org.zsx.android.api.widget.ImageButton_Activity;
import org.zsx.android.api.widget.ImageSwithcer_Activity;
import org.zsx.android.api.widget.ImageView_Activity;
import org.zsx.android.api.widget.ListView_Activity;
import org.zsx.android.api.widget.MultiAutoCompleteTextView_Activity;
import org.zsx.android.api.widget.PopupMenu_Activity;
import org.zsx.android.api.widget.PopupWindow_Activity;
import org.zsx.android.api.widget.PreferenceActivity_Activity;
import org.zsx.android.api.widget.PreferenceFragment_Activity;
import org.zsx.android.api.widget.PreferenceHeader_Activity;
import org.zsx.android.api.widget.ProgressBar_Activity;
import org.zsx.android.api.widget.ProgressDialog_Activity;
import org.zsx.android.api.widget.RaidoButton_Activity;
import org.zsx.android.api.widget.RatingBar_Activity;
import org.zsx.android.api.widget.ScrollView_Activity;
import org.zsx.android.api.widget.SearchView_Activity;
import org.zsx.android.api.widget.SeekBar_Activity;
import org.zsx.android.api.widget.SlidingDrawer_Activity;
import org.zsx.android.api.widget.Spinner_Activity;
import org.zsx.android.api.widget.SurfaceView_Activity;
import org.zsx.android.api.widget.SwipeRefreshLayout_Activity;
import org.zsx.android.api.widget.Switch_Activity;
import org.zsx.android.api.widget.TabHost_Activity;
import org.zsx.android.api.widget.TableLayout_Activity;
import org.zsx.android.api.widget.TextClock_Activity;
import org.zsx.android.api.widget.TextSwitch_Activity;
import org.zsx.android.api.widget.TextToSpeech_Activity;
import org.zsx.android.api.widget.TextView_Activity;
import org.zsx.android.api.widget.TimePickerDialog_Activity;
import org.zsx.android.api.widget.TimePicker_Activity;
import org.zsx.android.api.widget.Toast_Activity;
import org.zsx.android.api.widget.ToggleButton_Activity;
import org.zsx.android.api.widget.VideoView_Activity;
import org.zsx.android.api.widget.ViewFlipper_Activity;
import org.zsx.android.api.widget.ViewGroup_Activity;
import org.zsx.android.api.widget.WebView_Activity;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends _BaseActivity {
    private ExpandableListView mELV;
    // group类别
    private String[] activity_list = new String[]{"动画", "设备", "图像", "文件数据", "多媒体", "格式解析", "传感器", "服务", "工具类", "控件", "自定义View", "Activity", "Fragment", "绘图"};
    private Class<?>[] widget_Cls = new Class<?>[]{AlertDialog_Activity.class, AnalogClock_Activity.class, AutoCompleteTextView_Activity.class,
            Button_Activity.class, CheckBox_Activity.class, CheckedTextView_Activity.class, Chronometer_Activity.class, ContextMenu_Activity.class,
            DatePicker_Activity.class, DatePickerDialog_Activity.class, DigitalClock_Activity.class, EditText_Activity.class,
            ExpandableListView_Activity.class, Gallery_Activity.class, GridLayout_Activity.class, GridView_Activity.class, HorizontalScrollView_Activity.class,
            ImageButton_Activity.class, ImageSwithcer_Activity.class, ImageView_Activity.class, ListView_Activity.class, PopupMenu_Activity.class,
            PopupWindow_Activity.class, PreferenceActivity_Activity.class, PreferenceFragment_Activity.class, PreferenceHeader_Activity.class,
            ProgressBar_Activity.class, ProgressDialog_Activity.class, RaidoButton_Activity.class, RatingBar_Activity.class, SearchView_Activity.class,
            ScrollView_Activity.class, SeekBar_Activity.class, SlidingDrawer_Activity.class, Switch_Activity.class, SwipeRefreshLayout_Activity.class,
            SurfaceView_Activity.class, Spinner_Activity.class, TabHost_Activity.class, TextClock_Activity.class, TableLayout_Activity.class,
            TextToSpeech_Activity.class, TextView_Activity.class, TextSwitch_Activity.class, TimePicker_Activity.class, TimePickerDialog_Activity.class,
            Toast_Activity.class, ToggleButton_Activity.class, MultiAutoCompleteTextView_Activity.class, VideoView_Activity.class, ViewFlipper_Activity.class,
            ViewGroup_Activity.class, WebView_Activity.class};
    private Class<?>[] bitmap_Cls = new Class<?>[]{ShapeDrawable_Activity.class};
    private Class<?>[] anim_Cls = new Class<?>[]{Animator_Activity.class, AlphaAnimation_Activity.class, AnimatorSet_Activity.class,
            FrameAnimation_Activity.class, Keyframe_Activity.class, RotateAnimation_Activity.class, ScaleAnimation_Activity.class,
            LayoutAnimation_Activity.class, LayoutTransition_Activity.class, TranslateAnimation_Activity.class, TransitionSet_Activity.class, ObjectAnimator_Activity.class, ViewAnimator_Activity.class,
            ValueAnimator_Activity.class, ViewPropertyAnimator_Activity.class};
    private Class<?>[] io_Cls = new Class<?>[]{OpenFileStrem_Activity.class, SharedPreference_Activity.class, SQLiteDatabase_Activity.class};
    private Class<?>[] sensor_Cls = new Class<?>[]{SensorLight_Activity.class};
    private Class<?>[] device_Cls = new Class<?>[]{Configuration_Activity.class, SoftInputModes_Activity.class, WallpaperManager_Activity.class};
    private Class<?>[] service_Cls = new Class<?>[]{AlarmManager_Activity.class, Clipboard_Activity.class, NfcManager_Activity.class,
            Notification_Activity.class, SensorManager_Activity.class, Service_Activity.class, SmsManager_Activity.class, Telephony_Activity.class,
            WindowManager_Activity.class};
    private Class<?>[] util_Cls = new Class<?>[]{AccountManager_Activity.class, AsyncTask_Activity.class, BroadcastReceiver_Activity.class,
            CountDownTimer_Activity.class, Environment_Activity.class, Handler_Activity.class, ID_Activity.class, Intent_Activity.class,
            LauncherActivity_Activity.class, QRCode_Activity.class, SystemDrawable_Activity.class, VelocityTracker_Activity.class, ViewDragHelper_Activity.class};
    private Class<?>[] parse_Cls = new Class<?>[]{Attrs_Activity.class, XmlResourceParser_Activity.class};
    private Class<?>[] media_Cls = new Class<?>[]{AsyncPlayer_Activity.class, AudioRecord_Activity.class, Camera_Activity.class, MediaPlayer_Activity.class,
            MediaRecorder_Activity.class, SoundPool_Activity.class};
    private Class<?>[] view_Cls = new Class<?>[]{Secure_Activity.class, CustomFullView_Activity.class};
    private Class<?>[] act_Cls = new Class<?>[]{Activity_Activity.class, WallpaperActivity_Activity.class,};
    private Class<?>[] fragmentCls = new Class<?>[]{DialogFragment_Activity.class, Fragment_Activity.class};
    private Class<?>[] graphiceCls = new Class<?>[]{Animation_Activity.class, Arcs_Activity.class, Movie_Activity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _setDoubleBackExit(true);
        List<Class<?>[]> mList = new ArrayList<Class<?>[]>();
        initData(mList);
        mELV = (ExpandableListView) findViewById(R.id.elv_list);
        mELV.setAdapter(new ELVAdapter(this, activity_list, mList));
    }

    private final static Comparator<Class<?>> sDisplayNameComparator = new Comparator<Class<?>>() {
        private final Collator collator = Collator.getInstance();

        public int compare(Class<?> map1, Class<?> map2) {
            return collator.compare(map1.getSimpleName(), map2.getSimpleName());
        }
    };

    private void initData(List<Class<?>[]> mList) {
        // item anim
        addClassToList(mList, anim_Cls);
        // item device
        addClassToList(mList, device_Cls);
        // item bitmap
        addClassToList(mList, bitmap_Cls);
        // item io
        addClassToList(mList, io_Cls);
        // item media
        addClassToList(mList, media_Cls);
        // item parse
        addClassToList(mList, parse_Cls);
        // item sensor
        addClassToList(mList, sensor_Cls);
        // item service
        addClassToList(mList, service_Cls);
        // item util
        addClassToList(mList, util_Cls);
        // item widget
        addClassToList(mList, widget_Cls);
        // item view
        addClassToList(mList, view_Cls);
        // item view
        addClassToList(mList, act_Cls);
        // item fragment
        addClassToList(mList, fragmentCls);
        // item graphice
        addClassToList(mList, graphiceCls);
    }

    private void addClassToList(List<Class<?>[]> mList, Class<?>[] cls) {
        Arrays.sort(cls, sDisplayNameComparator);
        mList.add(cls);
    }

    private class ELVAdapter extends BaseExpandableListAdapter {
        private List<Class<?>[]> list;
        private Context context;
        private String[] group;

        public ELVAdapter(Context context, String[] group, List<Class<?>[]> list) {
            this.list = list;
            this.group = group;
            this.context = context;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return groupPosition * 1000 + childPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return groupPosition * 1000 + childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView t = new TextView(context);
            t.setPadding(80, 20, 0, 20);
            t.setText(list.get(groupPosition)[childPosition].getSimpleName().substring(0, list.get(groupPosition)[childPosition].getSimpleName().indexOf("_")));
            t.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, list.get(groupPosition)[childPosition]);
                    context.startActivity(intent);
                }
            });
            return t;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return list.get(groupPosition).length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return list.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return list.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView t = new TextView(context);
            t.setPadding(80, 20, 0, 20);
            t.setText(group[groupPosition]);
            return t;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
