package com.blogspot.karabut.rescal.colorcode;

import android.content.Context;
import android.content.res.Resources;

import com.blogspot.karabut.rescal.R;
import com.blogspot.karabut.rescal.model.Color;

class MultiplierBand extends AbstractColorBand {

  protected MultiplierBand(Color color) {
    super(color);

    if (color.getMultiplier() == null)
      throw new IllegalArgumentException("Cannot create multiplier band with" + color);
  }

  @Override
  public String getDescription(Context context) {
    Resources res = context.getResources();
    if (color.getMultiplier() == 0) {
      String kilo = res.getString(R.string.prefix_kilo);
      String mega = res.getString(R.string.prefix_mega);
      return res.getString(R.string.description_multiplier, getMultiplier(color.getMultiplier(), kilo, mega));
    }
    else {
      return res.getString(R.string.description_multiplier_without);
    }
  }

  private static String getMultiplier(int value, String kilo, String mega) {
    switch (value) {
      case -2:
        return "0.01";
      case -1:
        return "0.1";
      case 0:
        return "1";
      case 1:
        return "10";
      case 2:
        return "100";
      case 3:
      case 4:
      case 5:
        return getMultiplier(value - 3, kilo, mega) + kilo;
      case 6:
      case 7:
      case 8:
        return getMultiplier(value - 6, kilo, mega) + mega;
      default:
        return null;
    }
  }
}
