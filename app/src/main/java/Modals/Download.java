package Modals;

import java.text.DecimalFormat;

public class Download {
    Double currentFileSize;
    DecimalFormat df = new DecimalFormat("0.0");
    int downloadProgress;
    Double totalFileSize;

    public int getDownloadProgress() {
        return this.downloadProgress;
    }

    public void setDownloadProgress(int downloadProgress) {
        this.downloadProgress = downloadProgress;
    }

    public String getCurrentFileSize() {
        return this.df.format(this.currentFileSize);
    }

    public void setCurrentFileSize(Double currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public String getTotalFileSize() {
        return this.df.format(this.totalFileSize);
    }

    public void setTotalFileSize(Double totalFileSize) {
        this.totalFileSize = totalFileSize;
    }
}
