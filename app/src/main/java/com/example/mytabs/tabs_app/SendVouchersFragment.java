package com.example.mytabs.tabs_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SendVouchersFragment extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (MainActivity.beingShop) {
            // TODO(Katharina): Substitute this by a good layout.
            view = inflater.inflate(R.layout.fragment_sendvouchers, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_two, container, false);
        }

        return view;
    }

}
