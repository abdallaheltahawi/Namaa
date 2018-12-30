package com.example.abdallaheltahawi.namaaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Listitem> listitems;
    private Context context;

    public MyAdapter(List<Listitem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
     View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
     return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
     Listitem listitem=listitems.get(position);
     holder.textViewHead.setText(listitem.getHead());
     //holder.textViewDesc.setText(listitem.getDesc());
    Picasso.get().load(listitem.getImageUrl())
            .into(holder.imageview);
    }

    @Override
    public int getItemCount() {
        return listitems.size();
        //return 0;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
       // public TextView textViewDesc;
        public ImageView imageview;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead=(TextView)itemView.findViewById(R.id.textViewHead);
           // textViewDesc=(TextView)itemView.findViewById(R.id.textViewDesc);
            imageview=(ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}
