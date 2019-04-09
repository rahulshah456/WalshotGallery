package Fragments;

import Adapters.ConnectionDetector;
import Adapters.SearchWallpapersAdapter;
import Adapters.SearchWallpapersAdapter.OnItemClickListener;
import Modals.Example;
import Modals.Result;
import Retrofit.WallpaperApi.Factory;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.MainActivity;
import com.walshotbeta.walshotvbeta.MainActivity.QueryEvent;
import com.walshotbeta.walshotvbeta.SearchViewpagerActivity;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchWallpaperFragment extends Fragment {
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    private static final String TAG = SearchWallpapersAdapter.class.getSimpleName();
    private static int page = 1;
    public static List<Result> resultImages;
    private RelativeLayout connectionLost;
    private SearchWallpapersAdapter imageListAdapter;
    private RecyclerView imageRecyclerView;
    private boolean isLoading = false;
    private RelativeLayout loadingLayout;
    Context mContext;
    private LayoutManager mLayoutManager;
    private Parcelable mListState;
    private ConnectionDetector networkState;
    private ImageView noConnectionGif;
    private ImageView noResultGif;
    private RelativeLayout resultsNotFound;
    String searchQuery;
    private Animation slide_down;
    private Animation slide_up;
    private SwipeRefreshLayout swipeLayout;

    /* renamed from: Fragments.SearchWallpaperFragment$1 */
    class C08041 extends OnScrollListener {
        C08041() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 20) {
                MainActivity.searchViewToolbar.animate().alpha(0.0f).start();
                MainActivity.toolbarMenu.animate().alpha(0.0f).start();
                MainActivity.fake.setVisibility(8);
            } else if (dy < 0) {
                MainActivity.searchViewToolbar.setAlpha(1.0f);
                MainActivity.toolbarMenu.animate().alpha(1.0f).start();
                MainActivity.fake.setVisibility(0);
            }
            if (!SearchWallpaperFragment.this.isLoading && ((LinearLayoutManager) SearchWallpaperFragment.this.mLayoutManager).findLastVisibleItemPosition() == SearchWallpaperFragment.this.imageListAdapter.getItemCount() - 1) {
                SearchWallpaperFragment.this.isLoading = true;
                Log.d(SearchWallpaperFragment.TAG, "End has reached, loading more images!");
                SearchWallpaperFragment.this.loadingLayout.startAnimation(SearchWallpaperFragment.this.slide_up);
                SearchWallpaperFragment.this.loadingLayout.setVisibility(0);
                SearchWallpaperFragment.access$608();
                SearchWallpaperFragment.this.downloadImages(SearchWallpaperFragment.this.searchQuery);
            }
        }
    }

    /* renamed from: Fragments.SearchWallpaperFragment$2 */
    class C08052 implements OnRefreshListener {

        /* renamed from: Fragments.SearchWallpaperFragment$2$1 */
        class C00551 implements Runnable {
            C00551() {
            }

            public void run() {
                SearchWallpaperFragment.this.swipeLayout.setRefreshing(false);
            }
        }

        C08052() {
        }

        public void onRefresh() {
            SearchWallpaperFragment.this.connectionLost.setVisibility(8);
            SearchWallpaperFragment.this.resultsNotFound.setVisibility(8);
            SearchWallpaperFragment.this.imageListAdapter.clearList();
            SearchWallpaperFragment.this.imageRecyclerView.setVisibility(0);
            SearchWallpaperFragment.this.downloadImages(SearchWallpaperFragment.this.searchQuery);
            SearchWallpaperFragment.this.generateRecyclerView();
            new Handler().postDelayed(new C00551(), 4000);
        }
    }

    /* renamed from: Fragments.SearchWallpaperFragment$3 */
    class C08063 implements OnItemClickListener {
        C08063() {
        }

        public void OnItemClick(int position) {
            Intent intent = new Intent(SearchWallpaperFragment.this.mContext, SearchViewpagerActivity.class);
            intent.putExtra("position", Integer.toString(position).trim());
            SearchWallpaperFragment.this.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) SearchWallpaperFragment.this.mContext, SearchWallpaperFragment.this.imageRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(C1420R.id.image_thumbnail), "item_transition").toBundle());
        }
    }

    /* renamed from: Fragments.SearchWallpaperFragment$4 */
    class C08074 implements Callback<Example> {
        C08074() {
        }

        public void onResponse(Call<Example> call, Response<Example> response) {
            if (response != null) {
                SearchWallpaperFragment.this.imageListAdapter.addImages(((Example) response.body()).getResults());
                SearchWallpaperFragment.this.isLoading = false;
                SearchWallpaperFragment.this.loadingLayout.setVisibility(4);
                SearchWallpaperFragment.this.loadingLayout.startAnimation(SearchWallpaperFragment.this.slide_down);
                EventBus.getDefault().post("Refresh Adapter");
            }
            if (SearchWallpaperFragment.this.imageListAdapter.getItemCount() == 0) {
                SearchWallpaperFragment.this.imageRecyclerView.setVisibility(8);
                SearchWallpaperFragment.this.connectionLost.setVisibility(8);
                Glide.with(SearchWallpaperFragment.this.mContext).asGif().load(Integer.valueOf(C1420R.drawable.no_searchresults)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(SearchWallpaperFragment.this.noResultGif);
                SearchWallpaperFragment.this.resultsNotFound.setVisibility(0);
            }
        }

        public void onFailure(Call<Example> call, Throwable t) {
            String access$300 = SearchWallpaperFragment.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed ");
            stringBuilder.append(t.getMessage());
            Log.e(access$300, stringBuilder.toString());
            SearchWallpaperFragment.this.isLoading = false;
            SearchWallpaperFragment.access$610();
            SearchWallpaperFragment.this.imageRecyclerView.setVisibility(8);
            SearchWallpaperFragment.this.resultsNotFound.setVisibility(8);
            Glide.with(SearchWallpaperFragment.this.mContext).asGif().load(Integer.valueOf(C1420R.drawable.no_connection)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(SearchWallpaperFragment.this.noConnectionGif);
            SearchWallpaperFragment.this.connectionLost.setVisibility(0);
        }
    }

    static /* synthetic */ int access$608() {
        int i = page;
        page = i + 1;
        return i;
    }

    static /* synthetic */ int access$610() {
        int i = page;
        page = i - 1;
        return i;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.search_wall_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getActivity();
        resultImages = new ArrayList();
        this.imageRecyclerView = (RecyclerView) view.findViewById(C1420R.id.images_recycler_view);
        this.connectionLost = (RelativeLayout) view.findViewById(C1420R.id.noConnectionLayoutID);
        this.resultsNotFound = (RelativeLayout) view.findViewById(C1420R.id.noResultsLayoutID);
        this.noResultGif = (ImageView) view.findViewById(C1420R.id.no_searchResultsImageID);
        this.loadingLayout = (RelativeLayout) view.findViewById(C1420R.id.loadingRecyclerID);
        this.noConnectionGif = (ImageView) view.findViewById(C1420R.id.no_connectionImageID);
        this.networkState = new ConnectionDetector(this.mContext);
        this.swipeLayout = (SwipeRefreshLayout) view.findViewById(C1420R.id.swipeRefreshID);
        this.slide_down = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_down);
        this.slide_up = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_up);
        this.swipeLayout.setColorSchemeColors(getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454));
        MainActivity.searchViewToolbar.setVisibility(0);
        MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
        MainActivity.toolbarMenu.setVisibility(0);
        MainActivity.toolbarMenu.animate().alpha(1.0f).start();
        MainActivity.fake.setVisibility(0);
    }

    @Subscribe
    public void onEvent(QueryEvent event) {
        this.searchQuery = event.getSearchQuery();
        if (this.searchQuery != null) {
            downloadImages(this.searchQuery);
            generateRecyclerView();
            this.imageRecyclerView.addOnScrollListener(new C08041());
            this.swipeLayout.setOnRefreshListener(new C08052());
        }
    }

    public void generateRecyclerView() {
        resultImages = new ArrayList();
        this.mLayoutManager = new GridLayoutManager(this.mContext, 2);
        this.imageListAdapter = new SearchWallpapersAdapter(this.mContext);
        this.imageRecyclerView.setLayoutManager(this.mLayoutManager);
        this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.imageRecyclerView.setAdapter(this.imageListAdapter);
        this.imageListAdapter.setOnItemClickListener(new C08063());
    }

    public void downloadImages(String queryType) {
        Factory.getInstance().getSearchResult(queryType, 20, page).enqueue(new C08074());
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
