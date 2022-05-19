package com.jashrana.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.example.journeyjournal.R;

public class GridViewActivity extends AppCompatActivity {

    GridView gridView;

    GridAdapter gridAdapter;
    DatabaseHelperJournal databaseHelperJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        gridView = findViewById(R.id.gridView);

        databaseHelperJournal = new DatabaseHelperJournal(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        gridAdapter = new GridAdapter(this, databaseHelperJournal.getDetailList());

        gridView.setAdapter(gridAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}