package Fragments;

import Adapters.GalleryTabsPagerAdapter;
import Custom_UI.SlidingTabLayout.TabColorizer;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.MainActivity;
import com.walshotbeta.walshotvbeta.SettingsActivity;

public class AboutFragment extends Fragment {
    private static final String TAG = AboutFragment.class.getSimpleName();
    public ConstraintLayout bugReport;
    public ConstraintLayout donate;
    public TextView facebook;
    ImageButton filterButton;
    public TextView github;
    ImageButton hamButton;
    FirebaseAuth mAuth;
    Context mContext;
    ImageButton optionsButton;
    public ConstraintLayout rate;
    public TextView sendEmail;
    public TextView text0;
    public TextView text1;
    public TextView text2;
    public TextView text3;
    public TextView text4;
    public TextView text5;
    public TextView text6;
    public TextView text7;
    public TextView text8;
    public TextView text9;
    public TextView unsplash;
    public ConstraintLayout updates;

    /* renamed from: Fragments.AboutFragment$1 */
    class C00091 implements OnClickListener {
        C00091() {
        }

        public void onClick(View v) {
            MainActivity.mDrawerLayout.openDrawer(MainActivity.mNavigationView);
        }
    }

    /* renamed from: Fragments.AboutFragment$2 */
    class C00112 implements OnClickListener {

        /* renamed from: Fragments.AboutFragment$2$1 */
        class C00101 implements OnMenuItemClickListener {

            /* renamed from: Fragments.AboutFragment$2$1$1 */
            class C07621 implements ResultCallback<Status> {
                C07621() {
                }

                public void onResult(@NonNull Status status) {
                    Log.d(AboutFragment.TAG, "User Succesfully Signed Out");
                }
            }

