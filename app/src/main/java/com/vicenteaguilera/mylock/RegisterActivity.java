package com.vicenteaguilera.mylock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.vicenteaguilera.mylock.interfaces.Status;
import com.vicenteaguilera.mylock.models.User;
import com.vicenteaguilera.mylock.utility.FirebaseAuthHelper;

public class RegisterActivity extends AppCompatActivity
{
    private CardView button_registrarse;
    private EditText editText_email,editText_password;
    private FirebaseAuthHelper firebaseAuthHelper = new FirebaseAuthHelper();




    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuthHelper.setContext(this);
        firebaseAuthHelper.setOnStatusListener(new Status() {
            @Override
            public void status(String message)
            {
                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });



    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setElevation(0);
        setTitle("MyLock App");
        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);
        button_registrarse = findViewById(R.id.button_registrarse);


        button_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //firebaseAuthHelper.setMessage(editText_email.getText().toString(),editText_password.getText().toString());

                ProgressDialog dialog = ProgressDialog.show(RegisterActivity.this, "",
                        "Cargando. Porfa espera...", true);
                dialog.show();
                firebaseAuthHelper.createUserEmailAndPassword(editText_email.getText().toString(),editText_password.getText().toString(),dialog);

            }
        });
    }
}