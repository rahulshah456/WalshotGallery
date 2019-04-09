package Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Result {
    @SerializedName("categories")
    @Expose
    private List<Object> categories = null;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("current_user_collections")
    @Expose
    private List<Object> currentUserCollections = null;
    @SerializedName("description")
    @Expose
    private String description;
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
    private Links links;
    @SerializedName("photo_tags")
    @Expose
    private List<PhotoTag> photoTags = null;
    @SerializedName("slug")
    @Expose
    private Object slug;
    @SerializedName("sponsored")
    @Expose
    private Boolean sponsored;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
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

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Urls getUrls() {
        return this.urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Links getLinks() {
        return this.links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public List<Object> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    public Boolean getSponsored() {
        return this.sponsored;
    }

    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
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

    public List<Object> getCurrentUserCollections() {
        return this.currentUserCollections;
    }

    public void setCurrentUserCollections(List<Object> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }

    public Object getSlug() {
        return this.slug;
    }

    public void setSlug(Object slug) {
        this.slug = slug;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<PhotoTag> getPhotoTags() {
        return this.photoTags;
    }

    public void setPhotoTags(List<PhotoTag> photoTags) {
        this.photoTags = photoTags;
    }
}
