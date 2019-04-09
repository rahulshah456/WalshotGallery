package Modals;

import java.util.ArrayList;

public class ImagesFolder {
    String allFolderName;
    ArrayList<String> allImageDateModified;
    ArrayList<String> allImageDateTaken;
    ArrayList<Integer> allImageHeight;
    ArrayList<Integer> allImageLatitude;
    ArrayList<Integer> allImageLongtitude;
    ArrayList<String> allImageMimeType;
    ArrayList<String> allImageOrientation;
    ArrayList<String> allImagePaths;
    ArrayList<Integer> allImageSize;
    ArrayList<Integer> allImageWidth;

    public ArrayList<Integer> getAllImageSize() {
        return this.allImageSize;
    }

    public void setAllImageSize(ArrayList<Integer> allImageSize) {
        this.allImageSize = allImageSize;
    }

    public ArrayList<String> getAllImageMimeType() {
        return this.allImageMimeType;
    }

    public void setAllImageMimeType(ArrayList<String> allImageMimeType) {
        this.allImageMimeType = allImageMimeType;
    }

    public ArrayList<Integer> getAllImageWidth() {
        return this.allImageWidth;
    }

    public void setAllImageWidth(ArrayList<Integer> allImageWidth) {
        this.allImageWidth = allImageWidth;
    }

    public ArrayList<Integer> getAllImageHeight() {
        return this.allImageHeight;
    }

    public void setAllImageHeight(ArrayList<Integer> allImageHeight) {
        this.allImageHeight = allImageHeight;
    }

    public ArrayList<Integer> getAllImageLatitude() {
        return this.allImageLatitude;
    }

    public void setAllImageLatitude(ArrayList<Integer> allImageLatitude) {
        this.allImageLatitude = allImageLatitude;
    }

    public ArrayList<Integer> getAllImageLongtitude() {
        return this.allImageLongtitude;
    }

    public void setAllImageLongtitude(ArrayList<Integer> allImageLongtitude) {
        this.allImageLongtitude = allImageLongtitude;
    }

    public ArrayList<String> getAllImageDateTaken() {
        return this.allImageDateTaken;
    }

    public void setAllImageDateTaken(ArrayList<String> allImageDateTaken) {
        this.allImageDateTaken = allImageDateTaken;
    }

    public ArrayList<String> getAllImageDateModified() {
        return this.allImageDateModified;
    }

    public void setAllImageDateModified(ArrayList<String> allImageDateModified) {
        this.allImageDateModified = allImageDateModified;
    }

    public ArrayList<String> getAllImageOrientation() {
        return this.allImageOrientation;
    }

    public void setAllImageOrientation(ArrayList<String> allImageOrientation) {
        this.allImageOrientation = allImageOrientation;
    }

    public String getAllFolderName() {
        return this.allFolderName;
    }

    public void setAllFolderName(String allFolderName) {
        this.allFolderName = allFolderName;
    }

    public ArrayList<String> getAllImagePaths() {
        return this.allImagePaths;
    }

    public void setAllImagePaths(ArrayList<String> allImagePaths) {
        this.allImagePaths = allImagePaths;
    }
}
