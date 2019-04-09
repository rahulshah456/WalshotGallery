package com.app.walshotgallery;

import Adapters.ConnectionDetector;
import Adapters.DailyShotsAdapter;
import Adapters.DailyShotsAdapter.OnItemClickListener;
import Modals.FirebaseDailyShots;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.FileDownloadService;

public class MyUploadActivity extends AppCompatActivity {
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = MyUploadActivity.class.getSimpleName();
    private static int page = 0;
    public static List<FirebaseDailyShots> wallpaperList;
    private final String CONTENT = "contentType";
    private RelativeLayout connectionLost;
    private FirebaseDatabase database;
    private ValueEventListener eventListener;
    private DailyShotsAdapter imageListAdaper;
    private RecyclerView imageRecyclerView;
    private boolean isLoading = false;
    private int itemsPerPage = 10;
    private DatabaseReference likeDatabase;
    private RelativeLayout loadingLayout;
    private FirebaseAuth mAuth;
    private String mLastKey = null;
    private LayoutManager mLayoutManager;
    private Parcelable mListState;
    private ConnectionDetector networkState;
    private ImageView noConnectionGif;
    private SharedPreferences preferences;
    private DatabaseReference recyclerDatabaseReference;
    private Animation slide_down;
    private Animation slide_up;
    private FirebaseStorage storage;
    private SwipeRefreshLayout swipeLayout;
    private Toolbar toolbar;
    private FirebaseDailyShots wallpapers;

    /* renamed from: com.walshotbeta.walshotvbeta.MyUploadActivity$1 */
    class C17821 implements OnRefreshListener {

        /* renamed from: com.walshotbeta.walshotvbeta.MyUploadActivity$1$1 */
        class C14061 implements Runnable {
            C14061() {
            }

            public void run() {
                MyUploadActivity.this.swipeLayout.setRefreshing(false);
            }
        }

        C17821() {
        }

