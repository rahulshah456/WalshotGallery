package com.app.walshotgallery;

import Adapters.HiddenFoldersAdapter;
import Adapters.HiddenFoldersAdapter.OnItemClickListener;
import Modals.FolderUtils;
import Modals.HiddenFolders;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore.Files;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;

public class HiddenFoldersActivity extends AppCompatActivity {
    private static final String FILE_TYPE_NO_MEDIA = ".nomedia";
    private static final String PREF_NAME = "myPreference";
    private static final int REQUEST_PERMISSIONS = 100;
    private ArrayList<HiddenFolders> folders;
    private RecyclerView foldersRecyclerView;
    private HiddenFoldersAdapter hiddenFoldersAdapter;
    private LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeLayout;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.HiddenFoldersActivity$3 */
    class C13863 implements OnScanCompletedListener {
        C13863() {
        }

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
            HiddenFoldersActivity.this.getApplicationContext().getContentResolver().delete(uri, null, null);
        }
    }

    public class HiddenFoldersTask extends AsyncTask<Void, Void, ArrayList<HiddenFolders>> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        /* renamed from: com.walshotbeta.walshotvbeta.HiddenFoldersActivity$HiddenFoldersTask$1 */
        class C13871 implements Runnable {
            C13871() {
            }

            public void run() {
                HiddenFoldersActivity.this.CreateRecyclerView();
            }
        }

        static {
            Class cls = HiddenFoldersActivity.class;
        }

        protected ArrayList<HiddenFolders> doInBackground(Void... voids) {
            HiddenFoldersActivity.this.folders.clear();
            String where = new StringBuilder();
            where.append("media_type=0");
            where.append(" AND ");
            where.append("title");
            where.append(" LIKE ?");
            Cursor cursor = HiddenFoldersActivity.this.getApplicationContext().getContentResolver().query(Files.getContentUri("external"), new String[]{"_data"}, where.toString(), new String[]{"%.nomedia%"}, null);
            while (cursor.moveToNext()) {
                String hiddenFilePath = cursor.getString(cursor.getColumnIndex("_data"));
                if (hiddenFilePath != null && HiddenFoldersActivity.this.isDirHaveImages(FolderUtils.getFileParent(hiddenFilePath))) {
                    HiddenFoldersActivity.this.folders.add(new HiddenFolders(hiddenFilePath, FolderUtils.getFileParent(hiddenFilePath), HiddenFoldersActivity.this.isDirHaveImages(FolderUtils.getFileParent(hiddenFilePath))));
                }
            }
            cursor.close();
            return HiddenFoldersActivity.this.folders;
        }

        protected void onPostExecute(ArrayList<HiddenFolders> hiddenFolders) {
            super.onPostExecute(hiddenFolders);
            new Handler(Looper.getMainLooper()).post(new C13871());
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.HiddenFoldersActivity$1 */
    class C17641 implements OnRefreshListener {

        /* renamed from: com.walshotbeta.walshotvbeta.HiddenFoldersActivity$1$1 */
        class C13831 implements Runnable {
            C13831() {
            }

            public void run() {
                HiddenFoldersActivity.this.swipeLayout.setRefreshing(false);
            }
        }

        C17641() {
        }

        public void onRefresh() {
            if (ContextCompat.checkSelfPermission(HiddenFoldersActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(HiddenFoldersActivity.this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                new HiddenFoldersTask().execute(new Void[0]);
            } else if (!ActivityCompat.shouldShowRequestPermissionRationale(HiddenFoldersActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") || !ActivityCompat.shouldShowRequestPermissionRationale(HiddenFoldersActivity.this, "android.permission.READ_EXTERNAL_STORAGE")) {
                if (VERSION.SDK_INT >= 16) {
                    ActivityCompat.requestPermissions(HiddenFoldersActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 100);
                }
            }
            new Handler().postDelayed(new C13831(), 4000);
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.HiddenFoldersActivity$2 */
    class C17652 implements OnItemClickListener {

        /* renamed from: com.walshotbeta.walshotvbeta.HiddenFoldersActivity$2$2 */
        class C13852 implements OnClickListener {
            C13852() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }

        C17652() {
        }

        public void OnItemClick(final int position) {
            Builder alertDialog2 = new Builder(HiddenFoldersActivity.this);
            alertDialog2.setTitle("Confirm Unhide...");
            alertDialog2.setMessage("Are you sure you want to unhide this Folder?");
            alertDialog2.setPositiveButton("YES", new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    HiddenFoldersActivity.this.scanDeleteFile(((HiddenFolders) HiddenFoldersActivity.this.folders.get(position)).getNoMediaPath());
                    new File(((HiddenFolders) HiddenFoldersActivity.this.folders.get(position)).getNoMediaPath()).delete();
                    HiddenFoldersActivity.this.folders.remove(position);
                    HiddenFoldersActivity.this.hiddenFoldersAdapter.notifyItemRemoved(position);
                }
            });
            alertDialog2.setNegativeButton("NO", new C13852());
            alertDialog2.show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1420R.layout.activity_hidden_folders);
        this.folders = new ArrayList();
        this.toolbar = (Toolbar) findViewById(C1420R.id.mainToolbarID);
        this.toolbar.setTitle("Hidden Folders");
        setSupportActionBar(this.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        this.foldersRecyclerView = (RecyclerView) findViewById(C1420R.id.folders_recycler_view);
        this.swipeLayout = (SwipeRefreshLayout) findViewById(C1420R.id.swipeRefreshID);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            new HiddenFoldersTask().execute(new Void[0]);
        } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE") || !ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            if (VERSION.SDK_INT >= 16) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 100);
            }
        }
        this.swipeLayout.setOnRefreshListener(new C17641());
        this.swipeLayout.setColorSchemeColors(new int[]{getResources().getColor(17170459), getResources().getColor(17170452), getResources().getColor(17170456), getResources().getColor(17170454)});
    }

    public void CreateRecyclerView() {
        this.mLayoutManager = new GridLayoutManager(this, 1);
        this.hiddenFoldersAdapter = new HiddenFoldersAdapter(this, this.folders);
        this.foldersRecyclerView.setLayoutManager(this.mLayoutManager);
        this.foldersRecyclerView.setHasFixedSize(true);
        this.foldersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.foldersRecyclerView.setAdapter(this.hiddenFoldersAdapter);
        this.hiddenFoldersAdapter.setOnItemClickListener(new C17652());
    }

    public boolean isDirHaveImages(String hiddenDirectoryPath) {
        File[] listFile = new File(hiddenDirectoryPath).listFiles();
        if (listFile == null || listFile.length <= 0) {
            return false;
        }
        int i = 0;
        while (i < listFile.length) {
            if (!(listFile[i].getName().endsWith(".png") || listFile[i].getName().endsWith(".jpg") || listFile[i].getName().endsWith(".jpeg") || listFile[i].getName().endsWith(".mkv") || listFile[i].getName().endsWith(".avi") || listFile[i].getName().endsWith(".3gp") || listFile[i].getName().endsWith(".mp4") || listFile[i].getName().endsWith(".mpeg4"))) {
                if (!listFile[i].getName().endsWith(".gif")) {
                    i++;
                }
            }
            return true;
        }
        return false;
    }

    private void scanDeleteFile(String path) {
        try {
            MediaScannerConnection.scanFile(this, new String[]{path}, null, new C13863());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
