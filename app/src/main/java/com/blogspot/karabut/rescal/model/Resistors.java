package com.blogspot.karabut.rescal.model;

public class Resistors {
  private Resistors() {}

  public static Resistor get(Color c1, Color c2, Color c3, Color c4) {
    return new Band4Resistor(c1, c2, c3, c4);
  }

  public static Resistor get(Color c1, Color c2, Color c3, Color c4, Color c5) {
    return new Band56Resistor(c1, c2, c3, c4, c5);
  }

  public static Resistor get(Color c1, Color c2, Color c3, Color c4, Color c5, Color c6) {
    return new Band56Resistor(c1, c2, c3, c4, c5, c6);
  }
}
