package zsx.com.test.ui.download;

/**
 * Created by Administrator on 2016/1/11.
 */
public class DownloadBean {
    private String key;
    private String url;
    private String savePath;

    public DownloadBean(String key, String url, String savePath) {
        this.key = key;
        this.url = url;
        this.savePath = savePath;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }

    public String getSavePath() {
        return savePath;
    }
}
