package com.vicenteaguilera.mylock;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.vicenteaguilera.mylock.adapters.TelefonoAdapter;
import com.vicenteaguilera.mylock.interfaces.Status;
import com.vicenteaguilera.mylock.interfaces.Telefonos;
import com.vicenteaguilera.mylock.models.Telefono;
import com.vicenteaguilera.mylock.utility.FirestoreHelper;
import java.util.List;
import java.util.Objects;
import static androidx.navigation.Navigation.findNavController;


public class ListaFragment extends Fragment implements Status, Telefonos {

    private ListView listView_telefonos;
    private ProgressBar progressBar;
    private ImageButton imageButton_add;
    private FirestoreHelper firestoreHelper = new FirestoreHelper();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestoreHelper.setOnStatusListener(this);
        firestoreHelper.setOnTelefonosListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView_telefonos = view.findViewById(R.id.listView_telefonos);
        imageButton_add = view.findViewById(R.id.imageButton_add);
        progressBar = view.findViewById(R.id.progressBar);
        imageButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //import static androidx.navigation.Navigation.findNavController;
               findNavController(requireView()).navigate(
                       R.id.action_listaFragment_to_addElementFragment);
            }
        });

    }
    @Override
    public void onStart()
    {
        firestoreHelper.getAllTelefonos();
        super.onStart();
    }



    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void status(String message)
    {
        Toast.makeText(Objects.requireNonNull(getView()).getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAll(List<Telefono> telefonoList)
    {
        progressBar.setVisibility(View.INVISIBLE);
        listView_telefonos.setVisibility(View.VISIBLE);
        TelefonoAdapter adapterTelefono = new TelefonoAdapter(getContext(),R.layout.list_item_telefono,telefonoList);
        listView_telefonos.setAdapter(adapterTelefono);
    }
}