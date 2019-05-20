package com.example.devilsgod.binimoy;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.devilsgod.binimoy.Adapter.LoadExchangeAdapter;
import com.example.devilsgod.binimoy.Model.ImportExchangeItemModel;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoadExchangeItems extends Fragment {

    FirebaseDatabase database;
    DatabaseReference rootRef;
    DatabaseReference ex_childRef;
    DatabaseReference ex;
    private RecyclerView exrecyclerView;
    FirebaseAuth mAuth;
    public LoadExchangeAdapter adapter;
   private List<ImportExchangeItemModel> items = new ArrayList<ImportExchangeItemModel>();

    private com.github.clans.fab.FloatingActionButton fab;
    private FloatingActionMenu fabMenu;
private String iName;
private String iDesc;
private String iImage;
private long phn;
private String exProductName;
private Context context;

    public LoadExchangeItems() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_load_exchange_items, container, false);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference("rootDataRef");
        ex_childRef = rootRef.child("ExchangeItemsData");


        exrecyclerView = view.findViewById(R.id.exchangeRecyclerVeiwId);
        adapter=new LoadExchangeAdapter(items,getActivity().getApplicationContext());
        GridLayoutManager glm = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        exrecyclerView.setLayoutManager(glm);
        exrecyclerView.setAdapter(adapter);
        recyclerData();

        fab = view.findViewById(R.id.load_exchange_fab_addId);
        fabMenu = view.findViewById(R.id.load_exchange_menuId);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddExchangeItemFragment exFrag = new AddExchangeItemFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .replace(R.id.exchange_fragment_holderId, exFrag, "exc");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fabMenu.setVisibility(View.GONE);
            }
        });


        return view;
    }

private void recyclerData(){
        items.clear();
    ex_childRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot d:dataSnapshot.getChildren()) {
                  String currentUserId=d.getKey();
                  ex_childRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          for (DataSnapshot dd:dataSnapshot.getChildren()){
                              String userIds=dd.getKey();
                              ImportExchangeItemModel model=dd.getValue(ImportExchangeItemModel.class);
                                 iName=model.getProductName();
                                 iDesc=model.getProductDesc();
                                 iImage=model.getImageUrl();
                                 exProductName=model.getExchangeProductName();
                                 phn=model.getPhoneNo();
                                 items.add(new ImportExchangeItemModel(iImage,iName,iDesc,exProductName,phn));
                                 adapter.notifyDataSetChanged();

                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}
}
