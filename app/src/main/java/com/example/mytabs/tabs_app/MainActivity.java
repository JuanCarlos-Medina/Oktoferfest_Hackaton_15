package com.example.mytabs.tabs_app;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import dad.model.VoucherContract;

public class MainActivity extends ActionBarActivity {

    VoucherContract myDB;

    public static String[] openMessages = new String[] {"Null", "Null", "Null", "Null", "Null"};
    private static final String TAG = "junk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        setUpTabs(savedInstanceState);

        openDB();
        initDB();
        readDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void readDB(){
        Cursor cursor = myDB.getAllRows();

        int counter = 0;

        if(cursor.moveToFirst())
            do {
                String company = cursor.getString(5);
                if(counter < 5) openMessages[counter] = company;
                counter++;
            } while(cursor.moveToNext());

        cursor.close();
    }

    private void initDB(){
        myDB.insertRow("TEST",10.0,"Euro","Amazon","18 May","18 May", 1, "CSJWF", 0);
    }

    private void closeDB() {
        myDB.deleteAll();
        myDB.close();
    }

    private void openDB() {
        myDB = new VoucherContract(this);
        myDB.open();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //            save the selected tab's index so it's re-selected on orientation change
        outState.putInt("tabIndex", getSupportActionBar().getSelectedNavigationIndex());
    }

    private void setUpTabs(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        Tab tab_one = actionBar.newTab();
        Tab tab_two = actionBar.newTab();

        MyVouchersFragment myVouchersFragment = new MyVouchersFragment();
        tab_one.setText("My Vouchers")
                .setContentDescription("SeeMyVouchers tab")
                .setTabListener(
                        new MyTabListener<MyVouchersFragment>(
                                myVouchersFragment));

        SendVouchersFragment sendVouchersFragment = new SendVouchersFragment();
        tab_two.setText("Send Vouchers")
                .setContentDescription("SendVouchers tab")
                .setTabListener(
                        new MyTabListener<SendVouchersFragment>(
                                sendVouchersFragment));

        actionBar.addTab(tab_one);
        actionBar.addTab(tab_two);

        if (savedInstanceState != null) {
            Log.i(TAG, "setting selected tab from saved bundle");
//            get the saved selected tab's index and set that tab as selected
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tabIndex", 0));
        }
    }

    public void sendMessageOnClickHandler(View v) {
        String voucherToRemove = (String) v.getTag();

        Context context = getApplicationContext();
        CharSequence text = "Sent!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        ListView openMessagesListView = (ListView) findViewById(R.id.listview_openmessages);
        MessageListAdapter adapter = (MessageListAdapter) openMessagesListView.getAdapter();
        adapter.remove(voucherToRemove);
    }

}
