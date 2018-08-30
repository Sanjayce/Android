package com.xl.android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 主页面
 */

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment,container,false);
        TextView textView = view.findViewById(R.id.main_text);
        textView.setText(getData());
        return view;
    }

    private String getData(){
        StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream =  getActivity().getResources().getAssets().open("discription.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream,"utf8");
            BufferedReader reader = new BufferedReader(streamReader);
            String line;
            while ((line = reader.readLine())!=null){
                builder.append(line).append("\n");
            }
            reader.close();
            streamReader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return builder.toString();
    }
}
