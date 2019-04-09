package Fragments;

import Adapters.GalleryTabsPagerAdapter;
import Custom_UI.SlidingTabLayout.TabColorizer;
import Modals.DeviceImages;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.MainActivity;
import com.walshotbeta.walshotvbeta.MyUploadActivity;
import com.walshotbeta.walshotvbeta.SettingsActivity;
import com.walshotbeta.walshotvbeta.UserDeviceActivity;
import com.walshotbeta.walshotvbeta.UserDownloadsActivity;
import java.io.File;
import java.util.ArrayList;

public class AccountFragment extends Fragment {
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = AccountFragment.class.getSimpleName();
    public static ArrayList<DeviceImages> mImagesList;
    CardView downloads;
    TextView downloadsNum;
    CardView favourites;
    ImageButton filterButton;
    ImageButton hamButton;
    FirebaseAuth mAuth;
    Context mContext;
    CardView myDevice;
    ImageButton optionsButton;
    private SharedPreferences preferences;
    TextView profileName;
    CircularImageView profilePic;
    ScrollView scrollView;
    CardView uploads;
    TextView uploadsNum;

    /* renamed from: Fragments.AccountFragment$1 */
    class C00191 implements OnClickListener {
        C00191() {
        }

        public void onClick(View v) {
            MainActivity.mDrawerLayout.openDrawer(MainActivity.mNavigationView);
        }
    }

    /* renamed from: Fragments.AccountFragment$2 */
    class C00212 implements OnClickListener {

        /* renamed from: Fragments.AccountFragment$2$1 */
        class C00201 implements OnMenuItemClickListener {

            /* renamed from: Fragments.AccountFragment$2$1$1 */
            class C07631 implements ResultCallback<Status> {
                C07631() {
                }

                public void onResult(@NonNull Status status) {
                    Log.d(AccountFragment.TAG, "User Succesfully Signed Out");
                }
            }

