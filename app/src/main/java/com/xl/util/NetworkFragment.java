package com.xl.util;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xl.android.R;
import com.xl.android.network.HttpActivity;
import com.xl.android.network.WebActivity;
import com.xl.android.network.XmlAndJsonActivity;

/**
 *  Network界面
 */

public class NetworkFragment extends Fragment  implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.network_layout,container,false);
        view.findViewById(R.id.button5).setOnClickListener(this);
        view.findViewById(R.id.button6).setOnClickListener(this);
        view.findViewById(R.id.button7).setOnClickListener(this);
        view.findViewById(R.id.button8).setOnClickListener(this);
        view.findViewById(R.id.button9).setOnClickListener(this);
        view.findViewById(R.id.button10).setOnClickListener(this);
        view.findViewById(R.id.button11).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button5:
                startActivity(new Intent(getActivity(), HttpActivity.class));
                break;
            case R.id.button6:

                break;
            case R.id.button7:
                startActivity(new Intent(getActivity(), XmlAndJsonActivity.class));
                break;
            case R.id.button8:
                startActivity(new Intent(getActivity(), WebActivity.class));
                break;
            case R.id.button9:

                break;
            case R.id.button10:

                break;
            case R.id.button11:

                break;
        }
    }
}
