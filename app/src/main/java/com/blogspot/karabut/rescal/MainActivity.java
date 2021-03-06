package com.blogspot.karabut.rescal;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.blogspot.karabut.rescal.model.Color;
import com.blogspot.karabut.rescal.model.Resistor;
import com.blogspot.karabut.rescal.model.Resistors;

public class MainActivity extends Activity {

  private static final Resistor[] INITIAL_RESISTORS = {
    Resistors.get(Color.BLUE, Color.GREEN, Color.VIOLET, Color.BROWN),
    Resistors.get(Color.BLUE, Color.GREEN, Color.BLUE, Color.YELLOW, Color.VIOLET),
    Resistors.get(Color.BLUE, Color.GREEN, Color.BLUE, Color.YELLOW, Color.VIOLET, Color.BROWN),
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final ActionBar actionBar = getActionBar();
    actionBar.setIcon(android.R.color.transparent);
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    for (int i = 0; i < 3; i++) {
      Fragment fragment = new ResistorFragment();
      Bundle args = new Bundle();
      args.putInt(ResistorFragment.KEY_ID, i);
      args.putSerializable(ResistorFragment.KEY_RESISTOR, INITIAL_RESISTORS[i]);
      fragment.setArguments(args);

      ActionBar.Tab bandTab = actionBar
          .newTab()
          .setText(getTabTitle(i))
          .setTabListener(new FragmentTabListener(this, i, fragment));

      actionBar.addTab(bandTab);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.activity_select, menu);

    return true;
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
