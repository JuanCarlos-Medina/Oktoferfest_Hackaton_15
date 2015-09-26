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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_one, container, false);

        List<String> openMessageList = MainActivity.openMessages;

        MessageListAdapter openMessagesAdapter =
                new MessageListAdapter(
                        getActivity(),
                        R.layout.list_item_openmessages,
                        openMessageList);

        final ListView openMessagesListView = (ListView) rootView.findViewById(R.id.listview_openmessages);
        openMessagesListView.setAdapter(openMessagesAdapter);

        return rootView;
    }

}
