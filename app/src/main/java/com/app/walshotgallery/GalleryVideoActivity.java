package com.app.walshotgallery;

import Adapters.AutoPlayAdapter;
import Adapters.AutoPlayAdapter.OnItemClickListener;
import Fragments.GalleryVideosFragment;
import Modals.VideosFolder;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import java.util.ArrayList;

public class GalleryVideoActivity extends AppCompatActivity {
    public static final String BUNDLE_LIST_STATE = "list_state";
    private static final int NUM_COLUMNS = 2;
    private static final String TAG = GalleryActivity.class.getSimpleName();
    private static int currentPosition;
    public static AutoPlayAdapter imageListAdapter;
    public static ArrayList<String> mThumbnails;
    public static ArrayList<String> mVideos;
    private Bundle extras;
    private LayoutManager mLayoutManager;
    private Parcelable mListState;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.GalleryVideoActivity$1 */
    class C17631 implements OnItemClickListener {
        C17631() {
        }

        public void OnItemClick(int position) {
            Intent intent = new Intent(GalleryVideoActivity.this, AutoPlayActivity.class);
            intent.putExtra("itemPosition", position);
            intent.putExtra("folderPosition", GalleryVideoActivity.currentPosition);
            GalleryVideoActivity.this.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(GalleryVideoActivity.this, GalleryVideoActivity.this.recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(C1420R.id.autoplayVideoID), "autoPlay").toBundle());
        }

        public void OnItemLongClick(int position) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(67108864, 67108864);
        setContentView(C1420R.layout.activity_gallery_video);
        mVideos = new ArrayList();
        mThumbnails = new ArrayList();
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
            this.toolbar.setTitle(((VideosFolder) GalleryVideosFragment.videosFolders.get(currentPosition)).getAllFolderName());
            mVideos = ((VideosFolder) GalleryVideosFragment.videosFolders.get(currentPosition)).getAllVideoPaths();
            mThumbnails = ((VideosFolder) GalleryVideosFragment.videosFolders.get(currentPosition)).getAllVideoThumbnails();
        }
        if (mVideos.size() != 0) {
            this.mLayoutManager = new GridLayoutManager(this, 1);
            imageListAdapter = new AutoPlayAdapter(this, mThumbnails);
            this.recyclerView.setLayoutManager(this.mLayoutManager);
            this.recyclerView.setItemAnimator(new DefaultItemAnimator());
            this.recyclerView.setAdapter(imageListAdapter);
            this.recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
            this.recyclerView.setHasFixedSize(true);
            imageListAdapter.setOnItemClickListener(new C17631());
        }
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
