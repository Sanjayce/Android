package com.xl.util;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xl.android.R;
import com.xl.android.broadcast.BroadcastActivity;

/**
 *  BroadCast界面
 */

public class BroadcastFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.broadcast_fragment,container,false);
        view.findViewById(R.id.fragment_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BroadcastActivity.class));
            }
        });
        return view;
    }
}
