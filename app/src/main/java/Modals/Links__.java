package Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links__ {
    @SerializedName("download")
    @Expose
    private String download;
    @SerializedName("html")
    @Expose
    private String html;
    @SerializedName("self")
    @Expose
    private String self;

    public String getSelf() {
        return this.self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return this.html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getDownload() {
        return this.download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
}
