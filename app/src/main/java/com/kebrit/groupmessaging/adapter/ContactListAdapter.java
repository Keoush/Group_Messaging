package com.kebrit.groupmessaging.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kebrit.groupmessaging.R;

import java.util.ArrayList;

/**
 * Created by Keoush on 5/1/2015.
 */
public class ContactListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<String> contactList;

    public ContactListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);

        contactList = new ArrayList<>();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String name = contactList.get(position);

        convertView = layoutInflater.inflate(R.layout.contact_list_view, parent, false);

        TextView contactName = (TextView) convertView.findViewById(R.id.contactNameText);
        contactName.setText(name);

        return convertView;
    }

    public void addContact(String name) {
        contactList.add(name);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public int getCount() {
        return contactList.size();
    }
}
