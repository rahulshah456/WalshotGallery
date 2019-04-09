package Fragments;

import Adapters.ConnectionDetector;
import Adapters.SearchCollectionAdapter;
import Adapters.SearchCollectionAdapter.RecyclerViewClickListener;
import Modals.ResultsItem;
import Modals.SearchCollections;
import Retrofit.WallpaperApi.Factory;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.MainActivity;
import com.walshotbeta.walshotvbeta.MainActivity.QueryEvent;
import com.walshotbeta.walshotvbeta.SearchWallCollectionActivity;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCollectionFragment extends Fragment implements RecyclerViewClickListener {
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    private static final String TAG = SearchCollectionFragment.class.getSimpleName();
    private static int page = 1;
    public static List<ResultsItem> resultImages;
    private RelativeLayout connectionLost;
    private SearchCollectionAdapter imageListAdapter;
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
    TextView textView;

    /* renamed from: Fragments.SearchCollectionFragment$1 */
    class C08021 extends OnScrollListener {
        C08021() {
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
            if (!SearchCollectionFragment.this.isLoading && ((LinearLayoutManager) SearchCollectionFragment.this.mLayoutManager).findLastVisibleItemPosition() == SearchCollectionFragment.this.imageListAdapter.getItemCount() - 1) {
                SearchCollectionFragment.this.isLoading = true;
                Log.d(SearchCollectionFragment.TAG, "End has reached, loading more images!");
                SearchCollectionFragment.this.loadingLayout.startAnimation(SearchCollectionFragment.this.slide_up);
                SearchCollectionFragment.this.loadingLayout.setVisibility(0);
                SearchCollectionFragment.access$608();
                SearchCollectionFragment.this.downloadImages(SearchCollectionFragment.this.searchQuery);
            }
        }
    }

    /* renamed from: Fragments.SearchCollectionFragment$2 */
    class C08032 implements Callback<SearchCollections> {
        C08032() {
        }

        public void onResponse(Call<SearchCollections> call, Response<SearchCollections> response) {
            if (response != null) {
                SearchCollectionFragment.this.imageListAdapter.addCollections(((SearchCollections) response.body()).getResults());
                SearchCollectionFragment.this.isLoading = false;
                SearchCollectionFragment.this.loadingLayout.setVisibility(4);
                SearchCollectionFragment.this.loadingLayout.startAnimation(SearchCollectionFragment.this.slide_down);
                EventBus.getDefault().post("Refresh Adapter");
            }
            if (SearchCollectionFragment.this.imageListAdapter.getItemCount() == 0) {
                SearchCollectionFragment.this.imageRecyclerView.setVisibility(8);
                SearchCollectionFragment.this.connectionLost.setVisibility(8);
                Glide.with(SearchCollectionFragment.this.mContext).asGif().load(Integer.valueOf(C1420R.drawable.no_searchresults)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(SearchCollectionFragment.this.noResultGif);
                SearchCollectionFragment.this.resultsNotFound.setVisibility(0);
            }
        }

        public void onFailure(Call<SearchCollections> call, Throwable t) {
            String access$300 = SearchCollectionFragment.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed ");
            stringBuilder.append(t.getMessage());
            Log.e(access$300, stringBuilder.toString());
            SearchCollectionFragment.this.isLoading = false;
            SearchCollectionFragment.access$610();
            SearchCollectionFragment.this.imageRecyclerView.setVisibility(8);
            SearchCollectionFragment.this.resultsNotFound.setVisibility(8);
            Glide.with(SearchCollectionFragment.this.mContext).asGif().load(Integer.valueOf(C1420R.drawable.no_connection)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(SearchCollectionFragment.this.noConnectionGif);
            SearchCollectionFragment.this.connectionLost.setVisibility(0);
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

    public static SearchCollectionFragment newInstance() {
        Bundle args = new Bundle();
        SearchCollectionFragment fragment = new SearchCollectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.search_collection_fragment, container, false);
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
            this.imageRecyclerView.addOnScrollListener(new C08021());
        }
    }

    public void generateRecyclerView() {
        resultImages = new ArrayList();
        this.mLayoutManager = new GridLayoutManager(this.mContext, 1);
        this.imageListAdapter = new SearchCollectionAdapter(this.mContext, this);
        this.imageRecyclerView.setLayoutManager(this.mLayoutManager);
        this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.imageRecyclerView.setAdapter(this.imageListAdapter);
    }

    public void downloadImages(String queryType) {
        Factory.getInstance().getSearchCollectionResult(queryType, 20, page).enqueue(new C08032());
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void recyclerViewListClicked(View v, int id) {
        Intent intent = new Intent(getActivity(), SearchWallCollectionActivity.class);
        intent.putExtra("collectionId", String.valueOf(id));
        startActivity(intent);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CollectionId: ");
        stringBuilder.append(String.valueOf(id));
        Log.d("Collection", stringBuilder.toString());
    }
}
