package com.app.walshotgallery;

import Modals.Wallpaper;
import Retrofit.WallpaperApi.Factory;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.DeviceMetrics;

public class RandomWallpaper extends BroadcastReceiver {
    public void onReceive(final Context context, Intent intent) {
        Log.d("Jhalota", "Alarm just fired");
        Factory.getInstance().getRandomPic().enqueue(new Callback<Wallpaper>() {

            /* renamed from: com.walshotbeta.walshotvbeta.RandomWallpaper$1$1 */
            class C23581 extends SimpleTarget<Bitmap> {
                static final /* synthetic */ boolean $assertionsDisabled = false;

                static {
                    Class cls = RandomWallpaper.class;
                }

                C23581() {
                }

                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService("wallpaper");
                    wallpaperManager.suggestDesiredDimensions(DeviceMetrics.getDisplayWidth(context), DeviceMetrics.getDisplayHeight(context));
                    try {
                        wallpaperManager.setBitmap(resource);
                        Log.d("Jhalota", "Random Wallpaper Set Successfully");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onResponse(Call<Wallpaper> call, Response<Wallpaper> response) {
                Glide.with(context).asBitmap().load(((Wallpaper) response.body()).getUrls().getFull()).apply(new RequestOptions().centerCrop()).into(new C23581());
            }

            public void onFailure(Call<Wallpaper> call, Throwable t) {
            }
        });
    }
}
