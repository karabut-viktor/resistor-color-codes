package com.blogspot.karabut.rescal.colorcode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.blogspot.karabut.rescal.R;
import com.blogspot.karabut.rescal.model.Color;
import com.blogspot.karabut.rescal.model.Resistor;
import com.blogspot.karabut.rescal.model.Resistors;

import static com.blogspot.karabut.rescal.model.Color.*;

public class ColorCode {
  public static final String TAG = "ColorCode";

  public static final int DIGIT1 = 0;
  public static final int DIGIT2 = 1;
  public static final int DIGIT3 = 2;
  public static final int MULTIPLIER = 3;
  public static final int TOLERANCE_LOW = 4;
  public static final int TOLERANCE_HIGH = 5;
  public static final int TEMPERATURE = 6;

  private static final ColorBand[] DIGIT1_BANDS;
  private static final ColorBand[] DIGIT2_BANDS;
  private static final ColorBand[] DIGIT3_BANDS;
  private static final ColorBand[] MULTIPLIER_BANDS;

  static {
    ArrayList<ColorBand> bands = new ArrayList<ColorBand>();
    for (Color color : Color.values()) {
      if (color.getDigit() != null && !BLACK.equals(color)) {
        bands.add(new DigitColorBand(color, R.string.description_digit_first));
      }
    }
    DIGIT1_BANDS = bands.toArray(new ColorBand[bands.size()]);
  }

  static {
    ArrayList<ColorBand> bands = new ArrayList<ColorBand>();
    for (Color color : Color.values()) {
      if (color.getDigit() != null) {
        bands.add(new DigitColorBand(color, R.string.description_digit_second));
      }
    }
    DIGIT2_BANDS = bands.toArray(new ColorBand[bands.size()]);
  }

  static {
    ArrayList<ColorBand> bands = new ArrayList<ColorBand>();
    for (Color color : Color.values()) {
      if (color.getDigit() != null) {
        bands.add(new DigitColorBand(color, R.string.description_digit_third));
      }
    }
    DIGIT3_BANDS = bands.toArray(new ColorBand[bands.size()]);
  }

  static {
    ArrayList<ColorBand> bands = new ArrayList<ColorBand>();
    for (Color color : Color.values()) {
      if (color.getMultiplier() != null) {
        bands.add(new MultiplierBand(color));
      }
    }
    Collections.sort(bands, new Comparator<ColorBand>() {
      @Override
      public int compare(ColorBand cb1, ColorBand cb2) {
        Integer mult1 = cb1.getColor().getMultiplier();
        Integer mult2 = cb2.getColor().getMultiplier();
        return mult1.compareTo(mult2);
      }
    });
    MULTIPLIER_BANDS = bands.toArray(new ColorBand[bands.size()]);
  }

  private static final ColorBand[] TOLERANCE_LOW_BANDS = {
      new ToleranceBand(BROWN),
      new ToleranceBand(RED),
      new ToleranceBand(GOLD),
      new ToleranceBand(SILVER)
  };

  private static final ColorBand[] TOLERANCE_HIGH_BANDS = {
      new ToleranceBand(GRAY),
      new ToleranceBand(VIOLET),
      new ToleranceBand(BLUE),
      new ToleranceBand(GREEN),
      new ToleranceBand(BROWN),
      new ToleranceBand(RED),
      new ToleranceBand(GOLD),
      new ToleranceBand(SILVER)
  };

  private static final ColorBand[] TCR_BANDS = {
      new TCRBand(BROWN),
      new TCRBand(RED),
      new TCRBand(ORANGE),
      new TCRBand(YELLOW),
      new TCRBand(BLUE),
      new TCRBand(VIOLET),
      new TCRBand(WHITE)
  };

  private static final int[] BANDS_4 = {
      DIGIT1, DIGIT2, MULTIPLIER, TOLERANCE_LOW,
  };
  private static final int[] BANDS_5 = {
      DIGIT1, DIGIT2, DIGIT3, MULTIPLIER, TOLERANCE_HIGH,
  };
  private static final int[] BANDS_6 = {
      DIGIT1, DIGIT2, DIGIT3, MULTIPLIER, TOLERANCE_HIGH, TEMPERATURE,
  };

  private static final int[] E6_VALUES = {
      10, 15, 22, 33, 47, 68,
  };
  private static final int[] E12_VALUES = {
      10, 12, 15, 18, 22, 27, 33, 39, 47, 56, 68, 82
  };

  private static final BigDecimal THOUSAND = BigDecimal.ONE.scaleByPowerOfTen(3);
  private static final BigDecimal MILLION = BigDecimal.ONE.scaleByPowerOfTen(6);

  /**
   * Get {@link ColorBand} at specific position of resistance code table.
   * <p>
   * For example, on 4 band resistor 3th band codes multiplier and at
   * 2nd position there is gold band with multiplier '0.1'.
   * All positions counts from 0. In order to get gold multiplier band call
   * <code>ColorCode.getColorBand(1, 2, 4)</code>.
   *
   * @param position     position of color
   * @param bandNr       position of band (0-5)
   * @param resistorSize resistor's size (4-6)
   * @return immutable {@link ColorBand} object
   * @throws IllegalArgumentException In case of invalid color code position
   */
  public static ColorBand getColorBand(int position, int bandNr, int resistorSize) {
    ColorBand[] bands = getBandsByNrAndResistorSize(bandNr, resistorSize);
    if (position < 0 || bands.length <= position) {
      throw new IllegalArgumentException("There is no band at  " + position
          + ", bandNr = " + bandNr
          + ", bandCount = " + resistorSize);
    }
    return bands[position];
  }

