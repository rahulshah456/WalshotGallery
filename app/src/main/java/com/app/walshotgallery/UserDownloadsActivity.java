package com.app.walshotgallery;

import Adapters.DownloadsAdapter;
import Adapters.DownloadsAdapter.OnItemClickListener;
import Modals.DeviceImages;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import java.io.File;
import java.util.ArrayList;

public class UserDownloadsActivity extends AppCompatActivity {
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String EVENTBUSKEY_REFRESH_PAGER_ADAPTER = "Refresh Adapter";
    private static final String TAG = UserDownloadsActivity.class.getSimpleName();
    public static DownloadsAdapter imageListAdapter;
    public static ArrayList<DeviceImages> mImagesList;
    private LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.UserDownloadsActivity$1 */
    class C18181 implements OnItemClickListener {
        C18181() {
        }

        public void OnItemClick(int position) {
            Intent intent = new Intent(UserDownloadsActivity.this, DownloadsPagerActivity.class);
            intent.putExtra("position", Integer.toString(position).trim());
            UserDownloadsActivity.this.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(UserDownloadsActivity.this, UserDownloadsActivity.this.recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(C1420R.id.image_thumbnail), "item_transition").toBundle());
        }

        public void OnItemLongClick(int position) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1420R.layout.activity_user_downloads);
        mImagesList = new ArrayList();
        this.toolbar = (Toolbar) findViewById(C1420R.id.mainToolbarID);
        this.toolbar.setTitle("My Downloads");
        setSupportActionBar(this.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        this.recyclerView = (RecyclerView) findViewById(C1420R.id.recyclerViewID);
        imageListAdapter = new DownloadsAdapter(this, getData());
        this.mLayoutManager = new GridLayoutManager(this, 2);
        this.recyclerView.setLayoutManager(this.mLayoutManager);
        this.recyclerView.setAdapter(imageListAdapter);
        imageListAdapter.setOnItemClickListener(new C18181());
    }

    private ArrayList<DeviceImages> getData() {
        mImagesList = new ArrayList();
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
                mImagesList.add(images);
            }
        }
        return mImagesList;
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
