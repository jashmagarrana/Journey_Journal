package com.jashrana.journeyjournal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journeyjournal.R;
import com.jashrana.journeyjournal.helperclass.Detailinfo;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.RecycleViewAdapter> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<Detailinfo> list;

    public Adapter(Context context, ArrayList<Detailinfo> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecycleViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_journal_design, null);
        return new RecycleViewAdapter(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter holder, int position) {
        Detailinfo detailinfo = list.get(position);
        holder.title.setText(detailinfo.title);
        holder.details.setText(detailinfo.details);
        holder.date.setText(detailinfo.date);
        // For to redirect to the detail page from the home page.
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", detailinfo.id);
                context.startActivity(intent);
            }
        });
        holder.imageView.setImageBitmap(DatabaseHelperJournal.getBitmap(detailinfo.image));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class RecycleViewAdapter extends RecyclerView.ViewHolder {
        TextView title, details, date;
        ImageView imageView;

        public RecycleViewAdapter(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            title = itemView.findViewById(R.id.titleview);
            details = itemView.findViewById(R.id.detailview);
            imageView = itemView.findViewById(R.id.imageview);
            date = itemView.findViewById(R.id.datetimeview);

        }
    }
}
