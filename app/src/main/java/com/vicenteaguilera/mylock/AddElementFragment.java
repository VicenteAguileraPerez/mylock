package com.vicenteaguilera.mylock;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.vicenteaguilera.mylock.interfaces.Status;
import com.vicenteaguilera.mylock.models.Telefono;
import com.vicenteaguilera.mylock.utility.FirestoreHelper;
import com.vicenteaguilera.mylock.utility.StringHelper;

import java.util.Objects;
import java.util.UUID;


public class AddElementFragment extends Fragment implements Status
{
    private EditText editText_nombre,editText_apellidos,editText_telefono,editText_email;
    private CardView cardView;
    private FirestoreHelper firestoreHelper = new FirestoreHelper();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Status status = this;
        firestoreHelper.setOnStatusListener(status);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_element, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText_nombre = view.findViewById(R.id.editText_nombre);
        editText_apellidos = view.findViewById(R.id.editText_apellidos);
        editText_telefono = view.findViewById(R.id.editText_telefono);
        editText_email = view.findViewById(R.id.editText_email);
        cardView = view.findViewById(R.id.button_agregar);
        onClick();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    public void onClick()
    {

       cardView.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View view)
           {
               ProgressDialog dialog = ProgressDialog.show(Objects.requireNonNull(getView()).getContext(), "",
                       "Cargando. Porfa espera...", true);
               dialog.show();
               String nombre=editText_nombre.getText().toString();
               String apellidos = editText_apellidos.getText().toString();
               String telefono = editText_telefono.getText().toString();
               String email = editText_email.getText().toString();
               if(!nombre.isEmpty()){
                   if(!apellidos.isEmpty()){
                       if (telefono.length()==10)
                       {
                           if(new StringHelper().isEmail(email))
                           {
                               firestoreHelper.addTelefono(new Telefono(UUID.randomUUID().toString(),nombre,apellidos,telefono,email),dialog);
                           }
                           else
                           {
                               editText_email.setError("Email erróneo");
                               editText_email.getText().clear();
                               dialog.dismiss();
                           }
                       }
                       else
                       {
                           editText_telefono.setError("Teléfono incompleto");
                           editText_telefono.getText().clear();
                           dialog.dismiss();
                       }
                   }
                   else
                   {
                       editText_apellidos.setError("Campo requerido");
                       editText_apellidos.getText().clear();
                       dialog.dismiss();
                   }
               }
               else
               {
                   editText_nombre.setError("Campo requerido");
                   editText_nombre.getText().clear();
                   dialog.dismiss();
               }
           }
       });


    }

    @Override
    public void status(String message)
    {
        if(message.equals("Télefono agregado"))
        {
            editText_nombre.getText().clear();
            editText_apellidos.getText().clear();
            editText_telefono.getText().clear();
            editText_email.getText().clear();
        }
        Toast.makeText(Objects.requireNonNull(getView()).getContext(),message,Toast.LENGTH_SHORT).show();

    }
}