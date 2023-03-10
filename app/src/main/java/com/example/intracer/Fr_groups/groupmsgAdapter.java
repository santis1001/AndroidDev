package com.example.intracer.Fr_groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intracer.R;

import java.util.List;

public class groupmsgAdapter extends RecyclerView.Adapter<groupmsgAdapter.ViewHolder> implements View.OnClickListener{

    private List<groupmsg> list;

    private LayoutInflater minflater;
    private Context context;

    private View.OnClickListener listener;

    public groupmsgAdapter(List<groupmsg> list, Context context) {
        this.minflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public groupmsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.group_chat, null);
        view.setOnClickListener(this);
        return new groupmsgAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(groupmsgAdapter.ViewHolder holder, final int position) {
        holder.bindData(list.get(position));

//            ChatList chatlist = list.get(position);
//            viewHolder.usName.setText(chatlist.getUsername());
//            viewHolder.usEmail.setText(chatlist.getUsername());

//            Glide
//                    .with(context)
//                    .load(chatlist.getUrlProfile())
//                    .into(viewHolder.profile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener (View.OnClickListener listener){
        this.listener = listener;

    }
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }

    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView gpdate, gpemail, gpMsg;

        public ViewHolder(View view) {
            super(view);
            gpemail = view.findViewById(R.id.user);
            gpMsg =  view.findViewById(R.id.msg);
            gpdate = view.findViewById(R.id.date);
        }

        void bindData(final groupmsg item){
            gpemail.setText(item.getEmail());
            gpMsg.setText(item.getMsg());
            gpdate.setText(item.getDate());
        }
    }


}
