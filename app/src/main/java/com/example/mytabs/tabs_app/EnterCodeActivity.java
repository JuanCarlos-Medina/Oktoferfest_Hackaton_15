package com.example.mytabs.tabs_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mytabs.tabs_app.R;

public class EnterCodeActivity extends AppCompatActivity {

    public final static String EXTRA_BEINGSHOP = "com.example.mytabs.tabs_app.EXTRA_BEINGSHOP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_code, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void codeEnteredOnClickListener(View v) {
        // TODO(Katharina): Change here something about this,
        // such that shop can do something in MainActivity.
        MainActivity.beingShop = true;

        // TODO(whoever): Change this...best pratices, ohoh.
        this.finish();
    }
}
