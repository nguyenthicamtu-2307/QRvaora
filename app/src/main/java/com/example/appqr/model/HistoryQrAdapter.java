package com.example.appqr.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appqr.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryQrAdapter extends RecyclerView.Adapter<HistoryQrAdapter.adapterhoder> {
    private Context context;
    private  List<HistoryQR> monList;

    public HistoryQrAdapter(Context context, List<HistoryQR> monList) {
        this.context = context;
        this.monList = monList;
    }

    @NonNull
    @Override
    public adapterhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.createqr,parent,false);
        return new adapterhoder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull adapterhoder holder, int position) {
        holder.name.setText(monList.get(position).getTenkh());
        holder.sdt.setText(monList.get(position).getSdt());
        holder.note.setText(monList.get(position).getNoidung());
        holder.username.setText(monList.get(position).getUsername());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return monList.size();
    }
    public class adapterhoder extends RecyclerView.ViewHolder {
        TextView name, sdt,note,username;



        public adapterhoder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tenkh);
            sdt = itemView.findViewById(R.id.sdt);
            note = itemView.findViewById(R.id.note);
            username = itemView.findViewById(R.id.user);
        }
    }
    public void filterList(ArrayList<HistoryQR> filteredList){
        monList = filteredList;
        notifyDataSetChanged();
    }
}

