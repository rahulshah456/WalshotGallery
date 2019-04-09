package com.app.walshotgallery;

import Modals.Wallpaper;
import Retrofit.WallpaperApi.Factory;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomWallpaperActivity extends AppCompatActivity {
    private static final String TAG = RandomWallpaperActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private Button random;
    private ImageViewTouch randomImage;
    private Toolbar toolbar;
    private Wallpaper wallpaper;

    /* renamed from: com.walshotbeta.walshotvbeta.RandomWallpaperActivity$2 */
    class C14212 implements OnClickListener {
        C14212() {
        }

        public void onClick(View v) {
            RandomWallpaperActivity.this.progressBar.setVisibility(0);
            RandomWallpaperActivity.this.NewWallpaper();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.RandomWallpaperActivity$1 */
    class C17971 implements Callback<Wallpaper> {

        /* renamed from: com.walshotbeta.walshotvbeta.RandomWallpaperActivity$1$1 */
        class C17961 implements RequestListener<Drawable> {
            C17961() {
            }

            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Log.d(RandomWallpaperActivity.TAG, "onResourceReady: Loaded!");
                RandomWallpaperActivity.this.progressBar.setVisibility(8);
                RandomWallpaperActivity.this.randomImage.setVisibility(0);
                return false;
            }
        }

        C17971() {
        }

        public void onResponse(Call<Wallpaper> call, Response<Wallpaper> response) {
            RandomWallpaperActivity.this.wallpaper = (Wallpaper) response.body();
            RequestManager with = Glide.with(RandomWallpaperActivity.this);
            String regular = RandomWallpaperActivity.this.wallpaper.getUrls().getRegular();
            regular.getClass();
            with.load(regular).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new C17961()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).override(Integer.MIN_VALUE, Integer.MIN_VALUE)).into(RandomWallpaperActivity.this.randomImage);
            RandomWallpaperActivity.this.randomImage.setDisplayType(DisplayType.FIT_HEIGHT);
        }

        public void onFailure(Call<Wallpaper> call, Throwable t) {
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.RandomWallpaperActivity$3 */
    class C17993 implements Callback<Wallpaper> {

        /* renamed from: com.walshotbeta.walshotvbeta.RandomWallpaperActivity$3$1 */
        class C17981 implements RequestListener<Drawable> {
            C17981() {
            }

            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Log.d(RandomWallpaperActivity.TAG, "onResourceReady: Loaded!");
                RandomWallpaperActivity.this.progressBar.setVisibility(8);
                RandomWallpaperActivity.this.randomImage.setVisibility(0);
                return false;
            }
        }

        C17993() {
        }

        public void onResponse(Call<Wallpaper> call, Response<Wallpaper> response) {
            RandomWallpaperActivity.this.wallpaper = (Wallpaper) response.body();
            RequestManager with = Glide.with(RandomWallpaperActivity.this);
            Wallpaper access$000 = RandomWallpaperActivity.this.wallpaper;
            access$000.getClass();
            with.load(access$000.getUrls().getRegular()).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new C17981()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).override(Integer.MIN_VALUE, Integer.MIN_VALUE)).into(RandomWallpaperActivity.this.randomImage);
            RandomWallpaperActivity.this.randomImage.setDisplayType(DisplayType.FIT_HEIGHT);
        }

        public void onFailure(Call<Wallpaper> call, Throwable t) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(512, 512);
        getWindow().setFlags(134217728, 134217728);
        getWindow().setFlags(67108864, 67108864);
        setContentView(C1420R.layout.activity_random_wallpaper);
        this.randomImage = (ImageViewTouch) findViewById(C1420R.id.randomImageID);
        this.random = (Button) findViewById(C1420R.id.randomID);
        this.progressBar = (ProgressBar) findViewById(C1420R.id.progress_bar);
        this.toolbar = (Toolbar) findViewById(C1420R.id.pagerToolbarID);
        this.toolbar.setTitle(StringUtils.SPACE);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.wallpaper = null;
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        Factory.getInstance().getRandomPic().enqueue(new C17971());
        this.random.setOnClickListener(new C14212());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1420R.menu.random_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == C1420R.id.random_intentID) {
            String imageUri = "";
            if (this.wallpaper.getUrls().getRegular() != null) {
                imageUri = this.wallpaper.getUrls().getRegular().trim();
                Intent intent = new Intent(this, SetWallpaperActivity.class);
                intent.putExtra("imageURL", imageUri);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void NewWallpaper() {
        Factory.getInstance().getRandomPic().enqueue(new C17993());
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
