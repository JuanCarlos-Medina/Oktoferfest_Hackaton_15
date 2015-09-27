package com.example.mytabs.tabs_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytabs.tabs_app.R;

public class VoucherActivity extends AppCompatActivity {

    String voucherInfo = "";
    private Voucher selectedVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        Intent intent = getIntent();
        voucherInfo = intent.getStringExtra(MessageListAdapter.EXTRA_MESSAGE);
        selectedVoucher = new Voucher(0);

        for (Voucher voucher : MainActivity.openMessages) {
            if (voucher.getId() == Integer.parseInt(voucherInfo)) {
                selectedVoucher = voucher;
            }
        }

        TextView companyName = (TextView) findViewById(R.id.voucher_textView);
        companyName.setText(selectedVoucher.getShop() + "\n\n " + selectedVoucher.getAmount() + " "
                + selectedVoucher.getUnit());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_voucher, menu);
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

    public void useVoucherOnClickHandler(View v) {

        Button voucherButton = (Button) findViewById(R.id.message_useVoucher);
        voucherButton.setVisibility(View.INVISIBLE);

        RelativeLayout theVoucher = (RelativeLayout) findViewById(R.id.linearlayout_voucher);
        theVoucher.setVisibility(View.VISIBLE);

        TextView voucherCode = (TextView) findViewById(R.id.voucher_textViewCode);
        voucherCode.setText(selectedVoucher.getCode());

        MainActivity.deletedMessages.add(selectedVoucher);

        //ListView openMessagesListView = (ListView) findViewById(R.id.listview_openmessages);
        //MessageListAdapter adapter = (MessageListAdapter) openMessagesListView.getAdapter();
        //adapter.remove(voucherInfo);
    }
}
