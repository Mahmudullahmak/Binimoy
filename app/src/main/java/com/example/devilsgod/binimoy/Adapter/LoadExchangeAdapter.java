package com.example.devilsgod.binimoy.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devilsgod.binimoy.ExchangeItemsFragment;
import com.example.devilsgod.binimoy.Model.ImportExchangeItemModel;
import com.example.devilsgod.binimoy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoadExchangeAdapter  extends RecyclerView.Adapter<LoadExchangeAdapter.LoadExchangeItemViewHolder>{

    public List<ImportExchangeItemModel> mainRecycle_lists;
    Context context;
    private LinearLayout linearLayout;


    public class LoadExchangeItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName,itemDesc;
        ImageView itemImage;


        public LoadExchangeItemViewHolder(View view) {
            super(view);
           itemName=view.findViewById(R.id.exchangeitemNameIdTV);
           itemDesc=view.findViewById(R.id.exchangeitemDescriptionCardViewId);
           itemImage=view.findViewById(R.id.exchangeItemImageCardId);
           linearLayout=view.findViewById(R.id.linearId);

        }
    }
    public LoadExchangeAdapter(List<ImportExchangeItemModel> mainRecycle_lists, Context context) {
        this.mainRecycle_lists = mainRecycle_lists;
        this.context = context;

    }
    @Override
    public LoadExchangeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exchange_single_row, parent, false);

        return new LoadExchangeItemViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(LoadExchangeItemViewHolder holder, final int position) {
        ImportExchangeItemModel recycleList = mainRecycle_lists.get(position);
        holder.itemName.setText(recycleList.getProductName());
        holder.itemDesc.setText(recycleList.getProductDesc());
        Picasso.get().load(recycleList.getImageUrl()).into(holder.itemImage);
          linearLayout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  ImportExchangeItemModel recycleList = mainRecycle_lists.get(position);
                  AppCompatActivity activity = (AppCompatActivity) v.getContext();
                  ExchangeItemsFragment myFragment = new ExchangeItemsFragment();
                  Bundle argss=new Bundle();
                  argss.putString("name",recycleList.getProductName());
                  argss.putString("desc",recycleList.getProductDesc());
                  argss.putString("image",recycleList.getImageUrl());
                  argss.putString("exproductName",recycleList.getExchangeProductName());
                   argss.putLong("phhn",recycleList.getPhoneNo());
                  myFragment.setArguments(argss);
                  activity.getSupportFragmentManager().beginTransaction().replace(R.id.exchange_fragment_holderId, myFragment).addToBackStack(null).commit();
              }
          });

    }
    @Override
    public int getItemCount() {
        return mainRecycle_lists.size();
    }

}
