package com.example.intracer.Fr_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intracer.R;
import com.example.intracer.UserLogin.SignUpActivity;
import com.google.firebase.firestore.auth.User;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>  implements View.OnClickListener{

    private List<ChatList> list;
    private LayoutInflater minflater;
    private Context context;

    private View.OnClickListener listener;
    public ChatAdapter(List<ChatList> list, Context context) {
        this.minflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.chat_list_layout, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, final int position) {
        holder.bindData(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {


        private TextView usName, usEmail;
        private ImageView profile;

        public ViewHolder(View view) {
            super(view);
            usName = view.findViewById(R.id.username);
            usEmail = view.findViewById(R.id.email_user);
            profile = view.findViewById(R.id.profile_image);

        }

        void bindData(final ChatList item){
            usName.setText(item.getUsername());
            usEmail.setText(item.getUsermail());
        }


    }
    public void setOnClickListener (View.OnClickListener listener){
        this.listener = listener;

    }
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }
}