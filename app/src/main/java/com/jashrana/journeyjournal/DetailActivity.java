package com.jashrana.journeyjournal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.journeyjournal.R;
import com.jashrana.journeyjournal.helperclass.Detailinfo;

public class DetailActivity extends AppCompatActivity {

    String id;
    TextView title, address, date, details;
    Button update, delete, share;
    DatabaseHelperJournal databaseHelperJournal;
    ImageView imageView;

    Detailinfo detailinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);


        id = getIntent().getStringExtra("id");

        title = findViewById(R.id.about);
        address = findViewById(R.id.location);
        date = findViewById(R.id.date_time);
        details = findViewById(R.id.details);
        update = findViewById(R.id.updatebtn);
        delete = findViewById(R.id.deletebtn);
        share = findViewById(R.id.sharebtn);
        imageView = findViewById(R.id.cimage);


        databaseHelperJournal = new DatabaseHelperJournal(this);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, AddActivity.class);
                intent.putExtra("id", Integer.parseInt(id));
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
// Using action send intent for to share the journal.
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable)imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "title", "content");

                Uri uri = Uri.parse(bitmapPath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("imge/png");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Title: " + title.getText().toString()+"\n" + "Date: " + date.getText().toString()+"\n" + "Location: " + address.getText().toString()+"\n" + "My Thoughts: " + details.getText().toString());
                startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });

    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Journal");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelperJournal.deleteDetail(id);
                Intent intent = new Intent(DetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailinfo = databaseHelperJournal.getDetailInfo(id);

        title.setText(detailinfo.title);
        address.setText(detailinfo.address);
        date.setText(detailinfo.date);
        details.setText(detailinfo.details);
        if (detailinfo.image != null)
            imageView.setImageBitmap(DatabaseHelperJournal.getBitmap(detailinfo.image));
    }
}