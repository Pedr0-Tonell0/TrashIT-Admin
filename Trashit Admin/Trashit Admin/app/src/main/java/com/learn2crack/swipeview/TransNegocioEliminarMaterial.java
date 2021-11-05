package com.learn2crack.swipeview;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TransNegocioEliminarMaterial extends AsyncTask<String,  Void, String> {

    public String id;
    public Context ct;
    public EditText informacion;
    public EditText imagen;
    public EditText materialName;

    public TransNegocioEliminarMaterial(String id, Context ct, EditText informacion, EditText imagen, EditText materialName) {
    this.id = id;
    this.ct = ct;
    this.informacion = informacion;
    this.imagen = imagen;
    this.materialName = materialName;
    }

    @Override
    protected String doInBackground(String... strings) {

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            int rs1 = st.executeUpdate("delete from Material where id = '" + this.id+ "';");
            if(rs1 > 0)
            {
                this.imagen.setText("");
                this.informacion.setText("");
                this.materialName.setText("");
                con.close();
                return "Se ha eliminado el registro exitosamente";
            }
            con.close();
            return "Ha ocurrido un error al eliminar el registro";
        }catch(Exception e){
            return e.getMessage();
        }
    }

    protected void onPostExecute(String response) {
        Toast.makeText(ct, response, Toast.LENGTH_SHORT).show();
    }
}
