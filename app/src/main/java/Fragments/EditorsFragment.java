package Fragments;

import Adapters.ConnectionDetector;
import Adapters.EditorsImageListAdapter;
import Adapters.EditorsImageListAdapter.OnItemClickListener;
import Modals.FirebaseWallpapers;
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
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.EditorsPagerActivity;
import com.walshotbeta.walshotvbeta.MainActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditorsFragment extends Fragment {
    private static final String ADDMOB_APP_ID = "ca-app-pub-8215970961458765~4792719695";
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = EditorsFragment.class.getSimpleName();
    static int page = 0;
    public static List<FirebaseWallpapers> wallpaperList;
    RelativeLayout connectionLost;
    FirebaseDatabase database;
    ValueEventListener eventListener;
    EditorsImageListAdapter imageListAdaper;
    RecyclerView imageRecyclerView;
    boolean isLoading = false;
    int itemsPerPage = 10;
    DatabaseReference likeDatabase;
    RelativeLayout loadingLayout;
    private AdView mAdView;
    FirebaseAuth mAuth;
    Context mContext;
    String mLastKey = null;
    LayoutManager mLayoutManager;
    private Parcelable mListState;
    ConnectionDetector networkState;
    ImageView noConnectionGif;
    DatabaseReference recyclerDatabaseReference;
    Animation slide_down;
    Animation slide_up;
    SwipeRefreshLayout swipeLayout;
    FirebaseWallpapers wallpapers;

    /* renamed from: Fragments.EditorsFragment$1 */
    class C07781 implements OnRefreshListener {

        /* renamed from: Fragments.EditorsFragment$1$1 */
        class C00361 implements Runnable {
            C00361() {
            }

            public void run() {
                EditorsFragment.this.swipeLayout.setRefreshing(false);
            }
        }

        C07781() {
        }

        public void onRefresh() {
            EditorsFragment.this.CheckNetworkConnection();
            EditorsFragment.this.connectionLost.setVisibility(4);
            EditorsFragment.this.imageRecyclerView.setVisibility(0);
            EditorsFragment.this.downloadImages();
            new Handler().postDelayed(new C00361(), 4000);
        }
    }

    /* renamed from: Fragments.EditorsFragment$2 */
    class C07792 extends OnScrollListener {
        C07792() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 20) {
                MainActivity.searchViewToolbar.setVisibility(8);
                MainActivity.searchViewToolbar.animate().alpha(0.0f).start();
                MainActivity.toolbarMenu.setVisibility(8);
                MainActivity.toolbarMenu.animate().alpha(0.0f).start();
                MainActivity.fake.setVisibility(8);
                EditorsFragment.this.mAdView.animate().alpha(0.0f).start();
                EditorsFragment.this.mAdView.setVisibility(8);
            } else if (dy < 0) {
                MainActivity.searchViewToolbar.setVisibility(0);
                MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
                MainActivity.toolbarMenu.setVisibility(0);
                MainActivity.toolbarMenu.animate().alpha(1.0f).start();
                MainActivity.fake.setVisibility(0);
                EditorsFragment.this.mAdView.animate().alpha(0.0f).start();
                EditorsFragment.this.mAdView.setVisibility(8);
            }
        }
    }

    /* renamed from: Fragments.EditorsFragment$3 */
    class C07803 implements ValueEventListener {
        C07803() {
        }

        public void onDataChange(DataSnapshot snapshot) {
            if (((Boolean) snapshot.getValue(Boolean.class)).booleanValue()) {
                EditorsFragment.this.imageRecyclerView.setVisibility(0);
                EditorsFragment.this.noConnectionGif.setVisibility(4);
                EditorsFragment.this.connectionLost.setVisibility(4);
                return;
            }
            EditorsFragment.this.imageRecyclerView.setVisibility(8);
            Glide.with(EditorsFragment.this.getContext()).asGif().load(Integer.valueOf(C1420R.drawable.no_connection)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(EditorsFragment.this.noConnectionGif);
            EditorsFragment.this.connectionLost.setVisibility(0);
        }

        public void onCancelled(DatabaseError error) {
            System.err.println("Listener was cancelled");
        }
    }

    /* renamed from: Fragments.EditorsFragment$4 */
    class C07824 implements ValueEventListener {

        /* renamed from: Fragments.EditorsFragment$4$1 */
        class C07811 implements OnItemClickListener {
            C07811() {
            }

            public void OnItemClick(int position) {
                Intent intent = new Intent(EditorsFragment.this.mContext, EditorsPagerActivity.class);
                intent.putExtra("position", Integer.toString(position));
                EditorsFragment.this.mContext.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) EditorsFragment.this.mContext, EditorsFragment.this.imageRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(C1420R.id.image_thumbnail), "item_transition").toBundle());
            }
        }

        C07824() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            EditorsFragment.wallpaperList.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                EditorsFragment.this.wallpapers = (FirebaseWallpapers) postSnapshot.getValue(FirebaseWallpapers.class);
                EditorsFragment.wallpaperList.add(EditorsFragment.this.wallpapers);
                Collections.reverse(EditorsFragment.wallpaperList);
            }
            EditorsFragment.this.imageListAdaper = new EditorsImageListAdapter(EditorsFragment.this.mContext, EditorsFragment.wallpaperList);
            EditorsFragment.this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
            EditorsFragment.this.mLayoutManager = new GridLayoutManager(EditorsFragment.this.mContext, 2);
            EditorsFragment.this.imageRecyclerView.setLayoutManager(EditorsFragment.this.mLayoutManager);
            EditorsFragment.this.imageRecyclerView.setAdapter(EditorsFragment.this.imageListAdaper);
            EditorsFragment.this.mLastKey = dataSnapshot.getRef().push().getKey();
            EditorsFragment.this.imageListAdaper.setOnItemClickListener(new C07811());
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    /* renamed from: Fragments.EditorsFragment$5 */
    class C07835 implements ValueEventListener {
        C07835() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            EditorsFragment.wallpaperList.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                EditorsFragment.this.wallpapers = (FirebaseWallpapers) postSnapshot.getValue(FirebaseWallpapers.class);
                EditorsFragment.wallpaperList.add(EditorsFragment.this.wallpapers);
                Collections.reverse(EditorsFragment.wallpaperList);
            }
            EditorsFragment.this.mLayoutManager = new GridLayoutManager(EditorsFragment.this.mContext, 2);
            EditorsFragment.this.imageListAdaper = new EditorsImageListAdapter(EditorsFragment.this.mContext, EditorsFragment.wallpaperList);
            EditorsFragment.this.imageRecyclerView.setLayoutManager(EditorsFragment.this.mLayoutManager);
            EditorsFragment.this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
            EditorsFragment.this.imageRecyclerView.setAdapter(EditorsFragment.this.imageListAdaper);
            EditorsFragment.this.mLastKey = dataSnapshot.getRef().push().getKey();
            Toast.makeText(EditorsFragment.this.getContext(), EditorsFragment.this.mLastKey, 0).show();
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    public static EditorsFragment newInstance() {
        Bundle args = new Bundle();
        EditorsFragment fragment = new EditorsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.editors_fragment, container, false);
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
        this.database = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.recyclerDatabaseReference = FirebaseDatabase.getInstance().getReference("Wallpapers");
        CheckNetworkConnection();
        downloadImages();
        MainActivity.searchViewToolbar.setAlpha(1.0f);
        MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
        MainActivity.toolbarMenu.setVisibility(0);
        MainActivity.toolbarMenu.animate().alpha(1.0f).start();
        MainActivity.fake.setVisibility(0);
        this.swipeLayout.setOnRefreshListener(new C07781());
        this.swipeLayout.setColorSchemeColors(getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454));
        this.slide_down = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_down);
        this.slide_up = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_up);
        this.imageRecyclerView.addOnScrollListener(new C07792());
    }

    public void CheckNetworkConnection() {
        FirebaseDatabase.getInstance().getReference(".info/connected").addValueEventListener(new C07803());
    }

    public void downloadImages() {
        wallpaperList = new ArrayList();
        this.eventListener = this.recyclerDatabaseReference.addValueEventListener(new C07824());
    }

    public void downloadMoreImages() {
        wallpaperList = new ArrayList();
        this.recyclerDatabaseReference.orderByChild("Wallpapers").startAt((double) (page * 10)).limitToFirst(10).addListenerForSingleValueEvent(new C07835());
    }

    public void onSaveInstanceState(Bundle outState) {
        if (this.mListState != null) {
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
        if (this.mListState == null || this.imageRecyclerView == null) {
            Log.d(TAG, "onResume: ListState empty!");
        } else if (this.imageRecyclerView.getLayoutManager() != null) {
            Log.d(TAG, "onResume: Restoring list state ...");
            this.mLayoutManager.onRestoreInstanceState(this.mListState);
        }
        super.onResume();
    }
}
