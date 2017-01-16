package com.blogspot.karabut.rescal.colorcode;

import android.content.Context;
import android.content.res.Resources;

import com.blogspot.karabut.rescal.model.Color;

class DigitColorBand extends AbstractColorBand {
  private final int descriptionId;

  DigitColorBand(Color color, int descriptionId) {
    super(color);

    if (color.getDigit() == null)
      throw new IllegalArgumentException("Cannot create DigitColorBand with " + color);

    this.descriptionId = descriptionId;
  }

  @Override
  public String getDescription(Context context) {
    Resources res = context.getResources();
    return res.getString(descriptionId, String.valueOf(color.getDigit()));
  }
}
