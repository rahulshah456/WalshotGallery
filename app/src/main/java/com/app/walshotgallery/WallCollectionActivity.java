package com.app.walshotgallery;

import Adapters.ConnectionDetector;
import Adapters.WallCollectionAdapter;
import Adapters.WallCollectionAdapter.RecyclerViewClickListener;
import Modals.Wallpaper;
import Retrofit.WallpaperApi.Factory;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WallCollectionActivity extends AppCompatActivity implements RecyclerViewClickListener {
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    private static final String TAG = "WallCollectionActivity";
    private static int page = 1;
    public static List<Wallpaper> wallpaperList;
    private int collectionID;
    private String collectionId;
    private RelativeLayout connectionLost;
    private Bundle extras;
    private WallCollectionAdapter imageListAdapter;
    private boolean isLoading = false;
    private RelativeLayout loadingLayout;
    private LayoutManager mLayoutManager;
    private Parcelable mListState;
    private ConnectionDetector networkState;
    private ImageView noConnectionGif;
    private RecyclerView recyclerView;
    private Animation slide_down;
    private Animation slide_up;
    private SwipeRefreshLayout swipeLayout;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.WallCollectionActivity$1 */
    class C18191 implements OnRefreshListener {

        /* renamed from: com.walshotbeta.walshotvbeta.WallCollectionActivity$1$1 */
        class C14741 implements Runnable {
            C14741() {
            }

            public void run() {
                WallCollectionActivity.this.swipeLayout.setRefreshing(false);
            }
        }

        C18191() {
        }

        public void onRefresh() {
            WallCollectionActivity.this.connectionLost.setVisibility(4);
            WallCollectionActivity.this.imageListAdapter.clearList();
            WallCollectionActivity.this.recyclerView.setVisibility(0);
            WallCollectionActivity.this.downloadImages();
            WallCollectionActivity.this.generateRecyclerView();
            new Handler().postDelayed(new C14741(), 4000);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.WallCollectionActivity$2 */
    class C18202 extends OnScrollListener {
        C18202() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!WallCollectionActivity.this.isLoading && ((LinearLayoutManager) WallCollectionActivity.this.mLayoutManager).findLastVisibleItemPosition() == WallCollectionActivity.this.imageListAdapter.getItemCount() - 1) {
                WallCollectionActivity.this.isLoading = true;
                Log.d(WallCollectionActivity.TAG, "End has reached, loading more images!");
                WallCollectionActivity.this.loadingLayout.startAnimation(WallCollectionActivity.this.slide_up);
                WallCollectionActivity.this.loadingLayout.setVisibility(0);
                WallCollectionActivity.access$808();
                WallCollectionActivity.this.downloadImages();
            }
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.WallCollectionActivity$3 */
    class C18213 implements Callback<List<Wallpaper>> {
        C18213() {
        }

        public void onResponse(Call<List<Wallpaper>> call, Response<List<Wallpaper>> response) {
            if (response != null) {
                WallCollectionActivity.this.imageListAdapter.addImages((List) response.body());
                WallCollectionActivity.this.isLoading = false;
                WallCollectionActivity.this.loadingLayout.setVisibility(4);
                WallCollectionActivity.this.loadingLayout.startAnimation(WallCollectionActivity.this.slide_down);
                EventBus.getDefault().post("Refresh Adapter");
            }
        }

        public void onFailure(Call<List<Wallpaper>> call, Throwable t) {
            String str = WallCollectionActivity.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed ");
            stringBuilder.append(t.getMessage());
            Log.e(str, stringBuilder.toString());
            WallCollectionActivity.this.isLoading = false;
            WallCollectionActivity.access$810();
            WallCollectionActivity.this.recyclerView.setVisibility(8);
            Glide.with(WallCollectionActivity.this).asGif().load(Integer.valueOf(C1420R.drawable.no_connection)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(WallCollectionActivity.this.noConnectionGif);
            WallCollectionActivity.this.connectionLost.setVisibility(0);
        }
    }

    static /* synthetic */ int access$808() {
        int i = page;
        page = i + 1;
        return i;
    }

    static /* synthetic */ int access$810() {
        int i = page;
        page = i - 1;
        return i;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(67108864, 67108864);
        setContentView(C1420R.layout.activity_wall_collection);
        wallpaperList = new ArrayList();
        this.toolbar = (Toolbar) findViewById(C1420R.id.toolbarWallpapersViewID);
        this.toolbar.setTitle("Collections");
        setSupportActionBar(this.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.getClass();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.recyclerView = (RecyclerView) findViewById(C1420R.id.images_recycler_view);
        this.connectionLost = (RelativeLayout) findViewById(C1420R.id.noConnectionLayoutID);
        this.loadingLayout = (RelativeLayout) findViewById(C1420R.id.loadingRecyclerID);
        this.noConnectionGif = (ImageView) findViewById(C1420R.id.no_connectionImageID);
        this.networkState = new ConnectionDetector(this);
        this.swipeLayout = (SwipeRefreshLayout) findViewById(C1420R.id.swipeRefreshID);
        this.recyclerView.setHasFixedSize(true);
        this.extras = getIntent().getExtras();
        if (this.extras != null) {
            this.collectionId = this.extras.getString("collectionId").trim();
            this.collectionID = Integer.valueOf(this.collectionId).intValue();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CollectionId: ");
            stringBuilder.append(this.collectionId);
            Log.d("Collection", stringBuilder.toString());
        }
        generateRecyclerView();
        this.swipeLayout.setOnRefreshListener(new C18191());
        this.swipeLayout.setColorSchemeColors(new int[]{getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454)});
        this.slide_down = AnimationUtils.loadAnimation(this, C1420R.anim.slide_down);
        this.slide_up = AnimationUtils.loadAnimation(this, C1420R.anim.slide_up);
        downloadImages();
        this.recyclerView.addOnScrollListener(new C18202());
    }

    public void generateRecyclerView() {
        wallpaperList = new ArrayList();
        this.mLayoutManager = new GridLayoutManager(this, 2);
        this.imageListAdapter = new WallCollectionAdapter(this, this);
        this.recyclerView.setLayoutManager(this.mLayoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.imageListAdapter);
    }

    public void downloadImages() {
        Factory.getInstance().getSingleCollection(this.collectionID, page, 20).enqueue(new C18213());
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

    public void recyclerViewListClicked(View v, int position) {
        Intent intent = new Intent(this, CollectionPagerActivity.class);
        intent.putExtra("position", Integer.toString(position).trim());
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this, this.recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(C1420R.id.image_thumbnail), "item_transition").toBundle());
    }
}
