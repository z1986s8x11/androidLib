package org.zsx.android.api.widget;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.widget.TextView;

import com.tools.Lib_Class_ShowCodeUtil;

import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import java.io.File;

public class TextView_Activity extends _BaseActivity {
	private TextView mHtmlClickTV;
	private String html = "这里有张图片<img src='ic_launcher'/> 可以点击试试, 后面是个标签  <zsx>点击试试</zsx>后面一个是网络图片<img src='http://www.sogou.com/docs/images/soft/icon/qq.png'>";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_textview);
		initHtmlClick();
		initTextViewMoved();
		TextView linkTV = (TextView) findViewById(R.id.global_textview3);
		linkTV.setMovementMethod(LinkMovementMethod.getInstance());

		SpannableString ss = new SpannableString(
				"text4: Manually created spans. Click here to dial the phone.");
		ss.setSpan(new StyleSpan(Typeface.BOLD), 0, 30,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new URLSpan("tel:13996687614"), 31 + 6, 31 + 10,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		TextView t4 = (TextView) findViewById(R.id.global_textview4);
		t4.setText(ss);
		t4.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void initTextViewMoved() {
		TextView scrollTV = (TextView) findViewById(R.id.global_textview1);
		scrollTV.setMovementMethod(ScrollingMovementMethod.getInstance());
	}

	private void initHtmlClick() {
		mHtmlClickTV = (TextView) findViewById(R.id.global_textview2);
		Spanned span = Html.fromHtml(html, new TextView_ImageGetter(this,
				mHtmlClickTV), new TextView_TagHandler(this));
		mHtmlClickTV.setTag(span);
		mHtmlClickTV.setText(span);
		/** 必须加下面两个 才可以点击 */
		mHtmlClickTV.setClickable(true);
		mHtmlClickTV.setMovementMethod(LinkMovementMethod.getInstance());
	}
}
