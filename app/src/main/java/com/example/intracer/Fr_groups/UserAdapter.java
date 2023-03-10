package com.example.intracer.Fr_groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intracer.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements View.OnClickListener{

    private List<UserList> list;

    private LayoutInflater minflater;
    private Context context;

    private View.OnClickListener listener;

    public UserAdapter(List<UserList> list, Context context) {
        this.minflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.user_att, null);
        view.setOnClickListener(this);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, final int position) {
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

        private TextView gpdate, gpemail;

        public ViewHolder(View view) {
            super(view);
            gpemail = view.findViewById(R.id.mail);
            gpdate = view.findViewById(R.id.Date_SC);

        }

        void bindData(final UserList item){
            gpemail.setText(item.getEmail());
            gpdate.setText(item.getDatesc());
        }
    }


}
