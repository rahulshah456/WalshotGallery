package Modals;

import com.google.gson.annotations.SerializedName;

public class PreviewPhotosItem {
    @SerializedName("id")
    private int id;
    @SerializedName("urls")
    private Urls urls;

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Urls getUrls() {
        return this.urls;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
