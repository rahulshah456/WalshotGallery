package Modals;

import com.google.firebase.database.Exclude;

public class FirebaseDailyShots {
    private String compressedURL;
    private String deviceName;
    private String mKey;
    private String userName;
    private String userPic;

    public FirebaseDailyShots(String compressedURL, String deviceName, String userName, String userPic) {
        this.compressedURL = compressedURL;
        this.deviceName = deviceName;
        this.userName = userName;
        this.userPic = userPic;
    }

    public String getUserPic() {
        return this.userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompressedURL() {
        return this.compressedURL;
    }

    public void setCompressedURL(String compressedURL) {
        this.compressedURL = compressedURL;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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
