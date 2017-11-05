package com.project.shrey.currencyconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button mCon1, mCon2;
    TextView mRes1, mRes2;
    EditText mAmt1, mAmt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("USD to INR");
        spec.setContent(R.id.tab1);
        spec.setIndicator("USD to INR");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("INR to USD");
        spec.setContent(R.id.tab2);
        spec.setIndicator("INR to USD");
        host.addTab(spec);

        host.setOnTabChangedListener(new AnimatedTabHostListener(this, host));

        mCon1 = findViewById(R.id.con1);
        mCon2 = findViewById(R.id.con2);

        mRes1 = findViewById(R.id.res1);
        mRes2 = findViewById(R.id.res2);

        mAmt1 = findViewById(R.id.amt1);
        mAmt2 = findViewById(R.id.amt2);

        mCon1.setOnClickListener((view) -> {
            final String amt = mAmt1.getText().toString();

            mAmt1.setText("");

            String url = "http://api.fixer.io/latest?symbols=USD,INR&base=USD";

            StringRequest stringRequest= new StringRequest(url, (response) -> {

                String baseValue = "", dateValue = "", rateValue = "", msg = "";
                Float rate = null, result;

                try {

                    JSONObject object = new JSONObject(response);

                    baseValue = object.getString("base");

                    dateValue = object.getString("date");

                    JSONObject rateObj = object.getJSONObject("rates");

                    rateValue = rateObj.getString("INR");

                    rate = Float.parseFloat(rateValue);

                } catch (JSONException e) {
                    Log.e("JSON Error", e.toString());
                    mRes1.setText("JSON Error");
                }

                if (amt.equals("")){
                    msg = "Base Currency: " + baseValue + "\n\nForeign Currency: INR\n\nDate of Fetching: " + dateValue + "\n\nRate: " + rateValue;
                } else {
                    Float amount = Float.parseFloat(amt);
                    result = rate * amount;
                    msg = "Base Currency: " + baseValue + "\n\nForeign Currency: INR\n\nDate of Fetching: " + dateValue + "\n\nRate: " + rateValue + "\n\nOriginal Value: " + String.valueOf(amount) + "\n\nConverted Value: " + String.valueOf(result);
                    Log.e("Amount", String.valueOf(result));
                }
                mRes1.setText(String.format("%s\n\n%s", mRes1.getText().toString(), msg));
            }, error -> {
                mRes1.setText(String.format("%s\n\n%s", mRes1.getText().toString(), "Network Error"));
                Log.e("Network Error", error.toString());
            });

            Volley.newRequestQueue(MainActivity.this).add(stringRequest);

            try{
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e){
                Log.e("keyboard",e.toString());
            }
        });

        mCon2.setOnClickListener((view) -> {
            final String amt = mAmt2.getText().toString();

            mAmt2.setText("");

            String url = "http://api.fixer.io/latest?symbols=USD,INR&base=INR";

            StringRequest stringRequest= new StringRequest(url, (response) -> {

                String baseValue = "", dateValue = "", rateValue = "", msg = "";
                Float rate = null, result;

                try {

                    JSONObject object = new JSONObject(response);

                    baseValue = object.getString("base");

                    dateValue = object.getString("date");

                    JSONObject rateObj = object.getJSONObject("rates");

                    rateValue = rateObj.getString("USD");

                    rate = Float.parseFloat(rateValue);

                } catch (JSONException e) {
                    Log.e("JSON Error", e.toString());
                    mRes2.setText("JSON Error");
                }

                if (amt.equals("")){
                    msg = "Base Currency: " + baseValue + "\n\nForeign Currency: USD\n\nDate of Fetching: " + dateValue + "\n\nRate: " + rateValue;
                } else {
                    Float amount = Float.parseFloat(amt);
                    result = rate * amount;
                    msg = "Base Currency: " + baseValue + "\n\nForeign Currency: USD\n\nDate of Fetching: " + dateValue + "\n\nRate: " + rateValue + "\n\nOriginal Value: " + String.valueOf(amount) + "\n\nConverted Value: " + String.valueOf(result);
                    Log.e("Amount", String.valueOf(result));
                }
                mRes2.setText(String.format("%s\n\n%s", mRes2.getText().toString(), msg));
            }, error -> {
                mRes2.setText(String.format("%s\n\n%s", mRes2.getText().toString(), "Network Error"));
                Log.e("Network Error", error.toString());
            });

            Volley.newRequestQueue(MainActivity.this).add(stringRequest);

            try{
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e){
                Log.e("keyboard",e.toString());
            }
        });
    }

    public void reset(View view) {
        if (view.equals(findViewById(R.id.rst1))) {
            mRes1.setText("Result: ");
            mAmt1.setText("");
        }
        if (view.equals(findViewById(R.id.rst2))) {
            mRes2.setText("Result: ");
            mAmt2.setText("");
        }
    }
}
