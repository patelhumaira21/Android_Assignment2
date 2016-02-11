package edu.sdsu.cs.cs646.assign2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class ListActivity extends ActionBarActivity implements CountryFragment.OnCountrySelectedListener,
        SportFragment.OnSportSelectedListener {

    private String country_name=null;
    private String sport_name=null;

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
    }


    public void goToMain(View backButton) {

        Intent toPassBack = getIntent();

        if(sport_name != null) {
            toPassBack.putExtra("message", sport_name);
        }
        else {
            toPassBack.putExtra("message", country_name);
        }
        setResult(RESULT_OK, toPassBack);
        finish();
    }

    @Override
    public void countrySelected(String countryChosen) {
        country_name = countryChosen;
        Log.d ("Country", country_name);
    }

    @Override
    public void sportSelected(String sportChosen) {
        sport_name = sportChosen;
        Log.d ("Sport",sport_name);
    }
}
