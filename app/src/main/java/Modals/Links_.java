package Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links_ {
    @SerializedName("followers")
    @Expose
    private String followers;
    @SerializedName("following")
    @Expose
    private String following;
    @SerializedName("html")
    @Expose
    private String html;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("photos")
    @Expose
    private String photos;
    @SerializedName("portfolio")
    @Expose
    private String portfolio;
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

    public String getPhotos() {
        return this.photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getLikes() {
        return this.likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getPortfolio() {
        return this.portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getFollowing() {
        return this.following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowers() {
        return this.followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }
}
