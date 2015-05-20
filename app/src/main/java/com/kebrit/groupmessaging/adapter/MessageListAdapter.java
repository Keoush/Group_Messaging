package com.kebrit.groupmessaging.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kebrit.groupmessaging.R;
import com.kebrit.groupmessaging.util.MessageClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessageListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Pair<MessageClass, Boolean>> messages;
    private Context context;

    private DateFormat dateFormatter;

    private String senderId;

    public MessageListAdapter(Context context, String senderId) {

        layoutInflater = LayoutInflater.from(context);
        messages = new ArrayList<Pair<MessageClass, Boolean>>();
        this.context = context;

        this.senderId = senderId;

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        dateFormatter.setLenient(false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean inCome = messages.get(position).second;

        if (!inCome)
            convertView = layoutInflater.inflate(R.layout.chat_sent_view, parent, false);
        else
            convertView = layoutInflater.inflate(R.layout.chat_recived_view, parent, false);


        TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        TextView txtName = (TextView) convertView.findViewById(R.id.txtSender);

        txtName.setText(messages.get(position).first.getSenderId());

        txtMessage.setText(messages.get(position).first.getContent());

        txtDate.setText(messages.get(position).first.getDate());


        Animation animation = AnimationUtils.loadAnimation(context, R.anim.down_from_top);
        convertView.startAnimation(animation);

        return convertView;
    }

    public void addMessage(MessageClass msg) {
//        -------------------------------------------------------------------------------------------
        messages.add(new Pair<MessageClass, Boolean>(msg, (msg.getSenderId().equals(senderId)) ? false : true));

        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

}


