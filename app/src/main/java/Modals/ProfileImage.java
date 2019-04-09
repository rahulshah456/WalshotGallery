package Modals;

import com.google.gson.annotations.SerializedName;

public class ProfileImage {
    @SerializedName("large")
    private String large;
    @SerializedName("medium")
    private String medium;
    @SerializedName("small")
    private String small;

    public void setSmall(String small) {
        this.small = small;
    }

    public String getSmall() {
        return this.small;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getLarge() {
        return this.large;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getMedium() {
        return this.medium;
    }
}
