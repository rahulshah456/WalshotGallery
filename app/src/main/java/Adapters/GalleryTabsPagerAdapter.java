package Adapters;

import Fragments.GalleryImagesFragment;
import Fragments.GalleryVideosFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class GalleryTabsPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence[] mNames;
    int totalTabs;

    public GalleryTabsPagerAdapter(FragmentManager fm, CharSequence[] mNames, int totalTabs) {
        super(fm);
        this.mNames = mNames;
        this.totalTabs = totalTabs;
    }

    public CharSequence getPageTitle(int position) {
        return this.mNames[position];
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            return new GalleryImagesFragment();
        }
        return new GalleryVideosFragment();
    }

    public int getCount() {
        return this.totalTabs;
    }
}
