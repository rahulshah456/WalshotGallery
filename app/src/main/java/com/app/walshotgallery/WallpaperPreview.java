package com.app.walshotgallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.adobe.creativesdk.foundation.internal.entitlement.AdobeEntitlementUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;

public class WallpaperPreview extends AppCompatActivity {
    private static final String TAG = WallpaperPreview.class.getSimpleName();
    private static int currentPosition;
    private Bundle extras;
    private ImageViewTouch imageViewTouch;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.WallpaperPreview$1 */
    class C18221 implements RequestListener<Drawable> {
        C18221() {
        }

        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            Log.d(WallpaperPreview.TAG, "onResourceReady: Loaded!");
            WallpaperPreview.this.progressBar.setVisibility(8);
            WallpaperPreview.this.imageViewTouch.setVisibility(0);
            return false;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(512, 512);
        getWindow().setFlags(134217728, 134217728);
        setContentView(C1420R.layout.activity_wallpaper_preview);
        this.extras = getIntent().getExtras();
        this.toolbar = (Toolbar) findViewById(C1420R.id.pagerToolbarID);
        this.toolbar.setTitle(StringUtils.SPACE);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.imageViewTouch = (ImageViewTouch) findViewById(C1420R.id.wallpaperPreviewID);
        this.progressBar = (ProgressBar) findViewById(C1420R.id.progressBarID);
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        if (this.extras != null) {
            String string = this.extras.getString("position");
            string.getClass();
            currentPosition = Integer.parseInt(string.trim());
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateView: wallpaperlist size: ");
            stringBuilder.append(UserDeviceActivity.mImagesList.size());
            Log.d(str, stringBuilder.toString());
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateView: currentPosition: ");
            stringBuilder.append(currentPosition);
            Log.d(str, stringBuilder.toString());
        }
        Bitmap bitmap = (Bitmap) UserDeviceActivity.mImagesList.get(currentPosition);
        Glide.with(this).load((Bitmap) UserDeviceActivity.mImagesList.get(currentPosition)).transition(DrawableTransitionOptions.withCrossFade()).listener(new C18221()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(Integer.MIN_VALUE, Integer.MIN_VALUE)).into(this.imageViewTouch);
        this.imageViewTouch.setDoubleTapEnabled(false);
        this.imageViewTouch.setDisplayType(DisplayType.FIT_HEIGHT);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1420R.menu.preview_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId != C1420R.id.favourites_menuID) {
            if (itemId == C1420R.id.save_menuID) {
                SaveImage((Bitmap) UserDeviceActivity.mImagesList.get(currentPosition));
            } else if (itemId == C1420R.id.share_menuID) {
                Uri bitmapUri = Uri.parse(Media.insertImage(getContentResolver(), (Bitmap) UserDeviceActivity.mImagesList.get(currentPosition), AdobeEntitlementUtils.AdobeEntitlementServiceImage, null));
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.STREAM", bitmapUri);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveImage(Bitmap finalBitmap) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(C1420R.string.app_name));
        File myDir = new File(stringBuilder.toString());
        myDir.mkdirs();
        int n = new Random().nextInt(10000);
        String fileName = new StringBuilder();
        fileName.append("Image-");
        fileName.append(n);
        fileName.append(".jpg");
        File file = new File(myDir, fileName.toString());
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(CompressFormat.JPEG, 100, out);
            Toast.makeText(getApplicationContext(), "Image Saved", 0).show();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
