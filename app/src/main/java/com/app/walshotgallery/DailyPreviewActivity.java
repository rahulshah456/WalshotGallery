package com.app.walshotgallery;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
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

public class DailyPreviewActivity extends AppCompatActivity {
    private static final String TAG = DailyPreviewActivity.class.getSimpleName();
    private Bundle extras;
    private ImageViewTouch imageView;
    private String postURL;
    private ProgressBar progressBar;

    /* renamed from: com.walshotbeta.walshotvbeta.DailyPreviewActivity$1 */
    class C17421 implements RequestListener<Drawable> {
        C17421() {
        }

        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            Log.d(DailyPreviewActivity.TAG, "onResourceReady: Loaded!");
            DailyPreviewActivity.this.progressBar.setVisibility(8);
            DailyPreviewActivity.this.imageView.setVisibility(0);
            return false;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(512, 512);
        setContentView(C1420R.layout.activity_daily_preview);
        this.extras = getIntent().getExtras();
        this.progressBar = (ProgressBar) findViewById(C1420R.id.progressBarID);
        if (this.extras != null) {
            String string = this.extras.getString("imageURL");
            string.getClass();
            this.postURL = string.trim();
        }
        this.imageView = (ImageViewTouch) findViewById(C1420R.id.image_previewID);
        Glide.with(this).load(this.postURL).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new C17421()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).override(Integer.MIN_VALUE, Integer.MIN_VALUE)).into(this.imageView);
        this.imageView.setDisplayType(DisplayType.FIT_HEIGHT);
    }
}
