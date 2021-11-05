package com.learn2crack.swipeview;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class TransNegocioDropDown2 extends AsyncTask<String,  Void, String> {
    private static String result2;
    private static ArrayList<String> listBarrios = new ArrayList<String>();
    private static ArrayList<String> listMateriales = new ArrayList<String>();
    private Spinner lvBarrios;
    private Spinner lvMateriales;
    private Context context;
    ArrayAdapter<String> comboAdapterBarrio;
    ArrayAdapter<String> comboAdapterMateriales;

    public TransNegocioDropDown2(Spinner lv1, Spinner lv2, Context ct)
    {
        lvBarrios = lv1;
        lvMateriales = lv2;
        context = ct;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select descripcion from Barrio ");
            while(rs.next()) {
                String item = rs.getString("descripcion");
                listBarrios.add(item);
            }
            rs = st.executeQuery("select descripcion from Material ");
            while(rs.next()) {
                String item = rs.getString("descripcion");
                listMateriales.add(item);
            }
            response = "Conexion exitosa";
        }
        catch(Exception e) {
            e.printStackTrace();
            result2 = "Conexion no exitosa";
        }
        return response;
    }
    @Override
    protected void onPostExecute(String response) {
        comboAdapterBarrio = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, listBarrios);
        lvBarrios.setAdapter(comboAdapterBarrio);
        comboAdapterMateriales = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, listMateriales);
        lvMateriales.setAdapter(comboAdapterMateriales);
    }

}
