package com.example.devilsgod.binimoy;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inputmethodservice.ExtractEditText;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareItemDetails extends Fragment {
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 0;
    private FirebaseDatabase database;
    private DatabaseReference rootRef;
    private DatabaseReference childRef;
    private ImageButton call;
    private EditText smsInput;
    private Button sendButton;
    private TextView nameTV, descTV;
    private ImageView itemImageIV;
    private static final int REQUEST_CALL = 1;
    String phnNo;
    String messageValue;


    public ShareItemDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_item_details, container, false);

        call = view.findViewById(R.id.share_details_callId);
        smsInput = view.findViewById(R.id.sms_input_EditTextId);
        nameTV = view.findViewById(R.id.share_details_ItemName);
        descTV = view.findViewById(R.id.share_details_ItemDescription);
        itemImageIV = view.findViewById(R.id.share_details_ImageId);
        sendButton = view.findViewById(R.id.sendButtonId);
        String name = getArguments().getString("name");
        String itemDesc = getArguments().getString("desc");
        String itemImg =getArguments().getString("image");
        phnNo = Long.toString(getArguments().getLong("phn"));
        Picasso.get().load(itemImg).into(itemImageIV);
        nameTV.setText(name);
        descTV.setText(itemDesc);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();

            }

        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });


        return view;
    }

    public void makePhoneCall() {
        if (phnNo.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:+880" + phnNo;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            }
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getContext(), "sms sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                Toast.makeText(getContext(), "NOthing ", Toast.LENGTH_SHORT).show();
            }
        }



    public void sendSms() {
          messageValue=smsInput.getText().toString();

        if (messageValue.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
            } else {
                String sendTo = "+880"+phnNo;
                SmsManager smsManager= SmsManager.getDefault();
                smsManager.sendTextMessage(sendTo,null,messageValue,null,null);
                Toast.makeText(getContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                smsInput.setText("");

            }
        }


            }



    }



