package Modals;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.webkit.MimeTypeMap;
import java.io.File;

public class FolderUtils {
    Context context;

    public FolderUtils(Context context) {
        this.context = context;
    }

    public static String getExtensionFromFilePath(String fullPath) {
        String[] filenameArray = fullPath.split("\\.");
        return filenameArray[filenameArray.length - 1];
    }

    public static String getFileName(String fullPath) {
        return fullPath.substring(fullPath.lastIndexOf(File.separator) + 1);
    }

    public static String getFileParent(String fullPath) {
        return fullPath.substring(0, fullPath.lastIndexOf(File.separator));
    }

    public static String getMimeTypeFromFilePath(String filePath) {
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getExtensionFromFilePath(filePath));
        return mimeType == null ? "*/*" : mimeType;
    }

    public static boolean isImage(String fullPath) {
        File imageFile = new File(fullPath);
        boolean z = false;
        if (imageFile != null) {
            if (imageFile.exists()) {
                Options options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imageFile.getPath(), options);
                if (!(options.outWidth == -1 || options.outHeight == -1)) {
                    z = true;
                }
                return z;
            }
        }
        return false;
    }
}
