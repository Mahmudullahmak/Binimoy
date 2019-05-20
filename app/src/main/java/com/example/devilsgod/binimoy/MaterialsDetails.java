package com.example.devilsgod.binimoy;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.devilsgod.binimoy.Model.ImportBooksModel;
import com.example.devilsgod.binimoy.Model.ImportNotesModel;
import com.example.devilsgod.binimoy.Model.ImportSlidesModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;



/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialsDetails extends Fragment {
    FirebaseDatabase database;
    DatabaseReference rootRef;
    DatabaseReference childRef;
    DatabaseReference cseRef;
    DatabaseReference semesterRef;
    DatabaseReference bookRef;
    DatabaseReference handNoteRef;
    DatabaseReference slideRef;
    private String urll;
    private String bookurl;
 private String noteurl;
  private   DatabaseReference dataRef;
    private RecyclerView bookRecycler,slideRecycler,handNoteRecycler;
    FirebaseAuth mAuth;
    private String currentUserId;
    private HashMap<String,String> map;


    public MaterialsDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_materials_details, container, false);
        String dept = getArguments().getString("dept");
        String deptt=dept;
        String semester=getArguments().getString("semester");


        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance();
        rootRef=database.getReference("rootDataRef");
        childRef =rootRef.child("StudyMaterials");
        cseRef=childRef.child(dept);
        if (dept.equals(deptt)&&semester.equals("2nd Semester")){
            dataRef=cseRef.child("SecondSemester");

        }
        else if (dept.equals(deptt)&&semester.equals("1st Semester")){
            dataRef=cseRef.child("FirstSemester");
        }
          else if (dept.equals("CSE")&&semester.equals("3rd Semester")){
            dataRef=cseRef.child("ThirdSemester");
        }
          else if (dept.equals("CSE")&&semester.equals("4th Semester")){
            dataRef=cseRef.child("FourthSemester");
        }
          else if (dept.equals("CSE")&&semester.equals("5th Semester")){
            dataRef=cseRef.child("FifthSemester");
        }
          else if (dept.equals("CSE")&&semester.equals("6th Semester")){
            dataRef=cseRef.child("SixthSemester");
        }
          else if (dept.equals("CSE")&&semester.equals("7th Semester")){
            dataRef=cseRef.child("SeventhSemester");
        }
          else if (dept.equals("CSE")&&semester.equals("8th Semester")){
            dataRef=cseRef.child("EighthSemester");
        }
          else if (dept.equals("CSE")&&semester.equals("9th Semester")){
            dataRef=cseRef.child("NinthSemester");
        }
          else if (dept.equals("CSE")&&semester.equals("10th Semester")){
            dataRef=cseRef.child("TenthSemester");
        }
          else if (dept.equals("CSE")&&semester.equals("11th Semester")){
            dataRef=cseRef.child("EleventhSemester");
        }
          else if (dept.equals("CSE")&&semester.equals("12th Semester")){
            dataRef=cseRef.child("TwelvethSemester");
        }


        bookRef=dataRef.child("Books");
        handNoteRef=dataRef.child("HandNotes");
        slideRef=dataRef.child("Slides");

        bookRecycler = view.findViewById(R.id.booksRecyclerViewId);
        slideRecycler=view.findViewById(R.id.slidesRecyclerViewId);
        handNoteRecycler=view.findViewById(R.id.handNotesRecyclerViewId);


        GridLayoutManager glm=new GridLayoutManager(getContext(),2);
        bookRecycler.setLayoutManager(glm);
        GridLayoutManager handGlm=new GridLayoutManager(getContext(),2);
        handNoteRecycler.setLayoutManager(handGlm);
        GridLayoutManager slideGlm=new GridLayoutManager(getContext(),2);
        slideRecycler.setLayoutManager(slideGlm);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<ImportBooksModel> boptions=
                new FirebaseRecyclerOptions.Builder<ImportBooksModel>()
                        .setQuery(childRef,ImportBooksModel.class)
                        .build();
        FirebaseRecyclerAdapter<ImportBooksModel,MaterialsDetails.BooksViewHolder> badapter
                =new FirebaseRecyclerAdapter<ImportBooksModel, BooksViewHolder>(boptions) {
            @Override
            protected void onBindViewHolder(@NonNull final MaterialsDetails.BooksViewHolder holder, int position, @NonNull ImportBooksModel model) {

                final String userIds=getRef(position).getKey();
                bookRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final String itemName =dataSnapshot.child("Name").getValue().toString();
                          bookurl=dataSnapshot.child("bookUrl").getValue().toString();
                        holder.bookName.setText(itemName);
                        holder.bookImage.setImageResource(R.drawable.m_book);
                       holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                        DownloadManager downloadManager= (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

                                Uri uri=Uri.parse(bookurl);
                                DownloadManager.Request request=new DownloadManager.Request(uri);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference=downloadManager.enqueue(request);
                                Toast.makeText(getContext(), "File Downloading", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public MaterialsDetails.BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.books_single_row,parent,false);
                MaterialsDetails.BooksViewHolder holder=new MaterialsDetails.BooksViewHolder(v);
                return holder;
            }
        };
        bookRecycler.setAdapter(badapter);
        badapter.startListening();
        // load hand notes
        FirebaseRecyclerOptions<ImportNotesModel> noptions=
                new FirebaseRecyclerOptions.Builder<ImportNotesModel>()
                        .setQuery(childRef,ImportNotesModel.class)
                        .build();
        FirebaseRecyclerAdapter<ImportNotesModel,MaterialsDetails.HandNoteViewHolder> nadapter
                =new FirebaseRecyclerAdapter<ImportNotesModel, HandNoteViewHolder>(noptions) {
            @Override
            protected void onBindViewHolder(@NonNull final MaterialsDetails.HandNoteViewHolder holder, int position, @NonNull ImportNotesModel model) {

                      String userIds=getRef(position).getKey();
                handNoteRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final String noteeName =dataSnapshot.child("Name").getValue().toString();
                       noteurl=dataSnapshot.child("noteUrl").getValue().toString();
                        holder.noteName.setText(noteeName);
                        holder.noteImage.setImageResource(R.drawable.m_hand_note);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                           DownloadManager downloadManager= (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

                                Uri uri=Uri.parse(noteurl);
                                DownloadManager.Request request=new DownloadManager.Request(uri);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference=downloadManager.enqueue(request);
                                Toast.makeText(getContext(), "File Downloading", Toast.LENGTH_SHORT).show();


                            }
                        });


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public MaterialsDetails.HandNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View vv=LayoutInflater.from(parent.getContext()).inflate(R.layout.hand_note_single_row,parent,false);
                MaterialsDetails.HandNoteViewHolder holder=new MaterialsDetails.HandNoteViewHolder(vv);
                return holder;
            }
        };
        handNoteRecycler.setAdapter(nadapter);
        nadapter.startListening();
