package com.blogspot.karabut.rescal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface Resistor extends List<Color>, Serializable {
  BigDecimal getResistance();

  String getTolerance();

  String getTCR();
}
