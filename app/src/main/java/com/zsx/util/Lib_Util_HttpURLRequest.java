package com.zsx.util;

import android.text.TextUtils;

import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;
import com.zsx.network.Lib_HttpParams;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * 100 继续
 * 101 分组交换协
 * 200 OK
 * 201 被创建
 * 202 被采纳
 * 203 非授权信息
 * 204 无内容
 * 205 重置内容
 * 206 部分内容
 * 300 多选项
 * 301 永久地传送
 * 302 找到
 * 303 参见其他
 * 304 未改动
 * 305 使用代理
 * 307 暂时重定向
 * 400 错误请求
 * 401 未授权
 * 402 要求付费
 * 403 禁止
 * 404 未找到
 * 405 不允许的方法
 * 406 不被采纳
 * 407 要求代理授权
 * 408 请求超时
 * 409 冲突
 * 410 过期的
 * 411 要求的长度
 * 412 前提不成立
 * 413 请求实例太大
 * 414 请求URI太大
 * 415 不支持的媒体类型
 * 416 无法满足的请求范围
 * 417 失败的预期
 * 500 内部服务器错误
 * 501 未被使用
 * 502 网关错误
 * 503 不可用的服务
 * 504 网关超时
 * 505 HTTP版本未被支持
 * <p/>
 * Created by zhusx on 2015/11/5.
 */
public class Lib_Util_HttpURLRequest {
    public static int CONNECTION_TIMEOUT_INT = 10000;
    public static int READ_TIMEOUT_INT = 5000;

    public static String post(String requestUrl, Map<String, Object> map) throws IOException, Lib_Exception {
        return httpRequest(Lib_HttpParams.POST, requestUrl, map, null, false);
    }

    public static String post(String requestUrl, String textString) throws IOException, Lib_Exception {
        return httpRequest(Lib_HttpParams.POST, requestUrl, textString, "application/json", null, false);
    }

    public static String get(String requestUrl, Map<String, Object> param) throws IOException, Lib_Exception {
        String getUrl;
        if (param == null) {
            getUrl = requestUrl;
        } else {
            getUrl = encodeUrl(requestUrl, param);
        }
        return get(getUrl);
    }

    public static String get(String requestUrl) throws IOException, Lib_Exception {
        return httpRequest(Lib_HttpParams.GET, requestUrl, null, null, null, false);
    }

    public static String httpRequest(String requestMethod, String requestUrl, Map<String, Object> map, Map<String, ?> requestPropertys, boolean isReadHttpCodeError) throws IOException, Lib_Exception {
        StringBuffer sb = new StringBuffer();
        if (map != null && !map.isEmpty()) {
            for (String key : map.keySet()) {
                sb.append("&");
                sb.append(key);
                sb.append("=");
                sb.append(URLEncoder.encode(map.get(key) == null ? "" : String.valueOf(map.get(key)), "utf-8"));
            }
            sb.deleteCharAt(0);
        }
        return httpRequest(requestMethod, requestUrl, sb.toString(), null, requestPropertys, isReadHttpCodeError);
    }

    public static String httpRequest(String requestMethod, String requestUrl, String param, Map<String, ?> requestPropertys, boolean isReadHttpCodeError) throws IOException, Lib_Exception {
        return httpRequest(requestMethod, requestUrl, param, "application/json", requestPropertys, isReadHttpCodeError);
    }

    public static String httpRequest(String requestMethod, String requestUrl, String param, Map<String, ?> requestPropertys) throws IOException, Lib_Exception {
        return httpRequest(requestMethod, requestUrl, param, "application/json", requestPropertys, false);
    }

