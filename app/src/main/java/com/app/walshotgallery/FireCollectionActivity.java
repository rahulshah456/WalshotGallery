package com.app.walshotgallery;

import Adapters.ConnectionDetector;
import Adapters.EditorsImageListAdapter;
import Adapters.EditorsImageListAdapter.OnItemClickListener;
import Modals.FirebaseWallpapers;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FireCollectionActivity extends AppCompatActivity {
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    private static final String TAG = "FireCollectionActivity";
    static int page = 0;
    public static List<FirebaseWallpapers> wallpaperList;
    private String collectionId;
    private RelativeLayout connectionLost;
    FirebaseDatabase database;
    ValueEventListener eventListener;
    private Bundle extras;
    EditorsImageListAdapter imageListAdaper;
    private RecyclerView imageRecyclerView;
    private boolean isLoading = false;
    int itemsPerPage = 10;
    DatabaseReference likeDatabase;
    private String listCategory;
    private RelativeLayout loadingLayout;
    FirebaseAuth mAuth;
    String mLastKey = null;
    private LayoutManager mLayoutManager;
    private Parcelable mListState;
    private ConnectionDetector networkState;
    private ImageView noConnectionGif;
    DatabaseReference recyclerDatabaseReference;
    private Animation slide_down;
    private Animation slide_up;
    private SwipeRefreshLayout swipeLayout;
    private Toolbar toolbar;
    FirebaseWallpapers wallpapers;

    /* renamed from: com.walshotbeta.walshotvbeta.FireCollectionActivity$1 */
    class C17531 implements OnRefreshListener {

        /* renamed from: com.walshotbeta.walshotvbeta.FireCollectionActivity$1$1 */
        class C13751 implements Runnable {
            C13751() {
            }

            public void run() {
                FireCollectionActivity.this.swipeLayout.setRefreshing(false);
            }
        }

        C17531() {
        }

        public void onRefresh() {
            FireCollectionActivity.this.CheckNetworkConnection();
            FireCollectionActivity.this.connectionLost.setVisibility(4);
            FireCollectionActivity.this.imageRecyclerView.setVisibility(0);
            FireCollectionActivity.this.downloadImages();
            new Handler().postDelayed(new C13751(), 4000);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.FireCollectionActivity$2 */
    class C17542 extends OnScrollListener {
        C17542() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.FireCollectionActivity$3 */
    class C17553 implements ValueEventListener {
        C17553() {
        }

        public void onDataChange(DataSnapshot snapshot) {
            if (((Boolean) snapshot.getValue(Boolean.class)).booleanValue()) {
                FireCollectionActivity.this.imageRecyclerView.setVisibility(0);
                FireCollectionActivity.this.noConnectionGif.setVisibility(4);
                FireCollectionActivity.this.connectionLost.setVisibility(4);
                return;
            }
            FireCollectionActivity.this.imageRecyclerView.setVisibility(8);
            Glide.with(FireCollectionActivity.this.getApplicationContext()).asGif().load(Integer.valueOf(C1420R.drawable.no_connection)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(FireCollectionActivity.this.noConnectionGif);
            FireCollectionActivity.this.connectionLost.setVisibility(0);
        }

        public void onCancelled(DatabaseError error) {
            System.err.println("Listener was cancelled");
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.FireCollectionActivity$4 */
    class C17574 implements ValueEventListener {

        /* renamed from: com.walshotbeta.walshotvbeta.FireCollectionActivity$4$1 */
        class C17561 implements OnItemClickListener {
            C17561() {
            }

            public void OnItemClick(int position) {
                Intent intent = new Intent(FireCollectionActivity.this, EditorsFirePagerActivity.class);
                intent.putExtra("position", Integer.toString(position).trim());
                FireCollectionActivity.this.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(FireCollectionActivity.this, FireCollectionActivity.this.imageRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(C1420R.id.image_thumbnail), "item_transition").toBundle());
            }
        }

        C17574() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            FireCollectionActivity.wallpaperList.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                FireCollectionActivity.this.wallpapers = (FirebaseWallpapers) postSnapshot.getValue(FirebaseWallpapers.class);
                if (FireCollectionActivity.this.wallpapers.getImageCategory().equals(FireCollectionActivity.this.listCategory.trim())) {
                    FireCollectionActivity.wallpaperList.add(FireCollectionActivity.this.wallpapers);
                    Collections.reverse(FireCollectionActivity.wallpaperList);
                }
            }
            FireCollectionActivity.this.mLayoutManager = new GridLayoutManager(FireCollectionActivity.this, 2);
            FireCollectionActivity.this.imageListAdaper = new EditorsImageListAdapter(FireCollectionActivity.this, FireCollectionActivity.wallpaperList);
            FireCollectionActivity.this.imageRecyclerView.setLayoutManager(FireCollectionActivity.this.mLayoutManager);
            FireCollectionActivity.this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
            FireCollectionActivity.this.imageRecyclerView.setAdapter(FireCollectionActivity.this.imageListAdaper);
            FireCollectionActivity.this.mLastKey = dataSnapshot.getRef().push().getKey();
            FireCollectionActivity.this.imageListAdaper.setOnItemClickListener(new C17561());
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(67108864, 67108864);
        setContentView(C1420R.layout.activity_fire_collection);
        wallpaperList = new ArrayList();
        this.toolbar = (Toolbar) findViewById(C1420R.id.toolbarWallpapersViewID);
        this.toolbar.setTitle("Collections");
        setSupportActionBar(this.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.getClass();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.imageRecyclerView = (RecyclerView) findViewById(C1420R.id.images_recycler_view);
        this.connectionLost = (RelativeLayout) findViewById(C1420R.id.noConnectionLayoutID);
        this.loadingLayout = (RelativeLayout) findViewById(C1420R.id.loadingRecyclerID);
        this.noConnectionGif = (ImageView) findViewById(C1420R.id.no_connectionImageID);
        this.networkState = new ConnectionDetector(this);
        this.swipeLayout = (SwipeRefreshLayout) findViewById(C1420R.id.swipeRefreshID);
        this.imageRecyclerView.setHasFixedSize(true);
        this.extras = getIntent().getExtras();
        this.mAuth = FirebaseAuth.getInstance();
        this.recyclerDatabaseReference = FirebaseDatabase.getInstance().getReference("Wallpapers");
        if (this.extras != null) {
            this.listCategory = this.extras.getString("category").trim();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Category:");
            stringBuilder.append(this.listCategory);
            Log.d("Category", stringBuilder.toString());
        }
        CheckNetworkConnection();
        downloadImages();
        this.swipeLayout.setOnRefreshListener(new C17531());
        this.swipeLayout.setColorSchemeColors(new int[]{getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454)});
        this.slide_down = AnimationUtils.loadAnimation(this, C1420R.anim.slide_down);
        this.slide_up = AnimationUtils.loadAnimation(this, C1420R.anim.slide_up);
        this.imageRecyclerView.addOnScrollListener(new C17542());
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void CheckNetworkConnection() {
        FirebaseDatabase.getInstance().getReference(".info/connected").addValueEventListener(new C17553());
    }

    public void downloadImages() {
        wallpaperList = new ArrayList();
        this.eventListener = this.recyclerDatabaseReference.addValueEventListener(new C17574());
    }

    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public void onSaveInstanceState(Bundle outState) {
        if (this.mListState != null) {
            this.mListState = this.mLayoutManager.onSaveInstanceState();
            outState.putParcelable("list_state", this.mListState);
        }
        super.onSaveInstanceState(outState);
    }

    public void onResume() {
        if (this.mListState == null || this.imageRecyclerView == null) {
            Log.d(TAG, "onResume: ListState empty!");
        } else if (this.imageRecyclerView.getLayoutManager() != null) {
            Log.d(TAG, "onResume: Restoring list state ...");
            this.mLayoutManager.onRestoreInstanceState(this.mListState);
        }
        super.onResume();
    }
}
