package com.example.devilsgod.binimoy;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.devilsgod.binimoy.Model.ExchangeInsertModel;
import com.example.devilsgod.binimoy.Model.InsertLostItemsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddLostItem extends Fragment {

    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private StorageReference storageReference;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView itemImage;
    private Button takeButton,postButton;
    //For database
    private DatabaseReference rootRef;
    private DatabaseReference exchangeItemsDataRef;
    //FirebaseStorage
    String lost_currentUserId;
    private EditText itemNameET,whereFound,userPhoneNoET;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public AddLostItem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.add_lost_item_fragment, container, false);
        itemNameET=view.findViewById(R.id.lostproduct_name);
        whereFound=view.findViewById(R.id.lost_where_foundId);
        takeButton=view.findViewById(R.id.add_photo_lostButton);
        itemImage=view.findViewById(R.id.add_loast_imageId);
        postButton=view.findViewById(R.id.lost_postButtonId);
        userPhoneNoET=view.findViewById(R.id.lost_userPhoneNoId);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        rootRef= FirebaseDatabase.getInstance().getReference("rootDataRef");
        exchangeItemsDataRef=rootRef.child("LostItemsData");
        user=FirebaseAuth.getInstance().getCurrentUser();

takeButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        chooseExchangeImage();

    }
});
      postButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             uploadImage();
             LostandFoundFragment lost_home_fragment = new LostandFoundFragment();
             fragmentManager = getActivity().getSupportFragmentManager();
             fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.lost_fragment_holder, lost_home_fragment, "lost");
                  fragmentTransaction.addToBackStack(null);
                  fragmentTransaction.commit();

          }
      });
        return view;
    }
    private void chooseExchangeImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                itemImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null) {
            final StorageReference ref = storageReference.child("LostImages/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Image saved", Toast.LENGTH_SHORT).show();

                            Task<Uri> dUrlRef=ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downUrl=uri.toString();
                                    lost_currentUserId=user.getUid().toString();
                                    String lostUkey=exchangeItemsDataRef.push().getKey();
                                    String itemmName=itemNameET.getText().toString();
                                    String itemDesc=whereFound.getText().toString();
                                    Integer phnNo=Integer.parseInt(userPhoneNoET.getText().toString());
                                    InsertLostItemsModel modell=new InsertLostItemsModel(itemmName,itemDesc,lost_currentUserId,downUrl,phnNo);

                                    exchangeItemsDataRef.child(lost_currentUserId).child(lostUkey).setValue(modell).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getContext(), "DataSaved", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Data Could not saved ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Image failed"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }





    }
}
