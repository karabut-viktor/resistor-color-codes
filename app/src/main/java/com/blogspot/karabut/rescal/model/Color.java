package com.blogspot.karabut.rescal.model;

public enum Color {
  BLACK (   0,    0,   null),
  BROWN (   1,    1,    "1"),
  RED   (   2,    2,    "2"),
  ORANGE(   3,    3,   null),
  YELLOW(   4,    4,   null),
  GREEN (   5,    5,  "0.5"),
  BLUE  (   6,    6, "0.25"),
  VIOLET(   7,    7,  "0.1"),
  GRAY  (   8, null, "0.05"),
  WHITE (   9, null,   null),
  GOLD  (null,   -1,    "5"),
  SILVER(null,   -2,   "10");

  private final Integer digit;
  private final Integer multiplier;
  private final CharSequence tolerance;

  Color(Integer digit, Integer multiplier, CharSequence tolerance) {
    this.digit = digit;
    this.multiplier = multiplier;
    this.tolerance = tolerance;
  }

  public CharSequence getTolerance() {
    return tolerance;
  }

  public Integer getMultiplier() {
    return multiplier;
  }

  public Integer getDigit() {
    return digit;
  }
}
