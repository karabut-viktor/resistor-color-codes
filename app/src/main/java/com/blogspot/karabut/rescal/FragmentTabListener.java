package com.blogspot.karabut.rescal;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;

public class FragmentTabListener implements TabListener {
    private final Activity mActivity;
    private final String mTag;
    private final Fragment fragment;

    // This version defaults to replacing the entire activity content area
    // new FragmentTabListener<SomeFragment>(this, "first", SomeFragment.class))
    public FragmentTabListener(Activity activity, String tag, Fragment fragment) {
        mActivity = activity;
        mTag = tag;
        this.fragment = fragment;
    }

    /* The following are each of the ActionBar.TabListener callbacks */
    public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
        FragmentTransaction sft = mActivity.getFragmentManager().beginTransaction();
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
