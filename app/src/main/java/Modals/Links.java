package Modals;

import com.google.gson.annotations.SerializedName;

public class Links {
    @SerializedName("download")
    private String download;
    @SerializedName("download_location")
    private String downloadLocation;
    @SerializedName("followers")
    private String followers;
    @SerializedName("following")
    private String following;
    @SerializedName("html")
    private String html;
    @SerializedName("likes")
    private String likes;
    @SerializedName("photos")
    private String photos;
    @SerializedName("portfolio")
    private String portfolio;
    @SerializedName("related")
    private String related;
    @SerializedName("self")
    private String self;

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowers() {
        return this.followers;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getPortfolio() {
        return this.portfolio;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowing() {
        return this.following;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getSelf() {
        return this.self;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() {
        return this.html;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getPhotos() {
        return this.photos;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getLikes() {
        return this.likes;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getRelated() {
        return this.related;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDownload() {
        return this.download;
    }

    public void setDownloadLocation(String downloadLocation) {
        this.downloadLocation = downloadLocation;
    }

    public String getDownloadLocation() {
        return this.downloadLocation;
    }
}
