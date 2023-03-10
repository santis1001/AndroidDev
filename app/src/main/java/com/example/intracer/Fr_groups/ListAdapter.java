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

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener{

    private List<QRlist> list;

    private LayoutInflater minflater;
    private Context context;

    private View.OnClickListener listener;

    public ListAdapter(List<QRlist> list, Context context) {
        this.minflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }
    public ArrayList<QRlist> GetList(){
        ArrayList<QRlist> GL = (ArrayList<QRlist>) list;
        return GL;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflater.inflate(R.layout.list_qr_codes, null);
        view.setOnClickListener(this);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position) {
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

        private TextView gpdate;

        public ViewHolder(View view) {
            super(view);
            gpdate = view.findViewById(R.id.date);

        }

        void bindData(final QRlist item){
            gpdate.setText(item.getDate());

        }
    }


}
