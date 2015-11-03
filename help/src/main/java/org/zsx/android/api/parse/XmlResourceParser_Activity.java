package org.zsx.android.api.parse;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tools.Lib_Class_ShowCodeUtil;

import org.xmlpull.v1.XmlPullParserException;
import org.zsx.android.api.R;
import org.zsx.android.api._BaseActivity;

import java.io.IOException;

public class XmlResourceParser_Activity extends _BaseActivity implements
		Button.OnClickListener {
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parse_xmlresourceparser);
		mTextView = (TextView) findViewById(R.id.global_textview1);
		TextView source = (TextView) findViewById(R.id.global_textview2);
		Button b = (Button) findViewById(R.id.global_btn1);
		b.setOnClickListener(this);
		source.setText("<?xml version='1.0' encoding='UTF-8'?>" + "\n"
				+ "<system>" + "\n"
				+ "<linux pwd='当前路径' ls='当前目录文件' cd='更改当前路径'>命令</linux>" + "\n"
				+ "<linux pwd='当前路径' awk='awk方法' find='查找'>命令</linux>" + "\n"
				+ "</system>");
	}

	@Override
	public void onClick(View v) {
		// 根据xml资源的ID获取解析资源的解析器
		XmlResourceParser x = getResources().getXml(R.xml.test);
		StringBuilder s = new StringBuilder();
		try {
			// 如果没有到文档的结尾处
			while (x.getEventType() != XmlResourceParser.END_DOCUMENT) {
				switch (x.getEventType()) {
				case XmlResourceParser.START_TAG:
					// 获取标签的签名 <System><linux>
					String tagName = x.getName();
					s.append(tagName);
					s.append("\n");
					if ("linux".equals(tagName)) {
						// 根据属性名获取属性值pwd
						String pwd = x.getAttributeValue(null, "pwd");
						s.append("\t\t" + pwd);
						// 根据属性索引获取属性值ls
						String ls = x.getAttributeValue(2);
						s.append("\n\t\t" + ls);
						// 获取文本节点的值命令
						String cd = x.nextText();// 放在前面会报错
						s.append("\n\t" + cd + "\n");
					}
					break;
				case XmlResourceParser.START_DOCUMENT:
				case XmlResourceParser.END_TAG:
				case XmlResourceParser.END_DOCUMENT:
				case XmlResourceParser.TEXT:

					break;
				}
				// 解析下一个事件
				x.next();
			}
		} catch (XmlPullParserException e) {
			// 读取xml会要求抛
			e.printStackTrace();
		} catch (IOException e) {
			// 读取文本节点的值会抛
			e.printStackTrace();
		}
		mTextView.setText(s.toString());
	}

	@Override
	protected void _showCodeInit(Lib_Class_ShowCodeUtil showCode) {
		showCode.setShowXML(R.layout.parse_xmlresourceparser, R.xml.test);
	}
}
