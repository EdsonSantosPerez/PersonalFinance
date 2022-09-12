package com.edson.personalfinance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText txtPassword;
    Button btnLogin;
    Button btnSingUp;
    TextView labelPass;
    CheckBox chkbxSeSouvenir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSingUp = findViewById(R.id.btnSingUp);
        labelPass = findViewById(R.id.labelPass);
        chkbxSeSouvenir = findViewById(R.id.chkbxSeSouvenir);
        try {
            //get the shared preferences for validate if an user is already signed
            SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPr_PersonalFinance", 0);
            String pass = pref.getString("sp_password", null);
            String flag = "" + pref.getBoolean("sp_remember", false);
            Log.i("SE SOUVENIR", flag);
            if (pass != null) {//if a password already exists
                btnSingUp.setVisibility(View.GONE);
                labelPass.setVisibility(View.GONE);
            }
            if (flag.equals("true")) {//if the user clicked on remember me
                openPrincipal(this.getWindow().getDecorView().getRootView());
            } else {
                Log.i("SE SOUVENIR", "false");
            }
        } catch (NullPointerException npe) {//if there's np user signed
            npe.printStackTrace();
        }
    }

    public void login(View view) {
        try {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPr_PersonalFinance", 0);
            SharedPreferences.Editor editor = pref.edit();
            String pass = pref.getString("sp_password", null);
            if (pass != null && pass.equals(txtPassword.getText().toString().trim())) {
                if (chkbxSeSouvenir.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Se souvenir de moi selectionné", Toast.LENGTH_LONG).show();
                    editor.putBoolean("sp_remember", true);
                    editor.apply();
                }
                openPrincipal(view);
            } else {
                Toast.makeText(this, "Le mot de passe est incorrect", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void openSingUp(View view) {
        startActivity(new Intent(view.getContext(), SingUp.class));
    }

    public void openPrincipal(View view) {
        Intent intent = new Intent(view.getContext(), PrincipalActivity.class);
        startActivity(intent);
    }
}