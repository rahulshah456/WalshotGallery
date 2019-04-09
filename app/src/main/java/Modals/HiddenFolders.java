package Modals;

public class HiddenFolders {
    private String directory;
    private boolean isImageFile;
    private String noMediaPath;

    public HiddenFolders(String noMediaPath, String directory, boolean isImageFile) {
        this.noMediaPath = noMediaPath;
        this.directory = directory;
        this.isImageFile = isImageFile;
    }

    public String getNoMediaPath() {
        return this.noMediaPath;
    }

    public void setNoMediaPath(String noMediaPath) {
        this.noMediaPath = noMediaPath;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public boolean isImageFile() {
        return this.isImageFile;
    }

    public void setImageFile(boolean imageFile) {
        this.isImageFile = imageFile;
    }
}
