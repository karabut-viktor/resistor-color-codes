package com.blogspot.karabut.rescal.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

class Band56Resistor implements Resistor {
  private final List<Color> colors;

  public Band56Resistor(Color... colors) {
    this.colors = Arrays.asList(colors);
  }

  public String getTolerance() {
    return colors.get(4).getTolerance();
  }

  public String getTCR() {
    return colors.size() == 6? colors.get(5).getTCR() : null;
  }

  public BigDecimal getResistance() {
    Color c1 = colors.get(0), c2 = colors.get(1), c3 = colors.get(2), c4 = colors.get(3);

    BigDecimal value = new BigDecimal(100*c1.getDigit() + 10*c2.getDigit() + c3.getDigit());
    return value.scaleByPowerOfTen(c4.getMultiplier());
  }

  public List<Color> getColors() {
    return colors;
  }
}
