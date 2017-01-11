package com.blogspot.karabut.rescal.model;

public enum Color {

//  new ColorBand(BROWN, TEMPERATURE, 100),
//  new ColorBand(RED, TEMPERATURE, 50),
//  new ColorBand(ORANGE, TEMPERATURE, 15),
//  new ColorBand(YELLOW, TEMPERATURE, 25),
//  new ColorBand(BLUE, TEMPERATURE, 10),
//  new ColorBand(VIOLET, TEMPERATURE, 5),
//  new ColorBand(WHITE, TEMPERATURE, 1),
  BLACK (   0,    0,   null,  null),
  BROWN (   1,    1,    "1", "100"),
  RED   (   2,    2,    "2",  "50"),
  ORANGE(   3,    3,   null,  "15"),
  YELLOW(   4,    4,   null,  "25"),
  GREEN (   5,    5,  "0.5",  null),
  BLUE  (   6,    6, "0.25",  null),
  VIOLET(   7,    7,  "0.1",  "10"),
  GRAY  (   8, null, "0.05",   "5"),
  WHITE (   9, null,   null,   "1"),
  GOLD  (null,   -1,    "5",  null),
  SILVER(null,   -2,   "10",  null);

  private final Integer digit;
  private final Integer multiplier;
  private final String tolerance;
  private final String tcr;

  Color(Integer digit, Integer multiplier, String tolerance, String tcr) {
    this.digit = digit;
    this.multiplier = multiplier;
    this.tolerance = tolerance;
    this.tcr = tcr;
  }

  public String getTolerance() {
    return tolerance;
  }

  public Integer getMultiplier() {
    return multiplier;
  }

  public Integer getDigit() {
    return digit;
  }

  public String getTCR() {
    return tcr;
  }
}
