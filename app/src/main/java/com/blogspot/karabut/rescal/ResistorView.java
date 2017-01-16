package com.blogspot.karabut.rescal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.blogspot.karabut.rescal.model.Color;
import com.blogspot.karabut.rescal.model.Resistor;
import com.blogspot.karabut.rescal.model.Resistors;
import com.blogspot.karabut.utils.BoxBlur;

import java.util.List;

public class ResistorView extends View {
  public final static String TAG = "ResistorView";

  private final static int SMALL = 0;
  private final static int BIG = 1;

  private final static int COLOR_NUM = 12;


  private final static double BAND_WIDTH_4D = 0.8d;
  private final static double[] BANDS_OFFSET_4D = {20d / 176, 55d / 176, 82d / 176, 109d / 176};
  private final static int[] BANDS_TYPE_4 = {BIG, SMALL, SMALL, SMALL};
  private final static double BAND_WIDTH_5D = 0.75d;
  private final static double[] BANDS_OFFSET_5D = {22d / 176, 52d / 176, 73d / 176, 94d / 176, 115d / 176};
  private final static int[] BANDS_TYPE_5 = {BIG, SMALL, SMALL, SMALL, SMALL};
  private final static double BAND_WIDTH_6D = 0.75d;
  private final static double[] BANDS_OFFSET_6D = {22d / 176, 52d / 176, 73d / 176, 94d / 176, 115d / 176, 142d / 176};
  private final static int[] BANDS_TYPE_6 = {BIG, SMALL, SMALL, SMALL, SMALL, BIG};

  private int[] colors = {3, 4, 5, 6};
  private Resistor resistor = Resistors.get(Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE);
  Bitmap cacheBitmap;
  Bitmap bandsBig;
  Bitmap bandsSmall;
  Bitmap body;
  Bitmap bandsBigShadow;
  Bitmap bandsSmallShadow;
  Bitmap bodyShadow;

  public ResistorView(Context context) {
    super(context);
    loadBitmaps(context);

  }

  public ResistorView(Context context, AttributeSet attributes) {
    super(context, attributes);
    loadBitmaps(context);
  }

  public ResistorView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    loadBitmaps(context);
  }

  private void loadBitmaps(Context context) {
    Resources res = context.getResources();
    body = BitmapFactory.decodeResource(res, R.drawable.resistor_body);
    bandsBig = BitmapFactory.decodeResource(res, R.drawable.resistor_bands_big);
    bandsSmall = BitmapFactory.decodeResource(res, R.drawable.resistor_bands_small);
    bodyShadow = BitmapFactory.decodeResource(res, R.drawable.resistor_body_shadow);
    bandsBigShadow = BitmapFactory.decodeResource(res, R.drawable.resistor_bands_big_shadow);
    bandsSmallShadow = BitmapFactory.decodeResource(res, R.drawable.resistor_bands_small_shadow);

    cacheBitmap = body;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = body.getWidth();
    int height = body.getHeight();
    if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
      width = Math.max(MeasureSpec.getSize(widthMeasureSpec), width);
    }
    ;
    if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
      height = Math.max(MeasureSpec.getSize(heightMeasureSpec), height);
    }
    ;
    setMeasuredDimension(width, height);
  }

  public void setResistor(Resistor resistor) {
    if (!this.resistor.equals(resistor)) {
      this.resistor = resistor;
      new Thread(updateCacheRunnable).start();
    }
  }

  private Runnable updateCacheRunnable = new Runnable() {
    @Override
    public void run() {
      Bitmap temp = drawResistor();
      synchronized (cacheBitmap) {
        cacheBitmap = temp;
      }
      postInvalidate();
    }
  };

  public void setBands(ColorBandImpl[] bands) {
    Color[] cs = new Color[bands.length];
    for (int i = 0; i < bands.length; i++) {
      cs[i] = bands[i].color;
    }
    setResistor(Resistors.get(cs));
  }


  @Override
  protected void onDraw(Canvas canvas) {
    synchronized (cacheBitmap) {
      int dx = (getMeasuredWidth() - cacheBitmap.getWidth()) / 2;
      int dy = (getMeasuredHeight() - cacheBitmap.getHeight()) / 2;
      canvas.drawBitmap(cacheBitmap, dx, dy, null);
    }
  }

  private Bitmap drawResistor() {
    // XXX bad style, rewrite me!
    long startTime = System.currentTimeMillis();

    // mBody is immutable bitmap, so we need copy
    Bitmap resistor = body.copy(Bitmap.Config.ARGB_8888, true);
    Canvas resistorCanvas = new Canvas(resistor);
    Bitmap shadow = bodyShadow.copy(Bitmap.Config.ARGB_8888, true);
    Canvas shadowCanvas = new Canvas(shadow);

    // set eraser
    Paint eraser = new Paint();
    eraser.setColor(android.graphics.Color.TRANSPARENT);
    eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

    int bodyWidth = body.getWidth();
    int bodyHeight = body.getHeight();

    Rect src = new Rect();
    Rect dst = new Rect();

    // set top and bottom edges of all bands
    src.top = 0;
    src.bottom = bodyHeight;
    dst.top = 0;
    dst.bottom = bodyHeight;

    // set number of color bands
    double[] offset;
    int[] type;
    int width;

    int sourceWidth = bandsBig.getWidth() / COLOR_NUM;
    List<Color> colors = this.resistor.getColors();
    if (colors.size() == 4) {
      width = (int) Math.round(BAND_WIDTH_4D * sourceWidth);
      offset = BANDS_OFFSET_4D;
      type = BANDS_TYPE_4;
    }
    else if (colors.size() == 5) {
      width = (int) Math.round(BAND_WIDTH_5D * sourceWidth);
      offset = BANDS_OFFSET_5D;
      type = BANDS_TYPE_5;
    }
    else if (colors.size() == 6) {
      width = (int) Math.round(BAND_WIDTH_6D * sourceWidth);
      offset = BANDS_OFFSET_6D;
      type = BANDS_TYPE_6;
    }
    else {
      Log.e(TAG, "Unsupported number of bands");
      return resistor;
    }

    // draw color bands
    for (int i = 0; i < colors.size(); i++) {
      src.left = colors.get(i).ordinal() * sourceWidth;
      src.right = src.left + width;
      dst.left = (int) Math.round(offset[i] * bodyWidth);
      dst.right = dst.left + width;
      resistorCanvas.drawRect(dst, eraser);
      shadowCanvas.drawRect(dst, eraser);
      if (type[i] == BIG) {
        resistorCanvas.drawBitmap(bandsBig, src, dst, null);
        shadowCanvas.drawBitmap(bandsBigShadow, src, dst, null);
      }
      else {
        resistorCanvas.drawBitmap(bandsSmall, src, dst, null);
        shadowCanvas.drawBitmap(bandsSmallShadow, src, dst, null);
      }
    }

    BoxBlur.append(shadow, 4);
    shadowCanvas.drawBitmap(resistor, 0, 0, null);

    long duration = System.currentTimeMillis() - startTime;
    Log.d(TAG, "Resistor drawn. Took " + duration + " ms.");

    return shadow;
  }

}
