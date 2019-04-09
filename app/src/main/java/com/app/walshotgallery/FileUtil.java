package com.app.walshotgallery;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class FileUtil {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int EOF = -1;

    private FileUtil() {
    }

    public static File from(Context context, Uri uri) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        String fileName = getFileName(context, uri);
        String[] splitName = splitFileName(fileName);
        File tempFile = rename(File.createTempFile(splitName[0], splitName[1]), fileName);
        tempFile.deleteOnExit();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            copy(inputStream, out);
            inputStream.close();
        }
        if (out != null) {
            out.close();
        }
        return tempFile;
    }

    private static String[] splitFileName(String fileName) {
        String name = fileName;
        String extension = "";
        int i = fileName.lastIndexOf(".");
        if (i != -1) {
            name = fileName.substring(0, i);
            extension = fileName.substring(i);
        }
        return new String[]{name, extension};
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static String getFileName(Context r8, Uri r9) {
        /*
        r0 = 0;
        r1 = r9.getScheme();
        r2 = "content";
        r1 = r1.equals(r2);
        if (r1 == 0) goto L_0x0042;
    L_0x000d:
        r2 = r8.getContentResolver();
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r3 = r9;
        r1 = r2.query(r3, r4, r5, r6, r7);
        if (r1 == 0) goto L_0x003d;
    L_0x001c:
        r2 = r1.moveToFirst();	 Catch:{ Exception -> 0x0030 }
        if (r2 == 0) goto L_0x003d;
    L_0x0022:
        r2 = "_display_name";
        r2 = r1.getColumnIndex(r2);	 Catch:{ Exception -> 0x0030 }
        r2 = r1.getString(r2);	 Catch:{ Exception -> 0x0030 }
        r0 = r2;
        goto L_0x003d;
    L_0x002e:
        r2 = move-exception;
        goto L_0x0037;
    L_0x0030:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x002e }
        if (r1 == 0) goto L_0x0042;
    L_0x0036:
        goto L_0x003f;
    L_0x0037:
        if (r1 == 0) goto L_0x003c;
    L_0x0039:
        r1.close();
    L_0x003c:
        throw r2;
    L_0x003d:
        if (r1 == 0) goto L_0x0042;
    L_0x003f:
        r1.close();
    L_0x0042:
        if (r0 != 0) goto L_0x0057;
    L_0x0044:
        r0 = r9.getPath();
        r1 = java.io.File.separator;
        r1 = r0.lastIndexOf(r1);
        r2 = -1;
        if (r1 == r2) goto L_0x0057;
    L_0x0051:
        r2 = r1 + 1;
        r0 = r0.substring(r2);
    L_0x0057:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.walshotbeta.walshotvbeta.FileUtil.getFileName(android.content.Context, android.net.Uri):java.lang.String");
    }

    private static File rename(File file, String newName) {
        File newFile = new File(file.getParent(), newName);
        if (!newFile.equals(file)) {
            StringBuilder stringBuilder;
            if (newFile.exists() && newFile.delete()) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Delete old ");
                stringBuilder.append(newName);
                stringBuilder.append(" file");
                Log.d("FileUtil", stringBuilder.toString());
            }
            if (file.renameTo(newFile)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Rename file to ");
                stringBuilder.append(newName);
                Log.d("FileUtil", stringBuilder.toString());
            }
        }
        return newFile;
    }

    private static long copy(InputStream input, OutputStream output) throws IOException {
        long count = 0;
        byte[] buffer = new byte[4096];
        while (true) {
            int read = input.read(buffer);
            int n = read;
            if (-1 == read) {
                return count;
            }
            output.write(buffer, 0, n);
            count += (long) n;
        }
    }
}
