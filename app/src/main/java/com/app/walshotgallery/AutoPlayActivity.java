package com.app.walshotgallery;

import Fragments.GalleryVideosFragment;
import Modals.VideosFolder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.PlayerListener;
import tcking.github.com.giraffeplayer2.VideoView;
import tv.danmaku.ijk.media.player.IjkTimedText;

public class AutoPlayActivity extends AppCompatActivity implements PlayerListener {
    private static final String TAG = AutoPlayActivity.class.getSimpleName();
    private static int currentFolderPosition;
    private static int currentItemPosition;
    private View decorView;
    private Bundle extras;
    private VideoView videoView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_play);
        this.decorView = getWindow().getDecorView();
        this.videoView = (VideoView) findViewById(R.id.giraffePlayer);
        this.extras = getIntent().getExtras();
        if (this.extras != null) {
            currentItemPosition = this.extras.getInt("itemPosition");
            currentFolderPosition = this.extras.getInt("folderPosition");
        }
        Window window = getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        String videoUri = new StringBuilder();
        videoUri.append("file://");
        videoUri.append((String) ((VideosFolder) GalleryVideosFragment.videosFolders.get(currentFolderPosition)).getAllVideoPaths().get(currentItemPosition));
        videoUri = videoUri.toString();
        String name = (String) ((VideosFolder) GalleryVideosFragment.videosFolders.get(currentFolderPosition)).getAllVideoPaths().get(currentItemPosition);
        String result = name.substring(name.lastIndexOf(47) + 1).trim();
        this.videoView.setVideoPath(videoUri);
        this.videoView.getPlayer().start();
        this.videoView.getVideoInfo().setBgColor(ViewCompat.MEASURED_STATE_MASK);
        this.videoView.getVideoInfo().setTitle(result);
        this.videoView.getVideoInfo().setFullScreenAnimation(true);
        this.videoView.getVideoInfo().setShowTopBar(true);
        this.videoView.getPlayer().setPlayerListener(this);
        this.videoView.getVideoInfo().setLooping(true);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void hideNavBar() {
        if (VERSION.SDK_INT >= 19) {
            this.decorView.setSystemUiVisibility(5894);
        }
    }

    private void showSystemUI() {
        this.decorView.setSystemUiVisibility(768);
    }

    public void onPrepared(GiraffePlayer giraffePlayer) {
    }

    public void onBufferingUpdate(GiraffePlayer giraffePlayer, int percent) {
    }

    public boolean onInfo(GiraffePlayer giraffePlayer, int what, int extra) {
        return false;
    }

    public void onCompletion(GiraffePlayer giraffePlayer) {
    }

    public void onSeekComplete(GiraffePlayer giraffePlayer) {
    }

    public boolean onError(GiraffePlayer giraffePlayer, int what, int extra) {
        return false;
    }

    public void onPause(GiraffePlayer giraffePlayer) {
    }

    public void onRelease(GiraffePlayer giraffePlayer) {
    }

    public void onStart(GiraffePlayer giraffePlayer) {
    }

    public void onTargetStateChange(int oldState, int newState) {
    }

    public void onCurrentStateChange(int oldState, int newState) {
    }

    public void onDisplayModelChange(int oldModel, int newModel) {
        if (newModel == 1) {
            hideNavBar();
        } else {
            showSystemUI();
        }
    }

    public void onPreparing(GiraffePlayer giraffePlayer) {
    }

    public void onTimedText(GiraffePlayer giraffePlayer, IjkTimedText text) {
    }

    public void onLazyLoadProgress(GiraffePlayer giraffePlayer, int progress) {
    }

    public void onLazyLoadError(GiraffePlayer giraffePlayer, String message) {
    }
}
