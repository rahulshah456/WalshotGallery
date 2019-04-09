package Fragments;

import Adapters.ConnectionDetector;
import Adapters.LatestImageListAdapter;
import Adapters.LatestImageListAdapter.OnItemClickListener;
import Modals.OrderSelectedEvent;
import Modals.Wallpaper;
import Retrofit.WallpaperApi.Factory;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.State;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.adobe.creativesdk.foundation.internal.analytics.AdobeAnalyticsETSEvent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.LatestViewpagerActivity;
import com.walshotbeta.walshotvbeta.MainActivity;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestFragment extends Fragment {
    private static final String ADDMOB_APP_ID = "ca-app-pub-8215970961458765~4792719695";
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    public static final String ORDER_BY_LATEST = "latest";
    public static final String ORDER_BY_OLDEST = "oldest";
    public static final String ORDER_BY_POPULAR = "popular";
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = LatestFragment.class.getSimpleName();
    private static int firstVisibleInListview;
    public static String order = "latest";
    static int page = 1;
    public static List<Wallpaper> wallpaperList;
    RelativeLayout connectionLost;
    LatestImageListAdapter imageListAdapter;
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

    /* renamed from: Fragments.LatestFragment$1 */
    class C07911 implements OnRefreshListener {

        /* renamed from: Fragments.LatestFragment$1$1 */
        class C00521 implements Runnable {
            C00521() {
            }

            public void run() {
                LatestFragment.this.swipeLayout.setRefreshing(false);
            }
        }

        C07911() {
        }

        public void onRefresh() {
            LatestFragment.this.connectionLost.setVisibility(4);
            LatestFragment.this.imageListAdapter.clearList();
            LatestFragment.this.imageRecyclerView.setVisibility(0);
            SharedPreferences orderPrefs = LatestFragment.this.mContext.getSharedPreferences(LatestFragment.PREF_NAME, 0);
            if (orderPrefs.contains("Order")) {
                int newOrder = orderPrefs.getInt("Order", 0);
                if (newOrder == 0) {
                    LatestFragment.this.downloadImages("latest");
                } else if (newOrder == 1) {
                    LatestFragment.this.downloadImages("popular");
                } else if (newOrder == 2) {
                    LatestFragment.this.downloadImages("oldest");
                } else {
                    LatestFragment.this.downloadImages("latest");
                }
            } else {
                LatestFragment.this.downloadImages("latest");
            }
            LatestFragment.this.generateRecyclerView();
            new Handler().postDelayed(new C00521(), 4000);
        }
    }

    /* renamed from: Fragments.LatestFragment$2 */
    class C07922 extends OnScrollListener {
        C07922() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 20) {
                MainActivity.searchViewToolbar.setVisibility(8);
                MainActivity.searchViewToolbar.animate().alpha(0.0f).start();
                MainActivity.toolbarMenu.setVisibility(8);
                MainActivity.toolbarMenu.animate().alpha(0.0f).start();
                MainActivity.fake.setVisibility(8);
                LatestFragment.this.mAdView.animate().alpha(1.0f).start();
                LatestFragment.this.mAdView.setVisibility(0);
            } else if (dy < 0) {
                MainActivity.searchViewToolbar.setVisibility(0);
                MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
                MainActivity.toolbarMenu.setVisibility(0);
                MainActivity.toolbarMenu.animate().alpha(1.0f).start();
                MainActivity.fake.setVisibility(0);
                LatestFragment.this.mAdView.animate().alpha(0.0f).start();
                LatestFragment.this.mAdView.setVisibility(8);
            }
            if (!LatestFragment.this.isLoading && ((LinearLayoutManager) LatestFragment.this.mLayoutManager).findLastVisibleItemPosition() == LatestFragment.this.imageListAdapter.getItemCount() - 3) {
                LatestFragment.this.isLoading = true;
                Log.d(LatestFragment.TAG, "End has reached, loading more images!");
                LatestFragment.this.loadingLayout.startAnimation(LatestFragment.this.slide_up);
                LatestFragment.this.loadingLayout.setVisibility(0);
                LatestFragment.page++;
                SharedPreferences orderPrefs = LatestFragment.this.mContext.getSharedPreferences(LatestFragment.PREF_NAME, 0);
                if (orderPrefs.contains("Order")) {
                    Integer newOrder = Integer.valueOf(orderPrefs.getInt("Order", 0));
                    if (newOrder.intValue() == 0) {
                        LatestFragment.this.downloadImages("latest");
                    } else if (newOrder.intValue() == 1) {
                        LatestFragment.this.downloadImages("popular");
                    } else if (newOrder.intValue() == 2) {
                        LatestFragment.this.downloadImages("oldest");
                    } else {
                        LatestFragment.this.downloadImages("latest");
                    }
                    return;
                }
                LatestFragment.this.downloadImages("latest");
            }
        }
    }

    /* renamed from: Fragments.LatestFragment$3 */
    class C07933 implements OnItemClickListener {
        C07933() {
        }

        public void OnItemClick(int position) {
            Intent intent = new Intent(LatestFragment.this.mContext, LatestViewpagerActivity.class);
            intent.putExtra("position", position);
            LatestFragment.this.mContext.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) LatestFragment.this.mContext, LatestFragment.this.imageRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(C1420R.id.image_thumbnail), "item_transition").toBundle());
        }
    }

    /* renamed from: Fragments.LatestFragment$4 */
    class C07944 implements Callback<List<Wallpaper>> {
        C07944() {
        }

        public void onResponse(Call<List<Wallpaper>> call, Response<List<Wallpaper>> response) {
            if (response != null) {
                LatestFragment.this.imageListAdapter.addImages((List) response.body());
                LatestFragment.this.isLoading = false;
                LatestFragment.this.loadingLayout.setVisibility(4);
                LatestFragment.this.loadingLayout.startAnimation(LatestFragment.this.slide_down);
                EventBus.getDefault().post("Refresh Adapter");
            }
        }

        public void onFailure(Call<List<Wallpaper>> call, Throwable t) {
            String access$100 = LatestFragment.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed ");
            stringBuilder.append(t.getMessage());
            Log.e(access$100, stringBuilder.toString());
            LatestFragment.this.isLoading = false;
            LatestFragment.page--;
            LatestFragment.this.imageRecyclerView.setVisibility(8);
            Glide.with(LatestFragment.this.mContext).asGif().load(Integer.valueOf(C1420R.drawable.no_connection)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(LatestFragment.this.noConnectionGif);
            LatestFragment.this.connectionLost.setVisibility(0);
        }
    }

    public static LatestFragment newInstance() {
        Bundle args = new Bundle();
        LatestFragment fragment = new LatestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.latest_fragment, container, false);
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
        generateRecyclerView();
        MainActivity.searchViewToolbar.setVisibility(0);
        MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
        MainActivity.toolbarMenu.setVisibility(0);
        MainActivity.toolbarMenu.animate().alpha(1.0f).start();
        MainActivity.fake.setVisibility(0);
        this.swipeLayout.setOnRefreshListener(new C07911());
        this.swipeLayout.setColorSchemeColors(getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454));
        this.slide_down = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_down);
        this.slide_up = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_up);
        SharedPreferences orderPrefs = this.mContext.getSharedPreferences(PREF_NAME, 0);
        if (orderPrefs.contains("Order")) {
            int newOrder = orderPrefs.getInt("Order", 0);
            if (newOrder == 0) {
                downloadImages("latest");
            } else if (newOrder == 1) {
                downloadImages("popular");
            } else if (newOrder == 2) {
                downloadImages("oldest");
            } else {
                downloadImages("latest");
            }
        } else {
            downloadImages("latest");
        }
        this.imageRecyclerView.getLayoutManager().smoothScrollToPosition(this.imageRecyclerView, new State(), this.imageRecyclerView.getAdapter().getItemCount());
        this.imageRecyclerView.addOnScrollListener(new C07922());
    }

    public void generateRecyclerView() {
        SharedPreferences gridPrefs = getActivity().getSharedPreferences(PREF_NAME, 0);
        if (!gridPrefs.contains(AdobeAnalyticsETSEvent.ADOBE_ETS_VALUE_VIEW_TYPE_GRID)) {
            this.mLayoutManager = new GridLayoutManager(this.mContext, 2);
            this.imageRecyclerView.setLayoutManager(this.mLayoutManager);
        } else if (gridPrefs.getInt(AdobeAnalyticsETSEvent.ADOBE_ETS_VALUE_VIEW_TYPE_GRID, 2) == 1) {
            this.mLayoutManager = new GridLayoutManager(this.mContext, 1);
            this.imageRecyclerView.setLayoutManager(this.mLayoutManager);
        } else {
            this.mLayoutManager = new GridLayoutManager(this.mContext, 2);
            this.imageRecyclerView.setLayoutManager(this.mLayoutManager);
        }
        wallpaperList = new ArrayList();
        this.imageListAdapter = new LatestImageListAdapter(this.mContext);
        this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.imageRecyclerView.setAdapter(this.imageListAdapter);
        this.imageListAdapter.setOnItemClickListener(new C07933());
    }

    public void downloadImages(String orderBy) {
        Factory.getInstance().getWallpapers(orderBy, 20, page).enqueue(new C07944());
    }

    @Subscribe
    public void onOrderSelectedEvent(OrderSelectedEvent event) {
        page = 1;
        this.imageListAdapter.clearList();
        order = event.getOrder();
        SharedPreferences orderPrefs = this.mContext.getSharedPreferences(PREF_NAME, 0);
        if (orderPrefs.contains("Order")) {
            Integer newOrder = Integer.valueOf(orderPrefs.getInt("Order", 0));
            if (newOrder.intValue() == 0) {
                downloadImages("latest");
            } else if (newOrder.intValue() == 1) {
                downloadImages("popular");
            } else if (newOrder.intValue() == 2) {
                downloadImages("oldest");
            } else {
                downloadImages("latest");
            }
            return;
        }
        downloadImages("latest");
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            this.mListState = this.mLayoutManager.onSaveInstanceState();
            outState.putParcelable("list_state", this.mListState);
        }
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
}
