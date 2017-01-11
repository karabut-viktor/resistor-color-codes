package com.blogspot.karabut.rescal;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class MainActivity extends Activity {
  static final int[][] INITIAL_BANDS = {
      {6, 5, 8, 1,},
      {6, 5, 6, 4, 7,},
      {6, 5, 5, 4, 7, 2},
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
          .setText(getTabTitle(i))
          .setTabListener(new FragmentTabListener(this, i, fragment));

      actionBar.addTab(bandTab);
    }
  }

  private CharSequence getTabTitle(int position) {
    switch (position) {
      case 0:
        return getString(R.string.tab_band_4).toUpperCase();
      case 1:
        return getString(R.string.tab_band_5).toUpperCase();
      case 2:
        return getString(R.string.tab_band_6).toUpperCase();
    }
    return null;
  }
}
