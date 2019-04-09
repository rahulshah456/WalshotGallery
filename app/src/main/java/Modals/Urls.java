package Modals;

import com.google.gson.annotations.SerializedName;

public class Urls {
    @SerializedName("full")
    private String full;
    @SerializedName("raw")
    private String raw;
    @SerializedName("regular")
    private String regular;
    @SerializedName("small")
    private String small;
    @SerializedName("thumb")
    private String thumb;

    public void setSmall(String small) {
        this.small = small;
    }

    public String getSmall() {
        return this.small;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getRaw() {
        return this.raw;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getRegular() {
        return this.regular;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getFull() {
        return this.full;
    }
}
