package com.berstek.hcisos.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Utils {

  private Context context;

  public Utils(Context context) {
    this.context = context;
  }

  public void loadImage(String url, ImageView img) {
    Glide.with(context).load(url).skipMemoryCache(true).into(img);
  }

  public void loadImage(String url, ImageView img, int size) {
    Glide.with(context).load(url).skipMemoryCache(true).override(size, size).into(img);
  }
}
