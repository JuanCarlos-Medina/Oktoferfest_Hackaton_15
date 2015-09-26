package com.example.mytabs.tabs_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyVouchersFragment extends Fragment {

    public final static String EXTRA_MESSAGE = "com.example.mytabs.tabs_app.MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_one, container, false);

        String[] openMessages = new String[] { "Voucher 1", "Voucher 2", "Voucher 3",
                "Voucher 4", "Voucher 5"};

        List<String> openMessageList = new ArrayList<>(Arrays.asList(openMessages));

        MessageListAdapter openMessagesAdapter =
                new MessageListAdapter(
                        getActivity(),
                        R.layout.list_item_openmessages,
                        openMessageList);

        final ListView openMessagesListView = (ListView) rootView.findViewById(R.id.listview_openmessages);
        openMessagesListView.setAdapter(openMessagesAdapter);

        openMessagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                String item = (String) parent.getItemAtPosition(position);


                Context context =  view.getContext();
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                /*
                Intent intent = new Intent(getActivity(), VoucherActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "hallo Test");
                startActivity(intent);
                */
            }
        });

        return rootView;
    }

}
