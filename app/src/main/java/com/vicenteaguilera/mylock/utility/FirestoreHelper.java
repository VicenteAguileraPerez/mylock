package com.vicenteaguilera.mylock.utility;


import android.app.ProgressDialog;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vicenteaguilera.mylock.interfaces.Status;
import com.vicenteaguilera.mylock.interfaces.Telefonos;
import com.vicenteaguilera.mylock.models.Telefono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirestoreHelper
{
    private Status status;
    private Telefonos telefonos;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference directorio = db.collection("telefonos");

    public void addTelefono(Telefono telefono, final ProgressDialog dialog)
    {
       Map<String,Object> telefonoMap = new HashMap<>();
       telefonoMap.put("nombre",telefono.getNombre());
       telefonoMap.put("apellido",telefono.getApellido());
       telefonoMap.put("telefono",telefono.getTelefono());
       telefonoMap.put("email",telefono.getEmail());

       directorio.document(telefono.getUid()).set(telefonoMap).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task)
           {
               if(task.isComplete())
               {
                   status.status("Télefono agregado");
                   System.out.println("agregado");
               }
               else
               {
                   status.status("Télefono no agregado");
                   System.out.println("error"+ Objects.requireNonNull(task.getException()).getMessage());
               }
               dialog.dismiss();
           }
       });

    }
    public void deleteTelefono(String iDtelefono)
    {
        directorio.document(iDtelefono).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isComplete()){
                    System.out.println("eliminado");
                }
                else
                {
                    System.out.println("error"+ Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }
    public void getAllTelefonos()
    {
        final List<Telefono> telefonoList = new ArrayList<>();
        directorio.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isComplete())
                {
                    Map<String,Object> telefono;
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                    {
                       telefono = document.getData();
                       telefonoList.add(
                               new Telefono
                               (
                                   document.getId(),
                                   String.valueOf(telefono.get("nombre")),
                                   String.valueOf(telefono.get("apellido")),
                                   String.valueOf(telefono.get("telefono")),
                                   String.valueOf(telefono.get("email"))
                               )
                       );
                    }
                    //interface
                    telefonos.getAll(telefonoList);
                }
                else
                {
                    System.out.println("error"+task.getException().getMessage());
                    status.status("Error al cargar la lista verifica tu conexión");
                }
            }
        });
    }
    public void setOnStatusListener(Status status)
    {
        this.status=status;
    }
    public void setOnTelefonosListener(Telefonos telefonos)
    {
        this.telefonos = telefonos;
    }
}
