package com.blogspot.karabut.rescal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ResistorFragment extends Fragment {
	public static final String TAG = "ResistorFragment";
	
	public static final int SELECT_REQUEST_CODE = 1337;
	
	public static final String KEY_ID    = "KEY_ID";
	public static final String KEY_BANDS = "KEY_BANDS";
	
	private int[] bands;
	private int id;
	
	private BandListAdapter adapter;
	private TextView valueText;
	private TextView preferedValue;
	private ResistorView resistor;
	
	public ResistorFragment() {
		
	}
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle args = getArguments();
		if (args != null) {
			id = args.getInt(KEY_ID);
			bands = args.getIntArray(KEY_BANDS);
		}
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_resistor, container, false);
		adapter = new BandListAdapter(getActivity());
		for(int i = 0; i < bands.length; ++i) {
			adapter.addBand(ColorCode.getColorBand(bands[i], i, bands.length));
		}
		
		ListView bandList = (ListView) view.findViewById(R.id.colorList);
		bandList.setAdapter(adapter);
		bandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onBandClicked(position);				
			}
		});
		
		preferedValue = (TextView)     view.findViewById(R.id.preferedValue);
		valueText     = (TextView)     view.findViewById(R.id.valueText);
		resistor      = (ResistorView) view.findViewById(R.id.resistor);
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
		if(context == null) {
			Log.e(TAG, "updateTexts(): fragment isn't attached to any activity.");
			return;
		}
		
		ColorBand[] bands = adapter.getBandsArray();
		if (valueText != null) {
			String text = ColorCode.getValueText(bands, getActivity());
			valueText.setText(text);
		}
		if (preferedValue != null) {
			String text = ColorCode.getPrefferedValue(bands, getActivity());
			preferedValue.setText(text);
		}
		if (resistor != null) {
			resistor.setBands(bands);
		}
	}
	
	private void onBandClicked(int position) {
		Intent intent = new Intent(getActivity(), SelectActivity.class);
		intent.putExtra(SelectActivity.EXTRA_RESISTOR_ID,   id);
		intent.putExtra(SelectActivity.EXTRA_RESISTOR_SIZE, bands.length);
		intent.putExtra(SelectActivity.EXTRA_BAND_NUMBER,   position);
		startActivityForResult(intent, SELECT_REQUEST_CODE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SELECT_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
			int resultId = data.getIntExtra(SelectActivity.RESULT_RESISTOR_ID, Integer.MIN_VALUE);
			if (resultId == id) {
				int bandNr   = data.getIntExtra(SelectActivity.RESULT_BAND_NUMBER, 0);
				int position = data.getIntExtra(SelectActivity.RESULT_POSITION, 0);
				
				bands[bandNr] = position;
				adapter.setBand(bandNr, ColorCode.getColorBand(position, bandNr, bands.length));
				updateBands();
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	
}
