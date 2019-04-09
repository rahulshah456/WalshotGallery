package Modals;

import java.util.ArrayList;

public class VideosFolder {
    String allFolderName;
    ArrayList<String> allVideoDateTaken;
    ArrayList<Integer> allVideoHeight;
    ArrayList<String> allVideoPaths;
    ArrayList<Integer> allVideoSize;
    ArrayList<String> allVideoThumbnails;
    ArrayList<Integer> allVideoWidth;

    public ArrayList<String> getAllVideoThumbnails() {
        return this.allVideoThumbnails;
    }

    public void setAllVideoThumbnails(ArrayList<String> allVideoThumbnails) {
        this.allVideoThumbnails = allVideoThumbnails;
    }

    public String getAllFolderName() {
        return this.allFolderName;
    }

    public void setAllFolderName(String allFolderName) {
        this.allFolderName = allFolderName;
    }

    public ArrayList<String> getAllVideoPaths() {
        return this.allVideoPaths;
    }

    public void setAllVideoPaths(ArrayList<String> allVideoPaths) {
        this.allVideoPaths = allVideoPaths;
    }

    public ArrayList<Integer> getAllVideoSize() {
        return this.allVideoSize;
    }

    public void setAllVideoSize(ArrayList<Integer> allVideoSize) {
        this.allVideoSize = allVideoSize;
    }

    public ArrayList<Integer> getAllVideoWidth() {
        return this.allVideoWidth;
    }

    public void setAllVideoWidth(ArrayList<Integer> allVideoWidth) {
        this.allVideoWidth = allVideoWidth;
    }

    public ArrayList<Integer> getAllVideoHeight() {
        return this.allVideoHeight;
    }

    public void setAllVideoHeight(ArrayList<Integer> allVideoHeight) {
        this.allVideoHeight = allVideoHeight;
    }

    public ArrayList<String> getAllVideoDateTaken() {
        return this.allVideoDateTaken;
    }

    public void setAllVideoDateTaken(ArrayList<String> allVideoDateTaken) {
        this.allVideoDateTaken = allVideoDateTaken;
    }
}
