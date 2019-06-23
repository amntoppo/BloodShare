package com.hack.bloodshare;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hack.bloodshare.adapter.RecyclerViewAdapter;
import com.hack.bloodshare.model.Receivers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PatientClass extends AppCompatActivity {

    //private final String JSONString = "https://api.myjson.com/bins/1cc78d";
    private final String JSONString = "https://api.myjson.com/bins/dd07p";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Receivers> receiversList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerviewid);
        receiversList = new ArrayList<>();
        JsonRequest();
    }
    private void JsonRequest() {
        //Log.e(JSONString)
        request = new JsonArrayRequest(JSONString, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                Log.e("Json", response.toString());

                //String responsestring = response.toString().substring(10, response.length()-10);
                //System.out.println(responsestring);
                //response.toJSONObject()
               // Log.e("Json", responsestring);
                for(int i=0; i< response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Receivers receivers = new Receivers();
                        receivers.setName(jsonObject.getString("name"));
                        receivers.setGroup(jsonObject.getString("group"));
                        receivers.setLocation(jsonObject.getString("location"));
                        receiversList.add(receivers);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                setupRecyclerView(receiversList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Json", error.toString());
            }
        });
        requestQueue = Volley.newRequestQueue(PatientClass.this);
        requestQueue.add(request);
    }
    private void setupRecyclerView(List<Receivers> receiversList) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, receiversList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
