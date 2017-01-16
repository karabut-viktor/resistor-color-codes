package com.blogspot.karabut.rescal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface Resistor extends Serializable {
  List<Color> getColors();

  BigDecimal getResistance();

  String getTolerance();

  String getTCR();
}
