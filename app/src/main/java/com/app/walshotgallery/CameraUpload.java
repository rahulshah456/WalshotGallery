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
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.adobe.creativesdk.aviary.AdobeImageIntent;
import com.adobe.creativesdk.aviary.utils.SharedPreferencesUtils;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraUpload extends AppCompatActivity {
    private static final int CAPTURE_IMAGE_REQUEST = 10;
    private static final String NOMEDIA = ".nomedia";
    private static final String TAG = "CameraUpload";
    private File actualImage = null;
    private Button cancel;
    private File compressedImage;
    private TextView countdown;
    private Uri custom;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String deviceName;
    private String imageFilePath;
    private Uri imageUri;
    private FirebaseAuth mAuth;
    private File nomediaFile;
    private NumberProgressBar progressBar;
    private StorageReference storageReference;
    private CountDownTimer timer;
    private Toolbar toolbar;
    private Button upload;
    private ImageView uploadImage;
    private StorageTask uploadTask;
    private TextView uploading;
    private String userAccountName = "Droid Developer";

    /* renamed from: com.walshotbeta.walshotvbeta.CameraUpload$1 */
    class C13471 implements OnClickListener {
        C13471() {
        }

        public void onClick(View v) {
            CameraUpload.this.openCameraIntent();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.CameraUpload$2 */
    class C13482 implements OnClickListener {
        C13482() {
        }

        public void onClick(View v) {
            CameraUpload.this.timer.start();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.CameraUpload$3 */
    class C13493 implements OnClickListener {
        C13493() {
        }

        public void onClick(View v) {
            CameraUpload.this.timer.cancel();
            CameraUpload.this.onBackPressed();
            CameraUpload.this.finish();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.CameraUpload$5 */
    class C17335 implements OnProgressListener<TaskSnapshot> {
        C17335() {
        }

        public void onProgress(TaskSnapshot taskSnapshot) {
            CameraUpload.this.progressBar.setProgress((int) ((double) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount())));
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.CameraUpload$6 */
    class C17346 implements OnFailureListener {
        C17346() {
        }

        public void onFailure(@NonNull Exception e) {
            Toast.makeText(CameraUpload.this, e.getMessage(), 1).show();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.CameraUpload$8 */
    class C17368 implements Consumer<File> {
        C17368() {
        }

        public void accept(File file) {
            CameraUpload.this.compressedImage = file;
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.CameraUpload$9 */
    class C17379 implements Consumer<Throwable> {
        C17379() {
        }

        public void accept(Throwable throwable) {
            throwable.printStackTrace();
            Toast.makeText(CameraUpload.this, throwable.getMessage(), 1).show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_upload);
        this.toolbar = (Toolbar) findViewById(R.id.toolbarUploadID);
        this.toolbar.setTitle("Upload Your Daily Shot");
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.uploadImage = (ImageView) findViewById(R.id.imageUploadID);
        this.upload = (Button) findViewById(R.id.uploadButtonID);
        this.storageReference = FirebaseStorage.getInstance().getReference("uploads");
        this.databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        this.database = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.progressBar = (NumberProgressBar) findViewById(R.id.progressBarID);
        this.progressBar.setMax(100);
        this.deviceName = Build.MODEL;
        this.uploading = (TextView) findViewById(R.id.uploadingTextID);
        this.countdown = (TextView) findViewById(R.id.countdownID);
        this.cancel = (Button) findViewById(R.id.cancelButtonID);
        this.uploadImage.setOnClickListener(new C13471());
        this.uploadImage.performClick();
        this.upload.setOnClickListener(new C13482());
        this.cancel.setOnClickListener(new C13493());
        this.timer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                CameraUpload.this.uploading.setVisibility(0);
                CameraUpload.this.countdown.setVisibility(0);
                TextView access$300 = CameraUpload.this.countdown;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("0");
                stringBuilder.append(millisUntilFinished / 1000);
                stringBuilder.append("s");
                access$300.setText(stringBuilder.toString());
            }

            public void onFinish() {
                CameraUpload.this.uploading.setVisibility(4);
                CameraUpload.this.countdown.setVisibility(4);
                CameraUpload.this.uploadData();
            }
        };
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                this.actualImage = createImageFile();
                if (this.actualImage != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(getPackageName());
                    stringBuilder.append(".provider");
                    this.imageUri = FileProvider.getUriForFile(this, stringBuilder.toString(), this.actualImage);
                    pictureIntent.putExtra(AdobeImageIntent.EXTRA_OUTPUT, this.imageUri);
                    startActivityForResult(pictureIntent, 10);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == -1) {
                Glide.with(this).load(this.imageFilePath).transition(DrawableTransitionOptions.withCrossFade()).apply(new RequestOptions().centerCrop()).into(this.uploadImage);
                customCompressImage();
            }
        } else if (resultCode == 0) {
            onBackPressed();
            finish();
        }
    }

    private void uploadData() {
        if (this.custom != null) {
            StorageReference fileReference = this.storageReference;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(System.currentTimeMillis());
            stringBuilder.append(".");
            stringBuilder.append(getFileExtension(this.custom));
            fileReference = fileReference.child(stringBuilder.toString());
            final String uploadId = this.databaseReference.push().getKey();
            fileReference.putFile(this.custom).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {

                /* renamed from: com.walshotbeta.walshotvbeta.CameraUpload$7$1 */
                class C13511 implements Runnable {
                    C13511() {
                    }

                    public void run() {
                        CameraUpload.this.progressBar.setProgress(0);
                    }
                }

                public void onSuccess(TaskSnapshot taskSnapshot) {
                    new Handler().postDelayed(new C13511(), 500);
                    CameraUpload.this.databaseReference.child(uploadId).child("compressedURL").setValue(taskSnapshot.getDownloadUrl().toString());
                    CameraUpload.this.databaseReference.child(uploadId).child("deviceName").setValue(CameraUpload.this.deviceName);
                    CameraUpload.this.databaseReference.child(uploadId).child("userName").setValue(CameraUpload.this.mAuth.getCurrentUser().getEmail());
                    DatabaseReference child = CameraUpload.this.databaseReference.child(uploadId).child("userPic");
                    Uri photoUrl = CameraUpload.this.mAuth.getCurrentUser().getPhotoUrl();
                    photoUrl.getClass();
                    child.setValue(photoUrl.toString());
                }
            }).addOnFailureListener(new C17346()).addOnProgressListener(new C17335());
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

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat(SharedPreferencesUtils.DEFAULT_OUTPUT_FILENAME_FORMAT, Locale.getDefault()).format(new Date());
        String imageFileName = new StringBuilder();
        imageFileName.append("IMG_");
        imageFileName.append(timeStamp);
        imageFileName.append("_");
        File image = File.createTempFile(imageFileName.toString(), ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        this.imageFilePath = image.getAbsolutePath();
        return image;
    }

    public void customCompressImage() {
        if (this.actualImage == null) {
            Toast.makeText(this, "Please choose an image!", 1).show();
            return;
        }
        new Compressor(this).compressToFileAsFlowable(this.actualImage).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new C17368(), new C17379());
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

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
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
