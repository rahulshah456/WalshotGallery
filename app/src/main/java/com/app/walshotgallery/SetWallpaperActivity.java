package com.app.walshotgallery;

import Modals.Item;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.CropImageView.CropMode;
import com.isseiaoki.simplecropview.CropImageView.RotateDegrees;
import com.isseiaoki.simplecropview.callback.CropCallback;
import java.io.IOException;
import util.DeviceMetrics;

public class SetWallpaperActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 200;
    private final String TAG = "MainActivity";
    ListAdapter adapter;
    private ProgressDialog dialogBox;
    private Bundle extras;
    private CropImageView imageView;
    private ImageButton leftRotate;
    private final CropCallback mCropCallback = new C20366();
    private ProgressBar progressBar;
    private ImageButton rightRotate;
    private RelativeLayout setWall;
    private ImageButton squareCrop;
    private Toolbar toolbar;
    private ImageButton verticalCrop;
    private RelativeLayout wallpaperInterface;
    private String wallpaperURL;

    /* renamed from: com.walshotbeta.walshotvbeta.SetWallpaperActivity$2 */
    class C14382 implements OnClickListener {
        C14382() {
        }

        public void onClick(View v) {
            SetWallpaperActivity.this.imageView.rotateImage(RotateDegrees.ROTATE_M90D);
            SetWallpaperActivity.this.imageView.setAnimationEnabled(true);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.SetWallpaperActivity$3 */
    class C14393 implements OnClickListener {
        C14393() {
        }

        public void onClick(View v) {
            SetWallpaperActivity.this.imageView.rotateImage(RotateDegrees.ROTATE_90D);
            SetWallpaperActivity.this.imageView.setAnimationEnabled(true);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.SetWallpaperActivity$4 */
    class C14404 implements OnClickListener {
        C14404() {
        }

        public void onClick(View v) {
            SetWallpaperActivity.this.imageView.setCropMode(CropMode.SQUARE);
            SetWallpaperActivity.this.imageView.setAnimationEnabled(true);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.SetWallpaperActivity$5 */
    class C14415 implements OnClickListener {
        C14415() {
        }

        public void onClick(View v) {
            SetWallpaperActivity.this.imageView.setCropMode(CropMode.RATIO_9_16);
            SetWallpaperActivity.this.imageView.setAnimationEnabled(true);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.SetWallpaperActivity$6 */
    class C20366 implements CropCallback {
        C20366() {
        }

        public void onSuccess(final Bitmap cropped) {
            final int width = cropped.getWidth();
            final int height = cropped.getHeight();
            CharSequence[] options = new CharSequence[]{"LockScreen", "HomeScreen", "Both"};
            Builder builder = new Builder(SetWallpaperActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Set Wallpaper");
            builder.setAdapter(SetWallpaperActivity.this.adapter, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        if (width < height) {
                            SetWallpaperActivity.this.setVerticalWallpaper(cropped);
                        } else {
                            SetWallpaperActivity.this.setHorizontalWallpaper(cropped);
                        }
                    } else if (which == 1) {
                        SetWallpaperActivity.this.setLockScreenWallpaper(cropped);
                    } else if (width < height) {
                        SetWallpaperActivity.this.setSystemVerticalWallpaper(cropped);
                    } else {
                        SetWallpaperActivity.this.setSystemHorizontalWallpaper(cropped);
                    }
                }
            }).show();
        }

        public void onError(Throwable e) {
            Toast.makeText(SetWallpaperActivity.this.getApplicationContext(), e.getMessage(), 0).show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(67108864, 67108864);
        getWindow().setFlags(134217728, 134217728);
        setContentView(C1420R.layout.activity_set_wallpaper);
        Toolbar toolbar = (Toolbar) findViewById(C1420R.id.wallpaperToolbarID);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        this.wallpaperInterface = (RelativeLayout) findViewById(C1420R.id.wallpaperInterfaceID);
        setMargins(this.wallpaperInterface, 0, 0, 0, getNavigationBarHeight());
        this.extras = getIntent().getExtras();
        this.imageView = (CropImageView) findViewById(C1420R.id.cropView);
        this.leftRotate = (ImageButton) findViewById(C1420R.id.leftID);
        this.rightRotate = (ImageButton) findViewById(C1420R.id.rightID);
        this.squareCrop = (ImageButton) findViewById(C1420R.id.squareID);
        this.verticalCrop = (ImageButton) findViewById(C1420R.id.rectID);
        this.dialogBox = new ProgressDialog(this);
        this.dialogBox.setCancelable(false);
        this.dialogBox.setMessage("Setting Wallpaper....");
        this.progressBar = (ProgressBar) findViewById(C1420R.id.progressBarID);
        Item[] items = new Item[]{new Item("Home screen", Integer.valueOf(C1420R.drawable.ic_home_black_24dp)), new Item("Lock screen", Integer.valueOf(C1420R.drawable.ic_lock_black_24dp)), new Item("Both", Integer.valueOf(C1420R.drawable.ic_smartphone_black_24dp))};
        final Item[] itemArr = items;
        this.adapter = new ArrayAdapter<Item>(this, 17367057, 16908308, items) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(16908308);
                textView.setTextSize(18.0f);
                textView.setCompoundDrawablesWithIntrinsicBounds(itemArr[position].icon, 0, 0, 0);
                textView.setCompoundDrawablePadding((int) (30.0f * SetWallpaperActivity.this.getResources().getDisplayMetrics().density));
                return view;
            }
        };
        if (this.extras != null) {
            this.wallpaperURL = this.extras.getString("imageURL").trim();
        }
        Glide.with(this).load(this.wallpaperURL).apply(new RequestOptions().override(Integer.MIN_VALUE, Integer.MIN_VALUE)).into(this.imageView);
        this.imageView.setCropMode(CropMode.SQUARE);
        this.leftRotate.setOnClickListener(new C14382());
        this.rightRotate.setOnClickListener(new C14393());
        this.squareCrop.setOnClickListener(new C14404());
        this.verticalCrop.setOnClickListener(new C14415());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1420R.menu.set_wallpaper_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == C1420R.id.setWallpaperID) {
            this.imageView.crop(this.imageView.getSourceUri()).execute(this.mCropCallback);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof MarginLayoutParams) {
            ((MarginLayoutParams) view.getLayoutParams()).setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public int getNavigationBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public void setHorizontalWallpaper(final Bitmap bitmap) {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Setting Wallpaper...", true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                WallpaperManager manager;
                if (VERSION.SDK_INT >= 24) {
                    manager = (WallpaperManager) SetWallpaperActivity.this.getSystemService("wallpaper");
                    manager.getClass();
                    manager.setWallpaperOffsets(SetWallpaperActivity.this.imageView.getApplicationWindowToken(), 0.5f, 0.0f);
                    manager.suggestDesiredDimensions(DeviceMetrics.getDisplayWidth(SetWallpaperActivity.this) * 2, DeviceMetrics.getDisplayHeight(SetWallpaperActivity.this));
                    try {
                        manager.setBitmap(bitmap, null, true, 1);
                        Toast.makeText(SetWallpaperActivity.this, "Wallpaper Set Successfully", 0).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    manager = (WallpaperManager) SetWallpaperActivity.this.getSystemService("wallpaper");
                    manager.getClass();
                    manager.setWallpaperOffsets(SetWallpaperActivity.this.imageView.getApplicationWindowToken(), 0.5f, 0.0f);
                    manager.suggestDesiredDimensions(DeviceMetrics.getDisplayWidth(SetWallpaperActivity.this) * 2, DeviceMetrics.getDisplayHeight(SetWallpaperActivity.this));
                    try {
                        manager.setBitmap(bitmap);
                        Toast.makeText(SetWallpaperActivity.this, "Wallpaper Set Successfully", 0).show();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                dialog.dismiss();
                SetWallpaperActivity.this.onBackPressed();
            }
        }, (long) SPLASH_TIME_OUT);
    }

    public void setVerticalWallpaper(final Bitmap bitmap) {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Setting Wallpaper...", true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                WallpaperManager manager;
                if (VERSION.SDK_INT >= 24) {
                    manager = (WallpaperManager) SetWallpaperActivity.this.getSystemService("wallpaper");
                    manager.getClass();
                    manager.suggestDesiredDimensions(DeviceMetrics.getDisplayWidth(SetWallpaperActivity.this), DeviceMetrics.getDisplayHeight(SetWallpaperActivity.this));
                    try {
                        manager.setBitmap(bitmap, null, true, 1);
                        Toast.makeText(SetWallpaperActivity.this, "Wallpaper Set Successfully", 0).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    manager = (WallpaperManager) SetWallpaperActivity.this.getSystemService("wallpaper");
                    manager.getClass();
                    manager.suggestDesiredDimensions(DeviceMetrics.getDisplayWidth(SetWallpaperActivity.this), DeviceMetrics.getDisplayHeight(SetWallpaperActivity.this));
                    try {
                        manager.setBitmap(bitmap);
                        Toast.makeText(SetWallpaperActivity.this, "Wallpaper Set Successfully", 0).show();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                dialog.dismiss();
                SetWallpaperActivity.this.onBackPressed();
            }
        }, (long) SPLASH_TIME_OUT);
    }

    public void setLockScreenWallpaper(final Bitmap bitmap) {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Setting Wallpaper...", true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (VERSION.SDK_INT >= 24) {
                    WallpaperManager manager = (WallpaperManager) SetWallpaperActivity.this.getSystemService("wallpaper");
                    manager.getClass();
                    manager.suggestDesiredDimensions(DeviceMetrics.getDisplayWidth(SetWallpaperActivity.this), DeviceMetrics.getDisplayHeight(SetWallpaperActivity.this));
                    try {
                        manager.setBitmap(bitmap, null, true, 2);
                        Toast.makeText(SetWallpaperActivity.this, "Wallpaper Set Successfully", 0).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SetWallpaperActivity.this.getApplicationContext(), "Your Device Doesn't support Lock Screen Wallpaper", 0).show();
                }
                dialog.dismiss();
                SetWallpaperActivity.this.onBackPressed();
            }
        }, (long) SPLASH_TIME_OUT);
    }

    public void setSystemVerticalWallpaper(final Bitmap bitmap) {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Setting Wallpaper...", true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                WallpaperManager manager = (WallpaperManager) SetWallpaperActivity.this.getSystemService("wallpaper");
                manager.getClass();
                manager.suggestDesiredDimensions(DeviceMetrics.getDisplayWidth(SetWallpaperActivity.this), DeviceMetrics.getDisplayHeight(SetWallpaperActivity.this));
                try {
                    manager.setBitmap(bitmap);
                    Toast.makeText(SetWallpaperActivity.this, "Wallpaper Set Successfully", 0).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                SetWallpaperActivity.this.onBackPressed();
            }
        }, (long) SPLASH_TIME_OUT);
    }

    public void setSystemHorizontalWallpaper(final Bitmap bitmap) {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Setting Wallpaper...", true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                WallpaperManager manager = (WallpaperManager) SetWallpaperActivity.this.getSystemService("wallpaper");
                manager.getClass();
                manager.setWallpaperOffsets(SetWallpaperActivity.this.imageView.getApplicationWindowToken(), 0.5f, 0.0f);
                manager.suggestDesiredDimensions(DeviceMetrics.getDisplayWidth(SetWallpaperActivity.this) * 2, DeviceMetrics.getDisplayHeight(SetWallpaperActivity.this));
                try {
                    manager.setBitmap(bitmap);
                    Toast.makeText(SetWallpaperActivity.this, "Wallpaper Set Successfully", 0).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                SetWallpaperActivity.this.onBackPressed();
            }
        }, (long) SPLASH_TIME_OUT);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