// load slide items
        FirebaseRecyclerOptions<ImportSlidesModel> soptions=
                new FirebaseRecyclerOptions.Builder<ImportSlidesModel>()
                        .setQuery(childRef,ImportSlidesModel.class)
                        .build();
        FirebaseRecyclerAdapter<ImportSlidesModel,MaterialsDetails.SlideViewHolder> sadapter
                =new FirebaseRecyclerAdapter<ImportSlidesModel, SlideViewHolder>(soptions) {
            @Override
            protected void onBindViewHolder(@NonNull final MaterialsDetails.SlideViewHolder holder, int position, @NonNull ImportSlidesModel model) {

                final String userIds=getRef(position).getKey();
                slideRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final String itemName =dataSnapshot.child("Name").getValue().toString();
                         urll=dataSnapshot.child("slideUrl").getValue().toString();
                        holder.slideName.setText(itemName);
                        holder.slideImage.setImageResource(R.drawable.m_slide);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                             DownloadManager downloadManager= (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

                                Uri uri=Uri.parse(urll);
                                DownloadManager.Request request=new DownloadManager.Request(uri);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference=downloadManager.enqueue(request);
                                Toast.makeText(getContext(), "File Downloading", Toast.LENGTH_SHORT).show();


                            }
                        });


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public MaterialsDetails.SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.slides_single_row,parent,false);
                MaterialsDetails.SlideViewHolder holder=new MaterialsDetails.SlideViewHolder(v);
                return holder;
            }
        };
        slideRecycler.setAdapter(sadapter);
        sadapter.startListening();

    }
    public static class HandNoteViewHolder extends RecyclerView.ViewHolder{
      TextView noteName;
      ImageView noteImage;
        public HandNoteViewHolder(View itemView) {
            super(itemView);
            noteName=itemView.findViewById(R.id.noteNameId);
            noteImage=itemView.findViewById(R.id.noteImageId);
        }
    }
    public static class SlideViewHolder extends RecyclerView.ViewHolder{
        TextView slideName;
        ImageView slideImage;
        public SlideViewHolder(View itemView) {
            super(itemView);
            slideName=itemView.findViewById(R.id.slideNameId);
            slideImage=itemView.findViewById(R.id.slideImageId);
        }
    }
    public static class BooksViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        ImageView bookImage;

        public BooksViewHolder(View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.showBookNameId);
            bookImage = itemView.findViewById(R.id.bookImageId);
        }
    }

    }

