package com.example.devilsgod.binimoy;


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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devilsgod.binimoy.Adapter.LoadShareAdapter;
import com.example.devilsgod.binimoy.Model.ImportShareItemModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoadShareItems extends Fragment {
    FirebaseDatabase database;
    DatabaseReference rootRef;
    DatabaseReference childRef;
    private RecyclerView recyclerView;
    FirebaseAuth mAuth;

    private com.github.clans.fab.FloatingActionButton fab;
    private FloatingActionMenu fabMenu;
    List<ImportShareItemModel> sitems = new ArrayList<>();
    private String iName;
    private String iDesc;
    private String iImage;
    private long phn;
public LoadShareAdapter shareAdapter;
    public LoadShareItems() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_load_share_items, container, false);
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        rootRef=database.getReference("rootDataRef");
        childRef =rootRef.child("ShareItemsData");
        recyclerView = view.findViewById(R.id.my_recycler_view);
           shareAdapter=new LoadShareAdapter(sitems,getActivity().getApplicationContext());
        GridLayoutManager glm=new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(shareAdapter);
        srecyclerData();
        fab = view.findViewById(R.id.load_share_fab_addId);
        fabMenu = view.findViewById(R.id.load_shareItemfeb_menuId);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSharedItemsFragment newFragment = new AddSharedItemsFragment();
               FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .replace(R.id.share_fragment_holder, newFragment, "tag");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fabMenu.setVisibility(View.GONE);
            }
        });
        return view;
    }
    private void srecyclerData(){
        sitems.clear();
        childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()) {
                    String currentUserId=d.getKey();
                    childRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dd:dataSnapshot.getChildren()){
                                ImportShareItemModel model=dd.getValue(ImportShareItemModel.class);
                                iName=model.getItemName();
                                iDesc=model.getItemDescription();
                                iImage=model.getmImageUrl();
                                phn=model.getPhoneNo();
                                sitems.add(new ImportShareItemModel(iName,iDesc,iImage,phn));
                                shareAdapter.notifyDataSetChanged();

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
