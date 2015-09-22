package com.zsx.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 处理Http网络请求
 *
 * @author zsx
 * @date 2013-12-23
 * @description post(); </br>get();
 */
public final class Lib_Util_HttpRequest {
    /**
     * 连接超时 (还未连接上服务器)
     */
    public static int CONNECTION_TIME_OUT = 15000;
    /**
     * 响应超时(连接上服务器,限制请求服务器响应时间)
     */
    public static int SO_TIME_OUT = 30000;
    /**
     * 默认的套接字缓冲区大小
     */
    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    /**
     * http请求最大并发连接数
     */
    private static final int DEFAULT_MAX_CONNECTIONS = 10;
    private static int maxConnections = DEFAULT_MAX_CONNECTIONS;
    private static boolean isSubmitUserAgent = false;
    private static String userAgent = null;

    public final static void _setSubmitUserAgent(boolean isSubmit,
                                                 Context appContext) {
        if (isSubmit) {
            PackageInfo info = null;
            try {
                info = appContext.getPackageManager().getPackageInfo(
                        appContext.getPackageName(), 0);
            } catch (NameNotFoundException e) {
                e.printStackTrace(System.err);
            }
            if (Lib_Util_System.isPermisson(appContext,
                    android.Manifest.permission.READ_PHONE_STATE)) {
                TelephonyManager tm = (TelephonyManager) appContext
                        .getSystemService(Context.TELEPHONY_SERVICE);
                StringBuilder ua = new StringBuilder("rmjk");
                ua.append('/' + info.versionName + '_' + info.versionCode);// App版本
                ua.append("/Android");// 手机系统平台
                ua.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
                ua.append("/" + android.os.Build.MODEL); // 手机型号
                // String ANDROID_ID =
                // Settings.System.getString(getContentResolver(),
                // Settings.System.ANDROID_ID);
                // String SerialNumber = android.os.Build.SERIAL;
                ua.append("/" + tm.getDeviceId());// 客户端唯一标识
                userAgent = ua.toString();
            }
        }
        isSubmitUserAgent = isSubmit;
    }

    private static HttpClient getHttpClient() {
        BasicHttpParams httpParams = new BasicHttpParams();

        ConnManagerParams.setTimeout(httpParams, SO_TIME_OUT);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams,
                new ConnPerRouteBean(maxConnections));
        ConnManagerParams.setMaxTotalConnections(httpParams,
                DEFAULT_MAX_CONNECTIONS);

