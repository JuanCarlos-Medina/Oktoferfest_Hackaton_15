package com.example.mytabs.tabs_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyVouchersFragment extends Fragment {

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

        ArrayAdapter <String> openMessagesAdapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.list_item_openmessages, // ID der XML-Layout Datei
                        R.id.list_item_openmessages_textview,
                        openMessages);

        ListView openMessagesListView = (ListView) rootView.findViewById(R.id.listview_openmessages);
        openMessagesListView.setAdapter(openMessagesAdapter);

        return rootView;
    }
}
