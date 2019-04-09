package Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Wallpaper implements Serializable {
    @SerializedName("categories")
    @Expose
    private List<Category> categories = new ArrayList();
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("current_user_collections")
    @Expose
    private List<Object> currentUserCollections = new ArrayList();
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("liked_by_user")
    @Expose
    private Boolean likedByUser;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("links")
    @Expose
    private Links__ links;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("urls")
    @Expose
    private Urls urls;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("width")
    @Expose
    private Integer width;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return this.location;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getLikes() {
        return this.likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Boolean getLikedByUser() {
        return this.likedByUser;
    }

    public void setLikedByUser(Boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Object> getCurrentUserCollections() {
        return this.currentUserCollections;
    }

    public void setCurrentUserCollections(List<Object> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }

    public Urls getUrls() {
        return this.urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Links__ getLinks() {
        return this.links;
    }

    public void setLinks(Links__ links) {
        this.links = links;
    }
}
