package com.blogspot.karabut.rescal;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.karabut.rescal.colorcode.ColorBand;

public class BandListAdapter extends BaseAdapter {
  private LayoutInflater inflater;
  private ArrayList<ColorBand> bands = new ArrayList<ColorBand>();

  public BandListAdapter(Context context) {
    inflater = LayoutInflater.from(context);
  }

  public BandListAdapter(Context context, Collection<ColorBand> bands) {
    this(context);
    this.bands = new ArrayList<ColorBand>(bands);
  }

  public BandListAdapter(Context context, ColorBand[] bands) {
    this(context);
    for (ColorBand band : bands) {
      this.bands.add(band);
    }
  }

  public void addBand(ColorBand band) {
    bands.add(band);
    notifyDataSetChanged();
  }

  public void setBand(int position, ColorBand band) {
    bands.set(position, band);
    notifyDataSetChanged();
  }

  public ColorBand[] getBandsArray() {
    ColorBand[] result = new ColorBand[bands.size()];
    return bands.toArray(result);
  }

  public ColorBand removeBand(int position) {
    ColorBand band = bands.remove(position);
    notifyDataSetChanged();
    return band;
  }

  public ColorBand getBand(int position) {
    return bands.get(position);
  }

  public int getCount() {
    return bands.size();
  }

  public Object getItem(int position) {
    return getBand(position);
  }


  public long getItemId(int position) {
    return position;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;

    if (convertView == null) {
      convertView = inflater.inflate(R.layout.color_item, null);

      holder = new ViewHolder();
      holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
      holder.itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
      holder.itemIcon = (ImageView) convertView.findViewById(R.id.itemIcon);

      convertView.setTag(holder);
    }
    else {
      holder = (ViewHolder) convertView.getTag();
    }

    Context context = parent.getContext();
    ColorBand band = getBand(position);
    holder.itemTitle.setText(band.getTitle(context));
    holder.itemDescription.setText(band.getDescription(context));
    holder.itemIcon.setImageDrawable(band.getIcon(context));

    return convertView;
  }

  private static class ViewHolder {
    TextView itemTitle;
    TextView itemDescription;
    ImageView itemIcon;
  }
}

