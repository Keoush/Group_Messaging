package com.kebrit.groupmessaging.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kebrit.groupmessaging.R;
import com.kebrit.groupmessaging.adapter.ContactListAdapter;

public class MainActivity extends ActionBarActivity {

    private ContactListAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView contactList = (ListView) findViewById(R.id.contactListView);

        adapter = new ContactListAdapter(this);
        contactList.setAdapter(adapter);

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String name = (String) parent.getItemAtPosition(position);

                Log.d("Kebrit_Log", "contact element selected : " + name);

                Intent myIntent = new Intent(MainActivity.this, ChatActivity.class);
                myIntent.putExtra("contactName", name); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
//      ------------------------------------------------------------------------------------------------ added Test element...
        adapter.addContact("Group");
//      ------------------------------------------------------------------------------------------------

    }
    
}
