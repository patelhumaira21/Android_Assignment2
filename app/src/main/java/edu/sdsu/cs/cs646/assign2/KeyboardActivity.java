package edu.sdsu.cs.cs646.assign2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class KeyboardActivity extends MainActivity {

    private Button hideButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Bundle extraBundle = getIntent().getExtras();
        String messageFromMain = extraBundle.getString("message");

        EditText mainText = (EditText)this.findViewById(R.id.text1);
        mainText.setText(messageFromMain);

        EditText textField2 = (EditText)this.findViewById(R.id.text2);
        textField2.requestFocus();

        backButton = (Button)this.findViewById(R.id.keyboard_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hideButton = (Button)this.findViewById(R.id.hide_button);
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

    }

}



