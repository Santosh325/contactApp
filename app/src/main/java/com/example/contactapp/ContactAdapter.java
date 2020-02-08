package com.example.contactapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.contactViewHolder> {

    List<Details> mDetails;
    private Context mContext;
    public ContactAdapter(List<Details> mDetails,Context mContext) {
       this.mDetails = mDetails;
       this.mContext = mContext;
    }

    @NonNull
    @Override
    public contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       View view = inflater.inflate(R.layout.item,parent,false);
       return new contactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull contactViewHolder holder, int position) {
     final Details data = mDetails.get(position);
      holder.mTextView.setText(data.getName());

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent(mContext,showContact.class);
              i.putExtra("id",data.getId());
              i.putExtra("name",data.getName());
              i.putExtra("phone",data.getPhone());
              mContext.startActivity(i);
          }
      });
    }

    @Override
    public int getItemCount() {
         return mDetails.size();
    }


    public class contactViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;

        public contactViewHolder(View itemview) {
               super(itemview);
               mTextView = itemview.findViewById(R.id.title);

        }
    }
}
