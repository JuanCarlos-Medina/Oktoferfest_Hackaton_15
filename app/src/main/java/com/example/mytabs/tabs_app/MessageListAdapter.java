package com.example.mytabs.tabs_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Katharina on 26.09.2015.
 */
public class MessageListAdapter extends ArrayAdapter<String> {

    public final static String EXTRA_MESSAGE = "com.example.mytabs.tabs_app.MESSAGE";

    private List<String> items;
    private int layoutResourceId;
    private Context context;

    public MessageListAdapter(Context context, int layoutResourceId, List<String> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StringHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new StringHolder();
        holder.voucherInfoString = items.get(position);
        holder.sendMessageButton = (Button)row.findViewById(R.id.message_sendMessage);
        holder.sendMessageButton.setTag(holder.voucherInfoString);

        row.setTag(holder.voucherInfoString);

        holder.voucherInfo = (TextView)row.findViewById(R.id.list_item_openmessages_textview);
        holder.voucherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click", "TextView clicked on " + (String) v.getTag());
                Intent intent = new Intent((Activity) v.getContext(), VoucherActivity.class);
                // TODO(Katharina): Get here the number etc.
                intent.putExtra(EXTRA_MESSAGE, "Test");
                //intent.putExtra(EXTRA_MESSAGE, (String) v.getTag());
                v.getContext().startActivity(intent);
            }
        });

        setupItem(holder);
        return row;
    }

    private void setupItem(StringHolder holder) {
        holder.voucherInfo.setText(holder.voucherInfoString);
    }

    public static class StringHolder {
        String voucherInfoString;
        TextView voucherInfo;
        Button sendMessageButton;
    }
}
