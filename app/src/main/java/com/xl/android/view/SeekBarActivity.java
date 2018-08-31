package com.xl.android.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xl.android.R;

/**
 * SeekBar:可拖动进度条-->用于音频，视频播放进度显示
 */

public class SeekBarActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seekpar_layout);
        setTitle("SeekBar");
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.text);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        textView.setText("当前进度:"+i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Toast.makeText(this,"Start...",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Toast.makeText(this,"Stop...",Toast.LENGTH_SHORT).show();
    }
}
