package Modals;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CoverPhoto {
    @SerializedName("categories")
    private List<Object> categories;
    @SerializedName("color")
    private String color;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("current_user_collections")
    private List<Object> currentUserCollections;
    @SerializedName("description")
    private String description;
    @SerializedName("height")
    private int height;
    @SerializedName("id")
    private String id;
    @SerializedName("liked_by_user")
    private boolean likedByUser;
    @SerializedName("likes")
    private int likes;
    @SerializedName("links")
    private Links links;
    @SerializedName("slug")
    private Object slug;
    @SerializedName("sponsored")
    private boolean sponsored;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("urls")
    private Urls urls;
    @SerializedName("user")
    private User user;
    @SerializedName("width")
    private int width;

    public void setCurrentUserCollections(List<Object> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }

    public List<Object> getCurrentUserCollections() {
        return this.currentUserCollections;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setSponsored(boolean sponsored) {
        this.sponsored = sponsored;
    }

    public boolean isSponsored() {
        return this.sponsored;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public boolean isLikedByUser() {
        return this.likedByUser;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Urls getUrls() {
        return this.urls;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Links getLinks() {
        return this.links;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    public List<Object> getCategories() {
        return this.categories;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setSlug(Object slug) {
        this.slug = slug;
    }

    public Object getSlug() {
        return this.slug;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLikes() {
        return this.likes;
    }
}
