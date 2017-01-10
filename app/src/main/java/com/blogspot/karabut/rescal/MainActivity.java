package com.blogspot.karabut.rescal;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class MainActivity extends Activity {
	static final int[][] INITIAL_BANDS = {
		{6, 5, 8, 1,},
		{6, 5, 6, 4, 7,},
		{6, 5 ,5, 4, 7, 2},
	};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (int i = 0; i < 3; i++) {
            Fragment fragment = new ResistorFragment();
            Bundle args = new Bundle();
            args.putInt(ResistorFragment.KEY_ID, i);
            args.putIntArray(ResistorFragment.KEY_BANDS, INITIAL_BANDS[i]);
            fragment.setArguments(args);

            ActionBar.Tab bandTab = actionBar
                    .newTab()
                    .setText(getString(R.string.tab_band_4).toUpperCase())
                    .setTabListener(new FragmentTabListener(this, "4 band", fragment));

            actionBar.addTab(bandTab);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }

    

//
//    /**
//     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
//     * sections of the app.
//     */
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int i) {
//            Fragment fragment = new ResistorFragment();
//
//            Bundle args = new Bundle();
//            args.putInt(ResistorFragment.KEY_ID, i);
//            args.putIntArray(ResistorFragment.KEY_BANDS, INITIAL_BANDS[i]);
//            fragment.setArguments(args);
//
//            return fragment;
//        }
//
//        @Override
//        public int getCount() {
//            return 3;
//        }
//
//        @Override
    private CharSequence getTabTitle(int position) {
        switch (position) {
            case 0: return getString(R.string.tab_band_4).toUpperCase();
            case 1: return getString(R.string.tab_band_5).toUpperCase();
            case 2: return getString(R.string.tab_band_6).toUpperCase();
        }
        return null;
    }
//    }

}
