package Fragments;

import Adapters.ConnectionDetector;
import Adapters.DailyShotsAdapter;
import Adapters.DailyShotsAdapter.OnItemClickListener;
import Modals.FirebaseDailyShots;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.CameraUpload;
import com.walshotbeta.walshotvbeta.DailyPreviewActivity;
import com.walshotbeta.walshotvbeta.MainActivity;
import com.walshotbeta.walshotvbeta.SettingsActivity;
import com.walshotbeta.walshotvbeta.StorageUpload;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.FileDownloadService;

public class DailyShotsFragment extends Fragment {
    private static final String ADDMOB_APP_ID = "ca-app-pub-8215970961458765~4792719695";
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    private static final int REQUEST_CAMERA = 7;
    private static final int SELECT_FILES = 5;
    private static final String TAG = DailyShotsFragment.class.getSimpleName();
    static int page = 0;
    public static List<FirebaseDailyShots> wallpaperList;
    final String CONTENT = "contentType";
    RelativeLayout connectionLost;
    FirebaseDatabase database;
    ValueEventListener eventListener;
    FloatingActionMenu fab;
    ImageButton filterButton;
    ImageButton hamButton;
    DailyShotsAdapter imageListAdaper;
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
    ImageButton optionsButton;
    DatabaseReference recyclerDatabaseReference;
    Animation slide_down;
    Animation slide_up;
    SwipeRefreshLayout swipeLayout;
    FloatingActionButton uploadFromCamera;
    FloatingActionButton uploadFromStorage;
    FirebaseDailyShots wallpapers;

    /* renamed from: Fragments.DailyShotsFragment$1 */
    class C00261 implements OnClickListener {
        C00261() {
        }

        public void onClick(View v) {
            MainActivity.mDrawerLayout.openDrawer(MainActivity.mNavigationView);
        }
    }

    /* renamed from: Fragments.DailyShotsFragment$2 */
    class C00282 implements OnClickListener {

