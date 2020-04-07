package com.memeful.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.memeful.R;
import com.memeful.model.GalleryResponseModel;

public class MemesAdapter extends RecyclerView.Adapter<MemesAdapter.ViewHolder> {
    private List<GalleryResponseModel> values;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView views, points, description, imageCount;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            views = v.findViewById(R.id.views);
            points = v.findViewById(R.id.points);
            description = v.findViewById(R.id.description);
            imageCount = v.findViewById(R.id.imageCount);
        }
    }

    public MemesAdapter(List<GalleryResponseModel> myDataset, Context context) {
        values = myDataset;
        mContext = context;
    }

    @Override
    public MemesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        View v = inflater.inflate(R.layout.meme_feed, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final GalleryResponseModel url = values.get(position);
        boolean canshow = false;
        if(url.getImages() == null) {
            canshow = true;
            Glide.with(mContext).load(url.getLink()).thumbnail(0.25f).into(holder.image);
        } else if(!url.getImages().isEmpty()) {
            canshow = true;
            Glide.with(mContext).load(url.getImages().get(0).getLink()).thumbnail(0.25f).into(holder.image);
        }

        if(canshow) {
            holder.views.setText(" " + url.getViews());
            holder.points.setText(" " + url.getPoints());
            if(holder.description == null) {
                holder.description.setText("...");
            } else {
                holder.description.setText(" " + url.getDescription());
            }

            if(url.getImages_count() > 1) {
                holder.imageCount.setVisibility(View.VISIBLE);
                holder.imageCount.setText(" " + url.getImages_count());
            } else {
                holder.imageCount.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void addItems(List<GalleryResponseModel> postItems) {
        values.addAll(postItems);
        notifyDataSetChanged();
    }

}