        HttpConnectionParams.setConnectionTimeout(httpParams,
                CONNECTION_TIME_OUT);
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIME_OUT);
        // HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams,
                DEFAULT_SOCKET_BUFFER_SIZE);

        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory
                .getSocketFactory(), 443));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
                httpParams, schemeRegistry);
        DefaultHttpClient client = new DefaultHttpClient(cm, httpParams);
        /** request拦截器 */
        // client.addRequestInterceptor(new HttpRequestInterceptor() {
        // @Override
        // public void process(HttpRequest request, HttpContext context) {
        // if (!request.containsHeader("Accept-Encoding")) {
        // request.addHeader("Accept-Encoding", "gzip");
        // }
        // }
        // });
        /** response拦截器 */
        // client.addResponseInterceptor(new HttpResponseInterceptor() {
        // @Override
        // public void process(HttpResponse response, HttpContext context) {
        // }
        // });
        /** 请求重试处理 */
        // HttpRequestRetryHandler myRetryHandler = new
        // HttpRequestRetryHandler() {
        // public boolean retryRequest(IOException exception,
        // int executionCount, HttpContext context) {
        // if (executionCount >= 5) {
        // // 如果超过最大重试次数，那么就不要继续了
        // return false;
        // }
        // if (exception instanceof NoHttpResponseException) {
        // // 如果服务器丢掉了连接，那么就重试
        // return true;
        // }
        // if (exception instanceof SSLHandshakeException) {
        // // 不要重试SSL握手异常
        // return false;
        // }
        // HttpRequest request = (HttpRequest) context
        // .getAttribute(ExecutionContext.HTTP_REQUEST);
        // boolean idempotent = !(request instanceof
        // HttpEntityEnclosingRequest);
        // if (idempotent) {
        // // 如果请求被认为是幂等的，那么就重试
        // return true;
        // }
        // return false;
        // }
        // };
        // client.setHttpRequestRetryHandler(myRetryHandler);
        return client;
    }

    public final static String _post(String url)
            throws ConnectTimeoutException, SocketTimeoutException,
            ClientProtocolException, IOException, URISyntaxException,
            Lib_Exception {
        Map<String, Object> m = null;
        return _post(url, m);
    }

    public final static String _post(String url,
                                     Map<String, Object> parameters) throws ConnectTimeoutException,
            SocketTimeoutException, ClientProtocolException, IOException,
            URISyntaxException, Lib_Exception {
        String returnStr = null;
        HttpPost post = new HttpPost(url);
        if (isSubmitUserAgent) {
            post.addHeader("User-Agent", userAgent);
            // HttpPost.setRequestHeader("Host", URLs.HOST);
            // HttpPost.setRequestHeader("Connection","Keep-Alive");
            // HttpPost.setRequestHeader("Cookie", cookie);
            // HttpPost.setRequestHeader("User-Agent", userAgent);
        }
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (parameters != null) {
            Iterator<String> keys = parameters.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next().toString();
                Object value = parameters.get(key);
                NameValuePair nvp = new BasicNameValuePair(key,
                        value == null ? "" : String.valueOf(value));
                list.add(nvp);
            }
        }
        if (list.size() != 0) {
            HttpEntity entity = new UrlEncodedFormEntity(list, HTTP.UTF_8);
            post.setEntity(entity);
        }
        HttpClient client = getHttpClient();
        HttpResponse response = client.execute(post);
        returnStr = responseToString(response);
        client.getConnectionManager().shutdown();
        return returnStr;
    }

    public static final String _post(String url, JSONObject parameters)
            throws ClientProtocolException, SocketTimeoutException,
            IOException, URISyntaxException, Lib_Exception {
        return _post(url, parameters == null ? null : parameters.toString());
    }

    public static final String _post(String url, String parameters)
            throws ClientProtocolException, SocketTimeoutException,
            IOException, URISyntaxException, Lib_Exception {
        String returnStr = null;
        HttpPost post = new HttpPost(url);
        if (parameters != null) {
            post.setEntity(new StringEntity(parameters, HTTP.UTF_8));
        }
        HttpClient client = getHttpClient();
        HttpResponse response = client.execute(post);
        returnStr = responseToString(response);
        client.getConnectionManager().shutdown();
        return returnStr;
    }

    private static final String responseToString(HttpResponse response)
            throws ParseException, IOException, Lib_Exception {
        String returnStr = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            if (response != null) {
                returnStr = EntityUtils.toString(response.getEntity(),
                        HTTP.UTF_8);
            }
        } else {
            if (LogUtil.DEBUG) {
                LogUtil.e(Lib_Util_HttpRequest.class, "http status Code :"
                        + response.getStatusLine().getStatusCode());
            }
            throw new Lib_Exception(response.getStatusLine().getStatusCode(),
                    "Http status Code:"
                            + response.getStatusLine().getStatusCode());
        }
        return returnStr;
    }

    /**
     * HttpGet请求
     *
     * @throws IOException
     * @throws ParseException
     * @throws Lib_Exception
     */
    public static final String _get(String url) throws URISyntaxException,
            ParseException, IOException, Lib_Exception {
        URI uri = new URI(url);
        String returnStr = null;
        HttpGet get = new HttpGet(uri);
        if (isSubmitUserAgent) {
            get.addHeader("User-Agent", userAgent);
            // HttpPost.setRequestHeader("Host", URLs.HOST);
            // HttpPost.setRequestHeader("Connection","Keep-Alive");
            // HttpPost.setRequestHeader("Cookie", cookie);
            // HttpPost.setRequestHeader("User-Agent", userAgent);
        }
        HttpClient client = getHttpClient();
        HttpResponse response = client.execute(get);
        returnStr = responseToString(response);
        client.getConnectionManager().shutdown();
        return returnStr;
    }

    /**
     * 上传文件到服务器
     *
     * @param file       需要上传的文件
     * @param RequestURL 请求的rul
     * @return 返回响应的内容
     * @throws ProtocolException
     * @throws FileNotFoundException
     * @throws MalformedURLException
     * @throws Lib_Exception
     */
    public static String uploadFile(File file, String RequestURL)
            throws ProtocolException, FileNotFoundException,
            MalformedURLException, IOException, Lib_Exception {
        if (!file.exists()) {
            throw new FileNotFoundException("File Not Found Exception!!");
        }
        String contentType = "image/jpeg";// 流  application/octet-stream
        int res = 0;
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String Charset = "UTF-8";
        URL url = new URL(RequestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(20000);
        conn.setConnectTimeout(60000);
        conn.setDoInput(true); // 允许输入流
        conn.setDoOutput(true); // 允许输出流
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST"); // 请求方式
        conn.setRequestProperty("Charset", Charset); // 设置编码
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                + BOUNDARY);
        /**
         * 当文件不为空时执行上传
         */
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        StringBuffer sb = new StringBuffer();
        sb.append(PREFIX);
        sb.append(BOUNDARY);
        sb.append(LINE_END);
        /**
         * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 filename是文件的名字，包含后缀名
         */
        sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                + file.getName() + "\"" + LINE_END);
        sb.append("Content-Type: " + contentType + "; charset=" + Charset
                + LINE_END);
        sb.append(LINE_END);
        dos.write(sb.toString().getBytes());
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = is.read(bytes)) != -1) {
            dos.write(bytes, 0, len);
        }
        is.close();
        dos.write(LINE_END.getBytes());
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
        dos.write(end_data);
        dos.flush();
        /**
         * 获取响应码 200=成功 当响应成功，获取响应的流
         */
        res = conn.getResponseCode();
        if (res == 200) {
            InputStream input = conn.getInputStream();
            StringBuffer sb1 = new StringBuffer();
            int ss;
            while ((ss = input.read()) != -1) {
                sb1.append((char) ss);
            }
            result = sb1.toString();
        } else {
            throw new Lib_Exception(res, "HTTP CODE:" + res);
        }
        return result;
    }

}
