package com.app.walshotgallery;

import Modals.IntroPrefManager;
import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class IntroActivity extends MaterialIntroActivity {
    IntroPrefManager prefManager;

    /* renamed from: com.walshotbeta.walshotvbeta.IntroActivity$2 */
    class C13882 implements OnClickListener {
        C13882() {
        }

        public void onClick(View v) {
            IntroActivity.this.showMessage("We provide daily new fresh wallpapers.");
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.IntroActivity$3 */
    class C13893 implements OnClickListener {
        C13893() {
        }

        public void onClick(View v) {
            IntroActivity.this.showMessage("Easy UI Experience for your gallery.");
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.IntroActivity$4 */
    class C13904 implements OnClickListener {
        C13904() {
        }

        public void onClick(View v) {
            IntroActivity.this.showMessage("You can upload your daily beautiful captions.");
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.IntroActivity$5 */
    class C13915 implements OnClickListener {
        C13915() {
        }

        public void onClick(View v) {
            IntroActivity.this.showMessage("Thanks For Choosing US!");
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.IntroActivity$1 */
    class C17661 implements IViewTranslation {
        C17661() {
        }

        public void translate(View view, @FloatRange(from = 0.0d, to = 1.0d) float percentage) {
            view.setAlpha(percentage);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(67108864, 67108864);
        enableLastSlideAlphaExitTransition(true);
        this.prefManager = new IntroPrefManager(this);
        getBackButtonTranslationWrapper().setEnterTranslation(new C17661());
        addSlide(new SlideFragmentBuilder().backgroundColor(C1420R.color.first_slide_background).buttonsColor(C1420R.color.first_slide_buttons).image(C1420R.drawable.wallpaper).title("Customize Your Wallpaper").description("Make your device look fresh always").build(), new MessageButtonBehaviour(new C13882(), "Fresh Wallpapers"));
        addSlide(new SlideFragmentBuilder().backgroundColor(C1420R.color.second_slide_background).buttonsColor(C1420R.color.second_slide_buttons).image(C1420R.drawable.photos_gallery).title("Manage Your Gallery").description("Easily manage your device photos ").build(), new MessageButtonBehaviour(new C13893(), "Manage Gallery"));
        addSlide(new SlideFragmentBuilder().backgroundColor(C1420R.color.third_slide_background).buttonsColor(C1420R.color.third_slide_buttons).image(C1420R.drawable.daily).title("Show Your Daily Captions").description("Join Our Community").build(), new MessageButtonBehaviour(new C13904(), "Upload Captions"));
        addSlide(new CustomSlide());
        addSlide(new SlideFragmentBuilder().backgroundColor(C1420R.color.fourth_slide_background).buttonsColor(C1420R.color.fourth_slide_buttons).neededPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.INTERNET", "android.permission.SET_WALLPAPER", "android.permission.CAMERA", "android.permission.SET_WALLPAPER_HINTS", "android.permission.WAKE_LOCK"}).image(C1420R.drawable.permissions).title("We Believe in Genuinity").description("Give App Permisions").build(), new MessageButtonBehaviour(new C13915(), "Done"));
    }

    public void onFinish() {
        super.onFinish();
        startActivity(new Intent(this, MainActivity.class));
        this.prefManager.setFirstTimeLaunch(false);
        Toast.makeText(this, "Welcome to Walshot!", 0).show();
    }
}
