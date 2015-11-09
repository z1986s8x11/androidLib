package zsx.com.test.ui.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.zsx.exception.Lib_Exception;
import com.zsx.util.Lib_Util_HttpRequest;
import com.zsx.util.Lib_Util_HttpURLRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import zsx.com.test.R;

public class HttpUrlConnectActivity extends Activity implements View.OnClickListener {
    private TextView messageTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_httpurlconnect);
        messageTV = (TextView) findViewById(R.id.tv_message);
        messageTV.setMovementMethod(ScrollingMovementMethod.getInstance());
        messageTV.setFocusable(true);
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);
        findViewById(R.id.btn_client_get).setOnClickListener(this);
        findViewById(R.id.btn_client_post).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                httpGet(messageTV);
                break;
            case R.id.btn_post:
                httpPost(messageTV);
                break;
            case R.id.btn_client_get:
                httpClientGet(messageTV);
                break;
            case R.id.btn_client_post:
                httpClientPost(messageTV);
                break;
        }
    }

    public static void httpPost(final TextView textView) {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                Map<String, Object> map = new HashMap<String, Object>();
                try {
                    map.put("token", "0cb854d39d540aa3bfc4996a7466c7c9");
                    map.put("number", "1");
                    map.put("goods_id", "5503");
                    return Lib_Util_HttpURLRequest.post("http://3.cqgod.sinaapp.com/tanmao.php?name=zhusixiang", map);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Lib_Exception e) {
                    e.printStackTrace();
                }
                return "null";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                textView.setText(result);
            }
        }.execute();
    }

    public static void httpGet(final TextView textView) {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return Lib_Util_HttpURLRequest.get("api.m.qu.cn/v1/orders/payments");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Lib_Exception e) {
                    e.printStackTrace();
                }
                return "null";

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                textView.setText(result);
            }
        }.execute();
    }

    public static void httpClientPost(final TextView textView) {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
//                    Map<String, Object> map = new HashMap<String, Object>();
//                    map.put("token", "0cb854d39d540aa3bfc4996a7466c7c9");
//                    map.put("number", "1");
//                    map.put("goods_id", "5503");
                    JSONObject map = new JSONObject();
                    map.put("token", "0cb854d39d540aa3bfc4996a7466c7c9");
                    map.put("number", "1");
                    map.put("goods_id", "5503");
                    return Lib_Util_HttpRequest._post("http://3.cqgod.sinaapp.com/tanmao.php?name=zhusixiang", map.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Lib_Exception e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "null";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                textView.setText(result);
            }
        }.execute();
    }

    public static void httpClientGet(final TextView textView) {
        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return Lib_Util_HttpRequest._get("api.m.qu.cn/v1/orders/payments");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Lib_Exception e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                return "null";

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                textView.setText(result);
            }
        }.execute();
    }
}
