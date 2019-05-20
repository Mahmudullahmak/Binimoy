package com.example.devilsgod.binimoy.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.devilsgod.binimoy.Model.ImportShareItemModel;
import com.example.devilsgod.binimoy.R;
import com.example.devilsgod.binimoy.ShareItemDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoadShareAdapter  extends RecyclerView.Adapter<LoadShareAdapter.LoadShareItemViewHolder>{

    public List<ImportShareItemModel> shareRecyclerList;
    Context context;
    private LinearLayout linearLayout;


    public class LoadShareItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName,itemDesc;
        ImageView itemImage;


        public LoadShareItemViewHolder(View view) {
            super(view);
            itemName=view.findViewById(R.id.itemNameIdTV);
            itemDesc=view.findViewById(R.id.itemDescriptionCardViewId);
            itemImage=view.findViewById(R.id.shareItemImageCardId);
            linearLayout=view.findViewById(R.id.shareLinearId);


        }
    }
    public LoadShareAdapter(List<ImportShareItemModel> shareRecyclerList, Context context) {
        this.shareRecyclerList = shareRecyclerList;
        this.context = context;
    }
    @Override
    public LoadShareAdapter.LoadShareItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.share_item_single_row, parent, false);

        return new LoadShareAdapter.LoadShareItemViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(LoadShareAdapter.LoadShareItemViewHolder holder, final int position) {
        ImportShareItemModel recycleList = shareRecyclerList.get(position);
        holder.itemName.setText(recycleList.getItemName());
        holder.itemDesc.setText(recycleList.getItemDescription());
        Picasso.get().load(recycleList.getmImageUrl()).into(holder.itemImage);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportShareItemModel recycleList = shareRecyclerList.get(position);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                ShareItemDetails sFrag = new ShareItemDetails();
                Bundle argss=new Bundle();
                argss.putString("name",recycleList.getItemName());
                argss.putString("desc",recycleList.getItemDescription());
                argss.putString("image",recycleList.getmImageUrl());
                argss.putLong("phn",recycleList.getPhoneNo());
                sFrag.setArguments(argss);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.share_fragment_holder, sFrag).addToBackStack(null).commit();
            }
        });


    }
    @Override
    public int getItemCount() {
        return shareRecyclerList.size();
    }

}
