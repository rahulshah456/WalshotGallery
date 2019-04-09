package util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DeviceMetrics {
    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (context == null) {
            return null;
        }
        return context.getResources().getDisplayMetrics();
    }

    public static int getDisplayWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getDisplayHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }
}
