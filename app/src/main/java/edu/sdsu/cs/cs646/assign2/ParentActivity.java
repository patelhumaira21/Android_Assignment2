/**
 * File Name : ParentActivity.java
 * Created by: Humaira Patel
 * Date: 02/4/2016
 *
 */
package edu.sdsu.cs.cs646.assign2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * This abstract class serves as the parent class to all the activities in this
 * project. It is created so that it provides the functionality of action bar which
 * will be common to all the activities. Hence, the activity can extend this class
 * and have the action bar in it.
 *
 * The class provides default implementation of the methods defined in
 * it. The child activity overrides the method according to its needs.
 *
 */
public abstract class ParentActivity extends ActionBarActivity {

    protected final int REQUEST_CODE = 0;
    protected Intent goIntent = null;

    /**
     * Overriding the onCreate() of the parent class. This basically creates
     * an action bar.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Create method called ", this.getClass().getSimpleName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        // Setting up the action bar.
        getSupportActionBar().show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     *
     * Overriding the onActivityResult method. This is common to all activities because
     * all activities except the MainActivity should pass along the result to its caller.
     * This mechanism is to support the back stacking of result to the MainActivity.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Log.i("Got activity result ", this.getClass().getSimpleName());
            if (requestCode == REQUEST_CODE) {
                if(RESULT_OK == resultCode) {
                    String result = data.getStringExtra("message");
                    processResult(result);
                }
            }
    }

    /**
     * This function takes the result from the onActivityResult() and passes
     * it along to its caller.
     *
     * @param result
     */
    public void processResult(String result) {
        Intent goHome = getIntent();
        Log.i("Passing "+result, this.getClass().getSimpleName() );
        goHome.putExtra("message", result);
        setResult(RESULT_OK, goHome);
        finish();
    }

    /**
     * Overriding the onCreateOptionsMenu of the ActionBarActivity. This inflates the
     * action bar with values.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Overriding the onOptionsItemSelected. This routes to various activities
     * depending on the activity selected in the Action bar.
     *
     * @param item
     * @return
     */
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
                goToMain();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Overriding the onDestroy() to check if the child activity was destroyed
     * while back stacking.
     */
    @Override
    public void onDestroy() {
        Log.i("Destroy method called ", this.getClass().toString());
        super.onDestroy();
    }

    /**
     * This method navigates to Time Activity from the ActionBar.
     */
    public void goToTime(){
        Log.i("Go to Time activity ",this.getLocalClassName());
        goIntent = new Intent(this, TimeActivity.class);
        startActivityForResult(goIntent, REQUEST_CODE);
    }

    /**
     * This method navigates to Keyboard Activity from the ActionBar.
     */
    public void goToKeyboard(){
        Log.i("Go to keyboard activity",this.getLocalClassName());
        goIntent = new Intent(this, KeyboardActivity.class);
        startActivityForResult(goIntent, REQUEST_CODE);
    }

    /**
     * This method navigates to List Activity from the ActionBar.
     */
    public void goToList(){
        Log.i("Go to list activity ",this.getLocalClassName());
        goIntent = new Intent(this, ListActivity.class);
        startActivityForResult(goIntent, REQUEST_CODE);
    }

    /**
     * This method hides the Soft Keyboard.
     * @param view
     */
    public void hideSoftKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * This abstract method is used to go back to the Main activity when the user clicks the Back
     * in the action bar. Every activity implements its own activity as it passes different data
     * to the Main activity.
     *
     */
    abstract void goToMain();
}