  public static ColorBand getColorBand(Color color, int bandNr, int resistorSize) {
    ColorBand[] bands = getBandsByNrAndResistorSize(bandNr, resistorSize);
    for (ColorBand band : bands) {
      if (band.getColor().equals(color)) {
        return band;
      }
    }

    throw new IllegalArgumentException("Cannot find band with color " + color + " at position " + bandNr + " size " + resistorSize);
  }

  /**
   * Get array of possible {@link ColorBand} at specific band number.
   *
   * @param bandNr       band number
   * @param resistorSize resistor's size (4-6)
   * @return array of {@link ColorBand}
   * @throws IllegalArgumentException In case of invalid color code position
   */
  public static ColorBand[] getBandsByNrAndResistorSize(int bandNr, int resistorSize) {
    if (bandNr < 0 || resistorSize <= bandNr) {
      throw new IllegalArgumentException("Invalid band nr " + bandNr);
    }

    int type = 0;
    switch (resistorSize) {
      case 4:
        type = BANDS_4[bandNr];
        break;
      case 5:
        type = BANDS_5[bandNr];
        break;
      case 6:
        type = BANDS_6[bandNr];
        break;
      default:
        throw new IllegalArgumentException("Invalid resistor size " + resistorSize);
    }

    return getBandsByType(type);
  }

  /**
   * Decode resistance value by color code.
   *
   * @param bands Array of {@link ColorBand} objects
   * @return resistance value
   */
  public static BigDecimal decodeResistance(ColorBand[] bands) {
    Resistor resistor = Resistors.get(toColors(bands));
    return resistor.getResistance();
  }

  public static String getPreferredValue(ColorBand[] bands, Context context) {
    BigDecimal value = decodeResistance(bands);
    int e = -3;
    long v = value.scaleByPowerOfTen(3).longValue();
    while (v >= 100) {
      e += 1;
      v /= 10;
    }
    Log.d(TAG, "v = " + v);
    Log.d(TAG, "e = " + e);

    BigDecimal valueE6 = new BigDecimal(getNearestArrayValue(v, E6_VALUES));
    valueE6 = valueE6.scaleByPowerOfTen(e);
    Log.d(TAG, "E6 = " + valueE6.toPlainString());

    BigDecimal valueE12 = new BigDecimal(getNearestArrayValue(v, E12_VALUES));
    valueE12 = valueE12.scaleByPowerOfTen(e);
    Log.d(TAG, "E12 = " + valueE12.toPlainString());

    Resources res = context.getResources();
    if (valueE6.equals(value)) {
      return res.getString(R.string.nearest_value_equal_E6);
    }
    else if (valueE12.equals(value)) {
      return res.getString(R.string.nearest_value_equal_E12);
    }
    else if (valueE12.equals(valueE6)) {
      return res.getString(
          R.string.nearest_value_E6,
          getScaledValue(valueE6, context));
    }
    else {
      return res.getString(
          R.string.nearest_value_E6_and_E12,
          getScaledValue(valueE6, context),
          getScaledValue(valueE12, context));
    }
  }

  private static int getNearestArrayValue(long value, int[] a) {
    int k = 0;
    for (int i = 0; i < a.length; i++) {
      if (Math.abs(a[i] - value) < Math.abs(a[k] - value)) {
        k = i;
      }
    }
    return a[k];
  }

  public static String getScaledValue(BigDecimal value, Context context) {
    String prefix = "";
    if (value.compareTo(MILLION) > 0) {
      prefix = context.getResources().getString(R.string.prefix_mega);
      value = value.scaleByPowerOfTen(-6);
    }
    else if (value.compareTo(THOUSAND) > 0) {
      prefix = context.getResources().getString(R.string.prefix_kilo);
      value = value.scaleByPowerOfTen(-3);
    }

    return value.toPlainString() + "\u2009" + prefix;
  }

  public static String getValueText(ColorBand[] bands, Context context) {
    String value = getScaledValue(decodeResistance(bands), context);
    Resources res = context.getResources();

    if (bands.length == 4) {
      String tolerance = bands[3].getColor().getTolerance();
      return res.getString(R.string.value_text, value, tolerance);
    }
    else if (bands.length == 5) {
      String tolerance = bands[4].getColor().getTolerance();
      return res.getString(R.string.value_text, value, tolerance);
    }
    else {
      String temperature = bands[5].getColor().getTCR() + "\u2009";
      String tolerance = bands[4].getColor().getTolerance();
      return res.getString(
          R.string.value_text_temperature,
          value,
          tolerance,
          temperature);
    }
  }

  private static Color[] toColors(ColorBand... bands) {
    Color[] colors = new Color[bands.length];
    for (int i = 0; i < bands.length; i++) {
      colors[i] = bands[i].getColor();
    }
    return colors;
  }

  private static ColorBand[] getBandsByType(int type) {
    switch (type) {
      case DIGIT1:
        return DIGIT1_BANDS;
      case DIGIT2:
        return DIGIT2_BANDS;
      case DIGIT3:
        return DIGIT3_BANDS;
      case MULTIPLIER:
        return MULTIPLIER_BANDS;
      case TOLERANCE_LOW:
        return TOLERANCE_LOW_BANDS;
      case TOLERANCE_HIGH:
        return TOLERANCE_HIGH_BANDS;
      case TEMPERATURE:
        return TCR_BANDS;
      default:
        throw new IllegalArgumentException("Invalid band type " + type);
    }
  }
}
