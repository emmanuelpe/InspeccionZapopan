package com.perspective.inszap;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Reporte1 extends AppCompatActivity {
 private Button btnfecha,btnfecha2;
 private EditText ediF1,edif2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte1);

      this.btnfecha=(Button)findViewById(R.id.btnFecha1);
      this.btnfecha2=(Button)findViewById(R.id.btnFecha2);
      this.ediF1=(EditText)findViewById(R.id.etfecham1);
      this.edif2=(EditText)findViewById(R.id.etfecham2);


      btnfecha2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          }
      });

      btnfecha.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          }
      });





    }
}