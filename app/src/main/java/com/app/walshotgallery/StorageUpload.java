package com.app.walshotgallery;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class StorageUpload extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 11;
    private File actualImage;
    private ArrayAdapter arrayAdapter;
    private Button cancel;
    private File compressedImage;
    private TextView countdown;
    private Uri custom;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String deviceName;
    private FirebaseAuth mAuth;
    private Collection<String> mCollection = new ArrayList();
    private NumberProgressBar progressBar;
    private StorageReference storageReference;
    private CountDownTimer timer;
    private Toolbar toolbar;
    private Button upload;
    private ImageView uploadImage;
    private StorageTask uploadTask;
    private TextView uploading;
    private String userAccountName = "Droid Developer";

    /* renamed from: com.walshotbeta.walshotvbeta.StorageUpload$1 */
    class C14631 implements OnClickListener {
        C14631() {
        }

        public void onClick(View v) {
            StorageUpload.this.pickImage();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.StorageUpload$2 */
    class C14642 implements OnClickListener {
        C14642() {
        }

        public void onClick(View v) {
            StorageUpload.this.timer.start();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.StorageUpload$3 */
    class C14653 implements OnClickListener {
        C14653() {
        }

        public void onClick(View v) {
            StorageUpload.this.timer.cancel();
            StorageUpload.this.onBackPressed();
            StorageUpload.this.finish();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.StorageUpload$5 */
    class C18125 implements OnProgressListener<TaskSnapshot> {
        C18125() {
        }

        public void onProgress(TaskSnapshot taskSnapshot) {
            StorageUpload.this.progressBar.setProgress((int) ((double) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount())));
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.StorageUpload$6 */
    class C18136 implements OnFailureListener {
        C18136() {
        }

        public void onFailure(@NonNull Exception e) {
            Toast.makeText(StorageUpload.this, e.getMessage(), 1).show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.StorageUpload$8 */
    class C18158 implements Consumer<File> {
        C18158() {
        }

        public void accept(File file) {
            StorageUpload.this.compressedImage = file;
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.StorageUpload$9 */
    class C18169 implements Consumer<Throwable> {
        C18169() {
        }

        public void accept(Throwable throwable) {
            throwable.printStackTrace();
            Toast.makeText(StorageUpload.this, throwable.getMessage(), 1).show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1420R.layout.activity_storage_upload);
        this.toolbar = (Toolbar) findViewById(C1420R.id.toolbarUploadID);
        this.toolbar.setTitle("Upload Your Daily Shot");
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.uploadImage = (ImageView) findViewById(C1420R.id.imageUploadID);
        this.upload = (Button) findViewById(C1420R.id.uploadButtonID);
        this.storageReference = FirebaseStorage.getInstance().getReference("DailyShots");
        this.databaseReference = FirebaseDatabase.getInstance().getReference("DailyShots");
        this.database = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.progressBar = (NumberProgressBar) findViewById(C1420R.id.progressBarID);
        this.progressBar.setMax(100);
        this.deviceName = Build.MODEL;
        this.uploading = (TextView) findViewById(C1420R.id.uploadingTextID);
        this.countdown = (TextView) findViewById(C1420R.id.countdownID);
        this.cancel = (Button) findViewById(C1420R.id.cancelButtonID);
        this.uploadImage.setOnClickListener(new C14631());
        this.uploadImage.performClick();
        this.upload.setOnClickListener(new C14642());
        this.cancel.setOnClickListener(new C14653());
        this.timer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                StorageUpload.this.uploading.setVisibility(0);
                StorageUpload.this.countdown.setVisibility(0);
                TextView access$300 = StorageUpload.this.countdown;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("0");
                stringBuilder.append(millisUntilFinished / 1000);
                stringBuilder.append("s");
                access$300.setText(stringBuilder.toString());
            }

            public void onFinish() {
                StorageUpload.this.uploading.setVisibility(4);
                StorageUpload.this.countdown.setVisibility(4);
                StorageUpload.this.uploadData();
            }
        };
    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(intent, 11);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (data == null) {
                Log.d("Upload", "Failed to load picture!");
                onBackPressed();
                finish();
                return;
            }
            try {
                this.uploadImage.setOnClickListener(null);
                this.actualImage = FileUtil.from(this, data.getData());
                customCompressImage();
                Glide.with(this).load(this.actualImage.getAbsolutePath()).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().centerCrop()).into(this.uploadImage);
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), 1).show();
                e.printStackTrace();
            }
        }
    }

    private void uploadData() {
        if (this.custom != null) {
            StorageReference fileReference = this.storageReference;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(System.currentTimeMillis());
            stringBuilder.append(".JPEG");
            fileReference = fileReference.child(stringBuilder.toString());
            final String uploadId = this.databaseReference.push().getKey();
            fileReference.putFile(this.custom).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {

                /* renamed from: com.walshotbeta.walshotvbeta.StorageUpload$7$1 */
                class C14671 implements Runnable {
                    C14671() {
                    }

                    public void run() {
                        StorageUpload.this.progressBar.setProgress(0);
                    }
                }

                public void onSuccess(TaskSnapshot taskSnapshot) {
                    new Handler().postDelayed(new C14671(), 500);
                    StorageUpload.this.databaseReference.child(uploadId).child("compressedURL").setValue(taskSnapshot.getDownloadUrl().toString());
                    StorageUpload.this.databaseReference.child(uploadId).child("deviceName").setValue(StorageUpload.this.deviceName);
                    StorageUpload.this.databaseReference.child(uploadId).child("userName").setValue(StorageUpload.this.mAuth.getCurrentUser().getEmail());
                    DatabaseReference child = StorageUpload.this.databaseReference.child(uploadId).child("userPic");
                    Uri photoUrl = StorageUpload.this.mAuth.getCurrentUser().getPhotoUrl();
                    photoUrl.getClass();
                    child.setValue(photoUrl.toString());
                }
            }).addOnFailureListener(new C18136()).addOnProgressListener(new C18125());
            onBackPressed();
            finish();
        } else if (this.actualImage == null) {
            Toast.makeText(this, "Please Select Any Image", 0).show();
        } else if (this.custom == null) {
            Toast.makeText(this, "Please Try Again", 0).show();
        } else {
            Toast.makeText(this, "No File Selected", 0).show();
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    public void customCompressImage() {
        if (this.actualImage != null) {
            new Compressor(this).compressToFileAsFlowable(this.actualImage).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C18158(), new C18169());
            try {
                Compressor compressFormat = new Compressor(this).setMaxWidth(1024).setMaxHeight(768).setQuality(80).setCompressFormat(CompressFormat.WEBP);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(Environment.getExternalStorageDirectory());
                stringBuilder.append("/Android/data/com.walshotbeta.walshotvbeta/files/compressed");
                this.compressedImage = compressFormat.setDestinationDirectoryPath(stringBuilder.toString()).compressToFile(this.actualImage);
                this.custom = getImageContentUri(this, this.compressedImage);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), 1).show();
            }
        }
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        Cursor cursor = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=? ", new String[]{imageFile.getAbsolutePath()}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            cursor.close();
            Uri uri = Media.EXTERNAL_CONTENT_URI;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(id);
            return Uri.withAppendedPath(uri, stringBuilder.toString());
        } else if (!imageFile.exists()) {
            return null;
        } else {
            ContentValues values = new ContentValues();
            values.put("_data", filePath);
            return context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
