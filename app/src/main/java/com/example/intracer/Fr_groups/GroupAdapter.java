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
import com.google.common.util.concurrent.ExecutionError;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> implements View.OnClickListener{

        private List<GroupList> list;

        private LayoutInflater minflater;
        private Context context;

        private View.OnClickListener listener;

    public GroupAdapter(List<GroupList> list, Context context) {
            this.minflater = LayoutInflater.from(context);
            this.list = list;
            this.context = context;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = minflater.inflate(R.layout.group_list_layout, null);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GroupAdapter.ViewHolder holder, final int position) {
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

            private TextView gpName, gpDesc;
            private ImageView profile;

            public ViewHolder(View view) {
                super(view);
                gpName = view.findViewById(R.id.groupname);
                gpDesc = view.findViewById(R.id.Description);

            }

            void bindData(final GroupList item){
                gpName.setText(item.getGroupname());
                gpDesc.setText(item.getDescription());
            }
        }
}