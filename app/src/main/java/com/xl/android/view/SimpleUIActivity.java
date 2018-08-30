package com.xl.android.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.xl.android.R;

/**
 * Android UI控件:普通控件
 */

public class SimpleUIActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private TextView textView;
    private EditText editText;
    private ImageButton imageButton;
    private Button button;
    private RadioGroup radiogroup;
    private RadioButton radiobutton1, radiobutton2, radiobutton3;
    private CheckBox cbox1;
    private CheckBox cbox2;
    private CheckBox cbox3;
    private CheckBox cbox4;
    private ToggleButton togglebutton;
    private ImageView imageView;
    private AutoCompleteTextView autoCompleteTextView;
    private MultiAutoCompleteTextView multiAutoCompleteTextView;
    private Spinner spinner;

    private String[] res={"beijing","shanghai","guangzhou","shenzheng","chengdu","chongqing"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_ui_layout);
        setTitle("SimpleUI");
        initUi();
    }

    private void initUi() {
        textView = (TextView) findViewById(R.id.text_view);
        textView.setAutoLinkMask(Linkify.ALL);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        editText = (EditText) findViewById(R.id.editText);
        imageButton = (ImageButton) findViewById(R.id.imagebutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SimpleUIActivity.this, "ImageButton", Toast.LENGTH_SHORT).show();
            }
        });
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SimpleUIActivity.this, "Button", Toast.LENGTH_SHORT).show();
            }
        });
        radiogroup = (RadioGroup) findViewById(R.id.radio_group);
        radiobutton1 = (RadioButton) findViewById(R.id.radioButton1);
        radiobutton2 = (RadioButton) findViewById(R.id.radioButton2);
        radiobutton3 = (RadioButton) findViewById(R.id.radioButton3);
        cbox1 = (CheckBox) findViewById(R.id.checkbox1);
        cbox2 = (CheckBox) findViewById(R.id.checkbox2);
        cbox3 = (CheckBox) findViewById(R.id.checkbox3);
        cbox4 = (CheckBox) findViewById(R.id.checkbox4);
        togglebutton = (ToggleButton) findViewById(R.id.toggle_button);
        imageView = (ImageView) findViewById(R.id.image_view);
        radiogroup.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                if (arg1 == R.id.radioButton1) {
                    Toast.makeText(SimpleUIActivity.this, radiobutton1.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                if (arg1 == R.id.radioButton2) {
                    Toast.makeText(SimpleUIActivity.this, radiobutton2.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                if (arg1 == R.id.radioButton3) {
                    Toast.makeText(SimpleUIActivity.this, radiobutton3.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        cbox1.setOnCheckedChangeListener(this);
        cbox2.setOnCheckedChangeListener(this);
        cbox3.setOnCheckedChangeListener(this);
        cbox4.setOnCheckedChangeListener(this);

        togglebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                imageView.setBackgroundResource(arg1 ? R.mipmap.lighton : R.mipmap.lightoff);
            }
        });

        final ArrayAdapter<String> arrayadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv);
        autoCompleteTextView.setAdapter(arrayadapter);
        multiAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.mactv);
        multiAutoCompleteTextView.setAdapter(arrayadapter);
        //设置分隔符
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        final ArrayAdapter<String> arrayadapters = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(arrayadapters);
        spinner.setDropDownWidth(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SimpleUIActivity.this, arrayadapter.getItem(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (cbox1.isChecked()) {
            Toast.makeText(SimpleUIActivity.this, cbox1.getText().toString(), Toast.LENGTH_SHORT).show();
            cbox1.setChecked(false);
        }
        if (cbox2.isChecked()) {
            Toast.makeText(SimpleUIActivity.this, cbox2.getText().toString(), Toast.LENGTH_SHORT).show();
            cbox2.setChecked(false);
        }
        if (cbox3.isChecked()) {
            Toast.makeText(SimpleUIActivity.this, cbox3.getText().toString(), Toast.LENGTH_SHORT).show();
            cbox3.setChecked(false);
        }
        if (cbox4.isChecked()) {
            Toast.makeText(SimpleUIActivity.this, cbox4.getText().toString(), Toast.LENGTH_SHORT).show();
            cbox4.setChecked(false);
        }
    }
}
