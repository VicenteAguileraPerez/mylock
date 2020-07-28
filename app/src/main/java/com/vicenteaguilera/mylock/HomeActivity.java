package com.vicenteaguilera.mylock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vicenteaguilera.mylock.models.User;
import com.vicenteaguilera.mylock.utility.FirebaseAuthHelper;

public class HomeActivity extends AppCompatActivity {

    private Button button_logout;
    FirebaseAuthHelper firebaseAuthHelper = new FirebaseAuthHelper();

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuthHelper.setContext(this);
        //Toast.makeText(HomeActivity.this,FirebaseAuthHelper.getUser().getUid(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button_logout = findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ProgressDialog dialog = ProgressDialog.show(HomeActivity.this, "",
                        "Saliendo, nos vemos pronto!!...", true);
                dialog.show();
                firebaseAuthHelper.signout(dialog);
                User user = FirebaseAuthHelper.getUser();

                //Toast.makeText(HomeActivity.this,user==null?"Soy nulo":user.getUid(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}