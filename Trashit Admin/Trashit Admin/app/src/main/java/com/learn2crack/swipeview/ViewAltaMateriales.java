package com.learn2crack.swipeview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewAltaMateriales extends Fragment {

    public static final String TITLE = "ALTA";
    EditText material, informacion, imagen;
    Button agregar;

    public static ViewAltaMateriales newInstance() {

        return new ViewAltaMateriales();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_alta_materiales, container, false);
        material = (EditText) v.findViewById(R.id.Material);
        informacion = (EditText) v.findViewById(R.id.Informacion);
        imagen = (EditText) v.findViewById(R.id._imagen);
        agregar = (Button) v.findViewById(R.id.Modificar);
        agregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(
                        material.getText().toString().equals("") ||
                        informacion.getText().toString().equals("") ||
                        imagen.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Complete todos los campos",Toast.LENGTH_SHORT).show();
                } else {
                    ViewAltaMateriales.this.crearMaterial();
                }
            }
        });
        return v;
    }

    public void crearMaterial()
    {
        TransNegocioInsertMaterial task = new TransNegocioInsertMaterial(
                material.getText().toString(),
                informacion.getText().toString(),
                imagen.getText().toString(),
                this.getContext());
        task.execute();
    }
}