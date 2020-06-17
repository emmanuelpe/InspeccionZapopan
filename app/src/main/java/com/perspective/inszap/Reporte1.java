package com.perspective.inszap;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class Reporte1 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
 private Button btnfecha,btnfecha2,botonaceptar;
 private EditText ediF1,edif2;
 private int dayf,monthf,yearf,boton1,boton2;

 private String fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte1);

      this.btnfecha=(Button)findViewById(R.id.btnFecha1);
      this.btnfecha2=(Button)findViewById(R.id.btnFecha2);
      this.botonaceptar=(Button)findViewById(R.id.aceptarF);
      this.ediF1=(EditText)findViewById(R.id.etfecham1);
      this.edif2=(EditText)findViewById(R.id.etfecham2);
      this.boton1=0;
      this.boton2=0;
      final DataPickerDialogFragment p= new DataPickerDialogFragment();
      btnfecha2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              p.show(getSupportFragmentManager(),"data picker");
              //updateDisplay1(edif2);
              boton2=1;
          }
      });

      btnfecha.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

             p.show(getSupportFragmentManager(),"data picker");
             boton1=1;
             //updateDisplay1(ediF1);
          }
      });

      botonaceptar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              
          }
      });








    }
    private void updateDisplay1( EditText v) {

      v.setText(fecha);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);

        //String currentString= DateFormat.getDateInstance().format(dayOfMonth+"/"+month+"/"+year);
        //ediF1.setText(currentString);
        /*yearf=year;
        dayf=dayOfMonth;
        monthf=monthOfYear;*/


        fecha= dayOfMonth+"/"+monthOfYear+"/"+year;
        if (boton2==1 && boton1==0)
        updateDisplay1(edif2);
        boton2=0;
        if(boton2==0 && boton1==1)
            updateDisplay1(ediF1);
        boton1=0;
    }


}