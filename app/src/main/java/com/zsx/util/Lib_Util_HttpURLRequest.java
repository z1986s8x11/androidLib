package com.zsx.util;

import android.text.TextUtils;

import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2015/11/5.
 */
public class Lib_Util_HttpURLRequest {
    public static int CONNECTION_TIMEOUT_INT = 10000;
    public static int READ_TIMEOUT_INT = 5000;

    public static String post(String requestUrl, Map<String, Object> map) throws IOException, Lib_Exception {
        if (map == null) {
            return post(requestUrl, "");
        }
        JSONObject json = new JSONObject();
        for (String key : map.keySet()) {
            try {
                json.put(key, map.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return post(requestUrl, json.toString(), null);
    }

    public static String post(String requestUrl, JSONObject json) throws IOException, Lib_Exception {
        if (json == null) {
            return post(requestUrl, "");
        }
        return post(requestUrl, json.toString(), null);
    }

    public static String post(String requestUrl, String param) throws IOException, Lib_Exception {
        return post(requestUrl, param, null);
    }

    private static String post(String requestUrl, String param, String cookie) throws IOException, Lib_Exception {
        if (TextUtils.isEmpty(param)) {
            param = "{}";
        }
        String result = null;
        String encoding = "utf-8";
        BufferedReader bufferReader = null;
        HttpURLConnection urlConn = null;
        try {
            if (LogUtil.DEBUG) {
                LogUtil.e("requestData params:", String.valueOf(requestUrl) + String.valueOf(param));
            }
            URL url = new URL(requestUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true); // 设置输入流采用字节流
            urlConn.setDoOutput(true); // 设置输出流采用字节流
            urlConn.setRequestMethod("POST");
            urlConn.setUseCaches(false); // 设置缓存
            byte[] data = param.getBytes(encoding);
            urlConn.setRequestProperty("Content-Type", "application/x-javascript; charset=" + encoding);
            urlConn.setRequestProperty("Content-Length", String.valueOf(data.length));
            urlConn.setRequestProperty("Charset", encoding);
            urlConn.setConnectTimeout(CONNECTION_TIMEOUT_INT);
            urlConn.setReadTimeout(READ_TIMEOUT_INT);
            urlConn.connect(); // 连接既往服务端发送消息
            DataOutputStream dop = new DataOutputStream(urlConn.getOutputStream());
            dop.write(data); // 发送参数
            dop.flush(); // 发送，清空缓存
            dop.close(); // 关闭
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 下面开始做接收工作
                bufferReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String readLine = null;
                result = "";
                while ((readLine = bufferReader.readLine()) != null) {
                    result += readLine;
                }
            } else {
                throw new Lib_Exception(urlConn.getResponseCode(), "HTTP CODE:" + urlConn.getResponseCode());
            }
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String get(String requestUrl, Map<String, Object> param) throws IOException, Lib_Exception {
        String getUrl;
        if (param == null) {
            getUrl = requestUrl;
        } else {
            String getParam = "";
            if (param.size() != 0) {
                if (!requestUrl.endsWith("?")) {
                    getParam = "?";
                }
                for (String key : param.keySet()) {
                    Object o = param.get(key);
                    getParam += key
                            + "="
                            + URLEncoder.encode(
                            String.valueOf(o == null ? "" : o),
                            "UTF-8");
                    getParam += "&";
                }
                getParam = getParam.substring(0, getParam.length() - 1);
            }
            getUrl = requestUrl + getParam;
        }
        return get(getUrl);
    }

    public static String get(String requestUrl) throws IOException, Lib_Exception {
        BufferedReader bufferReader = null;
        HttpURLConnection urlConn = null;
        String result = null;
        try {
            if (LogUtil.DEBUG) {
                LogUtil.e("requestData params:", String.valueOf(requestUrl));
            }
            URL url = new URL(requestUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(CONNECTION_TIMEOUT_INT);
            urlConn.setReadTimeout(READ_TIMEOUT_INT);
            urlConn.setRequestMethod("GET");
            urlConn.connect(); // 连接既往服务端发送消息
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 下面开始做接收工作
                bufferReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String readLine = null;
                result = "";
                while ((readLine = bufferReader.readLine()) != null) {
                    result += readLine;
                }
            } else {
                throw new Lib_Exception(urlConn.getResponseCode(), "HTTP CODE:" + urlConn.getResponseCode());
            }
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
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
            throws IOException, Lib_Exception {
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
        if (res == HttpURLConnection.HTTP_OK) {
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
