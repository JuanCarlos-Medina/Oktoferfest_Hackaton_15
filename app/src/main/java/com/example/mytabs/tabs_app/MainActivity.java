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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.UUID;

import ch.uepaa.p2pkit.ConnectionCallbacks;
import ch.uepaa.p2pkit.ConnectionResult;
import ch.uepaa.p2pkit.ConnectionResultHandling;
import ch.uepaa.p2pkit.KitClient;
import ch.uepaa.p2pkit.discovery.GeoListener;
import ch.uepaa.p2pkit.discovery.InfoTooLongException;
import ch.uepaa.p2pkit.discovery.P2pListener;
import ch.uepaa.p2pkit.discovery.Peer;
import ch.uepaa.p2pkit.messaging.MessageListener;

import dad.model.VoucherContract;

public class MainActivity extends ActionBarActivity {

    VoucherContract myDB;

    //public String newVoucher;
    public static ActionBar actionBar;
    public static Tab tab_one;
    public static ArrayList<Voucher> openMessages = new ArrayList<>();
    public static ArrayList<Voucher> deletedMessages = new ArrayList<>();

    private static final String TAG = "junk";

    private static final String APP_KEY = "eyJzaWduYXR1cmUiOiJReUVTVFMwbWVtU2ltbWFBRXV3dnd4aGlEN1g5bnN1QVBZSlpMcUltNldObTkwcjh3MjYxUGk3a3FsbWxXVWdOZDQ0NVlQN0JWNE1sd0FTaEsvbVBhaGJDbWw1UlpXYkI2TlFVSU8wSzgrQTQvT1MrV3JybzV3MXdRZE1ZOUlOeVB2ZEZKMlA2WVBTMXQreGg2eE5LK0trYStrMm9kY052T1AwRGJJcXc5Lzg9IiwiYXBwSWQiOjEyNjEsInZhbGlkVW50aWwiOjE2Nzk0LCJhcHBVVVVJRCI6IjE2RTY2NzM5LTlFMkYtNEVEMy1BQ0Q4LUJCMUFBRTZCQkY2NyJ9";

    private static int i=10;



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
        //resetDB();

        mShouldStartServices = true;
        showColorPickerDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();

        actionBar.selectTab(tab_one);

        if (!deletedMessages.isEmpty()) {
            ListView openMessagesListView = (ListView) findViewById(R.id.listview_openmessages);
            MessageListAdapter adapter = (MessageListAdapter) openMessagesListView.getAdapter();
            for (Voucher voucher : deletedMessages) {
                myDB.deleteRow(voucher.getId());
                adapter.remove(voucher);
            }
            deletedMessages.clear();
        }

