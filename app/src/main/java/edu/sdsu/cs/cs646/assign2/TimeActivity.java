package edu.sdsu.cs.cs646.assign2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import java.util.Calendar;

public class TimeActivity extends ActionBarActivity {
    private TimePicker mTimePicker;
    private MyTime myTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mTimePicker = (TimePicker)findViewById(R.id.time_picker);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            }

        });
        loadData();
    }

    public void openConfirmDialog(View setButton) {
        myTime = new MyTime(mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
        Log.d("Time selected", myTime.toString());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TimeActivity.this);
        alertDialogBuilder.setMessage("You have entered the time as " + myTime + ". Do you wish to continue?");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                saveData(mTimePicker.getCurrentHour(),mTimePicker.getCurrentMinute());
            }


        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }


    public void saveData(int savedHour, int savedMin) {
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Hour", savedHour);
        editor.putInt("Minute", savedMin);
        editor.commit();
        Log.d("Save time", "" + savedHour + ":" + savedMin);
    }

    public void loadData() {
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        int prevSavedHour = sharedPref.getInt("Hour", mTimePicker.getCurrentHour());
        int prevSavedMin = sharedPref.getInt("Minute", mTimePicker.getCurrentMinute());

        mTimePicker.setCurrentHour(prevSavedHour);
        mTimePicker.setCurrentMinute(prevSavedMin);

        Log.d("Load time", "" + prevSavedHour + prevSavedMin);
    }

    public void onBack(View button) {
        Log.i("rew", "Back");
        Intent toPassBack = getIntent();
        if (myTime != null) {
            toPassBack.putExtra("message", myTime.toString());
        }
        else {
            toPassBack.putExtra("message", "");
        }
        setResult(RESULT_OK, toPassBack);
        finish();
    }

}

class MyTime {

    private int hour;
    private int minutes;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int mins) {
        this.minutes = mins;
    }

    public MyTime() {
        Calendar c = Calendar.getInstance();
        this.hour = c.get(Calendar.HOUR);
        this.minutes = c.get(Calendar.MINUTE);
    }

    public MyTime(int hour, int mins) {
        this.hour = hour;
        this.minutes = mins;
    }

    @Override
    public String toString() {
        String hr= String.valueOf(this.hour % 12);
        if(hr.equals("0")){
            hr = "12";
        }

        String mins= String.valueOf(this.minutes);
        if(this.minutes<10){
            mins = "0"+mins;
        }

        return (hr + ":" + mins + " " + ((this.hour>=12) ? "PM" : "AM"));

    }

}