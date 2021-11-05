package com.learn2crack.swipeview;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TransNegocioInsertPuntos extends AsyncTask<String,  Void, String> {

    private Puntos Puntos;
    private Context context;

    public TransNegocioInsertPuntos(Context ct, Puntos Products1)
    {
        Puntos = Products1;
        context = ct;
    }

    @Override
    protected String doInBackground(String... strings) {
        int UltimoRegistro = 0;
        String response = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
             ResultSet rs1 = st.executeQuery("SELECT id FROM Material WHERE descripcion='" + Puntos.getMaterial() + "'");
                if(rs1.first())
                {
                    Puntos.setIdMaterial(rs1.getInt("id"));
                }
            ResultSet rs2 = st.executeQuery("SELECT id FROM Barrio WHERE descripcion='" + Puntos.getBarrio() + "'");
            if(rs2.first())
            {
                Puntos.setIdBarrio(rs2.getInt("id"));
            }
            int Resultado1 = st.executeUpdate("INSERT INTO Puntos_Reciclado (type,Direccion,Barrio,Materiales,Mas_Info,geometrytype,geometrycoordinates_x,geometrycoordinates_y,comuna)" + "VALUES ( 'Feature'," + "'" + Puntos.getDireccion() + "'" + "," + Puntos.getIdBarrio() + "," + "'" + Puntos.getMaterial() + "'" + ", 'Los materiales deben estar limpios y secos', 'Point'," + Puntos.getLongitud() +"," + Puntos.getLatitud() +",1)");
            ResultSet rs3 = st.executeQuery("SELECT id FROM Puntos_Reciclado order by id desc limit 1");
            if(rs3.first())
            {
                UltimoRegistro = rs3.getInt("id");
            }
            int Resultado2 = st.executeUpdate("INSERT INTO Punto_Materiales (ID_Punto,ID_Material)" + "VALUES (" + UltimoRegistro+ "," +  Puntos.getIdMaterial() + ")");
            if(Resultado2 > 0 && Resultado1 >0)
                {
                    response = "Punto de reciclaje agregado correctamente";
                }
            else
            {
                response = "No se ha podido agregar el punto de reciclaje";
            }
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            response = "No se ha podido agregar el punto de reciclaje";
        }
        return response;
    }
    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
    }
}
