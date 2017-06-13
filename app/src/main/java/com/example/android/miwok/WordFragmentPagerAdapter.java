package com.example.android.miwok;

/**
 * Created by Sam Chang (local) on 5/29/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class WordFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Numbers", "Family Members", "Colors", "Phrases" };

    public WordFragmentPagerAdapter (FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new NumbersFragment();
        } else if (position == 1){
            return new FamilyFragment();
        } else if (position == 2){
            return new ColorsFragment();
        } else {
            return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