            C00201() {
            }

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == C1420R.id.signout_menuID) {
                    AccountFragment.this.mAuth.signOut();
                    Auth.GoogleSignInApi.signOut(MainActivity.googleApiClient).setResultCallback(new C07631());
                    Glide.with(AccountFragment.this.mContext).load(Integer.valueOf(C1420R.drawable.walshot_logo)).into(MainActivity.profilePic);
                    MainActivity.profileName.setText("Walshot Gallery");
                    MainActivity.profileEmail.setText("droid2developers.com");
                    MainActivity.mainLayout.setVisibility(8);
                    MainActivity.frameLayout.setVisibility(0);
                    AccountFragment.this.getFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new LoginFragment()).commit();
                    MainActivity.mNavigationView.setCheckedItem((int) C1420R.id.nav_accountID);
                } else if (item.getItemId() == C1420R.id.signin_menuID) {
                    MainActivity.mainLayout.setVisibility(8);
                    MainActivity.frameLayout.setVisibility(0);
                    AccountFragment.this.getFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new LoginFragment()).commit();
                } else if (item.getItemId() == C1420R.id.exit_menuID) {
                    FragmentActivity activity = AccountFragment.this.getActivity();
                    activity.getClass();
                    activity.finish();
                } else if (item.getItemId() == C1420R.id.settings_menuID) {
                    AccountFragment.this.startActivity(new Intent(AccountFragment.this.mContext, SettingsActivity.class));
                }
                return true;
            }
        }

        C00212() {
        }

        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(AccountFragment.this.mContext, AccountFragment.this.optionsButton);
            if (AccountFragment.this.mAuth.getCurrentUser() != null) {
                popupMenu.getMenuInflater().inflate(C1420R.menu.menu_main2, popupMenu.getMenu());
            } else {
                popupMenu.getMenuInflater().inflate(C1420R.menu.menu_main, popupMenu.getMenu());
            }
            popupMenu.setOnMenuItemClickListener(new C00201());
            popupMenu.show();
        }
    }

    /* renamed from: Fragments.AccountFragment$3 */
    class C00223 implements OnClickListener {
        C00223() {
        }

        public void onClick(View v) {
            Toast.makeText(AccountFragment.this.getActivity(), "Coming Soon!", 0).show();
        }
    }

    /* renamed from: Fragments.AccountFragment$4 */
    class C00234 implements OnClickListener {
        C00234() {
        }

        public void onClick(View v) {
            AccountFragment.this.startActivity(new Intent(AccountFragment.this.getActivity(), MyUploadActivity.class));
        }
    }

    /* renamed from: Fragments.AccountFragment$5 */
    class C00245 implements OnClickListener {
        C00245() {
        }

        public void onClick(View v) {
            AccountFragment.this.startActivity(new Intent(AccountFragment.this.getActivity(), UserDownloadsActivity.class));
        }
    }

    /* renamed from: Fragments.AccountFragment$6 */
    class C00256 implements OnClickListener {
        C00256() {
        }

        public void onClick(View v) {
            AccountFragment.this.startActivity(new Intent(AccountFragment.this.getActivity(), UserDeviceActivity.class));
        }
    }

    /* renamed from: Fragments.AccountFragment$7 */
    class C07647 implements TabColorizer {
        C07647() {
        }

        public int getIndicatorColor(int position) {
            return AccountFragment.this.getResources().getColor(C1420R.color.tab_indicator);
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.account_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getActivity();
        mImagesList = new ArrayList();
        this.mAuth = FirebaseAuth.getInstance();
        this.profilePic = (CircularImageView) view.findViewById(C1420R.id.profilePicID);
        this.profileName = (TextView) view.findViewById(C1420R.id.profileNameID);
        this.uploads = (CardView) view.findViewById(C1420R.id.uploadCardID);
        this.downloads = (CardView) view.findViewById(C1420R.id.downloadsCardID);
        this.favourites = (CardView) view.findViewById(C1420R.id.favouritesCardID);
        this.myDevice = (CardView) view.findViewById(C1420R.id.deviceCardID);
        this.downloadsNum = (TextView) view.findViewById(C1420R.id.myDownloadsTextID);
        this.uploadsNum = (TextView) view.findViewById(C1420R.id.myUploadsTextID);
        mImagesList = getData();
        this.preferences = getActivity().getSharedPreferences(PREF_NAME, 0);
        this.hamButton = (ImageButton) view.findViewById(C1420R.id.hamButtonID);
        this.optionsButton = (ImageButton) view.findViewById(C1420R.id.optionsButtonID);
        MainActivity.searchViewToolbar.setVisibility(8);
        MainActivity.searchViewToolbar.animate().alpha(0.0f).start();
        MainActivity.toolbarMenu.setVisibility(8);
        MainActivity.toolbarMenu.animate().alpha(0.0f).start();
        MainActivity.fake.setVisibility(8);
        this.hamButton.setOnClickListener(new C00191());
        this.optionsButton.setOnClickListener(new C00212());
        if (mImagesList.size() != 0) {
            this.downloadsNum.setText(String.valueOf(mImagesList.size()));
        }
        SharedPreferences getPrefs = getActivity().getSharedPreferences(PREF_NAME, 0);
        if (getPrefs.contains("uploadCount")) {
            this.uploadsNum.setText(String.valueOf(getPrefs.getInt("uploadCount", 0)));
        }
        if (this.mAuth.getCurrentUser() != null) {
            this.profileName.setText(this.mAuth.getCurrentUser().getDisplayName().toUpperCase());
            Glide.with((Fragment) this).load(this.mAuth.getCurrentUser().getPhotoUrl()).apply(new RequestOptions().centerCrop()).into(this.profilePic);
        }
        this.favourites.setOnClickListener(new C00223());
        this.uploads.setOnClickListener(new C00234());
        this.downloads.setOnClickListener(new C00245());
        this.myDevice.setOnClickListener(new C00256());
    }

    private ArrayList<DeviceImages> getData() {
        ArrayList<DeviceImages> list = new ArrayList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(C1420R.string.app_name));
        File downloadsFolder = new File(stringBuilder.toString());
        if (downloadsFolder.exists()) {
            File[] files = downloadsFolder.listFiles();
            for (File file : files) {
                DeviceImages images = new DeviceImages();
                images.setName(file.getName());
                images.setUri(Uri.fromFile(file));
                list.add(images);
            }
        }
        return list;
    }

    public void setGalleryTabs() {
        MainActivity.viewPager.setAdapter(new GalleryTabsPagerAdapter(getFragmentManager(), MainActivity.GalleryTabsMenu, 2));
        MainActivity.slidingTabLayout.setTextColor(getResources().getColor(C1420R.color.lightWhite));
        MainActivity.slidingTabLayout.setTextColorSelected(getResources().getColor(C1420R.color.White));
        MainActivity.slidingTabLayout.setDistributeEvenly();
        MainActivity.slidingTabLayout.setViewPager(MainActivity.viewPager);
        MainActivity.slidingTabLayout.setTabSelected(0);
        MainActivity.slidingTabLayout.setCustomTabColorizer(new C07647());
    }
}
