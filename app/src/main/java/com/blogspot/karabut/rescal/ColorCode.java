package com.blogspot.karabut.rescal;

import java.math.BigDecimal;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

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

  private static final ColorBandImpl[] DIGIT1_BANDS = {
      new ColorBandImpl(BROWN, DIGIT1, 1),
      new ColorBandImpl(RED, DIGIT1, 2),
      new ColorBandImpl(ORANGE, DIGIT1, 3),
      new ColorBandImpl(YELLOW, DIGIT1, 4),
      new ColorBandImpl(GREEN, DIGIT1, 5),
      new ColorBandImpl(BLUE, DIGIT1, 6),
      new ColorBandImpl(VIOLET, DIGIT1, 7),
      new ColorBandImpl(GRAY, DIGIT1, 8),
      new ColorBandImpl(WHITE, DIGIT1, 9),
  };
  private static final ColorBandImpl[] DIGIT2_BANDS = {
      new ColorBandImpl(BLACK, DIGIT2, 0),
      new ColorBandImpl(BROWN, DIGIT2, 1),
      new ColorBandImpl(RED, DIGIT2, 2),
      new ColorBandImpl(ORANGE, DIGIT2, 3),
      new ColorBandImpl(YELLOW, DIGIT2, 4),
      new ColorBandImpl(GREEN, DIGIT2, 5),
      new ColorBandImpl(BLUE, DIGIT2, 6),
      new ColorBandImpl(VIOLET, DIGIT2, 7),
      new ColorBandImpl(GRAY, DIGIT2, 8),
      new ColorBandImpl(WHITE, DIGIT2, 9),
  };
  private static final ColorBandImpl[] DIGIT3_BANDS = {
      new ColorBandImpl(BLACK, DIGIT3, 0),
      new ColorBandImpl(BROWN, DIGIT3, 1),
      new ColorBandImpl(RED, DIGIT3, 2),
      new ColorBandImpl(ORANGE, DIGIT3, 3),
      new ColorBandImpl(YELLOW, DIGIT3, 4),
      new ColorBandImpl(GREEN, DIGIT3, 5),
      new ColorBandImpl(BLUE, DIGIT3, 6),
      new ColorBandImpl(VIOLET, DIGIT3, 7),
      new ColorBandImpl(GRAY, DIGIT3, 8),
      new ColorBandImpl(WHITE, DIGIT3, 9),
  };
  private static final ColorBandImpl[] MULTIPLIER_BANDS = {
      new ColorBandImpl(SILVER, MULTIPLIER, -2),
      new ColorBandImpl(GOLD, MULTIPLIER, -1),
      new ColorBandImpl(BLACK, MULTIPLIER, 0),
      new ColorBandImpl(BROWN, MULTIPLIER, 1),
      new ColorBandImpl(RED, MULTIPLIER, 2),
      new ColorBandImpl(ORANGE, MULTIPLIER, 3),
      new ColorBandImpl(YELLOW, MULTIPLIER, 4),
      new ColorBandImpl(GREEN, MULTIPLIER, 5),
      new ColorBandImpl(BLUE, MULTIPLIER, 6),
      new ColorBandImpl(VIOLET, MULTIPLIER, 7),
  };
  private static final ColorBandImpl[] TOLERANCE_LOW_BANDS = {
      new ColorBandImpl(BROWN, TOLERANCE_LOW, 100),
      new ColorBandImpl(RED, TOLERANCE_LOW, 200),
      new ColorBandImpl(GOLD, TOLERANCE_LOW, 500),
      new ColorBandImpl(SILVER, TOLERANCE_LOW, 1000),
  };
  private static final ColorBandImpl[] TOLERANCE_HIGH_BANDS = {
      new ColorBandImpl(GRAY, TOLERANCE_HIGH, 5),
      new ColorBandImpl(VIOLET, TOLERANCE_HIGH, 10),
      new ColorBandImpl(BLUE, TOLERANCE_HIGH, 25),
      new ColorBandImpl(GREEN, TOLERANCE_HIGH, 50),
      new ColorBandImpl(BROWN, TOLERANCE_HIGH, 100),
      new ColorBandImpl(RED, TOLERANCE_HIGH, 200),
      new ColorBandImpl(GOLD, TOLERANCE_HIGH, 500),
      new ColorBandImpl(SILVER, TOLERANCE_HIGH, 1000),
  };
  private static final ColorBandImpl[] TEMPERATURE_BANDS = {
      new ColorBandImpl(BROWN, TEMPERATURE, 100),
      new ColorBandImpl(RED, TEMPERATURE, 50),
      new ColorBandImpl(ORANGE, TEMPERATURE, 15),
      new ColorBandImpl(YELLOW, TEMPERATURE, 25),
      new ColorBandImpl(BLUE, TEMPERATURE, 10),
      new ColorBandImpl(VIOLET, TEMPERATURE, 5),
      new ColorBandImpl(WHITE, TEMPERATURE, 1),
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

  private static final String DEFAULT_TOLERANCE = "20";
  private static final BigDecimal THOUSAND = BigDecimal.ONE.scaleByPowerOfTen(3);
  private static final BigDecimal MILLION = BigDecimal.ONE.scaleByPowerOfTen(6);

  private ColorCode(Context context) {
  }

  /**
   * Get {@link ColorBandImpl} at specific position of resistance code table.
   * <p>
   * For example, on 4 band resistor 3th band codes multiplier and at
   * 2nd position there is gold band with multiplier '0.1'.
   * All positions counts from 0. In order to get gold multiplier band call
   * <code>ColorCode.getColorBand(1, 2, 4)</code>.
   *
   * @param position     position of color
   * @param bandNr       position of band (0-5)
   * @param resistorSize resistor's size (4-6)
   * @return immutable {@link ColorBandImpl} object
   * @throws IllegalArgumentException In case of invalid color code position
   */
  public static ColorBandImpl getColorBand(int position, int bandNr, int resistorSize) {
    ColorBandImpl[] bands = getBandsByNrAndResistorSize(bandNr, resistorSize);
    if (position < 0 || bands.length <= position) {
      throw new IllegalArgumentException("There is no band at  " + position
          + ", bandNr = " + bandNr
          + ", bandCount = " + resistorSize);
    }
    return bands[position];
  }

  /**
   * Get array of possible {@link ColorBandImpl} at specific band number.
   *
   * @param bandNr       band number
   * @param resistorSize resistor's size (4-6)
   * @return array of {@link ColorBandImpl}
   * @throws IllegalArgumentException In case of invalid color code position
   */
  public static ColorBandImpl[] getBandsByNrAndResistorSize(int bandNr, int resistorSize) {
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
   * @param bands Array of {@link ColorBandImpl} objects
   * @return resistance value
   */
  public static BigDecimal decodeResistance(ColorBandImpl[] bands) {
    BigDecimal value;
    int mult;

    if (bands.length == 4) {
      value = new BigDecimal(bands[0].value * 10 + bands[1].value);
      mult = bands[2].value;
      Log.d(TAG, "v = " + (bands[0].value * 10 + bands[1].value));
      Log.d(TAG, "m = " + mult);
    }
    else if (bands.length == 5 || bands.length == 6) {
      value = new BigDecimal(
          bands[0].value * 100 +
              bands[1].value * 10 +
              bands[2].value);
      mult = bands[3].value;
    }
    else {
      throw new IllegalArgumentException("Unsupport bands number " + bands.length);
    }

    return value.scaleByPowerOfTen(mult);
  }

  public static String getPrefferedValue(ColorBandImpl[] bands, Context context) {
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

  public static String getValueText(ColorBandImpl[] bands, Context context) {
    String value = getScaledValue(decodeResistance(bands), context);
    String tolerance = DEFAULT_TOLERANCE;
    for (ColorBandImpl band : bands) {
      if (band.type == TOLERANCE_HIGH || band.type == TOLERANCE_LOW) {
        tolerance = band.getTolerance();
        break;
      }
    }

    Resources res = context.getResources();
    if (bands.length == 6) {
      String temperature = bands[5].value + "\u2009";
      return res.getString(
          R.string.value_text_temperature,
          value,
          tolerance,
          temperature);
    }
    else {
      return res.getString(R.string.value_text, value, tolerance);
    }
  }

  private static ColorBandImpl[] getBandsByType(int type) {
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
        return TEMPERATURE_BANDS;
      default:
        throw new IllegalArgumentException("Invalid band type " + type);
    }
  }
}
