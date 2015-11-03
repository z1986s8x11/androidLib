package org.zsx.android.api.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Toast;

import com.zsx.util.Lib_Util_Encryption;

import org.xml.sax.XMLReader;

import java.io.File;

public class TextView_TagHandler implements TagHandler {

	private Context context;
	private int startIndex = 0;
	private int stopIndex = 0;

	public TextView_TagHandler(Context context) {
		this.context = context;
	}

	@Override
	public void handleTag(boolean opening, String tag, Editable output,
			XMLReader xmlReader) {

		// 处理标签<img>
		if (tag.toLowerCase().equals("img")) {
			// System.out.println("imgimgimgimgimgimgimgimg");
			// 获取长度
			int len = output.length();
			// 获取图片地址
			ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
			String imgURL = images[0].getSource();

			// 使图片可点击并监听点击事件
			output.setSpan(new ImageClick(context, imgURL), len - 1, len,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		// 处理自定义的标签
		if (tag.toLowerCase().equals("zsx")) {
			if (opening) {
				startIndex = output.length();
			} else {
				stopIndex = output.length();
				output.setSpan(new TextLinkSpan(), startIndex, stopIndex,
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				output.setSpan(new UnderlineSpan(), startIndex, stopIndex,
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				output.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
						startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 粗体
			}
		}

	}

	private class TextLinkSpan extends ClickableSpan {

		public TextLinkSpan() {
			super();
		}

		@Override
		public void onClick(View v) {
			// 跳转某页面
			Toast.makeText(context, "截获点击事件和数据，在此处你可以做出相应的逻辑处理",
					Toast.LENGTH_LONG).show();
		}
	}

	private class ImageClick extends ClickableSpan {

		private String url;
		private Context context;

		public ImageClick(Context context, String url) {
			this.context = context;
			this.url = url;
		}

		@Override
		public void onClick(View widget) {
			if (url != null) {
				if (!url.contains("http://") && !url.contains("https://")) {
					Toast.makeText(context, "点击了图片" + url, Toast.LENGTH_SHORT)
							.show();
					return;
				}
			}
			// 将图片URL转化为本地路径，可以将图片处理类里的图片处理过程写为一个方法，方便调用
			String imageName = Lib_Util_Encryption.encodeMD5(url);
			String sdcardPath = Environment.getExternalStorageDirectory()
					.toString(); // 获取SDCARD的路径
			// 获取图片后缀名
			String[] ss = url.split("\\.");
			String ext = ss[ss.length - 1];
			// 最终图片保持的地址
			String savePath = sdcardPath + "/" + context.getPackageName() + "/"
					+ imageName + "." + ext;
			File file = new File(savePath);
			if (file.exists()) {
				// 处理点击事件，开启一个新的activity来处理显示图片
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "image/*");
				context.startActivity(intent);
			}
		}
	}
}