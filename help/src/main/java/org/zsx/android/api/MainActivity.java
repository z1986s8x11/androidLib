package org.zsx.android.api;

import com.zsx.debug.P_ProjectActivity;

public class MainActivity extends P_ProjectActivity {
//    private ExpandableListView mELV;
//    // group类别
//    private String[] activity_list = new String[]{"动画", "设备", "图像", "文件数据", "多媒体", "格式解析", "传感器", "服务", "工具类", "控件", "自定义View", "Activity", "Fragment", "绘图"};
//    private Class<?>[] widget_Cls = new Class<?>[]{AlertDialog_Activity.class, AnalogClock_Activity.class, AutoCompleteTextView_Activity.class,
//            Button_Activity.class, CheckBox_Activity.class, CheckedTextView_Activity.class, Chronometer_Activity.class, ContextMenu_Activity.class,
//            DatePicker_Activity.class, DatePickerDialog_Activity.class, DigitalClock_Activity.class, EditText_Activity.class,
//            ExpandableListView_Activity.class, Gallery_Activity.class, GridLayout_Activity.class, GridView_Activity.class, HorizontalScrollView_Activity.class,
//            ImageButton_Activity.class, ImageSwithcer_Activity.class, ImageView_Activity.class, ListView_Activity.class, PopupMenu_Activity.class,
//            PopupWindow_Activity.class, PreferenceActivity_Activity.class, PreferenceFragment_Activity.class, PreferenceHeader_Activity.class,
//            ProgressBar_Activity.class, ProgressDialog_Activity.class, RaidoButton_Activity.class, RatingBar_Activity.class, RecyclerView_Activity.class, SearchView_Activity.class,
//            ScrollView_Activity.class, SeekBar_Activity.class, SlidingDrawer_Activity.class, Switch_Activity.class, SwipeRefreshLayout_Activity.class,
//            SurfaceView_Activity.class, Spinner_Activity.class, TabHost_Activity.class, TextClock_Activity.class, TableLayout_Activity.class,
//            TextToSpeech_Activity.class, TextView_Activity.class, TextSwitch_Activity.class, TimePicker_Activity.class, TimePickerDialog_Activity.class,
//            Toast_Activity.class, ToggleButton_Activity.class, MultiAutoCompleteTextView_Activity.class, VideoView_Activity.class, ViewFlipper_Activity.class,
//            ViewGroup_Activity.class, WebView_Activity.class};
//    private Class<?>[] bitmap_Cls = new Class<?>[]{ShapeDrawable_Activity.class};
//    private Class<?>[] anim_Cls = new Class<?>[]{Animator_Activity.class, AlphaAnimation_Activity.class, AnimatorSet_Activity.class,
//            FrameAnimation_Activity.class, Keyframe_Activity.class, RotateAnimation_Activity.class, ScaleAnimation_Activity.class,
//            LayoutAnimation_Activity.class, LayoutTransition_Activity.class, TranslateAnimation_Activity.class, TransitionSet_Activity.class, ObjectAnimator_Activity.class, ViewAnimator_Activity.class,
//            ValueAnimator_Activity.class, ViewPropertyAnimator_Activity.class};
//    private Class<?>[] io_Cls = new Class<?>[]{OpenFileStrem_Activity.class, SharedPreference_Activity.class, SQLiteDatabase_Activity.class};
//    private Class<?>[] sensor_Cls = new Class<?>[]{SensorLight_Activity.class};
//    private Class<?>[] device_Cls = new Class<?>[]{Configuration_Activity.class, SoftInputModes_Activity.class, WallpaperManager_Activity.class};
//    private Class<?>[] service_Cls = new Class<?>[]{AlarmManager_Activity.class, Clipboard_Activity.class, NfcManager_Activity.class,
//            Notification_Activity.class, SensorManager_Activity.class, Service_Activity.class, SmsManager_Activity.class, Telephony_Activity.class,
//            WindowManager_Activity.class};
//    private Class<?>[] util_Cls = new Class<?>[]{AccountManager_Activity.class, AsyncTask_Activity.class, BroadcastReceiver_Activity.class,
//            CountDownTimer_Activity.class, Environment_Activity.class, Handler_Activity.class, ID_Activity.class, Intent_Activity.class,
//            LauncherActivity_Activity.class, QRCode_Activity.class, SystemDrawable_Activity.class, VelocityTracker_Activity.class, ViewDragHelper_Activity.class};
//    private Class<?>[] parse_Cls = new Class<?>[]{Attrs_Activity.class, XmlResourceParser_Activity.class};
//    private Class<?>[] media_Cls = new Class<?>[]{AsyncPlayer_Activity.class, AudioRecord_Activity.class, Camera_Activity.class, MediaPlayer_Activity.class,
//            MediaRecorder_Activity.class, SoundPool_Activity.class};
//    private Class<?>[] view_Cls = new Class<?>[]{Secure_Activity.class, CustomFullView_Activity.class};
//    private Class<?>[] act_Cls = new Class<?>[]{Activity_Activity.class, WallpaperActivity_Activity.class,};
//    private Class<?>[] fragmentCls = new Class<?>[]{DialogFragment_Activity.class, Fragment_Activity.class};
//    private Class<?>[] graphiceCls = new Class<?>[]{Animation_Activity.class, Arcs_Activity.class, Movie_Activity.class};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        _setDoubleBackExit(true);
//        List<Class<?>[]> mList = new ArrayList<Class<?>[]>();
//        initData(mList);
//        mELV = (ExpandableListView) findViewById(R.id.elv_list);
//        mELV.setAdapter(new ELVAdapter(this, activity_list, mList));
//    }
//
//    private final static Comparator<Class<?>> sDisplayNameComparator = new Comparator<Class<?>>() {
//        private final Collator collator = Collator.getInstance();
//
//        public int compare(Class<?> map1, Class<?> map2) {
//            return collator.compare(map1.getSimpleName(), map2.getSimpleName());
//        }
//    };
//
//    private void initData(List<Class<?>[]> mList) {
//        // item anim
//        addClassToList(mList, anim_Cls);
//        // item device
//        addClassToList(mList, device_Cls);
//        // item bitmap
//        addClassToList(mList, bitmap_Cls);
//        // item io
//        addClassToList(mList, io_Cls);
//        // item media
//        addClassToList(mList, media_Cls);
//        // item parse
//        addClassToList(mList, parse_Cls);
//        // item sensor
//        addClassToList(mList, sensor_Cls);
//        // item service
//        addClassToList(mList, service_Cls);
//        // item util
//        addClassToList(mList, util_Cls);
//        // item widget
//        addClassToList(mList, widget_Cls);
//        // item view
//        addClassToList(mList, view_Cls);
//        // item view
//        addClassToList(mList, act_Cls);
//        // item fragment
//        addClassToList(mList, fragmentCls);
//        // item graphice
//        addClassToList(mList, graphiceCls);
//    }
//
//    private void addClassToList(List<Class<?>[]> mList, Class<?>[] cls) {
//        Arrays.sort(cls, sDisplayNameComparator);
//        mList.add(cls);
//    }
//
//    private class ELVAdapter extends BaseExpandableListAdapter {
//        private List<Class<?>[]> list;
//        private Context context;
//        private String[] group;
//
//        public ELVAdapter(Context context, String[] group, List<Class<?>[]> list) {
//            this.list = list;
//            this.group = group;
//            this.context = context;
//        }
//
//        @Override
//        public Object getChild(int groupPosition, int childPosition) {
//            return groupPosition * 1000 + childPosition;
//        }
//
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            return groupPosition * 1000 + childPosition;
//        }
//
//        @Override
//        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//            TextView t = new TextView(context);
//            t.setPadding(80, 20, 0, 20);
//            t.setText(list.get(groupPosition)[childPosition].getSimpleName().substring(0, list.get(groupPosition)[childPosition].getSimpleName().indexOf("_")));
//            t.setOnClickListener(new TextView.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, list.get(groupPosition)[childPosition]);
//                    context.startActivity(intent);
//                }
//            });
//            return t;
//        }
//
//        @Override
//        public int getChildrenCount(int groupPosition) {
//            return list.get(groupPosition).length;
//        }
//
//        @Override
//        public Object getGroup(int groupPosition) {
//            return list.get(groupPosition);
//        }
//
//        @Override
//        public int getGroupCount() {
//            return list.size();
//        }
//
//        @Override
//        public long getGroupId(int groupPosition) {
//            return groupPosition;
//        }
//
//        @Override
//        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//            TextView t = new TextView(context);
//            t.setPadding(80, 20, 0, 20);
//            t.setText(group[groupPosition]);
//            return t;
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return true;
//        }
//
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) {
//            return true;
//        }
//    }
}
