package com.blogspot.karabut.rescal;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

public interface ColorBand extends Serializable{
  String getTitle(Context context);

  String getDescription(Context context);

  Drawable getIcon(Context context);
}
