package com.blogspot.karabut.rescal.model;

import java.util.List;

public class Resistors {
  private Resistors() {}

  public static Resistor get(Color... colors) {
    if (colors.length == 4) {
      return new Band4Resistor(colors[0], colors[1], colors[2], colors[3]);
    }
    else {
      return new Band56Resistor(colors);
    }
  }

  public static Resistor get(List<Color> colors) {
    return get(colors.toArray(new Color[colors.size()]));
  }
}
