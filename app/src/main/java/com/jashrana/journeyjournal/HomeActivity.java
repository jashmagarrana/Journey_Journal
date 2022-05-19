package com.jashrana.journeyjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.journeyjournal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity implements RecyclerViewInterface {
    FloatingActionButton addjournal;

    RecyclerView recyclerView;

    DatabaseHelperJournal databaseHelperJournal;
    Adapter adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acitivity);

        sharedPreferences = getSharedPreferences("Detailinfo", Context.MODE_PRIVATE);

        addjournal = findViewById(R.id.fabadd);

        databaseHelperJournal = new DatabaseHelperJournal(this);

        addjournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.list_recycle);
        adapter = new Adapter(this, databaseHelperJournal.getDetailList(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);

        //this one if not work

        intent.putExtra("title", adapter.getItemId(position));
        intent.putExtra("detail", adapter.getItemId(position));
        intent.putExtra("image", adapter.getItemId(position));
        intent.putExtra("location", adapter.getItemId(position));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.grid:
                Intent intent = new Intent(HomeActivity.this, GridViewActivity.class);
                startActivity(intent);
                break;
            case R.id.logmenu:
                showAlertDialog();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Logout application!");
        dialog.setMessage("Are you sure?");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPreferences.edit().putBoolean("rememberme", false).commit();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.setNegativeButton("Cancel", null);
        dialog.setCancelable(false);
        dialog.show();
    }
}