        if(mWantToConnect && !KitClient.getInstance(this).isConnected()) {
            enableKit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void readDB(){
        Cursor cursor = myDB.getAllRows();

        if(cursor.moveToFirst())
            do {
                String company = cursor.getString(5);
                int dismissed = cursor.getInt(4);
                Integer id = (Integer) cursor.getInt(0);
                if(openMessages.size() < 5 && dismissed == 0) {
                    openMessages.add(new Voucher(cursor.getInt(0), cursor.getString(5), cursor.getFloat(2), cursor.getString(3), cursor.getString(6), cursor.getString(7), cursor.getString(9), (cursor.getInt(4) == 0)));
                }
            } while(cursor.moveToNext());

        cursor.close();
    }

    private void initDB(){
        //myDB.insertRow("TEST", 10.0, "Euro", "Amazon", "18 May", "18 May", 1, "CSJWF", 0);
        //myDB.insertRow("TEST",10.0,"Euro","Google","18 May","18 May", 1, "CS1W2", 0);
        //myDB.insertRow("TEST",10.0,"Euro","Yahoo","18 May","18 May", 1, "CS99F", 0);
        //myDB.insertRow("TEST",10.0,"Euro","Twitter","18 May","18 May", 1, "CSMMF", 0);
        //myDB.insertRow("TEST",10.0,"Euro","Youtube","18 May","18 May", 1, "5S1WF", 0);
        //myDB.insertRow("TEST", 10.0, "Euro", "9Gag", "18 May", "18 May", 1, "DSJWF", 0);
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

        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        tab_one = actionBar.newTab();
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

        // TODO(Julian): Delete item from data base. Here.

        if(myDB.getNumDismissed() < 5)
            myDB.dismissVoucher(voucherToRemove_Id);
        else
            myDB.deleteRow(voucherToRemove_Id);

        System.out.println("Number Dismissed: " + myDB.getNumDismissed());

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

        showColorPickerDialog();

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    //P2P STUFF
    private final P2pListener mP2pDiscoveryListener = new P2pListener() {

        @Override
        public void onStateChanged(final int state) {
            logToView("P2pListener | State changed: " + state);
        }

        @Override
        public void onPeerDiscovered(final Peer peer) {
            System.out.println("LOOOG oh yeah");
            byte[] colorBytes = peer.getDiscoveryInfo();
            if (colorBytes != null && colorBytes.length == 3) {
                logToView("P2pListener | Peer discovered: " + peer.getNodeId() + " with color: ");
            } else {
                logToView("P2pListener | Peer discovered: " + peer.getNodeId() + " without color");
            }
        }

        @Override
        public void onPeerLost(final Peer peer) {
            logToView("P2pListener | Peer lost: " + peer.getNodeId());
        }

        @Override
        public void onPeerUpdatedDiscoveryInfo(Peer peer) {

            System.out.println("LOOOG oh yeah 2");
            //logToView("Voucher 20% H&M");
            //mLogView.setText(SendVouchersFragment.voucherMessage);
            // mLogView.setText("Voucher 20% H&M");
            String newVoucherText = "Voucher 20% H&M 1Kf83fja";

            String[] voucherWords = newVoucherText.split(" ");
            myDB.insertRow("TEST", 10.0, "Euro", voucherWords[2], "18 May", "18 May", 1, voucherWords[3], 0);
            // TODO(Julian): Get the highest ID in the data base.
            openMessages.add(new Voucher(273, voucherWords[2], 20, "%", "", "", voucherWords[3], true ));
            //myDB.insertRow("", 20, "%",  voucherWords[0], "", "", 1,  voucherWords[3], 0);
            // public Voucher(int id, String shop, double amount, String unit, String received, String expires, String code, boolean isVisible) {

            ListView openMessagesListView = (ListView) findViewById(R.id.listview_openmessages);
            MessageListAdapter adapter = (MessageListAdapter) openMessagesListView.getAdapter();
            adapter.notifyDataSetChanged();

            Context context = getApplicationContext();
            CharSequence text = "New voucher arrived!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //WRITE "VOUCHER" TO EXISTING LIST

            /*byte[] voucherBytes = peer.getDiscoveryInfo();
            try {
                if (voucherBytes != null) {
                    MainActivity.newVoucher = new String(voucherBytes, "UTF-8");
                }
            } catch (Exception e) {

            }*/

        }
    };










    private final GeoListener mGeoDiscoveryListener = new GeoListener() {

        @Override
        public void onStateChanged(final int state) {
            logToView("GeoListener | State changed: " + state);
        }

        @Override
        public void onPeerDiscovered(final UUID nodeId) {
            logToView("GeoListener | Peer discovered: " + nodeId);

            // sending a message to the peer
            KitClient.getInstance(MainActivity.this).getMessageServices().sendMessage(nodeId, "SimpleChatMessage", "From Android: Hello GEO!".getBytes());
        }

        @Override
        public void onPeerLost(final UUID nodeId) {
            logToView("GeoListener | Peer lost: " + nodeId);
        }
    };

    private final MessageListener mMessageListener = new MessageListener() {

        @Override
        public void onStateChanged(final int state) {
            logToView("MessageListener | State changed: " + state);
        }

        @Override
        public void onMessageReceived(final long timestamp, final UUID origin, final String type, final byte[] message) {
            logToView("MessageListener | Message received: From=" + origin + " type=" + type + " message=" + new String(message));
        }
    };

    private final ConnectionCallbacks mConnectionCallbacks = new ConnectionCallbacks() {

        @Override
        public void onConnected() {
            logToView("Successfully connected to P2P Services, with id: " + KitClient.getInstance(MainActivity.this).getNodeId().toString());


            if (mShouldStartServices) {
                mShouldStartServices = false;

                startP2pDiscovery();
                startGeoDiscovery();
            }
        }

        @Override
        public void onConnectionSuspended() {
            logToView("Connection to P2P Services suspended");


        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            logToView("Connection to P2P Services failed with status: " + connectionResult.getStatusCode());
            ConnectionResultHandling.showAlertDialogForConnectionError(MainActivity.this, connectionResult.getStatusCode());
        }
    };

    private boolean mShouldStartServices;
    private boolean mWantToConnect = false;

    private int mCurrentColor;

    private TextView mLogView;

    private void enableKit() {

        final int statusCode = KitClient.isP2PServicesAvailable(this);
        if (statusCode == ConnectionResult.SUCCESS) {
            KitClient client = KitClient.getInstance(this);
            client.registerConnectionCallbacks(mConnectionCallbacks);

            if (client.isConnected()) {
                logToView("Client already connected");
            } else {
                logToView("Connecting P2PKit client");
                client.connect(APP_KEY);
            }
            mWantToConnect = false;
        } else {
            mWantToConnect = true;
            logToView("Cannot start P2PKit, status code: " + statusCode);
            ConnectionResultHandling.showAlertDialogForConnectionError(this, statusCode);
        }
    }

    private void disableKit() {
        KitClient.getInstance(this).disconnect();
    }

    private void startP2pDiscovery() {
        try {
            KitClient.getInstance(this).getDiscoveryServices().setP2pDiscoveryInfo(getColorBytes(mCurrentColor));
        } catch (InfoTooLongException e) {
            logToView("P2pListener | The discovery info is too long");
        }
        KitClient.getInstance(this).getDiscoveryServices().addListener(mP2pDiscoveryListener);
    }

    private void stopP2pDiscovery() {
        KitClient.getInstance(this).getDiscoveryServices().removeListener(mP2pDiscoveryListener);
        logToView("P2pListener removed");
    }

    private void startGeoDiscovery() {
        KitClient.getInstance(this).getMessageServices().addListener(mMessageListener);

        KitClient.getInstance(this).getDiscoveryServices().addListener(mGeoDiscoveryListener);
    }

    private void stopGeoDiscovery() {
        KitClient.getInstance(this).getMessageServices().removeListener(mMessageListener);
        logToView("MessageListener removed");

        KitClient.getInstance(this).getDiscoveryServices().removeListener(mGeoDiscoveryListener);
        logToView("GeoListener removed");
    }

    private void logToView(String message) {
        // CharSequence currentTime = DateFormat.format("hh:mm:ss - ", System.currentTimeMillis());
        //mLogView.setText(currentTime + message + "\n" + mLogView.getText());
    }

    private byte[] getColorBytes(int color) {
        return new byte[] {(byte) Color.red(color), (byte) Color.green(color), (byte) Color.blue(color)};
    }


    public void onColorPicked(int colorCode) {
        mCurrentColor = colorCode;
        System.out.println("LOOOG oh yeah sending a");
        if (mShouldStartServices) {
            System.out.println("LOOOG oh yeah sending b");
            enableKit();
        } else if (KitClient.getInstance(this).isConnected()) {
            System.out.println("LOOOG oh yeah sending c");
            try {
                byte[] colorBytes = getColorBytes(mCurrentColor);
                KitClient.getInstance(this).getDiscoveryServices().setP2pDiscoveryInfo(colorBytes);
                System.out.println("LOOOG oh yeah sending d");
            } catch (InfoTooLongException e) {
                logToView("P2pListener | The discovery info is too long");
            }
        }
    }

    private void showColorPickerDialog() {
        System.out.println("LOOOG oh yeah sending1");
        i+=10;
        onColorPicked(i);
    }


}
