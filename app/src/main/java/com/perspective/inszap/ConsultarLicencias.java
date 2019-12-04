package com.perspective.inszap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ConsultarLicencias extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

	private EditText etNombre,etNumeroL,etCalle,etExterior,etColonia;
	private Button btnBuscar;
	private ListView lv;
	private ArrayAdapter<String> adapter;
	private List<String> datos = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_licencias);

		etNombre = findViewById(R.id.etNombre);
		etNumeroL = findViewById(R.id.etNumeroL);
		etCalle = findViewById(R.id.etCalle);
		etExterior = findViewById(R.id.etExterior);
		etColonia = findViewById(R.id.etColonia);
		btnBuscar = findViewById(R.id.btnBuscar);
		lv = findViewById(R.id.lv);

		btnBuscar.setOnClickListener(this);

		adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.simple_list_item,datos);

		lv.setAdapter(adapter);

		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM v_LicenciasReglamentos", null);
		Log.i("que", "SELECT * FROM v_LicenciasReglamentos");

		Log.v("count",c.getCount() + "");

		//lv.setOnItemSelectedListener(this);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnBuscar:
				if(validarCampos() < 5)
					new BuscarL().execute(etNombre.getText().toString(),etNumeroL.getText().toString(),etCalle.getText().toString(),etExterior.getText().toString(),etColonia.getText().toString());
				else {
					Toast toast = Toast.makeText(getApplicationContext(),"Debe ingresar por lo menos un filtro de busqueda",Toast.LENGTH_LONG);
					toast.setGravity(0,0,15);
					toast.show();
				}
				break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

		mostrarDialogo();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mostrarDialogo();
	}

	public class BuscarL extends AsyncTask<String,Void,Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(String... strings) {
			buscarL(strings);
			return null;
		}

		@Override
		protected void onPostExecute(Boolean aBoolean) {
			super.onPostExecute(aBoolean);
			if(datos.isEmpty()) {
				Toast toast = Toast.makeText(ConsultarLicencias.this, "No se encontro licencia", Toast.LENGTH_SHORT);
				toast.setGravity(0,0,15);
				toast.show();
			}
			adapter.notifyDataSetChanged();
		}
	}

	public void buscarL(String... strings) {
		String condicion = "";
		if(!strings[0].equalsIgnoreCase(""))
			condicion += " and Nombre like'%"+strings[0].trim()+"%'";
		if(!strings[1].equalsIgnoreCase(""))
			condicion += " and NumeroLicencia like '%"+strings[1].trim()+"%'";
		if(!strings[2].equalsIgnoreCase(""))
			condicion += " and NombreCalle like '"+strings[2].trim()+"'";
		if(!strings[3].equalsIgnoreCase(""))
			condicion += " and Exterior like '"+strings[3].trim()+"'";
		if(!strings[4].equalsIgnoreCase(""))
			condicion += " and NombreColonia like '%"+strings[4].trim()+"%'";

		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		datos.clear();
		try{
			Cursor c = db.rawQuery("SELECT * FROM v_LicenciasReglamentos where 1=1 " + condicion, null);
			Log.i("que", "SELECT * FROM v_LicenciasReglamentos where 1=1 " + condicion);
			if(c.moveToFirst()){
				Log.i("no", c.getCount() + "");
				do{
					datos.add("Nombre:" + c.getString(c.getColumnIndex("Nombre")) + " Número licencia:" + c.getString(c.getColumnIndex("NumeroLicencia")) + " Calle:" + c.getString(c.getColumnIndex("NombreCalle")) + " Exterior:" + c.getString(c.getColumnIndex("Exterior")) + " Colonia:" + c.getString(c.getColumnIndex("NombreColonia")) + " Giro:" + c.getString(c.getColumnIndex("GiroPrincipal")) + " Categoria:" + c.getString(c.getColumnIndex("GiroPrincipal")) + " Fecha pago:" + c.getString(c.getColumnIndex("FechaPago")));
				}while(c.moveToNext());

			}
			c.close();
		}catch (SQLiteException e) {
			Log.i("ERROR FATAL", e.getMessage());
		}finally{
			db.close();
		}
	}

	public int validarCampos() {
		int count = 0;
		if(etNombre.getText().toString().trim().equalsIgnoreCase(""))
			count++;
		if(etNumeroL.getText().toString().trim().equalsIgnoreCase(""))
			count++;
		if(etExterior.getText().toString().trim().equalsIgnoreCase(""))
			count++;
		if(etCalle.getText().toString().trim().equalsIgnoreCase(""))
			count++;
		if(etColonia.getText().toString().trim().equalsIgnoreCase(""))
			count++;
		return count;
	}

	public void mostrarDialogo() {
		AlertDialog.Builder builder = new AlertDialog.Builder(ConsultarLicencias.this);
		builder.setTitle("Mensaje").setMessage("Seleccione Accion");
		builder.setPositiveButton("Orden de visita", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.setNegativeButton("Infracción", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
