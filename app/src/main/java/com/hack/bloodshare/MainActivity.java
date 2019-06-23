package com.hack.bloodshare;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
//import android.util.Log;
//import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hack.bloodshare.adapter.RecyclerViewAdapter;
import com.hack.bloodshare.model.Receivers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//import com.google.firebase.messaging.FirebaseMessaging;

//import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ImageView imageView2;
//    private final String JSONString = "https://api.myjson.com/bins/1cc78d";
//    private JsonArrayRequest request;
//    private RequestQueue requestQueue;
//    private List<Receivers> receiversList;
//    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        mAuth = FirebaseAuth.getInstance();
        imageView2 = findViewById(R.id.imageView2);
//        recyclerView = findViewById(R.id.recyclerviewid);
//        receiversList = new ArrayList<>();
//        JsonRequest();

        findViewById(R.id.patientButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PatientClass.class);
                startActivity(intent);
                //inish();
            }
        });
        findViewById(R.id.hospitalButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, hospitals.class);
                startActivity(intent);

            }
        });


    }

    @Override
    @NonNull
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName()

        if(currentUser == null) {
            Intent authIntent = new Intent(MainActivity.this,AuthActivity.class);
            startActivity(authIntent);
            finish();
        }


        SharedPreferences prefs = getSharedPreferences("BLOOD", MODE_PRIVATE);
        String group = prefs.getString("group", "B+");

        //if(group.endsWith("+")) {
            if(group.charAt(group.length()-1) == '+') {
            group = group.substring(0, group.length()-1) + "positive";
        }
        else if(group.endsWith("-")) {
            group = group.substring(0, group.length()-1) + "negative";
        }
        Toast.makeText(this, group, Toast.LENGTH_SHORT).show();



        FirebaseMessaging.getInstance().subscribeToTopic(group)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Success";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Log.d("push", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
