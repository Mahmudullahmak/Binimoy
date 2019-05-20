package com.example.devilsgod.binimoy;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devilsgod.binimoy.Model.ImportExchangeItemModel;
import com.example.devilsgod.binimoy.Model.ImportLostItemModel;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class BinimoyFragment extends Fragment {
private RecyclerView shareRecycler,exchangeRecycler,foundRecycler;
    FirebaseDatabase database;
    DatabaseReference rootRef;
    DatabaseReference childRef;
    FirebaseAuth mAuth;
    LinearLayout binimoyUi;
    private String currentUserId;
    DatabaseReference ex_childRef;
    private   DatabaseReference lost_childRef;
    private com.github.clans.fab.FloatingActionButton exFab,shareFab,foundFab;
    private FloatingActionMenu fabMenu;
    private String noPost="You have no post";

    public BinimoyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_binimoy, container, false);
       binimoyUi=view.findViewById(R.id.binimoyLayoutid);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid().toString();
        database=FirebaseDatabase.getInstance();
        rootRef=database.getReference("rootDataRef");
        childRef =rootRef.child("ShareItemsData").child(currentUserId);
        ex_childRef =rootRef.child("ExchangeItemsData").child(currentUserId);
        lost_childRef =rootRef.child("LostItemsData").child(currentUserId);

        shareRecycler=view.findViewById(R.id.home_shareRecyclerId);
               exchangeRecycler=view.findViewById(R.id.home_exchangeRecyclerId);
               foundRecycler=view.findViewById(R.id.home_foundRecyclerId);
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        shareRecycler.setLayoutManager(llm);
        LinearLayoutManager exllm=new LinearLayoutManager(getContext());
        exllm.setOrientation(LinearLayoutManager.HORIZONTAL);
        exchangeRecycler.setLayoutManager(exllm);
        LinearLayoutManager foundllm=new LinearLayoutManager(getContext());
        foundllm.setOrientation(LinearLayoutManager.HORIZONTAL);
        foundRecycler.setLayoutManager(foundllm);

       return view;
    }

 @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<ImportShareItemModel> options=
                new FirebaseRecyclerOptions.Builder<ImportShareItemModel>()
                        .setQuery(childRef,ImportShareItemModel.class)
                        .build();
        FirebaseRecyclerAdapter<ImportShareItemModel,ItemViewHolder> adapter
                =new FirebaseRecyclerAdapter<ImportShareItemModel,ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ItemViewHolder holder, int position, @NonNull ImportShareItemModel model) {

                final String itemName =model.getItemName();
                final String itemDesc =model.getItemDescription();
                final String itemImg =model.getmImageUrl();
                                      holder.itemName.setText(itemName);
                                      holder.itemDesc.setText(itemDesc);
                                      Picasso.get().load(itemImg).into(holder.itemImage);

                                      holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                                          @Override
                                          public boolean onLongClick(View v) {
                                              final CharSequence[] items = {"Delete"};

                                              AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                              builder.setItems(items, new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(DialogInterface dialog, int which) {
                                                      if (which == 0) {
                                                          childRef.child(getRef(which).getKey()).removeValue();
                                                          Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                                                      }
                                                  }
                                              });
                                              builder.show();
                                              return true;
                                          }
                                      });

            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.share_item_single_row,parent,false);
               ItemViewHolder holder=new ItemViewHolder(v);
                return holder;
            }
        };

        shareRecycler.setAdapter(adapter);
        adapter.startListening();
        // Exchange Load
        FirebaseRecyclerOptions<ImportExchangeItemModel>exOptions=
                new FirebaseRecyclerOptions.Builder<ImportExchangeItemModel>()
                        .setQuery(ex_childRef,ImportExchangeItemModel.class)
                        .build();
        FirebaseRecyclerAdapter<ImportExchangeItemModel,ExchangeItemViewHolder>addapter=
                new FirebaseRecyclerAdapter<ImportExchangeItemModel,ExchangeItemViewHolder>(exOptions) {
                    @NonNull
                    @Override
                    public ExchangeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.exchange_single_row,parent,false);
                       ExchangeItemViewHolder holder=new ExchangeItemViewHolder(v);
                        return holder;
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull final ExchangeItemViewHolder holder, final int position, @NonNull ImportExchangeItemModel model) {
                        final String userIds=getRef(position).getKey();



                        final String exitemName=model.getProductName();
                        final String exitemDesc =model.getProductDesc();
                        final String exitemImg =model.getImageUrl();

                                          holder.exitemName.setText(exitemName);
                                          holder.exitemDesc.setText(exitemDesc);
                                          Picasso.get().load(exitemImg).into(holder.exitemImage);
                                          holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                                              @Override
                                              public boolean onLongClick(View v) {
                                                  final CharSequence[] items = {"Delete"};

                                                  AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                                  builder.setItems(items, new DialogInterface.OnClickListener() {
                                                      @Override
                                                      public void onClick(DialogInterface dialog, int which) {
                                                          ex_childRef.child(getRef(which).getKey()).removeValue();
                                                          Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                                                      }
                                                  });
                                                  builder.show();
                                                  return true;
                                              }
                                          });
                                      }



                };
        exchangeRecycler.setAdapter(addapter);
        addapter.startListening();

