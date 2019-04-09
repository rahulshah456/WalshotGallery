package Modals;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("bio")
    private String bio;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("id")
    private String id;
    @SerializedName("instagram_username")
    private String instagramUsername;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("links")
    private Links links;
    @SerializedName("location")
    private String location;
    @SerializedName("name")
    private String name;
    @SerializedName("portfolio_url")
    private String portfolioUrl;
    @SerializedName("profile_image")
    private ProfileImage profileImage;
    @SerializedName("total_collections")
    private int totalCollections;
    @SerializedName("total_likes")
    private int totalLikes;
    @SerializedName("total_photos")
    private int totalPhotos;
    @SerializedName("twitter_username")
    private String twitterUsername;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("username")
    private String username;

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public int getTotalPhotos() {
        return this.totalPhotos;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public String getTwitterUsername() {
        return this.twitterUsername;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return this.bio;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public int getTotalLikes() {
        return this.totalLikes;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public String getPortfolioUrl() {
        return this.portfolioUrl;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public ProfileImage getProfileImage() {
        return this.profileImage;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Links getLinks() {
        return this.links;
    }

    public void setTotalCollections(int totalCollections) {
        this.totalCollections = totalCollections;
    }

    public int getTotalCollections() {
        return this.totalCollections;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public String getInstagramUsername() {
        return this.instagramUsername;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
