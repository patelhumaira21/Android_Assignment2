/**
 * File Name : ParentActivity.java
 * Created by: Humaira Patel
 * Date: 02/4/2016
 *
 */
package edu.sdsu.cs.cs646.assign2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * This class allows the user to set time on the TimePicker and save
 * it to a permanent storage.
 */
public class TimeActivity extends ParentActivity {
    private TimePicker mTimePicker;
    private String timeString;
    private Button backButton;

    /**
     * Overriding the onCreate() of the  class. This method loads the time
     * from the permanent storage and displays it on the TimePicker.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        // Setting the TimePicker
        mTimePicker = (TimePicker)findViewById(R.id.time_picker);

        // Loading the previously saved time.
        loadData();

        // Setting up the Back Button.
        backButton = (Button)(findViewById(R.id.time_back_button));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clicked ",backButton.getText().toString());
                goToMain();
            }
        });
    }

    /**
     * This method is called when the SET button is clicked.This opens a
     * dialog to confirm if the user wants to set the time. If YES, it saves
     * the time to permanent storage.
     *
     * @param setButton
     */
    public void openConfirmDialog(View setButton) {
        int hour = mTimePicker.getCurrentHour();
        int minutes = mTimePicker.getCurrentMinute();
        timeString = formatTime(hour,minutes);
        Log.i("Confirming time",timeString);

        // Setting up the Dialog.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TimeActivity.this);
        alertDialogBuilder.setMessage("You have entered the time as " + timeString + ". Do you wish to continue?");

        // Handling the YES event.
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.i("Clicked ","Yes on the time picker dialog");
                saveData(mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
            }
        });

        // Handling the NO event.
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.i("Clicked ","No on the time picker dialog");
                dialog.dismiss();
            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    /**
     * This method saves data to permanent storage.
     * @param savedHour
     * @param savedMin
     */
    public void saveData(int savedHour, int savedMin) {
        Log.i("Saving time ",formatTime(savedHour,savedMin));
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Hour", savedHour);
        editor.putInt("Minute", savedMin);
        editor.commit();
    }

    /**
     * This method retrieves data from permanent storage and displays it
     * on TimePicker.
     */
    public void loadData() {
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        int prevSavedHour = sharedPref.getInt("Hour", mTimePicker.getCurrentHour());
        int prevSavedMin = sharedPref.getInt("Minute", mTimePicker.getCurrentMinute());
        Log.i("Loading time ",formatTime(prevSavedHour,prevSavedMin));
        mTimePicker.setCurrentHour(prevSavedHour);
        mTimePicker.setCurrentMinute(prevSavedMin);
    }

    /**
     *  Overrides the goToMain of the parent activity. This method goes back to it's
     *  caller and passes the time selected to it's caller. The selected time ultimately
     *  goes back to the MainActivity as a result of back stacking.
     */
    public void goToMain() {
        Intent toPassBack = getIntent();
        if (timeString != null) {
            toPassBack.putExtra("message", timeString);
        }
        setResult(RESULT_OK, toPassBack);
        finish();
    }

    /**
     * Thsi method converts the 24 hour format to string of the format "Hr:Min:AM/PM".
     * @param hour
     * @param minutes
     * @return
     */
    private String formatTime(int hour,int minutes) {
        String hr= String.valueOf(hour % 12);
        if(hr.equals("0")){
            hr = "12";
        }
        String mins= String.valueOf(minutes);
        if(minutes<10){
            mins = "0"+mins;
        }
        return (hr + ":" + mins + " " + ((hour>=12) ? "PM" : "AM"));
    }
}





