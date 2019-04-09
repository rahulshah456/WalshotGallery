package com.app.walshotgallery;

import Fragments.PopularFragment;
import Modals.AutoScrollViewPager;
import Modals.Wallpaper;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import util.FileDownloadService;

public class PopularViewpagerActivity extends AppCompatActivity {
    private static final String ADDMOB_APP_ID = "ca-app-pub-8215970961458765~4792719695";
    public static final String BUNDLE_LIST_STATE = "list_state";
    private static final int IMAGE_EDITOR_RESULT = 9;
    private static final String INTERSITIAL_ADD_ID = "ca-app-pub-8215970961458765/3726780498";
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = PopularViewpagerActivity.class.getSimpleName();
    private static int currentPosition;
    private ActionBarContainer actionBarContainer;
    private ConstraintLayout creditsLayout;
    private TextView creditsTextView;
    private TextView dimension;
    private ImageView downloadBtn;
    Editor editor;
    private Bundle extras;
    private TextView format;
    private TextView hexCode;
    private ImageButton hexColor;
    private AutoScrollViewPager imageViewPager;
    private TextView likes;
    private TextView location;
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
    private TextView time;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$3 */
    class C14133 implements OnClickListener {
        C14133() {
        }

        public void onClick(View v) {
            PopularViewpagerActivity.this.ShowPopup();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$4 */
    class C14144 implements OnClickListener {
        C14144() {
        }

        public void onClick(View v) {
            int i = 0;
            SharedPreferences wallPrefs = PopularViewpagerActivity.this.getSharedPreferences(PopularViewpagerActivity.PREF_NAME, 0);
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
                                        intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                                        intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getRegular().trim());
                                        PopularViewpagerActivity.this.startActivity(intent1);
                                        break;
                                    case 1:
                                        intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                                        intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getFull().trim());
                                        PopularViewpagerActivity.this.startActivity(intent1);
                                        break;
                                    case 2:
                                        intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                                        intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getFull().trim());
                                        PopularViewpagerActivity.this.startActivity(intent1);
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
                                intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                                intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getRegular().trim());
                                PopularViewpagerActivity.this.startActivity(intent1);
                                break;
                            case 1:
                                intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                                intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getFull().trim());
                                PopularViewpagerActivity.this.startActivity(intent1);
                                break;
                            case 2:
                                intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                                intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getFull().trim());
                                PopularViewpagerActivity.this.startActivity(intent1);
                                break;
                            default:
                                break;
                        }
                        return;
                    }
                } else if (wallQuality.equals("Regular")) {
                    switch (i) {
                        case 0:
                            intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                            intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getRegular().trim());
                            PopularViewpagerActivity.this.startActivity(intent1);
                            break;
                        case 1:
                            intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                            intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getFull().trim());
                            PopularViewpagerActivity.this.startActivity(intent1);
                            break;
                        case 2:
                            intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                            intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getFull().trim());
                            PopularViewpagerActivity.this.startActivity(intent1);
                            break;
                        default:
                            break;
                    }
                    return;
                }
                i = -1;
                switch (i) {
                    case 0:
                        intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                        intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getRegular().trim());
                        PopularViewpagerActivity.this.startActivity(intent1);
                        break;
                    case 1:
                        intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                        intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getFull().trim());
                        PopularViewpagerActivity.this.startActivity(intent1);
                        break;
                    case 2:
                        intent1 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
                        intent1.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getFull().trim());
                        PopularViewpagerActivity.this.startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return;
            }
            Intent intent12 = new Intent(PopularViewpagerActivity.this, SetWallpaperActivity.class);
            intent12.putExtra("imageURL", ((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getRegular().trim());
            PopularViewpagerActivity.this.startActivity(intent12);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$5 */
    class C14155 implements OnClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$5$1 */
        class C23571 extends SimpleTarget<Bitmap> {
            C23571() {
            }

            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Uri bitmapUri = Uri.parse(Media.insertImage(PopularViewpagerActivity.this.getContentResolver(), resource, AdobeEntitlementUtils.AdobeEntitlementServiceImage, null));
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.STREAM", bitmapUri);
                PopularViewpagerActivity.this.startActivity(Intent.createChooser(intent, "Share"));
            }
        }

        C14155() {
        }

        public void onClick(View v) {
            String imageUri = "";
            Glide.with(PopularViewpagerActivity.this.getApplicationContext()).asBitmap().load(((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem())).getUrls().getRegular()).into(new C23571());
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$6 */
    class C14176 implements OnClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$6$1 */
        class C14161 implements DialogInterface.OnClickListener {
            C14161() {
            }

            public void onClick(DialogInterface dialog, int which) {
                Wallpaper wallpaper = (Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.imageViewPager.getCurrentItem());
                String imageUri = "";
                String fileName = "";
                StringBuilder stringBuilder;
                switch (which) {
                    case 0:
                        imageUri = wallpaper.getUrls().getSmall();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("photo-");
                        stringBuilder.append(wallpaper.getId());
                        stringBuilder.append("-");
                        stringBuilder.append(wallpaper.getUser().getId());
                        stringBuilder.append("-small.jpg");
                        fileName = stringBuilder.toString();
                        break;
                    case 1:
                        imageUri = wallpaper.getUrls().getRegular();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("photo-");
                        stringBuilder.append(wallpaper.getId());
                        stringBuilder.append("-");
                        stringBuilder.append(wallpaper.getUser().getId());
                        stringBuilder.append("-regular.jpg");
                        fileName = stringBuilder.toString();
                        break;
                    case 2:
                        imageUri = wallpaper.getUrls().getFull();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("photo-");
                        stringBuilder.append(wallpaper.getId());
                        stringBuilder.append("-");
                        stringBuilder.append(wallpaper.getUser().getId());
                        stringBuilder.append("-full.jpg");
                        fileName = stringBuilder.toString();
                        break;
                    case 3:
                        imageUri = wallpaper.getUrls().getRaw();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("photo-");
                        stringBuilder.append(wallpaper.getId());
                        stringBuilder.append("-");
                        stringBuilder.append(wallpaper.getUser().getId());
                        stringBuilder.append("-raw.jpg");
                        fileName = stringBuilder.toString();
                        break;
                    default:
                        break;
                }
                if (VERSION.SDK_INT > 22 && PopularViewpagerActivity.this.getApplicationContext().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    Log.d(PopularViewpagerActivity.TAG, "onClick: Asking for permissions...");
                    PopularViewpagerActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 200);
                }
                PopularViewpagerActivity.this.downloadImageToLocal(fileName, imageUri);
            }
        }

        C14176() {
        }

        public void onClick(View v) {
            Builder builder = new Builder(PopularViewpagerActivity.this);
            builder.setTitle("Select image size to download:");
            ArrayAdapter<String> adapter = new ArrayAdapter(PopularViewpagerActivity.this, 17367043);
            adapter.add("Small");
            adapter.add("Regular");
            adapter.add("Full (Recommended)");
            adapter.add("Raw");
            builder.setAdapter(adapter, new C14161());
            builder.show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$7 */
    class C14187 implements OnClickListener {
        C14187() {
        }

        public void onClick(View v) {
            Toast.makeText(PopularViewpagerActivity.this.getApplicationContext(), "Will be Available Shortly", 0).show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$9 */
    class C14199 implements OnClickListener {
        C14199() {
        }

        public void onClick(View v) {
            if (((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.scrollPosition)).getUser().getPortfolioUrl() != null) {
                PopularViewpagerActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.this.scrollPosition)).getUser().getPortfolioUrl().trim())));
            }
            PopularViewpagerActivity.this.profileDialog.dismiss();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$1 */
    class C17911 extends AdListener {
        C17911() {
        }

        public void onAdClosed() {
            PopularViewpagerActivity.this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$2 */
    class C17922 implements PageTransformer {
        C17922() {
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

    /* renamed from: com.walshotbeta.walshotvbeta.PopularViewpagerActivity$8 */
    class C17938 implements OnPageChangeListener {
        C17938() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            String userName = new StringBuilder();
            userName.append("Photo by ");
            userName.append(((Wallpaper) PopularFragment.wallpaperList.get(position)).getUser().getName());
            userName.append(" / Unsplash");
            PopularViewpagerActivity.this.creditsTextView.setText(userName.toString());
            PopularViewpagerActivity.this.scrollPosition = position;
            String pixels = new StringBuilder();
            pixels.append(((Wallpaper) PopularFragment.wallpaperList.get(position)).getHeight());
            pixels.append(" X ");
            pixels.append(((Wallpaper) PopularFragment.wallpaperList.get(position)).getWidth());
            pixels = pixels.toString();
            if (pixels != null) {
                PopularViewpagerActivity.this.dimension.setText(pixels);
            }
            if (((Wallpaper) PopularFragment.wallpaperList.get(position)).getCreatedAt().trim() != null) {
                PopularViewpagerActivity.this.time.setText(((Wallpaper) PopularFragment.wallpaperList.get(position)).getCreatedAt().trim());
            }
            if (((Wallpaper) PopularFragment.wallpaperList.get(position)).getColor().trim() != null) {
                PopularViewpagerActivity.this.hexCode.setText(((Wallpaper) PopularFragment.wallpaperList.get(position)).getColor().trim());
            }
            if (((Wallpaper) PopularFragment.wallpaperList.get(position)).getLikes().toString() != null) {
                String likesCount = new StringBuilder();
                likesCount.append(((Wallpaper) PopularFragment.wallpaperList.get(position)).getLikes().toString().trim());
                likesCount.append(" Likes");
                PopularViewpagerActivity.this.likes.setText(likesCount.toString());
            }
            if (((Wallpaper) PopularFragment.wallpaperList.get(position)).getColor() != null) {
                PopularViewpagerActivity.this.hexColor.setBackgroundColor(Color.parseColor(((Wallpaper) PopularFragment.wallpaperList.get(position)).getColor()));
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public class SliderViewPagerAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;
        ImageViewTouch previewImageView;

        public int getCount() {
            return PopularFragment.wallpaperList.size();
        }

        public Object instantiateItem(ViewGroup container, int position) {
            this.layoutInflater = (LayoutInflater) PopularViewpagerActivity.this.getSystemService("layout_inflater");
            View view = this.layoutInflater.inflate(C1420R.layout.image_fragment_preview, container, false);
            view.setTag(Integer.valueOf(position));
            this.previewImageView = (ImageViewTouch) view.findViewById(C1420R.id.image_preview);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(C1420R.id.progress_bar);
            Wallpaper wallpaper = (Wallpaper) PopularFragment.wallpaperList.get(position);
            String access$200 = PopularViewpagerActivity.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("instantiateItem: WallpaperURL: ");
            stringBuilder.append(wallpaper.getUrls().getFull());
            Log.d(access$200, stringBuilder.toString());
            Glide.with(PopularViewpagerActivity.this).load(wallpaper.getUrls().getRegular()).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    Log.d(PopularViewpagerActivity.TAG, "onResourceReady: Loaded!");
                    progressBar.setVisibility(8);
                    SliderViewPagerAdapter.this.previewImageView.setVisibility(0);
                    return false;
                }
            }).apply(new RequestOptions().override(Integer.MIN_VALUE, Integer.MIN_VALUE).diskCacheStrategy(DiskCacheStrategy.ALL)).into(this.previewImageView);
            this.previewImageView.setDisplayType(DisplayType.FIT_HEIGHT);
            container.addView(view, 0);
            return view;
        }

        public void testRefresh(int position) {
            if (PopularViewpagerActivity.this.imageViewPager.findViewWithTag(Integer.valueOf(position + 1)) != null) {
                String access$200 = PopularViewpagerActivity.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("testRefresh: View at ");
                stringBuilder.append(position + 1);
                stringBuilder.append(" Found!");
                Log.d(access$200, stringBuilder.toString());
                if (PopularViewpagerActivity.this.creditsLayout != null) {
                    access$200 = PopularViewpagerActivity.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("testRefresh: creditsLayout at ");
                    stringBuilder.append(position + 1);
                    stringBuilder.append(" set to ");
                    stringBuilder.append(PopularViewpagerActivity.this.creditsLayout.getAlpha());
                    Log.d(access$200, stringBuilder.toString());
                    return;
                }
                Log.e(PopularViewpagerActivity.TAG, "testRefresh: creditsTextView not found!");
                return;
            }
            Log.e(PopularViewpagerActivity.TAG, "testRefresh: View not found!");
        }

        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((View) object);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        String str;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(512, 512);
        setContentView(C1420R.layout.activity_popular_viewpager);
        MobileAds.initialize(this, ADDMOB_APP_ID);
        this.mInterstitialAd = new InterstitialAd(this);
        this.mInterstitialAd.setAdUnitId(INTERSITIAL_ADD_ID);
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
        this.mInterstitialAd.setAdListener(new C17911());
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
        this.dimension = (TextView) findViewById(C1420R.id.dimensionsID);
        this.location = (TextView) findViewById(C1420R.id.locationID);
        this.time = (TextView) findViewById(C1420R.id.uploadTimeID);
        this.format = (TextView) findViewById(C1420R.id.imageFormatID);
        this.hexCode = (TextView) findViewById(C1420R.id.colorHexID);
        this.likes = (TextView) findViewById(C1420R.id.likesID);
        this.hexColor = (ImageButton) findViewById(C1420R.id.image6);
        this.profileDialog = new Dialog(this);
        this.preferences = getSharedPreferences(PREF_NAME, 0);
        this.editor = this.preferences.edit();
        this.editor.putBoolean("slide1", true);
        this.editor.commit();
        this.creditsTextView = (TextView) findViewById(C1420R.id.wallpaperUploaderID);
        if (this.extras != null) {
            currentPosition = Integer.parseInt(this.extras.getString("position").trim());
            str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateView: wallpaperlist size: ");
            stringBuilder.append(PopularFragment.wallpaperList.size());
            Log.d(str, stringBuilder.toString());
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateView: currentPosition: ");
            stringBuilder.append(currentPosition);
            Log.d(str, stringBuilder.toString());
        }
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.mPagerAdapter = new SliderViewPagerAdapter();
        this.imageViewPager.setAdapter(this.mPagerAdapter);
        this.imageViewPager.setCurrentItem(currentPosition);
        this.imageViewPager.setPageTransformer(false, new C17922());
        String userName = new StringBuilder();
        userName.append("Photo by ");
        userName.append(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getName());
        userName.append(" / Unsplash");
        this.creditsTextView.setText(userName.toString());
        str = new StringBuilder();
        str.append(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getHeight());
        str.append(" X ");
        str.append(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getWidth());
        str = str.toString();
        if (str != null) {
            this.dimension.setText(str);
        }
        if (((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getCreatedAt().trim() != null) {
            this.time.setText(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getCreatedAt().trim());
        }
        if (((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getColor().trim() != null) {
            this.hexCode.setText(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getColor().trim());
        }
        if (((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getLikes().toString() != null) {
            String likesCount = new StringBuilder();
            likesCount.append(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getLikes().toString().trim());
            likesCount.append(" Likes");
            this.likes.setText(likesCount.toString());
        }
        if (((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getColor() != null) {
            this.hexColor.setBackgroundColor(Color.parseColor(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getColor()));
        }
        this.creditsTextView.setOnClickListener(new C14133());
        this.setWallBtn.setOnClickListener(new C14144());
        this.shareBtn.setOnClickListener(new C14155());
        this.downloadBtn.setOnClickListener(new C14176());
        this.origBtn.setOnClickListener(new C14187());
        this.imageViewPager.setOnPageChangeListener(new C17938());
    }

    public void ShowPopup() {
        this.profileDialog.setContentView(C1420R.layout.profile_popup);
        ImageView closeBtn = (ImageView) this.profileDialog.findViewById(C1420R.id.closeID);
        CircularImageView profileImage = (CircularImageView) this.profileDialog.findViewById(C1420R.id.profilePicID);
        TextView name = (TextView) this.profileDialog.findViewById(C1420R.id.profileNameID);
        TextView userName = (TextView) this.profileDialog.findViewById(C1420R.id.profileUsernameID);
        TextView instaName = (TextView) this.profileDialog.findViewById(C1420R.id.instagramProfileID);
        TextView location = (TextView) this.profileDialog.findViewById(C1420R.id.locationID);
        Button profileLink = (Button) this.profileDialog.findViewById(C1420R.id.profileLinkID);
        TextView likes = (TextView) this.profileDialog.findViewById(C1420R.id.likesCountID);
        TextView photos = (TextView) this.profileDialog.findViewById(C1420R.id.photosCountID);
        TextView collections = (TextView) this.profileDialog.findViewById(C1420R.id.collectionsCountID);
        if (this.scrollPosition != 0) {
            Glide.with(this).load(((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getProfileImage().getLarge().trim()).apply(new RequestOptions().centerCrop()).into(profileImage);
            if (((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getName() != null) {
                name.setText(((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getName().trim().toUpperCase());
            }
            if (((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getUsername() != null) {
                userName.setText(((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getUsername().trim());
            }
            if (((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getInstagramUsername() != null) {
                instaName.setText(((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getInstagramUsername().trim());
            }
            if (((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getLocation() != null) {
                location.setText(((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getLocation().trim());
            }
            likes.setText(String.valueOf(((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getTotalLikes()));
            photos.setText(String.valueOf(((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getTotalPhotos()));
            collections.setText(String.valueOf(((Wallpaper) PopularFragment.wallpaperList.get(this.scrollPosition)).getUser().getTotalCollections()));
            profileLink.setOnClickListener(new C14199());
        } else {
            Glide.with(this).load(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getProfileImage().getLarge().trim()).apply(new RequestOptions().centerCrop()).into(profileImage);
            if (((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getName() != null) {
                name.setText(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getName().trim().toUpperCase());
            }
            if (((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getUsername() != null) {
                userName.setText(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getUsername().trim());
            }
            if (((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getInstagramUsername() != null) {
                instaName.setText(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getInstagramUsername().trim());
            }
            if (((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getLocation() != null) {
                location.setText(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getLocation().trim());
            }
            likes.setText(String.valueOf(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getTotalLikes()));
            photos.setText(String.valueOf(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getTotalPhotos()));
            collections.setText(String.valueOf(((Wallpaper) PopularFragment.wallpaperList.get(currentPosition)).getUser().getTotalCollections()));
            profileLink.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.currentPosition)).getUser().getPortfolioUrl() != null) {
                        PopularViewpagerActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(((Wallpaper) PopularFragment.wallpaperList.get(PopularViewpagerActivity.currentPosition)).getUser().getPortfolioUrl().trim())));
                    }
                    PopularViewpagerActivity.this.profileDialog.dismiss();
                }
            });
        }
        closeBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PopularViewpagerActivity.this.profileDialog.dismiss();
            }
        });
        this.profileDialog.setCancelable(true);
        Window window = this.profileDialog.getWindow();
        window.getClass();
        window.setBackgroundDrawable(new ColorDrawable(0));
        if (!this.profileDialog.isShowing()) {
            this.profileDialog.show();
        }
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
                startActivityForResult(new AdobeImageIntent.Builder(this).setData(Uri.parse(((Wallpaper) PopularFragment.wallpaperList.get(this.imageViewPager.getCurrentItem())).getUrls().getRegular().trim())).build(), 9);
            } else if (editPrefs.getString("editing", "Regular") == "Regular") {
                startActivityForResult(new AdobeImageIntent.Builder(this).setData(Uri.parse(((Wallpaper) PopularFragment.wallpaperList.get(this.imageViewPager.getCurrentItem())).getUrls().getRegular().trim())).build(), 9);
            } else {
                startActivityForResult(new AdobeImageIntent.Builder(this).setData(Uri.parse(((Wallpaper) PopularFragment.wallpaperList.get(this.imageViewPager.getCurrentItem())).getUrls().getFull().trim())).build(), 9);
            }
        } else if (itemId != C1420R.id.favourites_menuID) {
            if (itemId == C1420R.id.slideshow_menuID) {
                if (this.preferences.contains("slide1")) {
                    if (this.preferences.getBoolean("slide1", true)) {
                        this.editor.putBoolean("slide1", true);
                        this.editor.commit();
                        this.imageViewPager.startAutoScroll();
                        this.imageViewPager.setInterval(2500);
                        this.imageViewPager.setCycle(false);
                        this.imageViewPager.setAutoScrollDurationFactor(10.0d);
                        Toast.makeText(getApplicationContext(), "Slide Show Started", 0).show();
                        Log.d(TAG, "Slide Show Started");
                        this.editor.putBoolean("slide1", false);
                        this.editor.commit();
                    } else {
                        this.editor.putBoolean("slide1", false);
                        this.editor.commit();
                        this.imageViewPager.stopAutoScroll();
                        this.imageViewPager.stopNestedScroll();
                        Toast.makeText(getApplicationContext(), "Slide Show Stopped", 0).show();
                        Log.d(TAG, "Slide Show Stopped");
                        this.editor.putBoolean("slide1", true);
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