        /* renamed from: Fragments.DailyShotsFragment$2$1 */
        class C00271 implements OnMenuItemClickListener {
            C00271() {
            }

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() != C1420R.id.newest_menuID) {
                    if (item.getItemId() != C1420R.id.popularity_menuID) {
                        item.getItemId();
                    }
                }
                return true;
            }
        }

        C00282() {
        }

        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(DailyShotsFragment.this.mContext, DailyShotsFragment.this.filterButton);
            popupMenu.getMenuInflater().inflate(C1420R.menu.dailyshot_filter, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new C00271());
            popupMenu.show();
        }
    }

    /* renamed from: Fragments.DailyShotsFragment$3 */
    class C00303 implements OnClickListener {

        /* renamed from: Fragments.DailyShotsFragment$3$1 */
        class C00291 implements OnMenuItemClickListener {

            /* renamed from: Fragments.DailyShotsFragment$3$1$1 */
            class C07661 implements ResultCallback<Status> {
                C07661() {
                }

                public void onResult(@NonNull Status status) {
                    Log.d(DailyShotsFragment.TAG, "User Succesfully Signed Out");
                }
            }

            C00291() {
            }

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == C1420R.id.signout_menuID) {
                    DailyShotsFragment.this.mAuth.signOut();
                    Auth.GoogleSignInApi.signOut(MainActivity.googleApiClient).setResultCallback(new C07661());
                    Glide.with(DailyShotsFragment.this.mContext).load(Integer.valueOf(C1420R.drawable.walshot_logo)).into(MainActivity.profilePic);
                    MainActivity.profileName.setText("Walshot Gallery");
                    MainActivity.profileEmail.setText("droid2developers.com");
                } else if (item.getItemId() == C1420R.id.signin_menuID) {
                    MainActivity.mainLayout.setVisibility(8);
                    MainActivity.frameLayout.setVisibility(0);
                    DailyShotsFragment.this.getFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new LoginFragment()).commit();
                    MainActivity.mNavigationView.setCheckedItem((int) C1420R.id.nav_accountID);
                } else if (item.getItemId() == C1420R.id.exit_menuID) {
                    FragmentActivity activity = DailyShotsFragment.this.getActivity();
                    activity.getClass();
                    activity.finish();
                } else if (item.getItemId() == C1420R.id.settings_menuID) {
                    DailyShotsFragment.this.startActivity(new Intent(DailyShotsFragment.this.mContext, SettingsActivity.class));
                }
                return true;
            }
        }

        C00303() {
        }

        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(DailyShotsFragment.this.mContext, DailyShotsFragment.this.optionsButton);
            if (DailyShotsFragment.this.mAuth.getCurrentUser() != null) {
                popupMenu.getMenuInflater().inflate(C1420R.menu.menu_main2, popupMenu.getMenu());
            } else {
                popupMenu.getMenuInflater().inflate(C1420R.menu.menu_main, popupMenu.getMenu());
            }
            popupMenu.setOnMenuItemClickListener(new C00291());
            popupMenu.show();
        }
    }

    /* renamed from: Fragments.DailyShotsFragment$6 */
    class C00326 implements OnClickListener {
        C00326() {
        }

        public void onClick(View v) {
            if (DailyShotsFragment.this.mAuth.getCurrentUser() != null) {
                DailyShotsFragment.this.mContext.startActivity(new Intent(DailyShotsFragment.this.getActivity(), CameraUpload.class));
                DailyShotsFragment.this.fab.close(true);
                return;
            }
            Toast.makeText(DailyShotsFragment.this.mContext, "Login first", 0).show();
            DailyShotsFragment.this.fab.close(true);
        }
    }

    /* renamed from: Fragments.DailyShotsFragment$7 */
    class C00337 implements OnClickListener {
        C00337() {
        }

        public void onClick(View v) {
            if (DailyShotsFragment.this.mAuth.getCurrentUser() != null) {
                Intent intent = new Intent(DailyShotsFragment.this.getActivity(), StorageUpload.class);
                intent.putExtra(Param.CONTENT, "gallery");
                DailyShotsFragment.this.mContext.startActivity(intent);
                DailyShotsFragment.this.fab.close(true);
                return;
            }
            Toast.makeText(DailyShotsFragment.this.mContext, "Login first", 0).show();
            DailyShotsFragment.this.fab.close(true);
        }
    }

    /* renamed from: Fragments.DailyShotsFragment$4 */
    class C07674 implements OnRefreshListener {

        /* renamed from: Fragments.DailyShotsFragment$4$1 */
        class C00311 implements Runnable {
            C00311() {
            }

            public void run() {
                DailyShotsFragment.this.swipeLayout.setRefreshing(false);
            }
        }

        C07674() {
        }

        public void onRefresh() {
            DailyShotsFragment.this.CheckNetworkConnection();
            DailyShotsFragment.this.connectionLost.setVisibility(4);
            DailyShotsFragment.this.imageRecyclerView.setVisibility(0);
            DailyShotsFragment.this.downloadImages();
            new Handler().postDelayed(new C00311(), 4000);
        }
    }

    /* renamed from: Fragments.DailyShotsFragment$5 */
    class C07685 extends OnScrollListener {
        C07685() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                DailyShotsFragment.this.fab.setAlpha(0.0f);
            } else {
                DailyShotsFragment.this.fab.setAlpha(1.0f);
            }
        }
    }

    /* renamed from: Fragments.DailyShotsFragment$8 */
    class C07698 implements ValueEventListener {
        C07698() {
        }

        public void onDataChange(DataSnapshot snapshot) {
            if (((Boolean) snapshot.getValue(Boolean.class)).booleanValue()) {
                DailyShotsFragment.this.imageRecyclerView.setVisibility(0);
                DailyShotsFragment.this.noConnectionGif.setVisibility(4);
                DailyShotsFragment.this.connectionLost.setVisibility(4);
                return;
            }
            DailyShotsFragment.this.imageRecyclerView.setVisibility(8);
            Glide.with(DailyShotsFragment.this.mContext).asGif().load(Integer.valueOf(C1420R.drawable.no_connection)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(DailyShotsFragment.this.noConnectionGif);
            DailyShotsFragment.this.connectionLost.setVisibility(0);
        }

        public void onCancelled(DatabaseError error) {
            System.err.println("Listener was cancelled");
        }
    }

    /* renamed from: Fragments.DailyShotsFragment$9 */
    class C07719 implements ValueEventListener {

        /* renamed from: Fragments.DailyShotsFragment$9$1 */
        class C07701 implements OnItemClickListener {
            C07701() {
            }

            public void OnItemClick(final int position) {
                PopupMenu popupMenu = new PopupMenu(DailyShotsFragment.this.mContext, DailyShotsFragment.this.imageRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(C1420R.id.optionsID));
                popupMenu.getMenuInflater().inflate(C1420R.menu.dailyshots_publicmenu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == C1420R.id.report_menuID) {
                            Intent emailSelectorIntent = new Intent("android.intent.action.SENDTO");
                            emailSelectorIntent.setData(Uri.parse("mailto:"));
                            Intent emailIntent = new Intent("android.intent.action.SEND");
                            emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{"rahulkumarshah5000@gmail.com"});
                            emailIntent.putExtra("android.intent.extra.SUBJECT", "Report User");
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Report ");
                            stringBuilder.append(((FirebaseDailyShots) DailyShotsFragment.wallpaperList.get(position)).getUserName());
                            stringBuilder.append(" for uploading inappropriate content to the Walshot Community.");
                            emailIntent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
                            emailIntent.setSelector(emailSelectorIntent);
                            FragmentActivity activity = DailyShotsFragment.this.getActivity();
                            activity.getClass();
                            if (emailIntent.resolveActivity(activity.getPackageManager()) != null) {
                                DailyShotsFragment.this.startActivity(emailIntent);
                            }
                        } else if (item.getItemId() == C1420R.id.save_menuID) {
                            String saveShot = ((FirebaseDailyShots) DailyShotsFragment.wallpaperList.get(position)).getCompressedURL();
                            String fileName = new StringBuilder();
                            fileName.append("DailyShot");
                            fileName.append(System.currentTimeMillis());
                            fileName.append(".JPEG");
                            DailyShotsFragment.this.downloadImageToLocal(fileName.toString(), saveShot);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }

            public void OnItemLongClick(int position) {
            }
        }

        C07719() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            DailyShotsFragment.wallpaperList.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                DailyShotsFragment.this.wallpapers = (FirebaseDailyShots) postSnapshot.getValue(FirebaseDailyShots.class);
                DailyShotsFragment.wallpaperList.add(DailyShotsFragment.this.wallpapers);
                Collections.reverse(DailyShotsFragment.wallpaperList);
            }
            DailyShotsFragment.this.mLayoutManager = new GridLayoutManager(DailyShotsFragment.this.mContext, 1);
            DailyShotsFragment.this.imageListAdaper = new DailyShotsAdapter(DailyShotsFragment.this.mContext, DailyShotsFragment.wallpaperList);
            DailyShotsFragment.this.imageRecyclerView.setLayoutManager(DailyShotsFragment.this.mLayoutManager);
            DailyShotsFragment.this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
            DailyShotsFragment.this.imageRecyclerView.setAdapter(DailyShotsFragment.this.imageListAdaper);
            DailyShotsFragment.this.mLastKey = dataSnapshot.getRef().push().getKey();
            DailyShotsFragment.this.imageListAdaper.setOnItemClickListener(new C07701());
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    public static DailyShotsFragment newInstance() {
        Bundle args = new Bundle();
        DailyShotsFragment fragment = new DailyShotsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.dailyshots_fragment, container, false);
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
        this.fab = (FloatingActionMenu) view.findViewById(C1420R.id.fabProfileID);
        this.uploadFromCamera = (FloatingActionButton) view.findViewById(C1420R.id.addCameraImagesFabID);
        this.uploadFromStorage = (FloatingActionButton) view.findViewById(C1420R.id.addStorageImagesFabID);
        this.database = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.recyclerDatabaseReference = FirebaseDatabase.getInstance().getReference("DailyShots");
        this.hamButton = (ImageButton) view.findViewById(C1420R.id.hamButtonID);
        this.filterButton = (ImageButton) view.findViewById(C1420R.id.filterButtonID);
        this.optionsButton = (ImageButton) view.findViewById(C1420R.id.optionsButtonID);
        MainActivity.searchViewToolbar.setVisibility(8);
        MainActivity.searchViewToolbar.animate().alpha(0.0f).start();
        MainActivity.toolbarMenu.setVisibility(8);
        MainActivity.toolbarMenu.animate().alpha(0.0f).start();
        MainActivity.fake.setVisibility(8);
        CheckNetworkConnection();
        downloadImages();
        this.hamButton.setOnClickListener(new C00261());
        this.filterButton.setOnClickListener(new C00282());
        this.optionsButton.setOnClickListener(new C00303());
        this.swipeLayout.setOnRefreshListener(new C07674());
        this.swipeLayout.setColorSchemeColors(getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454));
        this.slide_down = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_down);
        this.slide_up = AnimationUtils.loadAnimation(this.mContext, C1420R.anim.slide_up);
        this.imageRecyclerView.addOnScrollListener(new C07685());
        this.uploadFromCamera.setOnClickListener(new C00326());
        this.uploadFromStorage.setOnClickListener(new C00337());
    }

    public void CheckNetworkConnection() {
        FirebaseDatabase.getInstance().getReference(".info/connected").addValueEventListener(new C07698());
    }

    public void downloadImages() {
        wallpaperList = new ArrayList();
        this.eventListener = this.recyclerDatabaseReference.addValueEventListener(new C07719());
    }

    public void downloadImageToLocal(String fileName, String imageUri) {
        String finalFileName = fileName.toLowerCase();
        Intent intent = new Intent(this.mContext, FileDownloadService.class);
        Bundle bundle = new Bundle();
        bundle.putString(FileDownloadService.INTENT_URL, imageUri);
        bundle.putString(FileDownloadService.INTENT_FILE_NAME, finalFileName);
        intent.putExtras(bundle);
        Log.d(TAG, "downloadImageToLocal: Starting download service...");
        this.mContext.startService(intent);
        showSnackBar("Downloading image...");
    }

    private void showSnackBar(String message) {
        Snackbar.with(this.mContext, null);
        Snackbar.type(Type.SUCCESS);
        Snackbar.message(message);
        Snackbar.duration(Duration.SHORT);
        Snackbar.fillParent(true);
        Snackbar.textAlign(Align.LEFT);
        Snackbar.show();
    }

    public void downloadMoreImages() {
        wallpaperList = new ArrayList();
        this.recyclerDatabaseReference.orderByChild("Wallpapers").startAt((double) (page * 10)).limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {

            /* renamed from: Fragments.DailyShotsFragment$10$1 */
            class C07651 implements OnItemClickListener {
                C07651() {
                }

                public void OnItemClick(int position) {
                }

                public void OnItemLongClick(int position) {
                    Intent intent = new Intent(DailyShotsFragment.this.mContext, DailyPreviewActivity.class);
                    intent.putExtra("imageURL", ((FirebaseDailyShots) DailyShotsFragment.wallpaperList.get(position)).getCompressedURL());
                    DailyShotsFragment.this.startActivity(intent);
                }
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                DailyShotsFragment.wallpaperList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DailyShotsFragment.this.wallpapers = (FirebaseDailyShots) postSnapshot.getValue(FirebaseDailyShots.class);
                    DailyShotsFragment.wallpaperList.add(DailyShotsFragment.this.wallpapers);
                    Collections.reverse(DailyShotsFragment.wallpaperList);
                }
                DailyShotsFragment.this.mLayoutManager = new GridLayoutManager(DailyShotsFragment.this.mContext, 2);
                DailyShotsFragment.this.imageListAdaper = new DailyShotsAdapter(DailyShotsFragment.this.mContext, DailyShotsFragment.wallpaperList);
                DailyShotsFragment.this.imageRecyclerView.setLayoutManager(DailyShotsFragment.this.mLayoutManager);
                DailyShotsFragment.this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
                DailyShotsFragment.this.imageRecyclerView.setAdapter(DailyShotsFragment.this.imageListAdaper);
                DailyShotsFragment.this.mLastKey = dataSnapshot.getRef().push().getKey();
                DailyShotsFragment.this.imageListAdaper.setOnItemClickListener(new C07651());
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
