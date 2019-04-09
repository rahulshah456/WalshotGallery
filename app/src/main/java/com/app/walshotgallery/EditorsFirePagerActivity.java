package com.app.walshotgallery;

import Modals.AutoScrollViewPager;
import Modals.FirebaseWallpapers;
import Modals.Wallpaper;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.adobe.creativesdk.aviary.AdobeImageIntent;
import com.adobe.creativesdk.foundation.internal.entitlement.AdobeEntitlementUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import util.FileDownloadService;

public class EditorsFirePagerActivity extends AppCompatActivity {
    private static final String ADDMOB_APP_ID = "ca-app-pub-8215970961458765~4792719695";
    public static final String BUNDLE_LIST_STATE = "list_state";
    private static final int IMAGE_EDITOR_RESULT = 9;
    private static final String INTERSITIAL_ADD_ID = "ca-app-pub-8215970961458765/3123596525";
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = EditorsFirePagerActivity.class.getSimpleName();
    private static int currentPosition;
    private ActionBarContainer actionBarContainer;
    private ConstraintLayout creditsLayout;
    private TextView creditsTextView;
    private ImageView downloadBtn;
    Editor editor;
    private Bundle extras;
    private ImageButton hexColor;
    private AutoScrollViewPager imageViewPager;
    private InterstitialAd mInterstitialAd;
    private Parcelable mListState;
    private SliderViewPagerAdapter mPagerAdapter;
    private Wallpaper mWallpaper;
    private ImageView origBtn;
    private SharedPreferences preferences;
    private Dialog profileDialog;
    private int scrollPosition = 0;
    private ImageView setWallBtn;
    private ImageView shareBtn;
    private Animation slideDown;
    private Animation slideUp;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.EditorsFirePagerActivity$3 */
    class C13653 implements OnClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.EditorsFirePagerActivity$3$1 */
        class C23531 extends SimpleTarget<Bitmap> {
            C23531() {
            }

            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Uri bitmapUri = Uri.parse(Media.insertImage(EditorsFirePagerActivity.this.getContentResolver(), resource, AdobeEntitlementUtils.AdobeEntitlementServiceImage, null));
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.STREAM", bitmapUri);
                EditorsFirePagerActivity.this.startActivity(Intent.createChooser(intent, "Share"));
            }
        }

        C13653() {
        }

        public void onClick(View v) {
            String str = "";
            Glide.with(EditorsFirePagerActivity.this.getApplicationContext()).asBitmap().load(((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL()).into(new C23531());
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.EditorsFirePagerActivity$4 */
    class C13664 implements OnClickListener {
        C13664() {
        }

        public void onClick(View v) {
            String str = "";
            str = ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL();
            Intent intent = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
            intent.putExtra("imageURL", str);
            EditorsFirePagerActivity.this.startActivity(intent);
            int i = 0;
            SharedPreferences wallPrefs = EditorsFirePagerActivity.this.getSharedPreferences(EditorsFirePagerActivity.PREF_NAME, 0);
            if (wallPrefs.contains("wallpaper")) {
                Intent intent1;
                String wallQuality = wallPrefs.getString("wallpaper", "Regular");
                int hashCode = wallQuality.hashCode();
                if (hashCode != -1543850116) {
                    if (hashCode != 2201263) {
                        if (hashCode == 1443687921) {
                            if (wallQuality.equals("Original")) {
                                i = 2;
                                switch (i) {
                                    case 0:
                                        intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                                        intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                                        EditorsFirePagerActivity.this.startActivity(intent1);
                                        break;
                                    case 1:
                                        intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                                        intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                                        EditorsFirePagerActivity.this.startActivity(intent1);
                                        break;
                                    case 2:
                                        intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                                        intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getOrignalURL());
                                        EditorsFirePagerActivity.this.startActivity(intent1);
                                        break;
                                    default:
                                        break;
                                }
                                return;
                            }
                        }
                    } else if (wallQuality.equals("Full")) {
                        i = 1;
                        switch (i) {
                            case 0:
                                intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                                intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                                EditorsFirePagerActivity.this.startActivity(intent1);
                                break;
                            case 1:
                                intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                                intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                                EditorsFirePagerActivity.this.startActivity(intent1);
                                break;
                            case 2:
                                intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                                intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getOrignalURL());
                                EditorsFirePagerActivity.this.startActivity(intent1);
                                break;
                            default:
                                break;
                        }
                        return;
                    }
                } else if (wallQuality.equals("Regular")) {
                    switch (i) {
                        case 0:
                            intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                            intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                            EditorsFirePagerActivity.this.startActivity(intent1);
                            break;
                        case 1:
                            intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                            intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                            EditorsFirePagerActivity.this.startActivity(intent1);
                            break;
                        case 2:
                            intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                            intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getOrignalURL());
                            EditorsFirePagerActivity.this.startActivity(intent1);
                            break;
                        default:
                            break;
                    }
                    return;
                }
                i = -1;
                switch (i) {
                    case 0:
                        intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                        intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                        EditorsFirePagerActivity.this.startActivity(intent1);
                        break;
                    case 1:
                        intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                        intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                        EditorsFirePagerActivity.this.startActivity(intent1);
                        break;
                    case 2:
                        intent1 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
                        intent1.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getOrignalURL());
                        EditorsFirePagerActivity.this.startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return;
            }
            Intent intent12 = new Intent(EditorsFirePagerActivity.this, SetWallpaperActivity.class);
            intent12.putExtra("imageURL", ((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
            EditorsFirePagerActivity.this.startActivity(intent12);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.EditorsFirePagerActivity$5 */
    class C13675 implements OnClickListener {
        C13675() {
        }

        public void onClick(View v) {
            Toast.makeText(EditorsFirePagerActivity.this.getApplicationContext(), "Will be Available Shortly", 0).show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.EditorsFirePagerActivity$6 */
    class C13696 implements OnClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.EditorsFirePagerActivity$6$1 */
        class C13681 implements DialogInterface.OnClickListener {
            C13681() {
            }

            public void onClick(DialogInterface dialog, int which) {
                FirebaseWallpapers wallpaper = (FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(EditorsFirePagerActivity.this.imageViewPager.getCurrentItem());
                String imageUri = "";
                String fileName = "";
                StringBuilder stringBuilder;
                switch (which) {
                    case 0:
                        imageUri = wallpaper.getCompressedURL();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("photo-");
                        stringBuilder.append(wallpaper.getImageCategory());
                        stringBuilder.append("-regular.jpg");
                        fileName = stringBuilder.toString();
                        break;
                    case 1:
                        imageUri = wallpaper.getOrignalURL();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("photo-");
                        stringBuilder.append(wallpaper.getImageCategory());
                        stringBuilder.append("-orignal.jpg");
                        fileName = stringBuilder.toString();
                        break;
                    default:
                        break;
                }
                if (VERSION.SDK_INT > 22 && EditorsFirePagerActivity.this.getApplicationContext().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    Log.d(EditorsFirePagerActivity.TAG, "onClick: Asking for permissions...");
                    EditorsFirePagerActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 200);
                }
                EditorsFirePagerActivity.this.downloadImageToLocal(fileName, imageUri);
            }
        }

        C13696() {
        }

        public void onClick(View v) {
            Builder builder = new Builder(EditorsFirePagerActivity.this);
            builder.setTitle("Select image size to download:");
            ArrayAdapter<String> adapter = new ArrayAdapter(EditorsFirePagerActivity.this, 17367043);
            adapter.add("Regular (Recommended)");
            adapter.add("Orignal");
            builder.setAdapter(adapter, new C13681());
            builder.show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.EditorsFirePagerActivity$1 */
    class C17451 extends AdListener {
        C17451() {
        }

        public void onAdClosed() {
            EditorsFirePagerActivity.this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.EditorsFirePagerActivity$2 */
    class C17462 implements PageTransformer {
        C17462() {
        }

        public void transformPage(@NonNull View page, float position) {
            if (position > -1.0f) {
                if (position < 1.0f) {
                    if (position == 0.0f) {
                        page.findViewById(C1420R.id.image_preview).setVisibility(0);
                        return;
                    } else {
                        page.findViewById(C1420R.id.image_preview).setTranslationX(((-position) * ((float) page.getWidth())) / 2.0f);
                        return;
                    }
                }
            }
            page.findViewById(C1420R.id.image_preview).setVisibility(0);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.EditorsFirePagerActivity$7 */
    class C17477 implements OnPageChangeListener {
        C17477() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public class SliderViewPagerAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;
        String url;
        String userName;

        public int getCount() {
            return FireCollectionActivity.wallpaperList.size();
        }

        public Object instantiateItem(ViewGroup container, int position) {
            this.layoutInflater = (LayoutInflater) EditorsFirePagerActivity.this.getSystemService("layout_inflater");
            View view = this.layoutInflater.inflate(C1420R.layout.image_fragment_preview, container, false);
            view.setTag(Integer.valueOf(position));
            final ImageViewTouch previewImageView = (ImageViewTouch) view.findViewById(C1420R.id.image_preview);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(C1420R.id.progress_bar);
            FirebaseWallpapers wallpaper = (FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(position);
            String access$200 = EditorsFirePagerActivity.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("instantiateItem: WallpaperURL: ");
            stringBuilder.append(wallpaper.getOrignalURL());
            Log.d(access$200, stringBuilder.toString());
            this.userName = "Photo by admin / Firebase";
            Glide.with(EditorsFirePagerActivity.this).load(wallpaper.getCompressedURL()).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    Log.d(EditorsFirePagerActivity.TAG, "onResourceReady: Loaded!");
                    progressBar.setVisibility(8);
                    previewImageView.setVisibility(0);
                    return false;
                }
            }).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).override(Integer.MIN_VALUE, Integer.MIN_VALUE)).into(previewImageView);
            previewImageView.setDisplayType(DisplayType.FIT_HEIGHT);
            container.addView(view, 0);
            return view;
        }

        public void testRefresh(int position) {
            if (EditorsFirePagerActivity.this.imageViewPager.findViewWithTag(Integer.valueOf(position + 1)) != null) {
                String access$200 = EditorsFirePagerActivity.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("testRefresh: View at ");
                stringBuilder.append(position + 1);
                stringBuilder.append(" Found!");
                Log.d(access$200, stringBuilder.toString());
                if (EditorsFirePagerActivity.this.creditsLayout != null) {
                    access$200 = EditorsFirePagerActivity.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("testRefresh: creditsLayout at ");
                    stringBuilder.append(position + 1);
                    stringBuilder.append(" set to ");
                    stringBuilder.append(EditorsFirePagerActivity.this.creditsLayout.getAlpha());
                    Log.d(access$200, stringBuilder.toString());
                    return;
                }
                Log.e(EditorsFirePagerActivity.TAG, "testRefresh: creditsTextView not found!");
                return;
            }
            Log.e(EditorsFirePagerActivity.TAG, "testRefresh: View not found!");
        }

        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((View) object);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(512, 512);
        setContentView(C1420R.layout.activity_editors_fire_pager);
        MobileAds.initialize(this, ADDMOB_APP_ID);
        this.mInterstitialAd = new InterstitialAd(this);
        this.mInterstitialAd.setAdUnitId(INTERSITIAL_ADD_ID);
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
        this.mInterstitialAd.setAdListener(new C17451());
        this.extras = getIntent().getExtras();
        this.toolbar = (Toolbar) findViewById(C1420R.id.pagerToolbarID);
        this.toolbar.setTitle(StringUtils.SPACE);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.imageViewPager = (AutoScrollViewPager) findViewById(C1420R.id.sliderViewPagerID);
        this.downloadBtn = (ImageView) findViewById(C1420R.id.menu_save2);
        this.shareBtn = (ImageView) findViewById(C1420R.id.menu_save3);
        this.origBtn = (ImageView) findViewById(C1420R.id.menu_save);
        this.setWallBtn = (ImageView) findViewById(C1420R.id.menu_apply);
        this.profileDialog = new Dialog(this);
        this.preferences = getSharedPreferences(PREF_NAME, 0);
        this.editor = this.preferences.edit();
        this.editor.putBoolean("slide7", true);
        this.editor.commit();
        this.creditsTextView = (TextView) findViewById(C1420R.id.wallpaperUploaderID);
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        if (this.extras != null) {
            currentPosition = Integer.parseInt(this.extras.getString("position").trim());
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateView: wallpaperlist size: ");
            stringBuilder.append(FireCollectionActivity.wallpaperList.size());
            Log.d(str, stringBuilder.toString());
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateView: currentPosition: ");
            stringBuilder.append(currentPosition);
            Log.d(str, stringBuilder.toString());
        }
        this.mPagerAdapter = new SliderViewPagerAdapter();
        this.imageViewPager.setAdapter(this.mPagerAdapter);
        this.imageViewPager.setCurrentItem(currentPosition);
        this.imageViewPager.setPageTransformer(false, new C17462());
        this.shareBtn.setOnClickListener(new C13653());
        this.setWallBtn.setOnClickListener(new C13664());
        this.origBtn.setOnClickListener(new C13675());
        this.downloadBtn.setOnClickListener(new C13696());
        this.imageViewPager.setOnPageChangeListener(new C17477());
    }

    public void downloadImageToLocal(String fileName, String imageUri) {
        if (this.mInterstitialAd.isLoaded()) {
            this.mInterstitialAd.show();
        } else {
            Log.d(com.google.ads.AdRequest.LOGTAG, "The interstitial wasn't loaded yet.");
        }
        String finalFileName = fileName.toLowerCase();
        Intent intent = new Intent(getApplicationContext(), FileDownloadService.class);
        Bundle bundle = new Bundle();
        bundle.putString(FileDownloadService.INTENT_URL, imageUri);
        bundle.putString(FileDownloadService.INTENT_FILE_NAME, finalFileName);
        intent.putExtras(bundle);
        Log.d(TAG, "downloadImageToLocal: Starting download service...");
        getApplicationContext().startService(intent);
        showSnackBar("Downloading image...");
    }

    private void showSnackBar(String message) {
        Snackbar.with(this, null);
        Snackbar.type(Type.SUCCESS);
        Snackbar.message(message);
        Snackbar.duration(Duration.SHORT);
        Snackbar.fillParent(true);
        Snackbar.textAlign(Align.LEFT);
        Snackbar.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1420R.menu.slider_preview_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == C1420R.id.editSave_menuID) {
            SharedPreferences editPrefs = getSharedPreferences(PREF_NAME, 0);
            if (!editPrefs.contains("editing")) {
                startActivityForResult(new AdobeImageIntent.Builder(this).setData(Uri.parse(((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(this.imageViewPager.getCurrentItem())).getCompressedURL())).build(), 9);
            } else if (editPrefs.getString("editing", "Regular") == "Regular") {
                startActivityForResult(new AdobeImageIntent.Builder(this).setData(Uri.parse(((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(this.imageViewPager.getCurrentItem())).getCompressedURL())).build(), 9);
            } else {
                startActivityForResult(new AdobeImageIntent.Builder(this).setData(Uri.parse(((FirebaseWallpapers) FireCollectionActivity.wallpaperList.get(this.imageViewPager.getCurrentItem())).getOrignalURL())).build(), 9);
            }
        } else if (itemId != C1420R.id.favourites_menuID) {
            if (itemId == C1420R.id.slideshow_menuID) {
                if (this.preferences.contains("slide7")) {
                    if (this.preferences.getBoolean("slide7", true)) {
                        this.editor.putBoolean("slide7", true);
                        this.editor.commit();
                        this.imageViewPager.startAutoScroll();
                        this.imageViewPager.setInterval(2500);
                        this.imageViewPager.setCycle(false);
                        this.imageViewPager.setAutoScrollDurationFactor(10.0d);
                        Toast.makeText(getApplicationContext(), "Slide Show Started", 0).show();
                        Log.d(TAG, "Slide Show Started");
                        this.editor.putBoolean("slide7", false);
                        this.editor.commit();
                    } else {
                        this.editor.putBoolean("slide7", false);
                        this.editor.commit();
                        this.imageViewPager.stopAutoScroll();
                        this.imageViewPager.stopNestedScroll();
                        Toast.makeText(getApplicationContext(), "Slide Show Stopped", 0).show();
                        Log.d(TAG, "Slide Show Stopped");
                        this.editor.putBoolean("slide7", true);
                        this.editor.commit();
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (requestCode != 9) {
            throw new IllegalArgumentException("Unexpected request code");
        }
        Uri editedImageUri = (Uri) data.getParcelableExtra(AdobeImageIntent.EXTRA_OUTPUT_URI);
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("editedImageUri: ");
        stringBuilder.append(editedImageUri.toString());
        Log.d(str, stringBuilder.toString());
        Bundle extra = data.getExtras();
        if (extra != null) {
            boolean changed = extra.getBoolean(AdobeImageIntent.EXTRA_OUT_BITMAP_CHANGED);
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Image edited: ");
            stringBuilder2.append(changed);
            Log.d(str2, stringBuilder2.toString());
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

    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            this.mListState = this.mPagerAdapter.saveState();
            outState.putParcelable("list_state", this.mListState);
        }
        super.onSaveInstanceState(outState);
    }

    public void onResume() {
        if (this.mListState != null) {
            Log.d(TAG, "onResume: Restoring list state ...");
            this.mPagerAdapter.restoreState(this.mListState, getClassLoader());
        } else {
            Log.d(TAG, "onResume: ListState empty!");
        }
        super.onResume();
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(String key) {
        if (key.equals("Refresh Adapter")) {
            Log.d(TAG, "onEvent: Calling notifyDataSetChanged on PagerAdapter ...");
            this.mPagerAdapter.notifyDataSetChanged();
        }
    }
}
