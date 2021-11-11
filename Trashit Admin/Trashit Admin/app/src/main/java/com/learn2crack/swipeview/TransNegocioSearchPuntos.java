package com.learn2crack.swipeview;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mysql.fabric.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TransNegocioSearchPuntos extends AsyncTask<String,  Void, String> {
    private Puntos puntos;
    private Context context;
    private String IdRegistro;
    Spinner material, barrio;
    EditText id, latitud, longitud, direccion;
    ArrayAdapter<String> comboAdapterBarrio;
    ArrayAdapter<String> comboAdapterMaterial;

    public TransNegocioSearchPuntos(Context ct, String Id, EditText id, EditText latitud, EditText longitud, EditText direccion, Spinner barrio, Spinner material)
    {
        IdRegistro = Id;
        context = ct;
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.material = material;
        this.barrio = barrio;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select id,direccion, barrio, pm.id_material,geometrycoordinates_y, geometrycoordinates_x from Puntos_Reciclado as pr inner join Punto_Materiales as pm on pm.id_punto = pr.id where id =" + IdRegistro + " limit 1");
                if(rs.next())
                {
                    puntos = new Puntos();
                    puntos.setId(rs.getInt("id"));
                    puntos.setDireccion(rs.getString("direccion"));
                    puntos.setIdBarrio(rs.getInt("barrio"));
                    puntos.setIdMaterial(rs.getInt("pm.id_material"));
                    puntos.setLatitud(rs.getDouble("geometrycoordinates_y"));
                    puntos.setLongitud(rs.getDouble("geometrycoordinates_x"));
                    response = "Se encontro un punto de reciclaje";
                }
                else
                {
                    response = "No se ha encontrado ningun punto de reciclaje";
                }
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            response = "No se ha encontrado ningun punto de reciclaje";
        }
        return response;
    }
    @Override
    protected void onPostExecute(String response) {

        if(response == "Se encontro un punto de reciclaje")
        {
            if(puntos != null)
            {
                //id.setText(puntos.getId());
                direccion.setText(puntos.getDireccion());
                latitud.setText(puntos.getLatitud().toString());
                longitud.setText(puntos.getLongitud().toString());
                int selectionPosition1= puntos.getIdBarrio();
                barrio.setSelection(selectionPosition1-1);
                int selectionPosition2= puntos.getIdMaterial();
                material.setSelection(selectionPosition2-1);
            }
        }
        else {
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        }
    }
}

