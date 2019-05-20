package com.example.devilsgod.binimoy;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.devilsgod.binimoy.Model.ShareItemDatabaseInsertModel;
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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddSharedItemsFragment extends Fragment {
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private StorageReference storageReference;
   private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private Spinner shareNameSpinner;
    private ImageView itemImage;
    private Button takeButton,postButton;
   //For database
   private DatabaseReference rootRef;
   private DatabaseReference shareItemsDataRef;
   //FirebaseStorage
String currentUserId;
   private EditText itemDescriptionET,userPhoneNoET;
    public AddSharedItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_shared_items, container, false);
              shareNameSpinner=view.findViewById(R.id.shareItemNameSpinnerId);
        takeButton=view.findViewById(R.id.add_photo_shareId);
        itemImage=view.findViewById(R.id.add_share_imageId);
        postButton=view.findViewById(R.id.share_postButtonId);
        itemDescriptionET=view.findViewById(R.id.share_item_descriptionId);
        userPhoneNoET=view.findViewById(R.id.share_userPhoneNoId);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
         rootRef=FirebaseDatabase.getInstance().getReference("rootDataRef");
         shareItemsDataRef=rootRef.child("ShareItemsData");
         user=FirebaseAuth.getInstance().getCurrentUser();




        loadShareNameSpinner();
        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    uploadImage();
             ShareFragment shareFragment = new ShareFragment();
               android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
               android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.share_fragment_holder, shareFragment, "share");
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();

           }



        });


        return view;
    }
    private void chooseImage() {
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

    public void loadShareNameSpinner(){
       ArrayAdapter<CharSequence>nameAdapter=ArrayAdapter.createFromResource(getContext(),R.array.shareProductName,android.R.layout.simple_spinner_item);
       nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       shareNameSpinner.setAdapter(nameAdapter);
   }
    private void uploadImage() {

        if(filePath != null) {
            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Image saved", Toast.LENGTH_SHORT).show();

                    Task<Uri> dUrlRef=ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downUrl=uri.toString();

                            currentUserId=user.getUid().toString();
                            String shareItemRefId=shareItemsDataRef.push().getKey();
                            String itemmName=shareNameSpinner.getSelectedItem().toString();
                            String itemDesc=itemDescriptionET.getText().toString();
                            Integer phnNo=Integer.parseInt(userPhoneNoET.getText().toString());
                             String shareUkey=shareItemsDataRef.push().getKey();
                            ShareItemDatabaseInsertModel model=new ShareItemDatabaseInsertModel(itemmName,phnNo,itemDesc,downUrl,currentUserId);
                            shareItemsDataRef.child(currentUserId).child(shareUkey).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
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


