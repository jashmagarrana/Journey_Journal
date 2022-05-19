package com.jashrana.journeyjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journeyjournal.R;

public class ResetActivity extends AppCompatActivity {
    TextView username;
    EditText pass, repass;
    Button confirm;

    DatabaseHelper DH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        username = findViewById(R.id.username_reset_text);
        pass = findViewById(R.id.password_reset);
        repass = findViewById(R.id.repassword_reset);
        confirm = findViewById(R.id.btnconfirm);
        DH = new DatabaseHelper(this);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String password = pass.getText().toString();

                String repassword = repass.getText().toString();
                if(password.equals(repassword))
                {

                    Boolean checkpasswordupdate = DH.updatepassword(user, password);
                    if(checkpasswordupdate == true)
                    {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(ResetActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        Toast.makeText(ResetActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }

                }else
                {
                    Toast.makeText(ResetActivity.this, "The password did not match", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}