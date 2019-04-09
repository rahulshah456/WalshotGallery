package Adapters;

import Fragments.EditorsFragment;
import Fragments.LatestFragment;
import Fragments.PopularFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class WallpaperTabsPagerAdapter extends FragmentStatePagerAdapter {
    int NumbOfTabs;
    CharSequence[] Titles;

    public WallpaperTabsPagerAdapter(FragmentManager fragmentManager, CharSequence[] mTitles, int totalTabsCount) {
        super(fragmentManager);
        this.Titles = mTitles;
        this.NumbOfTabs = totalTabsCount;
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            return new LatestFragment();
        }
        if (position == 1) {
            return new PopularFragment();
        }
        return new EditorsFragment();
    }

    public CharSequence getPageTitle(int position) {
        return this.Titles[position];
    }

    public int getCount() {
        return this.NumbOfTabs;
    }
}
