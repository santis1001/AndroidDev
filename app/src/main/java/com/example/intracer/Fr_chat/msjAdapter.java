package com.example.intracer.Fr_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.intracer.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class msjAdapter extends RecyclerView.Adapter<msjAdapter.ViewHolder> {

    private List<msj> list;
    private LayoutInflater minflater;
    private Context context;

    public static final int msj_right = 0;
    public static final int msj_left = 1;

    public msjAdapter(List<msj> list, Context context) {
        this.minflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public msjAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == msj_left){
            View view = minflater.inflate(R.layout.chatbubble_left, parent, false);
            return new msjAdapter.ViewHolder(view);
        }
        else if (viewType == msj_right){
            View view = minflater.inflate(R.layout.chatbubble_right, parent, false);
            return new msjAdapter.ViewHolder(view);
        }
        else{
            View view = minflater.inflate(R.layout.chatbubble_left, parent, false);
            return new msjAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(msjAdapter.ViewHolder holder, int position) {
        holder.bindData(list.get(position));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {


        private TextView message, time, sender;

        public ViewHolder(View view) {
            super(view);
            message = view.findViewById(R.id.MessageMsj);
            time = view.findViewById(R.id.time);
        }

        void bindData(final msj item){

            message.setText(item.getMensaje());
            time.setText(item.getHora());

        }
    }

    @Override
    public int getItemViewType(int position) {
        String Suser = ActualChat.Smail;

        if (list.get(position).getSender().equals(Suser)){
            return msj_right;
        }else{
            return msj_left;
        }

    }
}