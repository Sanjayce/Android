package com.xl.util;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.xl.android.Activitys;
import com.xl.android.R;
/**
 * Activity界面
 */

public class ActivityFragment extends Fragment implements View.OnClickListener{

    private Button btn1,btn2;
    private TextView url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_layout,container,false);
        btn1 = view.findViewById(R.id.button1);
        btn2 = view.findViewById(R.id.button2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        url = view.findViewById(R.id.text_view);
        url.setAutoLinkMask(Linkify.ALL);
        url.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }


    @Override
    public void onClick(View view) {
      int id = view.getId();
        if(id == R.id.button1){
            startActivity(new Intent(getActivity(), Activitys.class));
        }
        if(id == R.id.button2){

        }
    }
}