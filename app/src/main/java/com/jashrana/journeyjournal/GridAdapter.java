package com.jashrana.journeyjournal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.journeyjournal.R;
import com.jashrana.journeyjournal.helperclass.Detailinfo;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter<Detailinfo> {
    Context context;

    public GridAdapter(@NonNull Context context, ArrayList<Detailinfo> list) {
        super(context, 0,list);
        this.context = context;
    }
Bitmap bitmap;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_grid_view, null);

        TextView title = view.findViewById(R.id.titleg);
        TextView date = view.findViewById(R.id.dateg);
        ImageView imageView = view.findViewById(R.id.imageg);

        Detailinfo info = getItem(position);

       title.setText(info.title);
        date.setText(info.date);
        imageView.setImageBitmap(DatabaseHelperJournal.getBitmap(info.image));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("id",info.id);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