//Lost Items loading
      FirebaseRecyclerOptions<ImportLostItemModel> lostOptions=
                new FirebaseRecyclerOptions.Builder<ImportLostItemModel>()
                        .setQuery(lost_childRef,ImportLostItemModel.class)
                        .build();
        FirebaseRecyclerAdapter<ImportLostItemModel,LostItemViewHolder> lostaddapter=
                new FirebaseRecyclerAdapter<ImportLostItemModel, LostItemViewHolder>(lostOptions) {
                    @NonNull
                    @Override
                    public LostItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.lost_single_row,parent,false);
                        LostItemViewHolder holder=new LostItemViewHolder(v);
                        return holder;            }

                    @Override
                    protected void onBindViewHolder(@NonNull final LostItemViewHolder holder, int position, @NonNull ImportLostItemModel model) {
                        final String userIds=getRef(position).getKey();

                        String lItemName =model.getLostItemName();
                        String lWhereFound =model.getWhereFound();
                        String lostImage =model.getLostProductImage();
                                    holder.lostItemName.setText(lItemName);
                                    holder.whereFound.setText(lWhereFound);
                                    Picasso.get().load(lostImage).into(holder.lostItemImage);
                                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                                        @Override
                                        public boolean onLongClick(View v) {
                                            final CharSequence[] items = {"Delete"};

                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                            builder.setItems(items, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    lost_childRef.child(getRef(which).getKey()).removeValue();
                                                    Toast.makeText(getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            builder.show();
                                            return true;
                                        }
                                    });

                    }
                };
        foundRecycler.setAdapter(lostaddapter);
        lostaddapter.startListening();
    }
   public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemDesc;
        ImageView itemImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.itemNameIdTV);
            itemDesc=itemView.findViewById(R.id.itemDescriptionCardViewId);
            itemImage=itemView.findViewById(R.id.shareItemImageCardId);
        }
    }
    public static class ExchangeItemViewHolder extends RecyclerView.ViewHolder{
        TextView exitemName,exitemDesc;
        ImageView exitemImage;
        public ExchangeItemViewHolder(View itemView) {
            super(itemView);
            exitemName=itemView.findViewById(R.id.exchangeitemNameIdTV);
            exitemDesc=itemView.findViewById(R.id.exchangeitemDescriptionCardViewId);
            exitemImage=itemView.findViewById(R.id.exchangeItemImageCardId);
        }
    }
    public static class LostItemViewHolder extends RecyclerView.ViewHolder{
        TextView lostItemName,whereFound;
        ImageView lostItemImage;
        public LostItemViewHolder(View itemView) {
            super(itemView);
            lostItemName=itemView.findViewById(R.id.lostitemNameIdTV);
            whereFound=itemView.findViewById(R.id.lostItemDescriptionCardViewId);
            lostItemImage=itemView.findViewById(R.id.lostItemImageCardId);
        }
    }

}
