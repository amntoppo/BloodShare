package com.hack.bloodshare;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import androidx.appcompat.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.UserProfileChangeRequest;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.hack.bloodshare.model.User;

public class newUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText editTextName;
    private EditText editTextGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_activity);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        //mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("name").setValue("aman");
        editTextName = findViewById(R.id.editTextName);
        editTextGroup = findViewById(R.id.editTextGroup);

        findViewById(R.id.buttonNameContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String group = editTextGroup.getText().toString().trim();
                if(name.isEmpty()) {
                    editTextName.setError("Enter valid code");
                    //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                    editTextName.requestFocus();
                    //return;
                }
                else if(group.isEmpty()) {
                    editTextGroup.setError("Enter valid group");
                    editTextGroup.requestFocus();
                }
                else {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                    currentUser.updateProfile(changeRequest);
                    writeNewUser(currentUser.getUid(), name, group);
                    //mDatabase.child("users").child(currentUser.getUid()).child("group").setValue(group);
                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                    Log.e("logged", name);

                    SharedPreferences.Editor prefs = getSharedPreferences("BLOOD", MODE_PRIVATE).edit();
                    prefs.putString("group", group);
                    prefs.apply();

                    Intent intent = new Intent(newUserActivity.this, MainActivity.class);
                    //intent.putExtra("group", group);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                    Log.e("logged", currentUser.getUid());
                }
            }
        });
    }

    private void writeNewUser(String UserID, String name, String group) {
        User user = new User(name, group);
        Toast.makeText(getApplicationContext(),user.getGroup(),Toast.LENGTH_SHORT).show();
        mDatabase.child(UserID).setValue(user);
        Log.e("db", "wroks?");
    }

}
