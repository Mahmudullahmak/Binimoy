package com.example.devilsgod.binimoy;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.devilsgod.binimoy.Model.ExchangeInsertModel;
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
public class AddExchangeItemFragment extends Fragment {
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
    String ex_currentUserId;
    private EditText ex_itemNameET,ex_itemDescriptionET,exchangeItemNameET,userPhoneNoET;

    public AddExchangeItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_exchange_item, container, false);

        ex_itemNameET=view.findViewById(R.id.ex_product_name);
        exchangeItemNameET=view.findViewById(R.id.ex_exchangeProductNameId);
        takeButton=view.findViewById(R.id.add_photo_exchange);
        itemImage=view.findViewById(R.id.add_exchange_imageId);
        postButton=view.findViewById(R.id.exchange_postButtonId);
        ex_itemDescriptionET=view.findViewById(R.id.ex_product_descriptionId);
        userPhoneNoET=view.findViewById(R.id.ex_userPhoneNoId);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        rootRef= FirebaseDatabase.getInstance().getReference("rootDataRef");
        exchangeItemsDataRef=rootRef.child("ExchangeItemsData");
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


                    ExchangeHomeFragment ex_home_fragment = new ExchangeHomeFragment();
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.exchange_fragment_holderId, ex_home_fragment, "ex");
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
            final StorageReference ref = storageReference.child("ExchangeImages/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Image saved", Toast.LENGTH_SHORT).show();

                            Task<Uri> dUrlRef=ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downUrl=uri.toString();
                                    ex_currentUserId=user.getUid().toString();
                                    String itemmName=ex_itemNameET.getText().toString();
                                    String itemDesc=ex_itemDescriptionET.getText().toString();
                                    Integer phnNo=Integer.parseInt(userPhoneNoET.getText().toString());
                                    String  itemasExchange=exchangeItemNameET.getText().toString();
                                    String exUkey=exchangeItemsDataRef.push().getKey();
                                   ExchangeInsertModel modell=new ExchangeInsertModel(ex_currentUserId,downUrl,itemmName,itemDesc,itemasExchange,phnNo);
                                    exchangeItemsDataRef.child(ex_currentUserId).child(exUkey).setValue(modell).addOnCompleteListener(new OnCompleteListener<Void>() {
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
