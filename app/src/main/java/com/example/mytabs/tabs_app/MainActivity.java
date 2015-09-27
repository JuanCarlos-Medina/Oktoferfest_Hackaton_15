package com.example.mytabs.tabs_app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dad.model.VoucherContract;

public class MainActivity extends ActionBarActivity {

    VoucherContract myDB;

    public static ArrayList<Voucher> openMessages = new ArrayList<>();
    public static ArrayList<Voucher> deletedMessages = new ArrayList<>();

    private static final String TAG = "junk";

    // TODO(Katharina): Store this to savedInstanceState.
    public static boolean beingShop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        setUpTabs(savedInstanceState);

        openDB();
        initDB();
        readDB();
        resetDB();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (beingShop) {
            setContentView(R.layout.activity_container);
        }

        // TODO(Julian): Remove from data base.

        ListView openMessagesListView = (ListView) findViewById(R.id.listview_openmessages);
        MessageListAdapter adapter = (MessageListAdapter) openMessagesListView.getAdapter();
        for (Voucher voucher : deletedMessages) {
            adapter.remove(voucher);
        }
        deletedMessages.clear();
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
                int dismissed = cursor.getInt(4);
                Integer id = (Integer) cursor.getInt(0);
                if(counter < 5 && dismissed == 0) {
                    openMessages.add(new Voucher(cursor.getInt(0), cursor.getString(5), cursor.getFloat(2), cursor.getString(3), cursor.getString(6), cursor.getString(7), cursor.getString(9), (cursor.getInt(4) == 0)));
                    counter++;
                }
            } while(cursor.moveToNext());

        cursor.close();
    }

    private void initDB(){
        myDB.insertRow("TEST", 10.0, "Euro", "Amazon", "18 May", "18 May", 1, "CSJWF", 0);
        myDB.insertRow("TEST",10.0,"Euro","Google","18 May","18 May", 1, "CSJWF", 0);
        myDB.insertRow("TEST",10.0,"Euro","Yahoo","18 May","18 May", 1, "CSJWF", 0);
        myDB.insertRow("TEST",10.0,"Euro","Twitter","18 May","18 May", 1, "CSJWF", 1);
        myDB.insertRow("TEST",10.0,"Euro","Youtube","18 May","18 May", 1, "CSJWF", 0);
        myDB.insertRow("TEST", 10.0, "Euro", "9Gag", "18 May", "18 May", 1, "CSJWF", 0);
    }

    private void resetDB() {
        myDB.deleteAll();
    }

    private void closeDB() {
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
            // get the saved selected tab's index and set that tab as selected
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tabIndex", 0));
        }
        if (beingShop) {
            // TODO(Katharina): Change this as soon as we know how to refresh the fragment.
            actionBar.selectTab(tab_one);
        }
    }

    public void sendMessageOnClickHandler(View v) {
        int voucherToRemove_Id = (int) v.getTag();

        Context context = getApplicationContext();
        CharSequence text = "Sent!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        ListView openMessagesListView = (ListView) findViewById(R.id.listview_openmessages);
        MessageListAdapter adapter = (MessageListAdapter) openMessagesListView.getAdapter();

        myDB.dismissVoucher(voucherToRemove_Id);

        Voucher voucherToRemove = new Voucher(voucherToRemove_Id);
        adapter.remove(voucherToRemove);
        //System.out.println(v.);
        //System.out.println(voucherID.get(v.getId()));
        //myDB.dismissVoucher(voucherID.get(v.getId()));
    }

    public void registerDeveloperOnClickListener(View v) {
        Intent intent = new Intent((Activity) v.getContext(), RegisterShopActivity.class);
        v.getContext().startActivity(intent);
    }

    public void releaseVoucherOnClickListener(View v) {
        Context context = getApplicationContext();
        CharSequence text = "Sent to customers!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
