package org.zsx.android.api.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zsx.util.Lib_Util_Encryption;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.XMLReader;
import org.zsx.android.api.R;
import org.zsx.android.base._BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

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

    public class TextView_TagHandler implements Html.TagHandler {

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

    public class TextView_ImageGetter implements Html.ImageGetter {
        private Context context;
        private TextView mImageTV;

        public TextView_ImageGetter(Context context, TextView imageTV) {
            this.context = context;
            this.mImageTV = imageTV;
        }

        @Override
        public Drawable getDrawable(String source) {

            if (source.contains("http://") || source.contains("https://")) {
                return getNetWorkDrawable(source);
            } else {
                return getFaceDrawable(source);
            }
        }

        private Drawable getFaceDrawable(String source) {
            Drawable faceDraw = null;
            String tempName = source;// demo传入的source是 "ic_launcher";
            try {
                Field field = R.drawable.class.getDeclaredField(tempName);
                int resourceId = Integer.parseInt(field.get(null).toString());
                faceDraw = context.getResources().getDrawable(resourceId);
                faceDraw.setBounds(0, 0, faceDraw.getIntrinsicWidth(),
                        faceDraw.getIntrinsicHeight());
            } catch (Exception e) {
                CustomDrawable drawable = new CustomDrawable(context.getResources()
                        .getDrawable(R.drawable.ic_launcher));
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                return drawable;
            }
            return faceDraw;
        }

        private Drawable getNetWorkDrawable(String source) {
            // 将source进行MD5加密并保存至本地
            String imageName = Lib_Util_Encryption.encodeMD5(source);
            // 获取图片后缀名
            String[] ss = source.split("\\.");
            String ext = ss[ss.length - 1];

            // 最终图片保持的地址
            String savePath = new File(mImageTV.getContext().getExternalCacheDir(), imageName + "." + ext).getPath();
            File file = new File(savePath);
            if (file.exists()) {
                // 如果文件已经存在，直接返回
                Drawable drawable = Drawable.createFromPath(savePath);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                return drawable;
            }

            // 不存在文件时返回默认图片，并异步加载网络图片
            Resources res = context.getResources();
            CustomDrawable drawable = new CustomDrawable(
                    res.getDrawable(R.drawable.ic_launcher));
            new ImageAsync(drawable).execute(savePath, source);
            return drawable;
        }

        private class ImageAsync extends AsyncTask<String, Integer, Drawable> {

            private CustomDrawable drawable;

            public ImageAsync(CustomDrawable drawable) {
                this.drawable = drawable;
            }

            @Override
            protected Drawable doInBackground(String... params) {
                String savePath = params[0];
                String url = params[1];

                InputStream in = null;
                try {
                    // 获取网络图片
                    HttpGet http = new HttpGet(url);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response = (HttpResponse) client.execute(http);
                    BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
                            response.getEntity());
                    in = bufferedHttpEntity.getContent();

                } catch (Exception e) {
                    try {
                        if (in != null)
                            in.close();
                    } catch (Exception e2) {
                    }
                }

                if (in == null)
                    return drawable;

                try {
                    File file = new File(savePath);
                    String basePath = file.getParent();
                    File basePathFile = new File(basePath);
                    if (!basePathFile.exists()) {
                        basePathFile.mkdirs();
                    }
                    file.createNewFile();
                    FileOutputStream fileout = new FileOutputStream(file);
                    byte[] buffer = new byte[4 * 1024];
                    while (in.read(buffer) != -1) {
                        fileout.write(buffer);
                    }
                    fileout.flush();
                    fileout.close();
                    Drawable mDrawable = Drawable.createFromPath(savePath);
                    return mDrawable;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return drawable;
            }

            @Override
            protected void onPostExecute(Drawable result) {
                super.onPostExecute(result);
                if (result != null) {
                    /** 通过这里的重新设置 TextView 的文字来更新UI */
                    mImageTV.setText((Spanned) mImageTV.getTag());
                }
            }
        }

        public class CustomDrawable extends BitmapDrawable {
            private Drawable drawable;

            public CustomDrawable(Drawable defaultDraw) {
                drawable = defaultDraw;
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
            }

            @Override
            public void draw(Canvas canvas) {
                drawable.draw(canvas);
            }
        }
    }
}
