package Modals;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResultsItem {
    @SerializedName("cover_photo")
    private CoverPhoto coverPhoto;
    @SerializedName("curated")
    private boolean curated;
    @SerializedName("description")
    private Object description;
    @SerializedName("featured")
    private boolean featured;
    @SerializedName("id")
    private int id;
    @SerializedName("private")
    private boolean jsonMemberPrivate;
    @SerializedName("links")
    private Links links;
    @SerializedName("preview_photos")
    private List<PreviewPhotosItem> previewPhotos;
    @SerializedName("published_at")
    private String publishedAt;
    @SerializedName("share_key")
    private String shareKey;
    @SerializedName("tags")
    private List<TagsItem> tags;
    @SerializedName("title")
    private String title;
    @SerializedName("total_photos")
    private int totalPhotos;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("user")
    private User user;

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isFeatured() {
        return this.featured;
    }

    public void setJsonMemberPrivate(boolean jsonMemberPrivate) {
        this.jsonMemberPrivate = jsonMemberPrivate;
    }

    public boolean isJsonMemberPrivate() {
        return this.jsonMemberPrivate;
    }

    public void setCoverPhoto(CoverPhoto coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public CoverPhoto getCoverPhoto() {
        return this.coverPhoto;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public int getTotalPhotos() {
        return this.totalPhotos;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

    public String getShareKey() {
        return this.shareKey;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTags(List<TagsItem> tags) {
        this.tags = tags;
    }

    public List<TagsItem> getTags() {
        return this.tags;
    }

    public void setPreviewPhotos(List<PreviewPhotosItem> previewPhotos) {
        this.previewPhotos = previewPhotos;
    }

    public List<PreviewPhotosItem> getPreviewPhotos() {
        return this.previewPhotos;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setCurated(boolean curated) {
        this.curated = curated;
    }

    public boolean isCurated() {
        return this.curated;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Links getLinks() {
        return this.links;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() {
        return this.publishedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
