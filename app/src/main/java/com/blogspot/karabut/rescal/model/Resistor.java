package com.blogspot.karabut.rescal.model;

import java.util.Arrays;
import java.util.List;

public class Resistor {
  private final List<Color> colors;

  public Resistor(Color... colors) {
    this.colors = Arrays.asList(colors);
  }
}
