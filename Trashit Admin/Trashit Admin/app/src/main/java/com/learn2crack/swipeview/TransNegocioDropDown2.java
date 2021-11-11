package com.learn2crack.swipeview;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class TransNegocioDropDown2 extends AsyncTask<String,  Void, String> {
    private static String result2;
    private static ArrayList<String> listBarrios = new ArrayList<String>();
    private static ArrayList<String> listMateriales = new ArrayList<String>();
    private Spinner lvBarrios;
    private Spinner lvMateriales;
    private Context context;
    ArrayAdapter<String> comboAdapterBarrio;
    ArrayAdapter<String> comboAdapterMateriales;
    private String idPunto = "0";
    SharedPreferences pref ;
    Set<String> listaDeMateriales = new HashSet<>();


    public TransNegocioDropDown2(Spinner lv1, Spinner lv2, Context ct)
    {
        lvBarrios = lv1;
        lvMateriales = lv2;
        context = ct;
    }
    public TransNegocioDropDown2(Spinner lv1, Spinner lv2, Context ct, String idABuscar)
    {
        lvBarrios = lv1;
        lvMateriales = lv2;
        context = ct;
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
            listaDeMateriales.clear();
            listBarrios.clear();

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
                rs = st.executeQuery("select m.descripcion from Material as m join Punto_Materiales as pm on m.ID = pm.ID_Material where Baja is null and ID_Punto = "+idPunto);
            }
            else
            {
                rs = st.executeQuery("select descripcion from Material ");
            }

            while(rs.next()) {
                String item = rs.getString("descripcion");
                listMateriales.add(item);
                if(idPunto != "0") {
                    listaDeMateriales.add(item);
                }
            }
            response = "Conexion exitosa";
            con.close();
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



        if( !listaDeMateriales.isEmpty()){
            pref = context.getSharedPreferences("listaDeMateriales",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putStringSet("listaDeMateriales",listaDeMateriales);
            editor.commit();
        }



    }

}
