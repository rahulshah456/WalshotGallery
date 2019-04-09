package com.app.walshotgallery;

import Fragments.GalleryImagesFragment;
import Modals.AutoScrollViewPager;
import Modals.ImagesFolder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import java.io.File;
import java.text.DecimalFormat;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class GalleryPagerActivity extends AppCompatActivity {
    public static final String BUNDLE_LIST_STATE = "list_state";
    private static final int IMAGE_EDITOR_RESULT = 9;
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = GalleryPagerActivity.class.getSimpleName();
    private static int currentFolderPosition;
    private static int currentItemPosition;
    private ConstraintLayout creditsLayout;
    private ImageView deleteBtn;
    private TextView dimension;
    private ImageView editBtn;
    Editor editor;
    private Bundle extras;
    private TextView format;
    private TextView imageName;
    private AutoScrollViewPager imageViewPager;
    private InterstitialAd mInterstitialAd;
    private Parcelable mListState;
    private SliderViewPagerAdapter mPagerAdapter;
    private SharedPreferences preferences;
    private ImageView setWallBtn;
    private ImageView shareBtn;
    private TextView size;
    private TextView timeCreated;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryPagerActivity$3 */
    class C13763 implements OnClickListener {
        C13763() {
        }

        public void onClick(View v) {
            String imageUri = "";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("file://");
            stringBuilder.append((String) GalleryActivity.mImages.get(GalleryPagerActivity.this.imageViewPager.getCurrentItem()));
            imageUri = stringBuilder.toString();
            Intent intent = new Intent(GalleryPagerActivity.this, SetWallpaperActivity.class);
            intent.putExtra("imageURL", imageUri);
            GalleryPagerActivity.this.startActivity(intent);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryPagerActivity$4 */
    class C13774 implements OnClickListener {
        C13774() {
        }

        public void onClick(View v) {
            File imageFile = new File((String) GalleryActivity.mImages.get(GalleryPagerActivity.this.imageViewPager.getCurrentItem()));
            Uri bitmapUri = GalleryPagerActivity.this.getApplicationContext();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(GalleryPagerActivity.this.getPackageName());
            stringBuilder.append(".provider");
            bitmapUri = FileProvider.getUriForFile(bitmapUri, stringBuilder.toString(), imageFile);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.STREAM", bitmapUri);
            GalleryPagerActivity.this.startActivity(Intent.createChooser(intent, "Share"));
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryPagerActivity$5 */
    class C13785 implements OnClickListener {
        C13785() {
        }

        public void onClick(View v) {
            GalleryPagerActivity.this.startActivityForResult(new Builder(GalleryPagerActivity.this.getApplicationContext()).setData(Uri.parse((String) GalleryActivity.mImages.get(GalleryPagerActivity.this.imageViewPager.getCurrentItem()))).build(), 9);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryPagerActivity$6 */
    class C13816 implements OnClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.GalleryPagerActivity$6$1 */
        class C13791 implements DialogInterface.OnClickListener {
            C13791() {
            }

            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.fromFile(new File((String) GalleryActivity.mImages.get(GalleryPagerActivity.this.imageViewPager.getCurrentItem())));
                GalleryPagerActivity.this.scanDeleteFile((String) GalleryActivity.mImages.get(GalleryPagerActivity.this.imageViewPager.getCurrentItem()));
                ((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImagePaths().remove(GalleryPagerActivity.this.imageViewPager.getCurrentItem());
                ((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImageDateTaken().remove(GalleryPagerActivity.this.imageViewPager.getCurrentItem());
                ((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImageHeight().remove(GalleryPagerActivity.this.imageViewPager.getCurrentItem());
                ((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImageWidth().remove(GalleryPagerActivity.this.imageViewPager.getCurrentItem());
                ((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImageSize().remove(GalleryPagerActivity.this.imageViewPager.getCurrentItem());
                GalleryActivity.imageListAdapter.notifyItemRemoved(GalleryPagerActivity.this.imageViewPager.getCurrentItem());
                GalleryImagesFragment.galleryFolderAdapter.notifyDataSetChanged();
                GalleryPagerActivity.this.mPagerAdapter.notifyDataSetChanged();
                if (((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImagePaths().size() == 0) {
                    GalleryImagesFragment.folders.remove(GalleryPagerActivity.currentFolderPosition);
                    GalleryPagerActivity.this.onBackPressed();
                }
            }
        }

        /* renamed from: com.walshotbeta.walshotvbeta.GalleryPagerActivity$6$2 */
        class C13802 implements DialogInterface.OnClickListener {
            C13802() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }

        C13816() {
        }

        public void onClick(View v) {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(GalleryPagerActivity.this);
            alertDialog2.setTitle("Confirm Delete...");
            alertDialog2.setMessage("Are you sure you want to delete this file?");
            alertDialog2.setPositiveButton("YES", new C13791());
            alertDialog2.setNegativeButton("NO", new C13802());
            alertDialog2.show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryPagerActivity$8 */
    class C13828 implements OnScanCompletedListener {
        C13828() {
        }

        public void onScanCompleted(String path, Uri uri) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Scanned ");
            stringBuilder.append(path);
            stringBuilder.append(":");
            Log.d("ExternalStorage", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("-> uri=");
            stringBuilder.append(uri);
            Log.d("ExternalStorage", stringBuilder.toString());
            GalleryPagerActivity.this.getApplicationContext().getContentResolver().delete(uri, null, null);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryPagerActivity$1 */
    class C17601 extends AdListener {
        C17601() {
        }

        public void onAdClosed() {
            GalleryPagerActivity.this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryPagerActivity$2 */
    class C17612 implements PageTransformer {
        C17612() {
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

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryPagerActivity$7 */
    class C17627 implements OnPageChangeListener {
        C17627() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            String name = (String) GalleryActivity.mImages.get(GalleryPagerActivity.this.imageViewPager.getCurrentItem());
            GalleryPagerActivity.this.imageName.setText(name.substring(name.lastIndexOf(47) + 1).trim());
            Integer width = (Integer) ((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImageWidth().get(GalleryPagerActivity.this.imageViewPager.getCurrentItem());
            Integer height = (Integer) ((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImageHeight().get(GalleryPagerActivity.this.imageViewPager.getCurrentItem());
            TextView access$600 = GalleryPagerActivity.this.dimension;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(width);
            stringBuilder.append(" X ");
            stringBuilder.append(height);
            access$600.setText(stringBuilder.toString());
            GalleryPagerActivity.this.format.setText("JPG");
            GalleryPagerActivity.this.timeCreated.setText((CharSequence) ((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImageDateTaken().get(GalleryPagerActivity.this.imageViewPager.getCurrentItem()));
            if (((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImageSize().get(GalleryPagerActivity.this.imageViewPager.getCurrentItem()) != null) {
                GalleryPagerActivity.this.size.setText(GalleryPagerActivity.formatFileSize((long) ((Integer) ((ImagesFolder) GalleryImagesFragment.folders.get(GalleryPagerActivity.currentFolderPosition)).getAllImageSize().get(GalleryPagerActivity.this.imageViewPager.getCurrentItem())).intValue()));
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    public class SliderViewPagerAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;
        String url;
        String userName;
        View view;

        public int getCount() {
            return GalleryActivity.mImages.size();
        }

        public Object instantiateItem(ViewGroup container, int position) {
            this.layoutInflater = (LayoutInflater) GalleryPagerActivity.this.getSystemService("layout_inflater");
            this.view = this.layoutInflater.inflate(C1420R.layout.image_viewpager_preview, container, false);
            this.view.setTag(Integer.valueOf(position));
            SubsamplingScaleImageView previewImageView = (SubsamplingScaleImageView) this.view.findViewById(C1420R.id.image_preview);
            ((ProgressBar) this.view.findViewById(C1420R.id.progress_bar)).setVisibility(4);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("file://");
            stringBuilder.append((String) GalleryActivity.mImages.get(position));
            previewImageView.setImage(ImageSource.uri(stringBuilder.toString()));
            container.addView(this.view, 0);
            return this.view;
        }

        public void testRefresh(int position) {
            if (GalleryPagerActivity.this.imageViewPager.findViewWithTag(Integer.valueOf(position + 1)) != null) {
                String access$1000 = GalleryPagerActivity.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("testRefresh: View at ");
                stringBuilder.append(position + 1);
                stringBuilder.append(" Found!");
                Log.d(access$1000, stringBuilder.toString());
                if (GalleryPagerActivity.this.creditsLayout != null) {
                    access$1000 = GalleryPagerActivity.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("testRefresh: creditsLayout at ");
                    stringBuilder.append(position + 1);
                    stringBuilder.append(" set to ");
                    stringBuilder.append(GalleryPagerActivity.this.creditsLayout.getAlpha());
                    Log.d(access$1000, stringBuilder.toString());
                    return;
                }
                Log.e(GalleryPagerActivity.TAG, "testRefresh: creditsTextView not found!");
                return;
            }
            Log.e(GalleryPagerActivity.TAG, "testRefresh: View not found!");
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

        public void removeView(int position) {
            GalleryPagerActivity.this.imageViewPager.removeViewAt(position);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        StringBuilder stringBuilder;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(512, 512);
        setContentView(C1420R.layout.activity_gallery_pager);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        this.mInterstitialAd = new InterstitialAd(this);
        this.mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
        this.mInterstitialAd.setAdListener(new C17601());
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
        this.editor.putBoolean("slide10", true);
        this.editor.commit();
        this.dimension = (TextView) findViewById(C1420R.id.dimensionsID);
        this.size = (TextView) findViewById(C1420R.id.sizeID);
        this.format = (TextView) findViewById(C1420R.id.imageFormatID);
        this.timeCreated = (TextView) findViewById(C1420R.id.dateCreatedID);
        this.extras = getIntent().getExtras();
        if (this.extras != null) {
            currentItemPosition = this.extras.getInt("itemPosition");
            currentFolderPosition = this.extras.getInt("folderPosition");
            String str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateView: wallpaperlist size: ");
            stringBuilder.append(GalleryActivity.mImages.size());
            Log.d(str, stringBuilder.toString());
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("onCreateView: currentPosition: ");
            stringBuilder.append(currentItemPosition);
            Log.d(str, stringBuilder.toString());
        }
        this.mPagerAdapter = new SliderViewPagerAdapter();
        this.imageViewPager.setAdapter(this.mPagerAdapter);
        this.imageViewPager.setCurrentItem(currentItemPosition);
        this.imageViewPager.setPageTransformer(false, new C17612());
        String name = (String) GalleryActivity.mImages.get(this.imageViewPager.getCurrentItem());
        this.imageName.setText(name.substring(name.lastIndexOf(47) + 1).trim());
        Integer width = (Integer) ((ImagesFolder) GalleryImagesFragment.folders.get(currentFolderPosition)).getAllImageWidth().get(currentItemPosition);
        Integer height = (Integer) ((ImagesFolder) GalleryImagesFragment.folders.get(currentFolderPosition)).getAllImageHeight().get(currentItemPosition);
        TextView textView = this.dimension;
        stringBuilder = new StringBuilder();
        stringBuilder.append(width);
        stringBuilder.append(" X ");
        stringBuilder.append(height);
        textView.setText(stringBuilder.toString());
        this.format.setText("JPG");
        this.timeCreated.setText((CharSequence) ((ImagesFolder) GalleryImagesFragment.folders.get(currentFolderPosition)).getAllImageDateTaken().get(currentItemPosition));
        if (((ImagesFolder) GalleryImagesFragment.folders.get(currentFolderPosition)).getAllImageSize().get(currentItemPosition) != null) {
            this.size.setText(formatFileSize((long) ((Integer) ((ImagesFolder) GalleryImagesFragment.folders.get(currentFolderPosition)).getAllImageSize().get(currentItemPosition)).intValue()));
        }
        this.setWallBtn.setOnClickListener(new C13763());
        this.shareBtn.setOnClickListener(new C13774());
        this.editBtn.setOnClickListener(new C13785());
        this.deleteBtn.setOnClickListener(new C13816());
        this.imageViewPager.addOnPageChangeListener(new C17627());
    }

    private void scanDeleteFile(String path) {
        try {
            MediaScannerConnection.scanFile(this, new String[]{path}, null, new C13828());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1420R.menu.device_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C1420R.id.copy_to_folderID:
                break;
            case C1420R.id.dailyShot_uploadID:
                break;
            case C1420R.id.edit_inID:
                this.mInterstitialAd.show();
                break;
            case C1420R.id.favourites_menuID:
                break;
            case C1420R.id.move_to_folderID:
                break;
            case C1420R.id.rotate_leftID:
                Toast.makeText(getApplicationContext(), "Coming Soon", 0).show();
                break;
            case C1420R.id.rotate_rightID:
                Toast.makeText(getApplicationContext(), "Coming Soon", 0).show();
                break;
            case C1420R.id.slideshow_menuID:
                if (this.preferences.contains("slide10")) {
                    if (!this.preferences.getBoolean("slide10", true)) {
                        this.editor.putBoolean("slide10", false);
                        this.editor.commit();
                        this.imageViewPager.stopAutoScroll();
                        this.imageViewPager.stopNestedScroll();
                        Toast.makeText(getApplicationContext(), "Slide Show Stopped", 0).show();
                        Log.d(TAG, "Slide Show Stopped");
                        this.editor.putBoolean("slide10", true);
                        this.editor.commit();
                        break;
                    }
                    this.editor.putBoolean("slide", true);
                    this.editor.commit();
                    this.imageViewPager.startAutoScroll();
                    this.imageViewPager.setInterval(2500);
                    this.imageViewPager.setCycle(false);
                    this.imageViewPager.setAutoScrollDurationFactor(10.0d);
                    Toast.makeText(getApplicationContext(), "Slide Show Started", 0).show();
                    Log.d(TAG, "Slide Show Started");
                    this.editor.putBoolean("slide10", false);
                    this.editor.commit();
                    break;
                }
                break;
            case C1420R.id.use_asID:
                break;
            default:
                break;
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

    public static String formatFileSize(long size) {
        long j = size;
        double b = (double) j;
        double k = ((double) j) / 1024.0d;
        double m = (((double) j) / 1024.0d) / 1024.0d;
        double g = ((((double) j) / 1024.0d) / 1024.0d) / 1024.0d;
        double t = (((((double) j) / 1024.0d) / 1024.0d) / 1024.0d) / 1024.0d;
        DecimalFormat dec = new DecimalFormat("0.00");
        if (t > 1.0d) {
            return dec.format(t).concat(" TB");
        }
        if (g > 1.0d) {
            return dec.format(g).concat(" GB");
        }
        if (m > 1.0d) {
            return dec.format(m).concat(" MB");
        }
        if (k > 1.0d) {
            return dec.format(k).concat(" KB");
        }
        return dec.format(b).concat(" Bytes");
    }
}
