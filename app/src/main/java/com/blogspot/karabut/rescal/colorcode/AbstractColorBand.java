package com.blogspot.karabut.rescal.colorcode;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.blogspot.karabut.rescal.R;
import com.blogspot.karabut.rescal.model.Color;

abstract class AbstractColorBand implements ColorBand {
  protected final Color color;

  protected AbstractColorBand(Color color) {
    this.color = color;
  }

  @Override
  public String getTitle(Context context) {
    Resources res = context.getResources();
    return res.getStringArray(R.array.color_titles)[color.ordinal()];
  }

  @Override
  public Drawable getIcon(Context context) {
    Resources res = context.getResources();
    return res.obtainTypedArray(R.array.color_icons).getDrawable(color.ordinal());
  }

  @Override
  public Color getColor() {
    return color;
  }
}
