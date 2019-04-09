package com.app.walshotgallery;

import agency.tango.materialintroscreen.SlideFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class CustomSlide extends SlideFragment {
    private CheckBox checkBox;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(C1420R.layout.fragment_custom_slide, container, false);
        this.checkBox = (CheckBox) view.findViewById(C1420R.id.checkBoxID);
        return view;
    }

    public int backgroundColor() {
        return C1420R.color.custom_slide_background;
    }

    public int buttonsColor() {
        return C1420R.color.custom_slide_buttons;
    }

    public boolean canMoveFurther() {
        return this.checkBox.isChecked();
    }

    public String cantMoveFurtherErrorMessage() {
        return getString(C1420R.string.error_message);
    }
}
