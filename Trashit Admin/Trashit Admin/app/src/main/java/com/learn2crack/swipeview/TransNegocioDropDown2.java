package com.learn2crack.swipeview;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebIconDatabase;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    TableRow registro;
    TableLayout tabla;
    TextView textView;
    private String idPunto = "0";

    public TransNegocioDropDown2(Spinner lv1, Spinner lv2, Context ct, TableLayout tab, TableRow row)
    {
        lvBarrios = lv1;
        lvMateriales = lv2;
        context = ct;
        tabla = tab;
        registro = row;
    }
    public TransNegocioDropDown2(Spinner lv1, Spinner lv2, Context ct, TableLayout tab, TableRow row,String idABuscar)
    {
        lvBarrios = lv1;
        lvMateriales = lv2;
        context = ct;
        tabla = tab;
        registro = row;
        idPunto = idABuscar;

    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs;
            listMateriales.clear();
            //if(idPunto != 0){
              //   rs = st.executeQuery("select descripcion from Barrio where id = "+idPunto);
           // }
            //else {
                rs = st.executeQuery("select descripcion from Barrio ");
            //}

            while(rs.next()) {
                String item = rs.getString("descripcion");
                listBarrios.add(item);
            }
            if(idPunto != "0"){
                rs = st.executeQuery("select m.descripcion from Material as m join Punto_Materiales as pm on m.ID = pm.ID_Material where ID_Punto = "+idPunto);
            }
            else
            {
                rs = st.executeQuery("select descripcion from Material ");
            }

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

        Integer contador = 0;
        for (String material: listMateriales) {


            Button boton = new Button(context);
            boton.setId(contador);

           // boton.setBackgroundResource(R.drawable._5422);
           // boton.getLayoutParams().height = 20;
            //boton.getLayoutParams().width = 20;
            registro = new TableRow(context);
            textView = new TextView(context);
            textView.setText(material);
            registro.addView(boton);
            registro.addView(textView);
            tabla.addView(registro);
            contador ++;


        }

        comboAdapterBarrio = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, listBarrios);
        lvBarrios.setAdapter(comboAdapterBarrio);
        comboAdapterMateriales = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, listMateriales);
        lvMateriales.setAdapter(comboAdapterMateriales);
    }

}
