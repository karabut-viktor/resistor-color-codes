package com.blogspot.karabut.rescal.model;

import java.util.AbstractList;

abstract class AbstractResistor extends AbstractList<Color> implements  Resistor {
  protected final Color[] colors;

  protected AbstractResistor(Color... colors) {
    this.colors = colors;
  }

  @Override
  public int size() {
    return colors.length;
  }

  @Override
  public Color get(int i) {
    return colors[i];
  }
}
