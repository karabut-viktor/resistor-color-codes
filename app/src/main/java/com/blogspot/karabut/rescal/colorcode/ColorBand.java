package com.blogspot.karabut.rescal.colorcode;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.blogspot.karabut.rescal.model.Color;

import java.io.Serializable;

public interface ColorBand extends Serializable{
  String getTitle(Context context);

  String getDescription(Context context);

  Drawable getIcon(Context context);

  Color getColor();
}
