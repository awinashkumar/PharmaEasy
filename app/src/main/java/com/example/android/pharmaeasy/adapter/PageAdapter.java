package com.example.android.pharmaeasy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.pharmaeasy.R;
import com.example.android.pharmaeasy.activities.PageDetailActivity;
import com.example.android.pharmaeasy.model.Page;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Awinash on 17-05-2017.
 */

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {

    private static List<Page> mPageDataset;
    private static Context mContext;
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

    public static class PageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.first_name)
        TextView firstName;

        @BindView(R.id.last_name)
        TextView lastName;

        @BindView(R.id.person_image)
        ImageView personImage;

        public PageViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, PageDetailActivity.class);
            intent.putExtra("FIRSTNAME", mPageDataset.get(getAdapterPosition()).getFirstName());
            intent.putExtra("LASTNAME", mPageDataset.get(getAdapterPosition()).getLastName());
            intent.putExtra("IMAGEURL", mPageDataset.get(getAdapterPosition()).getAvatar());
            mContext.startActivity(intent);
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
