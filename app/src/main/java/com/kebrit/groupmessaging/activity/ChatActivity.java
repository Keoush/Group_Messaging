package com.kebrit.groupmessaging.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.kebrit.groupmessaging.R;
import com.kebrit.groupmessaging.adapter.MessageListAdapter;
import com.kebrit.groupmessaging.util.Constants;
import com.kebrit.groupmessaging.util.MessageClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ChatActivity extends ActionBarActivity {

    private MessageListAdapter adapter;

    private DateFormat dateFormatter;

    private boolean lock = true;

    private static String SENDER_ID = "defualt";
    private static String RECEIVER_ID = "Group";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//        setTitle(getIntent().getStringExtra("contactName"));

        loadPreferences();

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        dateFormatter.setLenient(false);

        adapter = new MessageListAdapter(this, SENDER_ID);

        ListView chatList = (ListView) findViewById(R.id.listMessages);

        chatList.setAdapter(adapter);

        final EditText inputText = (EditText) findViewById(R.id.messageBodyField);

        final Button sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lock){
                    Toast.makeText(getApplicationContext(), "Please wait until messages load...",
                            Toast.LENGTH_SHORT).show();
                }
                else if (!inputText.getText().toString().equals("")) {

                    final MessageClass msg = new MessageClass(inputText.getText().toString(), SENDER_ID, RECEIVER_ID, dateFormatter.format(new Date()));

                    sendMessageToFirebase(msg);

                    inputText.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter some text...",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        setMsgReceiver();

        checkMessageExistance();

        Toast.makeText(getApplicationContext(), "Please wait until messages load...",
                Toast.LENGTH_LONG).show();
    }

    private void checkMessageExistance() {
        Constants.myFirebase.child("messages").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null) {
                    Log.d("kebrit:firebase", "no message found in group.");
                    lock = false;
                }
                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }
            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                //This method will be called once with the results of the transaction.
            }
        });
    }

    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
        SENDER_ID = settings.getString("USERID", "default");
    }

    @Override
    protected void onResume() {
        super.onResume();

//        setMsgReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setMsgReceiver() {
        ChildEventListener childEventListener = Constants.myFirebase.child("messages").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (lock)
                    lock = false;

                Map messageMap = (Map) dataSnapshot.getValue();
                MessageClass msg = new MessageClass(messageMap.get("content").toString(), messageMap.get("senderId").toString()
                        , messageMap.get("senderId").toString(), messageMap.get("date").toString());

                Log.d("Kebrit:msg", "New msg added to DB: " + messageMap.get("senderId") + "->" + messageMap.get("content"));

                adapter.addMessage(msg);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("kebrit","hey...____________________________________");
            }
        });
    }

    private void sendMessageToFirebase(MessageClass msg) {
        Constants.myFirebase.child("messages").push().
                setValue(msg);
    }
}
