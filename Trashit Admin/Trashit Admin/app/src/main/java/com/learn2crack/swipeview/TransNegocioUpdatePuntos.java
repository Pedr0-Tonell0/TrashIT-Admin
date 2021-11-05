package com.learn2crack.swipeview;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TransNegocioUpdatePuntos extends AsyncTask<String,  Void, String>

    {

        private Puntos Puntos;
        private Context context;

    public TransNegocioUpdatePuntos(Context ct, Puntos Puntos1)
        {
            Puntos = Puntos1;
            context = ct;
        }

        @Override
        protected String doInBackground(String... strings) {
        String response = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet flag = st.executeQuery("SELECT * FROM Puntos_Reciclado WHERE id=" + Puntos.getId());

            if(flag.next()){
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
                int Resultado1 = st.executeUpdate("UPDATE Puntos_Reciclado set Direccion= '" + Puntos.getDireccion() + "'" + ", Materiales = '" + Puntos.getMaterial() + "'" + ",  Barrio=" + Puntos.getIdBarrio() + ",geometrycoordinates_x=" + Puntos.getLongitud() + ",geometrycoordinates_y = " + Puntos.getLatitud()  + " WHERE id=" + Puntos.getId());
                int Resultado2 = st.executeUpdate("UPDATE Punto_Materiales set Id_Material= "+ Puntos.getIdMaterial() + " WHERE id_Punto=" + Puntos.getId());
                if(Resultado1 > 0 && Resultado2 > 0)
                {
                    response = "Se ha actualizado el punto de reciclaje";
                }
            } else return "No se ha podido actualizar el punto de reciclaje";con.close();
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            response = "No se ha podido actualizar el el punto de reciclaje";
        }
        return response;
    }
        @Override
        protected void onPostExecute(String response) {
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
    }
}
