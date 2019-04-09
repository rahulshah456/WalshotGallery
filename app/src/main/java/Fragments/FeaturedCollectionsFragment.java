package Fragments;

import Adapters.CollectionsImageListAdapter;
import Adapters.CollectionsImageListAdapter.RecyclerViewClickListener;
import Adapters.ConnectionDetector;
import Modals.Collections;
import Retrofit.WallpaperApi.Factory;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.MainActivity;
import com.walshotbeta.walshotvbeta.WallCollectionActivity;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeaturedCollectionsFragment extends Fragment implements RecyclerViewClickListener {
    private static final String ADDMOB_APP_ID = "ca-app-pub-8215970961458765~4792719695";
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    private static final String TAG = EditorsCollectionsFragment.class.getSimpleName();
    public static List<Collections> collectionsList;
    static int page = 1;
    RelativeLayout connectionLost;
    CollectionsImageListAdapter imageListAdapter;
    RecyclerView imageRecyclerView;
    boolean isLoading = false;
    RelativeLayout loadingLayout;
    private AdView mAdView;
    Context mContext;
    LayoutManager mLayoutManager;
    private Parcelable mListState;
    ConnectionDetector networkState;
    ImageView noConnectionGif;
    Animation slide_down;
    Animation slide_up;
    SwipeRefreshLayout swipeLayout;

    /* renamed from: Fragments.FeaturedCollectionsFragment$1 */
    class C07841 implements OnRefreshListener {

        /* renamed from: Fragments.FeaturedCollectionsFragment$1$1 */
        class C00371 implements Runnable {
            C00371() {
            }

            public void run() {
                FeaturedCollectionsFragment.this.swipeLayout.setRefreshing(false);
            }
        }

        C07841() {
        }

        public void onRefresh() {
            FeaturedCollectionsFragment.this.connectionLost.setVisibility(4);
            FeaturedCollectionsFragment.this.imageListAdapter.clearList();
            FeaturedCollectionsFragment.this.imageRecyclerView.setVisibility(0);
            FeaturedCollectionsFragment.this.downloadImages();
            FeaturedCollectionsFragment.this.generateRecyclerView();
            new Handler().postDelayed(new C00371(), 4000);
        }
    }

    /* renamed from: Fragments.FeaturedCollectionsFragment$2 */
    class C07852 extends OnScrollListener {
        C07852() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 20) {
                MainActivity.searchViewToolbar.setVisibility(8);
                MainActivity.searchViewToolbar.animate().alpha(0.0f).start();
                MainActivity.toolbarMenu.setVisibility(8);
                MainActivity.toolbarMenu.animate().alpha(0.0f).start();
                MainActivity.fake.setVisibility(8);
                FeaturedCollectionsFragment.this.mAdView.animate().alpha(1.0f).start();
                FeaturedCollectionsFragment.this.mAdView.setVisibility(0);
            } else if (dy < 0) {
                MainActivity.searchViewToolbar.setVisibility(0);
                MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
                MainActivity.toolbarMenu.setVisibility(0);
                MainActivity.toolbarMenu.animate().alpha(1.0f).start();
                MainActivity.fake.setVisibility(0);
                MainActivity.fake.setVisibility(0);
                FeaturedCollectionsFragment.this.mAdView.animate().alpha(0.0f).start();
                FeaturedCollectionsFragment.this.mAdView.setVisibility(8);
            }
            if (!FeaturedCollectionsFragment.this.isLoading && ((LinearLayoutManager) FeaturedCollectionsFragment.this.mLayoutManager).findLastVisibleItemPosition() == FeaturedCollectionsFragment.this.imageListAdapter.getItemCount() - 1) {
                FeaturedCollectionsFragment.this.isLoading = true;
                Log.d(FeaturedCollectionsFragment.TAG, "End has reached, loading more images!");
                FeaturedCollectionsFragment.this.loadingLayout.startAnimation(FeaturedCollectionsFragment.this.slide_up);
                FeaturedCollectionsFragment.this.loadingLayout.setVisibility(0);
                FeaturedCollectionsFragment.page++;
                FeaturedCollectionsFragment.this.downloadImages();
            }
        }
    }

    /* renamed from: Fragments.FeaturedCollectionsFragment$3 */
    class C07863 implements Callback<List<Collections>> {
        C07863() {
        }

        public void onResponse(Call<List<Collections>> call, Response<List<Collections>> response) {
            if (response != null) {
                FeaturedCollectionsFragment.this.imageListAdapter.addCollections((List) response.body());
                FeaturedCollectionsFragment.this.isLoading = false;
                FeaturedCollectionsFragment.this.loadingLayout.setVisibility(4);
                FeaturedCollectionsFragment.this.loadingLayout.startAnimation(FeaturedCollectionsFragment.this.slide_down);
                EventBus.getDefault().post("Refresh Adapter");
            }
        }

        public void onFailure(Call<List<Collections>> call, Throwable t) {
            String access$100 = FeaturedCollectionsFragment.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed ");
            stringBuilder.append(t.getMessage());
            Log.e(access$100, stringBuilder.toString());
            FeaturedCollectionsFragment.this.isLoading = false;
            FeaturedCollectionsFragment.page--;
            FeaturedCollectionsFragment.this.imageRecyclerView.setVisibility(8);
            Glide.with(FeaturedCollectionsFragment.this.mContext).asGif().load(Integer.valueOf(C1420R.drawable.no_connection)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(FeaturedCollectionsFragment.this.noConnectionGif);
            FeaturedCollectionsFragment.this.connectionLost.setVisibility(0);
        }
    }

    public static FeaturedCollectionsFragment newInstance() {
        Bundle args = new Bundle();
        FeaturedCollectionsFragment fragment = new FeaturedCollectionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.featured_collections_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MobileAds.initialize(this.mContext, ADDMOB_APP_ID);
        this.mAdView = (AdView) view.findViewById(C1420R.id.adViewID);
        this.mAdView.loadAd(new Builder().build());
        this.mContext = getActivity();
        this.imageRecyclerView = (RecyclerView) view.findViewById(C1420R.id.images_recycler_view);
        this.connectionLost = (RelativeLayout) view.findViewById(C1420R.id.noConnectionLayoutID);
        this.loadingLayout = (RelativeLayout) view.findViewById(C1420R.id.loadingRecyclerID);
        this.noConnectionGif = (ImageView) view.findViewById(C1420R.id.no_connectionImageID);
        this.networkState = new ConnectionDetector(this.mContext);
        this.swipeLayout = (SwipeRefreshLayout) view.findViewById(C1420R.id.swipeRefreshID);
        MainActivity.searchViewToolbar.setVisibility(0);
        MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
        MainActivity.toolbarMenu.setVisibility(0);
        MainActivity.toolbarMenu.animate().alpha(1.0f).start();
        MainActivity.fake.setVisibility(0);
        generateRecyclerView();
        this.swipeLayout.setOnRefreshListener(new C07841());
        this.swipeLayout.setColorSchemeColors(getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454));
        this.slide_down = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_down);
        this.slide_up = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_up);
        downloadImages();
        this.imageRecyclerView.addOnScrollListener(new C07852());
    }

    public void generateRecyclerView() {
        collectionsList = new ArrayList();
        this.mLayoutManager = new GridLayoutManager(this.mContext, 1);
        this.imageListAdapter = new CollectionsImageListAdapter(this.mContext, this);
        this.imageRecyclerView.setLayoutManager(this.mLayoutManager);
        this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.imageRecyclerView.setAdapter(this.imageListAdapter);
    }

    public void downloadImages() {
        Factory.getInstance().getFeaturedCollections(page, 20).enqueue(new C07863());
    }

    public void onSaveInstanceState(Bundle outState) {
        this.mListState = this.mLayoutManager.onSaveInstanceState();
        outState.putParcelable("list_state", this.mListState);
        super.onSaveInstanceState(outState);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.mListState = savedInstanceState.getParcelable("list_state");
        }
        super.onActivityCreated(savedInstanceState);
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

    public void recyclerViewListClicked(View v, int id) {
        Intent intent = new Intent(getActivity(), WallCollectionActivity.class);
        intent.putExtra("collectionId", String.valueOf(id));
        startActivity(intent);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CollectionId: ");
        stringBuilder.append(String.valueOf(id));
        Log.d("Collection", stringBuilder.toString());
    }
}
