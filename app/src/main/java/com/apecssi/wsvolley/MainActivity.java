package com.apecssi.wsvolley;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.apecssi.wsvolley.model.Publicacion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> datos= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.lstPublicaciones);
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,datos);
        listView.setAdapter(arrayAdapter);
        getDatos();
    }

    private void getDatos(){
        String url="https://jsonplaceholder.typicode.com/";//endpoint.
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                pasarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);//hacemos la peticion al API
    }

    private void pasarJson( JSONArray array){

        for(int i=0;i<array.length();i++){
            JSONObject json=null;
            Publicacion publicacion=new Publicacion();

            try {
                json=array.getJSONObject(i);
                publicacion.setUserId(json.getInt("userId"));//como viene del API
                publicacion.setId(json.getInt("id"));
                publicacion.setCuerpo(json.getString("body"));
                publicacion.setTitulo(json.getString("title"));
                datos.add(publicacion.getCuerpo());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        arrayAdapter.notifyDataSetChanged();
    }

}