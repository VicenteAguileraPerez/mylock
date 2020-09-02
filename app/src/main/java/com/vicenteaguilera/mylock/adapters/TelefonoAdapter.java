package com.vicenteaguilera.mylock.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.vicenteaguilera.mylock.R;
import com.vicenteaguilera.mylock.models.Telefono;
import com.vicenteaguilera.mylock.utility.FirestoreHelper;
import java.util.List;



public class TelefonoAdapter extends BaseAdapter
{
    LayoutInflater inflater;
    private int list_item_layout;
    private Context context;
    private List<Telefono> telefonos;

    public TelefonoAdapter(Context context,int list_item_layout,List<Telefono> telefonos)
    {
        this.context=context;
        this.list_item_layout = list_item_layout;
        this.telefonos=telefonos;
        inflater = (LayoutInflater) ((Activity)context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /// indicare cuantos elementos va a mostras
    @Override
    public int getCount() {
        return telefonos.size();
    }
    //retornar Objetos de tipo Telefono (Modelo)
    @Override
    public Object getItem(int position) {
        return telefonos.get(position);
    }
    //id
    @Override
    public long getItemId(int position) {
        return telefonos.indexOf(telefonos.get(position));
    }

    @Override
    public View getView(final int position, View contentView, ViewGroup parent)
    {
        final Telefono telefono = telefonos.get(position);
        TelefonoHolder telefonoHolder=null;
        if(contentView == null)
        {
            contentView = inflater.inflate(list_item_layout,null);
            telefonoHolder = new TelefonoHolder();

            telefonoHolder.textView_nombre = contentView.findViewById(R.id.textView_nombre_completo);
            telefonoHolder.textView_telefono = contentView.findViewById(R.id.textView_telefono);
            telefonoHolder.textView_email = contentView.findViewById(R.id.textView_email);
            telefonoHolder.imageButton_delete = contentView.findViewById(R.id.imageButton_delete);
            telefonoHolder.imageButton_edit = contentView.findViewById(R.id.imageButton_edit);
            contentView.setTag(telefonoHolder);
            telefonoHolder.textView_nombre.setSelected(true);
            telefonoHolder.textView_nombre.setText(telefono.getNombre().concat(" ").concat(telefono.getApellido()));
            telefonoHolder.textView_telefono.setText(telefono.getTelefono());
            telefonoHolder.textView_email.setText(telefono.getEmail());

            telefonoHolder.imageButton_edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                }
            });
            telefonoHolder.imageButton_delete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    final AlertDialog.Builder  alertDialogBuilder = new AlertDialog.Builder(TelefonoAdapter.this.context);
                    alertDialogBuilder.setTitle("Eliminación");
                    alertDialogBuilder.setMessage("¿Quieres eliminar el teléfono de: "+telefono.getNombre()+"?");
                    alertDialogBuilder.setPositiveButton("Eliminar",
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface alertDialog, int i)
                        {
                            //eliminacion de firebase
                            new FirestoreHelper().deleteTelefono(telefono.getUid());
                            // eliminar de la lista telefonos que tenemos con el metodo ???
                            telefonos.remove(position);
                            // notificacion del cambio en la lista
                            notifyDataSetChanged();

                            alertDialog.cancel();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface alertDialog, int i) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialogBuilder.show();

                    /**
                     *
                     */

                }
            });

        }
        else
        {
            contentView.getTag();
        }

        return contentView;
    }
    static class TelefonoHolder
    {
        public TextView textView_nombre,textView_telefono,textView_email;
        public ImageButton imageButton_delete, imageButton_edit;
    }
}

