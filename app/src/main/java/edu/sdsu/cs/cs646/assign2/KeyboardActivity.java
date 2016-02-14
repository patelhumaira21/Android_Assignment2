/**
 * File Name : ParentActivity.java
 * Created by: Humaira Patel
 * Date: 02/4/2016
 *
 */
package edu.sdsu.cs.cs646.assign2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This class demonstrates resizing of screen to fit the soft keyboard.
 */
public class KeyboardActivity extends ParentActivity {

    private Button hideButton;
    private Button backButton;

    /**
     * Overriding the onCreate() of the parent class. This method retrieves the value from
     * MainActivity and sets it in the first EditText.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        // Setting up the First EditText.
        EditText mainText = (EditText)this.findViewById(R.id.text1);

        // Retrieving value from the Main Activity.
        Bundle extraBundle = getIntent().getExtras();
        if(extraBundle != null)
            mainText.setText(extraBundle.getString("message"));

        EditText textField2 = (EditText)this.findViewById(R.id.text2);
        textField2.requestFocus();

        // Setting the Back button.
        backButton = (Button)this.findViewById(R.id.keyboard_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clicked ",backButton.getText().toString());
                goToMain();
            }
        });

        // Setting the Hide button to hide the soft keyboard.
        hideButton = (Button)this.findViewById(R.id.hide_button);
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clicked ",hideButton.getText().toString());
                hideSoftKeyboard(v);
            }
        });

    }

    /**
     * Overrides the goToMain of the parent activity. This method goes back to it's caller
     * and ultimately to the MainActivity as a result of back stacking.
     */
    @Override
    public void goToMain() {
        Intent goHome = getParentActivityIntent();
        navigateUpTo(goHome);
    }

}



