package edu.sdsu.cs.cs646.assign2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class ListActivity extends MainActivity implements CountryFragment.OnCountrySelectedListener,
        SportFragment.OnSportSelectedListener {

    private String country_name=null;
    private String sport_name=null;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new CountryFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        backButton = (Button)this.findViewById(R.id.list_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });
    }

    public void goToMain() {

        Intent goHome = new Intent(this,MainActivity.class);
        Log.d("Parent Activity", goHome.toString());
        if(sport_name != null) {
            goHome.putExtra("message", sport_name);
        }
        else if (country_name != null){
            goHome.putExtra("message", country_name);
        }
        else{
            goHome.putExtra("message", "None Selected");
        }
        setResult(RESULT_OK, goHome);
        navigateUpTo(goHome);
    }

    @Override
    public void countrySelected(String countryChosen) {
        country_name = countryChosen;
        Log.d("Country", country_name);
    }

    @Override
    public void sportSelected(String sportChosen) {
        sport_name = sportChosen;
        Log.d ("Sport",sport_name);
    }



}
