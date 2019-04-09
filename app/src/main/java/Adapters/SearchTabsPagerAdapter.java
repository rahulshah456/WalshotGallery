package Adapters;

import Fragments.SearchCollectionFragment;
import Fragments.SearchWallpaperFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SearchTabsPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence[] mNames;
    String query;
    int totalTabs;

    public SearchTabsPagerAdapter(FragmentManager manager, CharSequence[] mNames, int totalTabs) {
        super(manager);
        this.mNames = mNames;
        this.totalTabs = totalTabs;
    }

    public CharSequence getPageTitle(int position) {
        return this.mNames[position];
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            return new SearchWallpaperFragment();
        }
        return new SearchCollectionFragment();
    }

    public int getCount() {
        return this.totalTabs;
    }
}