        public void onRefresh() {
            MyUploadActivity.this.CheckNetworkConnection();
            MyUploadActivity.this.connectionLost.setVisibility(4);
            MyUploadActivity.this.imageRecyclerView.setVisibility(0);
            MyUploadActivity.this.downloadImages();
            new Handler().postDelayed(new C14061(), 4000);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MyUploadActivity$2 */
    class C17832 extends OnScrollListener {
        C17832() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MyUploadActivity$3 */
    class C17843 implements ValueEventListener {
        C17843() {
        }

        public void onDataChange(DataSnapshot snapshot) {
            if (((Boolean) snapshot.getValue(Boolean.class)).booleanValue()) {
                MyUploadActivity.this.imageRecyclerView.setVisibility(0);
                MyUploadActivity.this.noConnectionGif.setVisibility(4);
                MyUploadActivity.this.connectionLost.setVisibility(4);
                return;
            }
            MyUploadActivity.this.imageRecyclerView.setVisibility(8);
            Glide.with(MyUploadActivity.this).asGif().load(Integer.valueOf(C1420R.drawable.no_connection)).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(MyUploadActivity.this.noConnectionGif);
            MyUploadActivity.this.connectionLost.setVisibility(0);
        }

        public void onCancelled(DatabaseError error) {
            System.err.println("Listener was cancelled");
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1420R.layout.activity_my_upload);
        this.toolbar = (Toolbar) findViewById(C1420R.id.mainToolbarID);
        this.toolbar.setTitle("My Uploads");
        setSupportActionBar(this.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        this.imageRecyclerView = (RecyclerView) findViewById(C1420R.id.images_recycler_view);
        this.connectionLost = (RelativeLayout) findViewById(C1420R.id.noConnectionLayoutID);
        this.loadingLayout = (RelativeLayout) findViewById(C1420R.id.loadingRecyclerID);
        this.noConnectionGif = (ImageView) findViewById(C1420R.id.no_connectionImageID);
        this.networkState = new ConnectionDetector(this);
        this.swipeLayout = (SwipeRefreshLayout) findViewById(C1420R.id.swipeRefreshID);
        this.database = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.storage = FirebaseStorage.getInstance();
        this.recyclerDatabaseReference = FirebaseDatabase.getInstance().getReference("DailyShots");
        this.preferences = getSharedPreferences(PREF_NAME, 0);
        CheckNetworkConnection();
        downloadImages();
        this.swipeLayout.setOnRefreshListener(new C17821());
        this.swipeLayout.setColorSchemeColors(new int[]{getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454)});
        this.slide_down = AnimationUtils.loadAnimation(this, C1420R.anim.slide_down);
        this.slide_up = AnimationUtils.loadAnimation(this, C1420R.anim.slide_up);
        this.imageRecyclerView.addOnScrollListener(new C17832());
    }

    public void downloadImageToLocal(String fileName, String imageUri) {
        String finalFileName = fileName.toLowerCase();
        Intent intent = new Intent(this, FileDownloadService.class);
        Bundle bundle = new Bundle();
        bundle.putString(FileDownloadService.INTENT_URL, imageUri);
        bundle.putString(FileDownloadService.INTENT_FILE_NAME, finalFileName);
        intent.putExtras(bundle);
        Log.d(TAG, "downloadImageToLocal: Starting download service...");
        startService(intent);
        showSnackBar("Downloading image...");
    }

    private void showSnackBar(String message) {
        Snackbar.with(this, null);
        Snackbar.type(Type.SUCCESS);
        Snackbar.message(message);
        Snackbar.duration(Duration.SHORT);
        Snackbar.fillParent(true);
        Snackbar.textAlign(Align.LEFT);
        Snackbar.show();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void CheckNetworkConnection() {
        FirebaseDatabase.getInstance().getReference(".info/connected").addValueEventListener(new C17843());
    }

    public void downloadImages() {
        wallpaperList = new ArrayList();
        final Editor editor = this.preferences.edit();
        this.eventListener = this.recyclerDatabaseReference.addValueEventListener(new ValueEventListener() {

            /* renamed from: com.walshotbeta.walshotvbeta.MyUploadActivity$4$1 */
            class C17871 implements OnItemClickListener {
                C17871() {
                }

                public void OnItemClick(final int position) {
                    PopupMenu popupMenu = new PopupMenu(MyUploadActivity.this, MyUploadActivity.this.imageRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(C1420R.id.optionsID));
                    popupMenu.getMenuInflater().inflate(C1420R.menu.dailyshots_usermenu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                        /* renamed from: com.walshotbeta.walshotvbeta.MyUploadActivity$4$1$1$1 */
                        class C17851 implements OnFailureListener {
                            C17851() {
                            }

                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MyUploadActivity.this, "Failed To Delete", 1).show();
                            }
                        }

                        public boolean onMenuItemClick(MenuItem item) {
                            String fileName;
                            if (item.getItemId() == C1420R.id.save_menuID) {
                                String saveShot = ((FirebaseDailyShots) MyUploadActivity.wallpaperList.get(position)).getCompressedURL();
                                fileName = new StringBuilder();
                                fileName.append("DailyShot");
                                fileName.append(System.currentTimeMillis());
                                fileName.append(".JPEG");
                                MyUploadActivity.this.downloadImageToLocal(fileName.toString(), saveShot);
                            } else if (item.getItemId() == C1420R.id.delete_menuID) {
                                FirebaseDailyShots selectedItem = (FirebaseDailyShots) MyUploadActivity.wallpaperList.get(position);
                                fileName = selectedItem.getmKey();
                                MyUploadActivity.this.storage.getReferenceFromUrl(selectedItem.getCompressedURL()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    public void onSuccess(Void aVoid) {
                                        MyUploadActivity.this.recyclerDatabaseReference.child(fileName).removeValue();
                                        Toast.makeText(MyUploadActivity.this, "Image Successfully Deleted", 1).show();
                                    }
                                }).addOnFailureListener(new C17851());
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }

                public void OnItemLongClick(int position) {
                }
            }

            @SuppressLint({"ApplySharedPref"})
            public void onDataChange(DataSnapshot dataSnapshot) {
                MyUploadActivity.wallpaperList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    MyUploadActivity.this.wallpapers = (FirebaseDailyShots) postSnapshot.getValue(FirebaseDailyShots.class);
                    MyUploadActivity.this.wallpapers.setmKey(postSnapshot.getKey());
                    if (MyUploadActivity.this.wallpapers.getUserName() != null) {
                        String userName = MyUploadActivity.this.wallpapers.getUserName();
                        FirebaseUser currentUser = MyUploadActivity.this.mAuth.getCurrentUser();
                        currentUser.getClass();
                        if (userName.equals(currentUser.getEmail())) {
                            MyUploadActivity.wallpaperList.add(MyUploadActivity.this.wallpapers);
                            Collections.reverse(MyUploadActivity.wallpaperList);
                        } else {
                            Log.d("DownloadActivity", "RecyclerView is Empty");
                        }
                    }
                }
                editor.putInt("uploadCount", MyUploadActivity.wallpaperList.size());
                editor.commit();
                MyUploadActivity.this.mLayoutManager = new GridLayoutManager(MyUploadActivity.this, 1);
                MyUploadActivity.this.imageListAdaper = new DailyShotsAdapter(MyUploadActivity.this, MyUploadActivity.wallpaperList);
                MyUploadActivity.this.imageRecyclerView.setLayoutManager(MyUploadActivity.this.mLayoutManager);
                MyUploadActivity.this.imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
                MyUploadActivity.this.imageRecyclerView.setAdapter(MyUploadActivity.this.imageListAdaper);
                MyUploadActivity.this.mLastKey = dataSnapshot.getRef().push().getKey();
                MyUploadActivity.this.imageListAdaper.setOnItemClickListener(new C17871());
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
