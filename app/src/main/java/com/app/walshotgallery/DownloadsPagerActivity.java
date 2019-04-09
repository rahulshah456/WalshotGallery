package com.app.walshotgallery;

import Modals.AutoScrollViewPager;
import Modals.DeviceImages;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.adobe.creativesdk.aviary.AdobeImageIntent.Builder;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DownloadsPagerActivity extends AppCompatActivity {
    public static final String BUNDLE_LIST_STATE = "list_state";
    private static final int IMAGE_EDITOR_RESULT = 9;
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = EditorsPagerActivity.class.getSimpleName();
    private static int currentPosition;
    private ConstraintLayout creditsLayout;
    private ImageView deleteBtn;
    private ImageView editBtn;
    Editor editor;
    private Bundle extras;
    private TextView imageName;
    private AutoScrollViewPager imageViewPager;
    private Parcelable mListState;
    private SliderViewPagerAdapter mPagerAdapter;
    private DeviceImages mWallpaper;
    private SharedPreferences preferences;
    private ImageView setWallBtn;
    private ImageView shareBtn;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.DownloadsPagerActivity$2 */
    class C13592 implements OnClickListener {
        C13592() {
        }

        public void onClick(View v) {
            String imageUri = "";
            imageUri = ((DeviceImages) UserDownloadsActivity.mImagesList.get(DownloadsPagerActivity.this.imageViewPager.getCurrentItem())).getUri().toString();
            Intent intent = new Intent(DownloadsPagerActivity.this, SetWallpaperActivity.class);
            intent.putExtra("imageURL", imageUri);
            DownloadsPagerActivity.this.startActivity(intent);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.DownloadsPagerActivity$3 */
    class C13603 implements OnClickListener {
        C13603() {
        }

        public void onClick(View v) {
            File imageFile = new File(((DeviceImages) UserDownloadsActivity.mImagesList.get(DownloadsPagerActivity.this.imageViewPager.getCurrentItem())).getUri().getPath());
            Uri bitmapUri = DownloadsPagerActivity.this.getApplicationContext();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(DownloadsPagerActivity.this.getPackageName());
            stringBuilder.append(".provider");
            bitmapUri = FileProvider.getUriForFile(bitmapUri, stringBuilder.toString(), imageFile);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.STREAM", bitmapUri);
            DownloadsPagerActivity.this.startActivity(Intent.createChooser(intent, "Share"));
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.DownloadsPagerActivity$4 */
    class C13614 implements OnClickListener {
        C13614() {
        }

        public void onClick(View v) {
            DownloadsPagerActivity.this.startActivityForResult(new Builder(DownloadsPagerActivity.this.getApplicationContext()).setData(Uri.parse(((DeviceImages) UserDownloadsActivity.mImagesList.get(DownloadsPagerActivity.this.imageViewPager.getCurrentItem())).getUri().toString())).build(), 9);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.DownloadsPagerActivity$5 */
    class C13645 implements OnClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.DownloadsPagerActivity$5$1 */
        class C13621 implements DialogInterface.OnClickListener {
            C13621() {
            }

            public void onClick(DialogInterface dialog, int which) {
                new File(((DeviceImages) UserDownloadsActivity.mImagesList.get(DownloadsPagerActivity.this.imageViewPager.getCurrentItem())).getUri().getPath()).delete();
                UserDownloadsActivity.mImagesList.remove(DownloadsPagerActivity.this.imageViewPager.getCurrentItem());
                UserDownloadsActivity.imageListAdapter.notifyItemRemoved(DownloadsPagerActivity.this.imageViewPager.getCurrentItem());
                DownloadsPagerActivity.this.mPagerAdapter.notifyDataSetChanged();
            }
        }

        /* renamed from: com.walshotbeta.walshotvbeta.DownloadsPagerActivity$5$2 */
        class C13632 implements DialogInterface.OnClickListener {
            C13632() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }

        C13645() {
        }

        public void onClick(View v) {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(DownloadsPagerActivity.this);
            alertDialog2.setTitle("Confirm Delete...");
            alertDialog2.setMessage("Are you sure you want delete this file?");
            alertDialog2.setPositiveButton("YES", new C13621());
            alertDialog2.setNegativeButton("NO", new C13632());
            alertDialog2.show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.DownloadsPagerActivity$1 */
    class C17431 implements PageTransformer {
        C17431() {
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

    /* renamed from: com.walshotbeta.walshotvbeta.DownloadsPagerActivity$6 */
    class C17446 implements OnPageChangeListener {
        C17446() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            DownloadsPagerActivity.this.imageName.setText(((DeviceImages) UserDownloadsActivity.mImagesList.get(DownloadsPagerActivity.this.imageViewPager.getCurrentItem())).getName());
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public class SliderViewPagerAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;
        String url;
        String userName;

        public int getCount() {
            return UserDownloadsActivity.mImagesList.size();
        }

        public Object instantiateItem(ViewGroup container, int position) {
            this.layoutInflater = (LayoutInflater) DownloadsPagerActivity.this.getSystemService("layout_inflater");
            View view = this.layoutInflater.inflate(C1420R.layout.image_viewpager_preview, container, false);
            view.setTag(Integer.valueOf(position));
            SubsamplingScaleImageView previewImageView = (SubsamplingScaleImageView) view.findViewById(C1420R.id.image_preview);
            ProgressBar progressBar = (ProgressBar) view.findViewById(C1420R.id.progress_bar);
            DeviceImages wallpaper = (DeviceImages) UserDownloadsActivity.mImagesList.get(position);
            String access$300 = DownloadsPagerActivity.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("instantiateItem: WallpaperURL: ");
            stringBuilder.append(wallpaper.getUri());
            Log.d(access$300, stringBuilder.toString());
            this.userName = "Photo by admin / Firebase";
            progressBar.setVisibility(4);
            previewImageView.setImage(ImageSource.uri(wallpaper.getUri()));
            container.addView(view, 0);
            return view;
        }

        public void testRefresh(int position) {
            if (DownloadsPagerActivity.this.imageViewPager.findViewWithTag(Integer.valueOf(position + 1)) != null) {
                String access$300 = DownloadsPagerActivity.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("testRefresh: View at ");
                stringBuilder.append(position + 1);
                stringBuilder.append(" Found!");
                Log.d(access$300, stringBuilder.toString());
                if (DownloadsPagerActivity.this.creditsLayout != null) {
                    access$300 = DownloadsPagerActivity.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("testRefresh: creditsLayout at ");
                    stringBuilder.append(position + 1);
                    stringBuilder.append(" set to ");
                    stringBuilder.append(DownloadsPagerActivity.this.creditsLayout.getAlpha());
                    Log.d(access$300, stringBuilder.toString());
                    return;
                }
                Log.e(DownloadsPagerActivity.TAG, "testRefresh: creditsTextView not found!");
                return;
            }
            Log.e(DownloadsPagerActivity.TAG, "testRefresh: View not found!");
        }

        public int getItemPosition(Object object) {
            return -2;
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
        setContentView(C1420R.layout.activity_downloads_pager);
        this.extras = getIntent().getExtras();
        this.toolbar = (Toolbar) findViewById(C1420R.id.pagerToolbarID);
        this.toolbar.setTitle(StringUtils.SPACE);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.imageViewPager = (AutoScrollViewPager) findViewById(C1420R.id.sliderViewPagerID);
        this.deleteBtn = (ImageView) findViewById(C1420R.id.menu_apply);
        this.shareBtn = (ImageView) findViewById(C1420R.id.menu_save);
        this.editBtn = (ImageView) findViewById(C1420R.id.menu_save3);
        this.setWallBtn = (ImageView) findViewById(C1420R.id.menu_save2);
        this.imageName = (TextView) findViewById(C1420R.id.imageNameID);
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.preferences = getSharedPreferences(PREF_NAME, 0);
        this.editor = this.preferences.edit();
        this.editor.putBoolean("slide9", true);
        this.editor.commit();
        if (this.extras != null) {
            currentPosition = Integer.parseInt(this.extras.getString("position").trim());
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateView: wallpaperlist size: ");
            stringBuilder.append(UserDownloadsActivity.mImagesList.size());
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
        this.imageViewPager.setPageTransformer(false, new C17431());
        this.imageName.setText(((DeviceImages) UserDownloadsActivity.mImagesList.get(this.imageViewPager.getCurrentItem())).getName());
        this.setWallBtn.setOnClickListener(new C13592());
        this.shareBtn.setOnClickListener(new C13603());
        this.editBtn.setOnClickListener(new C13614());
        this.deleteBtn.setOnClickListener(new C13645());
        this.imageViewPager.addOnPageChangeListener(new C17446());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1420R.menu.device_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId != C1420R.id.favourites_menuID) {
            if (itemId == C1420R.id.slideshow_menuID) {
                if (this.preferences.contains("slide9")) {
                    if (this.preferences.getBoolean("slide9", true)) {
                        this.editor.putBoolean("slide9", true);
                        this.editor.commit();
                        this.imageViewPager.startAutoScroll();
                        this.imageViewPager.setInterval(2500);
                        this.imageViewPager.setCycle(false);
                        this.imageViewPager.setAutoScrollDurationFactor(10.0d);
                        Toast.makeText(getApplicationContext(), "Slide Show Started", 0).show();
                        Log.d(TAG, "Slide Show Started");
                        this.editor.putBoolean("slide9", false);
                        this.editor.commit();
                    } else {
                        this.editor.putBoolean("slide9", false);
                        this.editor.commit();
                        this.imageViewPager.stopAutoScroll();
                        this.imageViewPager.stopNestedScroll();
                        Toast.makeText(getApplicationContext(), "Slide Show Stopped", 0).show();
                        Log.d(TAG, "Slide Show Stopped");
                        this.editor.putBoolean("slide9", true);
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
