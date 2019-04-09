package Fragments;

import Adapters.GalleryVideoFoldersAdapter;
import Adapters.GalleryVideoFoldersAdapter.OnItemClickListener;
import Modals.VideosFolder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.MediaStore.Video.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.adobe.android.common.io.FileUtils;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.GalleryVideoActivity;
import com.walshotbeta.walshotvbeta.MainActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class GalleryVideosFragment extends Fragment {
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String ORDER_BY_DATE = "date_added";
    public static final String ORDER_BY_NAME = "_display_name";
    public static final String ORDER_BY_SIZE = "_size";
    private static final String PREF_NAME = "myPreference";
    private static final int REQUEST_PERMISSIONS = 100;
    private static final String TAG = GalleryImagesFragment.class.getSimpleName();
    public static RecyclerView foldersRecyclerView;
    public static GalleryVideoFoldersAdapter galleryFolderAdapter;
    public static Boolean isSelected = Boolean.valueOf(false);
    public static ArrayList<Integer> selectedVideosFolders = new ArrayList();
    public static ArrayList<VideosFolder> videosFolders = new ArrayList();
    boolean boolean_folder;
    Context mContext;
    LayoutManager mLayoutManager;
    private Parcelable mListState;
    private Dialog profileDialog;

    /* renamed from: Fragments.GalleryVideosFragment$5 */
    class C00475 implements OnClickListener {
        C00475() {
        }

        public void onClick(View v) {
            GalleryVideosFragment.this.profileDialog.dismiss();
        }
    }

    /* renamed from: Fragments.GalleryVideosFragment$6 */
    class C00486 implements OnScanCompletedListener {
        C00486() {
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
            GalleryVideosFragment.this.mContext.getContentResolver().delete(uri, null, null);
        }
    }

    public class DeleteFolderTask extends AsyncTask<Integer, Void, Void> {

        /* renamed from: Fragments.GalleryVideosFragment$DeleteFolderTask$1 */
        class C00491 implements Runnable {
            C00491() {
            }

            public void run() {
                Toast.makeText(GalleryVideosFragment.this.mContext, "Folder Deleted Successfully", 0).show();
            }
        }

        protected Void doInBackground(Integer... integers) {
            int folderSize = ((VideosFolder) GalleryVideosFragment.videosFolders.get(integers[0].intValue())).getAllVideoPaths().size();
            int position = integers[0].intValue();
            for (int i = 0; i < folderSize; i++) {
                GalleryVideosFragment.this.scanDeleteFile((String) ((VideosFolder) GalleryVideosFragment.videosFolders.get(position)).getAllVideoPaths().get(i));
            }
            GalleryVideosFragment.videosFolders.remove(position);
            GalleryVideosFragment.galleryFolderAdapter.notifyItemRemoved(integers[0].intValue());
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Handler(Looper.getMainLooper()).post(new C00491());
        }
    }

    public class HideFolderTask extends AsyncTask<Integer, Void, Void> {

        /* renamed from: Fragments.GalleryVideosFragment$HideFolderTask$1 */
        class C00501 implements Runnable {
            C00501() {
            }

            public void run() {
                Toast.makeText(GalleryVideosFragment.this.mContext, "Folder Hidden Successfully", 0).show();
            }
        }

        protected Void doInBackground(Integer... integers) {
            String name = (String) ((VideosFolder) GalleryVideosFragment.videosFolders.get(integers[0].intValue())).getAllVideoPaths().get(0);
            GalleryVideosFragment.this.hideAlbum(name.substring(0, name.lastIndexOf(47)), GalleryVideosFragment.this.mContext);
            GalleryVideosFragment.videosFolders.remove(integers[0]);
            GalleryVideosFragment.galleryFolderAdapter.notifyItemRemoved(integers[0].intValue());
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Handler(Looper.getMainLooper()).post(new C00501());
        }
    }

    public class SearchStorageTask extends AsyncTask<Void, Void, ArrayList<VideosFolder>> {

        /* renamed from: Fragments.GalleryVideosFragment$SearchStorageTask$1 */
        class C00511 implements Runnable {
            C00511() {
            }

            public void run() {
                GalleryVideosFragment.this.CreateRecyclerView();
            }
        }

        protected ArrayList<VideosFolder> doInBackground(Void... voids) {
            String orderBy;
            int column_index_data;
            String[] projection;
            int thumb;
            GalleryVideosFragment.videosFolders.clear();
            String absoluteThumbnailOfVideo = null;
            Integer absoluteSizeOfVideo = null;
            Integer absoluteWidthOfVideo = null;
            Integer absoluteHeightOfVideo = null;
            Uri uri = Media.EXTERNAL_CONTENT_URI;
            String[] projection2 = new String[]{"_data", "bucket_display_name", "_size", "width", "height", "date_added", "_id", "_data"};
            SharedPreferences orderPrefs = GalleryVideosFragment.this.mContext.getSharedPreferences(GalleryVideosFragment.PREF_NAME, 0);
            if (orderPrefs.contains("folderOrder")) {
                String orderBy2;
                int newOrder = orderPrefs.getInt("folderOrder", 0);
                if (newOrder == 0) {
                    orderBy2 = "date_added";
                } else if (newOrder == 1) {
                    orderBy2 = "_display_name";
                } else if (newOrder == 2) {
                    orderBy2 = "_size";
                } else {
                    orderBy2 = "date_added";
                    orderBy = orderBy2;
                }
                orderBy = orderBy2;
            } else {
                orderBy = "date_added";
            }
            String orderBy3 = orderBy;
            Cursor cursor = GalleryVideosFragment.this.getActivity().getContentResolver();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(orderBy3);
            stringBuilder.append(" DESC");
            cursor = cursor.query(uri, projection2, null, null, stringBuilder.toString());
            int column_index_data2 = cursor.getColumnIndexOrThrow("_data");
            int column_index_folder_name = cursor.getColumnIndexOrThrow("bucket_display_name");
            int column_index_size = cursor.getColumnIndexOrThrow("_size");
            int column_index_width = cursor.getColumnIndexOrThrow("width");
            int column_index_height = cursor.getColumnIndexOrThrow("height");
            int position = 0;
            int column_index_date_taken = cursor.getColumnIndexOrThrow("date_added");
            String absolutePathOfVideo = null;
            int column_id = cursor.getColumnIndexOrThrow("_id");
            int thumb2 = cursor.getColumnIndexOrThrow("_data");
            while (cursor.moveToNext()) {
                String str;
                int i;
                String str2;
                Integer num;
                Integer num2;
                Integer num3;
                absoluteThumbnailOfVideo = cursor.getString(column_index_data2);
                absoluteSizeOfVideo = Integer.valueOf(cursor.getInt(column_index_size));
                absoluteWidthOfVideo = Integer.valueOf(cursor.getInt(column_index_width));
                absoluteHeightOfVideo = Integer.valueOf(cursor.getInt(column_index_height));
                String absoluteDateTakenOfVideo = cursor.getString(column_index_date_taken);
                int column_index_date_taken2 = column_index_date_taken;
                column_index_date_taken = cursor.getString(thumb2);
                column_index_data = column_index_data2;
                Log.d("Column", absoluteThumbnailOfVideo);
                projection = projection2;
                Log.d("Folder", cursor.getString(column_index_folder_name));
                Log.d("Thumbnails", cursor.getString(thumb2));
                column_index_data2 = 0;
                while (column_index_data2 < GalleryVideosFragment.videosFolders.size()) {
                    thumb = thumb2;
                    if (((VideosFolder) GalleryVideosFragment.videosFolders.get(column_index_data2)).getAllFolderName().equals(cursor.getString(column_index_folder_name)) != 0) {
                        GalleryVideosFragment.this.boolean_folder = true;
                        thumb2 = column_index_data2;
                        break;
                    }
                    GalleryVideosFragment.this.boolean_folder = false;
                    column_index_data2++;
                    thumb2 = thumb;
                }
                thumb = thumb2;
                thumb2 = position;
                ArrayList<String> al_path;
                ArrayList<Integer> al_size;
                if (GalleryVideosFragment.this.boolean_folder) {
                    al_path = new ArrayList();
                    al_path.addAll(((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).getAllVideoPaths());
                    al_path.add(absoluteThumbnailOfVideo);
                    ((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).setAllVideoPaths(al_path);
                    al_size = new ArrayList();
                    al_size.addAll(((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).getAllVideoSize());
                    al_size.add(absoluteSizeOfVideo);
                    ((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).setAllVideoSize(al_size);
                    ArrayList<Integer> al_width = new ArrayList();
                    al_width.addAll(((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).getAllVideoWidth());
                    al_width.add(absoluteWidthOfVideo);
                    ((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).setAllVideoWidth(al_width);
                    al_size = new ArrayList();
                    al_size.addAll(((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).getAllVideoHeight());
                    al_size.add(absoluteHeightOfVideo);
                    ((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).setAllVideoHeight(al_size);
                    al_path = new ArrayList();
                    al_path.addAll(((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).getAllVideoDateTaken());
                    al_path.add(absoluteDateTakenOfVideo);
                    ((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).setAllVideoDateTaken(al_path);
                    ArrayList<String> al_thumb = new ArrayList();
                    al_thumb.addAll(((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).getAllVideoThumbnails());
                    al_thumb.add(column_index_date_taken);
                    ((VideosFolder) GalleryVideosFragment.videosFolders.get(thumb2)).setAllVideoThumbnails(al_thumb);
                    str = column_index_date_taken;
                    i = thumb2;
                    str2 = absoluteThumbnailOfVideo;
                    num = absoluteSizeOfVideo;
                    num2 = absoluteWidthOfVideo;
                    num3 = absoluteHeightOfVideo;
                } else {
                    al_path = new ArrayList();
                    al_path.add(absoluteThumbnailOfVideo);
                    al_size = new ArrayList();
                    al_size.add(absoluteSizeOfVideo);
                    i = thumb2;
                    thumb2 = new ArrayList();
                    thumb2.add(absoluteWidthOfVideo);
                    str2 = absoluteThumbnailOfVideo;
                    absoluteThumbnailOfVideo = new ArrayList();
                    absoluteThumbnailOfVideo.add(absoluteHeightOfVideo);
                    num = absoluteSizeOfVideo;
                    absoluteSizeOfVideo = new ArrayList();
                    absoluteSizeOfVideo.add(absoluteDateTakenOfVideo);
                    num2 = absoluteWidthOfVideo;
                    absoluteWidthOfVideo = new ArrayList();
                    absoluteWidthOfVideo.add(column_index_date_taken);
                    str = column_index_date_taken;
                    column_index_date_taken = new VideosFolder();
                    num3 = absoluteHeightOfVideo;
                    column_index_date_taken.setAllFolderName(cursor.getString(column_index_folder_name));
                    column_index_date_taken.setAllVideoPaths(al_path);
                    column_index_date_taken.setAllVideoSize(al_size);
                    column_index_date_taken.setAllVideoWidth(thumb2);
                    column_index_date_taken.setAllVideoHeight(absoluteThumbnailOfVideo);
                    column_index_date_taken.setAllVideoDateTaken(absoluteSizeOfVideo);
                    column_index_date_taken.setAllVideoThumbnails(absoluteWidthOfVideo);
                    GalleryVideosFragment.videosFolders.add(column_index_date_taken);
                }
                column_index_date_taken = column_index_date_taken2;
                column_index_data2 = column_index_data;
                projection2 = projection;
                thumb2 = thumb;
                position = i;
                absolutePathOfVideo = str2;
                absoluteSizeOfVideo = num;
                absoluteWidthOfVideo = num2;
                absoluteThumbnailOfVideo = str;
                absoluteHeightOfVideo = num3;
            }
            thumb = thumb2;
            String str3 = absoluteThumbnailOfVideo;
            Integer num4 = absoluteSizeOfVideo;
            Integer num5 = absoluteWidthOfVideo;
            Integer num6 = absoluteHeightOfVideo;
            column_index_data = column_index_data2;
            projection = projection2;
            new Handler(Looper.getMainLooper()).post(new C00511());
            return GalleryVideosFragment.videosFolders;
        }
    }

    /* renamed from: Fragments.GalleryVideosFragment$1 */
    class C07891 extends OnScrollListener {
        C07891() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 20) {
                MainActivity.searchViewToolbar.setVisibility(8);
                MainActivity.searchViewToolbar.animate().alpha(0.0f).start();
                MainActivity.toolbarMenu.setVisibility(8);
                MainActivity.toolbarMenu.animate().alpha(0.0f).start();
                MainActivity.fake.setVisibility(8);
            } else if (dy < 0) {
                MainActivity.searchViewToolbar.setVisibility(0);
                MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
                MainActivity.toolbarMenu.setVisibility(0);
                MainActivity.toolbarMenu.animate().alpha(1.0f).start();
                MainActivity.fake.setVisibility(0);
            }
        }
    }

    /* renamed from: Fragments.GalleryVideosFragment$2 */
    class C07902 implements OnItemClickListener {
        C07902() {
        }

        public void OnItemClick(int position) {
            Intent intent = new Intent(GalleryVideosFragment.this.mContext, GalleryVideoActivity.class);
            intent.putExtra("position", Integer.toString(position).trim());
            GalleryVideosFragment.this.mContext.startActivity(intent);
        }

        public void OnItemLongClick(int position) {
            GalleryVideosFragment.this.ShowPopup(position);
        }
    }

    public static GalleryVideosFragment newInstance() {
        Bundle args = new Bundle();
        GalleryVideosFragment fragment = new GalleryVideosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.gallery_videos_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getActivity();
        this.profileDialog = new Dialog(this.mContext);
        foldersRecyclerView = (RecyclerView) view.findViewById(C1420R.id.videos_recycler_view);
        MainActivity.searchViewToolbar.setVisibility(0);
        MainActivity.searchViewToolbar.animate().alpha(1.0f).start();
        MainActivity.toolbarMenu.setVisibility(0);
        MainActivity.toolbarMenu.animate().alpha(1.0f).start();
        MainActivity.fake.setVisibility(0);
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 || ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            Log.e("Else", "Else");
            new SearchStorageTask().execute(new Void[0]);
        } else if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") || !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), "android.permission.READ_EXTERNAL_STORAGE")) {
            if (VERSION.SDK_INT >= 16) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 100);
            }
        }
        foldersRecyclerView.addOnScrollListener(new C07891());
    }

    public void CreateRecyclerView() {
        this.mLayoutManager = new GridLayoutManager(this.mContext, 2);
        galleryFolderAdapter = new GalleryVideoFoldersAdapter(this.mContext, videosFolders);
        foldersRecyclerView.setLayoutManager(this.mLayoutManager);
        foldersRecyclerView.setHasFixedSize(true);
        foldersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        foldersRecyclerView.setAdapter(galleryFolderAdapter);
        galleryFolderAdapter.setOnItemClickListener(new C07902());
    }

    public void ShowPopup(final int position) {
        this.profileDialog.setContentView(C1420R.layout.folder_popup);
        ImageView closeBtn = (ImageView) this.profileDialog.findViewById(C1420R.id.closeID);
        CardView deleteBtn = (CardView) this.profileDialog.findViewById(C1420R.id.deleteCardID);
        CardView hideBtn = (CardView) this.profileDialog.findViewById(C1420R.id.hideCardID);
        ((TextView) this.profileDialog.findViewById(C1420R.id.folderNameID)).setText(((VideosFolder) videosFolders.get(position)).getAllFolderName());
        deleteBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new DeleteFolderTask().execute(new Integer[]{Integer.valueOf(position)});
                GalleryVideosFragment.this.profileDialog.dismiss();
            }
        });
        hideBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new HideFolderTask().execute(new Integer[]{Integer.valueOf(position)});
                GalleryVideosFragment.this.profileDialog.dismiss();
            }
        });
        closeBtn.setOnClickListener(new C00475());
        this.profileDialog.setCancelable(true);
        Window window = this.profileDialog.getWindow();
        window.getClass();
        window.setBackgroundDrawable(new ColorDrawable(0));
        if (!this.profileDialog.isShowing()) {
            this.profileDialog.show();
        }
    }

    private void scanDeleteFile(String path) {
        try {
            MediaScannerConnection.scanFile(this.mContext, new String[]{path}, null, new C00486());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
