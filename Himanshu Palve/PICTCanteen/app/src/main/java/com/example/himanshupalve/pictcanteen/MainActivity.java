package com.example.himanshupalve.pictcanteen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences=getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        setSharedPreferences();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = BreakfastFragment.newInstance(MainActivity.this);
                                break;
                            case R.id.action_item2:
                                selectedFragment = LunchFragment.newInstance("","");
                                break;
                            case R.id.action_item3:
                                selectedFragment = CartFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, BreakfastFragment.newInstance(MainActivity.this));
        transaction.commit();
    }

    private void setSharedPreferences()
    {
        SharedPreferences sharedPreferences=getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        ArrayList<String> mArrayList=new ArrayList<>();
        AssetManager assetManager=getAssets();
        try {
            InputStream inputStream = assetManager.open("itemsnames.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                String word = line.trim();
                editor.putInt(word,0);
                editor.apply();
                mArrayList.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Could not load menu",Toast.LENGTH_LONG).show();
        }
        editor.putInt("CartSize",0);
        editor.apply();
    }
}