    public static String httpRequest(String requestMethod, String requestUrl, String param, String contentType, Map<String, ?> requestPropertys, boolean isReadHttpCodeError) throws IOException, Lib_Exception {
        String result = null;
        String encoding = "UTF-8";
        InputStreamReader bufferReader = null;
        HttpURLConnection urlConn = null;
        if (param == null) {
            param = "";
        }
        try {
            if (LogUtil.DEBUG) {
                LogUtil.e("requestData params:", String.valueOf(requestUrl) + "[" + String.valueOf(param) + "]");
            }
            URL url = new URL(requestUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            switch (requestMethod) {
                case Lib_HttpParams.GET:
                    urlConn.setRequestMethod("GET");
                    break;
                case Lib_HttpParams.POST:
                    urlConn.setDoInput(true); // 设置输入流采用字节流
                    urlConn.setDoOutput(true); // 设置输出流采用字节流
                    urlConn.setUseCaches(false); // 设置缓存
                    urlConn.setRequestMethod("POST");
                    break;
                case Lib_HttpParams.PUT:
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setRequestMethod("PUT");
                    break;
                case Lib_HttpParams.DELETE:
                    urlConn.setRequestMethod("DELETE");
                    if (LogUtil.DEBUG) {
                        if (TextUtils.isEmpty(param)) {
                            LogUtil.e(Lib_Util_HttpURLRequest.class, "DELETE 不支持提交参数");
                        }
                    }
                    param = "";//DELETE 不支持提交参数
                    break;
                default:
                    throw new Lib_Exception("no requestMethod: " + String.valueOf(requestMethod));
            }
            byte[] data = param.getBytes(encoding);
            //urlConn.setInstanceFollowRedirects(true);//是否连接遵循重定向
            //Content-Type: application/x-www-form-urlencoded   默认的提交方式，同GET类似，将参数组装成Key-value方式，用&分隔，但数据存放在body中提交
            //Content-Type: multipart/form-data                 这种方式一般用来上传文件，或大批量数据时。
            //Content-Type: text/plain                           这种方式一般用来上传字符串。
            if (!TextUtils.isEmpty(contentType)) {
                urlConn.setRequestProperty("Content-Type", contentType + "; charset=" + encoding);
            }
            urlConn.setRequestProperty("Content-Length", String.valueOf(data.length));
            urlConn.setRequestProperty("Charset", encoding);
            if (requestPropertys != null) {
                for (String key : requestPropertys.keySet()) {
                    Object value = requestPropertys.get(key);
                    urlConn.setRequestProperty(key, value == null ? "" : String.valueOf(value));
                }
            }
            urlConn.setConnectTimeout(CONNECTION_TIMEOUT_INT);
            urlConn.setReadTimeout(READ_TIMEOUT_INT);
            urlConn.connect(); // 连接既往服务端发送消息
            if (data.length > 0) {
                DataOutputStream dop = new DataOutputStream(urlConn.getOutputStream());
                dop.write(data); // 发送参数
                dop.flush(); // 发送，清空缓存
                dop.close(); // 关闭
            }
            if (urlConn.getResponseCode() >= HttpURLConnection.HTTP_OK && urlConn.getResponseCode() < HttpURLConnection.HTTP_MULT_CHOICE) {
                // 下面开始做接收工作
                bufferReader = new InputStreamReader(urlConn.getInputStream());
                StringBuffer sb = new StringBuffer();
                char[] chars = new char[128];
                int length;
                while ((length = bufferReader.read(chars)) != -1) {
                    sb.append(chars, 0, length);
                }
                result = sb.toString();
                if (LogUtil.DEBUG) {
                    LogUtil.e("requestData result:", String.valueOf(result));
                }
            } else {
                if (LogUtil.DEBUG) {
                    LogUtil.e("requestData result:", "HTTP CODE:" + urlConn.getResponseCode());
                }
                result = "HTTP CODE:" + urlConn.getResponseCode();
                if (isReadHttpCodeError) {
                    // 下面开始做接收工作
                    InputStream in = urlConn.getErrorStream();
                    if (in != null && in.available() >= 0) {
                        bufferReader = new InputStreamReader(in);
                        StringBuffer sb = new StringBuffer();
                        char[] chars = new char[128];
                        int length;
                        while ((length = bufferReader.read(chars)) != -1) {
                            sb.append(chars, 0, length);
                        }
                        result = sb.toString();
                        if (LogUtil.DEBUG) {
                            LogUtil.e("requestData result:", String.valueOf(result));
                        }
                    } else {
                        if (in != null) {
                            in.close();
                        }
                    }
                }
                throw new Lib_Exception(urlConn.getResponseCode(), result);
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

    public static String uploadFile(File file, String requestURL)
            throws IOException, Lib_Exception {
        return uploadFile(file, requestURL, null, null);
    }

    public static String uploadFile(File file, String requestURL, OnProgressListener listener)
            throws IOException, Lib_Exception {
        return uploadFile(file, requestURL, null, listener);
    }

    public static String uploadFile(File file, String requestURL, Map<String, ?> requestPropertys, OnProgressListener listener)
            throws IOException, Lib_Exception {
        return uploadFile(file, requestURL, null, requestPropertys, listener);
    }

    /**
     * 上传文件到服务器
     *
     * @param file       需要上传的文件
     * @param requestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file, String requestURL, String contentType, Map<String, ?> requestPropertys, OnProgressListener listener)
            throws IOException, Lib_Exception {
        if (!file.exists()) {
            throw new FileNotFoundException("File Not Found Exception!!");
        }
        if (contentType == null) {
            contentType = "image/jpeg";// 流  application/octet-stream
        }
        int res = 0;
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String Charset = "UTF-8";
        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(READ_TIMEOUT_INT);
        conn.setConnectTimeout(CONNECTION_TIMEOUT_INT);
        conn.setDoInput(true); // 允许输入流
        conn.setDoOutput(true); // 允许输出流
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST"); // 请求方式
        if (requestPropertys != null) {
            for (String key : requestPropertys.keySet()) {
                Object value = requestPropertys.get(key);
                conn.setRequestProperty(key, value == null ? "" : String.valueOf(value));
            }
        }
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
        FileInputStream is = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int totalByte = is.available();
        int len;
        int num = 0;
        int progress = 0;
        while ((len = is.read(bytes)) != -1) {
            dos.write(bytes, 0, len);
            if (listener != null) {
                if (listener.isCanceled()) {
                    throw new Lib_Exception("取消上传");
                }
                num += len;
                int current_progress = totalByte > 0 ? (int) ((float) num / totalByte * 100) : 0;
                if (progress != current_progress) {
                    progress = current_progress;
                    listener.onProgress(progress, num, totalByte);
                }
            }
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

    public static boolean downloadFile(String url, String savePath) throws Lib_Exception, IOException {
        return downloadFile(url, savePath, null);
    }

    public static boolean downloadFile(String url, String savePath, OnProgressListener listener) throws Lib_Exception, IOException {
        return downloadFile(url, savePath, listener, 10 * 60 * 1000);
    }

    public static boolean downloadFile(String url, String savePath, OnProgressListener listener, int cacheTime) throws Lib_Exception, IOException {
        InputStream input = null;
        FileOutputStream fos = null;
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        if (listener != null) {
            if (listener.isCanceled()) {
                throw new Lib_Exception(Lib_Exception.ERROR_CODE_CANCEL, "取消下载");
            }
        }
        try {
            File file = new File(savePath);
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    if (LogUtil.DEBUG) {
                        LogUtil.e(Lib_Util_HttpURLRequest.class, "创建文件夹失败:"
                                + file.getParentFile().getPath()
                                + "\n检查是否加入android.permission.WRITE_EXTERNAL_STORAGE和android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
                    }
                    throw new Lib_Exception("创建文件夹失败");
                }
            }
            if (file.exists() && Math.abs(System.currentTimeMillis() - file.lastModified()) < cacheTime) {
                return true;
            }
            File fileTemp = new File(file.getPath() + ".tmp");
            int totalByte = 0;
            int progress = -1;
            conn = (HttpURLConnection) new URL(url).openConnection();
            // 设置超时
            conn.setConnectTimeout(CONNECTION_TIMEOUT_INT);
            // 读取超时 一般不设置
            // conn.setReadTimeout(30000);
            conn.setRequestMethod("GET");
//            // 设置续传开始
            long start = 0;
            if (fileTemp.exists() && Math.abs(System.currentTimeMillis() - fileTemp.lastModified()) < 10 * 60 * 1000) {
                raf = new RandomAccessFile(fileTemp, "rw");
                start = raf.length();
                conn.setRequestProperty("Range", "bytes=" + start + "-");
                if (LogUtil.DEBUG) {
                    LogUtil.e(Lib_Util_HttpURLRequest.class, "start Range:" + start);
                }
            }
            // 设置方法为 GET
            if (listener != null) {
                if (listener.isCanceled()) {
                    throw new Lib_Exception(Lib_Exception.ERROR_CODE_CANCEL, "取消下载");
                }
            }
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (fileTemp.exists()) {
                    if (!fileTemp.delete()) {
                        if (LogUtil.DEBUG) {
                            LogUtil.e(Lib_Util_HttpURLRequest.class, "删除文件失败:" + fileTemp.getPath());
                        }
                        throw new Lib_Exception("删除文件失败");
                    }
                }
                fos = new FileOutputStream(fileTemp);
                String contentLength = conn.getHeaderField("Content-Length");
                if (contentLength == null) {
                    if (LogUtil.DEBUG) {
                        LogUtil.e(Lib_Util_HttpURLRequest.class, "Content-Length 文件大小获取失败");
                    }
                } else {
                    totalByte = Integer.parseInt(contentLength);
                }
                input = conn.getInputStream();
                int count = 0;
                int num = 0;
                byte[] b = new byte[1024 * 2];
                while ((count = input.read(b)) != -1) {
                    fos.write(b, 0, count);
                    if (listener != null) {
                        if (listener.isCanceled()) {
                            throw new Lib_Exception(Lib_Exception.ERROR_CODE_CANCEL, "取消下载");
                        }
                        num += count;
                        int current_progress = totalByte > 0 ? (int) ((float) num / totalByte * 100) : 0;
                        if (progress != current_progress) {
                            progress = current_progress;
                            listener.onProgress(progress, num, totalByte);
                        }
                    }
                }
                input.close();
                fos.close();
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                fileTemp.renameTo(file);
                return true;
            } else if (conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                if (LogUtil.DEBUG) {
                    LogUtil.e(Lib_Util_HttpURLRequest.class, "服务器返回 206");
                }
                raf.seek(start);
                String contentLength = conn.getHeaderField("Content-Length");
                if (contentLength == null) {
                    if (LogUtil.DEBUG) {
                        LogUtil.e(Lib_Util_HttpURLRequest.class, "Content-Length 文件大小获取失败");
                    }
                } else {
                    //contentLength 是剩余下载大小的长度
                    totalByte = Integer.parseInt(contentLength) + (int) start;
                }
                input = conn.getInputStream();
                int count = 0;
                int num = (int) start;
                byte[] b = new byte[1024 * 2];
                while ((count = input.read(b)) != -1) {
                    if (listener != null) {
                        if (listener.isCanceled()) {
                            throw new Lib_Exception(Lib_Exception.ERROR_CODE_CANCEL, "取消下载");
                        }
                        raf.write(b, 0, count);
                        num += count;
                        int current_progress = totalByte > 0 ? (int) ((float) num / totalByte * 100) : 0;
                        if (progress != current_progress) {
                            progress = current_progress;
                            listener.onProgress(progress, num, totalByte);
                        }
                    } else {
                        raf.write(b, 0, count);
                    }
                }
                input.close();
                raf.close();
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                fileTemp.renameTo(file);
                return true;
            } else {
                throw new Lib_Exception(conn.getResponseCode(), "HTTP CODE:" + conn.getResponseCode());
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * [scheme:][//host:port][path][?query][#fragment]
     *
     * @param url 需要转换的url
     *            必须以http:// 开头 转换中文url为可以访问的url
     */
    public static String encodeUrl(String url, Map<String, ?> map) {
        try {
            URI uri = URI.create(url.trim());
            String host = uri.getHost();
            if (host == null) {
                return url;
            }
            if (host.length() == url.length()) {
                return url;
            }
            StringBuffer urlSb = new StringBuffer();
            if (uri.getScheme() == null) {
                urlSb.append("http://");
            } else {
                urlSb.append(uri.getScheme());
                urlSb.append("://");
            }
            urlSb.append(host);
            if (uri.getPort() != -1) {
                urlSb.append(":");
                urlSb.append(uri.getPort());
            }
            String urlPath = uri.getPath();
            String[] paths = urlPath.split("/");
            if (paths.length > 0) {
                for (int i = 0; i < paths.length; i++) {
                    urlSb.append(URLEncoder.encode(paths[i], "utf-8"));
                    if (i != paths.length - 1) {
                        urlSb.append("/");
                    }
                }
            }
            String paramArray = uri.getQuery();
            if (paramArray != null) {
                urlSb.append("?");
                String[] keyValues = paramArray.split("&");
                int index = urlSb.length();
                for (int i = 0; i < keyValues.length; i++) {
                    urlSb.append("&");
                    String[] keyValue = keyValues[i].split("=");
                    if (keyValue.length == 1) {
                        urlSb.append(URLEncoder.encode(keyValue[0], "utf-8"));
                        urlSb.append("=");
                    } else {
                        urlSb.append(URLEncoder.encode(keyValue[0], "utf-8"));
                        urlSb.append("=");
                        urlSb.append(URLEncoder.encode(keyValue[1], "utf-8"));
                    }
                }
                urlSb.deleteCharAt(index);
            }
            if (map != null && !map.isEmpty()) {
                if (TextUtils.isEmpty(paramArray)) {
                    urlSb.append("?");
                }
                int index = urlSb.length();
                for (String key : map.keySet()) {
                    urlSb.append("&");
                    urlSb.append(URLEncoder.encode(key, "utf-8"));
                    urlSb.append("=");
                    if (map.get(key) != null) {
                        urlSb.append(URLEncoder.encode(String.valueOf(map.get(key)), "utf-8"));
                    }
                }
                if (TextUtils.isEmpty(paramArray)) {
                    urlSb.deleteCharAt(index);
                }
            }
            if (uri.getFragment() != null) {
                urlSb.append("#");
                urlSb.append(uri.getFragment());
            }
            return urlSb.toString();
        } catch (Exception e) {
            if (LogUtil.DEBUG) {
                LogUtil.e(e);
            }
        }
        return url;
    }

    public interface OnProgressListener {
        void onProgress(int progress, int currentSize, int totalSize);

        boolean isCanceled();
    }
}
