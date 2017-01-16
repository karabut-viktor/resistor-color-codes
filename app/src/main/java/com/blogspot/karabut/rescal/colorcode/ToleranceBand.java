package com.blogspot.karabut.rescal.colorcode;

import android.content.Context;
import android.content.res.Resources;

import com.blogspot.karabut.rescal.R;
import com.blogspot.karabut.rescal.model.Color;

class ToleranceBand extends AbstractColorBand {
  protected ToleranceBand(Color color) {
    super(color);

    if (color.getTolerance() == null)
      throw new IllegalArgumentException("Cannot create tolerance band with " + color);
  }

  @Override
  public String getDescription(Context context) {
    Resources res = context.getResources();
    return res.getString(R.string.description_tolerance, String.valueOf(color.getTolerance()));
  }
}
