package com.app.walshotgallery;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.adobe.creativesdk.foundation.internal.analytics.AdobeAnalyticsETSEvent;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREF_NAME = "myPreference";
    private Switch aSwitch;
    private RelativeLayout changeTheme;
    private TextView editInfo;
    private RelativeLayout editing;
    private CharSequence[] editingQuality = new String[]{"Regular", "Original"};
    private RelativeLayout grid;
    private CharSequence[] gridColumns = new String[]{"1", "2"};
    private TextView gridInfo;
    private RelativeLayout hiddenFolders;
    private RelativeLayout launch;
    private TextView launchInfo;
    private CharSequence[] launchItems = new String[]{"Wallpapers", "Collections", "Daily Shots", "Gallery"};
    private RelativeLayout notifications;
    private TextView notificationsInfo;
    private SharedPreferences preferences;
    private CharSequence[] thumbQuality = new String[]{"Small", "Regular"};
    private RelativeLayout thumbnail;
    private TextView thumbnailInfo;
    private Toolbar toolbar;
    private CharSequence[] wallQuality = new String[]{"Regular", "Full", "Original (Experimental)"};
    private RelativeLayout wallpaper;
    private TextView wallpaperInfo;

    /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$5 */
    class C14545 implements OnClickListener {
        C14545() {
        }

        public void onClick(View v) {
            if (SettingsActivity.this.aSwitch.isChecked()) {
                SettingsActivity.this.aSwitch.setChecked(false);
            } else {
                SettingsActivity.this.aSwitch.setChecked(true);
            }
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$6 */
    class C14566 implements OnCheckedChangeListener {

        /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$6$1 */
        class C14551 implements Runnable {
            C14551() {
            }

            public void run() {
                SettingsActivity.this.aSwitch.setChecked(false);
                Toast.makeText(SettingsActivity.this.getApplicationContext(), "Only premium members can disable notifications", 0).show();
            }
        }

        C14566() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            new Handler().postDelayed(new C14551(), 1000);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$8 */
    class C14598 implements OnClickListener {
        C14598() {
        }

        public void onClick(View v) {
            SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, HiddenFoldersActivity.class));
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$9 */
    class C14619 implements OnClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$9$1 */
        class C14601 implements Runnable {
            C14601() {
            }

            public void run() {
                Toast.makeText(SettingsActivity.this, "Upgrade to premium for dark mode", 0).show();
            }
        }

        C14619() {
        }

        public void onClick(View v) {
            new Handler().postDelayed(new C14601(), 1000);
        }
    }

    @SuppressLint({"SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1420R.layout.activity_settings);
        this.toolbar = (Toolbar) findViewById(C1420R.id.mainToolbarID);
        this.toolbar.setTitle("Settings");
        setSupportActionBar(this.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        this.launch = (RelativeLayout) findViewById(C1420R.id.layout1);
        this.launchInfo = (TextView) findViewById(C1420R.id.launchInfoID);
        this.wallpaper = (RelativeLayout) findViewById(C1420R.id.layout2);
        this.wallpaperInfo = (TextView) findViewById(C1420R.id.wallpaperInfoID);
        this.grid = (RelativeLayout) findViewById(C1420R.id.layout3);
        this.gridInfo = (TextView) findViewById(C1420R.id.gridInfoID);
        this.thumbnail = (RelativeLayout) findViewById(C1420R.id.layout4);
        this.thumbnailInfo = (TextView) findViewById(C1420R.id.thumbnailInfoID);
        this.notifications = (RelativeLayout) findViewById(C1420R.id.layout5);
        this.notificationsInfo = (TextView) findViewById(C1420R.id.notificationInfoID);
        this.editing = (RelativeLayout) findViewById(C1420R.id.layout6);
        this.editInfo = (TextView) findViewById(C1420R.id.editingInfoID);
        this.hiddenFolders = (RelativeLayout) findViewById(C1420R.id.layout7);
        this.changeTheme = (RelativeLayout) findViewById(C1420R.id.layout8);
        this.aSwitch = (Switch) findViewById(C1420R.id.switchID);
        this.preferences = getSharedPreferences(PREF_NAME, 0);
        final Editor editor = this.preferences.edit();
        SharedPreferences launchPrefs = getSharedPreferences(PREF_NAME, 0);
        if (launchPrefs.contains("launch")) {
            switch (launchPrefs.getInt("launch", 0)) {
                case 0:
                    this.launchInfo.setText("Launch with (Wallpapers )");
                    break;
                case 1:
                    this.launchInfo.setText("Launch with (Collections )");
                    break;
                case 2:
                    this.launchInfo.setText("Launch with (Daily Shots )");
                    break;
                case 3:
                    this.launchInfo.setText("Launch with (Gallery )");
                    break;
                default:
                    break;
            }
        }
        SharedPreferences wallPrefs = getSharedPreferences(PREF_NAME, 0);
        if (wallPrefs.contains("wallpaper")) {
            String wallQuality = wallPrefs.getString("wallpaper", "Regular");
            int i = -1;
            int hashCode = wallQuality.hashCode();
            if (hashCode != -1543850116) {
                if (hashCode != 2201263) {
                    if (hashCode == 1443687921) {
                        if (wallQuality.equals("Original")) {
                            i = 2;
                        }
                    }
                } else if (wallQuality.equals("Full")) {
                    i = true;
                }
            } else if (wallQuality.equals("Regular")) {
                i = null;
            }
            TextView textView;
            StringBuilder stringBuilder;
            switch (i) {
                case 0:
                    textView = this.wallpaperInfo;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Wallpaper Quality (");
                    stringBuilder.append(wallQuality);
                    stringBuilder.append(" )");
                    textView.setText(stringBuilder.toString());
                    break;
                case 1:
                    textView = this.wallpaperInfo;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Wallpaper Quality (");
                    stringBuilder.append(wallQuality);
                    stringBuilder.append(" )");
                    textView.setText(stringBuilder.toString());
                    break;
                case 2:
                    textView = this.wallpaperInfo;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Wallpaper Quality (");
                    stringBuilder.append(wallQuality);
                    stringBuilder.append(" )");
                    textView.setText(stringBuilder.toString());
                    break;
                default:
                    break;
            }
        }
        SharedPreferences gridPrefs = getSharedPreferences(PREF_NAME, 0);
        if (gridPrefs.contains(AdobeAnalyticsETSEvent.ADOBE_ETS_VALUE_VIEW_TYPE_GRID)) {
            if (gridPrefs.getInt(AdobeAnalyticsETSEvent.ADOBE_ETS_VALUE_VIEW_TYPE_GRID, 2) == 1) {
                this.gridInfo.setText("No. of Columns in Layout (1)");
            } else {
                this.gridInfo.setText("No. of Columns in Layout (2)");
            }
        }
        SharedPreferences thumbPrefs = getSharedPreferences(PREF_NAME, 0);
        if (thumbPrefs.contains("thumbnail")) {
            if (thumbPrefs.getString("thumbnail", "Small") == "Small") {
                this.thumbnailInfo.setText("Thumbnail Quality (Small)");
            } else {
                this.thumbnailInfo.setText("Thumbnail Quality (Regular)");
            }
        }
        SharedPreferences editPrefs = getSharedPreferences(PREF_NAME, 0);
        if (editPrefs.contains("editing")) {
            if (editPrefs.getString("editing", "Regular") == "Regular") {
                this.editInfo.setText("Image Edit Quality (Regular)");
            } else {
                this.editInfo.setText("Image Edit Quality (Original)");
            }
        }
        this.launch.setOnClickListener(new OnClickListener() {

            /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$1$1 */
            class C14461 implements DialogInterface.OnClickListener {
                C14461() {
                }

                @SuppressLint({"ApplySharedPref", "SetTextI18n"})
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            editor.putInt("launch", 0);
                            editor.commit();
                            SettingsActivity.this.launchInfo.setText("Launch with (Wallpapers )");
                            break;
                        case 1:
                            editor.putInt("launch", 1);
                            editor.commit();
                            SettingsActivity.this.launchInfo.setText("Launch with (Collections )");
                            break;
                        case 2:
                            editor.putInt("launch", 2);
                            editor.commit();
                            SettingsActivity.this.launchInfo.setText("Launch with (Daily Shots )");
                            break;
                        case 3:
                            editor.putInt("launch", 3);
                            editor.commit();
                            SettingsActivity.this.launchInfo.setText("Launch with (Gallery )");
                            break;
                        default:
                            break;
                    }
                    dialog.dismiss();
                }
            }

            public void onClick(View v) {
                Builder mBuilder = new Builder(SettingsActivity.this);
                mBuilder.setItems(SettingsActivity.this.launchItems, new C14461());
                mBuilder.create().show();
            }
        });
        this.wallpaper.setOnClickListener(new OnClickListener() {

            /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$2$1 */
            class C14481 implements DialogInterface.OnClickListener {
                C14481() {
                }

                @SuppressLint({"SetTextI18n"})
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            editor.putString("wallpaper", "Regular");
                            editor.commit();
                            SettingsActivity.this.wallpaperInfo.setText("Wallpaper Quality (Regular )");
                            return;
                        case 1:
                            editor.putString("wallpaper", "Full");
                            editor.commit();
                            SettingsActivity.this.wallpaperInfo.setText("Wallpaper Quality (Full )");
                            return;
                        case 2:
                            editor.putString("wallpaper", "Original");
                            editor.commit();
                            SettingsActivity.this.wallpaperInfo.setText("Wallpaper Quality (Original )");
                            return;
                        default:
                            return;
                    }
                }
            }

            public void onClick(View v) {
                Builder mBuilder = new Builder(SettingsActivity.this);
                mBuilder.setItems(SettingsActivity.this.wallQuality, new C14481());
                mBuilder.create().show();
            }
        });
        this.grid.setOnClickListener(new OnClickListener() {

            /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$3$1 */
            class C14501 implements DialogInterface.OnClickListener {
                C14501() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        editor.putInt(AdobeAnalyticsETSEvent.ADOBE_ETS_VALUE_VIEW_TYPE_GRID, 1);
                        editor.commit();
                        SettingsActivity.this.gridInfo.setText("No. of Columns in Layout (1)");
                        return;
                    }
                    editor.putInt(AdobeAnalyticsETSEvent.ADOBE_ETS_VALUE_VIEW_TYPE_GRID, 2);
                    editor.commit();
                    SettingsActivity.this.gridInfo.setText("No. of Columns in Layout (2)");
                }
            }

            public void onClick(View v) {
                Builder mBuilder = new Builder(SettingsActivity.this);
                mBuilder.setItems(SettingsActivity.this.gridColumns, new C14501());
                mBuilder.create().show();
            }
        });
        this.thumbnail.setOnClickListener(new OnClickListener() {

            /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$4$1 */
            class C14521 implements DialogInterface.OnClickListener {
                C14521() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        editor.putString("thumbnail", "Small");
                        editor.commit();
                        SettingsActivity.this.thumbnailInfo.setText("Thumbnail Quality (Small)");
                        return;
                    }
                    editor.putString("thumbnail", "Regular");
                    editor.commit();
                    SettingsActivity.this.thumbnailInfo.setText("Thumbnail Quality (Regular)");
                }
            }

            public void onClick(View v) {
                Builder mBuilder = new Builder(SettingsActivity.this);
                mBuilder.setItems(SettingsActivity.this.thumbQuality, new C14521());
                mBuilder.create().show();
            }
        });
        this.notifications.setOnClickListener(new C14545());
        this.aSwitch.setOnCheckedChangeListener(new C14566());
        this.editing.setOnClickListener(new OnClickListener() {

            /* renamed from: com.walshotbeta.walshotvbeta.SettingsActivity$7$1 */
            class C14571 implements DialogInterface.OnClickListener {
                C14571() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        editor.putString("editing", "Regular");
                        editor.commit();
                        SettingsActivity.this.editInfo.setText("Image Edit Quality (Regular)");
                        return;
                    }
                    editor.putString("editing", "Original");
                    editor.commit();
                    SettingsActivity.this.editInfo.setText("Image Edit Quality (Original)");
                }
            }

            public void onClick(View v) {
                Builder mBuilder = new Builder(SettingsActivity.this);
                mBuilder.setItems(SettingsActivity.this.editingQuality, new C14571());
                mBuilder.create().show();
            }
        });
        this.hiddenFolders.setOnClickListener(new C14598());
        this.changeTheme.setOnClickListener(new C14619());
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
