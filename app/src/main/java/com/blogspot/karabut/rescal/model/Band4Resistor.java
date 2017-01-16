package com.blogspot.karabut.rescal.model;

import java.math.BigDecimal;

class Band4Resistor extends AbstractResistor {
  private final static String DEFAULT_TOLERANCE = "20";

  public Band4Resistor(Color... colors) {
    super(colors);
  }

  public String getTolerance() {
    return DEFAULT_TOLERANCE;
  }

  public String getTCR() {
    return null;
  }

  public BigDecimal getResistance() {
    Color c1 =get(0), c2 = get(1), c3 = get(2);
    BigDecimal value = new BigDecimal(c1.getDigit() * 10 + c2.getDigit());

    return value.scaleByPowerOfTen(c3.getMultiplier());
  }
}
