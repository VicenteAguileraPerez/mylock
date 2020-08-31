package com.vicenteaguilera.mylock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.vicenteaguilera.mylock.interfaces.Status;
import com.vicenteaguilera.mylock.models.User;
import com.vicenteaguilera.mylock.utility.FirebaseAuthHelper;

public class LoginActivity extends AppCompatActivity //implements Status
{

    private CardView button_login,button_registrarse;
    private EditText editText_email,editText_password;
    private FirebaseAuthHelper firebaseAuthHelper = new FirebaseAuthHelper();

    //usuario@servidor
    //servidor
    //ibm.com.m


    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuthHelper.setOnStatusListener(new Status() {
            @Override
            public void status(String message)
            {
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });
        //
        User user = FirebaseAuthHelper.getUser();
        if(user!=null)
        {
            Log.e("users",user.getUid());
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setElevation(0);
        setTitle("MyLock App");

        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);

        button_login = findViewById(R.id.button_login);
        button_registrarse = findViewById(R.id.button_registrarse);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "",
                        "Cargando. Porfa espera...", true);
                dialog.show();
                firebaseAuthHelper.signInWithEmailAndPassword(editText_email.getText().toString(),editText_password.getText().toString(),dialog,LoginActivity.this);
            }
        });
        button_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

}