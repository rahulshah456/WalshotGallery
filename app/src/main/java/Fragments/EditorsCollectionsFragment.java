package Fragments;

import Adapters.ConnectionDetector;
import Adapters.EditorsCollectionsListAdapter;
import Adapters.EditorsCollectionsListAdapter.OnItemClickListener;
import Modals.FirebaseCollections;
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
import com.google.firebase.storage.FirebaseStorage;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.FireCollectionActivity;
import com.walshotbeta.walshotvbeta.MainActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditorsCollectionsFragment extends Fragment {
    private static final String ADDMOB_APP_ID = "ca-app-pub-8215970961458765~4792719695";
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    private static final String TAG = EditorsCollectionsFragment.class.getSimpleName();
    public static List<FirebaseCollections> collectionsList;
    static int page = 0;
    FirebaseCollections collections;
    EditorsCollectionsListAdapter collectionsListAdaper;
    RelativeLayout connectionLost;
    FirebaseDatabase database;
    ValueEventListener eventListener;
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
    FirebaseStorage storage;
    SwipeRefreshLayout swipeLayout;

    /* renamed from: Fragments.EditorsCollectionsFragment$1 */
    class C07721 implements OnRefreshListener {

        /* renamed from: Fragments.EditorsCollectionsFragment$1$1 */
        class C00351 implements Runnable {
            C00351() {
            }

            public void run() {
                EditorsCollectionsFragment.this.swipeLayout.setRefreshing(false);
            }
        }

        C07721() {
        }

        public void onRefresh() {
            EditorsCollectionsFragment.this.CheckNetworkConnection();
            EditorsCollectionsFragment.this.connectionLost.setVisibility(4);
            EditorsCollectionsFragment.this.imageRecyclerView.setVisibility(0);
            EditorsCollectionsFragment.this.downloadImages();
            new Handler().postDelayed(new C00351(), 4000);
        }
    }

    /* renamed from: Fragments.EditorsCollectionsFragment$2 */
    class C07732 extends OnScrollListener {
        C07732() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 20) {
                MainActivity.searchViewToolbar.setVisibility(8);
                MainActivity.searchViewToolbar.animate().alpha(0.0f).start();
                MainActivity.toolbarMenu.setVisibility(8);
                MainActivity.toolbarMenu.animate().alpha(0.0f).start();
                MainActivity.fake.setVisibility(8);
                EditorsCollectionsFragment.this.mAdView.animate().alpha(1.0f).start();
                EditorsCollectionsFragment.this.mAdView.setVisibility(0);
            } else if (dy < 0) {
                MainActivity.searchViewToolbar.setVisibility(0);
                MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
                MainActivity.toolbarMenu.setVisibility(0);
                MainActivity.toolbarMenu.animate().alpha(1.0f).start();
                MainActivity.fake.setVisibility(0);
                MainActivity.fake.setVisibility(0);
                EditorsCollectionsFragment.this.mAdView.animate().alpha(0.0f).start();
                EditorsCollectionsFragment.this.mAdView.setVisibility(8);
            }
        }
    }

    /* renamed from: Fragments.EditorsCollectionsFragment$3 */
    class C07743 implements ValueEventListener {
        C07743() {
        }

        public void onDataChange(DataSnapshot snapshot) {
            if (((Boolean) snapshot.getValue(Boolean.class)).booleanValue()) {
                EditorsCollectionsFragment.this.imageRecyclerView.setVisibility(0);
                EditorsCollectionsFragment.this.noConnectionGif.setVisibility(4);
                EditorsCollectionsFragment.this.connectionLost.setVisibility(4);
                return;
            }
            EditorsCollectionsFragment.this.imageRecyclerView.setVisibility(8);
            Glide.with(EditorsCollectionsFragment.this.mContext).asGif().load(Integer.valueOf(C1420R.drawable.no_connection)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(EditorsCollectionsFragment.this.noConnectionGif);
            EditorsCollectionsFragment.this.connectionLost.setVisibility(0);
        }

        public void onCancelled(DatabaseError error) {
            System.err.println("Listener was cancelled");
        }
    }

    /* renamed from: Fragments.EditorsCollectionsFragment$4 */
    class C07764 implements ValueEventListener {

        /* renamed from: Fragments.EditorsCollectionsFragment$4$1 */
        class C07751 implements OnItemClickListener {
            C07751() {
            }

            public void OnItemClick(String category) {
                Intent intent = new Intent(EditorsCollectionsFragment.this.getActivity(), FireCollectionActivity.class);
                intent.putExtra("category", category);
                EditorsCollectionsFragment.this.startActivity(intent);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Category:");
                stringBuilder.append(category);
                Log.d("Category", stringBuilder.toString());
            }
        }

        C07764() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            EditorsCollectionsFragment.collectionsList.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                EditorsCollectionsFragment.this.collections = (FirebaseCollections) postSnapshot.getValue(FirebaseCollections.class);
                EditorsCollectionsFragment.this.collections.setmKey(postSnapshot.getKey());
                EditorsCollectionsFragment.collectionsList.add(EditorsCollectionsFragment.this.collections);
                Collections.reverse(EditorsCollectionsFragment.collectionsList);
            }
            EditorsCollectionsFragment.this.mLayoutManager = new GridLayoutManager(EditorsCollectionsFragment.this.mContext, 1);
            EditorsCollectionsFragment.this.collectionsListAdaper = new EditorsCollectionsListAdapter(EditorsCollectionsFragment.this.mContext, EditorsCollectionsFragment.collectionsList);
            EditorsCollectionsFragment.this.imageRecyclerView.setLayoutManager(EditorsCollectionsFragment.this.mLayoutManager);
            EditorsCollectionsFragment.this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
            EditorsCollectionsFragment.this.imageRecyclerView.setAdapter(EditorsCollectionsFragment.this.collectionsListAdaper);
            EditorsCollectionsFragment.this.collectionsListAdaper.setOnItemClickListener(new C07751());
            EditorsCollectionsFragment.this.mLastKey = dataSnapshot.getRef().push().getKey();
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    /* renamed from: Fragments.EditorsCollectionsFragment$5 */
    class C07775 implements ValueEventListener {
        C07775() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            EditorsCollectionsFragment.collectionsList.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                EditorsCollectionsFragment.this.collections = (FirebaseCollections) postSnapshot.getValue(FirebaseCollections.class);
                EditorsCollectionsFragment.collectionsList.add(EditorsCollectionsFragment.this.collections);
                Collections.reverse(EditorsCollectionsFragment.collectionsList);
            }
            EditorsCollectionsFragment.this.mLayoutManager = new GridLayoutManager(EditorsCollectionsFragment.this.mContext, 2);
            EditorsCollectionsFragment.this.collectionsListAdaper = new EditorsCollectionsListAdapter(EditorsCollectionsFragment.this.mContext, EditorsCollectionsFragment.collectionsList);
            EditorsCollectionsFragment.this.imageRecyclerView.setLayoutManager(EditorsCollectionsFragment.this.mLayoutManager);
            EditorsCollectionsFragment.this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
            EditorsCollectionsFragment.this.imageRecyclerView.setAdapter(EditorsCollectionsFragment.this.collectionsListAdaper);
            EditorsCollectionsFragment.this.mLastKey = dataSnapshot.getRef().push().getKey();
            Toast.makeText(EditorsCollectionsFragment.this.getContext(), EditorsCollectionsFragment.this.mLastKey, 0).show();
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    public static EditorsCollectionsFragment newInstance() {
        Bundle args = new Bundle();
        EditorsCollectionsFragment fragment = new EditorsCollectionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.editors_collections_fragment, container, false);
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
        this.storage = FirebaseStorage.getInstance();
        this.recyclerDatabaseReference = FirebaseDatabase.getInstance().getReference("Collections");
        CheckNetworkConnection();
        downloadImages();
        MainActivity.searchViewToolbar.setAlpha(1.0f);
        MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
        MainActivity.toolbarMenu.setVisibility(0);
        MainActivity.toolbarMenu.animate().alpha(1.0f).start();
        MainActivity.fake.setVisibility(0);
        this.swipeLayout.setOnRefreshListener(new C07721());
        this.swipeLayout.setColorSchemeColors(getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454));
        this.slide_down = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_down);
        this.slide_up = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_up);
        this.imageRecyclerView.addOnScrollListener(new C07732());
    }

    public void CheckNetworkConnection() {
        FirebaseDatabase.getInstance().getReference(".info/connected").addValueEventListener(new C07743());
    }

    public void downloadImages() {
        collectionsList = new ArrayList();
        this.eventListener = this.recyclerDatabaseReference.addValueEventListener(new C07764());
    }

    public void downloadMoreImages() {
        collectionsList = new ArrayList();
        this.recyclerDatabaseReference.orderByChild("Wallpapers").startAt((double) (page * 10)).limitToFirst(10).addListenerForSingleValueEvent(new C07775());
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
