package Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("links")
    @Expose
    private Links_ links;
    @SerializedName("photo_count")
    @Expose
    private Integer photoCount;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPhotoCount() {
        return this.photoCount;
    }

    public void setPhotoCount(Integer photoCount) {
        this.photoCount = photoCount;
    }

    public Links_ getLinks() {
        return this.links;
    }

    public void setLinks(Links_ links) {
        this.links = links;
    }
}
