package com.blogspot.karabut.rescal.colorcode;

import android.content.Context;
import android.content.res.Resources;

import com.blogspot.karabut.rescal.R;
import com.blogspot.karabut.rescal.model.Color;

class TCRBand extends AbstractColorBand {
  protected TCRBand(Color color) {
      super(color);

      if (color.getTCR() == null)
        throw new IllegalArgumentException("Cannot create tcr band with" + color);
    }

    @Override
    public String getDescription(Context context) {
      Resources res = context.getResources();
      return res.getString(R.string.description_temperature, String.valueOf(color.getTCR()));
    }
}
