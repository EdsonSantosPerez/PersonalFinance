package com.edson.personalfinance.ui.convertisseur;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.edson.personalfinance.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ConvertisseurFragment extends Fragment {

    private ConvertisseurViewModel convertisseurViewModel;

    Spinner spnConvertFrom;
    Spinner spnConvertTo;
    EditText txtConvertFrom;
    TextView txtViewDeviseResult;
    Button btnConvert;

    List<String> spinnerArray;
    ArrayAdapter<String> adapter;
    String base;
    String symbols;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        convertisseurViewModel =
                new ViewModelProvider(this).get(ConvertisseurViewModel.class);
        View root = inflater.inflate(R.layout.fragment_convertisseur, container, false);

        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.INTERNET}, 1);
        context = this.getContext();

        spnConvertFrom = root.findViewById(R.id.spnConvertFrom);
        spnConvertTo = root.findViewById(R.id.spnConvertTo);
        txtConvertFrom = root.findViewById(R.id.txtConvertFrom);
        txtViewDeviseResult = root.findViewById(R.id.txtViewDeviseResult);
        btnConvert = root.findViewById(R.id.btnConvert);
        initializeSpinners();
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertMoney();
            }
        });

        return root;
    }

    public void convertMoney() {

        txtViewDeviseResult.setText("");
        base = spnConvertFrom.getSelectedItem().toString();
        symbols = spnConvertTo.getSelectedItem().toString();
        RequestQueue rq = Volley.newRequestQueue(this.getContext());

        String url = "https://api.ratesapi.io/api/latest?base=" + base + "&symbols=" + symbols;
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            //actions with request HTTP reponse.
            @Override
            public void onResponse(String response) {
                JsonParser jp = new JsonParser();
                JsonObject jso = (JsonObject) jp.parse(response);
                double value = jso.get("rates").getAsJsonObject().get(symbols).getAsDouble();
                try {
                    double amount = Double.parseDouble(txtConvertFrom.getText().toString());
                    txtViewDeviseResult.setText("Résultat :\n"
                            + amount +" "+ base + " = " + value * amount + " " + symbols);
                } catch (NumberFormatException nfe) {
                    Toast.makeText(context, "Saisissez une quantité valide à convertir !", Toast.LENGTH_LONG).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    JsonParser jp = new JsonParser();
                    JsonObject jso = (JsonObject) jp.parse(responseBody);
                    Toast.makeText(context, "Erreur : " + jso.get("error").getAsString() + "\n"
                            + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };
        StringRequest sr = new StringRequest(Request.Method.GET,
                url,
                responseListener, errorListener);
        rq.add(sr);
    }

    public void initializeSpinners() {
        spinnerArray = new ArrayList<>();
        RequestQueue rq = Volley.newRequestQueue(this.getContext());
        String url = "https://api.ratesapi.io/api/latest";
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            //actions with request HTTP reponse.
            @Override
            public void onResponse(String response) {
                spinnerArray.add("EUR");
                JsonParser jp = new JsonParser();
                JsonObject jso = (JsonObject) jp.parse(response);
                JsonObject rates = jso.get("rates").getAsJsonObject();
                for (String rate : rates.keySet()) {
                    spinnerArray.add(rate);
                }
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
                spnConvertFrom.setAdapter(adapter);
                spnConvertTo.setAdapter(adapter);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        StringRequest sr = new StringRequest(Request.Method.GET,
                url,
                responseListener, errorListener);
        rq.add(sr);
    }


}