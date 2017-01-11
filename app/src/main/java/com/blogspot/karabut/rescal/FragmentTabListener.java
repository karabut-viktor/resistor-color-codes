package com.blogspot.karabut.rescal;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.view.View;

public class FragmentTabListener implements TabListener {
  private final Activity activity;
  private final int pos;
  private final Fragment fragment;

  // This version defaults to replacing the entire activity content area
  // new FragmentTabListener<SomeFragment>(this, "first", SomeFragment.class))
  public FragmentTabListener(Activity activity, int pos, Fragment fragment) {
    this.activity = activity;
    this.pos = pos;
    this.fragment = fragment;
  }

  /* The following are each of the ActionBar.TabListener callbacks */
  public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
    View contentView =  activity.findViewById(android.R.id.content);
    Integer prev = (Integer) contentView.getTag(R.id.tag_tab);
    contentView.setTag(R.id.tag_tab, pos);
    FragmentTransaction sft = activity.getFragmentManager().beginTransaction();
    if (prev != null && prev < pos)
      sft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
    if (prev != null && prev > pos)
      sft.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left);
    sft.replace(android.R.id.content, fragment);
    sft.commit();
  }

  public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
    // do nothing
  }

  public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
    // do nothing
  }
}
