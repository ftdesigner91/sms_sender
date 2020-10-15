package com.gmail.smssender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText nPhoneNumber;
    private TextInputEditText nMessageHolder;
    private Button nSendBtn;

    String phoneNum;
    String msg;
    final int SEND_SMS_REQ_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nPhoneNumber = findViewById(R.id.phoneNumber);
        nMessageHolder = findViewById(R.id.messageHolder);
        nSendBtn = findViewById(R.id.sendBtn);

        nSendBtn.setEnabled(false);
        if (checkpermidsion(Manifest.permission.SEND_SMS))
        {nSendBtn.setEnabled(true);}
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQ_CODE);
        }

        nSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNum = String.valueOf(nPhoneNumber.getText());
                msg = String.valueOf(nMessageHolder.getText());

                if (checkpermidsion(Manifest.permission.SEND_SMS))
                {
                    sendMessage();
                }
            }
        });
    }

    private void sendMessage()
    {
        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum, null, msg,null,null);

            Toast.makeText(this, "Message sent successfully", Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(this, "Could not send! please try again", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkpermidsion(String permission)
    {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

}