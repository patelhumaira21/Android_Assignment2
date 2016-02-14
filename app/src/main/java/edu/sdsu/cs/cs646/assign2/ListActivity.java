/**
 * File Name : ListActivity.java
 * Created by: Humaira Patel
 * Date: 02/4/2016
 *
 */
package edu.sdsu.cs.cs646.assign2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * This class is allows you to select a country and a sport of that country.
 * It inherits the ActionBar from the ParentActivity. It contains a Country
 * and a Sport Fragment. It implements the OnCountrySelectedListener
 * to retrieve the country selected and creates the sport fragment depending
 * on the country selected. It implements the OnCountrySelectedListener
 * to retrieve the sport selected
 */
public class ListActivity extends ParentActivity implements CountryFragment.OnCountrySelectedListener,
        SportFragment.OnSportSelectedListener {

    private String country_name = null;
    private String sport_name = null;
    private Button backButton;

    /**
     * Overriding the onCreate method.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Creating the country fragment
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new CountryFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        // Setting up the back button.
        backButton = (Button)this.findViewById(R.id.list_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clicked ",backButton.getText().toString());
                goToMain();
            }
        });
    }

    /**
     * Overrides the goToMain of the parent activity. This method goes back to
     * it's caller and passes the value selected to it's caller. The selected value
     * ultimately goes back to the MainActivity as a result of back stacking.
     */
    @Override
    public void goToMain() {
        Intent goHome = getIntent();
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
        finish();
    }

    /**
     * Overriding the method of the OnCountrySelectedListener interface to retrieve
     * value from the country fragment. This also creates a new sport fragment depending
     * on what country was selected.
     *
     * @param countryChosen
     * @param pos
     */
    @Override
    public void countrySelected(String countryChosen, int pos) {
        Log.i("Country Selected ",countryChosen);
        country_name = countryChosen;
        SportFragment mySportFragment = new SportFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Country", pos);
        mySportFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_sport, mySportFragment);
        fragmentTransaction.commit();
    }
    /**
     * Overriding the method of the OnSportSelectedListener interface to retrieve
     * value from the sport fragment.
     * @param sportChosen
     */
    @Override
    public void sportSelected(String sportChosen) {
        Log.i("Sport Selected ",sportChosen);
        sport_name = sportChosen;
    }



}
