package com.learn2crack.swipeview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class ViewModificacionMateriales extends Fragment {

    public static final String TITLE = "MODIFICACION";
    public EditText id;
    public EditText informacion;
    public EditText imagen;
    public EditText materialName;
    public Button buscar;
    public Button eliminar;
    public Button modificar;

    protected ArrayAdapter<CharSequence> adapter;
    public static ViewModificacionMateriales newInstance() {

        return new ViewModificacionMateriales();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_modificacion_materiales,container,false);
        id = (EditText) v.findViewById(R.id.Id);
        informacion = (EditText) v.findViewById(R.id.Informacion);
        imagen = (EditText) v.findViewById(R.id._imagen);
        buscar = (Button) v.findViewById(R.id.Buscar);
        eliminar = (Button) v.findViewById(R.id.Eliminar);
        materialName = (EditText) v.findViewById(R.id.MaterialName);
        modificar = (Button) v.findViewById(R.id.Modificar);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        informacion.addTextChangedListener(textWatcher);
        imagen.addTextChangedListener(textWatcher);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewModificacionMateriales.this.buscar();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewModificacionMateriales.this.eliminar();
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(informacion.getText().toString().equals("") ||
                    imagen.getText().toString().equals("") ||
                    materialName.getText().toString().equals("") )
                {
                    Toast.makeText(getContext(),
                            "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ViewModificacionMateriales.this.modificar();
            }
        });

        return v;
    }

    public void buscar()
    {
        TransNegocioBuscarMaterial task = new TransNegocioBuscarMaterial(this.id, this.informacion, this.imagen, this.materialName,getContext());
        task.execute();
    }

    public void eliminar()
    {
        TransNegocioEliminarMaterial task = new TransNegocioEliminarMaterial(
                this.id.getText().toString(), getContext(), this.informacion, this.imagen, materialName);
        task.execute();
    }

    public void modificar()
    {
        TransNegocioModificarMaterial task = new TransNegocioModificarMaterial(
                getContext(),
                informacion.getText().toString(),
                imagen.getText().toString(),
                materialName.getText().toString(),
                id.getText().toString()
        );
        task.execute();
    }

}