package com.blogspot.karabut.rescal.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Resistor {
  private final static String DEFAULT_TOLERANCE = "20";

  private final List<Color> colors;

  public Resistor(Color... colors) {
    this.colors = Arrays.asList(colors);
  }

  public CharSequence getTolerance() {
    if (colors.size() == 4)
      return DEFAULT_TOLERANCE;
    else
      return colors.get(4).getTolerance();
  }

  public BigDecimal getResistance() {
    BigDecimal value;
    int mult;

    if (colors.size() == 4) {
      value = new BigDecimal(colors.get(0).getDigit() * 10 + colors.get(1).getDigit());
      mult = colors.get(2).getMultiplier();
    }  else  {
      value = new BigDecimal(
          + colors.get(0).getDigit() * 100
          + colors.get(1).getDigit() * 10
          + colors.get(2).getDigit() * 1);
      mult = colors.get(3).getMultiplier();
    }

    return value.scaleByPowerOfTen(mult);
  }
}
