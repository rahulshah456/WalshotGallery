package Modals;

import com.google.firebase.database.Exclude;

public class FirebaseWallpapers {
    private String compressedURL;
    private String imageCategory;
    private String mKey;
    private String orignalURL;
    private String thumbURL;

    public FirebaseWallpapers(String thumbURL, String compressedURL, String orignalURL, String imageCategory, String mKey) {
        this.thumbURL = thumbURL;
        this.compressedURL = compressedURL;
        this.orignalURL = orignalURL;
        this.imageCategory = imageCategory;
        this.mKey = mKey;
    }

    public String getThumbURL() {
        return this.thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    public String getCompressedURL() {
        return this.compressedURL;
    }

    public void setCompressedURL(String compressedURL) {
        this.compressedURL = compressedURL;
    }

    public String getOrignalURL() {
        return this.orignalURL;
    }

    public void setOrignalURL(String orignalURL) {
        this.orignalURL = orignalURL;
    }

    public String getImageCategory() {
        return this.imageCategory;
    }

    public void setImageCategory(String imageCategory) {
        this.imageCategory = imageCategory;
    }

    @Exclude
    public String getmKey() {
        return this.mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
