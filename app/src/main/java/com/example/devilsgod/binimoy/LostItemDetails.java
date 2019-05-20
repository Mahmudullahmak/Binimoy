package com.example.devilsgod.binimoy;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
public class LostItemDetails extends Fragment {
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 0;
    private ImageButton call;
    private EditText smsInput;
    private Button sendButton;
    private TextView itemName, whereFound;
    private ImageView itemImage;
    private static final int REQUEST_CALL = 1;
   private String phnNo;
   private String messageValue;

    public LostItemDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_lost_item_details, container, false);
        call = view.findViewById(R.id.lost_details_callId);
        smsInput = view.findViewById(R.id.lost_details_sms_input_EditTextId);
        itemName = view.findViewById(R.id.lost_details_ItemName);
        whereFound = view.findViewById(R.id.lost_details_whereFoundId);
        itemImage = view.findViewById(R.id.lost_details_ImageId);
        sendButton = view.findViewById(R.id.lost_details_sendButtonId);
        String name = getArguments().getString("name");
        String itemDesc = getArguments().getString("desc");
        String itemImg =getArguments().getString("image");
        phnNo =Long.toString(getArguments().getLong("phhn"));

        Picasso.get().load(itemImg).into(itemImage);
        itemName.setText(name);
        whereFound.setText(itemDesc);
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
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
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
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
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
