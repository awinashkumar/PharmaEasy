package com.example.android.pharmaeasy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.pharmaeasy.R;
import com.example.android.pharmaeasy.model.Page;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Awinash on 17-05-2017.
 */

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {

    private List<Page> mPageDataset;
    private Context mContext;
    private boolean isLoadingAdded = false;


    public PageAdapter(Context context) {
        this.mContext = context;
        mPageDataset = new ArrayList<>();
    }

    @Override
    public PageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_row_item, parent, false);
        PageViewHolder pageViewHolder = new PageViewHolder(view);
        return pageViewHolder;
    }

    @Override
    public void onBindViewHolder(PageViewHolder holder, int position) {

        holder.firstName.setText(mPageDataset.get(position).getFirstName());
        holder.lastName.setText(mPageDataset.get(position).getLastName());
        Picasso.with(mContext).load(mPageDataset.get(position).getAvatar()).into(holder.personImage);
        //Picasso.with(mContext).load(mPageDataset.get(position).getAvatar()).resize(120, 120).into(holder.personImage);

    }

    @Override
    public int getItemCount() {
        return mPageDataset == null ? 0 : mPageDataset.size();
    }

    public static class PageViewHolder extends RecyclerView.ViewHolder{

        private TextView firstName;
        private TextView lastName;
        private ImageView personImage;

        public PageViewHolder(View itemView) {
            super(itemView);
            firstName = (TextView) itemView.findViewById(R.id.first_name);
            lastName = (TextView) itemView.findViewById(R.id.last_name);
            personImage = (ImageView) itemView.findViewById(R.id.person_image);

        }
    }



      /*
   Helper methods
   _________________________________________________________________________________________________
    */

    public void add(Page p) {
        mPageDataset.add(p);
        notifyItemInserted(mPageDataset.size() - 1);
    }

    public void addAll(List<Page> pageResults) {
        for (Page page : pageResults) {
            add(page);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Page());
    }

    public void removeLoadingFooter() {

        isLoadingAdded = false;
        int position = mPageDataset.size() - 1;
        Page result = getItem(position);

        if (result != null) {
            mPageDataset.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Page getItem(int position) {

        return mPageDataset.get(position);
    }


}
