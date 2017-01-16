package com.blogspot.karabut.rescal.model;

import java.math.BigDecimal;

class Band56Resistor extends AbstractResistor {

  public Band56Resistor(Color... colors) {
    super(colors);
  }

  public String getTolerance() {
    return get(4).getTolerance();
  }

  public String getTCR() {
    return colors.length == 6? get(5).getTCR() : null;
  }

  public BigDecimal getResistance() {
    Color c1 = get(0), c2 = get(1), c3 =get(2), c4 = get(3);

    BigDecimal value = new BigDecimal(100*c1.getDigit() + 10*c2.getDigit() + c3.getDigit());
    return value.scaleByPowerOfTen(c4.getMultiplier());
  }
}
