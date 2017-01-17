package com.blogspot.karabut.rescal;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.blogspot.karabut.rescal.colorcode.ColorBand;
import com.blogspot.karabut.rescal.colorcode.ColorCode;

public class SelectActivity extends ListActivity {
  private static final String TAG = "SelectActivity";

  public static final String EXTRA_RESISTOR_SIZE = "EXTRA_RESISTOR_SIZE";
  public static final String EXTRA_BAND_NUMBER = "EXTRA_BAND_NUMBER";
  public static final String EXTRA_RESISTOR_ID = "EXTRA_RESISTOR_ID";

  public static final String RESULT_RESISTOR_SIZE = "RESULT_RESISTOR_SIZE";
  public static final String RESULT_RESISTOR_ID = "RESULT_RESISTOR_ID";
  public static final String RESULT_BAND_NUMBER = "RESULT_BAND_NUMBER";
  public static final String RESULT_POSITION = "RESULT_POSITION";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select);
    getActionBar().setDisplayHomeAsUpEnabled(true);
    int resistorSize = getIntent().getIntExtra(EXTRA_RESISTOR_SIZE, 0);
    int bandNumber = getIntent().getIntExtra(EXTRA_BAND_NUMBER, 0);
    ColorBand[] bands = ColorCode.getBandsByNrAndResistorSize(bandNumber, resistorSize);
    setListAdapter(new BandListAdapter(this, bands));
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    int resistorSize = getIntent().getIntExtra(EXTRA_RESISTOR_SIZE, 0);
    int bandNumber = getIntent().getIntExtra(EXTRA_BAND_NUMBER, 0);
    int resistorId = getIntent().getIntExtra(EXTRA_RESISTOR_ID, 0);

    Intent data = new Intent();
    data.putExtra(RESULT_POSITION, position);
    data.putExtra(RESULT_RESISTOR_SIZE, resistorSize);
    data.putExtra(RESULT_BAND_NUMBER, bandNumber);
    data.putExtra(RESULT_RESISTOR_ID, resistorId);
    setResult(RESULT_OK, data);
    finish();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        //NavUtils.navigateUpFromSameTask(this);
        return true;
      default:
        Log.w(TAG, "onOptionsItemSelected: id = " + item.getItemId());
    }
    return super.onOptionsItemSelected(item);
  }
}
