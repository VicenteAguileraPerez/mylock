package com.vicenteaguilera.mylock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vicenteaguilera.mylock.models.User;
import com.vicenteaguilera.mylock.utility.FirebaseAuthHelper;

public class HomeActivity extends AppCompatActivity {

   private CameraManager cameraManager;
   private  String camaraId;
   private boolean isActive = false;
   private FirebaseAuthHelper firebaseAuthHelper = new FirebaseAuthHelper();

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuthHelper.setContext(this);
        //Toast.makeText(HomeActivity.this,FirebaseAuthHelper.getUser().getUid(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            camaraId = cameraManager.getCameraIdList()[0];
        }
        catch ( CameraAccessException cae)
        {
            Toast.makeText(HomeActivity.this,"This phone doesn't have camaras",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case  R.id.item_cerrar_sesion:
                ProgressDialog dialog = ProgressDialog.show(HomeActivity.this, "",
                        "Saliendo, nos vemos pronto!!...", true);
                dialog.show();
                firebaseAuthHelper.signout(dialog);
                break;
            case  R.id.item_lampara:
                isActive = !isActive;
                flashOnAndOff(camaraId,isActive);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void flashOnAndOff(String camaraId,boolean enable)
    {
        try {
            cameraManager.setTorchMode(camaraId,enable);
        }
        catch (CameraAccessException cae)
        {
            Toast.makeText(HomeActivity.this,"Opps, this camara doesn't flash",Toast.LENGTH_SHORT).show();

        }
    }
}