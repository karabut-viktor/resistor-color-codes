package com.blogspot.karabut.rescal.model;

import java.math.BigDecimal;
import java.util.List;

public interface Resistor {
  List<Color> getColors();

  BigDecimal getResistance();

  String getTolerance();

  String getTCR();
}
