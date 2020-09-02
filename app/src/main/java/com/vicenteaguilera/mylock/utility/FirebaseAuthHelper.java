package com.vicenteaguilera.mylock.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.vicenteaguilera.mylock.HomeActivity;
import com.vicenteaguilera.mylock.LoginActivity;
import com.vicenteaguilera.mylock.interfaces.Status;
import com.vicenteaguilera.mylock.models.User;

import java.util.Objects;

public class FirebaseAuthHelper
{
    // Initialize Firebase Auth
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Status status;
    private Context context;
    private final StringHelper stringHelper = new StringHelper();

    public void setContext(Context context)
    {
        this.context=context;
    }

    public static User getUser()
{
    return createUser(mAuth.getCurrentUser());
}
    private static User createUser(FirebaseUser firebaseUser)
    {
        return (firebaseUser!=null)?new User(/*uid*/firebaseUser.getUid()):null;
    }

    //registrarse
    public void createUserEmailAndPassword(final String email, String password, final ProgressDialog dialog)
    {
        if(stringHelper.isNotEmptyCredentials(email,password))
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //FirebaseUser user = mAuth.getCurrentUser();
                                status.status("Ingresando al sistema...");
                                //createUser(user);
                                dialog.dismiss();
                                Intent intent = new Intent(context, HomeActivity.class);
                                context.startActivity(intent);
                            }
                            else
                            {
                                String error = Objects.requireNonNull(task.getException()).getMessage();
                                if(Objects.equals(error, "The email address is already in use by another account."))
                                {
                                    status.status("Error en el registro, email registrado");
                                }
                                else if(Objects.equals(error, "The given password is invalid. [ Password should be at least 6 characters ]"))
                                {
                                    status.status("La contraseña debe de tener por lo menos 6 caracteres");
                                }
                                else
                                {
                                    status.status("Error al registrarse");
                                }
                                //createUser(null);
                                dialog.dismiss();
                            }
                        }
                    });

        }
        else
        {
            status.status("Error en el email o en el password");
        }

    }
    //ingresar
    public void signInWithEmailAndPassword(final String email, String password, final ProgressDialog dialog, final Context context)
    {
        if(stringHelper.isNotEmptyCredentials(email,password))
        {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity)context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                status.status("Ingresando al sistema...");
                                dialog.dismiss();
                                Intent intent = new Intent(context, HomeActivity.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();

                            }
                            else
                            {
                                String error = Objects.requireNonNull(task.getException()).getMessage();
                                switch (Objects.requireNonNull(error))
                                {
                                    case "There is no user record corresponding to this identifier. The user may have been deleted.":
                                        status.status("Esas credenciales no existen en la base de datos o el email es invalido");
                                        break;
                                    case "The password is invalid or the user does not have a password.":
                                        status.status("La contraseña es incorrecta");
                                        break;
                                    case "The user account has been disabled by an administrator.":
                                        status.status("Cuenta inhabilidada, contacta al administrador");
                                        break;
                                    default:
                                        status.status("Verifica tu conexión a Internet");
                                        break;
                                }
                                Log.e("error",error);
                                dialog.dismiss();
                            }
                        }
                    });
        }
        else
        {
            status.status("Error en el email o en el password");
        }
    }
    ///salir
    public void signout(final ProgressDialog dialog)
    {
       mAuth.signOut();
       dialog.dismiss();
       Intent intent = new Intent(context, LoginActivity.class);
       context.startActivity(intent);
       ((Activity )context).finish();
    }


    public void setOnStatusListener(Status status)
    {
        this.status=status;
    }



}
