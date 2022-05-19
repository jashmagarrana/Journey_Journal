package com.jashrana.journeyjournal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journeyjournal.R;
import com.jashrana.journeyjournal.helperclass.Detailinfo;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText title, details, address;
    ImageView image, imagePicker,map;
    Button add, cancel;
    TextView textView;

    LinearLayout form;

    int id;

    DatabaseHelperJournal databaseHelperJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getIntExtra("id", 0);

        databaseHelperJournal = new DatabaseHelperJournal(this);

        form = findViewById(R.id.loginlayout);
        title = findViewById(R.id.aabout);
        details = findViewById(R.id.adetails);
        image = findViewById(R.id.agimage);
        address = findViewById(R.id.aaddress);
        add = findViewById(R.id.aaddbtn);
        cancel = findViewById(R.id.acancelbtn);
        textView = findViewById(R.id.atitle);
        imagePicker = findViewById(R.id.aimagePicker);
        map = findViewById(R.id.map);
// Using image picker library
        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(AddActivity.this)
                        .compress(1024)//Final image size will be less than 1 MB
                        .crop()
                       // .maxResultSize(1080,1080)//Fimal image resolution will be less than 1080 * 1080
                        .start();
            }
        });

        //    Using data and time format in system
        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);

            }
        });

        if (id != 0) {
            Detailinfo info = databaseHelperJournal.getDetailInfo(String.valueOf(id));
            title.setText(info.title);
            address.setText(info.address);
            details.setText(info.details);
            formattedDate.toString();
            add.setText("Update Journal");
            textView.setText("Update Journey Journal");

        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(emptyFieldValidation(title) && emptyFieldValidation(details)) {

                    String titleValue = title.getText().toString();
                    String detailValue = details.getText().toString();
                    String dateValue = formattedDate.toString();
                    String addressValue = address.getText().toString();


                    ContentValues contentValues = new ContentValues();
                    contentValues.put("title", titleValue);
                    contentValues.put("details", detailValue);
                    contentValues.put("address", addressValue);
                    contentValues.put("date", dateValue);
                    contentValues.put("image", DatabaseHelperJournal.getBlob(bitmap));


                    if (id == 0) {
                        databaseHelperJournal.insertDetail(contentValues);
                        Toast.makeText(AddActivity.this, "Added journal successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        databaseHelperJournal.updateDetail(id + "", contentValues);
                        Toast.makeText(AddActivity.this, "Journal updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }


    public boolean emptyFieldValidation(EditText view) {
        if (view.getText().toString().length() >= 2) {
            return true;
        } else {
            view.setError("Minimum length must be 2");
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    Bitmap bitmap;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
        Uri uri = data.getData();
        image.setImageURI(uri);
    }
}