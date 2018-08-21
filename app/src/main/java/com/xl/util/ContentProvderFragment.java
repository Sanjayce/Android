package com.xl.util;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xl.android.R;
import com.xl.android.content.AndroidFile;
import com.xl.android.content.AndroidSDFile;
import com.xl.android.content.ContentProviders;
import com.xl.android.content.ContentValue;
import com.xl.android.content.ContentpActivity;
import com.xl.android.content.SQLiteDatabases;
import com.xl.android.content.SQLites;
import com.xl.android.content.SharedPreferencesActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *  BroadCast界面
 */

public class ContentProvderFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contentp_layout,container,false);
        initView(view);
        StringBuilder builder = new StringBuilder();
        String str1 = readeRawData();
        String str2 = readeAssetsData();
        builder.append(str1).append(str2);
        TextView textView =  view.findViewById(R.id.content);
        textView.setText(builder.toString());
        return view;
    }

    private String readeRawData() {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream is = getResources().openRawResource(R.raw.file);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private String readeAssetsData() {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream is = getResources().getAssets().open("file.txt");
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private void initView(View view) {
        view.findViewById(R.id.Button00).setOnClickListener(this);
        view.findViewById(R.id.Button01).setOnClickListener(this);
        view.findViewById(R.id.Button02).setOnClickListener(this);
        view.findViewById(R.id.Button03).setOnClickListener(this);
        view.findViewById(R.id.Button04).setOnClickListener(this);
        view.findViewById(R.id.Button05).setOnClickListener(this);
        view.findViewById(R.id.Button06).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Button00:
                startActivity(new Intent(getActivity().getApplicationContext(), SQLites.class));
                break;
            case R.id.Button01:
                startActivity(new Intent(getActivity().getApplicationContext(), ContentValue.class));
                break;
            case R.id.Button02:
                startActivity(new Intent(getActivity().getApplicationContext(), ContentProviders.class));
                break;
            case R.id.Button03:
                startActivity(new Intent(getActivity().getApplicationContext(), SharedPreferencesActivity.class));
                break;
            case R.id.Button04:
                startActivity(new Intent(getActivity().getApplicationContext(), AndroidFile.class));
                break;
            case R.id.Button05:
                startActivity(new Intent(getActivity().getApplicationContext(), AndroidSDFile.class));
                break;
            case R.id.Button06:
                startActivity(new Intent(getActivity().getApplicationContext(), SQLiteDatabases.class));
                break;
        }
    }
}
