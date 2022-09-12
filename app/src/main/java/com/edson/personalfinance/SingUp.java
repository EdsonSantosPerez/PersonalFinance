package com.edson.personalfinance;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SingUp extends AppCompatActivity {

    Button btnSave;
    Button btnUploadPhoto;
    Uri imageUri;
    ImageView imgVUser;
    EditText txtConfirmPass;
    EditText txtNom;
    EditText txtPassSingUp;
    EditText txtPrenom;

    String path = "";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        initializeComponents();
    }

    public void initializeComponents() {
        this.imgVUser = (ImageView) findViewById(R.id.imgVUser);
        this.btnUploadPhoto = (Button) findViewById(R.id.btnUploadPhoto);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.txtNom = (EditText) findViewById(R.id.txtNom);
        this.txtPrenom = (EditText) findViewById(R.id.txtPrenom);
        this.txtPassSingUp = (EditText) findViewById(R.id.txtPassSingUp);
        this.txtConfirmPass = (EditText) findViewById(R.id.txtConfirmPass);
    }

    public void uploadPhoto(View view) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.INTERNAL_CONTENT_URI).addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION), 100);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        SingUp.super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 100) {
            this.imageUri = data.getData();
            this.imgVUser.setImageURI(imageUri);
            path = getRealPathFromURI(imageUri);
            this.imgVUser.setVisibility(View.VISIBLE);
        }
    }

    public void save(View view) {
        //Shared Preferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPr_PersonalFinance", 0);
        SharedPreferences.Editor editor = pref.edit();//We need Editor to edit and save the changes in shared preferences
        try {
            Context context = getApplicationContext();
            Toast toast;

            editor.putString("sp_nom", this.txtNom.getText().toString().trim());
            editor.putString("sp_prenom", this.txtPrenom.getText().toString().trim());
            editor.putString("sp_password", this.txtPassSingUp.getText().toString().trim());
            editor.putString("sp_imgUserPath", this.path);
            if (!isValidPassword(txtPassSingUp.getText().toString())) {
                toast = Toast.makeText(context, "Mot de passe invalide !", Toast.LENGTH_SHORT);
                toast.show();
                return;
            } else if (!txtPassSingUp.getText().toString().equals(txtConfirmPass.getText().toString())) {
                toast = Toast.makeText(context, "Veuillez bien confirmer le mot de passe !", Toast.LENGTH_SHORT);
                toast.show();
                return;
            } else {
                editor.commit();//to save/commit changes in shared prefrences
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            Toast.makeText(getApplicationContext(), "Champs Vides !", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isValidPassword(String pass) {
        Pattern pattern;
        Matcher matcher;
        //(at least 1 digit) (at least one special character) (no white white spaces) (any letter) {at least 8 characters}
        String pass_pattern = "^(?=.*[0-9])(?=.*[@_!?<>#&])(?=\\S+$)(?=.*[a-zA-Z]).{8,}$";
        pattern = Pattern.compile(pass_pattern);
        matcher = pattern.matcher(pass);
        return matcher.matches();
    }

    public String getRealPathFromURI(Uri contentUri) {
        //get the image's path to access it in Principal Activity
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}