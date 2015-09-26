package com.example.mytabs.tabs_app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "junk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        setUpTabs(savedInstanceState);
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

}
