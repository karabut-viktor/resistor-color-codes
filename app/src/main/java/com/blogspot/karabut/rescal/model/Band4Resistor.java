package com.blogspot.karabut.rescal.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

class Band4Resistor implements Resistor {
  private final static String DEFAULT_TOLERANCE = "20";

  private final List<Color> colors;

  public Band4Resistor(Color c1, Color c2, Color c3, Color c4) {
    this.colors = Arrays.asList(c1, c2, c3, c4);
  }

  public String getTolerance() {
    return DEFAULT_TOLERANCE;
  }

  public String getTCR() {
    return null;
  }

  public BigDecimal getResistance() {
    Color c1 = colors.get(0), c2 = colors.get(1), c3 = colors.get(2);
    BigDecimal value = new BigDecimal(c1.getDigit() * 10 + c2.getDigit());

    return value.scaleByPowerOfTen(c3.getMultiplier());
  }

  public List<Color> getColors() {
    return colors;
  }
}
