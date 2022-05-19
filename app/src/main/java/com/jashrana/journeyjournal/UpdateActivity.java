package com.jashrana.journeyjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.journeyjournal.R;

public class UpdateActivity extends AppCompatActivity {

    EditText title, details, date, address;
    ImageView image;
    Button add, cancel;


    int id;

    DatabaseHelperJournal databaseHelperJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_activity);
    }
}