package com.app.walshotgallery;

import Adapters.WallpaperAdapter;
import Adapters.WallpaperAdapter.OnItemClickListener;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class UserDeviceActivity extends AppCompatActivity {
    private static final int PICK_WALLPAPER_REQUEST = 9;
    private static final String PREF_NAME = "myPreference";
    private static final String TAG = "Jhalota";
    public static WallpaperAdapter imageListAdapter;
    public static ArrayList<Bitmap> mImagesList;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    private Switch enableDailyWall;
    private LayoutManager mLayoutManager;
    private AlarmManager manager;
    private PendingIntent pendingIntent;
    private SharedPreferences preferences;
    private RecyclerView recyclerView;
    private TextView timeInterval;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.UserDeviceActivity$2 */
    class C14682 implements OnClickListener {
        C14682() {
        }

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.GET_CONTENT");
            UserDeviceActivity.this.startActivityForResult(intent, 9);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.UserDeviceActivity$3 */
    class C14693 implements OnClickListener {
        C14693() {
        }

        public void onClick(View v) {
            UserDeviceActivity.this.startActivity(new Intent(UserDeviceActivity.this, RandomWallpaperActivity.class));
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.UserDeviceActivity$4 */
    class C14704 implements OnClickListener {
        C14704() {
        }

        public void onClick(View v) {
            if (UserDeviceActivity.this.enableDailyWall.isChecked()) {
                UserDeviceActivity.this.enableDailyWall.setChecked(false);
            } else {
                UserDeviceActivity.this.enableDailyWall.setChecked(true);
            }
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.UserDeviceActivity$1 */
    class C18171 implements OnItemClickListener {
        C18171() {
        }

        public void OnItemClick(int position) {
            Intent intent = new Intent(UserDeviceActivity.this, WallpaperPreview.class);
            intent.putExtra("position", Integer.toString(position).trim());
            UserDeviceActivity.this.startActivity(intent);
        }

        public void OnItemLongClick(int position) {
        }
    }

    @SuppressLint({"MissingPermission"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1420R.layout.activity_user_device);
        mImagesList = new ArrayList();
        this.toolbar = (Toolbar) findViewById(C1420R.id.mainToolbarID);
        this.toolbar.setTitle("Device");
        setSupportActionBar(this.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        this.recyclerView = (RecyclerView) findViewById(C1420R.id.recyclerViewID);
        this.cardView1 = (CardView) findViewById(C1420R.id.cardOneID);
        this.cardView2 = (CardView) findViewById(C1420R.id.cardTwoID);
        this.cardView3 = (CardView) findViewById(C1420R.id.cardThreeID);
        this.cardView4 = (CardView) findViewById(C1420R.id.cardFourID);
        this.enableDailyWall = (Switch) findViewById(C1420R.id.switchID);
        this.timeInterval = (TextView) findViewById(C1420R.id.time_intro);
        this.manager = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
        final Intent alarmIntent = new Intent(this, RandomWallpaper.class);
        this.preferences = getSharedPreferences(PREF_NAME, 0);
        final Editor editor = this.preferences.edit();
        if (VERSION.SDK_INT >= 24) {
            WallpaperManager manager = (WallpaperManager) getSystemService("wallpaper");
            manager.getClass();
            Drawable peekDrawable = manager.peekDrawable();
            if (peekDrawable != null) {
                mImagesList.add(((BitmapDrawable) peekDrawable).getBitmap());
            }
            ParcelFileDescriptor wallpaperFile = null;
            if (manager.getWallpaperId(2) != -1) {
                try {
                    wallpaperFile = manager.getWallpaperFile(2);
                } catch (Throwable e) {
                    Log.e("LockScreen", "Failed to Access Image Uri", e);
                }
            } else {
                wallpaperFile = manager.getWallpaperFile(1);
            }
            wallpaperFile.getClass();
            mImagesList.add(BitmapFactory.decodeFileDescriptor(wallpaperFile.getFileDescriptor()));
            try {
                wallpaperFile.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } else {
            WallpaperManager manager2 = (WallpaperManager) getSystemService("wallpaper");
            manager2.getClass();
            Drawable peekDrawable2 = manager2.peekDrawable();
            if (peekDrawable2 != null) {
                mImagesList.add(((BitmapDrawable) peekDrawable2).getBitmap());
            } else {
                mImagesList.add(((BitmapDrawable) manager2.getBuiltInDrawable()).getBitmap());
            }
        }
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, 0);
        if (prefs.contains("timeInterval")) {
            int interval = prefs.getInt("timeInterval", 24);
            TextView textView = this.timeInterval;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Wallpaper Refresh After Every ");
            stringBuilder.append(String.valueOf(interval));
            stringBuilder.append("hours");
            textView.setText(stringBuilder.toString());
        }
        imageListAdapter = new WallpaperAdapter(getApplicationContext(), mImagesList);
        this.mLayoutManager = new GridLayoutManager(this, 2);
        this.recyclerView.setLayoutManager(this.mLayoutManager);
        this.recyclerView.setAdapter(imageListAdapter);
        imageListAdapter.setOnItemClickListener(new C18171());
        this.cardView1.setOnClickListener(new C14682());
        this.cardView2.setOnClickListener(new C14693());
        this.cardView3.setOnClickListener(new C14704());
        this.cardView4.setOnClickListener(new OnClickListener() {

            /* renamed from: com.walshotbeta.walshotvbeta.UserDeviceActivity$5$1 */
            class C14711 implements OnTimeSetListener {
                C14711() {
                }

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    TextView access$100 = UserDeviceActivity.this.timeInterval;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("After Every ");
                    stringBuilder.append(hourOfDay);
                    stringBuilder.append("hours");
                    access$100.setText(stringBuilder.toString());
                    editor.putInt("timeInterval", hourOfDay);
                    editor.commit();
                }
            }

            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new TimePickerDialog(UserDeviceActivity.this, new C14711(), c.get(11), c.get(12), true).show();
            }
        });
        this.enableDailyWall.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boolean alarmUp = PendingIntent.getBroadcast(UserDeviceActivity.this.getApplicationContext(), 0, alarmIntent, 536870912) != null;
                    UserDeviceActivity.this.pendingIntent = PendingIntent.getBroadcast(UserDeviceActivity.this.getApplicationContext(), 0, alarmIntent, 0);
                    if (alarmUp) {
                        SharedPreferences getPrefs = UserDeviceActivity.this.getSharedPreferences(UserDeviceActivity.PREF_NAME, 0);
                        if (getPrefs.contains("timeInterval")) {
                            int timeInterval = getPrefs.getInt("timeInterval", 24);
                            UserDeviceActivity.this.manager.cancel(UserDeviceActivity.this.pendingIntent);
                            Log.d(UserDeviceActivity.TAG, "Refresh Removed Alarm");
                            UserDeviceActivity.this.startAlarm(timeInterval);
                        } else {
                            UserDeviceActivity.this.manager.cancel(UserDeviceActivity.this.pendingIntent);
                            Log.d(UserDeviceActivity.TAG, "Refresh Removed Alarm");
                            UserDeviceActivity.this.startAlarm(24);
                        }
                    } else {
                        Log.d(UserDeviceActivity.TAG, "Alarm is already active");
                    }
                    return;
                }
                UserDeviceActivity.this.manager.cancel(UserDeviceActivity.this.pendingIntent);
                Log.d(UserDeviceActivity.TAG, "Previous Alarm Removed");
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9) {
            if (data == null) {
                Log.d("Upload", "Failed to load picture!");
                return;
            }
            String imageUri = "";
            imageUri = String.valueOf(data.getData());
            Intent intent = new Intent(this, SetWallpaperActivity.class);
            intent.putExtra("imageURL", imageUri);
            startActivity(intent);
        }
    }

    public void startAlarm(int interval) {
        this.manager.setRepeating(0, System.currentTimeMillis(), (long) (((interval * 60) * 60) * 100), this.pendingIntent);
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("New Alarm Set For ");
        stringBuilder.append(interval);
        stringBuilder.append("hrs");
        Log.d(str, stringBuilder.toString());
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
