package edu.sdsu.cs.cs646.assign2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private EditText messageText;
    private Spinner activitySpinner;
    private int selectedActivity;
    private static final int REQUEST_CODE_TIME = 0;
    private static final int REQUEST_CODE_LIST = 1;
    Intent goIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        activitySpinner = (Spinner) findViewById(R.id.activity_spinner);
        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Log.d("Position", "" + pos);
                Log.d("Id", "" + id);
                selectedActivity = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    }

    public void goToTime(){
        goIntent = new Intent(this, TimeActivity.class);
        startActivityForResult(goIntent, REQUEST_CODE_TIME);
    }

    public void goToKeyboard(){
        Log.d("keyboard", "keyboard activity called");
        goIntent = new Intent(this, KeyboardActivity.class);
        goIntent.putExtra("message", messageText.getText().toString());
        startActivity(goIntent);
    }

    public void goToList(){
        goIntent = new Intent(this, ListActivity.class);
        startActivityForResult(goIntent, REQUEST_CODE_LIST);
    }

    public void goToActivity(View goButton) {

        Log.d("Position", "" + selectedActivity);
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

    public void hideSoftKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_TIME) {
            switch (resultCode) {
                case RESULT_OK:
                    String display_text = data.getStringExtra("message");
                    if (display_text!=null) {
                        messageText.setText(display_text);
                    }
                    break;
                case RESULT_CANCELED:
                    break;
            }
        }

        if (requestCode == REQUEST_CODE_LIST)
            switch (resultCode) {
            case RESULT_OK: {
                String display_text = data.getStringExtra("message");
                if (display_text != null) {
                    messageText.setText(display_text);
                } else
                    messageText.setText("None Selected");
                break;
            }

            case RESULT_CANCELED:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_time:
                goToTime();
                return true;

            case R.id.action_keyboard:
                goToKeyboard();
                return true;

            case R.id.action_list:
                goToList();
                return true;

            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                super. onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


}