            C00101() {
            }

            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == C1420R.id.signout_menuID) {
                    AboutFragment.this.mAuth.signOut();
                    Auth.GoogleSignInApi.signOut(MainActivity.googleApiClient).setResultCallback(new C07621());
                    Glide.with(AboutFragment.this.mContext).load(Integer.valueOf(C1420R.drawable.walshot_logo)).into(MainActivity.profilePic);
                    MainActivity.profileName.setText("Walshot Gallery");
                    MainActivity.profileEmail.setText("droid2developers.com");
                } else if (item.getItemId() == C1420R.id.signin_menuID) {
                    MainActivity.mainLayout.setVisibility(8);
                    MainActivity.frameLayout.setVisibility(0);
                    AboutFragment.this.getFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new LoginFragment()).commit();
                    MainActivity.mNavigationView.setCheckedItem((int) C1420R.id.nav_accountID);
                } else if (item.getItemId() == C1420R.id.exit_menuID) {
                    FragmentActivity activity = AboutFragment.this.getActivity();
                    activity.getClass();
                    activity.finish();
                } else if (item.getItemId() == C1420R.id.settings_menuID) {
                    AboutFragment.this.startActivity(new Intent(AboutFragment.this.mContext, SettingsActivity.class));
                }
                return true;
            }
        }

        C00112() {
        }

        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(AboutFragment.this.mContext, AboutFragment.this.optionsButton);
            if (AboutFragment.this.mAuth.getCurrentUser() != null) {
                popupMenu.getMenuInflater().inflate(C1420R.menu.menu_main2, popupMenu.getMenu());
            } else {
                popupMenu.getMenuInflater().inflate(C1420R.menu.menu_main, popupMenu.getMenu());
            }
            popupMenu.setOnMenuItemClickListener(new C00101());
            popupMenu.show();
        }
    }

    /* renamed from: Fragments.AboutFragment$3 */
    class C00123 implements OnClickListener {
        C00123() {
        }

        public void onClick(View v) {
            Intent emailSelectorIntent = new Intent("android.intent.action.SENDTO");
            emailSelectorIntent.setData(Uri.parse("mailto:"));
            Intent emailIntent = new Intent("android.intent.action.SEND");
            emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{"rahulkumarshah5000@gmail.com"});
            emailIntent.putExtra("android.intent.extra.SUBJECT", "Walshot Gallery");
            emailIntent.setSelector(emailSelectorIntent);
            FragmentActivity activity = AboutFragment.this.getActivity();
            activity.getClass();
            if (emailIntent.resolveActivity(activity.getPackageManager()) != null) {
                AboutFragment.this.startActivity(emailIntent);
            }
        }
    }

    /* renamed from: Fragments.AboutFragment$4 */
    class C00134 implements OnClickListener {
        C00134() {
        }

        public void onClick(View v) {
            AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://unsplash.com/search/photos/android-wallpaper")));
        }
    }

    /* renamed from: Fragments.AboutFragment$5 */
    class C00145 implements OnClickListener {
        C00145() {
        }

        public void onClick(View v) {
            AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/conan.scott.96")));
        }
    }

    /* renamed from: Fragments.AboutFragment$6 */
    class C00156 implements OnClickListener {
        C00156() {
        }

        public void onClick(View v) {
            Toast.makeText(AboutFragment.this.getActivity(), "This Link is Encrypted", 0).show();
        }
    }

    /* renamed from: Fragments.AboutFragment$7 */
    class C00167 implements OnClickListener {
        C00167() {
        }

        public void onClick(View v) {
            Intent emailSelectorIntent = new Intent("android.intent.action.SENDTO");
            emailSelectorIntent.setData(Uri.parse("mailto:"));
            Intent emailIntent = new Intent("android.intent.action.SEND");
            emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{"rahulkumarshah5000@gmail.com"});
            emailIntent.putExtra("android.intent.extra.SUBJECT", "Bug Report Walshot");
            emailIntent.setSelector(emailSelectorIntent);
            FragmentActivity activity = AboutFragment.this.getActivity();
            activity.getClass();
            if (emailIntent.resolveActivity(activity.getPackageManager()) != null) {
                AboutFragment.this.startActivity(emailIntent);
            }
        }
    }

    /* renamed from: Fragments.AboutFragment$8 */
    class C00178 implements OnClickListener {
        C00178() {
        }

        public void onClick(View v) {
            Toast.makeText(AboutFragment.this.getActivity(), "Available when walshot going live in playstore", 0).show();
        }
    }

    /* renamed from: Fragments.AboutFragment$9 */
    class C00189 implements OnClickListener {
        C00189() {
        }

        public void onClick(View v) {
            Toast.makeText(AboutFragment.this.getActivity(), "Not now idiot!", 0).show();
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.about_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getActivity();
        this.mAuth = FirebaseAuth.getInstance();
        this.sendEmail = (TextView) view.findViewById(C1420R.id.sendEmailID);
        this.facebook = (TextView) view.findViewById(C1420R.id.facebookID);
        this.github = (TextView) view.findViewById(C1420R.id.githubID);
        this.bugReport = (ConstraintLayout) view.findViewById(C1420R.id.bugID);
        this.rate = (ConstraintLayout) view.findViewById(C1420R.id.rateID);
        this.donate = (ConstraintLayout) view.findViewById(C1420R.id.donateID);
        this.updates = (ConstraintLayout) view.findViewById(C1420R.id.updateID);
        this.text0 = (TextView) view.findViewById(C1420R.id.text1ID);
        this.text1 = (TextView) view.findViewById(C1420R.id.text2ID);
        this.text2 = (TextView) view.findViewById(C1420R.id.text3ID);
        this.text3 = (TextView) view.findViewById(C1420R.id.text4ID);
        this.text4 = (TextView) view.findViewById(C1420R.id.text5ID);
        this.text5 = (TextView) view.findViewById(C1420R.id.text6ID);
        this.text6 = (TextView) view.findViewById(C1420R.id.text7ID);
        this.text7 = (TextView) view.findViewById(C1420R.id.text8ID);
        this.text8 = (TextView) view.findViewById(C1420R.id.text9ID);
        this.text9 = (TextView) view.findViewById(C1420R.id.text10ID);
        this.unsplash = (TextView) view.findViewById(C1420R.id.unsplashID);
        this.hamButton = (ImageButton) view.findViewById(C1420R.id.hamButtonID);
        this.optionsButton = (ImageButton) view.findViewById(C1420R.id.optionsButtonID);
        MainActivity.searchViewToolbar.setVisibility(8);
        MainActivity.searchViewToolbar.animate().alpha(0.0f).start();
        MainActivity.toolbarMenu.setVisibility(8);
        MainActivity.toolbarMenu.animate().alpha(0.0f).start();
        MainActivity.fake.setVisibility(8);
        this.hamButton.setOnClickListener(new C00091());
        this.optionsButton.setOnClickListener(new C00112());
        this.sendEmail.setOnClickListener(new C00123());
        this.unsplash.setOnClickListener(new C00134());
        this.facebook.setOnClickListener(new C00145());
        this.github.setOnClickListener(new C00156());
        this.bugReport.setOnClickListener(new C00167());
        this.rate.setOnClickListener(new C00178());
        this.donate.setOnClickListener(new C00189());
        this.updates.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(AboutFragment.this.getActivity(), "You have the latest Version", 0).show();
            }
        });
        this.text0.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/TangoAgency")));
            }
        });
        this.text1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/lopspower")));
            }
        });
        this.text2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/greenrobot")));
            }
        });
        this.text3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/bumptech")));
            }
        });
        this.text4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/sephiroth74")));
            }
        });
        this.text5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/umano")));
            }
        });
        this.text6.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/ebanx")));
            }
        });
        this.text7.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/Clans")));
            }
        });
        this.text8.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/daimajia")));
            }
        });
        this.text9.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AboutFragment.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/H07000223")));
            }
        });
    }

    public void setGalleryTabs() {
        MainActivity.viewPager.setAdapter(new GalleryTabsPagerAdapter(getFragmentManager(), MainActivity.GalleryTabsMenu, 2));
        MainActivity.slidingTabLayout.setTextColor(getResources().getColor(C1420R.color.lightWhite));
        MainActivity.slidingTabLayout.setTextColorSelected(getResources().getColor(C1420R.color.White));
        MainActivity.slidingTabLayout.setDistributeEvenly();
        MainActivity.slidingTabLayout.setViewPager(MainActivity.viewPager);
        MainActivity.slidingTabLayout.setTabSelected(0);
        MainActivity.slidingTabLayout.setCustomTabColorizer(new TabColorizer() {
            public int getIndicatorColor(int position) {
                return AboutFragment.this.getResources().getColor(C1420R.color.tab_indicator);
            }
        });
    }
}
