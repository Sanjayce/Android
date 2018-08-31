package com.xl.android.view;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.xl.android.R;

import java.util.Calendar;

/**
 * 日历，日期，时间控件
 */

public class DataTimeActivity extends AppCompatActivity {

    private Calendar calendar;
    private DatePicker datePicker;
    private DatePickerDialog datePickerDialog;
    private TimePicker timePicker;
    private TimePickerDialog timePickerDialog;
    private int year,mouth,day,hour,miute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_time_layout);
        setTitle("Data/Time");
        initUI();
    }

    @TargetApi(24)
    private void initUI() {
        //Calendar是从0开始计数
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        mouth = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        miute = calendar.get(Calendar.MINUTE);

        //日期
        datePicker = (DatePicker) findViewById(R.id.date_picker);
        datePicker.init(year, mouth, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                i1++;
                Toast.makeText(DataTimeActivity.this,i+"/"+i1+"/"+i2,Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.date_picker_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateDialog();
            }
        });

        //时间
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                Toast.makeText(DataTimeActivity.this,i+":"+i1,Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.time_picker_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimeDialog();
            }
        });

    }

    private void getDateDialog(){
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1++;
                Toast.makeText(DataTimeActivity.this,i+"/"+i1+"/"+i2,Toast.LENGTH_SHORT).show();
            }
        },year,mouth,day);
        datePickerDialog.show();
    }

    private void getTimeDialog(){
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Toast.makeText(DataTimeActivity.this,i+":"+i1,Toast.LENGTH_SHORT).show();
            }
        },hour,miute,true);
        timePickerDialog.show();
    }
}
