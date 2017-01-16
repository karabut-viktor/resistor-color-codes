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

public class BandListAdapter extends BaseAdapter {
  private LayoutInflater inflater;
  private ArrayList<ColorBandImpl> bands = new ArrayList<ColorBandImpl>();

  public BandListAdapter(Context context) {
    inflater = LayoutInflater.from(context);
  }

  public BandListAdapter(Context context, Collection<ColorBandImpl> bands) {
    this(context);
    this.bands = new ArrayList<ColorBandImpl>(bands);
  }

  public BandListAdapter(Context context, ColorBandImpl[] bands) {
    this(context);
    for (ColorBandImpl band : bands) {
      this.bands.add(band);
    }
  }

  public void addBand(ColorBandImpl band) {
    bands.add(band);
    notifyDataSetChanged();
  }

  public void setBand(int position, ColorBandImpl band) {
    bands.set(position, band);
    notifyDataSetChanged();
  }

  public ColorBandImpl[] getBandsArray() {
    ColorBandImpl[] result = new ColorBandImpl[bands.size()];
    return bands.toArray(result);
  }

  public ColorBandImpl removeBand(int position) {
    ColorBandImpl band = bands.remove(position);
    notifyDataSetChanged();
    return band;
  }

  public ColorBandImpl getBand(int position) {
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
    ColorBandImpl band = getBand(position);
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

