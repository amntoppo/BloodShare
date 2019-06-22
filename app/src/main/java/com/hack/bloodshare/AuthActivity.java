package com.hack.bloodshare;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AuthActivity extends AppCompatActivity {

    private EditText mobileText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_layout);

        mobileText = findViewById(R.id.editTextMobile);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mobileText.getText().toString().trim();
                if(mobile.isEmpty() || mobile.length() < 10) {
                    mobileText.setError("Enter a valid mobile number");
                    mobileText.requestFocus();
                    return;
                }
                Intent intent = new Intent(AuthActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);

            }
        });

    }
}
