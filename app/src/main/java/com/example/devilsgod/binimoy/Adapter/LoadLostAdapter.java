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
import android.widget.Toast;

import com.example.devilsgod.binimoy.LostItemDetails;
import com.example.devilsgod.binimoy.Model.ImportLostItemModel;
import com.example.devilsgod.binimoy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoadLostAdapter  extends RecyclerView.Adapter<LoadLostAdapter.LoadLostItemViewHolder>{

    public List<ImportLostItemModel> lostRecyclerList;
    Context context;
    private LinearLayout linearLayout;


    public class LoadLostItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName,itemDesc;
        ImageView itemImage;


        public LoadLostItemViewHolder(View view) {
            super(view);
            itemName=view.findViewById(R.id.lostitemNameIdTV);
            itemDesc=view.findViewById(R.id.lostItemDescriptionCardViewId);
            itemImage=view.findViewById(R.id.lostItemImageCardId);
            linearLayout=view.findViewById(R.id.lostLinearId);

        }
    }
    public LoadLostAdapter(List<ImportLostItemModel> lostRecyclerList, Context context) {
        this.lostRecyclerList = lostRecyclerList;
        this.context = context;
    }
    @Override
    public LoadLostItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lost_single_row, parent, false);

        return new LoadLostItemViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(LoadLostItemViewHolder holder, final int position) {
        ImportLostItemModel recycleList = lostRecyclerList.get(position);
        holder.itemName.setText(recycleList.getLostItemName());
        holder.itemDesc.setText(recycleList.getWhereFound());
        Picasso.get().load(recycleList.getLostProductImage()).into(holder.itemImage);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportLostItemModel recycleList = lostRecyclerList.get(position);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                LostItemDetails lFrag = new LostItemDetails();
                Bundle argss=new Bundle();
                argss.putString("name",recycleList.getLostItemName());
                argss.putString("desc",recycleList.getWhereFound());
                argss.putString("image",recycleList.getLostProductImage());
                argss.putLong("phhn",recycleList.getPhoneNo());
                lFrag.setArguments(argss);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.lost_fragment_holder, lFrag).addToBackStack(null).commit();
            }
        });


    }
    @Override
    public int getItemCount() {
        return lostRecyclerList.size();
    }

}