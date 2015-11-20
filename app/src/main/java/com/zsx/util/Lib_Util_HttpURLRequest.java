package com.zsx.util;

import android.text.TextUtils;

import com.zsx.debug.LogUtil;
import com.zsx.exception.Lib_Exception;

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
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhusx on 2015/11/5.
 */
public class Lib_Util_HttpURLRequest {
    public static int CONNECTION_TIMEOUT_INT = 10000;
    public static int READ_TIMEOUT_INT = 5000;

    public static String post(String requestUrl, Map<String, Object> map) throws IOException, Lib_Exception {
        if (map == null || map.size() == 0) {
            return post(requestUrl, "", true);
        }
        StringBuffer sb = new StringBuffer();
        for (String key : map.keySet()) {
            sb.append("&");
            sb.append(key);
            sb.append("=");
            sb.append(URLEncoder.encode(map.get(key) == null ? "" : String.valueOf(map.get(key)), "utf-8"));
        }
        sb.deleteCharAt(0);
        return post(requestUrl, sb.toString(), true);
    }

    public static String post(String requestUrl, String textString) throws IOException, Lib_Exception {
        if (TextUtils.isEmpty(textString)) {
            return post(requestUrl, "", false);
        }
        return post(requestUrl, textString, false);
    }

    private static String post(String requestUrl, String param, boolean isUrlEncoded) throws IOException, Lib_Exception {
        if (param == null) {
            param = "";
        }
        String result = null;
        String encoding = "UTF-8";
        InputStreamReader bufferReader = null;
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
            //urlConn.setInstanceFollowRedirects(true);//是否连接遵循重定向
            //Content-Type: application/x-www-form-urlencoded   默认的提交方式，同GET类似，将参数组装成Key-value方式，用&分隔，但数据存放在body中提交
            //Content-Type: multipart/form-data                 这种方式一般用来上传文件，或大批量数据时。
            if (isUrlEncoded) {
                urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=" + encoding);
            } else {
                urlConn.setRequestProperty("Content-Type", "text/plain; charset=" + encoding);
            }
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
                bufferReader = new InputStreamReader(urlConn.getInputStream());
                StringBuffer sb = new StringBuffer();
                char[] chars = new char[128];
                int length;
                while ((length = bufferReader.read(chars)) != -1) {
                    sb.append(chars, 0, length);
                }
                result = sb.toString();
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
        InputStreamReader bufferReader = null;
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
                bufferReader = new InputStreamReader(urlConn.getInputStream());
                StringBuffer sb = new StringBuffer();
                char[] chars = new char[128];
                int length;
                while ((length = bufferReader.read(chars)) != -1) {
                    sb.append(chars, 0, length);
                }
                result = sb.toString();
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
     * @param requestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file, String requestURL)
            throws IOException, Lib_Exception {
        return uploadFile(file, requestURL, null);
    }

    /**
     * 上传文件到服务器
     *
     * @param file       需要上传的文件
     * @param requestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(File file, String requestURL, OnProgressListener listener)
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
        URL url = new URL(requestURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(READ_TIMEOUT_INT);
        conn.setConnectTimeout(CONNECTION_TIMEOUT_INT);
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
        InputStream input = null;
        FileOutputStream fos = null;
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
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
            if (file.exists() && Math.abs(System.currentTimeMillis() - file.lastModified()) < 10 * 60 * 1000) {
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
                    raf.write(b, 0, count);
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

    public interface OnProgressListener {
        void onProgress(int progress, int currentSize, int totalSize);

        boolean isCanceled();
    }
}
