package com.project.wissemcomp.veloapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.project.wissemcomp.veloapp.Utility;
import com.project.wissemcomp.veloapp.fragment.bikeListFragment.RecyclerBikeItemClickListener;
import com.project.wissemcomp.veloapp.R;
import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.model.Picture;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jhordan on 13/10/15.
 */
public class AdapterVelo extends RecyclerView.Adapter<AdapterVelo.BikeItemHolder> {


    private ArrayList<Bike> bikesList;
    private int itemLayout;
    private RecyclerBikeItemClickListener recyclerBikeItemClickListener;
    private Context mCtx;

    public void setRecyclerBikeItemClickListener(RecyclerBikeItemClickListener recyclerBikeItemClickListener) {
        this.recyclerBikeItemClickListener = recyclerBikeItemClickListener;
    }

    public AdapterVelo() {}

    public AdapterVelo(ArrayList<Bike> bikesList, int itemLayout) {
        this.bikesList = bikesList;
        this.itemLayout = itemLayout;

    }

    @Override
    public BikeItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mCtx= parent.getContext();
        View view = LayoutInflater.from(mCtx).inflate(itemLayout, parent, false);
        return new BikeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final BikeItemHolder holder, final int position) {
        final Bike bike = bikesList.get(position);
        holder.title.setText(bike.getName());
        holder.price.setText(bike.getPrice()+"€");
        Utility.getImageFromGallery(mCtx, Uri.parse(bike.getImage()),holder.imageView);

    }


    @Override
    public int getItemCount() {
        return bikesList.size();
    }


    public class BikeItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_title)
        TextView title;
        @BindView(R.id.txt_price)
        TextView price;
        @BindView(R.id.imageView)
        ImageView imageView;

        public BikeItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (recyclerBikeItemClickListener != null)
                recyclerBikeItemClickListener.onBikeItemClickListener(bikesList.get(getAdapterPosition()).getId());
        }
    }


}
