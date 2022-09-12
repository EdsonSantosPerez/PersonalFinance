package com.edson.personalfinance;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;

public class PrincipalActivity extends AppCompatActivity {

    ImageView headerImage;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        View headerView = navigationView.getHeaderView(0);
        headerImage = (ImageView) headerView.findViewById(R.id.imageView);

        //Shared Preferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPr_PersonalFinance", 0);
        String imgUriPath = pref.getString("sp_imgUserPath", "");
        String nom = pref.getString("sp_nom", null);
        String prenom = pref.getString("sp_prenom", null);
        //set the image to the ImageView in header navigation
        setImage(imgUriPath);
        //set user name and lastname to TextView in header navigation
        ((TextView) headerView.findViewById(R.id.textView)).setText(nom + " " + prenom);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_solde, R.id.nav_cashFlow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getBaseContext(), "Logout", Toast.LENGTH_LONG).show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("ShPr_PersonalFinance", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("sp_remember", false);
                editor.commit();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setImage(String path) {
        //new file from path
        File imgFile = new File(path);
        if (imgFile.exists()) {
            headerImage.setImageURI(Uri.fromFile(imgFile));
        }
    }

}
