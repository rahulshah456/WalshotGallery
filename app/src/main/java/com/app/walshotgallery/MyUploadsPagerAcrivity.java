package com.app.walshotgallery;

import Modals.AutoScrollViewPager;
import Modals.FirebaseDailyShots;
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
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import util.FileDownloadService;

public class MyUploadsPagerAcrivity extends AppCompatActivity {
    public static final String BUNDLE_LIST_STATE = "list_state";
    private static final int IMAGE_EDITOR_RESULT = 9;
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = MyUploadsPagerAcrivity.class.getSimpleName();
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

    /* renamed from: com.walshotbeta.walshotvbeta.MyUploadsPagerAcrivity$2 */
    class C14082 implements OnClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.MyUploadsPagerAcrivity$2$1 */
        class C23561 extends SimpleTarget<Bitmap> {
            C23561() {
            }

            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Uri bitmapUri = Uri.parse(Media.insertImage(MyUploadsPagerAcrivity.this.getContentResolver(), resource, AdobeEntitlementUtils.AdobeEntitlementServiceImage, null));
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.STREAM", bitmapUri);
                MyUploadsPagerAcrivity.this.startActivity(Intent.createChooser(intent, "Share"));
            }
        }

        C14082() {
        }

        public void onClick(View v) {
            String str = "";
            Glide.with(MyUploadsPagerAcrivity.this.getApplicationContext()).asBitmap().load(((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL()).into(new C23561());
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MyUploadsPagerAcrivity$3 */
    class C14093 implements OnClickListener {
        C14093() {
        }

        public void onClick(View v) {
            int i = 0;
            SharedPreferences wallPrefs = MyUploadsPagerAcrivity.this.getSharedPreferences(MyUploadsPagerAcrivity.PREF_NAME, 0);
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
                                        intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                                        intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                                        MyUploadsPagerAcrivity.this.startActivity(intent1);
                                        break;
                                    case 1:
                                        intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                                        intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                                        MyUploadsPagerAcrivity.this.startActivity(intent1);
                                        break;
                                    case 2:
                                        intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                                        intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                                        MyUploadsPagerAcrivity.this.startActivity(intent1);
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
                                intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                                intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                                MyUploadsPagerAcrivity.this.startActivity(intent1);
                                break;
                            case 1:
                                intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                                intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                                MyUploadsPagerAcrivity.this.startActivity(intent1);
                                break;
                            case 2:
                                intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                                intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                                MyUploadsPagerAcrivity.this.startActivity(intent1);
                                break;
                            default:
                                break;
                        }
                        return;
                    }
                } else if (wallQuality.equals("Regular")) {
                    switch (i) {
                        case 0:
                            intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                            intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                            MyUploadsPagerAcrivity.this.startActivity(intent1);
                            break;
                        case 1:
                            intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                            intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                            MyUploadsPagerAcrivity.this.startActivity(intent1);
                            break;
                        case 2:
                            intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                            intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                            MyUploadsPagerAcrivity.this.startActivity(intent1);
                            break;
                        default:
                            break;
                    }
                    return;
                }
                i = -1;
                switch (i) {
                    case 0:
                        intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                        intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                        MyUploadsPagerAcrivity.this.startActivity(intent1);
                        break;
                    case 1:
                        intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                        intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                        MyUploadsPagerAcrivity.this.startActivity(intent1);
                        break;
                    case 2:
                        intent1 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
                        intent1.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
                        MyUploadsPagerAcrivity.this.startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return;
            }
            Intent intent12 = new Intent(MyUploadsPagerAcrivity.this, SetWallpaperActivity.class);
            intent12.putExtra("imageURL", ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem())).getCompressedURL());
            MyUploadsPagerAcrivity.this.startActivity(intent12);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MyUploadsPagerAcrivity$4 */
    class C14104 implements OnClickListener {
        C14104() {
        }

        public void onClick(View v) {
            Toast.makeText(MyUploadsPagerAcrivity.this.getApplicationContext(), "Will be Available Shortly", 0).show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MyUploadsPagerAcrivity$5 */
    class C14125 implements OnClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.MyUploadsPagerAcrivity$5$1 */
        class C14111 implements DialogInterface.OnClickListener {
            C14111() {
            }

            public void onClick(DialogInterface dialog, int which) {
                FirebaseDailyShots wallpaper = (FirebaseDailyShots) MyUploadActivity.wallpaperList.get(MyUploadsPagerAcrivity.this.imageViewPager.getCurrentItem());
                String imageUri = "";
                String fileName = "";
                switch (which) {
                    case 0:
                        imageUri = wallpaper.getCompressedURL();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("photo-");
                        stringBuilder.append(wallpaper.getDeviceName());
                        stringBuilder.append("-regular.jpg");
                        fileName = stringBuilder.toString();
                        break;
                    case 1:
                        Toast.makeText(MyUploadsPagerAcrivity.this.getApplicationContext(), "Orignal Image Not Available", 0).show();
                        break;
                    default:
                        break;
                }
                if (VERSION.SDK_INT > 22 && MyUploadsPagerAcrivity.this.getApplicationContext().checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    Log.d(MyUploadsPagerAcrivity.TAG, "onClick: Asking for permissions...");
                    MyUploadsPagerAcrivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 200);
                }
                MyUploadsPagerAcrivity.this.downloadImageToLocal(fileName, imageUri);
            }
        }

        C14125() {
        }

        public void onClick(View v) {
            Builder builder = new Builder(MyUploadsPagerAcrivity.this);
            builder.setTitle("Select image size to download:");
            ArrayAdapter<String> adapter = new ArrayAdapter(MyUploadsPagerAcrivity.this, 17367043);
            adapter.add("Regular (Recommended)");
            adapter.add("Orignal");
            builder.setAdapter(adapter, new C14111());
            builder.show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MyUploadsPagerAcrivity$1 */
    class C17891 implements PageTransformer {
        C17891() {
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

    public class SliderViewPagerAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;
        String url;
        String userName;

        public int getCount() {
            return MyUploadActivity.wallpaperList.size();
        }

        public Object instantiateItem(ViewGroup container, int position) {
            this.layoutInflater = (LayoutInflater) MyUploadsPagerAcrivity.this.getSystemService("layout_inflater");
            View view = this.layoutInflater.inflate(C1420R.layout.image_fragment_preview, container, false);
            view.setTag(Integer.valueOf(position));
            final ImageViewTouch previewImageView = (ImageViewTouch) view.findViewById(C1420R.id.image_preview);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(C1420R.id.progress_bar);
            FirebaseDailyShots wallpaper = (FirebaseDailyShots) MyUploadActivity.wallpaperList.get(position);
            this.userName = "Photo by admin / Firebase";
            Glide.with(MyUploadsPagerAcrivity.this).load(wallpaper.getCompressedURL()).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    Log.d(MyUploadsPagerAcrivity.TAG, "onResourceReady: Loaded!");
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
            if (MyUploadsPagerAcrivity.this.imageViewPager.findViewWithTag(Integer.valueOf(position + 1)) != null) {
                String access$100 = MyUploadsPagerAcrivity.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("testRefresh: View at ");
                stringBuilder.append(position + 1);
                stringBuilder.append(" Found!");
                Log.d(access$100, stringBuilder.toString());
                if (MyUploadsPagerAcrivity.this.creditsLayout != null) {
                    access$100 = MyUploadsPagerAcrivity.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("testRefresh: creditsLayout at ");
                    stringBuilder.append(position + 1);
                    stringBuilder.append(" set to ");
                    stringBuilder.append(MyUploadsPagerAcrivity.this.creditsLayout.getAlpha());
                    Log.d(access$100, stringBuilder.toString());
                    return;
                }
                Log.e(MyUploadsPagerAcrivity.TAG, "testRefresh: creditsTextView not found!");
                return;
            }
            Log.e(MyUploadsPagerAcrivity.TAG, "testRefresh: View not found!");
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
        setContentView(C1420R.layout.activity_my_uploads_pager);
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
        this.editor.putBoolean("slide8", true);
        this.editor.commit();
        this.creditsTextView = (TextView) findViewById(C1420R.id.wallpaperUploaderID);
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        if (this.extras != null) {
            currentPosition = Integer.parseInt(this.extras.getString("position").trim());
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateView: wallpaperlist size: ");
            stringBuilder.append(MyUploadActivity.wallpaperList.size());
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
        this.imageViewPager.setPageTransformer(false, new C17891());
        this.shareBtn.setOnClickListener(new C14082());
        this.setWallBtn.setOnClickListener(new C14093());
        this.origBtn.setOnClickListener(new C14104());
        this.downloadBtn.setOnClickListener(new C14125());
    }

    public void downloadImageToLocal(String fileName, String imageUri) {
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
                startActivityForResult(new AdobeImageIntent.Builder(this).setData(Uri.parse(((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(this.imageViewPager.getCurrentItem())).getCompressedURL())).build(), 9);
            } else if (editPrefs.getString("editing", "Regular") == "Regular") {
                startActivityForResult(new AdobeImageIntent.Builder(this).setData(Uri.parse(((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(this.imageViewPager.getCurrentItem())).getCompressedURL())).build(), 9);
            } else {
                startActivityForResult(new AdobeImageIntent.Builder(this).setData(Uri.parse(((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(this.imageViewPager.getCurrentItem())).getCompressedURL())).build(), 9);
            }
        } else if (itemId != C1420R.id.favourites_menuID) {
            if (itemId == C1420R.id.slideshow_menuID) {
                if (this.preferences.contains("slide")) {
                    if (this.preferences.getBoolean("slide8", true)) {
                        this.editor.putBoolean("slide8", true);
                        this.editor.commit();
                        this.imageViewPager.startAutoScroll();
                        this.imageViewPager.setInterval(2500);
                        this.imageViewPager.setCycle(false);
                        this.imageViewPager.setAutoScrollDurationFactor(10.0d);
                        Toast.makeText(getApplicationContext(), "Slide Show Started", 0).show();
                        Log.d(TAG, "Slide Show Started");
                        this.editor.putBoolean("slide8", false);
                        this.editor.commit();
                    } else {
                        this.editor.putBoolean("slide8", false);
                        this.editor.commit();
                        this.imageViewPager.stopAutoScroll();
                        this.imageViewPager.stopNestedScroll();
                        Toast.makeText(getApplicationContext(), "Slide Show Stopped", 0).show();
                        Log.d(TAG, "Slide Show Stopped");
                        this.editor.putBoolean("slide8", true);
                        this.editor.commit();
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
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
