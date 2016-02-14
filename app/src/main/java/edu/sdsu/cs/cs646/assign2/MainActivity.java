/**
 * File Name : MainActivity.java
 * Created by: Humaira Patel
 * Date: 02/4/2016
 *
 */

package edu.sdsu.cs.cs646.assign2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * This class is the entry point to the app and serves as the main screen.
 * It inherits the ActionBar from the ParentActivity. It contains the Spinner,
 * EditText and Country Fragment. It implements the OnCountrySelectedListener
 * to retrieve the country selected.
 */

public class MainActivity extends ParentActivity implements CountryFragment.OnCountrySelectedListener{

    private EditText messageText;
    private Spinner activitySpinner;
    private int selectedActivity;

    /**
     * Overriding the onCreate method.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setting up the Spinner.
        activitySpinner = (Spinner) findViewById(R.id.activity_spinner);
        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedActivity = pos;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        // Setting up the EditText.
        messageText = (EditText) findViewById(R.id.text_field);
        messageText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView editText, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handled = true;
                }
                if (handled) {
                    hideSoftKeyboard(messageText);
                }
                return true;
            }
        });

        // Including the Country Fragment.
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_country);
        if (fragment == null) {
            fragment = new CountryFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_country, fragment)
                    .commit();
        }

    }

    /**
     * This method will take the user to the activity selected in the Spinner.
     * @param goButton
     */
    public void goToActivity(View goButton) {
        Log.i("Selected activity is",""+selectedActivity);
        switch (selectedActivity) {
            case (0): {
                goToTime();
                break;
            }
            case (1): {
                goToKeyboard();
                break;
            }
            case (2): {
                goToList();
                break;
            }
            default:
                break;
        }
    }

    /**
     * Overriding the processResult() to display text in the EditText.
     * @param result
     */
    @Override
    public void processResult(String result){
        if (result != null) {
            messageText.setText(result);
        }
    }

    /**
     * Overriding the goToKeyboard() to pass the value in the EditText on the
     * main screen.
     */
    @Override
    public void goToKeyboard(){
        goIntent = new Intent(this, KeyboardActivity.class);
        goIntent.putExtra("message", messageText.getText().toString());
        startActivityForResult(goIntent, REQUEST_CODE);
    }

    /**
     * Overriding the method of the OnCountrySelectedListener interface to retrieve
     * value from the country fragment.
     * @param countryChosen
     * @param position
     */
    @Override
    public void countrySelected(String countryChosen,int position) {
        messageText.setText(countryChosen);
    }

    @Override
    public void goToMain(){
        // This method does nothing.
    }
}