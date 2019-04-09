package util;

import Modals.Download;
import Retrofit.WallpaperApi.Factory;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.adobe.creativesdk.foundation.internal.cache.AdobeCommonCacheConstants;
import com.walshotbeta.walshotvbeta.C1420R;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class FileDownloadService extends IntentService {
    public static final String INTENT_FILE_NAME = "file_name";
    public static final String INTENT_URL = "file_url";
    private static final String TAG = FileDownloadService.class.getSimpleName();
    String CHANNEL_ID = "my_channel_01";
    private Download download;
    String fileName;
    String fileURL;
    CharSequence name = "blaa";
    private Builder notificationBuilder;
    private NotificationManager notificationManager;

    public FileDownloadService() {
        super("FileDownloadService");
    }

    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            this.fileURL = bundle.getString(INTENT_URL);
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onHandleIntent: FileURL ");
            stringBuilder.append(this.fileURL);
            Log.d(str, stringBuilder.toString());
            this.fileName = bundle.getString(INTENT_FILE_NAME);
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onHandleIntent: FileName ");
            stringBuilder.append(this.fileName);
            Log.d(str, stringBuilder.toString());
        }
        this.notificationManager = (NotificationManager) getSystemService("notification");
        this.notificationBuilder = new Builder(this).setSmallIcon(C1420R.drawable.ic_file_download_black_24dp).setContentTitle(getString(C1420R.string.download_notification_title)).setContentText(getString(C1420R.string.download_notification_status_ongoing)).setAutoCancel(false);
        this.notificationManager.notify(0, this.notificationBuilder.build());
        initDownload();
    }

    private void initDownload() {
        IOException e;
        Call<ResponseBody> call;
        Call<ResponseBody> request = Factory.getInstance().downloadImage(this.fileURL);
        try {
            ResponseBody body = (ResponseBody) request.execute().body();
            body.getClass();
            long fileSize = body.contentLength();
            byte[] data = new byte[4096];
            long total = 0;
            long startTime = System.currentTimeMillis();
            int timeCount = 1;
            r1.download = new Download();
            InputStream bis = new BufferedInputStream(body.byteStream(), 8192);
            boolean directoryStatus = true;
            File path = getSavePath();
            if (!path.exists()) {
                try {
                    directoryStatus = path.mkdir();
                    if (directoryStatus) {
                        MediaScannerConnection.scanFile(r1, new String[]{path.toString()}, null, null);
                    }
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                } catch (Exception e3) {
                    request = e3;
                    request.printStackTrace();
                }
            }
            if (directoryStatus) {
                File file = new File(path, r1.fileName);
                boolean fileStatus = true;
                boolean z;
                File file2;
                if (file.exists()) {
                    ResponseBody responseBody = body;
                    InputStream request2 = bis;
                    z = directoryStatus;
                    file2 = path;
                } else {
                    int count;
                    int timeCount2;
                    InputStream bis2;
                    fileStatus = file.createNewFile();
                    body = new FileOutputStream(file);
                    while (true) {
                        File file3 = file;
                        file = bis.read(data);
                        count = file;
                        z = directoryStatus;
                        if (file == true) {
                            break;
                        }
                        file2 = path;
                        file = count;
                        total += (long) file;
                        int count2 = file;
                        ResponseBody outputStream = body;
                        timeCount2 = timeCount;
                        bis2 = bis;
                        double totalFileSize = ((double) fileSize) / Math.pow(1024.0d, 0);
                        r1.download.setTotalFileSize(Double.valueOf(totalFileSize));
                        file = (double) Math.round(((double) total) / Math.pow(1024.0d, 2.0d));
                        r1.download.setCurrentFileSize(Double.valueOf(file));
                        timeCount = (int) ((100 * total) / fileSize);
                        r1.download.setDownloadProgress(timeCount);
                        double current = file;
                        if (System.currentTimeMillis() - startTime > ((long) (1000 * timeCount2))) {
                            sendNotification(r1.download);
                            file = TAG;
                            body = new StringBuilder();
                            body.append("initDownload: Progress:- ");
                            body.append(timeCount);
                            Log.d(file, body.toString());
                            file = timeCount2 + 1;
                        } else {
                            file = timeCount2;
                        }
                        int timeCount3 = file;
                        body = outputStream;
                        body.write(data, null, count2);
                        file = file3;
                        directoryStatus = z;
                        path = file2;
                        bis = bis2;
                        timeCount = timeCount3;
                    }
                    timeCount2 = timeCount;
                    bis2 = bis;
                    file2 = path;
                    path = count;
                    body.flush();
                    body.close();
                    bis2.close();
                    Log.d(TAG, "initDownload: Download successful!");
                    onDownloadComplete();
                    timeCount = timeCount2;
                }
                if (!fileStatus) {
                    onDownloadError("Error creating file");
                }
            }
            onDownloadError("Error creating directory");
        } catch (IOException e22) {
            call = request;
            e = e22;
            e.printStackTrace();
        } catch (Exception e32) {
            call = request;
            request = e32;
            request.printStackTrace();
        }
    }

    private File getSavePath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(C1420R.string.app_name));
        return new File(stringBuilder.toString());
    }

    private void sendNotification(Download download) {
        this.notificationBuilder.setProgress(100, download.getDownloadProgress(), false);
        this.notificationBuilder.setContentText(String.format("Downloaded (%s / %s) MB", new Object[]{download.getCurrentFileSize(), download.getTotalFileSize()}));
        this.notificationManager.notify(0, this.notificationBuilder.build());
    }

    private void onDownloadComplete() {
        this.notificationManager.cancel(0);
        String localFileURI = new StringBuilder();
        localFileURI.append("file://");
        localFileURI.append(getSavePath().getPath());
        localFileURI.append("/");
        localFileURI.append(this.fileName);
        localFileURI = localFileURI.toString();
        this.notificationBuilder.setProgress(0, 0, false);
        this.notificationBuilder.setContentText("File Downloaded");
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onDownloadComplete: Adding file link to notification ");
        stringBuilder.append(localFileURI);
        Log.d(str, stringBuilder.toString());
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(localFileURI));
        intent.addFlags(1);
        intent.setDataAndType(Uri.parse(localFileURI), MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg"));
        this.notificationBuilder.setContentIntent(PendingIntent.getActivity(this, 0, intent, AdobeCommonCacheConstants.GIGABYTES));
        this.notificationManager.notify(0, this.notificationBuilder.build());
    }

    private void onDownloadError(String error) {
        this.notificationManager.cancel(0);
        this.notificationBuilder.setProgress(0, 0, false);
        this.notificationBuilder.setContentText(error);
        this.notificationManager.notify(0, this.notificationBuilder.build());
    }

    public void shareImage(String fileName) {
        String savedFilePath = new StringBuilder();
        savedFilePath.append("file:///");
        savedFilePath.append(getSavePath().getPath());
        savedFilePath.append("/");
        savedFilePath.append(fileName);
        savedFilePath = savedFilePath.toString();
        Intent shareIntent = new Intent("android.intent.action.SEND");
        shareIntent.setType("image/jpeg");
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("shareImage: Share image location: ");
        stringBuilder.append(savedFilePath);
        Log.d(str, stringBuilder.toString());
        shareIntent.putExtra("android.intent.extra.TEXT", getResources().getString(C1420R.string.image_share_extra_text));
        shareIntent.putExtra("android.intent.extra.STREAM", Uri.parse(savedFilePath));
        Intent chooserIntent = Intent.createChooser(shareIntent, "Share Image");
        chooserIntent.addFlags(268435456);
        startActivity(chooserIntent);
    }
}
