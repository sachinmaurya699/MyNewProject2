package com.womsolution.mynewproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondForm extends AppCompatActivity {

    private TextView text_name,text_mobile,text_Gender,text_dateofbirth,text_pincode,text_pan,
            text_Restaurant_code;
    private EditText name,mobile,DOB,pincode,pannumber1,pannumber2,pannumber3,restaurant_code;
     private Button applynow;
    private String Name,Mobile,Gender,DateofBirth,PanNumber1,PanNumber2,PanNumber3,Pincode,Restaurantcode,Spinner;

    private DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayofmonth;
    private Spinner spinner;
    private Calendar calendar;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.background);
        /*<.........background.......>*/
        text_name=findViewById(R.id.textView_name);
        text_mobile=findViewById(R.id.textView_mobile);
        text_Gender=findViewById(R.id.textView_gender);
        text_dateofbirth=findViewById(R.id.textView_DOB);
        text_pincode=findViewById(R.id.textView_pincode);
        text_pan=findViewById(R.id.textView_PanNumber);
        text_Restaurant_code=findViewById(R.id.textView_restaurantcode);
       /* gender=findViewById(R.id.edit_gender);*/
        spinner=findViewById(R.id.spinner);
        /*........................create spiner.......*/
       final String[]s1={"Select gender","Male","Female"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,s1);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        name=findViewById(R.id.edit_name);

        mobile=findViewById(R.id.edit_mobile);

        DOB=findViewById(R.id.edit_DOB);
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                dayofmonth=calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog=new DatePickerDialog(SecondForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String monthString = String.valueOf(month);
                        if (monthString.length() == 1) {
                            monthString = "0" + monthString;
                            DOB.setText(day+"/"+monthString+"/"+year);
                        }


                    }
                },year,month,dayofmonth);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        pincode=findViewById(R.id.edit_pincode);
        pannumber1=findViewById(R.id.pannumber1);
        pannumber2=findViewById(R.id.pannumber2);
        pannumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PanNumber2= pannumber2.getText().toString().trim();

                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

                Matcher matcher = pattern .matcher(PanNumber2);

                if (matcher .matches()) {
                    Toast.makeText(getApplicationContext(), PanNumber2+" is Matching",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), PanNumber2+" is Not Matching",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        pannumber3=findViewById(R.id.pannumber3);
        pannumber3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PanNumber3= pannumber3.getText().toString().trim();

                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

                Matcher matcher = pattern .matcher(PanNumber3);

                if (matcher .matches()) {
                    Toast.makeText(getApplicationContext(), PanNumber3+" is Matching",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), PanNumber3+" is Not Matching",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        pannumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PanNumber1= pannumber1.getText().toString().trim();

                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

                Matcher matcher = pattern .matcher(PanNumber1);

                if (matcher .matches()) {
                    Toast.makeText(getApplicationContext(), PanNumber1+" is Matching",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), PanNumber1+" is Not Matching",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        restaurant_code=findViewById(R.id.edit_Restaurant_code);
        applynow=findViewById(R.id.button_applynow);
        applynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(SecondForm.this, "applynow", Toast.LENGTH_SHORT).show();
              Name=name.getText().toString();
                Mobile=mobile.getText().toString();
              /*  Gender=gender.getText().toString();*/
                DateofBirth=DOB.getText().toString();
                PanNumber1=pannumber1.getText().toString();
                PanNumber2=pannumber2.getText().toString();
                PanNumber3=pannumber3.getText().toString();
                Pincode=pincode.getText().toString();
                Spinner=spinner.getSelectedItem().toString();
                Restaurantcode=restaurant_code.getText().toString();

                if(Name.isEmpty())
                {
                    name.setError("Enter the name");
                    name.setFocusable(true);
                }
                else if(Mobile.isEmpty())
                {
                    mobile.setError("Enter the Mobile Number");
                    mobile.setFocusable(true);
                }
                else if(Spinner.equals("Select gender"))
                {
                    Toast.makeText(SecondForm.this, "Enter the Gender", Toast.LENGTH_SHORT).show();
                }
                else if(DateofBirth.isEmpty())
                {
                    DOB.setError("Enter the Date of Birth");
                    DOB.setFocusable(true);
                }
                else if(PanNumber1.isEmpty())
                {
                    pannumber1.setError("Enter the PanNumber");
                    pannumber1.setFocusable(true);
                }
                else if(PanNumber2.isEmpty())
                {
                    pannumber2.setError("Enter the PanNumber");
                    pannumber2.setFocusable(true);
                }
                else if(PanNumber3.isEmpty())
                {
                    pannumber3.setError("Enter the PanNumber");
                    pannumber3.setFocusable(true);
                }
                else if(Restaurantcode.isEmpty())
                {
                    restaurant_code.setError("Enter the Resurentcode");
                    restaurant_code.setFocusable(true);
                }
                else
                {
                    hiteapi( Name, Mobile, Spinner,DateofBirth, PanNumber1,PanNumber2,PanNumber3,Pincode, Restaurantcode);
                    name.setText("");
                    mobile.setText("");
                    pincode.setText("");
                    pannumber1.setText("");
                    pannumber2.setText("");
                    pannumber3.setText("");
                    DOB.setText("");
                    restaurant_code.setText("");
                    spinner.setSelection(0);
                }



            }
        });
    }

    private void hiteapi( String Name,String Mobile,String Spinner,String DateofBirth,String PanNumber1,String PanNumber2,String PanNumber3,String Pincode,String Restaurantcode)
    {
        Toast.makeText(getApplicationContext(), "api", Toast.LENGTH_SHORT).show();

       String url="http://speshenterprises.com/CaterersDeskHandler.ashx?RequestType=SaveLoanEnquiry&Name="+Name+"&MobileNo="+Mobile+"&Gender="+Spinner+"&DOB="+DateofBirth+"&PinCode="+Pincode+"&PAN="+PanNumber1+PanNumber2+PanNumber3+"&RestaurantCode="+Restaurantcode;

        StringRequest stringRequest= new StringRequest(Request.Method.GET
                , url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String s1=response.replaceAll("\\\\","");
                String s2=s1.substring(1,s1.length()-1);

                try {
                    JSONArray jsonArray= new JSONArray(s2);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    String result=jsonObject.getString("Result");
                    Toast.makeText(SecondForm.this,result, Toast.LENGTH_SHORT).show();
                    if(result.equals("1"))
                    {
                        Toast.makeText(SecondForm.this, "Data hasbeen saved", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(SecondForm.this, "not submit", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Errorr>>>>>>",e.toString());
                    Toast.makeText(SecondForm.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: " + error.getMessage());
                Log.d("Error>>>>>",error.toString());
                Toast.makeText(SecondForm.this,error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap= new HashMap<String, String>();
                hashMap.put("Content-Type","application/json; charset=utf-8");
                return hashMap;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache (false);
        Volley.newRequestQueue(this).add (stringRequest);

    }
}
