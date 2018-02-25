package com.project.wissemcomp.veloapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.wissemcomp.veloapp.fragment.pieceListFragment.RecyclerPieceItemClickListener;
import com.project.wissemcomp.veloapp.R;
import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.model.Picture;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jhordan on 13/10/15.
 */
public class AdapterPiece extends RecyclerView.Adapter<AdapterPiece.PieceItemHolder> {


    private ArrayList<Bike> pictureArrayList;
    private int itemLayout;
    private RecyclerPieceItemClickListener recyclerItemClickListener;

    public void setRecyclerPieceItemClickListener(RecyclerPieceItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public AdapterPiece() {
    }

    public AdapterPiece(ArrayList<Bike> pictureArrayList, int itemLayout) {
        this.pictureArrayList = pictureArrayList;
        this.itemLayout = itemLayout;

    }

    @Override
    public PieceItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new PieceItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final PieceItemHolder holder, final int position) {
        final Bike picture = pictureArrayList.get(position);
        holder.title.setText(picture.getName());
        //holder.imageView.setImageResource(picture.getImage());
    }


    @Override
    public int getItemCount() {
        return pictureArrayList.size();
    }


    public class PieceItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_title_piece)
        TextView title;
        @BindView(R.id.imageView_piece)
        ImageView imageView;

        public PieceItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (recyclerItemClickListener != null)
                recyclerItemClickListener.onPieceItemClickListener(getAdapterPosition());
        }
    }


}
