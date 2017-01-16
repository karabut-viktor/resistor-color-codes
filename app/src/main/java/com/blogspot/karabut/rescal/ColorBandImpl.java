package com.blogspot.karabut.rescal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.blogspot.karabut.rescal.model.Color;

public class ColorBandImpl implements ColorBand {
	public final Color color;
	public final int type;
	public final int value;
	
	public ColorBandImpl(Color color, int type, int value) {
		// TODO additional checks
		this.color = color;
		this.type = type;
		this.value = value;
	}
	
	@Override
	public String getTitle(Context context) {
		Resources res = context.getResources();
		return res.getStringArray(R.array.color_titles)[color.ordinal()];
	}
	
	@Override
	public String getDescription(Context context) {
		Resources res = context.getResources();
		
		switch (type) {
		case ColorCode.DIGIT1:
			return res.getString(R.string.description_digit_first, String.valueOf(value));
		case ColorCode.DIGIT2:
			return res.getString(R.string.description_digit_second, String.valueOf(value));
		case ColorCode.DIGIT3:
			return res.getString(R.string.description_digit_third, String.valueOf(value));
		case ColorCode.MULTIPLIER:
			if (value == 0) {
				return res.getString(R.string.description_multiplier_without);
			} else {
				String kilo = res.getString(R.string.prefix_kilo);
				String mega = res.getString(R.string.prefix_mega);
				return res.getString(
						R.string.description_multiplier,
						getMultiplier(value, kilo, mega));
			}
		case ColorCode.TOLERANCE_LOW:
		case ColorCode.TOLERANCE_HIGH:
			return res.getString(R.string.description_tolerance, getTolerance());
		case ColorCode.TEMPERATURE:
			return res.getString(R.string.description_temperature, String.valueOf(value));
		default:
			return null;
		}
	}
	
	@Override
	public Drawable getIcon(Context context) {
		Resources res = context.getResources();
		return res.obtainTypedArray(R.array.color_icons).getDrawable(color.ordinal());
	}
	
	private String getTolerance() {
		// TODO check		
		String tolerance = String.format("%.2f", 0.01*value);
		return tolerance.replaceAll("[,\\.]?0+$", "");
	}
	
	private static String getMultiplier(int value, String kilo, String mega) {
		switch (value) {
		case -2:
			return "0.01";
		case -1:
			return "0.1";
		case 0:
			return "1";
		case 1:
			return "10";
		case 2:
			return "100";
		case 3:
		case 4:
		case 5:
			return getMultiplier(value - 3, kilo, mega) + kilo;
		case 6:
		case 7:
		case 8:
			return getMultiplier(value - 6, kilo, mega) + mega;
		default:
			return null;
		}
	}

	public Color getColor() {
		return color;
	}
}
