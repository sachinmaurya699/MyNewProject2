package com.womsolution.mynewproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Form_Activity extends AppCompatActivity {
    private CalendarView calendarView;
    private DatePickerDialog datePickerDialog;
    private  String text1,text2,text3,text4,text5;
    private TextView Name,contect_no,Date_of_function,Number_of_person,choice_of_item;
    private TextView textView1,textView2,textView3,textView4,textView5;
    private Button sub,Reset,Data;
    int year;
    int month;
    int dayofmonth;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_);

        Name=findViewById(R.id.text_name);
        contect_no=findViewById(R.id.text_connectno);
        Date_of_function=findViewById(R.id.text_date);
        Number_of_person=findViewById(R.id.text_noperson);
        choice_of_item=findViewById(R.id.text_choiseItem);
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);

        textView3=findViewById(R.id.textView3);
        /*textView3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(textView3.isFocused())
                {
                    dilog();
                }
            }
        });*/
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayofmonth=calendar.get(Calendar.DAY_OF_MONTH);


                datePickerDialog= new DatePickerDialog(Form_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                Calendar cal=Calendar.getInstance();
                                cal.set(Calendar.YEAR,year);
                                cal.set(Calendar.MONTH,month);
                                cal.set(Calendar.DAY_OF_MONTH,dayofmonth);
                                if(cal.before(calendar))
                                {

                                }
                                StringBuilder date= new StringBuilder();
                                date.append((dayofmonth<10?"0":"")).append(dayofmonth)
                                        .append("-").append((month + 1) < 10 ? "0" : "")
                                        .append((month+1)).append("-").append(year);


                                textView3.setText(day+"/"+month+"/"+year);
                            }
                        },year,month,dayofmonth);
                      datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

                      datePickerDialog.show();
            }
        });
        textView4=findViewById(R.id.textView4);
        textView5=findViewById(R.id.textView5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        sub=findViewById(R.id.button_sub);
        Reset=findViewById(R.id.button_reset);
        Data=findViewById(R.id.button_Cancel);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text1=textView1.getText().toString();
                text2=textView2.getText().toString();
                text3=textView3.getText().toString();
                text4=textView4.getText().toString();
                text5=textView5.getText().toString();

                 if(text1.isEmpty())
                 {
                     textView1.setError("Enter the name");
                     textView1.setFocusable(true);
                 }
                  else if(text2.isEmpty())
                 {
                     textView2.setError("Enter the number");
                 }
                    else if(text2.length()!=10)
                 {
                     textView2.setError("Enter valied phone number");
                     textView2.setFocusable(true);
                 }
                   else if(text3.isEmpty())
                 {
                     textView3.setError("Enter the date");
                     textView3.setFocusable(true);
                 }
                    else if(text4.isEmpty())
                 {
                     textView4.setError("Enter the no of person");
                     textView4.setFocusable(true);
                 }
                   else if(text5.isEmpty())
                 {
                     textView5.setError("Enter the name of item");
                     textView5.setFocusable(true);
                 }
                 else
                 {
                     submetapi(text1,text2,text3,text4,text5);
                     textView1.setText("");
                     textView2.setText("");
                     textView3.setText("");
                     textView4.setText("");
                     textView5.setText("");
                 }


            }
        });
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(Form_Activity.this, SecondForm.class);
                startActivity(intent);


            }
        });
        Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(Form_Activity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }


    private void submetapi(String text1,String text2,String text3,String text4,String text5)
    {
          String url="http://www.speshenterprises.com/CaterersDeskHandler.ashx?RequestType=SaveCustomerSignup&Name="+text1+"&MobileNo="+text2+"&DateOfFunction="+text3+"&NoOfGuest="+text4+"&Menu="+text5;

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Form_Activity.this, "onresponce", Toast.LENGTH_SHORT).show();
                        String s1=response.replaceAll("\\\\","");
                        String s2=s1.substring(1,s1.length()-1);


                        try {
                            JSONArray jsonArray= new JSONArray(s2);
                            JSONObject jsonObject= jsonArray.getJSONObject(0);
                            String result=jsonObject.getString("Result");
                            Toast.makeText(Form_Activity.this,result, Toast.LENGTH_SHORT).show();
                            if(result.equals("1"))
                            {
                                Toast.makeText(Form_Activity.this, "Data hasbeen saved", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(Form_Activity.this, "not submit", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error>>>>",e.toString());
                            Toast.makeText(Form_Activity.this,e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: " + error.getMessage());
                Log.d("error>>>>>>",error.toString());
                Toast.makeText(Form_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap= new HashMap();
                hashMap.put("Content-Type","application/json; charset=utf-8");
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy (
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache (false);
        Volley.newRequestQueue(this).add (stringRequest);
    }

}
