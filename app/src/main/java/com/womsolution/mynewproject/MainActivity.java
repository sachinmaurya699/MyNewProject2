package com.womsolution.mynewproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Dataitems currentData;
    private List<Dataitems> dataitems;
    private InfoAda madapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        dataitems= new ArrayList<>();
        hitapi();

    }
    private void hitapi() {

        String url="http://speshenterprises.com/CaterersDeskHandler.ashx?RequestType=GetWithCommonProc&Flag=5&Param=";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String s1=response.replaceAll("\\\\","");
                        String s2=s1.substring(1,s1.length()-1);

                        try {
                            JSONArray jsonArray= new JSONArray(s2);
                           for(int i=0;i<jsonArray.length();i++)
                           {
                               Dataitems data= new Dataitems();
                               data.setName(jsonArray.getJSONObject(i).getString("Name"));
                               data.setContect_no(jsonArray.getJSONObject(i).getString("MobileNo"));
                               data.setDateoffunction(jsonArray.getJSONObject(i).getString("DateOfFunction"));
                               data.setNo_of_person(jsonArray.getJSONObject(i).getString("NoOfGuest"));
                               data.setName_of_item(jsonArray.getJSONObject(i).getString("Menu"));
                               dataitems.add(data);
                           }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        madapter=new InfoAda(getApplicationContext(),dataitems);
                        recyclerView.setAdapter(madapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error>>>",error.toString());
                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> hashMap= new HashMap<String, String>();
                hashMap.put("Content-Type", "application/json; charset=utf-8");
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        stringRequest.setShouldCache (false);
        // NetworkController.getInstance().addToRequestQueue(jsonReq);
        Volley.newRequestQueue(MainActivity.this).add (stringRequest);


    }

    public class InfoAda extends RecyclerView.Adapter<Imageviewholder> {

       private Context mContext;
       private List<Dataitems> dataitems;
       public InfoAda( Context context,List<Dataitems> dataitems1)
       {
            mContext=context;
            dataitems=dataitems1;

       }
       @NonNull
       @Override
       public Imageviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
       {
           View view= LayoutInflater.from(mContext).inflate(R.layout.design,parent,false);

           return new Imageviewholder(view);
       }

       @Override
       public void onBindViewHolder(@NonNull Imageviewholder holder, int position) {

           final Dataitems dataitem= dataitems.get(position);
           holder.text1.setText(dataitem.getName());
           holder.text2.setText(dataitem.getContect_no());
           holder.text3.setText(dataitem.getDateoffunction());
           holder.text4.setText(dataitem.getNo_of_person());
           holder.text5.setText(dataitem.getName_of_item());


       }

       @Override
       public int getItemCount() {
           return dataitems.size();
       }
   }

    private class Imageviewholder extends RecyclerView.ViewHolder {

        TextView text1,text2,text3,text4,text5;
        public Imageviewholder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.text1);
            text2=itemView.findViewById(R.id.text2);
            text3=itemView.findViewById(R.id.text3);
            text4=itemView.findViewById(R.id.text4);
            text5=itemView.findViewById(R.id.text5);
        }
    }
}
