package com.kebrit.groupmessaging.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.kebrit.groupmessaging.R;
import com.kebrit.groupmessaging.util.Constants;

import java.util.HashMap;
import java.util.Map;


public class LogInActivity extends Activity {

    private Button logInButton;
    private EditText nameText;
    private EditText passwordText;

    private SharedPreferences preferences;

    private Firebase myFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Firebase.setAndroidContext(this);
        myFirebase = new Firebase(Constants.URL_FIREBASE);

        preferences = getSharedPreferences("PREFERENCES", 0);

        logInButton = (Button) findViewById(R.id.logInButton);
        nameText = (EditText) findViewById(R.id.nameField);
        passwordText = (EditText) findViewById(R.id.passwordField);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = nameText.getText().toString();

                checkIfUserExists(name, name, passwordText.getText().toString());
            }
        });
    }

    private void checkIfUserExists(final String uID, final String uName, final String password) {
        myFirebase.child("users").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    if(password.length() < 4){
                        Toast.makeText(LogInActivity.this, "your password length must greater than 4 character\n" +
                                "please enter new password.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else {
                        Map map = new HashMap();
                        map.put("userId", uID);
                        map.put("username", uName);
                        map.put("password", password);

                        myFirebase.child("users").child(uID).setValue(map);

                        preferences.edit().putString("USERNAME", uName).commit();
                        preferences.edit().putString("USERID", uID).commit();

                        Toast.makeText(LogInActivity.this, "New user created : " + uID, Toast.LENGTH_LONG).show();

                        goToNextActivity(uName);
                    }

                }else if(( (Map) dataSnapshot.getValue()).get("password").toString().equals(password)){
                    goToNextActivity(uName);
                }
                else {
                    Toast.makeText(LogInActivity.this, "password dosent match\nand usersname already exists.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    private void goToNextActivity(String name) {

        Intent myIntent = new Intent(LogInActivity.this, ChatActivity.class);
        myIntent.putExtra("USERNAME", name);
        LogInActivity.this.startActivity(myIntent);

        finish();
        return;
    }
}
