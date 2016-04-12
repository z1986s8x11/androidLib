package com.zsx.debug;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zsx.R;
import com.zsx.adapter.Lib_BaseAdapter;
import com.zsx.app.Lib_BaseActivity;
import com.zsx.app._PublicFragmentActivity;
import com.zsx.tools.Lib_Subscribes;

import java.util.Arrays;

/**
 * Author       zhusx
 * Email        327270607@qq.com
 * Created      2016/4/12 15:49
 */
public class P_ProjectActivity extends Lib_BaseActivity {
    private ListView mListView;
    private Lib_BaseAdapter<P_ProjectHelper> adapter;
    private Class cls = getClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_activity_project);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(adapter = new Lib_BaseAdapter<P_ProjectHelper>(this) {
            @Override
            public View getView(LayoutInflater inflater, final P_ProjectHelper bean, int position, View convertView, ViewGroup parent) {
                View[] vs = _getViewHolder(inflater, convertView, parent, R.layout.lib_list_item_1);
                _toTextView(vs[0]).setText(bean.name);
                if (bean.isDir()) {
                    vs[0].setBackgroundResource(R.color.lib_yellow);
                    _toTextView(vs[0]).setTextColor(getResources().getColor(R.color.lib_blue));
                    vs[0].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(), cls);
                            intent.putExtra(_EXTRA_String, bean.path);
                            startActivity(intent);

                        }
                    });
                } else {
                    vs[0].setBackgroundResource(R.color.lib_white);
                    _toTextView(vs[0]).setTextColor(getResources().getColor(R.color.lib_black));
                    vs[0].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(bean.path)) {
                                return;
                            }
                            try {
                                Class cls = Class.forName(bean.path);
                                Intent intent;
                                Bundle b = new Bundle();
                                if (bean.path.endsWith("Activity")) {
                                    intent = new Intent(v.getContext(), cls);
                                } else if (bean.path.endsWith("Fragment")) {
                                    intent = new Intent(v.getContext(), _PublicFragmentActivity.class);
                                    b.putSerializable(_PublicFragmentActivity._EXTRA_FRAGMENT, cls);
                                } else {
                                    return;
                                }
                                b.putString(_EXTRA_String, bean.path);
                                intent.putExtras(b);
                                startActivity(intent);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                                _showToast(bean.path + " 没有找到");
                            } catch (Exception e) {
                                e.printStackTrace();
                                _showToast("Activity 在mainfest.xml 没有注册");
                            }
                        }
                    });
                }
                return vs[0];
            }
        });
        if (getIntent() != null && getIntent().getStringExtra(_EXTRA_String) != null) {
            adapter._setItemsToUpdate(Arrays.asList(P_ProjectHelper.getInstance().get(getIntent().getStringExtra(_EXTRA_String)).list()));
        } else {
            Lib_Subscribes.subscribe(new Lib_Subscribes.Subscriber<P_ProjectHelper>() {
                @Override
                public P_ProjectHelper doInBackground() {
                    return P_ProjectHelper.getInstance()._init(P_ProjectActivity.this).get(__getFilterPackage());
                }

                @Override
                public void onComplete(P_ProjectHelper helper) {
                    adapter._setItemsToUpdate(Arrays.asList(helper.list()));
                }

                @Override
                public void onError(Throwable t) {
                    _showToast("未知错误");
                    LogUtil.e(t);
                }
            }, this);
        }
    }

    protected String __getFilterPackage() {
        return getPackageName();
    }
}
