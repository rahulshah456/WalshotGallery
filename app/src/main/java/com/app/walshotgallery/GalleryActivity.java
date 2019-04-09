package com.app.walshotgallery;

import Adapters.GalleryAdapter;
import Adapters.GalleryAdapter.OnItemClickListener;
import Fragments.GalleryImagesFragment;
import Modals.ImagesFolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    public static final String BUNDLE_LIST_STATE = "list_state";
    private static final int NUM_COLUMNS = 2;
    private static final String TAG = GalleryActivity.class.getSimpleName();
    private static int currentPosition;
    public static GalleryAdapter imageListAdapter;
    public static ArrayList<String> mImages;
    private Bundle extras;
    private LayoutManager mLayoutManager;
    private Parcelable mListState;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryActivity$1 */
    class C17581 implements OnItemClickListener {
        C17581() {
        }

        public void OnItemClick(int position) {
            Intent intent = new Intent(GalleryActivity.this, GalleryPagerActivity.class);
            intent.putExtra("itemPosition", position);
            intent.putExtra("folderPosition", GalleryActivity.currentPosition);
            GalleryActivity.this.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(GalleryActivity.this, GalleryActivity.this.recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(C1420R.id.image_thumbnail), "imagePreview").toBundle());
        }

        public void OnItemLongClick(int position) {
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryActivity$2 */
    class C17592 extends OnScrollListener {
        C17592() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == 0) {
                recyclerView.invalidateItemDecorations();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(67108864, 67108864);
        setContentView(C1420R.layout.activity_gallery);
        mImages = new ArrayList();
        this.toolbar = (Toolbar) findViewById(C1420R.id.toolbarWallpapersViewID);
        this.toolbar.setTitle("Gallery Folder");
        setSupportActionBar(this.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.getClass();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.recyclerView = (RecyclerView) findViewById(C1420R.id.images_recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.extras = getIntent().getExtras();
        if (this.extras != null) {
            String string = this.extras.getString("position");
            string.getClass();
            currentPosition = Integer.parseInt(string.trim());
            this.toolbar.setTitle(((ImagesFolder) GalleryImagesFragment.folders.get(currentPosition)).getAllFolderName());
            mImages = ((ImagesFolder) GalleryImagesFragment.folders.get(currentPosition)).getAllImagePaths();
        }
        if (mImages.size() != 0) {
            this.mLayoutManager = new GridLayoutManager(this, 2);
            imageListAdapter = new GalleryAdapter(this, mImages);
            this.staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
            this.recyclerView.setLayoutManager(this.staggeredGridLayoutManager);
            this.recyclerView.setItemAnimator(new DefaultItemAnimator());
            this.recyclerView.setAdapter(imageListAdapter);
            this.recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
            this.recyclerView.setHasFixedSize(true);
            imageListAdapter.setOnItemClickListener(new C17581());
        }
        this.recyclerView.addOnScrollListener(new C17592());
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onSaveInstanceState(Bundle outState) {
        this.mListState = this.mLayoutManager.onSaveInstanceState();
        outState.putParcelable("list_state", this.mListState);
        super.onSaveInstanceState(outState);
    }

    public void onResume() {
        if (this.mListState != null) {
            Log.d(TAG, "onResume: Restoring list state ...");
            this.mLayoutManager.onRestoreInstanceState(this.mListState);
        } else {
            Log.d(TAG, "onResume: ListState empty!");
        }
        super.onResume();
    }

    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
