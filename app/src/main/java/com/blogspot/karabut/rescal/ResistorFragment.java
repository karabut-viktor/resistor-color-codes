package com.blogspot.karabut.rescal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.blogspot.karabut.rescal.colorcode.ColorBand;
import com.blogspot.karabut.rescal.colorcode.ColorCode;
import com.blogspot.karabut.rescal.model.Color;
import com.blogspot.karabut.rescal.model.Resistor;
import com.blogspot.karabut.rescal.model.Resistors;

import java.util.ArrayList;

public class ResistorFragment extends Fragment {
  public static final String TAG = "ResistorFragment";

  public static final int SELECT_REQUEST_CODE = 1337;

  public static final String KEY_ID = "KEY_ID";
  public static final String KEY_RESISTOR = "KEY_RESISTOR";

  private int id;
  private Resistor resistor;

  private BandListAdapter adapter;
  private TextView valueText;
  private TextView preferedValue;
  private ResistorView resistorView;

  public ResistorFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle args = getArguments();
    if (args != null) {
      id = args.getInt(KEY_ID);
      resistor = (Resistor) args.getSerializable(KEY_RESISTOR);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_resistor, container, false);
    adapter = new BandListAdapter(getActivity());

    for (int i = 0; i < resistor.size(); ++i) {
      adapter.addBand(ColorCode.getColorBand(resistor.get(i), i, resistor.size()));
    }

    ListView bandList = (ListView) view.findViewById(R.id.colorList);
    bandList.setAdapter(adapter);
    bandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onBandClicked(position);
      }
    });

    preferedValue = (TextView) view.findViewById(R.id.preferedValue);
    valueText = (TextView) view.findViewById(R.id.valueText);
    resistorView = (ResistorView) view.findViewById(R.id.resistor);
    updateBands();

    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    adapter = null;
    valueText = null;
    preferedValue = null;
  }

  private void updateBands() {
    Context context = getActivity();
    if (context == null) {
      Log.e(TAG, "updateTexts(): fragment isn't attached to any activity.");
      return;
    }

    ColorBand[] bands = adapter.getBandsArray();
    if (valueText != null) {
      String text = ColorCode.getValueText(bands, getActivity());
      valueText.setText(text);
    }
    if (preferedValue != null) {
      String text = ColorCode.getPreferredValue(bands, getActivity());
      preferedValue.setText(text);
    }
    if (resistorView != null) {
      resistorView.setResistor(resistor);
    }
  }

  private void onBandClicked(int position) {
    Intent intent = new Intent(getActivity(), SelectActivity.class);
    intent.putExtra(SelectActivity.EXTRA_RESISTOR_ID, id);
    intent.putExtra(SelectActivity.EXTRA_RESISTOR_SIZE, resistor.size());
    intent.putExtra(SelectActivity.EXTRA_BAND_NUMBER, position);
    startActivityForResult(intent, SELECT_REQUEST_CODE);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == SELECT_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
      int resultId = data.getIntExtra(SelectActivity.RESULT_RESISTOR_ID, Integer.MIN_VALUE);
      if (resultId == id) {
        int bandNr = data.getIntExtra(SelectActivity.RESULT_BAND_NUMBER, 0);
        int position = data.getIntExtra(SelectActivity.RESULT_POSITION, 0);

        ArrayList<Color> colors = new ArrayList<Color>(resistor);
        ColorBand band = ColorCode.getColorBand(position, bandNr, colors.size());
        colors.set(bandNr, band.getColor());
        resistor = Resistors.get(colors);
        adapter.setBand(bandNr, band);
        updateBands();
      }
    }
    else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }


}
