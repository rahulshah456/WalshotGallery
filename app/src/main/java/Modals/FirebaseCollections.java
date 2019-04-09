package Modals;

import com.google.firebase.database.Exclude;

public class FirebaseCollections {
    private String collectionCategory;
    private String collectionTitle;
    private String imageUrl;
    private String mKey;
    private String totalImages;

    public FirebaseCollections(String imageUrl, String collectionTitle, String collectionCategory, String totalImages) {
        this.imageUrl = imageUrl;
        this.collectionTitle = collectionTitle;
        this.collectionCategory = collectionCategory;
        this.totalImages = totalImages;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCollectionTitle() {
        return this.collectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }

    public String getCollectionCategory() {
        return this.collectionCategory;
    }

    public void setCollectionCategory(String collectionCategory) {
        this.collectionCategory = collectionCategory;
    }

    public String getTotalImages() {
        return this.totalImages;
    }

    public void setTotalImages(String totalImages) {
        this.totalImages = totalImages;
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
