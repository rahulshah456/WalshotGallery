package Adapters;

import Fragments.EditorsCollectionsFragment;
import Fragments.FeaturedCollectionsFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CollectionTabsPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence[] mNames;
    int totalTabs;

    public CollectionTabsPagerAdapter(FragmentManager fm, CharSequence[] mNames, int totalTabs) {
        super(fm);
        this.mNames = mNames;
        this.totalTabs = totalTabs;
    }

    public CharSequence getPageTitle(int position) {
        return this.mNames[position];
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            return new FeaturedCollectionsFragment();
        }
        return new EditorsCollectionsFragment();
    }

    public int getCount() {
        return this.totalTabs;
    }
}
