package com.berstek.hcisos.view.help;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.berstek.hcisos.R;
import com.berstek.hcisos.callback.RecviewItemClickCallback;
import com.berstek.hcisos.model.HelpSelection;
import com.berstek.hcisos.utils.Utils;
import com.google.android.flexbox.AlignSelf;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

public class HelpSelectionAdapter extends RecyclerView.Adapter<HelpSelectionAdapter.ListHolder> {

  private Utils utils;
  private ArrayList data;
  private LayoutInflater inflater;
  private RecviewItemClickCallback recviewItemClickCallback;

  public HelpSelectionAdapter(Context context, ArrayList data) {
    this.data = data;
    utils = new Utils(context);
    inflater = LayoutInflater.from(context);
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.viewholder_help_selection,
        parent, false));
  }

  @Override
  public void onBindViewHolder(ListHolder holder, int position) {
    HelpSelection selection = (HelpSelection) data.get(position);

    holder.titleTxt.setText(selection.getTitle());
    utils.loadImage(selection.getImg_url(), holder.img, 80);
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView img;
    private TextView titleTxt;

    public ListHolder(View itemView) {
      super(itemView);

      img = itemView.findViewById(R.id.img);
      titleTxt = itemView.findViewById(R.id.titleTxt);

      ViewGroup.LayoutParams lp = itemView.getLayoutParams();
      if (lp instanceof FlexboxLayoutManager.LayoutParams) {
        FlexboxLayoutManager.LayoutParams f = (FlexboxLayoutManager.LayoutParams) lp;
        f.setFlexGrow(1.0f);
        f.setAlignSelf(AlignSelf.FLEX_END);
      }

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      recviewItemClickCallback.onRecviewItemClick(view, getAdapterPosition());
    }
  }

  public void setRecviewItemClickCallback(RecviewItemClickCallback recviewItemClickCallback) {
    this.recviewItemClickCallback = recviewItemClickCallback;
  }
}
