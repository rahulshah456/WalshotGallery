package Fragments;

import Adapters.GalleryFolderAdapter;
import Adapters.GalleryFolderAdapter.OnItemClickListener;
import Modals.ImagesFolder;
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
import android.provider.MediaStore.Images.Media;
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
import com.walshotbeta.walshotvbeta.GalleryActivity;
import com.walshotbeta.walshotvbeta.MainActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class GalleryImagesFragment extends Fragment {
    public static final String BUNDLE_LIST_STATE = "list_state";
    public static final String ORDER_BY_DATE = "date_added";
    public static final String ORDER_BY_NAME = "_display_name";
    public static final String ORDER_BY_SIZE = "_size";
    private static final String PREF_NAME = "myPreference";
    private static final int REQUEST_PERMISSIONS = 100;
    private static final String TAG = GalleryImagesFragment.class.getSimpleName();
    public static ArrayList<ImagesFolder> folders = new ArrayList();
    public static RecyclerView foldersRecyclerView;
    public static GalleryFolderAdapter galleryFolderAdapter;
    public static Boolean isSelected = Boolean.valueOf(false);
    public static ArrayList<Integer> selectedImagesFolders = new ArrayList();
    boolean boolean_folder;
    Context mContext;
    LayoutManager mLayoutManager;
    private Parcelable mListState;
    private Dialog profileDialog;

    /* renamed from: Fragments.GalleryImagesFragment$5 */
    class C00405 implements OnClickListener {
        C00405() {
        }

        public void onClick(View v) {
            GalleryImagesFragment.this.profileDialog.dismiss();
        }
    }

    /* renamed from: Fragments.GalleryImagesFragment$6 */
    class C00416 implements OnScanCompletedListener {
        C00416() {
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
            GalleryImagesFragment.this.mContext.getContentResolver().delete(uri, null, null);
        }
    }

    public class DeleteFolderTask extends AsyncTask<Integer, Void, Void> {

        /* renamed from: Fragments.GalleryImagesFragment$DeleteFolderTask$1 */
        class C00421 implements Runnable {
            C00421() {
            }

            public void run() {
                Toast.makeText(GalleryImagesFragment.this.mContext, "Folder Deleted Successfully", 0).show();
            }
        }

        protected Void doInBackground(Integer... integers) {
            int folderSize = ((ImagesFolder) GalleryImagesFragment.folders.get(integers[0].intValue())).getAllImagePaths().size();
            int position = integers[0].intValue();
            for (int i = 0; i < folderSize; i++) {
                GalleryImagesFragment.this.scanDeleteFile((String) ((ImagesFolder) GalleryImagesFragment.folders.get(position)).getAllImagePaths().get(i));
            }
            GalleryImagesFragment.folders.remove(position);
            GalleryImagesFragment.galleryFolderAdapter.notifyItemRemoved(integers[0].intValue());
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Handler(Looper.getMainLooper()).post(new C00421());
        }
    }

    public class HideFolderTask extends AsyncTask<Integer, Void, Void> {

        /* renamed from: Fragments.GalleryImagesFragment$HideFolderTask$1 */
        class C00431 implements Runnable {
            C00431() {
            }

            public void run() {
                Toast.makeText(GalleryImagesFragment.this.mContext, "Folder Hidden Successfully", 0).show();
            }
        }

        protected Void doInBackground(Integer... integers) {
            String name = (String) ((ImagesFolder) GalleryImagesFragment.folders.get(integers[0].intValue())).getAllImagePaths().get(0);
            GalleryImagesFragment.this.hideAlbum(name.substring(0, name.lastIndexOf(47)), GalleryImagesFragment.this.mContext);
            GalleryImagesFragment.folders.remove(integers[0]);
            GalleryImagesFragment.galleryFolderAdapter.notifyItemRemoved(integers[0].intValue());
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Handler(Looper.getMainLooper()).post(new C00431());
        }
    }

    public class SearchStorageTask extends AsyncTask<Void, Void, ArrayList<ImagesFolder>> {

        /* renamed from: Fragments.GalleryImagesFragment$SearchStorageTask$1 */
        class C00441 implements Runnable {
            C00441() {
            }

            public void run() {
                GalleryImagesFragment.this.CreateRecyclerView();
            }
        }

        protected ArrayList<ImagesFolder> doInBackground(Void... voids) {
            String orderBy;
            int column_index_data;
            String[] projection;
            GalleryImagesFragment.folders.clear();
            Integer absoluteSizeOfImage = null;
            Integer absoluteWidthOfImage = null;
            Integer absoluteHeightOfImage = null;
            Uri uri = Media.EXTERNAL_CONTENT_URI;
            String[] projection2 = new String[]{"_data", "bucket_display_name", "_size", "width", "height", "date_added"};
            SharedPreferences orderPrefs = GalleryImagesFragment.this.mContext.getSharedPreferences(GalleryImagesFragment.PREF_NAME, 0);
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
            Cursor cursor = GalleryImagesFragment.this.getActivity().getContentResolver();
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
            while (cursor.moveToNext()) {
                String str;
                Integer num;
                Integer num2;
                Integer num3;
                String str2;
                String absolutePathOfImage = cursor.getString(column_index_data2);
                absoluteSizeOfImage = Integer.valueOf(cursor.getInt(column_index_size));
                absoluteWidthOfImage = Integer.valueOf(cursor.getInt(column_index_width));
                absoluteHeightOfImage = Integer.valueOf(cursor.getInt(column_index_height));
                String absoluteDateTakenOfImage = cursor.getString(column_index_date_taken);
                int column_index_date_taken2 = column_index_date_taken;
                Log.d("Column", absolutePathOfImage);
                column_index_data = column_index_data2;
                Log.d("Folder", cursor.getString(column_index_folder_name));
                column_index_date_taken = 0;
                while (column_index_date_taken < GalleryImagesFragment.folders.size()) {
                    projection = projection2;
                    if (((ImagesFolder) GalleryImagesFragment.folders.get(column_index_date_taken)).getAllFolderName().equals(cursor.getString(column_index_folder_name))) {
                        GalleryImagesFragment.this.boolean_folder = true;
                        column_index_data2 = column_index_date_taken;
                        break;
                    }
                    GalleryImagesFragment.this.boolean_folder = false;
                    column_index_date_taken++;
                    projection2 = projection;
                }
                projection = projection2;
                column_index_data2 = position;
                ArrayList<Integer> al_size;
                if (GalleryImagesFragment.this.boolean_folder != 0) {
                    column_index_date_taken = new ArrayList();
                    column_index_date_taken.addAll(((ImagesFolder) GalleryImagesFragment.folders.get(column_index_data2)).getAllImagePaths());
                    column_index_date_taken.add(absolutePathOfImage);
                    ((ImagesFolder) GalleryImagesFragment.folders.get(column_index_data2)).setAllImagePaths(column_index_date_taken);
                    al_size = new ArrayList();
                    ArrayList<String> al_path = column_index_date_taken;
                    al_size.addAll(((ImagesFolder) GalleryImagesFragment.folders.get(column_index_data2)).getAllImageSize());
                    al_size.add(absoluteSizeOfImage);
                    ((ImagesFolder) GalleryImagesFragment.folders.get(column_index_data2)).setAllImageSize(al_size);
                    column_index_date_taken = new ArrayList();
                    column_index_date_taken.addAll(((ImagesFolder) GalleryImagesFragment.folders.get(column_index_data2)).getAllImageWidth());
                    column_index_date_taken.add(absoluteWidthOfImage);
                    ((ImagesFolder) GalleryImagesFragment.folders.get(column_index_data2)).setAllImageWidth(column_index_date_taken);
                    al_size = new ArrayList();
                    ArrayList<Integer> al_width = column_index_date_taken;
                    al_size.addAll(((ImagesFolder) GalleryImagesFragment.folders.get(column_index_data2)).getAllImageHeight());
                    al_size.add(absoluteHeightOfImage);
                    ((ImagesFolder) GalleryImagesFragment.folders.get(column_index_data2)).setAllImageHeight(al_size);
                    column_index_date_taken = new ArrayList();
                    column_index_date_taken.addAll(((ImagesFolder) GalleryImagesFragment.folders.get(column_index_data2)).getAllImageDateTaken());
                    column_index_date_taken.add(absoluteDateTakenOfImage);
                    ((ImagesFolder) GalleryImagesFragment.folders.get(column_index_data2)).setAllImageDateTaken(column_index_date_taken);
                    str = absolutePathOfImage;
                    num = absoluteSizeOfImage;
                    num2 = absoluteWidthOfImage;
                    num3 = absoluteHeightOfImage;
                    str2 = absoluteDateTakenOfImage;
                } else {
                    column_index_date_taken = new ArrayList();
                    column_index_date_taken.add(absolutePathOfImage);
                    al_size = new ArrayList();
                    al_size.add(absoluteSizeOfImage);
                    str = absolutePathOfImage;
                    absolutePathOfImage = new ArrayList();
                    absolutePathOfImage.add(absoluteWidthOfImage);
                    num = absoluteSizeOfImage;
                    absoluteSizeOfImage = new ArrayList();
                    absoluteSizeOfImage.add(absoluteHeightOfImage);
                    num2 = absoluteWidthOfImage;
                    absoluteWidthOfImage = new ArrayList();
                    absoluteWidthOfImage.add(absoluteDateTakenOfImage);
                    num3 = absoluteHeightOfImage;
                    absoluteHeightOfImage = new ImagesFolder();
                    str2 = absoluteDateTakenOfImage;
                    absoluteHeightOfImage.setAllFolderName(cursor.getString(column_index_folder_name));
                    absoluteHeightOfImage.setAllImagePaths(column_index_date_taken);
                    absoluteHeightOfImage.setAllImageSize(al_size);
                    absoluteHeightOfImage.setAllImageWidth(absolutePathOfImage);
                    absoluteHeightOfImage.setAllImageHeight(absoluteSizeOfImage);
                    absoluteHeightOfImage.setAllImageDateTaken(absoluteWidthOfImage);
                    GalleryImagesFragment.folders.add(absoluteHeightOfImage);
                }
                position = column_index_data2;
                column_index_date_taken = column_index_date_taken2;
                column_index_data2 = column_index_data;
                projection2 = projection;
                absolutePathOfImage = str;
                absoluteSizeOfImage = num;
                absoluteWidthOfImage = num2;
                absoluteHeightOfImage = num3;
                absoluteDateTakenOfImage = str2;
            }
            Integer num4 = absoluteSizeOfImage;
            Integer num5 = absoluteWidthOfImage;
            Integer num6 = absoluteHeightOfImage;
            column_index_data = column_index_data2;
            projection = projection2;
            new Handler(Looper.getMainLooper()).post(new C00441());
            return GalleryImagesFragment.folders;
        }
    }

    /* renamed from: Fragments.GalleryImagesFragment$1 */
    class C07871 extends OnScrollListener {
        C07871() {
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

    /* renamed from: Fragments.GalleryImagesFragment$2 */
    class C07882 implements OnItemClickListener {
        C07882() {
        }

        public void OnItemClick(int position) {
            Intent intent = new Intent(GalleryImagesFragment.this.mContext, GalleryActivity.class);
            intent.putExtra("position", Integer.toString(position).trim());
            GalleryImagesFragment.this.mContext.startActivity(intent);
        }

        public void OnItemLongClick(int position) {
            GalleryImagesFragment.this.ShowPopup(position);
        }
    }

    public static GalleryImagesFragment newInstance() {
        Bundle args = new Bundle();
        GalleryImagesFragment fragment = new GalleryImagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.gallery_images_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getActivity();
        foldersRecyclerView = (RecyclerView) view.findViewById(C1420R.id.folders_recycler_view);
        this.profileDialog = new Dialog(this.mContext);
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
        foldersRecyclerView.addOnScrollListener(new C07871());
    }

    public void CreateRecyclerView() {
        this.mLayoutManager = new GridLayoutManager(this.mContext, 2);
        galleryFolderAdapter = new GalleryFolderAdapter(this.mContext, folders);
        foldersRecyclerView.setLayoutManager(this.mLayoutManager);
        foldersRecyclerView.setHasFixedSize(true);
        foldersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        foldersRecyclerView.setAdapter(galleryFolderAdapter);
        galleryFolderAdapter.setOnItemClickListener(new C07882());
    }

    public void ShowPopup(final int position) {
        this.profileDialog.setContentView(C1420R.layout.folder_popup);
        ImageView closeBtn = (ImageView) this.profileDialog.findViewById(C1420R.id.closeID);
        CardView deleteBtn = (CardView) this.profileDialog.findViewById(C1420R.id.deleteCardID);
        CardView hideBtn = (CardView) this.profileDialog.findViewById(C1420R.id.hideCardID);
        ((TextView) this.profileDialog.findViewById(C1420R.id.folderNameID)).setText(((ImagesFolder) folders.get(position)).getAllFolderName());
        deleteBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new DeleteFolderTask().execute(new Integer[]{Integer.valueOf(position)});
                GalleryImagesFragment.this.profileDialog.dismiss();
            }
        });
        hideBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new HideFolderTask().execute(new Integer[]{Integer.valueOf(position)});
                GalleryImagesFragment.this.profileDialog.dismiss();
            }
        });
        closeBtn.setOnClickListener(new C00405());
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
            MediaScannerConnection.scanFile(this.mContext, new String[]{path}, null, new C00416());
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

    public void onSaveInstanceState(Bundle outState) {
        if (this.mListState != null) {
            this.mListState = this.mLayoutManager.onSaveInstanceState();
            outState.putParcelable("list_state", this.mListState);
        }
        super.onSaveInstanceState(outState);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.mListState = savedInstanceState.getParcelable("list_state");
        }
        super.onActivityCreated(savedInstanceState);
    }

    public void onResume() {
        if (this.mListState == null || foldersRecyclerView == null) {
            Log.d(TAG, "onResume: ListState empty!");
        } else if (foldersRecyclerView.getLayoutManager() != null) {
            Log.d(TAG, "onResume: Restoring list state ...");
            this.mLayoutManager.onRestoreInstanceState(this.mListState);
        }
        super.onResume();
    }
}
