package com.example.intracer.Fr_chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageVH extends RecyclerView.ViewHolder {

    TextView tvsender, tvreceiver;

    public MessageVH(@NonNull View itemView) {
        super(itemView);
    }
}
