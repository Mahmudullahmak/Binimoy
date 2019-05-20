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
import android.widget.Toast;


import com.example.devilsgod.binimoy.Adapter.LoadLostAdapter;
import com.example.devilsgod.binimoy.Model.ImportLostItemModel;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoadLostItems extends Fragment {

   private FirebaseDatabase database;
   private DatabaseReference rootRef;
  private   DatabaseReference lost_childRef;
    private RecyclerView lost_recyclerView;
   private FirebaseAuth mAuth;
    private String currentUserId;
    private com.github.clans.fab.FloatingActionButton lostfab;
    private FloatingActionMenu lostfabMenu;
     List<ImportLostItemModel>litems=new ArrayList<ImportLostItemModel>();
     public LoadLostAdapter lostAdapter;
     private String iName;
     private String iDesc;
     private String iImage;
     private long phnNo;
    public LoadLostItems() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_load_lost_items, container, false);
        lostfab = view.findViewById(R.id.loadfabadd);
        lostfabMenu = view.findViewById(R.id.loadfabmenu);
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        rootRef=database.getReference("rootDataRef");
        lost_childRef =rootRef.child("LostItemsData");
        lost_recyclerView = view.findViewById(R.id.lostRecyclerVeiwId);
            lostAdapter=new LoadLostAdapter(litems,getActivity().getApplicationContext());

        GridLayoutManager lglm=new GridLayoutManager(getContext(),2);
        lost_recyclerView.setLayoutManager(lglm);
        lost_recyclerView.setAdapter(lostAdapter);
        lrecyclerData();

     lostfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLostItem losttFrag = new AddLostItem();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                        .replace(R.id.lost_fragment_holder, losttFrag, "loostt");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                lostfabMenu.setVisibility(View.GONE);
            }
        });
        return view;
    }

    private void lrecyclerData(){
        litems.clear();
        lost_childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren()) {
                    String currentUserId=d.getKey();
                    lost_childRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dd:dataSnapshot.getChildren()){
                                ImportLostItemModel model=dd.getValue(ImportLostItemModel.class);
                                iName=model.getLostItemName();
                                iDesc=model.getWhereFound();
                                iImage=model.getLostProductImage();
                                phnNo=model.getPhoneNo();
                                litems.add(new ImportLostItemModel(iImage,iName,iDesc,phnNo));
                                lostAdapter.notifyDataSetChanged();

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
