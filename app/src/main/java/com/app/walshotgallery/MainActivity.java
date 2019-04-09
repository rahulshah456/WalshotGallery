package com.app.walshotgallery;

import Adapters.CollectionTabsPagerAdapter;
import Adapters.GalleryTabsPagerAdapter;
import Adapters.SampleSuggestionsBuilder;
import Adapters.SearchTabsPagerAdapter;
import Adapters.SimpleAnimationListener;
import Adapters.WallpaperTabsPagerAdapter;
import Custom_UI.SlidingTabLayout;
import Custom_UI.SlidingTabLayout.TabColorizer;
import Fragments.AboutFragment;
import Fragments.AccountFragment;
import Fragments.DailyShotsFragment;
import Fragments.LoginFragment;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.adobe.android.common.io.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.actions.SearchIntents;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.cryse.widget.persistentsearch.PersistentSearchView;
import org.cryse.widget.persistentsearch.PersistentSearchView.HomeButtonListener;
import org.cryse.widget.persistentsearch.PersistentSearchView.SearchListener;
import org.cryse.widget.persistentsearch.SearchItem;
import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private static String AdMOB_ID = "ca-app-pub-3940256099942544~3347511713";
    public static CharSequence[] GalleryTabsMenu = new CharSequence[]{"Images", "Videos"};
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = "MainActivity";
    public static CardView fake;
    public static FrameLayout frameLayout;
    public static GoogleApiClient googleApiClient;
    public static DrawerLayout mDrawerLayout;
    public static NavigationView mNavigationView;
    public static RelativeLayout mainLayout;
    public static TextView profileEmail;
    public static TextView profileName;
    public static CircularImageView profilePic;
    public static PersistentSearchView searchViewToolbar;
    public static SlidingTabLayout slidingTabLayout;
    public static RelativeLayout toolbarLayout;
    public static RelativeLayout toolbarMenu;
    public static ViewPager viewPager;
    private CharSequence[] CollectionsTabsMenu = new CharSequence[]{"Featured", "Editor's"};
    private CharSequence[] SearchTabsMenu = new CharSequence[]{"Wallpapers", "Collections"};
    private CharSequence[] WallpaperTabsMenu = new CharSequence[]{"Latest", "Popular", "Editor's"};
    private int clickedNavItem = 0;
    private ImageButton filterMenu;
    private Boolean isClicked = Boolean.valueOf(false);
    private MenuItem item_search;
    private FirebaseAuth mAuth;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mRecyclerView;
    private View mSearchTintView;
    private ImageButton optionsMenu;
    private SharedPreferences preferences;
    private Toolbar searchToolbar;
    private Menu search_menu;
    private Animation slideDown;
    private Animation slideUp;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$2 */
    class C13992 implements OnClickListener {
        C13992() {
        }

        public void onClick(View v) {
            MainActivity.searchViewToolbar.cancelEditing();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$4 */
    class C14014 implements OnClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$4$1 */
        class C14001 implements OnMenuItemClickListener {

            /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$4$1$1 */
            class C17781 implements ResultCallback<Status> {
                C17781() {
                }

                public void onResult(@NonNull Status status) {
                    Log.d(MainActivity.TAG, "User Succesfully Signed Out");
                }
            }

            C14001() {
            }

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == C1420R.id.signout_menuID) {
                    MainActivity.this.mAuth.signOut();
                    Auth.GoogleSignInApi.signOut(MainActivity.googleApiClient).setResultCallback(new C17781());
                    Glide.with(MainActivity.this.getApplicationContext()).load(Integer.valueOf(C1420R.drawable.walshot_logo)).into(MainActivity.profilePic);
                    MainActivity.profileName.setText("Walshot Gallery");
                    MainActivity.profileEmail.setText("droid2developers.com");
                } else if (item.getItemId() == C1420R.id.signin_menuID) {
                    MainActivity.mainLayout.setVisibility(8);
                    MainActivity.frameLayout.setVisibility(0);
                    MainActivity.this.getSupportFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new LoginFragment()).commit();
                    MainActivity.mNavigationView.setCheckedItem(C1420R.id.nav_accountID);
                } else if (item.getItemId() == C1420R.id.exit_menuID) {
                    MainActivity.this.closeApplication();
                } else if (item.getItemId() == C1420R.id.settings_menuID) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                }
                return true;
            }
        }

        C14014() {
        }

        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(MainActivity.this, MainActivity.this.optionsMenu);
            if (MainActivity.this.mAuth.getCurrentUser() != null) {
                popupMenu.getMenuInflater().inflate(C1420R.menu.menu_main2, popupMenu.getMenu());
            } else {
                popupMenu.getMenuInflater().inflate(C1420R.menu.menu_main, popupMenu.getMenu());
            }
            popupMenu.setOnMenuItemClickListener(new C14001());
            popupMenu.show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$9 */
    class C14059 implements OnClickListener {
        C14059() {
        }

        public void onClick(View v) {
            if (VERSION.SDK_INT >= 21) {
                MainActivity.this.circleReveal(C1420R.id.searchtoolbarID, 1, true, false);
            } else {
                MainActivity.this.searchToolbar.setVisibility(8);
            }
        }
    }

    public static class QueryEvent {
        String searchQuery;

        public QueryEvent(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        public String getSearchQuery() {
            return this.searchQuery;
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$1 */
    class C17751 implements HomeButtonListener {
        C17751() {
        }

        public void onHomeButtonClick() {
            MainActivity.mDrawerLayout.openDrawer(MainActivity.mNavigationView);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$3 */
    class C17773 implements SearchListener {

        /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$3$1 */
        class C17761 extends SimpleAnimationListener {
            C17761() {
            }

            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                MainActivity.this.mSearchTintView.setVisibility(8);
            }
        }

        C17773() {
        }

        public void onSearchEditOpened() {
            MainActivity.searchViewToolbar.setSuggestionBuilder(new SampleSuggestionsBuilder(MainActivity.this));
            MainActivity.this.mSearchTintView.setVisibility(0);
            MainActivity.this.mSearchTintView.animate().alpha(1.0f).setDuration(300).setListener(new SimpleAnimationListener()).start();
            MainActivity.toolbarMenu.setVisibility(8);
            MainActivity.toolbarMenu.animate().alpha(0.0f).start();
        }

        public void onSearchEditClosed() {
            MainActivity.this.mSearchTintView.animate().alpha(0.0f).setDuration(300).setListener(new C17761()).start();
            MainActivity.toolbarMenu.setVisibility(0);
            MainActivity.toolbarMenu.animate().alpha(1.0f).start();
        }

        public boolean onSearchEditBackPressed() {
            return false;
        }

        public void onSearchExit() {
        }

        public void onSearchTermChanged(String term) {
        }

        public void onSearch(String string) {
            Context context = MainActivity.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" Searched");
            Toast.makeText(context, stringBuilder.toString(), 1).show();
            ArrayList<String> list;
            if (MainActivity.this.getSharedPreferences(MainActivity.PREF_NAME, 0).contains("history")) {
                list = new ArrayList();
                list = MainActivity.this.getArrayList("history");
                if (list.contains(string)) {
                    Log.d("Saving Search", "Item is already in List");
                } else {
                    list.add(string);
                    MainActivity.this.saveArrayList(list, "history");
                }
            } else {
                list = new ArrayList();
                list.add(string);
                MainActivity.this.saveArrayList(list, "history");
            }
            if (!MainActivity.mNavigationView.getMenu().findItem(C1420R.id.nav_galleryID).isChecked()) {
                MainActivity.frameLayout.setVisibility(8);
                MainActivity.slidingTabLayout.setVisibility(0);
                MainActivity.this.setSearchTabs();
                EventBus.getDefault().post(new QueryEvent(string.toLowerCase()));
            }
        }

        public boolean onSuggestion(SearchItem searchItem) {
            Log.d("onSuggestion", searchItem.getTitle());
            return false;
        }

        public void onSearchCleared() {
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$6 */
    class C17796 implements OnConnectionFailedListener {
        C17796() {
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$7 */
    class C17807 implements DrawerListener {
        C17807() {
        }

        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        public void onDrawerOpened(@NonNull View drawerView) {
        }

        public void onDrawerClosed(@NonNull View drawerView) {
            if (MainActivity.this.isClicked.booleanValue()) {
                switch (MainActivity.this.clickedNavItem) {
                    case C1420R.id.nav_WallpapersID:
                        MainActivity.this.setWallpapersTabs();
                        MainActivity.this.isClicked = Boolean.valueOf(false);
                        return;
                    case C1420R.id.nav_aboutUsID:
                        MainActivity.this.getSupportFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new AboutFragment()).commit();
                        MainActivity.this.isClicked = Boolean.valueOf(false);
                        return;
                    case C1420R.id.nav_accountID:
                        if (MainActivity.this.mAuth.getCurrentUser() != null) {
                            MainActivity.this.getSupportFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new AccountFragment()).commit();
                        } else {
                            MainActivity.this.getSupportFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new LoginFragment()).commit();
                        }
                        MainActivity.this.isClicked = Boolean.valueOf(false);
                        return;
                    case C1420R.id.nav_collectionsID:
                        MainActivity.this.setCollectionsTabs();
                        MainActivity.this.isClicked = Boolean.valueOf(false);
                        return;
                    case C1420R.id.nav_dailyShotsID:
                        MainActivity.this.getSupportFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new DailyShotsFragment()).commit();
                        MainActivity.this.isClicked = Boolean.valueOf(false);
                        return;
                    case C1420R.id.nav_galleryID:
                        MainActivity.this.setGalleryTabs();
                        MainActivity.this.isClicked = Boolean.valueOf(false);
                        return;
                    default:
                        return;
                }
            }
        }

        public void onDrawerStateChanged(int newState) {
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$8 */
    class C17818 extends TypeToken<ArrayList<String>> {
        C17818() {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(134217728, 134217728);
        setContentView(C1420R.layout.activity_main);
        MobileAds.initialize(this, AdMOB_ID);
        this.toolbar = (Toolbar) findViewById(C1420R.id.mainToolbarID);
        setSupportActionBar(this.toolbar);
        this.mAuth = FirebaseAuth.getInstance();
        frameLayout = (FrameLayout) findViewById(C1420R.id.frameLayoutID);
        slidingTabLayout = (SlidingTabLayout) findViewById(C1420R.id.slidingTabsID);
        mDrawerLayout = (DrawerLayout) findViewById(C1420R.id.drawer_layoutID);
        mNavigationView = (NavigationView) findViewById(C1420R.id.navigation_viewID);
        View headerView = mNavigationView.getHeaderView(0);
        profileName = (TextView) headerView.findViewById(C1420R.id.userProfileNameID);
        profilePic = (CircularImageView) headerView.findViewById(C1420R.id.userProfilePicID);
        profileEmail = (TextView) headerView.findViewById(C1420R.id.userProfileEmailID);
        viewPager = (ViewPager) findViewById(C1420R.id.viewPagerID);
        this.optionsMenu = (ImageButton) findViewById(C1420R.id.optionsMenuID);
        this.filterMenu = (ImageButton) findViewById(C1420R.id.filterMenuID);
        this.slideDown = AnimationUtils.loadAnimation(this, C1420R.anim.slide_down);
        this.slideUp = AnimationUtils.loadAnimation(this, C1420R.anim.swipe_up);
        searchViewToolbar = (PersistentSearchView) findViewById(C1420R.id.searchviewToolbarID);
        toolbarMenu = (RelativeLayout) findViewById(C1420R.id.toolbarMenuID);
        mainLayout = (RelativeLayout) findViewById(C1420R.id.mainLayoutID);
        this.mSearchTintView = findViewById(C1420R.id.view_search_tint);
        fake = (CardView) findViewById(C1420R.id.fakeID);
        this.preferences = getSharedPreferences(PREF_NAME, 0);
        final Editor editor = this.preferences.edit();
        mNavigationView.setNavigationItemSelectedListener(this);
        setSearchtollbar();
        searchViewToolbar.setHomeButtonListener(new C17751());
        this.mSearchTintView.setOnClickListener(new C13992());
        searchViewToolbar.setSuggestionBuilder(new SampleSuggestionsBuilder(this));
        searchViewToolbar.setSearchListener(new C17773());
        this.optionsMenu.setOnClickListener(new C14014());
        this.filterMenu.setOnClickListener(new OnClickListener() {

            /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$5$1 */
            class C14021 implements OnMenuItemClickListener {
                C14021() {
                }

                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == C1420R.id.nameID) {
                        editor.putInt("folderOrder", 1);
                        editor.commit();
                        MainActivity.mainLayout.setVisibility(0);
                        MainActivity.frameLayout.setVisibility(4);
                        MainActivity.this.setGalleryTabs();
                        MainActivity.mNavigationView.setCheckedItem(C1420R.id.nav_galleryID);
                    } else if (item.getItemId() == C1420R.id.sizeID) {
                        editor.putInt("folderOrder", 2);
                        editor.commit();
                        MainActivity.mainLayout.setVisibility(0);
                        MainActivity.frameLayout.setVisibility(4);
                        MainActivity.this.setGalleryTabs();
                        MainActivity.mNavigationView.setCheckedItem(C1420R.id.nav_galleryID);
                    } else {
                        editor.putInt("folderOrder", 0);
                        editor.commit();
                        MainActivity.mainLayout.setVisibility(0);
                        MainActivity.frameLayout.setVisibility(4);
                        MainActivity.this.setGalleryTabs();
                        MainActivity.mNavigationView.setCheckedItem(C1420R.id.nav_galleryID);
                    }
                    return true;
                }
            }

            /* renamed from: com.walshotbeta.walshotvbeta.MainActivity$5$2 */
            class C14032 implements OnMenuItemClickListener {
                C14032() {
                }

                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == C1420R.id.popularID) {
                        editor.putInt("Order", 1);
                        editor.commit();
                        MainActivity.frameLayout.setVisibility(8);
                        MainActivity.mainLayout.setVisibility(0);
                        MainActivity.this.setWallpapersTabs();
                        MainActivity.mNavigationView.setCheckedItem(C1420R.id.nav_WallpapersID);
                    } else if (item.getItemId() == C1420R.id.oldestID) {
                        editor.putInt("Order", 2);
                        editor.commit();
                        MainActivity.frameLayout.setVisibility(8);
                        MainActivity.mainLayout.setVisibility(0);
                        MainActivity.this.setWallpapersTabs();
                        MainActivity.mNavigationView.setCheckedItem(C1420R.id.nav_WallpapersID);
                    } else {
                        editor.putInt("Order", 0);
                        editor.commit();
                        MainActivity.frameLayout.setVisibility(8);
                        MainActivity.mainLayout.setVisibility(0);
                        MainActivity.this.setWallpapersTabs();
                        MainActivity.mNavigationView.setCheckedItem(C1420R.id.nav_WallpapersID);
                    }
                    return true;
                }
            }

            public void onClick(View v) {
                if (MainActivity.mNavigationView.getMenu().findItem(C1420R.id.nav_galleryID).isChecked()) {
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, MainActivity.this.optionsMenu);
                    popupMenu.getMenuInflater().inflate(C1420R.menu.folder_preference, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new C14021());
                    popupMenu.show();
                    return;
                }
                popupMenu = new PopupMenu(MainActivity.this, MainActivity.this.optionsMenu);
                popupMenu.getMenuInflater().inflate(C1420R.menu.preference_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new C14032());
                popupMenu.show();
            }
        });
        googleApiClient = new Builder(this).enableAutoManage(this, new C17796()).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(C1420R.string.default_web_client_id)).requestId().build()).build();
        if (this.mAuth.getCurrentUser() != null) {
            profileName.setText(this.mAuth.getCurrentUser().getDisplayName());
            profileEmail.setText(this.mAuth.getCurrentUser().getEmail());
            Glide.with(this).load(this.mAuth.getCurrentUser().getPhotoUrl()).apply(new RequestOptions().centerCrop()).into(profilePic);
        }
        SharedPreferences getPrefs = getSharedPreferences(PREF_NAME, 0);
        if (getPrefs.contains("launch")) {
            switch (getPrefs.getInt("launch", 3)) {
                case 0:
                    frameLayout.setVisibility(8);
                    mainLayout.setVisibility(0);
                    setWallpapersTabs();
                    mNavigationView.setCheckedItem(C1420R.id.nav_WallpapersID);
                    break;
                case 1:
                    frameLayout.setVisibility(8);
                    mainLayout.setVisibility(0);
                    setCollectionsTabs();
                    mNavigationView.setCheckedItem(C1420R.id.nav_collectionsID);
                    break;
                case 2:
                    mainLayout.setVisibility(8);
                    frameLayout.setVisibility(0);
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new DailyShotsFragment()).commit();
                    mNavigationView.setCheckedItem(C1420R.id.nav_dailyShotsID);
                    break;
                case 3:
                    mainLayout.setVisibility(0);
                    frameLayout.setVisibility(4);
                    setGalleryTabs();
                    mNavigationView.setCheckedItem(C1420R.id.nav_galleryID);
                    break;
                default:
                    break;
            }
        }
        mainLayout.setVisibility(0);
        frameLayout.setVisibility(4);
        setGalleryTabs();
        mNavigationView.setCheckedItem(C1420R.id.nav_galleryID);
        mDrawerLayout.addDrawerListener(new C17807());
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case C1420R.id.nav_WallpapersID:
                this.isClicked = Boolean.valueOf(true);
                this.clickedNavItem = C1420R.id.nav_WallpapersID;
                frameLayout.setVisibility(8);
                mainLayout.setVisibility(0);
                break;
            case C1420R.id.nav_aboutUsID:
                this.isClicked = Boolean.valueOf(true);
                this.clickedNavItem = C1420R.id.nav_aboutUsID;
                mainLayout.setVisibility(8);
                frameLayout.setVisibility(0);
                break;
            case C1420R.id.nav_accountID:
                this.isClicked = Boolean.valueOf(true);
                this.clickedNavItem = C1420R.id.nav_accountID;
                mainLayout.setVisibility(8);
                frameLayout.setVisibility(0);
                break;
            case C1420R.id.nav_collectionsID:
                this.isClicked = Boolean.valueOf(true);
                this.clickedNavItem = C1420R.id.nav_collectionsID;
                mainLayout.setVisibility(0);
                frameLayout.setVisibility(8);
                break;
            case C1420R.id.nav_dailyShotsID:
                this.isClicked = Boolean.valueOf(true);
                this.clickedNavItem = C1420R.id.nav_dailyShotsID;
                mainLayout.setVisibility(8);
                frameLayout.setVisibility(0);
                break;
            case C1420R.id.nav_galleryID:
                this.isClicked = Boolean.valueOf(true);
                this.clickedNavItem = C1420R.id.nav_galleryID;
                mainLayout.setVisibility(0);
                frameLayout.setVisibility(8);
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(mNavigationView);
        return true;
    }

    private void scanFile(Context context, String[] path) {
        MediaScannerConnection.scanFile(context, path, null, null);
    }

    public void hideAlbum(String path, Context context) {
        File file = new File(new File(path), FileUtils.NO_MEDIA);
        if (!file.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                out.flush();
                out.close();
                scanFile(context, new String[]{file.getAbsolutePath()});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void closeApplication() {
        finish();
    }

    public void saveArrayList(ArrayList<String> list, String key) {
        Editor editor = getSharedPreferences(PREF_NAME, 0).edit();
        editor.putString(key, new Gson().toJson((Object) list));
        editor.apply();
    }

    public ArrayList<String> getArrayList(String key) {
        return (ArrayList) new Gson().fromJson(getSharedPreferences(PREF_NAME, 0).getString(key, null), new C17818().getType());
    }

    public void setSearchtollbar() {
        this.searchToolbar = (Toolbar) findViewById(C1420R.id.searchtoolbarID);
        if (this.searchToolbar != null) {
            this.searchToolbar.inflateMenu(C1420R.menu.menu_search);
            this.search_menu = this.searchToolbar.getMenu();
            this.searchToolbar.setNavigationOnClickListener(new C14059());
            this.item_search = this.search_menu.findItem(C1420R.id.action_filter_search);
            MenuItemCompat.setOnActionExpandListener(this.item_search, new OnActionExpandListener() {
                @SuppressLint({"ResourceAsColor"})
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    if (VERSION.SDK_INT >= 21) {
                        MainActivity.this.circleReveal(C1420R.id.searchtoolbarID, 1, true, false);
                        MainActivity.slidingTabLayout.setVisibility(0);
                    } else {
                        MainActivity.this.searchToolbar.setVisibility(8);
                        MainActivity.slidingTabLayout.setVisibility(0);
                    }
                    return true;
                }

                public boolean onMenuItemActionExpand(MenuItem item) {
                    MainActivity.slidingTabLayout.startAnimation(MainActivity.this.slideUp);
                    MainActivity.slidingTabLayout.setVisibility(8);
                    return true;
                }
            });
            initSearchView();
            return;
        }
        Log.d("toolbar", "setSearchtollbar: NULL");
    }

    public void initSearchView() {
        final SearchView searchView = (SearchView) this.search_menu.findItem(C1420R.id.action_filter_search).getActionView();
        searchView.setSubmitButtonEnabled(false);
        ((ImageView) searchView.findViewById(C1420R.id.search_close_btn)).setImageResource(C1420R.drawable.ic_close_black_24dp);
        EditText txtSearch = (EditText) searchView.findViewById(C1420R.id.search_src_text);
        txtSearch.setHint("Search...");
        txtSearch.setHintTextColor(-7829368);
        txtSearch.setTextColor(getResources().getColor(C1420R.color.colorPrimary));
        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(C1420R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, Integer.valueOf(C1420R.drawable.search_cursor));
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public void callSearch(String query) {
                String str = SearchIntents.EXTRA_QUERY;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(query);
                Log.i(str, stringBuilder.toString());
                MainActivity.frameLayout.setVisibility(8);
                MainActivity.slidingTabLayout.setVisibility(0);
                MainActivity.this.setSearchTabs();
                MainActivity.this.getSupportActionBar().setTitle("Search Results");
                EventBus.getDefault().post(new QueryEvent(query.toLowerCase()));
                MainActivity.this.circleReveal(C1420R.id.searchtoolbarID, 1, true, false);
            }
        });
    }

    public void setWallpapersTabs() {
        viewPager.setAdapter(new WallpaperTabsPagerAdapter(getSupportFragmentManager(), this.WallpaperTabsMenu, 3));
        slidingTabLayout.setTextColor(getResources().getColor(C1420R.color.light));
        slidingTabLayout.setTextColorSelected(getResources().getColor(C1420R.color.black));
        slidingTabLayout.setDistributeEvenly();
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setTabSelected(0);
        slidingTabLayout.setCustomTabColorizer(new TabColorizer() {
            public int getIndicatorColor(int position) {
                return MainActivity.this.getResources().getColor(C1420R.color.tab_indicator);
            }
        });
    }

    public void setGalleryTabs() {
        viewPager.setAdapter(new GalleryTabsPagerAdapter(getSupportFragmentManager(), GalleryTabsMenu, 2));
        slidingTabLayout.setTextColor(getResources().getColor(C1420R.color.light));
        slidingTabLayout.setTextColorSelected(getResources().getColor(C1420R.color.black));
        slidingTabLayout.setDistributeEvenly();
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setTabSelected(0);
        slidingTabLayout.setCustomTabColorizer(new TabColorizer() {
            public int getIndicatorColor(int position) {
                return MainActivity.this.getResources().getColor(C1420R.color.tab_indicator);
            }
        });
    }

    public void setCollectionsTabs() {
        viewPager.setAdapter(new CollectionTabsPagerAdapter(getSupportFragmentManager(), this.CollectionsTabsMenu, 2));
        slidingTabLayout.setTextColor(getResources().getColor(C1420R.color.light));
        slidingTabLayout.setTextColorSelected(getResources().getColor(C1420R.color.black));
        slidingTabLayout.setDistributeEvenly();
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setTabSelected(0);
        slidingTabLayout.setCustomTabColorizer(new TabColorizer() {
            public int getIndicatorColor(int position) {
                return MainActivity.this.getResources().getColor(C1420R.color.tab_indicator);
            }
        });
    }

    public void setSearchTabs() {
        viewPager.setAdapter(new SearchTabsPagerAdapter(getSupportFragmentManager(), this.SearchTabsMenu, 2));
        slidingTabLayout.setTextColor(getResources().getColor(C1420R.color.light));
        slidingTabLayout.setTextColorSelected(getResources().getColor(C1420R.color.black));
        slidingTabLayout.setDistributeEvenly();
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setTabSelected(0);
        slidingTabLayout.setCustomTabColorizer(new TabColorizer() {
            public int getIndicatorColor(int position) {
                return MainActivity.this.getResources().getColor(C1420R.color.tab_indicator);
            }
        });
    }

    @RequiresApi(api = 21)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
        Animator anim;
        final View myView = findViewById(viewID);
        int width = myView.getWidth();
        if (posFromRight > 0) {
            width -= (getResources().getDimensionPixelSize(C1420R.dimen.abc_action_button_min_width_material) * posFromRight) - (getResources().getDimensionPixelSize(C1420R.dimen.abc_action_button_min_width_material) / 2);
        }
        if (containsOverflow) {
            width -= getResources().getDimensionPixelSize(C1420R.dimen.abc_action_button_min_width_overflow_material);
        }
        int cx = width;
        int cy = myView.getHeight() / 2;
        if (isShow) {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0.0f, (float) width);
        } else {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0.0f);
        }
        anim.setDuration(220);
        anim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(4);
                }
            }
        });
        if (isShow) {
            myView.setVisibility(0);
        }
        anim.start();
    }

    private void scanDeleteFile(String path) {
        try {
            MediaScannerConnection.scanFile(this, new String[]{path}, null, new OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Scanned ");
                    stringBuilder.append(path);
                    stringBuilder.append(":");
                    Log.d("ExternalStorage", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("-> uri=");
                    stringBuilder.append(uri);
                    Log.d("ExternalStorage", stringBuilder.toString());
                    MainActivity.this.getApplicationContext().getContentResolver().delete(uri, null, null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawer(mNavigationView);
        } else {
            super.onBackPressed();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDrawerToggle.onConfigurationChanged(newConfig);
    }

    protected void onStart() {
        super.onStart();
        if (!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }
}
