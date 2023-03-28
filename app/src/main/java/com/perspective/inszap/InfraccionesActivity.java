package com.perspective.inszap;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.bixolon.printer.BixolonPrinter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.DottedLineSeparator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import androidx.core.content.FileProvider;

public class InfraccionesActivity extends Activity implements OnClickListener, Runnable, OnCheckedChangeListener, OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {
    private Connection conect;
    static int id_inspectorQ=0;
    static String valW="";
    private View linea0,linea1,linea2,linea3,linea4,linea5,linea6,linea7,linea8,linea9,linea10,linea11,linea12,linea13,linea14,linea15;
	private Button btncopiar,btneliminarA,btneliminarA1,btneliminarA2,btneliminarA3,btneliminarA4,btnArticulos,btnFecha,btnInicio,btnaceptar,btnTomarF,btnGuardar,btnImprimir,btnConsultar,btnSi,btnNo,btnVisualizar,btnMostrar,btnSalir,tveliminar,tveliminar1,tveliminar2,tveliminar3,tveliminar4,tveliminar5,tveliminar6,tveliminar7,tveliminar8,tveliminar9,tveliminar10,tveliminar11,tveliminar12,tveliminar13,tveliminar14,tveliminar15,btnmodificar,btnFtp,btnB,btnOrden1,btnVista,btnver1,btnver2,btnver3,btnver4,btnver5,btnver6,btnver7,btnver8,btnver9,btnver10,btnver11,btnver12,btnver13,btnver14,btnver15,btnver16,btnImprimirResum,btnBCol;
	private TextView tvfechaOv,tvBuscar,tvfudamentoEx,tvfechaClau,tvfolioclau,tvfechap,tvfolioap,tvuni,tvuni1,tvuni2,tvuni3,tvuni4,tvuni5,tvuni6,tvuni7,tvuni8,tvuni9,tvuni10,tvuni11,tvuni12,tvuni13,tvuni14,tvuni15,tvTitle,tvTipo,tvEspe,tvOV,tvC,tvEvidencia,tvReg,tvActa,tvMotivo,tvAcomp,tvCondominio,tvNombreComercial,tvALicencia,etInfraccion,etSeleccion,tvReferencia,tvgiro,tvNLicencia,tvPeticion,tvNota,tvUso,tvPropietario,tvMC,tvPropiedad,spselec1,tvDonde;
	private String s, config = "",archivo = "",name,us,ifeI,noI,vigI,ifeA,ifeA1,ifeA2,ifeA3,ifeA4,noA,noA1,noA2,noA3,noA4,vigA,vigA1,vigA2,vigA3,vigA4,AnombreTestigo,ifeTestigo,unidad,/*codigo = "",zonificacion,reglamento,lap,ordenamientoEco,nae,leeepa,*/des,des1="",des2="",des3="",des4="",des5="",des6="",des7="",des8="",des9="",des10="",des11="",des12="",des13="",des14="",des15="",/*cod="",zon="",reg="",la="",ordeco="",na="",lee="", codi="",zoni="",regla="",l="",oe="",ne = "",leeep = "",*/text = "",regex=",",title,seleccion = "",fecha,hora,id_hechos = "",unidades="",numero = "", hr,c_fecha = "",tipoActa,result = "",dato,usoCatalogo = "S",msj = "",orde,direccion,ante = "IN",formato = "infraccion",numeroOV="",fechaOV="",competencias = "",regla= "",zon="",ident = "",firma="",idT = "",idT1 = "",medidas1 = "",mConnectedDeviceName = "",competencias1 = "",propiedad = "El Visitado",fracciones = "",articulos = "",folio = "",clave = "",fol = "",Axmedidas="",concatM="";
	private final String DECLARA = "A su vez, el visitado en ejercicio de su derecho y en uso de la voz declara:"; 
	private int banderagiro=0, countF = 0,mYear,mMonth,mDay,a,m,di,diaPlazo=0,con = 0,contc = 0,contz = 0,contl = 0,conto = 0, co = 0,foto = 0,id,evento,infrac = 1,id_inspector1,id_inspector2,id_infra,nuevo = 0,pos = 0,infraccion=0,id_inspector3 = 0,id_inspector4 = 0,id_inspector5 = 0,id_inspector6 = 0,idCompetencia1 = 0,idCompetencia2 = 0,idCompetencia3 = 0,idCompetencia4 = 0,idCompetencia5 = 0,conf = 0,tipoEntrega = 0;
	private Spinner spgiro,spnombre,spNombreA,spNombreA1,spNombreA2,spNombreA3,spNombreA4,spIdentifica,spManifiesta,spuso,spgravedad,spZona,spdesignado,spdesignado1,spInfraccion,spconsultar,spPoblacion,spFraccionamiento,spIdentificaT,spIdentificaT1,spReglamento,spMedida,spInspectorT,spInspectorT1,spPeticion,spNE,spUsoH,spuni,spuni1,spuni2,spuni3,spuni4,spuni5,spuni6,spuni7,spuni8,spuni9,spuni10,spuni11,spuni12,spuni13,spuni14,spuni15,spMeConstitui,spDensidad,spCreglamentos;
	private EditText etfudamentoEx,etfechaClau,etfolioclau,etfoliopeticion,etfolioap,etfechap,etNum,etFecham,etfecha,etDiaPlazo,etIfeI,etNoI,etVigI,etIfeA,etIfeA1,etIfeA2,etIfeA3,etIfeA4,etNoA,etNoA1,etNoA2,etNoA3,etNoA4,etVigA,etVigA1,etVigA2,etVigA3,etVigA4,etNombreT,etIfeT,etDesc,etDesc1,etDesc2,etDesc3,etDesc4,etDesc5,etDesc6,etDesc7,etDesc8,etDesc9,etDesc10,etDesc11,etDesc12,etDesc13,etDesc14,etDesc15,etdato,etdato1,etdato2,etdato3,etdato4,etdato5,etdato6,etdato7,etdato8,etdato9,etdato10,etdato11,etdato12,etdato13,etdato14,etdato15,desf,desf1,desf2,etNombreV,etFraccionamiento,etCalle,etNumero,etPropietario,etNombreT1,etIfeT2,etManifiesta,etNuemroInterior,etApellidoP,etApellidoM,etCitatorio,etNumeroActa,etEspecificacion,etDFoto,etDFoto1,etDFoto2,etDFoto3,etVManifiesta,etVIdentifica,etLatitud,etLongitud,etAnoCitatorio,etAnoOrden,etCondominio/*etDensidad*/,etManzana,etLote,etReferencia,etBuscar,etfolio,/*etAlineamiento,*/etConstruccion, etGiro, etMotivo,etOrden1,etfechaOV,etEntreC,etEntreC1,etResponsable,etRegistro,etMedida,etMedida1,etMedida2,etMedida3,etMedida4,etArticulo,etInspccionFue,etDFoto4,etDFoto5,etDFoto6,etDFoto7,etDFoto8,etDFoto9,etDFoto10,etDFoto11,etDFoto12,etDFoto13,etDFoto14,etDFoto15,etDFoto16,etDFoto17,etDFoto18,etDFoto19,etLGiro,etAGiro,etAlicencia,etSector,etNombreComercial,etObs,etObs1,etObs2,etObs3,etObs4,etObs5,etObs6,etObs7,etObs8,etObs9,etObs10,etObs11,etObs12,etObs13,etObs14,etObs15,etBCol,etOtro,etDondeActua,etNumeroSellos,etDecomiso;
	private LinearLayout lldiv,cons,llNota,llplazo,llreincidencia,llcomp,llconcepto,llPla,llfundamento;
	private RelativeLayout rlcampo,rlProp,rlTestA,rlVisita,rlLicencias,rlDonde_actua;
	private RadioGroup /*radiogroup,*/rgReincidencia,rgPopiedad,rgTipo;
	static final int DATE_DIALOG_ID = 0;
    private JSONParser jsonParser = new JSONParser();
    private ArrayList<String> foto2 = new ArrayList<String>();
    private ArrayList<String> archivo2 = new ArrayList<String>();
	private boolean desc=false;
    private boolean desc1=false;
    private boolean desc2=false;
    private boolean desc3=false;
    private boolean desc4=false;
    private boolean desc5=false;
    private boolean desc6=false;
    private boolean desc7=false;
    private boolean desc8=false;
    private boolean desc9=false;
    private boolean desc10=false;
    private boolean desc11=false;
    private boolean desc12=false;
    private boolean desc13=false;
    private boolean desc14=false;
    private boolean desc15=false;
    private boolean citatorio;
    private boolean inicio = false;
    private boolean res = false;
    private String res2 = "";
    private boolean consu = false;
    private boolean resu = false;
    private boolean resov = false;
    private boolean guarda = false;
	final Calendar c = Calendar.getInstance();
	final Calendar cal = Calendar.getInstance();
    private ArrayList<String> reglaArt= new ArrayList<>();
    private ArrayList<String> SeguimientoM1=new ArrayList<>();
    private ArrayList<String> reglaArt2= new ArrayList<>();
    private ArrayList<Integer> norepeat= new ArrayList<>();
    private String concatB="";
    private String concatA="";
    private int contador=0;
    final JSONParser jParser = new JSONParser();
    private ArrayList<String> arrayincaseF= new ArrayList<>();
    private ArrayList<String> arrayhechosC= new ArrayList<>();
	final ArrayList<String> arregloLista = new ArrayList<String>();
	private ArrayList<String> arregloLista1 = new ArrayList<String>();
	private ArrayList<String> arregloLista2 = new ArrayList<String>();
	final ArrayList<String> arregloInfraccion = new ArrayList<String>();
    final ArrayList<String> arregloInfraccion1 = new ArrayList<String>();
    final ArrayList<String> arregloCreglamentos= new ArrayList<String>();
    final ArrayList<String> arregloCreglamentosx=new ArrayList<>();
	final ArrayList<String> consultar = new ArrayList<String>();
	final ArrayList<Integer> id_hecho = new ArrayList<Integer>();
	final ArrayList<String> arreglo = new ArrayList<String>();
	final ArrayList<Integer> id_i1 = new ArrayList<Integer>();
	final ArrayList<Integer> id_i2 = new ArrayList<Integer>();
	final ArrayList<Integer> id_i3 = new ArrayList<Integer>();
	final ArrayList<Integer> id_i4 = new ArrayList<Integer>();
	final ArrayList<Integer> id_i5 = new ArrayList<Integer>();
	final ArrayList<Integer> id_i6 = new ArrayList<Integer>();
	final ArrayList<String> vManifiesta = new ArrayList<String>();
	final ArrayList<String> vIdentifica = new ArrayList<String>();
	final ArrayList<String> usoSuelo = new ArrayList<String>();
    final ArrayList<String> usoSueloH = new ArrayList<String>();
	final ArrayList<String> poblacion = new ArrayList<String>();
	final ArrayList<String> fraccionamiento = new ArrayList<String>();
	final ArrayList<String> giros = new ArrayList<String>();
	final ArrayList<String> zonas = new ArrayList<String>();
	private ArrayList<String> campos = new ArrayList<String>();
	private ArrayList<String> cmedida = new ArrayList<String>();
	private ArrayList<String> cMedidaC=new ArrayList<>();
	private ArrayList<String> art = new ArrayList<String>();
	private ArrayList<String> numero_acta = new ArrayList<String>();
	private ArrayList<String> orden = new ArrayList<String>();
	private ArrayList<String> peticion = new ArrayList<String>();
	private ArrayList array = new ArrayList();
	private ArrayList<String> zona = new ArrayList<String>();
	private HashSet<Integer> hs = new HashSet<Integer>();
	private double latitud = 0, longitud = 0 ;
	LocationManager mLocationManager;
	Location mLocation;
	static String urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";
	MyLocationListener mLocationListener;
	private Location currentLocation = null;
	private BluetoothAdapter mBluetoothAdapter = null;
	private JSONArray jArray;
	private JSONObject json_data;
	private Connection conn = new Connection();
	private int idl = 0,idComp = 0,il = 0;
	private ProgressDialog pd;
	private Thread thread = null;
	private ArrayList<String> campo = new ArrayList<String>();
	private String campo1="",campo2="",campo3="",campo4="",campo5="",campo6="",campo7="",campo8="",campo9="",campo0="",campo11="",campo12="",campo13="",campo14 = "",campo15 = "",campo16 = "",campo17 = "",campo18 = "",campo19 = "",campo20 = "",campo21 = "",campo22 = "",campo23 = "",campo24 = "",campo25 = "",campo26 = "",c1="",c2="",c3="",c4="",c5="",c6="",c7="",c8="",c9="",c0="",c11="",c12="",c13="",c14="",c15="",c16="",c17="",c18="",c19="",c20="",c21="",c22="",c23="",c24="",c25="",c26="",camp1="",camp2="",camp3="",camp4="",camp5="",camp6="",camp7="",camp8="",camp9="",camp0="",camp11="",camp12="",camp13="",camp14="",camp15="",camp16="",camp17="",camp18="",camp19="",camp20="",camp21="",camp22="",camp23="",camp24="",camp25="",camp26="",hech = "los hechos antes descritos, constituyen una infracciÔøΩn a lo dispuesto por los artÔøΩculos:",conti = "Los cuales constituyen infracciÔøΩn de conformidad con lo dispuesto por los artÔøΩculos:",na = "";
	private CheckBox cbFlag,cbFirma,cbDatos,cbDatos2;
	private Button rbaper,rborden,rbcitatorio,rbHechos,radioInfraccion,radioEvento,radioReimprimir;
	private List<Levantamiento> lev = new ArrayList<Levantamiento>();
	private List<String> reglamento = new ArrayList<String>();
	private List<String> competencia = new ArrayList<String>();
	private List<String> campoReg = new ArrayList<String>();
    private List<String> fundamento = new ArrayList<String>();
    private List<String> fraccion1 = new ArrayList<String>();
    private List<Integer> idFundamento = new ArrayList<Integer>();
    private List<Integer> idFraccion= new ArrayList<Integer>();
	//private List<String> reg = new ArrayList<String>();campoReg
	private List<Integer> idCompetencia = new ArrayList<Integer>();
	private List<MedidaSeguridad> medidas;
	private List<String> medida = new ArrayList<String>();
    private List<String> medidax = new ArrayList<String>();
	private PopupWindow popupWindow;
	private CheckBox cb;
	private String [] comp;
	private int [] iComp,reg,fra;
	private ArrayAdapter<String> adapter,adapter1,adapterUso;
	public static BixolonPrinter mBixolonPrinter;
	public static final String TAG = "BixolonPrinterSample";
	private AlertDialog mSampleDialog;
	private Switch swReincidencia;
	private ArrayAdapter adapterCol,adapterMeC,adapterDensidad,adapterGiro;
	private List<String> conceptos,articulo,fraccion,unis,unis1,unis2,unis3,unis4,unis5,unis6,unis7,unis8,unis9,unis10,unis11,unis12,unis13,unis14,unis15;
	private ArrayAdapter adapterUni,adapterUni1,adapterUni2,adapterUni3,adapterUni4,adapterUni5,adapterUni6,adapterUni7,adapterUni8,adapterUni9,adapterUni10,adapterUni11,adapterUni12,adapterUni13,adapterUni14,adapterUni15;
	private List<String> fundam = new ArrayList<>();
	private List<String> meConstitui = new ArrayList<>();
	private List<String> folios = new ArrayList<>();
	private ProgressBar pb;
    String reglamentoC = "";
    private Button btnBusArt;
    private TextInputLayout tilArticulo;
    private TextInputEditText etArti;
    private SharedPreferences sp;
    private int foliox = 0,id_l;
    static int validarM;
    static String tableta="";

    private Spinner reglamentoSP;
    private JSONArray jarray;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infracciones);
		
		Typeface helvetica = Typeface.createFromAsset(getAssets(), "font/HelveticaNeueLTStd-Bd.otf");
		
		Log.e("lala", "Hola Mundo!");
        savedInstanceState = getIntent().getExtras();
        title = savedInstanceState.getString("direccion");
        this.us = savedInstanceState.getString("usuario");
        this.id = savedInstanceState.getInt("id");
        //this.id_inspector1=savedInstanceState.getInt("id_inspector");
        System.out.println(MainActivity.id_ins_sesion);
        this.direccion = savedInstanceState.getString("direccion");
        this.conf = savedInstanceState.getInt("con");
        
        System.out.println("id " + id);
        System.err.println(conf + " con");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        String n1 = "09";
        int n2 = Integer.parseInt(n1) + 1;
        n1 = String.valueOf(n2);
        if(n1.length() == 1) 
        	Log.i("numero s ", "0" + n1);
        else
        	Log.i("numero n ", n1);
        
		foto();

		for(int x = 0;x < 5;x++) {
		    folios.add("");
        }

		System.out.println(subirFoto());
		idl = consultarLevantamientoID();
		if(idl == 0){
			idl = 1;
		}
        
        arreglo.add(us);
        
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        a = c.get(Calendar.YEAR);
        m = c.get(Calendar.MONTH);
        di = c.get(Calendar.DAY_OF_MONTH);
        
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int hor = cal.get(Calendar.HOUR_OF_DAY);
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
        Date dat = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Log.i("hr del dia", hor + " dia: " + dia + " " + diaSemana);
        if(hor > 16 | (diaSemana == 1 | diaSemana == 7) | diaNoHabiles(sdf.format(dat)) > 0) {
        	tipoActa = "Verificación";
        }
        else {
        	tipoActa = "Inspección";
        }
		Log.i("Tipo Acta", tipoActa);
		
		int n = mMonth + 1;
	    Log.i("Fecha", "dia: " + mDay + " mes: " + n + " ano: " + mYear);
		
        Log.i("usuario", us + " id " + id);
        
        validarFecha();
        if(!c_fecha.equalsIgnoreCase("")) {
        	Log.i("FECHA CON", c_fecha + " ");
            String reg = "/";
            String[] fe = c_fecha.split(reg);
            
            if((Integer.parseInt(fe[0]) < mDay & Integer.parseInt(fe[1]) <= n) & Integer.parseInt(fe[2]) == mYear) {
            	Log.i("Levantamiento", "True");
            }
            else {
            	Log.i("Levantamiento", "False");
    		}
        }

        this.reglamentoSP = (Spinner)findViewById(R.id.spCreglamento);
        this.linea0=(View)findViewById(R.id.linea0);
        this.linea1=(View)findViewById(R.id.linea1);
        this.linea2=(View)findViewById(R.id.linea2);
        this.linea3=(View)findViewById(R.id.linea3);
        this.linea4=(View)findViewById(R.id.linea4);

        this.linea5=(View)findViewById(R.id.linea5);
        this.linea6=(View)findViewById(R.id.linea6);
        this.linea7=(View)findViewById(R.id.linea7);
        this.linea8=(View)findViewById(R.id.linea8);
        this.linea9=(View)findViewById(R.id.linea9);

        this.linea10=(View)findViewById(R.id.linea10);
        this.linea11=(View)findViewById(R.id.linea11);
        this.linea12=(View)findViewById(R.id.linea12);
        this.linea13=(View)findViewById(R.id.linea13);
        this.linea14=(View)findViewById(R.id.linea14);

        this.linea15=(View)findViewById(R.id.linea15);


        this.btnFecha = (Button)findViewById(R.id.btnFecha);
        this.btncopiar=(Button)findViewById(R.id.btnCopiar);
        this.btnArticulos=(Button)findViewById(R.id.etAcepA);
        this.btneliminarA=(Button)findViewById(R.id.etdeleA);
        this.btneliminarA1=(Button)findViewById(R.id.etdeleA1);
        this.btneliminarA2=(Button)findViewById(R.id.etdeleA2);
        this.btneliminarA3=(Button)findViewById(R.id.etdeleA3);
        this.btneliminarA4=(Button)findViewById(R.id.etdeleA4);
        this.spIdentifica = (Spinner)findViewById(R.id.spIdentifica);
        this.spManifiesta = (Spinner)findViewById(R.id.spManifiesta);
        this.spuso = (Spinner)findViewById(R.id.spuso);
        this.spgravedad = (Spinner)findViewById(R.id.spgravedad);
        this.etFecham = (EditText)findViewById(R.id.etfecham);
        this.etfechap=(EditText)findViewById(R.id.etfechap);
        this.etfolioap=(EditText)findViewById(R.id.etfolioap);


        this.etfechaClau=(EditText)findViewById(R.id.etfechaclau);
        this.etfolioclau=(EditText)findViewById(R.id.etfolioclau);
        this.etfoliopeticion=(EditText)findViewById(R.id.folioapeticion);
        this.tvfechap=(TextView)findViewById(R.id.tvfechaap);
        this.tvfolioap=(TextView)findViewById(R.id.tvfolioap);
        this.tvfolioclau=(TextView)findViewById(R.id.tvfolioclau);
        this.tvfechaClau=(TextView)findViewById(R.id.tvfechaClau);
        this.btnInicio = (Button)findViewById(R.id.btnInicio);
        this.spZona = (Spinner)findViewById(R.id.spZona);
        this.spdesignado = (Spinner)findViewById(R.id.spdesignado);
        this.spdesignado1 = (Spinner)findViewById(R.id.spdesignado1);
        this.etfecha = (EditText)findViewById(R.id.etfecha);
        this.etDiaPlazo = (EditText)findViewById(R.id.etdiasplazo);
        this.spnombre = (Spinner)findViewById(R.id.spNombre);
        this.spNombreA = (Spinner)findViewById(R.id.spNombreAcompanante);
        this.spNombreA1 = (Spinner)findViewById(R.id.spNombreAcompanante1);
        this.spNombreA2 = (Spinner)findViewById(R.id.spNombreAcompanante2);
        this.spNombreA3 = (Spinner)findViewById(R.id.spNombreAcompanante3);
        this.spNombreA4 = (Spinner)findViewById(R.id.spNombreAcompanante4);
        this.etIfeI = (EditText)findViewById(R.id.etIfe);
        this.etNoI = (EditText)findViewById(R.id.etnoempleado);
        this.etVigI = (EditText)findViewById(R.id.etvigenciaIns);
        this.etIfeA = (EditText)findViewById(R.id.etIfeAcompanante);
        this.etIfeA1 = (EditText)findViewById(R.id.etIfeAcompanante1);
        this.etIfeA2 = (EditText)findViewById(R.id.etIfeAcompanante2);
        this.etIfeA3 = (EditText)findViewById(R.id.etIfeAcompanante3);
        this.etIfeA4 = (EditText)findViewById(R.id.etIfeAcompanante4);
        this.etNoA = (EditText)findViewById(R.id.etnoempleadoAcompanante);
        this.etNoA1 = (EditText)findViewById(R.id.etnoempleadoAcompanante1);
        this.etNoA2 = (EditText)findViewById(R.id.etnoempleadoAcompanante2);
        this.etNoA3 = (EditText)findViewById(R.id.etnoempleadoAcompanante3);
        this.etNoA4 = (EditText)findViewById(R.id.etnoempleadoAcompanante4);
        this.etVigA = (EditText)findViewById(R.id.etvigenciaAcom);
        this.etVigA1 = (EditText)findViewById(R.id.etvigenciaAcom1);
        this.etVigA2 = (EditText)findViewById(R.id.etvigenciaAcom2);
        this.etVigA3 = (EditText)findViewById(R.id.etvigenciaAcom3);
        this.etVigA4 = (EditText)findViewById(R.id.etvigenciaAcom4);
        this.etNombreT = (EditText)findViewById(R.id.etnombretestigo);
        this.etIfeT = (EditText)findViewById(R.id.etifetestigo);
        this.spInfraccion = (Spinner)findViewById(R.id.spHechos);
        this.etInfraccion = findViewById(R.id.etinfraccion);
        this.etDesc = (EditText)findViewById(R.id.etdescripcion);
        this.etDesc1 = (EditText)findViewById(R.id.etdescripcion1);
        this.etDesc2 = (EditText)findViewById(R.id.etdescripcion2);
        this.etDesc3 = (EditText)findViewById(R.id.etdescripcion3);
        this.etDesc4 = (EditText)findViewById(R.id.etdescripcion4);
        this.etDesc5 = (EditText)findViewById(R.id.etdescripcion5);
        this.etDesc6 = (EditText)findViewById(R.id.etdescripcion6);
        this.etDesc7 = (EditText)findViewById(R.id.etdescripcion7);
        this.etDesc8 = (EditText)findViewById(R.id.etdescripcion8);
        this.etDesc9 = (EditText)findViewById(R.id.etdescripcion9);
        this.etDesc10 = (EditText)findViewById(R.id.etdescripcion10);

        this.etDesc11 = (EditText)findViewById(R.id.etdescripcion11);
        this.etDesc12 = (EditText)findViewById(R.id.etdescripcion12);
        this.etDesc13 = (EditText)findViewById(R.id.etdescripcion13);
        this.etDesc14 = (EditText)findViewById(R.id.etdescripcion14);
        this.etDesc15 = (EditText)findViewById(R.id.etdescripcion15);

        this.etdato = (EditText)findViewById(R.id.etdato);
        this.tvuni = (TextView)findViewById(R.id.tvuni);
        this.tveliminar = (Button)findViewById(R.id.tveliminar);
        this.etdato1 = (EditText)findViewById(R.id.etdato1);
        this.tvuni1 = (TextView)findViewById(R.id.tvuni1);
        this.tveliminar1 = (Button)findViewById(R.id.tveliminar1);
        this.etdato2 = (EditText)findViewById(R.id.etdato2);
        this.tvuni2 = (TextView)findViewById(R.id.tvuni2);
        this.tveliminar2 = (Button)findViewById(R.id.tveliminar2);
        this.etdato3 = (EditText)findViewById(R.id.etdato3);
        this.tvuni3 = (TextView)findViewById(R.id.tvuni3);
        this.tveliminar3 = (Button)findViewById(R.id.tveliminar3);
        this.etdato4 = (EditText)findViewById(R.id.etdato4);
        this.tvuni4 = (TextView)findViewById(R.id.tvuni4);
        this.tveliminar4 = (Button)findViewById(R.id.tveliminar4);
        this.etdato5 = (EditText)findViewById(R.id.etdato5);
        this.tvuni5 = (TextView)findViewById(R.id.tvuni5);
        this.tveliminar5 = (Button)findViewById(R.id.tveliminar5);
        this.etdato6 = (EditText)findViewById(R.id.etdato6);
        this.tvuni6 = (TextView)findViewById(R.id.tvuni6);
        this.tveliminar6 = (Button)findViewById(R.id.tveliminar6);
        this.etdato7 = (EditText)findViewById(R.id.etdato7);
        this.tvuni7 = (TextView)findViewById(R.id.tvuni7);
        this.tveliminar7 = (Button)findViewById(R.id.tveliminar7);

        this.etdato8 = (EditText)findViewById(R.id.etdato8);
        this.tvuni8 = (TextView)findViewById(R.id.tvuni8);
        this.tveliminar8 = (Button)findViewById(R.id.tveliminar8);

        this.etdato9 = (EditText)findViewById(R.id.etdato9);
        this.tvuni9 = (TextView)findViewById(R.id.tvuni9);
        this.tveliminar9 = (Button)findViewById(R.id.tveliminar9);

        this.etdato10 = (EditText)findViewById(R.id.etdato10);
        this.tvuni10 = (TextView)findViewById(R.id.tvuni10);
        this.tveliminar10 = (Button)findViewById(R.id.tveliminar10);

        this.etdato11 = (EditText)findViewById(R.id.etdato11);
        this.tvuni11 = (TextView)findViewById(R.id.tvuni11);
        this.tveliminar11 = (Button)findViewById(R.id.tveliminar11);

        this.etdato12 = (EditText)findViewById(R.id.etdato12);
        this.tvuni12 = (TextView)findViewById(R.id.tvuni12);
        this.tveliminar12 = (Button)findViewById(R.id.tveliminar12);

        this.etdato13 = (EditText)findViewById(R.id.etdato13);
        this.tvuni13 = (TextView)findViewById(R.id.tvuni13);
        this.tveliminar13 = (Button)findViewById(R.id.tveliminar13);

        this.etdato14 = (EditText)findViewById(R.id.etdato14);
        this.tvuni14 = (TextView)findViewById(R.id.tvuni14);
        this.tveliminar14 = (Button)findViewById(R.id.tveliminar14);

        this.etdato15 = (EditText)findViewById(R.id.etdato15);
        this.tvuni15 = (TextView)findViewById(R.id.tvuni15);
        this.tveliminar15 = (Button)findViewById(R.id.tveliminar15);

        this.btnaceptar = (Button)findViewById(R.id.btnaceptar);
        this.lldiv = (LinearLayout)findViewById(R.id.llall);
        this.cons = (LinearLayout)findViewById(R.id.cons);
        this.tvTitle = (TextView)findViewById(R.id.tvTitle);
        this.desf = (EditText)findViewById(R.id.etDesFoto);
        this.desf1 = (EditText)findViewById(R.id.etDesFoto1);
        this.desf2 = (EditText)findViewById(R.id.etDesFoto2);
        this.btnTomarF = (Button)findViewById(R.id.btnTomarF);
        this.btnGuardar = (Button)findViewById(R.id.btnguardar);
        this.btnImprimir = (Button)findViewById(R.id.btnImprimir);
        this.etSeleccion = findViewById(R.id.etSelecion);
        this.rlcampo = (RelativeLayout)findViewById(R.id.rlcampos);
        //this.radiogroup = (RadioGroup)findViewById(R.id.ragroup);
        this.etNombreV = (EditText)findViewById(R.id.etNombreV);
        this.etFraccionamiento = (EditText)findViewById(R.id.etfraccionamiento);
        this.etCalle = (EditText)findViewById(R.id.etcalle);
        this.etNumero = (EditText)findViewById(R.id.etnumerocalle);
        this.etPropietario = (EditText)findViewById(R.id.etpropietario);
        this.etNombreT1 = (EditText)findViewById(R.id.etnombretestigo1);
        this.etIfeT2 = (EditText)findViewById(R.id.etifetestigo1);
        //this.etDensidad = (EditText)findViewById(R.id.etdensidad);
        this.etManifiesta = (EditText)findViewById(R.id.etvisitadom);
        this.btnConsultar = (Button)findViewById(R.id.btnConsultar);
        this.etNuemroInterior = (EditText)findViewById(R.id.etnumerointerior);
        this.etApellidoP = (EditText)findViewById(R.id.etApellidoP);
        this.etApellidoM = (EditText)findViewById(R.id.etApellidoM);
        this.btnSi = (Button)findViewById(R.id.btnSi);
        this.btnNo = (Button)findViewById(R.id.btnNo);
        this.etCitatorio = (EditText)findViewById(R.id.etCitatorio);
        this.etNumeroActa = (EditText)findViewById(R.id.etnActa);

        this.etEspecificacion = (EditText)findViewById(R.id.etespecificacion);
        
        this.etDFoto = (EditText)findViewById(R.id.etDesFoto);
        this.etDFoto1 = (EditText)findViewById(R.id.etDesFoto1);
        this.etDFoto2 = (EditText)findViewById(R.id.etDesFoto2);
        this.etDFoto3 = (EditText)findViewById(R.id.etDesFoto3);
        
        
        this.etDFoto4 = (EditText)findViewById(R.id.etDesFoto4);
        this.etDFoto5 = (EditText)findViewById(R.id.etDesFoto5);
        this.etDFoto6 = (EditText)findViewById(R.id.etDesFoto6);
        this.etDFoto7 = (EditText)findViewById(R.id.etDesFoto7);
        this.etDFoto8 = (EditText)findViewById(R.id.etDesFoto8);
        this.etDFoto9 = (EditText)findViewById(R.id.etDesFoto9);
        this.etDFoto10 = (EditText)findViewById(R.id.etDesFoto10);
        this.etDFoto11 = (EditText)findViewById(R.id.etDesFoto11);
        this.etDFoto12 = (EditText)findViewById(R.id.etDesFoto12);
        this.etDFoto13 = (EditText)findViewById(R.id.etDesFoto13);
        this.etDFoto14 = (EditText)findViewById(R.id.etDesFoto14);
        //this.etDFoto15 = (EditText)findViewById(R.id.etDesFoto15);
        this.etfudamentoEx=(EditText)findViewById(R.id.etfudamentoEx);
        this.tvfudamentoEx=(TextView)findViewById(R.id.tvfudamentoEx);
        this.etVManifiesta = (EditText)findViewById(R.id.etspecifique);
        this.etVIdentifica = (EditText)findViewById(R.id.etnumeroife);
        this.etLatitud = (EditText)findViewById(R.id.etLatitud);
        this.etLongitud = (EditText)findViewById(R.id.etLongitud);
        this.etNum = (EditText)findViewById(R.id.etnumero);
        this.btnVisualizar = (Button)findViewById(R.id.btnVFecha);
        this.etFecham.setEnabled(false);
        this.spconsultar = (Spinner)findViewById(R.id.spConsulta);
        this.tvTipo = (TextView)findViewById(R.id.tvTipo);
        this.btnMostrar = (Button)findViewById(R.id.btnDatos);
        this.tvEspe = (TextView)findViewById(R.id.tvEspecificacion);
        this.btnSalir = (Button)findViewById(R.id.btnSalirIn);
        this.spPoblacion = (Spinner)findViewById(R.id.spPoblacion);
        this.spFraccionamiento = (Spinner)findViewById(R.id.spFraccionamiento);
        this.spgiro=(Spinner)findViewById(R.id.spGiro);
        this.tvOV = (TextView)findViewById(R.id.tvOV);
        this.etAnoCitatorio = (EditText)findViewById(R.id.etAnoCitatorio);
        this.tvC = (TextView)findViewById(R.id.tvC);
        this.etAnoOrden = (EditText)findViewById(R.id.etAnoNumero);
        this.spIdentificaT = (Spinner)findViewById(R.id.spIdentificaT);
        this.spIdentificaT1 = (Spinner)findViewById(R.id.spIdentificaT1);
        this.etCondominio = (EditText)findViewById(R.id.etCondominio);
        this.btnmodificar = (Button)findViewById(R.id.btnModificar);
        this.etManzana = (EditText)findViewById(R.id.etManzana);
        this.etLote = (EditText)findViewById(R.id.etLote);
        this.tvEvidencia = (TextView)findViewById(R.id.evidencia);
        this.etReferencia = (EditText)findViewById(R.id.etReferencia);
        this.etBuscar = (EditText)findViewById(R.id.etBuscar);
        btnB = (Button)findViewById(R.id.btnBuscar);
        rlProp = (RelativeLayout)findViewById(R.id.rlPropietar);
        rlTestA = (RelativeLayout)findViewById(R.id.rlTestAsist);
        rlVisita = (RelativeLayout)findViewById(R.id.rlVisita);
        llNota = (LinearLayout)findViewById(R.id.rlNota);
        llplazo = (LinearLayout)findViewById(R.id.llPlazo);
        llreincidencia = (LinearLayout)findViewById(R.id.llreincidencia);
        rgReincidencia = (RadioGroup)findViewById(R.id.rgreincidencia);

        etfolio = (EditText)findViewById(R.id.etFolio);
        cbFlag = (CheckBox)findViewById(R.id.cbFlag);
        cbFlag = (CheckBox)findViewById(R.id.cbFlag);
        //etAlineamiento = (EditText)findViewById(R.id.etalineamiento);
        etConstruccion = (EditText)findViewById(R.id.etcontruccion);
        rlLicencias = (RelativeLayout)findViewById(R.id.rllicencias);
        spMedida = (Spinner)findViewById(R.id.spMedida);
        btnVista = (Button)findViewById(R.id.btnVista);
        spInspectorT = (Spinner)findViewById(R.id.spInspectorT);
        spInspectorT1 = (Spinner)findViewById(R.id.spInspectorT1);
        this.btnFtp = (Button)findViewById(R.id.btnEnviaFTP);
       
        this.rbaper = (Button)findViewById(R.id.radioApercibimiento);
        this.rbcitatorio = (Button)findViewById(R.id.radioCitatorio);
        this.rborden = (Button)findViewById(R.id.radioOrdenV);
        this.rbHechos = (Button)findViewById(R.id.radioHechos);
        
        etGiro = (EditText)findViewById(R.id.etGiro);
        etMotivo = (EditText)findViewById(R.id.etMotivo);
        this.etOrden1 = (EditText)findViewById(R.id.etOrden1);
        etfechaOV=(EditText)findViewById(R.id.etfechaOV);
        tvfechaOv=(TextView) findViewById(R.id.tvfechaOV);

        this.btnOrden1 = (Button)findViewById(R.id.btnBorden);
        spReglamento = (Spinner)findViewById(R.id.spReglamento);
        tvReg = (TextView)findViewById(R.id.tvReg);
        etEntreC = (EditText)findViewById(R.id.etEntreC);
        etEntreC1 = (EditText)findViewById(R.id.etEntreC1);
        etResponsable = (EditText)findViewById(R.id.etResponsable);
        etRegistro = (EditText)findViewById(R.id.etRegistro);
        tvActa = (TextView)findViewById(R.id.tvActa);
        etMedida = (EditText)findViewById(R.id.etMedida);
        etMedida1 = (EditText)findViewById(R.id.etMedida1);
        etMedida2 = (EditText)findViewById(R.id.etMedida2);
        etMedida3 = (EditText)findViewById(R.id.etMedida3);
        etMedida4 = (EditText)findViewById(R.id.etMedida4);
        etArticulo = (EditText)findViewById(R.id.etArticulos);
        cbFirma = (CheckBox)findViewById(R.id.cbFirma);
        etInspccionFue = (EditText)findViewById(R.id.etInpeccionFue);
        radioInfraccion = (Button)findViewById(R.id.radioInfraccion);
        radioEvento =(Button)findViewById(R.id.radioEventoEspecial);
        radioReimprimir=(Button)findViewById(R.id.radioReimprimir);
        
        tvCondominio = (TextView)findViewById(R.id.tvCondominio);
        
        tvNombreComercial = (TextView)findViewById(R.id.tvNombreComercial);
        
        
        etSector = (EditText)findViewById(R.id.etSector);
        etNombreComercial = (EditText)findViewById(R.id.etNombreComercia);
        
        btnver1 = (Button)findViewById(R.id.btnVer1);
        btnver2 = (Button)findViewById(R.id.btnVer2);
        btnver3 = (Button)findViewById(R.id.btnVer3);
        btnver4 = (Button)findViewById(R.id.btnVer4);
        
        
        btnver5 = (Button)findViewById(R.id.btnVer5);
        btnver6 = (Button)findViewById(R.id.btnVer6);
        btnver7 = (Button)findViewById(R.id.btnVer7);
        btnver8 = (Button)findViewById(R.id.btnVer8);
        btnver9 = (Button)findViewById(R.id.btnVer9);
        btnver10 = (Button)findViewById(R.id.btnVer10);
        btnver11 = (Button)findViewById(R.id.btnVer11);
        btnver12 = (Button)findViewById(R.id.btnVer12);
        btnver13 = (Button)findViewById(R.id.btnVer13);
        btnver14 = (Button)findViewById(R.id.btnVer14);
        btnver15 = (Button)findViewById(R.id.btnVer15);
        //btnver16 = (Button)findViewById(R.id.btnVer16);
        
        spPeticion = (Spinner)findViewById(R.id.spPeticion);
        
        this.tvAcomp = (TextView)findViewById(R.id.tvAcomp);
        
        tvMotivo = (TextView)findViewById(R.id.tvMotivo);
        
        llcomp = (LinearLayout)findViewById(R.id.llcomp);
        
        etLGiro = (EditText)findViewById(R.id.etLicGiro);
        etAGiro = (EditText)findViewById(R.id.etGiro1);
        etAlicencia = (EditText)findViewById(R.id.etALicencia);
        tvALicencia = (TextView)findViewById(R.id.tvALicencia);
        
        btnImprimirResum = (Button)findViewById(R.id.btnImprimirResum);

        spNE = findViewById(R.id.spNE);
        swReincidencia = findViewById(R.id.swReincidencia);

        etObs  = findViewById(R.id.etObs);
        etObs.setMovementMethod(new ScrollingMovementMethod());
        etObs1 = findViewById(R.id.etObs1);
        etObs1.setMovementMethod(new ScrollingMovementMethod());
        etObs2 = findViewById(R.id.etObs2);
        etObs2.setMovementMethod(new ScrollingMovementMethod());
        etObs3 = findViewById(R.id.etObs3);
        etObs3.setMovementMethod(new ScrollingMovementMethod());
        etObs4 = findViewById(R.id.etObs4);
        etObs4.setMovementMethod(new ScrollingMovementMethod());
        etObs5  = findViewById(R.id.etObs5);
        etObs5.setMovementMethod(new ScrollingMovementMethod());
        etObs6 = findViewById(R.id.etObs6);
        etObs6.setMovementMethod(new ScrollingMovementMethod());
        etObs7 = findViewById(R.id.etObs7);
        etObs7.setMovementMethod(new ScrollingMovementMethod());
        etObs8 = findViewById(R.id.etObs8);
        etObs8.setMovementMethod(new ScrollingMovementMethod());
        etObs9 = findViewById(R.id.etObs9);
        etObs9.setMovementMethod(new ScrollingMovementMethod());
        etObs10 = findViewById(R.id.etObs10);
        etObs10.setMovementMethod(new ScrollingMovementMethod());

        etObs11 = findViewById(R.id.etObs11);
        etObs11.setMovementMethod(new ScrollingMovementMethod());

        etObs12 = findViewById(R.id.etObs12);
        etObs12.setMovementMethod(new ScrollingMovementMethod());

        etObs13 = findViewById(R.id.etObs13);
        etObs13.setMovementMethod(new ScrollingMovementMethod());

        etObs14 = findViewById(R.id.etObs14);
        etObs14.setMovementMethod(new ScrollingMovementMethod());

        etObs15 = findViewById(R.id.etObs15);
        etObs15.setMovementMethod(new ScrollingMovementMethod());

        spUsoH = findViewById(R.id.spusoH);


        btnBCol = findViewById(R.id.btnBCol);
        etBCol = findViewById(R.id.etBCol);

        tvReferencia = findViewById(R.id.etReferencia);
        tvgiro = findViewById(R.id.tvgiro);
        tvNLicencia = findViewById(R.id.tvNLicencia);

        llconcepto = findViewById(R.id.llconcepto);

        etOtro = findViewById(R.id.etOtro);
        rgPopiedad = findViewById(R.id.rgPropiedad);
        rgTipo = findViewById(R.id.rgTipo);
        spuni = findViewById(R.id.spUni);
        spuni1 = findViewById(R.id.spUni1);
        spuni2 = findViewById(R.id.spUni2);
        spuni3 = findViewById(R.id.spUni3);
        spuni4 = findViewById(R.id.spUni4);
        spuni5 = findViewById(R.id.spUni5);
        spuni6 = findViewById(R.id.spUni6);
        spuni7 = findViewById(R.id.spUni7);
        spuni8 = findViewById(R.id.spUni8);
        spuni9 = findViewById(R.id.spUni9);
        spuni10 = findViewById(R.id.spUni10);

        spuni11 = findViewById(R.id.spUni11);
        spuni12 = findViewById(R.id.spUni12);
        spuni13 = findViewById(R.id.spUni13);
        spuni14 = findViewById(R.id.spUni14);
        spuni15= findViewById(R.id.spUni15);



        tvPeticion = findViewById(R.id.tvPeticion);
        tvNota = findViewById(R.id.tvNota);
        tvUso = findViewById(R.id.tvUso);
        llPla = findViewById(R.id.llPla);
        cbDatos = findViewById(R.id.cbDatos);
        cbDatos2=findViewById(R.id.cbDatos2);


        tvPropietario = findViewById(R.id.tvPropietario);
        rlDonde_actua = findViewById(R.id.rlDonde_actua);
        etDondeActua = findViewById(R.id.etDondeActua);
        llfundamento = findViewById(R.id.llfundamento);

        etNumeroSellos = findViewById(R.id.etNumeroSellos);

        spMeConstitui = findViewById(R.id.spMeConstitui);
        tvMC = findViewById(R.id.tvMeConstituye);
        tvPropiedad = findViewById(R.id.tvPropiedad);

        spselec1 = findViewById(R.id.spselec1);
        tvDonde = findViewById(R.id.tvDondeActua);

        spDensidad = findViewById(R.id.spDensidad);

        pb = findViewById(R.id.pb);
        spCreglamentos=findViewById(R.id.spCreglamento);

        etDecomiso=findViewById(R.id.etDecomiso);
        tvBuscar=findViewById(R.id.tvBuscar);
        btnBusArt = findViewById(R.id.btnBusArt);
        tilArticulo = findViewById(R.id.tilArticulo);
        etArti = findViewById(R.id.etArticulo);

        unis = new ArrayList<>();
        unis1 = new ArrayList<>();
        unis2 = new ArrayList<>();
        unis3 = new ArrayList<>();
        unis4 = new ArrayList<>();
        unis5 = new ArrayList<>();
        unis6 = new ArrayList<>();
        unis7 = new ArrayList<>();
        unis8 = new ArrayList<>();
        unis9 = new ArrayList<>();
        unis10 = new ArrayList<>();
        unis11 = new ArrayList<>();
        unis12 = new ArrayList<>();
        unis13 = new ArrayList<>();
        unis14 = new ArrayList<>();
        unis15 = new ArrayList<>();

         adapterUni = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis);
        adapterUni1 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis1);
        adapterUni2 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis2);
        adapterUni3 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis3);
        adapterUni4 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis4);

        adapterUni5 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis5);
        adapterUni6 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis6);
        adapterUni7 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis7);
        adapterUni8 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis8);
        adapterUni9 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis9);
        adapterUni10 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis10);

        adapterUni11 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis11);
        adapterUni12 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis12);
        adapterUni13 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis13);
        adapterUni14 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis14);
        adapterUni15 = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,unis15);


        adapterMeC = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,meConstitui);
        adapterGiro=new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,giros);
        adapterDensidad = new ArrayAdapter(this,R.layout.multiline_spinner_dropdown_item,getResources().getStringArray(R.array.densidad));
        spMeConstitui.setAdapter(adapterMeC);
        spDensidad.setAdapter(adapterDensidad);
        spgiro.setAdapter(adapterGiro);
        spuni.setAdapter(adapterUni);
        spuni1.setAdapter(adapterUni1);
        spuni2.setAdapter(adapterUni2);
        spuni3.setAdapter(adapterUni3);
        spuni4.setAdapter(adapterUni4);

        spuni5.setAdapter(adapterUni5);
        spuni6.setAdapter(adapterUni6);
        spuni7.setAdapter(adapterUni7);
        spuni8.setAdapter(adapterUni8);
        spuni9.setAdapter(adapterUni9);

        spuni10.setAdapter(adapterUni10);
        spuni11.setAdapter(adapterUni11);
        spuni12.setAdapter(adapterUni12);
        spuni13.setAdapter(adapterUni13);
        spuni14.setAdapter(adapterUni14);
        spuni15.setAdapter(adapterUni15);

        tvuni.setTypeface(helvetica);
        tvuni1.setTypeface(helvetica);
        tvuni2.setTypeface(helvetica);
        tvuni3.setTypeface(helvetica);
        tvuni4.setTypeface(helvetica);

        tvuni5.setTypeface(helvetica);
        tvuni6.setTypeface(helvetica);
        tvuni7.setTypeface(helvetica);
        tvuni8.setTypeface(helvetica);
        tvuni9.setTypeface(helvetica);

        tvuni10.setTypeface(helvetica);
        tvuni11.setTypeface(helvetica);
        tvuni12.setTypeface(helvetica);
        tvuni13.setTypeface(helvetica);
        tvuni14.setTypeface(helvetica);
        tvuni15.setTypeface(helvetica);


        tvTitle.setTypeface(helvetica);
        tvTipo.setTypeface(helvetica);
        tvEspe.setTypeface(helvetica);
        tvOV.setTypeface(helvetica);
        tvC.setTypeface(helvetica);
        tvEvidencia.setTypeface(helvetica);
        tvReg.setTypeface(helvetica);
        tvActa.setTypeface(helvetica);
        tvMotivo.setTypeface(helvetica);
        tvAcomp.setTypeface(helvetica);
        tvCondominio.setTypeface(helvetica);
        tvNombreComercial.setTypeface(helvetica);
        tvALicencia.setTypeface(helvetica);


        btnFecha.setTypeface(helvetica);
        btnInicio.setTypeface(helvetica);
        btnaceptar.setTypeface(helvetica);
        btnTomarF.setTypeface(helvetica);
        btnGuardar.setTypeface(helvetica);
        btnImprimir.setTypeface(helvetica);
        btnConsultar.setTypeface(helvetica);
        btnSi.setTypeface(helvetica);
        btnNo.setTypeface(helvetica);
        btnVisualizar.setTypeface(helvetica);
        btnMostrar.setTypeface(helvetica);
        btnSalir.setTypeface(helvetica);
        tveliminar.setTypeface(helvetica);
        tveliminar1.setTypeface(helvetica);
        tveliminar2.setTypeface(helvetica);
        tveliminar3.setTypeface(helvetica);
        tveliminar4.setTypeface(helvetica);

        tveliminar5.setTypeface(helvetica);
        tveliminar6.setTypeface(helvetica);
        tveliminar7.setTypeface(helvetica);
        tveliminar8.setTypeface(helvetica);
        tveliminar9.setTypeface(helvetica);
        tveliminar10.setTypeface(helvetica);

        tveliminar11.setTypeface(helvetica);
        tveliminar12.setTypeface(helvetica);
        tveliminar13.setTypeface(helvetica);
        tveliminar14.setTypeface(helvetica);
        tveliminar15.setTypeface(helvetica);

        btnmodificar.setTypeface(helvetica);
        btnFtp.setTypeface(helvetica);
        btnB.setTypeface(helvetica);
        btnOrden1.setTypeface(helvetica);
        btnVista.setTypeface(helvetica);
        btnver1.setTypeface(helvetica);
        btnver2.setTypeface(helvetica);
        btnver3.setTypeface(helvetica);
        btnver4.setTypeface(helvetica);
        btnver5.setTypeface(helvetica);
        btnver6.setTypeface(helvetica);
        btnver7.setTypeface(helvetica);
        btnver8.setTypeface(helvetica);
        btnver9.setTypeface(helvetica);
        btnver10.setTypeface(helvetica);
        btnver11.setTypeface(helvetica);
        btnver12.setTypeface(helvetica);
        btnver13.setTypeface(helvetica);
        btnver14.setTypeface(helvetica);
        btnver15.setTypeface(helvetica);
        tvPeticion.setTypeface(helvetica);
        tvNota.setTypeface(helvetica);
        tvUso.setTypeface(helvetica);
        //btnver16.setTypeface(helvetica);
        
        if(id == 2 | id == 5) {
        	etAlicencia.setVisibility(View.GONE);
        	tvALicencia.setVisibility(View.GONE);
        }
        
        
        //this.rbaper = (Button)findViewById(R.id.radioApercibimiento);
        //this.rbcitatorio = (Button)findViewById(R.id.radioCitatorio);
        //this.rborden = (Button)findViewById(R.id.radioOrdenV);
        
        spNombreA1.setOnItemSelectedListener(this);
        spNombreA2.setOnItemSelectedListener(this);
        spNombreA3.setOnItemSelectedListener(this);
        spNombreA4.setOnItemSelectedListener(this);
        spReglamento.setOnItemSelectedListener(this);
        spMedida.setOnItemSelectedListener(this);
        
        spInspectorT.setOnItemSelectedListener(this);
        spInspectorT1.setOnItemSelectedListener(this);
        spdesignado1.setOnItemSelectedListener(this);
        
        btnver1.setOnClickListener(this);
        btnver2.setOnClickListener(this);
        btnver3.setOnClickListener(this);
        btnver4.setOnClickListener(this);
        
        
        btnver5.setOnClickListener(this);
        btnver6.setOnClickListener(this);
        btnver7.setOnClickListener(this);
        btnver8.setOnClickListener(this);
        btnver9.setOnClickListener(this);
        btnver10.setOnClickListener(this);
        btnver11.setOnClickListener(this);
        btnver12.setOnClickListener(this);
        btnver13.setOnClickListener(this);
        btnver14.setOnClickListener(this);
        btnver15.setOnClickListener(this);
        //btnver16.setOnClickListener(this);
        btnImprimirResum.setOnClickListener(this);
        btnBCol.setOnClickListener(this);
        spUsoH.setOnItemSelectedListener(this);

        spZona.setOnItemSelectedListener(this);
        rgTipo.setOnCheckedChangeListener(this);
        rgPopiedad.setOnCheckedChangeListener(this);
        btnBusArt.setOnClickListener(this);
        mostrarReglamentos();
        
        if(id == 1) {
        	rlLicencias.setVisibility(View.VISIBLE);
        	this.rbaper.setVisibility(View.GONE);
            this.rbcitatorio.setVisibility(View.GONE);
            this.rborden.setVisibility(View.VISIBLE);
            this.rbHechos.setVisibility(View.GONE);
    	} else if(id == 3) {
            rlLicencias.setVisibility(View.GONE);
        }
        else {
        	rlLicencias.setVisibility(View.VISIBLE);
        	/*this.rbaper.setVisibility(View.VISIBLE);
            this.rbcitatorio.setVisibility(View.VISIBLE);*/
            this.rborden.setVisibility(View.VISIBLE);
            //this.rbHechos.setVisibility(View.VISIBLE);
        }

        if(id == 2 | id == 5) {
            rlLicencias.setVisibility(View.GONE);
            llcomp.setVisibility(View.GONE);
        }


        if(id == 3) {
            llcomp.setVisibility(View.GONE);
            tvNota.setText("Tipo de Establecimiento");
            tvUso.setText("Seleccione");
            llPla.setVisibility(View.GONE);;
        }
        if(id==4){
            //tvfolioap.setVisibility(View.VISIBLE);
            //etfolioap.setVisibility(View.VISIBLE);
            //tvfechap.setVisibility(View.VISIBLE);
            //etfechap.setVisibility(View.VISIBLE);
            tvReferencia.setVisibility(View.GONE);
            etfoliopeticion.setVisibility(View.VISIBLE);
        }else{
            etfoliopeticion.setVisibility(View.VISIBLE);
        }
        
        if(getIntent().getExtras().getString("na") != null) {
        	na = getIntent().getExtras().getString("na");
        	il = getIntent().getExtras().getInt("il");
        	btnInicio.setVisibility(View.GONE);
        	rborden.setVisibility(View.GONE);
        	//rbinfra.setChecked(true);
        	infrac = 1;
			etDiaPlazo.setText("20");
			etDiaPlazo.setEnabled(false);
			rlProp.setVisibility(View.VISIBLE);
			rlTestA.setVisibility(View.VISIBLE);
			rlVisita.setVisibility(View.VISIBLE);
			llNota.setVisibility(View.VISIBLE);
			llplazo.setVisibility(View.VISIBLE);
			llreincidencia.setVisibility(View.GONE);
			ante = "IN";
			formato = "infraccion";
			etGiro.setVisibility(View.GONE);
			etMotivo.setVisibility(View.GONE);
			spNombreA1.setVisibility(View.GONE);
			spNombreA2.setVisibility(View.GONE);
			etIfeA1.setVisibility(View.GONE);
			etIfeA2.setVisibility(View.GONE);
			etNoA2.setVisibility(View.GONE);
			etNoA3.setVisibility(View.GONE);
			etVigA1.setVisibility(View.GONE);
			etVigA2.setVisibility(View.GONE);

            if(id==4){
                //tvfolioap.setVisibility(View.VISIBLE);
                //etfolioap.setVisibility(View.VISIBLE);
                //tvfechap.setVisibility(View.VISIBLE);
                //etfechap.setVisibility(View.VISIBLE);
                tvReferencia.setVisibility(View.GONE);
                etfoliopeticion.setVisibility(View.VISIBLE);
            }

			/*tva1.setVisibility(View.GONE);
			tva2.setVisibility(View.GONE);*/
        	if (!inicio) {
				consu = false;
				Calendar calendar = Calendar.getInstance();
				String h,m;
				h = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
				m = (calendar.get(Calendar.MINUTE) < 10) ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));
				hora = h + ":" + m;
				int day = calendar.get(Calendar.MONTH);
				day += 1;
				fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + day + "/" + calendar.get(Calendar.YEAR);
				Log.i("Fecha/Hora", fecha + "/" + hora);
				lldiv.setVisibility(View.VISIBLE);
				btnInicio.setVisibility(View.GONE);
				btnConsultar.setVisibility(View.GONE);
				etEspecificacion.setVisibility(View.GONE);
				citatorio = false;
				spconsultar.setVisibility(View.GONE);
				mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					thread = new Thread(InfraccionesActivity.this);
					thread.start();
				} else {
					builAlert();
				}
				String [] fechas = fecha.split("/");
				int dia1, mes,a;
				String me;
				dia1 = Integer.parseInt(fechas[0]);
				mes = Integer.parseInt(fechas[1]);
				a = Integer.parseInt(fechas[2].substring(2, 4));
				me = Justificar.mes(mes);
				Log.i("fecha", dia + " " + me + "  " + a);
				//thread.start();
				if(id == 2)
		        	etOrden1.setText("OV" + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/");
			}
			else {
				InfraccionesActivity.this.finish();
				consu = false;
				Intent intent = new Intent(InfraccionesActivity.this, InfraccionesActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("direccion", title);
				bundle.putString("usuario", us);
				bundle.putInt("id", id);
				intent.putExtras(bundle);
				startActivity(intent);
				if(id == 2)
		        	etOrden1.setText("OV" + "/" + InfraccionesActivity.this.id + "/");
				
			}
        }
        
        System.err.println(na + " na " + il + " il");
        
        btnB.setOnClickListener(this);
        rborden.setOnClickListener(this);
        radioInfraccion.setOnClickListener(this);
        radioEvento.setOnClickListener(this);
        radioReimprimir.setOnClickListener(this);
        cbFlag.setOnCheckedChangeListener(this);
        cbDatos.setOnCheckedChangeListener(this);
        cbDatos2.setOnCheckedChangeListener(this);
        this.btnOrden1.setOnClickListener(this);
        btnVista.setOnClickListener(this);

        spuso.setOnItemSelectedListener(this);
        
        this.btnFtp = (Button)findViewById(R.id.btnEnviaFTP);
        
        btnFtp.setVisibility(View.GONE);
        
        this.tvEvidencia.setVisibility(View.GONE);
        this.btnTomarF.setVisibility(View.VISIBLE);
        
        //spReglamento.setVisibility(View.GONE);
		//tvReg.setVisibility(View.GONE);
        
        this.btnImprimir.setEnabled(false);
        this.btnImprimirResum.setEnabled(false);
        
        this.tvTitle.setText(direccion);
        
        Calendar calendar = Calendar.getInstance();
        String ano = String.valueOf(calendar.get(Calendar.YEAR));
        ano = ano.substring(2,4);
        
        etAnoCitatorio.setEnabled(false);
        etAnoOrden.setEnabled(false);
        
        etAnoCitatorio.setText(ano);
        etAnoOrden.setText(ano);
        
        etLongitud.setEnabled(false);
        etLatitud.setEnabled(false);
        swReincidencia.setChecked(false);
        
        this.btnSalir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				salir();
			}
		});
        
        this.spdesignado.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(spdesignado.getItemAtPosition(arg2).toString().equalsIgnoreCase("inspector")) {
					//spInspectorT.setSelection(0);
                    //selectValue(spdesignado,"Credencial oficial folio");
					spInspectorT.setEnabled(true);
					/*etIfeA.setText(ifeA);
					etNoA.setText(noA);
					etVigA.setText(vigA);
					etNombreT.setText(spNombreA.getSelectedItem().toString());
					etIfeT.setText(ifeA);*/
				}
				else {
					spInspectorT.setSelection(0);
					spInspectorT.setEnabled(false);
					etIfeT.setText("");
					/*etIfeA.setText("");
					etNoA.setText("");
					etVigA.setText("");
					etNombreT.setText("");
					etIfeT.setText("");*/
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        this.btnMostrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				etCitatorio.setText("12");
				etNum.setText("69");
				etNombreV.setText("Alberto Aguirre GarcÔøΩa");
				etFraccionamiento.setText("Rinconada Del Sol");
				etCalle.setText("LÔøΩpez M.");
				etNumero.setText("9346");
				etNuemroInterior.setText("A");
				etApellidoP.setText("Cortes");
				etApellidoM.setText("Silva");
				etPropietario.setText("Alberto Aguirre GarcÔøΩa");
				etNombreT1.setText("Juan Saavedra Barajas");
				etIfeT2.setText("Juan-Ceiwn-8436");
				//etDensidad.setText("Media");
				etManifiesta.setText("No");
			}
		});
        
        //btnImprimir.setEnabled(false);
        
        tvTipo.setText(tvTipo.getText().toString() + " " + tipoActa);
        this.etNumeroActa.setEnabled(false);

        conceptos = new ArrayList<>();
        articulo = new ArrayList<>();
        fraccion = new ArrayList<>();
        
        listar();
        listarInf();
        /*buscarCodigo();
        buscarLap();
        buscarOrdenamiento();
        buscarZonificacion();
        buscarOrdenamientoEco();
        buscarNAE();
        buscarLEEPA();*/
        listarZona(this.id);
        manifiesta();
        identifica();
        usoSuelo();
        poblacion();
        C_giro();

        buscarNombreCampo();
        buscarOrdenamientos();
        competencia();
        getMeConstitui();
        conceptosOV();

        etDiaPlazo.setText("20");
		etDiaPlazo.setEnabled(false);
		etNombreT.setText("HO");
        etIfeT.setText("LA");
        
        Log.i("ordenamientos", "o1 " + campo1 + " o2 " + campo2 + " o3 " + campo3 + " o4 " + campo4 + " o5 " + campo5 + "o6 " + campo6 + " o7 " + campo7 + " o8 " + campo8 + " o9 " + campo9 + " o0 " + campo0 + campo11 + " o11 " + campo12 + " o12 " + campo12 + " o13 " + campo13 + " o14 " + campo14 + "015" + campo15 + "o16 " + campo16 + " o17 " + campo17 + " o18 " + campo18 + " o19 " + campo19 + " o20 " + campo20);
		
        if(!zona.isEmpty()){
        	spZona.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, zona));
        }
        
        spnombre.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arreglo));
        spnombre.setEnabled(false);

        adapterCol = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, fraccionamiento);

        //if(!fraccionamiento.isEmpty())
        spFraccionamiento.setAdapter(adapterCol);
        fraccionamiento();



        
        if(!reglamento.isEmpty())
        	spReglamento.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,reglamento));
        
        if(!poblacion.isEmpty()) 
        	spPoblacion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,poblacion));
        
        arregloLista1 = arregloLista;
        arregloLista2 = arregloLista;
        if(!arregloLista.isEmpty()){
        	spNombreA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
        }
        
        if(!arregloLista1.isEmpty()){
        	spInspectorT.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista1));
        }
        
        if(!arregloLista2.isEmpty()){
        	spInspectorT1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista2));
        }
        
        if(!arregloLista.isEmpty()){
        	spNombreA1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
        }
        if(!arregloLista.isEmpty()){
        	spNombreA2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
        }
        if(!arregloLista.isEmpty()){
        	spNombreA3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
        }
        if(!arregloLista.isEmpty()){
        	spNombreA4.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
        }
        if(!arregloInfraccion1.isEmpty()){
            spInfraccion.setAdapter(new ArrayAdapter<String>(this,R.layout.multiline_spinner_dropdown_item,arregloInfraccion1));
        }
        if(!arregloCreglamentos.isEmpty()){
            spCreglamentos.setAdapter(new ArrayAdapter<String>(this,R.layout.multiline_spinner_dropdown_item,arregloCreglamentos));
        }
        
        spManifiesta.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				if (!spManifiesta.getItemAtPosition(position).toString().equals("")) {
				    if(spManifiesta.getItemAtPosition(position).toString().equals("Otro")){
                        etVManifiesta.setText("No manifiesta");
                    }else{
                        etVManifiesta.setText(spManifiesta.getItemAtPosition(position).toString());
                    }

				}
				else {
					etVManifiesta.setText("");
				}


			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});

        spgiro.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!spgiro.getItemAtPosition(position).toString().equals("")){
                    etGiro.setText(spgiro.getItemAtPosition(position).toString());
                }else{
                    etGiro.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        
        spPoblacion.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,int position, long id) {
				if(!spPoblacion.getItemAtPosition(position).toString().equals("")) 
					etFraccionamiento.setText(spPoblacion.getItemAtPosition(position).toString());
				else
					etFraccionamiento.setText("");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				
			}
		});
        
        spFraccionamiento.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,int position, long id) {
				if (!spFraccionamiento.getItemAtPosition(position).toString().equals("Seleccionar")) {
					etFraccionamiento.setText(spFraccionamiento.getItemAtPosition(position).toString());
					zon = zonas.get(position);
					etFraccionamiento.setEnabled(false);
				}
				else {
					etFraccionamiento.setText("");
                    etFraccionamiento.setEnabled(true);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        btnVisualizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!validarCampos(etDiaPlazo)) {
					diaPlazo = Integer.parseInt(etDiaPlazo.getText().toString());
					Calendar c = calcular("", diaPlazo);
					a = c.get(Calendar.YEAR);
					m = c.get(Calendar.MONTH);
					di = c.get(Calendar.DAY_OF_MONTH);
					int nim = c.get(Calendar.MONTH) + 1;
					Log.i("Calendario", "Dia: " + c.get(Calendar.DAY_OF_MONTH) + " Mes: " + nim + " AÃ±o: " + c.get(Calendar.YEAR));
					updateDisplay1();
				}
				else {
					Toast toast = Toast.makeText(getApplication(), "INGRESE EL NUMERO DE DIAS DEL PLAZO", Toast.LENGTH_SHORT);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
			}
		});
        
        spIdentifica.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				if (!spIdentifica.getItemAtPosition(position).toString().equals("")) {


                            if(spIdentifica.getItemAtPosition(position).toString().equals("Media filiacion")){
                                etVIdentifica.setHint("Indique descripcion del visitado ");
                            }else{
                                etVIdentifica.setHint("Indique el número de identificación");
                            }



					//etVIdentifica.setText(spIdentifica.getItemAtPosition(position).toString() + ": ");
				}
				else {
					etVIdentifica.setText("");
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        spIdentificaT.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				if (!spIdentificaT.getItemAtPosition(position).toString().equals("")) {
					if(!spIdentificaT.getItemAtPosition(position).toString().equalsIgnoreCase("Inspector")) {
						etIfeT.setText(idT1);
						etIfeT.setEnabled(true);
					}
					else {
						spIdentificaT.setEnabled(false);
						etIfeT.setEnabled(true);
					}
				}
				else {
					etIfeT.setText("");
					etIfeT.setEnabled(true);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        spIdentificaT1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				if (!spIdentificaT1.getItemAtPosition(position).toString().equals("")) {
					etIfeT2.setText(idT1);
				}
				else {
					etIfeT2.setText("");
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});

        class actualizarInspector extends AsyncTask<String,Integer,Boolean>{

            @Override
            protected Boolean doInBackground(String... strings) {
                Context main=getApplicationContext();
                conect=new Connection(main);
                Descarga.actualiza2(conect,main);
                return null;

            }

        }
        spnombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                Object sel = spnombre.getItemAtPosition(position);
                //buscarInspector(sel.toString());

                buscarIdInspector(sel.toString());

                etIfeI.setText(folio);
                etNoI.setText(noI);
                etVigI.setText(vigI);
                id_inspector1 = id_i1.get(position);
                id_inspectorQ=id_inspector1;
                //id_inspectorQ = id_inspector1;


                int n=0;
                //final Descarga d= new Descarga();

                if(infrac==1 ) {
                    int folio=0;
                    int max=0;
                    int min=0;
                    int next_max=0;
                    int next_min=0;
                    Log.i("id inspector", id_inspector1 + "");

                    GestionBD gestion = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
                    SQLiteDatabase db = gestion.getReadableDatabase();


                        Cursor c2 = db.rawQuery("SELECT  f_max FROM C_inspector where id_c_inspector= '" + id_inspector1 + "'  LIMIT 1", null);
                        if (db != null) {
                            if (c2.moveToFirst()) {
                                do {
                                    for (int i = 0; i < c2.getColumnCount(); i++) {
                                        System.err.println(c2.getColumnName(i) + " " + c2.getString(i));
                                        if(c2.getString(i)!=null){
                                            if(c2.getString(i).equals("") ||c2.getString(i).isEmpty() || c2.getString(i)=="" ) {

                                                max = 0;
                                            }else{
                                                max = Integer.parseInt(c2.getString(i));
                                            }
                                        }else {
                                            max = 0;
                                        }
                                    }
                                } while (c2.moveToNext());
                            }
                        }
                        Cursor c3 = db.rawQuery("SELECT  f_min FROM C_inspector where id_c_inspector= '" + id_inspector1 + "'  LIMIT 1", null);
                        if (db != null) {
                            if (c3.moveToFirst()) {
                                do {
                                    for (int i = 0; i < c3.getColumnCount(); i++) {
                                        System.err.println(c3.getColumnName(i) + " " + c3.getString(i));
                                        if(c3.getString(i)!=null){
                                            if(c3.getString(i).equals("") ||c3.getString(i).isEmpty() || c3.getString(i)=="" ) {

                                                min=0;
                                            }else{
                                                min = Integer.parseInt(c3.getString(i));
                                            }
                                        } else {
                                            min = 0;
                                        }
                                    }
                                } while (c3.moveToNext());
                            }
                        }
                        Cursor c4 = db.rawQuery("SELECT  next_max FROM C_inspector where id_c_inspector= '" + id_inspector1 + "'  LIMIT 1", null);
                        if (db != null) {
                            if (c4.moveToFirst()) {
                                do {
                                    for (int i = 0; i < c4.getColumnCount(); i++) {
                                        System.err.println(c4.getColumnName(i) + " " + c4.getString(i));
                                        if(c4.getString(i)!=null){
                                            if(c4.getString(i).equals("") || c4.getCount()< 0 ){
                                                next_max=0;
                                            }else{
                                                next_max = Integer.parseInt(c4.getString(i));
                                            }
                                        } else {
                                            next_max =0;
                                        }

                                    }
                                } while (c4.moveToNext());
                            }
                        }
                        Cursor c5 = db.rawQuery("SELECT  next_min FROM C_inspector where id_c_inspector= '" + id_inspector1 + "'  LIMIT 1", null);
                        if (db != null) {
                            if (c5.moveToFirst()) {
                                do {
                                    for (int i = 0; i < c5.getColumnCount(); i++) {
                                        System.err.println(c5.getColumnName(i) + " " + c5.getString(i));
                                        if(c5.getString(i)!=null){
                                            if(c5.getString(i).equals("")|| c5.getCount()< 0 ){
                                                next_min=0;
                                            }else{
                                                next_min = Integer.parseInt(c5.getString(i));
                                            }
                                        } else {
                                            next_min = 0;
                                        }

                                    }
                                } while (c5.moveToNext());
                            }
                        }

                        Cursor c = db.rawQuery("SELECT  max(cast(numero_acta as int)) FROM levantamiento where id_c_inspector1= '" + id_inspector1 + "' and infraccion=1 and cast(numero_acta as int)"+" BETWEEN "+min+" and "+max, null);
                    String column = "", dato = "";

                    try {
                        if (db != null) {
                            if (c.moveToFirst()) {
                                do {
                                    for (int i = 0; i < c.getColumnCount(); i++) {
                                        System.err.println(c.getColumnName(i) + " " + c.getString(i));

                                        if(c.getString(i)!=null){
                                            if(c.getString(i).equals("") ||c.getString(i).isEmpty() || c.getString(i)=="" ){
                                                folio=0;
                                            }else{
                                                folio = Integer.parseInt(c.getString(i));
                                            }
                                        } else {
                                            folio=0;
                                        }

                                    }
                                } while (c.moveToNext());
                            }
                        }





                        sp = getSharedPreferences("infracciones", Context.MODE_PRIVATE);
                        foliox = sp.getInt("folio",0);
                        validarM = sp.getInt("modo",0);
                        tableta=sp.getString("Tableta","");
                        Log.i(TAG, "tableta:"+tableta);
                        Log.i(TAG, "modo: "+validarM);
                        if(validarM==1) {
                            //modoT.setChecked(true);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("modo", 1);
                            editor.apply();
                            //titlem.setText("Modo de Tester: " + getResources().getString(R.string.version));
                            urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/infracciones_alfa/";
                        }else {
                            //modoT.setChecked(false);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("modo", 0);
                            editor.apply();
                            //titlem.setText("");
                            urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";
                        }


                        System.out.println(foliox + "---");

                         if(valW=="") {
                             if (foliox > 0 && foliox >= min && foliox <= max) {
                                 if (foliox > folio) {
                                     folio = foliox;
                                 } else {
                                     folio = folio + 1;
                                 }
                                 etNumeroActa.setText(String.valueOf(folio));

                             } else {
                                 if (folio >= min && folio <=max) {
                                     folio = folio + 1;

                                 }
                                 if (folio == 0 && next_max == 0 && next_min == 0) {
                                     folio = min;
                                 }
                                 if (folio < min) {
                                     folio = min;
                                 }

                                 etNumeroActa.setText(String.valueOf(folio));

                                 Log.v("folios ", folio + " " + max + " max");

                                 if (folio == 0 || folio > max || foliox > max) {

                                     MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(InfraccionesActivity.this)
                                             .setTitle("Requiere nuevo foliaje, reinicie con internet  en la tableta de origen")
                                             .setMessage(getResources().getString(R.string.continuar))
                                             .setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialog, int which) {


                                                     finish();
                                                 }
                                             });

                                     builder.create().show();
                                 }


                                 //etNumeroActa.setText("453");
                                 if (validarFoto(Integer.valueOf(etNumeroActa.getText().toString())) > 0) {
                                     btnImprimir.setEnabled(false);
                                     btnGuardar.setEnabled(false);
                                     btnVista.setEnabled(false);

                                    /*Toast toast = Toast.makeText(InfraccionesActivity.this, "EL NUMERO DE ACTA ASIGNADO YA SE ENCUENTRA EN EL DISPOSITIVO PORFAVOR DE SICRONIZAR DE NUEVO O DESCARGAR SUS DATOS RESPECTIVOS: "+etNumeroActa.getText().toString(), Toast.LENGTH_SHORT);
                                    toast.setGravity(0, 0, 15);
                                    toast.show();*/
                                     AlertDialog.Builder builder = new AlertDialog.Builder(InfraccionesActivity.this);
                                     builder.setTitle("Información");
                                     builder.setIcon(R.drawable.ic_baseline_check_circle_24);
                                     builder.setMessage("EL NUMERO DE ACTA  YA SE ENCUENTRA EN EL DISPOSITIVO PORFAVOR DE SICRONIZAR DE NUEVO O DESCARGAR SUS DATOS RESPECTIVOS: " + etNumeroActa.getText().toString());
                                     builder.setPositiveButton("Aceptar", null);

                                     AlertDialog dialog = builder.create();
                                     dialog.show();
                                 }


                             }
                         }





                    } catch (SQLiteException e) {
                        Log.e("SQLite", e.getMessage());
                    } finally {
                        db.close();
                        c.close();
                    }
                }else{
                    sp = getSharedPreferences("infracciones", Context.MODE_PRIVATE);
                    foliox = sp.getInt("folio",0);
                    validarM = sp.getInt("modo",0);
                    tableta=sp.getString("Tableta","");
                    Log.i(TAG, "tableta:"+tableta);
                    Log.i(TAG, "modo: "+validarM);

                    if(validarM==1) {
                        //modoT.setChecked(true);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("modo", 1);
                        editor.apply();
                        //titlem.setText("Modo de Tester: " + getResources().getString(R.string.version));
                        urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/infracciones_alfa/";
                    }else {
                        //modoT.setChecked(false);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("modo", 0);
                        editor.apply();
                        //titlem.setText("");
                        urlP="http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/";
                    }

                    if(!citatorio){
                        String [] na;
                        if(consultarActa() == 0){
                           // System.out.println(consultarActa()+"/GGGGG");
                            if(infrac!=1) {
                                //Log.i("consultar", "si");
                                numero = "01";
                                etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
                                //Log.i("acta", "entro aqui");
                                buscarNumeroActa();
                                if (!numero_acta.isEmpty()) {
                                    if (na()) {
                                        //Log.i("acta", "entro aqui2");
                                        //aqui consultar el ultimo y asignar
                                        na = ultimo().split("/");
                                        n = Integer.parseInt(na[6]) + 1;
                                        numero = String.valueOf(n);
                                        if (numero.length() == 1)
                                            numero = "0" + n;
                                        etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
                                    }
                                }
                            }
                        }
                        else{
                            //Log.i("acta", "entro aqui3");
                            //Log.i("consultar", "no");
                            asignarActa();
                            if(numero.equals("")){
                                numero="0";
                            }
                            n = Integer.valueOf(numero)+1;
                            Log.i("Numero1", numero);
                            if(n > 0 & n <= 9){
                                Log.i("acta", "entro aqui4");
                                numero = "0"+String.valueOf(n);
                                etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/"  + numero);
                              //  Log.i("numeros", "n " + etNumeroActa.getText().toString().substring(0, 16) + " v " + s.substring(0, 16));
                                if(s!=null) {
                                    if (s.equals("")) {
                                        s = "";
                                    } else {
                                        s = s.substring(0, 16);
                                    }
                                }

                                if(!etNumeroActa.getText().toString().substring(0, 16).equalsIgnoreCase(s)){
                                    Log.i("numero acta", "si");
                                    numero = "01";
                                    Log.i("acta", "entro aqui5");
                                    etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
                                    Log.i("nueva ", InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + infrac + "/" + numero);
                                }
                            }
                            else{
                                Log.i("acta", "entro aqui6");
                                numero = String.valueOf(n);
                                etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/"  + numero);
                            }

                            buscarNumeroActa();
                            if (!numero_acta.isEmpty()) {
                                if (na()) {
                                   // Log.i("acta", "entro aqui");
                                    //aqui consultar el ultimo y asignar
                                    na = ultimo().split("/");
                                    if(na.length>5)
                                        n = Integer.parseInt(na[6]) + 1;
                                    else
                                        n=50;
                                    //n = Integer.parseInt(na[6]) + 1;
                                    numero = String.valueOf(n);
                                    if (numero.length() == 1)
                                        numero = "0" + n;
                                    etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
                                }
                            }
                        }
                    }
                }



                valW="";


            }


            @Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
        	
		});
        btncopiar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String text = etNumeroActa.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text",  text);
                clipboard.setPrimaryClip(clip);
                Toast toast = Toast.makeText(InfraccionesActivity.this, "Copiado a portapapeles", Toast.LENGTH_SHORT);
                toast.setGravity(0, 0, 15);
                toast.show();
            }
        });

        this.spNombreA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				etIfeA.setEnabled(false);
				etNoA.setEnabled(false);
				etVigA.setEnabled(false);
				etNombreT.setEnabled(true);
				
				Object sel = spNombreA.getItemAtPosition(position);
				buscarAcompanante(sel.toString());
				etIfeA.setText(ifeA);
				etNoA.setText(noA);
				etVigA.setText(vigA);
				etNombreT.setText(sel.toString());
				etIfeT.setText(ifeA);
				if(position != 0){
					etIfeT.setEnabled(false);
					etIfeA.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA);
					etIfeT.setText(ifeA);
					int p = position-1;
					id_inspector2 = id_i2.get(p);
					System.out.println(id_inspector2);
				}
					
				else{
					if(resov) {
						etIfeT.setEnabled(false);
						etIfeA.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA);
						etIfeT.setText(ifeA);
						int p = position-1;
						id_inspector2 = id_i2.get(position);
						System.out.println(id_inspector2 + " <2");
					} else {
						etIfeA.setText("");
						etNoA.setText("");
						etVigA.setText("");
						etNombreT.setText("");
						etIfeT.setText("");
						/*etIfeA.setEnabled(true);
						etNoA.setEnabled(true);
						etVigA.setEnabled(true);*/
						etNombreT.setEnabled(true);
						//etIfeT.setEnabled(true);
					}
				}
				if(consu) {
					etIfeA.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA);
					etIfeT.setText(ifeA);
					etIfeA.setEnabled(false);
					etNoA.setEnabled(false);
					etVigA.setEnabled(false);
					etNombreT.setEnabled(false);
					etIfeT.setEnabled(false);
				}
				pos ++;
				
				Log.i("idinspector2", id_inspector2 + "");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        btnFecha.setVisibility(View.GONE);
        etfecha.setEnabled(false);
        
        etIfeA.setVisibility(View.GONE);
        etIfeA1.setVisibility(View.GONE);
        etIfeA2.setVisibility(View.GONE);
        etIfeA3.setVisibility(View.GONE);
        etIfeA4.setVisibility(View.GONE);
        
        etNoA.setVisibility(View.GONE);
        etNoA1.setVisibility(View.GONE);
        etNoA2.setVisibility(View.GONE);
        etNoA3.setVisibility(View.GONE);
        etNoA4.setVisibility(View.GONE);
        
        etVigA.setVisibility(View.GONE);
        etVigA1.setVisibility(View.GONE);
        etVigA2.setVisibility(View.GONE);
        etVigA3.setVisibility(View.GONE);
        etVigA4.setVisibility(View.GONE);
        
        this.btnConsultar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pos++;
				consu = true;
				cons.setVisibility(View.VISIBLE);
				inicio = true;
				btnConsultar.setVisibility(View.GONE);
				spconsultar.setVisibility(View.VISIBLE);
				lldiv.setVisibility(View.VISIBLE);
				boolean r = consultarLevantamiento();
				etEspecificacion.setVisibility(View.GONE);
				citatorio = true;
				btnSi.setEnabled(false);
				btnNo.setEnabled(false);
				tvEspe.setVisibility(View.GONE);
				btnTomarF.setEnabled(true);
				btnGuardar.setEnabled(false);
				btnVista.setEnabled(false);
				btnMostrar.setEnabled(false);
				btnFecha.setEnabled(false);
				spPoblacion.setEnabled(false);
				spFraccionamiento.setEnabled(false);
				etPropietario.setEnabled(false);
				etManifiesta.setEnabled(false);
				etVManifiesta.setEnabled(false);
				etVIdentifica.setEnabled(false);
				etCondominio.setEnabled(false);
				btnImprimir.setEnabled(true);
				etManzana.setEnabled(false);
				etReferencia.setEnabled(false);
				etNoA.setEnabled(false);
				etIfeA.setEnabled(false);
				etVigA.setEnabled(false);
				etNombreT.setEnabled(false);
				etLote.setEnabled(false);
				etOrden1.setEnabled(false);
				etConstruccion.setEnabled(false);
				etResponsable.setEnabled(false);
				etRegistro.setEnabled(false);
				etEntreC.setEnabled(false);
				etEntreC1.setEnabled(false);
				etMedida.setEnabled(false);
				etArticulo.setEnabled(false);
				if(id==5||id==2)
				btnImprimirResum.setEnabled(true);
				/*btnFtp.setEnabled(true);
				btnFtp.setVisibility(View.VISIBLE);*/
				InfraccionesActivity.this.btnTomarF.setVisibility(View.VISIBLE);
				if(!consultar.isEmpty()){
					spconsultar.setAdapter(new ArrayAdapter<String>(InfraccionesActivity.this, android.R.layout.simple_spinner_dropdown_item, consultar));
				}
				if (!r) {
					Toast toast = Toast.makeText(InfraccionesActivity.this, "No hay infracciones", Toast.LENGTH_SHORT);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
			}
		});
        
        spconsultar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				lldiv.setVisibility(View.GONE);
				if (!spconsultar.getItemAtPosition(position).equals("")) {
					lldiv.setVisibility(View.VISIBLE);
					consultarLevantamiento(spconsultar.getItemAtPosition(position).toString());
					//System.out.println("entro else consult");
					valW="y";
					//System.err.println(spconsultar.getSelectedItem().toString().substring(0, 2));
					//String ante = spconsultar.getSelectedItem().toString().substring(0, 2);
					
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        spInfraccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          int id2=id;
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                arrayincaseF.add((String) spInfraccion.getItemAtPosition(position));


			    if(con == 0){
					Log.i("Ento al if", (String)spInfraccion.getSelectedItem());
					con ++;
				}
				else{
				    if(position > 0) {
                        infraccion++;
                        btnaceptar.setVisibility(View.VISIBLE);
                        if(id2==2 || id2==5 || id2==4){
                            btnArticulos.setVisibility(View.VISIBLE);

                        }else{
                            btneliminarA.setVisibility(View.VISIBLE);
                        }

                        Log.i("Ento al else", (String) spInfraccion.getItemAtPosition(position));
                        int pos = 0;
                        for (int i = 0; i < arregloInfraccion.get(position).length(); i++) {
                        //for (int i = 0; i < spInfraccion.getItemAtPosition(position).toString().length(); i++) {
                            //if (!isNumeric(spInfraccion.getSelectedItem().toString().charAt(i) + "")) {
                            if (!isNumeric(arregloInfraccion.get(position).charAt(i) + "")) {
                                Log.i("char", spInfraccion.getSelectedItem().toString().charAt(i) + "");
                                pos = i;
                                break;
                            } else {
                                Log.i("char", arregloInfraccion.get(position).charAt(i) + "");
                            }
                        }
                        Log.i("pos", pos + "");
                        String t = arregloInfraccion.get(position).substring(pos);
                       buscarInfraccion(arregloInfraccion.get(position));
                        if (!desc) {
                           linea0.setVisibility(View.VISIBLE);
                            etDesc.setVisibility(View.VISIBLE);

                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                Log.i("uni bd", unidad);
                                String un[];
                                un = unidad.split(",");
                                Log.i("length", un.length + "");
                                unis.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].equalsIgnoreCase("")) {
                                        unis.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni.setVisibility(View.VISIBLE);
                                adapterUni.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato.setVisibility(View.VISIBLE);
                                etdato.setEnabled(true);
							/*tvuni.setVisibility(View.VISIBLE);
							tvuni.setText(unidad);
							tvuni.setFocusable(true);*/
                            }
                            des = t;
                            etDesc.setText((String) spInfraccion.getItemAtPosition(position));
                            tveliminar.setVisibility(View.VISIBLE);
                            etDesc.setText(t);
                            desc = true;
                            etObs.setVisibility(View.VISIBLE);
                            //Log.v("juanca estuvo aqui", "desc1 sp infraccion: "+spInfraccion.getItemAtPosition(position).toString().substring(0,4) );
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        } else if (!desc1) {
                            linea1.setVisibility(View.VISIBLE);

                            etDesc1.setVisibility(View.VISIBLE);

                            if ( !unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis1.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis1.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni1.setVisibility(View.VISIBLE);
                                adapterUni1.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato1.setVisibility(View.VISIBLE);
                                etdato1.setEnabled(true);
							/*tvuni1.setVisibility(View.VISIBLE);
							tvuni1.setText(unidad);
							tvuni1.setFocusable(true);*/
                            }
                            des1 = t;
                            tveliminar1.setVisibility(View.VISIBLE);
                            etDesc1.setText(t);
                            desc1 = true;
                            etObs1.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs1.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        } else if (!desc2) {
                            linea2.setVisibility(View.VISIBLE);
                            etDesc2.setVisibility(View.VISIBLE);

                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis2.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis2.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni2.setVisibility(View.VISIBLE);
                                adapterUni2.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato2.setVisibility(View.VISIBLE);
                                etdato2.setEnabled(true);
							/*tvuni2.setVisibility(View.VISIBLE);
							tvuni2.setText(unidad);
							tvuni2.setFocusable(true);*/
                            }
                            des2 = t;
                            tveliminar2.setVisibility(View.VISIBLE);
                            etDesc2.setText(t);
                            desc2 = true;
                            etObs2.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs2.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        } else if (!desc3) {

                            linea3.setVisibility(View.VISIBLE);
                            etDesc3.setVisibility(View.VISIBLE);

                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis3.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis3.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni3.setVisibility(View.VISIBLE);
                                adapterUni3.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato3.setVisibility(View.VISIBLE);
                                etdato3.setEnabled(true);
							/*tvuni3.setVisibility(View.VISIBLE);
							tvuni3.setText(unidad);
							tvuni3.setFocusable(true);*/
                            }
                            des3 = t;
                            tveliminar3.setVisibility(View.VISIBLE);
                            etDesc3.setText(des3);
                            desc3 = true;
                            etObs3.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs3.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        } else if(!desc4) {

                            linea4.setVisibility(View.VISIBLE);
                            etDesc4.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis4.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis4.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni4.setVisibility(View.VISIBLE);
                                adapterUni4.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato4.setVisibility(View.VISIBLE);
                                etdato4.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des4 = t;
                            tveliminar4.setVisibility(View.VISIBLE);
                            etDesc4.setText(t);
                            desc4 = true;
                            etObs4.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs4.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        else if(!desc5) {
                            linea5.setVisibility(View.VISIBLE);
                            etDesc5.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis5.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis5.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni5.setVisibility(View.VISIBLE);
                                adapterUni5.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato5.setVisibility(View.VISIBLE);
                                etdato5.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des5 = t;
                            tveliminar5.setVisibility(View.VISIBLE);
                            etDesc5.setText(t);
                            desc5 = true;
                            etObs5.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs5.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        else if(!desc6) {
                            linea6.setVisibility(View.VISIBLE);
                            etDesc6.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis6.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis6.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni6.setVisibility(View.VISIBLE);
                                adapterUni6.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato6.setVisibility(View.VISIBLE);
                                etdato6.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des6 = t;
                            tveliminar6.setVisibility(View.VISIBLE);
                            etDesc6.setText(t);
                            desc6 = true;
                            etObs6.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs6.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        else if(!desc7) {
                            linea7.setVisibility(View.VISIBLE);
                            etDesc7.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis7.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis7.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni7.setVisibility(View.VISIBLE);
                                adapterUni7.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato7.setVisibility(View.VISIBLE);
                                etdato7.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des7 = t;
                            tveliminar7.setVisibility(View.VISIBLE);
                            etDesc7.setText(t);
                            desc7 = true;
                            etObs7.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs7.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        else if(!desc8) {
                            linea8.setVisibility(View.VISIBLE);
                            etDesc8.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis8.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis8.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni8.setVisibility(View.VISIBLE);
                                adapterUni8.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato8.setVisibility(View.VISIBLE);
                                etdato8.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des8 = t;
                            tveliminar8.setVisibility(View.VISIBLE);
                            etDesc8.setText(t);
                            desc8 = true;
                            etObs8.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs8.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        else if(!desc9) {
                            linea9.setVisibility(View.VISIBLE);
                            etDesc9.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis9.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis9.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni9.setVisibility(View.VISIBLE);
                                adapterUni9.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato9.setVisibility(View.VISIBLE);
                                etdato9.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des9 = t;
                            tveliminar9.setVisibility(View.VISIBLE);
                            etDesc9.setText(t);
                            desc9 = true;
                            etObs9.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs9.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        else if(!desc10) {
                            linea10.setVisibility(View.VISIBLE);
                            etDesc10.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis10.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis10.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni10.setVisibility(View.VISIBLE);
                                adapterUni10.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato10.setVisibility(View.VISIBLE);
                                etdato10.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des10 = t;
                            tveliminar10.setVisibility(View.VISIBLE);
                            etDesc10.setText(t);
                            desc10 = true;
                            etObs10.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs10.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        else if(!desc11) {
                            linea11.setVisibility(View.VISIBLE);
                            etDesc11.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis11.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis11.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni11.setVisibility(View.VISIBLE);
                                adapterUni11.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato11.setVisibility(View.VISIBLE);
                                etdato11.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des11 = t;
                            tveliminar11.setVisibility(View.VISIBLE);
                            etDesc11.setText(t);
                            desc11 = true;
                            etObs11.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs11.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        else if(!desc12) {
                            linea12.setVisibility(View.VISIBLE);
                            etDesc12.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis12.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis12.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni12.setVisibility(View.VISIBLE);
                                adapterUni12.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato12.setVisibility(View.VISIBLE);
                                etdato12.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des12 = t;
                            tveliminar12.setVisibility(View.VISIBLE);
                            etDesc12.setText(t);
                            desc12 = true;
                            etObs12.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs12.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        else if(!desc13) {
                            linea13.setVisibility(View.VISIBLE);
                            etDesc13.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis13.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis13.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni13.setVisibility(View.VISIBLE);
                                adapterUni13.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato13.setVisibility(View.VISIBLE);
                                etdato13.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des13 = t;
                            tveliminar13.setVisibility(View.VISIBLE);
                            etDesc13.setText(t);
                            desc13 = true;
                            etObs13.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs13.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        else if(!desc14) {
                            linea14.setVisibility(View.VISIBLE);
                            linea4.setVisibility(View.VISIBLE);
                            etDesc14.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis14.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis14.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni14.setVisibility(View.VISIBLE);
                                adapterUni14.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato14.setVisibility(View.VISIBLE);
                                etdato14.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des14 = t;
                            tveliminar14.setVisibility(View.VISIBLE);
                            etDesc14.setText(t);
                            desc14 = true;
                            etObs14.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs14.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }

                        else if(!desc15) {
                            linea15.setVisibility(View.VISIBLE);
                            etDesc15.setVisibility(View.VISIBLE);
                            if (!unidad.trim().equalsIgnoreCase("") && !unidad.trim().equals("UNICO")) {
                                String un[];
                                un = unidad.split(",");
                                unis15.clear();
                                for (int i = 0; i < un.length; i++) {
                                    if (!un[i].isEmpty()) {
                                        unis15.add(un[i]);
                                        Log.i("unidades", un[i]);
                                    }
                                }
                                spuni15.setVisibility(View.VISIBLE);
                                adapterUni15.notifyDataSetChanged();
                                Toast toast = Toast.makeText(InfraccionesActivity.this, "Debe Ingrese la cantidad", Toast.LENGTH_LONG);
                                toast.setGravity(0, 0, 15);
                                toast.show();
                                etdato15.setVisibility(View.VISIBLE);
                                etdato15.setEnabled(true);
							/*tvuni4.setVisibility(View.VISIBLE);
							tvuni4.setText(unidad);
							tvuni4.setFocusable(true);*/
                            }
                            des15 = t;
                            tveliminar15.setVisibility(View.VISIBLE);
                            etDesc15.setText(t);
                            desc15 = true;
                            etObs15.setVisibility(View.VISIBLE);
                            if(id2==4) {
                                String numeroI=spInfraccion.getItemAtPosition(position).toString().substring(0, 4);
                                if (numeroI.equals("151 ")) {
                                    etObs15.setText("Folio: " + etfolioap.getText().toString() + " y Fecha:" + etfechap.getText().toString());

                                }
                            }
                            co++;
                        }
                        spInfraccion.setSelection(0);
                        actualizarTV(infraccion);
                    }
				}
				if (infraccion == 15)
					spInfraccion.setEnabled(false);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
        
        btnGuardar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//try {
					
					if (!validarCampos(etDiaPlazo)) {
						diaPlazo = Integer.parseInt(etDiaPlazo.getText().toString());
						Calendar c = calcular("", diaPlazo);
						a = c.get(Calendar.YEAR);
						m = c.get(Calendar.MONTH);
						di = c.get(Calendar.DAY_OF_MONTH);
						int nim = c.get(Calendar.MONTH) + 1;
						Log.i("Calendario", "Dia: " + c.get(Calendar.DAY_OF_MONTH) + " Mes: " + nim + " AÃ±o: " + c.get(Calendar.YEAR));
						updateDisplay1();
					}
					else {
						Toast toast = Toast.makeText(getApplication(), "INGRESE EL NUMERO DE DIAS DEL PLAZO", Toast.LENGTH_SHORT);
						toast.setGravity(0, 0, 15);
						toast.show();
					}
					
				
				
				
				
					/*if (validarCampos(etCitatorio)) {
						AlertDialog.Builder al = new AlertDialog.Builder(InfraccionesActivity.this)
						.setMessage("El campo Citatorio no se lleno. ÔøΩEs corecto?")
						.setPositiveButton("Si", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								guardar();
							}
						}).setNegativeButton("No", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
						});
						AlertDialog alert = al.create();
						alert.show();
					}
					else {*/
						//guardar();
                if(ante.contains("IN")){
                    //if(validarFoto(Integer.valueOf(etNumeroActa.getText().toString()))<=0){
                        if (!etInfraccion.getText().toString().equalsIgnoreCase("") | infrac == 2 | infrac == 3 | infrac == 4) {

                            new Descargas().execute();
                            if (!ante.contains("OV"))
                                new EFoto().execute();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "EL CAMPO INFRACCION ESTA VACIO", Toast.LENGTH_LONG);
                            toast.setGravity(0, 0, 15);
                            toast.show();
                        }
                   /* }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "ESE NUMERO DE ACTA YA EXISTE EN EL DISPOSITIVO: "+ etNumeroActa.getText().toString(), Toast.LENGTH_LONG);
                        toast.setGravity(0, 0, 15);
                        toast.show();
                    }*/
                }else {

                    if (validarI()) {

                        if (!etInfraccion.getText().toString().equalsIgnoreCase("") | infrac == 2 | infrac == 3 | infrac == 4) {

                            new Descargas().execute();
                            if (!ante.contains("OV"))
                                new EFoto().execute();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "EL CAMPO INFRACCION ESTA VACIO", Toast.LENGTH_LONG);
                            toast.setGravity(0, 0, 15);
                            toast.show();
                        }
                    }
                }
					//}
				/*}catch(Exception e) {
					
					btnGuardar.setEnabled(false);
					btnImprimir.setEnabled(true);
				}*/
				/*btnGuardar.setEnabled(false);
				btnImprimir.setEnabled(true);
				InfraccionesActivity.this.finish();*/
			}
		});
        
        this.btnImprimir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					try{
						//if(id == 1) {
							File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".pdf");
							
							if(file.exists()) {
								Intent i = new Intent(Intent.ACTION_VIEW);
						    	i.setPackage("com.dynamixsoftware.printershare");
                                if (Build.VERSION.SDK_INT < 24)
						    	    i.setDataAndType(Uri.fromFile(file), "application/pdf");
                                else
                                    i.setDataAndType(FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID + ".provider",file), "application/pdf");
                                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivity(i);
							}
					}catch (Exception e) {
						Log.e("Error al", e.getMessage());
					}
				}catch(Exception e) {
					System.out.println(e.getMessage() + " lll");
					Toast toast = Toast.makeText(InfraccionesActivity.this, "Hubo un error al imprimir", Toast.LENGTH_SHORT);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
			}
		});
        
        this.btnTomarF.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					foto ++;
					Log.i("fot", foto+"");
					
					name = "";
					name = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
					File folder = new File(name);
					folder.mkdirs();
					String nom = etNumeroActa.getText().toString()+"-"+foto+".jpg";
					name+=nom.replace("/", "_");
					
					showMsg(name);
					Log.i("Foto", name);
					if (foto == 15) {
						btnTomarF.setVisibility(View.GONE);
					}
					try{
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri out;
                        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }
                        if (Build.VERSION.SDK_INT < 24)
                            out = Uri.fromFile(new File(name));
                        else
                            out = FileProvider.getUriForFile(InfraccionesActivity.this,BuildConfig.APPLICATION_ID + ".provider",new File(name));
						intent.putExtra(MediaStore.EXTRA_OUTPUT,out);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
						startActivityForResult(intent, 1);
					}catch (Exception e) {
						Toast toast = Toast.makeText(InfraccionesActivity.this, "Hubo un error con la camara", Toast.LENGTH_SHORT);
						toast.setGravity(0, 0, 15);
						toast.show();
					}
				}catch(Exception e) {
					Toast toast = Toast.makeText(InfraccionesActivity.this, "Hubo un error con la camara", Toast.LENGTH_SHORT);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
                etDFoto.requestFocus();
			}

		});
        
        this.btnFecha.setOnClickListener(this);
        
        this.btnInicio.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//int n;
				if (!inicio) {
					consu = false;
					Calendar calendar = Calendar.getInstance();
					String h,m;
					h = (calendar.get(Calendar.HOUR_OF_DAY) < 12) ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
					m = (calendar.get(Calendar.MINUTE) < 10) ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));
					hora = h + ":" + m;
					int day = calendar.get(Calendar.MONTH);

					day += 1;
					/*String mes_fecha="";
					if(day<10) {
                    mes_fecha="0"+day;
                    }else{
                        mes_fecha=String.valueOf(day);
                    }*/
					fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + day + "/" + calendar.get(Calendar.YEAR);
					Log.i("Fecha/Hora", fecha + "/" + hora);
					lldiv.setVisibility(View.VISIBLE);
					btnInicio.setVisibility(View.GONE);
					btnConsultar.setVisibility(View.GONE);
					etEspecificacion.setVisibility(View.GONE);
					citatorio = false;
					spconsultar.setVisibility(View.GONE);
					mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
					if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
						thread = new Thread(InfraccionesActivity.this);
						thread.start();
					} else {
						builAlert();
					}
					String [] fechas = fecha.split("/");
					int dia, mes,a;
					String me;
					dia = Integer.parseInt(fechas[0]);
					mes = Integer.parseInt(fechas[1]);
					a = Integer.parseInt(fechas[2].substring(2, 4));
					me = Justificar.mes(mes);
					Log.i("fecha", dia + " " + me + "  " + a);
					//thread.start();
				}
				else {
					InfraccionesActivity.this.finish();
					consu = false;
					Intent intent = new Intent(InfraccionesActivity.this, InfraccionesActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("direccion", title);
					bundle.putString("usuario", us);
					bundle.putInt("id", id);
					intent.putExtras(bundle);
					startActivity(intent);
					
				}
				
			}
		});
        
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.identificacion_array, android.R.layout.simple_spinner_dropdown_item);
        spIdentifica.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vIdentifica));
        
        spIdentificaT.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vIdentifica));
        
        spIdentificaT1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vIdentifica));
        
        //ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.manifiesta_array, android.R.layout.simple_spinner_dropdown_item);
        spManifiesta.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vManifiesta));
        
        //ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.uso_array, android.R.layout.simple_spinner_dropdown_item);
        spuso.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, usoSuelo));
        adapterUso = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, usoSueloH);
        adapterGiro = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,giros);

        spUsoH.setAdapter(adapterUso);
        spgiro.setAdapter(adapterGiro);

        
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.gravedad_array, android.R.layout.simple_spinner_dropdown_item);
        spgravedad.setAdapter(adapter3);
        spNE.setAdapter(adapter3);
        
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this, R.array.designado_array, android.R.layout.simple_spinner_dropdown_item);
        spdesignado.setAdapter(adapter5);
        spdesignado1.setAdapter(adapter5);

        this.btnArticulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contador>=1){
                    //etArticulo.setEnabled(false);

                    mostrarArt(contador);
                    btnArticulos.setEnabled(false);
                }else{

                }

            }
        });
        this.btneliminarA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                reiniciarA(0,etMedida.getText().toString().trim());
                btnArticulos.setEnabled(true);
            }
        });
        this.btneliminarA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarA(1,etMedida1.getText().toString().trim());
                btnArticulos.setEnabled(true);
            }
        });
        this.btneliminarA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarA(2,etMedida2.getText().toString().trim());
                btnArticulos.setEnabled(true);
            }
        });
        this.btneliminarA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarA(3,etMedida3.getText().toString().trim());
                btnArticulos.setEnabled(true);
            }
        });
        this.btneliminarA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarA(4,etMedida4.getText().toString().trim());
                btnArticulos.setEnabled(true);
            }
        });
        
        this.tveliminar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infraccion--;
				if (infraccion < 15)
					spInfraccion.setEnabled(true);
				co--;
				etDesc.setText("");
				etDesc.setVisibility(View.GONE);
				etdato.setText("");
				etdato.setVisibility(View.GONE);
				tvuni.setVisibility(View.GONE);
				tvuni.setText("");
                etObs.setVisibility(View.GONE);
				tveliminar.setVisibility(View.GONE);
				desc = false;
				spuni.setVisibility(View.GONE);
				des = "";
				if(co==0){
					btnaceptar.setVisibility(View.GONE);
				}
				else
					btnaceptar.setVisibility(View.VISIBLE);

				actualizarTV(infraccion);
			}
		});
        
        this.tveliminar1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infraccion--;
				if (infraccion < 15)
					spInfraccion.setEnabled(true);
				co--;
				etDesc1.setText("");
				etDesc1.setVisibility(View.GONE);
				etdato1.setText("");
				etdato1.setVisibility(View.GONE);
				tvuni1.setVisibility(View.GONE);
				tvuni1.setText("");
                etObs1.setVisibility(View.GONE);
				tveliminar1.setVisibility(View.GONE);
                spuni1.setVisibility(View.GONE);
				desc1 = false;
				des1 = "";
				if(co==0){
					btnaceptar.setVisibility(View.GONE);
				}
				else
					btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
			}
		});
        
        this.tveliminar2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infraccion--;
				if (infraccion < 15)
					spInfraccion.setEnabled(true);
				co--;
				etDesc2.setText("");
				etDesc2.setVisibility(View.GONE);
				etdato2.setText("");
				etdato2.setVisibility(View.GONE);
				tvuni2.setVisibility(View.GONE);
				tvuni2.setText("");
                etObs2.setVisibility(View.GONE);
				tveliminar2.setVisibility(View.GONE);
                spuni2.setVisibility(View.GONE);
				desc2 = false;
				des2 = "";
				if(co==0){
					btnaceptar.setVisibility(View.GONE);
				}
				else
					btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
			}
		});
        
        this.tveliminar3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infraccion--;
				if (infraccion < 15)
					spInfraccion.setEnabled(true);
				co--;
				etDesc3.setText("");
				etDesc3.setVisibility(View.GONE);
				etdato3.setText("");
				etdato3.setVisibility(View.GONE);
				tvuni3.setVisibility(View.GONE);
				tvuni3.setText("");
                etObs3.setVisibility(View.GONE);
				tveliminar3.setVisibility(View.GONE);
                spuni3.setVisibility(View.GONE);
				desc3 = false;
				des3 = "";
				if(co==0){
					btnaceptar.setVisibility(View.GONE);
				}
				else
					btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
			}
		});
        
        this.tveliminar4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infraccion--;
				if (infraccion < 15)
					spInfraccion.setEnabled(true);
				co--;
				etDesc4.setText("");
				etDesc4.setVisibility(View.GONE);
				etdato4.setText("");
				etdato4.setVisibility(View.GONE);
				tvuni4.setVisibility(View.GONE);
				tvuni4.setText("");
                etObs4.setVisibility(View.GONE);
				tveliminar4.setVisibility(View.GONE);
                spuni4.setVisibility(View.GONE);
				desc4 = false;
				des4 = "";
				if(co==0){
					btnaceptar.setVisibility(View.GONE);
				}
				else
					btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
			}
		});

        this.tveliminar5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc5.setText("");
                etDesc5.setVisibility(View.GONE);
                etdato5.setText("");
                etdato5.setVisibility(View.GONE);
                tvuni5.setVisibility(View.GONE);
                tvuni5.setText("");
                etObs5.setVisibility(View.GONE);
                tveliminar5.setVisibility(View.GONE);
                spuni5.setVisibility(View.GONE);
                desc5 = false;
                des5 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });
        this.tveliminar6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc6.setText("");
                etDesc6.setVisibility(View.GONE);
                etdato6.setText("");
                etdato6.setVisibility(View.GONE);
                tvuni6.setVisibility(View.GONE);
                tvuni6.setText("");
                etObs6.setVisibility(View.GONE);
                tveliminar6.setVisibility(View.GONE);
                spuni6.setVisibility(View.GONE);
                desc6 = false;
                des6 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });

        this.tveliminar7.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc7.setText("");
                etDesc7.setVisibility(View.GONE);
                etdato7.setText("");
                etdato7.setVisibility(View.GONE);
                tvuni7.setVisibility(View.GONE);
                tvuni7.setText("");
                etObs7.setVisibility(View.GONE);
                tveliminar7.setVisibility(View.GONE);
                spuni7.setVisibility(View.GONE);
                desc7 = false;
                des7 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });

        this.tveliminar8.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc8.setText("");
                etDesc8.setVisibility(View.GONE);
                etdato8.setText("");
                etdato8.setVisibility(View.GONE);
                tvuni8.setVisibility(View.GONE);
                tvuni8.setText("");
                etObs8.setVisibility(View.GONE);
                tveliminar8.setVisibility(View.GONE);
                spuni8.setVisibility(View.GONE);
                desc8 = false;
                des8 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });
        this.tveliminar9.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc9.setText("");
                etDesc9.setVisibility(View.GONE);
                etdato9.setText("");
                etdato9.setVisibility(View.GONE);
                tvuni9.setVisibility(View.GONE);
                tvuni9.setText("");
                etObs9.setVisibility(View.GONE);
                tveliminar9.setVisibility(View.GONE);
                spuni9.setVisibility(View.GONE);
                desc9 = false;
                des9 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });

        this.tveliminar10.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc10.setText("");
                etDesc10.setVisibility(View.GONE);
                etdato10.setText("");
                etdato10.setVisibility(View.GONE);
                tvuni10.setVisibility(View.GONE);
                tvuni10.setText("");
                etObs10.setVisibility(View.GONE);
                tveliminar10.setVisibility(View.GONE);
                spuni10.setVisibility(View.GONE);
                desc10 = false;
                des10 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });

        this.tveliminar11.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc11.setText("");
                etDesc11.setVisibility(View.GONE);
                etdato11.setText("");
                etdato11.setVisibility(View.GONE);
                tvuni11.setVisibility(View.GONE);
                tvuni11.setText("");
                etObs11.setVisibility(View.GONE);
                tveliminar11.setVisibility(View.GONE);
                spuni11.setVisibility(View.GONE);
                desc11 = false;
                des11 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });

        this.tveliminar12.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc12.setText("");
                etDesc12.setVisibility(View.GONE);
                etdato12.setText("");
                etdato12.setVisibility(View.GONE);
                tvuni12.setVisibility(View.GONE);
                tvuni12.setText("");
                etObs12.setVisibility(View.GONE);
                tveliminar12.setVisibility(View.GONE);
                spuni12.setVisibility(View.GONE);
                desc12 = false;
                des12 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });

        this.tveliminar13.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc13.setText("");
                etDesc13.setVisibility(View.GONE);
                etdato13.setText("");
                etdato13.setVisibility(View.GONE);
                tvuni13.setVisibility(View.GONE);
                tvuni13.setText("");
                etObs13.setVisibility(View.GONE);
                tveliminar13.setVisibility(View.GONE);
                spuni13.setVisibility(View.GONE);
                desc13 = false;
                des13 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });

        this.tveliminar14.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc14.setText("");
                etDesc14.setVisibility(View.GONE);
                etdato14.setText("");
                etdato14.setVisibility(View.GONE);
                tvuni14.setVisibility(View.GONE);
                tvuni14.setText("");
                etObs14.setVisibility(View.GONE);
                tveliminar14.setVisibility(View.GONE);
                spuni14.setVisibility(View.GONE);
                desc14 = false;
                des14 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });

        this.tveliminar15.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                infraccion--;
                if (infraccion < 15)
                    spInfraccion.setEnabled(true);
                co--;
                etDesc15.setText("");
                etDesc15.setVisibility(View.GONE);
                etdato15.setText("");
                etdato15.setVisibility(View.GONE);
                tvuni15.setVisibility(View.GONE);
                tvuni15.setText("");
                etObs15.setVisibility(View.GONE);
                tveliminar15.setVisibility(View.GONE);
                spuni15.setVisibility(View.GONE);
                desc15 = false;
                des15 = "";
                if(co==0){
                    btnaceptar.setVisibility(View.GONE);
                }
                else
                    btnaceptar.setVisibility(View.VISIBLE);

                actualizarTV(infraccion);
            }
        });

        
        this.btnaceptar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(validar()>0){
					Toast toast = Toast.makeText(InfraccionesActivity.this, "Le falta ingresar la cantidad", Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
				}
				
				//->validar especificacion
				/*else if (etEspecificacion.getText().toString().equalsIgnoreCase("")) {
					Toast toast = Toast.makeText(InfraccionesActivity.this, "Le falta ingresar la especificaciÔøΩn", Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
				}*/
				else  {
					Log.i("Validar", "no esta visible");
					AlertDialog.Builder dialog = new AlertDialog.Builder(InfraccionesActivity.this);
					dialog.setTitle("Al Aceptar los Hechos se cargaran los articulos correspondientes");
					dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
						
						}
					});
					dialog.setMessage("¿Esta seguro?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							arrayhechosC.clear();
							rlcampo.setVisibility(View.GONE);
							dato = "";
							if(id==5|| id==2){

                                    Log.e("Axmedidas", Axmedidas);
                                    medidas2(Axmedidas);


                            }
							String descrip = etEspecificacion.getText().toString()+".";

							if(etEspecificacion.getText().toString().equals("")){
							    descrip="";
                            }
							/*if (!Double.toString(latitud).equals("0.0") & !Double.toString(longitud).equals("0.0"))   
								descrip += ". LAS COORDENADAS APROXIMADAS SON: LONGITUD: " + longitud + " LATITUD: " + latitud +".";*/
							
							Log.i("Descripcion", descrip);
							int pos = 0;
							Log.i("des",des);
							int x = 1;
							if(!des.equals("")){
                               buscarInfraccion(des);
								infraccion();
								tveliminar.setEnabled(false);
								if(spuni.getVisibility() != View.GONE) {
								    unidades += spuni.getSelectedItem().toString().trim()+",";
                                    if (!spuni.getSelectedItem().toString().equalsIgnoreCase("")) {
                                        seleccion += especialNum(x) + " " + des + " (" + etdato.getText().toString().trim() + " " + spuni.getSelectedItem().toString().trim() + " " + etObs.getText().toString() + "); ";
                                        arrayhechosC.add(especialNum(x) + " " + des + " (" + etdato.getText().toString().trim() + " " + spuni.getSelectedItem().toString().trim() + " " + etObs.getText().toString() + "); ");
                                    }
                                } else {
                                    unidades += " ,";
                                    if(etObs.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des + " (" + etObs.getText().toString() + "). ";
                                        arrayhechosC.add( especialNum(x) + " " + des + " (" + etObs.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des + ". ";
                                        arrayhechosC.add( especialNum(x) + " " + des + " ");
                                    }
                                    //seleccion += x + " " + des + "("+etObs.getText().toString()+"); ";
                                }
								x++;
								dato += etdato.getText().toString().trim() + ","; 
							}
							if(!des1.equals("")){
                                buscarInfraccion(des1);
								infraccion();
								tveliminar1.setEnabled(false);
                                if(spuni1.getVisibility() != View.GONE) {
                                    unidades += spuni1.getSelectedItem().toString().trim()+",";
                                    if (!spuni1.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des1 + " (" + etdato1.getText().toString().trim() + " " + spuni1.getSelectedItem().toString().trim() + " " + etObs1.getText().toString() + "); ";
                                        arrayhechosC.add(especialNum(x) + " " + des1 + " (" + etdato1.getText().toString().trim() + " " + spuni1.getSelectedItem().toString().trim() + " " + etObs1.getText().toString() + "); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs1.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des1 + " (" + etObs1.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des1 + " (" + etObs1.getText().toString() + "). ");
                                    }else {
                                        seleccion += x + " " + des1 + " ";
                                        arrayhechosC.add(especialNum(x) + " " + des1 + " ");
                                        //seleccion += x + " " + des1 + "("+etObs1.getText().toString()+"); ";
                                    }
                                }
                                x++;
								dato += etdato1.getText().toString().trim()+ ",";
							}
							if(!des2.equals("")){
                                buscarInfraccion(des2);
								infraccion();
								tveliminar2.setEnabled(false);
                                if(spuni2.getVisibility() != View.GONE) {
                                    unidades += spuni2.getSelectedItem().toString().trim()+",";
                                    if (!spuni2.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des2 + " (" + etdato2.getText().toString().trim() + " " + spuni2.getSelectedItem().toString() + " " + etObs2.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des2 + " (" + etdato2.getText().toString().trim() + " " + spuni2.getSelectedItem().toString() + " " + etObs2.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs2.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des2 + " (" + etObs2.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des2 + " (" + etObs2.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des2 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des2 + " ");
                                        //seleccion += x + " " + des2 + "("+etObs2.getText().toString()+"); ";
                                    }
                                }
                                x++;
								dato += etdato2.getText().toString().trim()+ ",";
							}
							if(!des3.equals("")){
                                buscarInfraccion(des3);
								infraccion();
								tveliminar3.setEnabled(false);
                                if(spuni3.getVisibility() != View.GONE) {
                                    unidades += spuni3.getSelectedItem().toString().trim()+",";
                                    if (!spuni3.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des3 + " (" + etdato3.getText().toString().trim() + " " + spuni3.getSelectedItem().toString() + " " + etObs3.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des3 + " (" + etdato3.getText().toString().trim() + " " + spuni3.getSelectedItem().toString() + " " + etObs3.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs3.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des3 + " (" + etObs3.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des3 + " (" + etObs3.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des3 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des3 + " ");
                                        //seleccion += x + " " + des3 + "("+etObs3.getText().toString()+"); ";
                                    }
                                }
                                x++;
								dato += etdato3.getText().toString().trim()+ ",";
							}
							if(!des4.equals("")){
                               buscarInfraccion(des4);
								infraccion();
								tveliminar4.setEnabled(false);
                                if(spuni4.getVisibility() != View.GONE) {
                                    unidades += spuni4.getSelectedItem().toString().trim()+",";
                                    if (!spuni4.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des4 + " (" + etdato4.getText().toString().trim() + " " + spuni4.getSelectedItem().toString() + " " + etObs4.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des4 + " (" + etdato4.getText().toString().trim() + " " + spuni4.getSelectedItem().toString() + " " + etObs4.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs4.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des4 + " (" + etObs4.getText().toString() + "). ";
                                        arrayhechosC.add( especialNum(x) + " " + des4 + " (" + etObs4.getText().toString() + "). ");
                                    } else {
                                        seleccion += especialNum(x) + " " + des4 + ". ";
                                        arrayhechosC.add( especialNum(x) + " " + des4 + " ");

                                    }
                                    //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                }
                                x++;
								dato += etdato4.getText().toString().trim()+ ",";
							}
                            if(!des5.equals("")){
                                buscarInfraccion(des5);
                                infraccion();
                                tveliminar5.setEnabled(false);
                                if(spuni5.getVisibility() != View.GONE) {
                                    unidades += spuni5.getSelectedItem().toString().trim()+",";
                                    if (!spuni5.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des5 + " (" + etdato5.getText().toString().trim() + " " + spuni5.getSelectedItem().toString() + " " + etObs5.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des5 + " (" + etdato5.getText().toString().trim() + " " + spuni5.getSelectedItem().toString() + " " + etObs5.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs5.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des5 + " (" + etObs5.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des5 + " (" + etObs5.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des5 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des5 + " ");
                                        //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                    }
                                }
                                x++;
                                dato += etdato5.getText().toString().trim()+ ",";
                            }
                            if(!des6.equals("")){
                                buscarInfraccion(des6);
                                infraccion();
                                tveliminar6.setEnabled(false);
                                if(spuni6.getVisibility() != View.GONE) {
                                    unidades += spuni6.getSelectedItem().toString().trim()+",";
                                    if (!spuni6.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des6 + " (" + etdato6.getText().toString().trim() + " " + spuni6.getSelectedItem().toString() + " " + etObs6.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des6 + " (" + etdato6.getText().toString().trim() + " " + spuni6.getSelectedItem().toString() + " " + etObs6.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs6.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des6 + " (" + etObs6.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des6 + " (" + etObs6.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des6 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des6 + " ");
                                        //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                    }
                                }
                                x++;
                                dato += etdato6.getText().toString().trim()+ ",";
                            }
                            if(!des7.equals("")){
                                buscarInfraccion(des7);
                                infraccion();
                                tveliminar7.setEnabled(false);
                                if(spuni7.getVisibility() != View.GONE) {
                                    unidades += spuni7.getSelectedItem().toString().trim()+",";
                                    if (!spuni7.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des7 + " (" + etdato7.getText().toString().trim() + " " + spuni7.getSelectedItem().toString() + " " + etObs7.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des7 + " (" + etdato7.getText().toString().trim() + " " + spuni7.getSelectedItem().toString() + " " + etObs7.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs7.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des7 + " (" + etObs7.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des7 + " (" + etObs7.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des7 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des7 + " ");
                                    }
                                    //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                }
                                x++;
                                dato += etdato7.getText().toString().trim()+ ",";
                            }
                            if(!des8.equals("")){
                                buscarInfraccion(des8);
                                infraccion();
                                tveliminar8.setEnabled(false);
                                if(spuni8.getVisibility() != View.GONE) {
                                    unidades += spuni8.getSelectedItem().toString().trim()+",";
                                    if (!spuni8.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des8 + " (" + etdato8.getText().toString().trim() + " " + spuni8.getSelectedItem().toString() + " " + etObs8.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des8 + " (" + etdato8.getText().toString().trim() + " " + spuni8.getSelectedItem().toString() + " " + etObs8.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs8.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des8 + " (" + etObs8.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des8 + " (" + etObs8.getText().toString() + "). ");
                                    } else {
                                        seleccion += especialNum(x) + " " + des8 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des8 + " ");
                                        //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                    }
                                }
                                x++;
                                dato += etdato8.getText().toString().trim()+ ",";
                            }
                            if(!des9.equals("")){
                                buscarInfraccion(des9);
                                infraccion();
                                tveliminar9.setEnabled(false);
                                if(spuni9.getVisibility() != View.GONE) {
                                    unidades += spuni9.getSelectedItem().toString().trim()+",";
                                    if (!spuni9.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des9 + " (" + etdato9.getText().toString().trim() + " " + spuni9.getSelectedItem().toString() + " " + etObs9.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des9 + " (" + etdato9.getText().toString().trim() + " " + spuni9.getSelectedItem().toString() + " " + etObs9.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs9.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des9 + " (" + etObs9.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des9 + " (" + etObs9.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des9 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des9 + " ");
                                        //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                    }
                                }
                                x++;
                                dato += etdato9.getText().toString().trim()+ ",";
                            }
                            if(!des10.equals("")){
                                buscarInfraccion(des10);
                                infraccion();
                                tveliminar10.setEnabled(false);
                                if(spuni10.getVisibility() != View.GONE) {
                                    unidades += spuni10.getSelectedItem().toString().trim()+",";
                                    if (!spuni10.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des10 + " (" + etdato10.getText().toString().trim() + " " + spuni10.getSelectedItem().toString() + " " + etObs10.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des10 + " (" + etdato10.getText().toString().trim() + " " + spuni10.getSelectedItem().toString() + " " + etObs10.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs10.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des10 + " (" + etObs10.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des10 + " (" + etObs10.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des10 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des10 + " ");
                                    }
                                    //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                }
                                x++;
                                dato += etdato10.getText().toString().trim()+ ",";
                            }
                            if(!des11.equals("")){
                                buscarInfraccion(des11);
                                infraccion();
                                tveliminar11.setEnabled(false);
                                if(spuni11.getVisibility() != View.GONE) {
                                    unidades += spuni11.getSelectedItem().toString().trim()+",";
                                    if (!spuni11.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des11 + " (" + etdato11.getText().toString().trim() + " " + spuni11.getSelectedItem().toString() + " " + etObs11.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des11 + " (" + etdato11.getText().toString().trim() + " " + spuni11.getSelectedItem().toString() + " " + etObs11.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs11.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des11 + " (" + etObs11.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des11 + " (" + etObs11.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des11 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des11 + " ");
                                    }
                                    //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                }
                                x++;
                                dato += etdato11.getText().toString().trim()+ ",";
                            }
                            if(!des12.equals("")){
                                buscarInfraccion(des12);
                                infraccion();
                                tveliminar12.setEnabled(false);
                                if(spuni12.getVisibility() != View.GONE) {
                                    unidades += spuni12.getSelectedItem().toString().trim()+",";
                                    if (!spuni12.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des12 + " (" + etdato12.getText().toString().trim() + " " + spuni12.getSelectedItem().toString() + " " + etObs12.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des12 + " (" + etdato12.getText().toString().trim() + " " + spuni12.getSelectedItem().toString() + " " + etObs12.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs12.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des12 + " (" + etObs12.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des12 + " (" + etObs12.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des12 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des12 + " ");
                                    }

                                    //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                }
                                x++;
                                dato += etdato12.getText().toString().trim()+ ",";
                            }
                            if(!des13.equals("")){
                                buscarInfraccion(des13);
                                infraccion();
                                tveliminar13.setEnabled(false);
                                if(spuni13.getVisibility() != View.GONE) {
                                    unidades += spuni13.getSelectedItem().toString().trim()+",";
                                    if (!spuni13.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des13 + " (" + etdato13.getText().toString().trim() + " " + spuni13.getSelectedItem().toString() + " " + etObs13.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des13 + " (" + etdato13.getText().toString().trim() + " " + spuni13.getSelectedItem().toString() + " " + etObs13.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs13.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des13 + " (" + etObs13.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des13 + " (" + etObs13.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des13 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des13 + " ");
                                    }
                                    //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                }
                                x++;
                                dato += etdato13.getText().toString().trim()+ ",";
                            }
                            if(!des14.equals("")){
                                buscarInfraccion(des14);
                                infraccion();
                                tveliminar14.setEnabled(false);
                                if(spuni14.getVisibility() != View.GONE) {
                                    unidades += spuni14.getSelectedItem().toString().trim()+",";
                                    if (!spuni14.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des14 + " (" + etdato14.getText().toString().trim() + " " + spuni14.getSelectedItem().toString() + " " + etObs14.getText().toString() +"); ";
                                        arrayhechosC.add(especialNum(x) + " " + des14 + " (" + etdato14.getText().toString().trim() + " " + spuni14.getSelectedItem().toString() + " " + etObs14.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs14.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des14 + " (" + etObs14.getText().toString() + "). ";
                                        arrayhechosC.add(especialNum(x) + " " + des14 + " (" + etObs14.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des14 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des14 + " ");
                                    }
                                    //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                }
                                x++;
                                dato += etdato14.getText().toString().trim()+ ",";
                            }
                            if(!des15.equals("")){
                                buscarInfraccion(des15);
                                infraccion();
                                tveliminar15.setEnabled(false);
                                if(spuni15.getVisibility() != View.GONE) {
                                    unidades += spuni15.getSelectedItem().toString().trim()+",";
                                    if (!spuni15.getSelectedItem().toString().equals("")) {
                                        seleccion += especialNum(x) + " " + des15 + " (" + etdato15.getText().toString().trim() + " " + spuni15.getSelectedItem().toString() + " " + etObs15.getText().toString() +"); ";
                                        arrayhechosC.add( especialNum(x) + " " + des15 + " (" + etdato15.getText().toString().trim() + " " + spuni15.getSelectedItem().toString() + " " + etObs15.getText().toString() +"); ");
                                    }
                                } else {
                                    unidades+=" ,";
                                    if(etObs15.getText().toString().trim().length()>1) {
                                        seleccion += especialNum(x) + " " + des15 + " (" + etObs15.getText().toString() + "). ";
                                        arrayhechosC.add( especialNum(x) + " " + des15 + " (" + etObs15.getText().toString() + "). ");
                                    }else {
                                        seleccion += especialNum(x) + " " + des15 + ". ";
                                        arrayhechosC.add(especialNum(x) + " " + des15 + " ");
                                    }
                                    //seleccion += x + " " + des4 + "("+etObs4.getText().toString()+"); ";
                                }
                                x++;
                                dato += etdato15.getText().toString().trim()+ ",";
                            }

							x++;
							seleccion += descrip;
							Log.i("Dato", dato);
                            Log.e("etEspecificacion", etEspecificacion.getText().toString()+"." );
							Log.i("seleccion", seleccion);
							Log.i("id", id_hechos);
							Log.i("text", text + " 1 " + camp19);

                            String text2="";

                            //AGREGAR
                            String text3="";
                            //FIN AGREGAR

                            text2 += (!camp1.equals("")) ? campo1 + ":" + camp1 +" ": "";
                            text2 += (!camp2.equals("")) ? campo2 + ":" + camp2 +" ": "";
                            text2 += (!camp3.equals("")) ? campo3 + ":" + camp3 +" ": "";
                            text2 += (!camp4.equals("")) ? campo4 + ":" + camp4 +" ": "";
                            text2 += (!camp5.equals("")) ? campo5 + ":" + camp5 +" ": "";
                            text2 += (!camp6.equals("")) ? campo6 + ":" + camp6 +" ": "";
                            text2 += (!camp7.equals("")) ? campo7 + ":" + camp7 +" ": "";
                            text2 += (!camp8.equals("")) ? campo8 + ":" + camp8 +" ": "";
                            text2 += (!camp9.equals("")) ? campo9 + ":" + camp9 +" ": "";
                            text2 += (!camp0.equals("")) ? campo0 + ":" + camp0 +" ": "";
                            text2 += (!camp11.equals("")) ? campo11 + ":" + camp11 +" ": "";
                            text2 += (!camp12.equals("")) ? campo12 + ":" + camp12 +" ": "";
                            text2 += (!camp13.equals("")) ? campo13 + ":" + camp13 +" ": "";
                            text2 += (!camp14.equals("")) ? campo14 + ":" + camp14 +" ": "";
                            text2 += (!camp15.equals("")) ? campo15 + ":" + camp15 +" ": "";
                            text2 += (!camp16.equals("")) ? campo16 + ":" + camp16 +" ": "";
                            text2 += (!camp17.equals("")) ? campo17 + ":" + camp17 +" ": "";
                            text2 += (!camp18.equals("")) ? campo18 + ":" + camp18 +" ": "";
                            text2 += (!camp19.equals("")) ? campo19 + ":" + camp19 +" ": "";
                            text2 += (!camp20.equals("")) ? campo20 + ":" + camp20 +" ": "";
                            text2 += (!camp21.equals("")) ? campo21 + ":" + camp21 +" ": "";
                            text2 += (!camp22.equals("")) ? campo22 + ":" + camp22 +" ": "";
                            text2 += (!camp23.equals("")) ? campo23 + ":" + camp23 +" ": "";
                            text2 += (!camp24.equals("")) ? campo24 + ":" + camp24 +" ": "";
                            text2 += (!camp25.equals("")) ? campo25 + ":" + camp25 +" ": "";
                            text2 += (!camp26.equals("")) ? campo26 + ":" + camp26 +" ": "";


                            //AGREGAR
                            text3 += (!camp1.equals("")) ?  camp1 + " " + campo1 + ". ": "";
                            text3 += (!camp2.equals("")) ?  camp2 + " " + campo2 + ". ": "";
                            text3 += (!camp3.equals("")) ?  camp3 + " " + campo3 + ". ": "";
                            text3 += (!camp4.equals("")) ?  camp4 + " " + campo4 + ". ": "";
                            text3 += (!camp5.equals("")) ?  camp5 + " " + campo5 + ". ": "";
                            text3 += (!camp6.equals("")) ?  camp6 + " " + campo6 + ". ": "";
                            text3 += (!camp7.equals("")) ?  camp7 + " " + campo7 + ". ": "";
                            text3 += (!camp8.equals("")) ?  camp8 + " " + campo8 + ". ": "";
                            text3 += (!camp9.equals("")) ?  camp9 + " " + campo9 + ". ": "";
                            text3 += (!camp0.equals("")) ?  camp0 + " " + campo0 + ". ": "";
                            text3 += (!camp11.equals("")) ?  camp1 + " " + campo11 +". ": "";
                            text3 += (!camp12.equals("")) ?  camp1 + " " + campo12 +". ": "";
                            text3 += (!camp13.equals("")) ?  camp1 + " " + campo13 +". ": "";
                            text3 += (!camp14.equals("")) ?  camp1 + " " + campo14 +". ": "";
                            text3 += (!camp15.equals("")) ?  camp1 + " " + campo15 +". ": "";
                            text3 += (!camp16.equals("")) ?  camp1 + " " + campo16 +". ": "";
                            text3 += (!camp17.equals("")) ?  camp1 + " " + campo17 +". ": "";
                            text3 += (!camp18.equals("")) ?  camp1 + " " + campo18 +". ": "";
                            text3 += (!camp19.equals("")) ?  camp1 + " " + campo19 +". ": "";
                            text3 += (!camp20.equals("")) ?  camp2 + " " + campo20 +". ": "";
                            text3 += (!camp21.equals("")) ? campo21 + ":" + camp21 +" ": "";
                            text3 += (!camp22.equals("")) ? campo22 + ":" + camp22 +" ": "";
                            text3 += (!camp23.equals("")) ? campo23 + ":" + camp23 +" ": "";
                            text3 += (!camp24.equals("")) ? campo24 + ":" + camp24 +" ": "";
                            text3 += (!camp25.equals("")) ? campo25 + ":" + camp25 +" ": "";
                            text3 += (!camp26.equals("")) ? campo26 + ":" + camp26 +" ": "";
                            //FIN AGREGAR

                            String articulos = "";
                            try{

                                if(text2.isEmpty()){
                                    articulos = "No se recibieron las infracciones, pero se subieron a la base de datos";
                                } else {

                                    articulos = algoritmoRem(text2);
                                }
                            } catch(Exception e){
                                articulos = text3;
                            }

                            System.out.println("+++++ALGORITMO: "+text2+"++++++");
                            System.out.println("++Algoritmo: "+articulos);

//							System.out.println(text);
							Log.i("text", text);
							etEspecificacion.setEnabled(false);
							etInfraccion.setText("");
							etInfraccion.setText(articulos);
                            articulos="";
                            btnaceptar.setVisibility(View.GONE);
							btnmodificar.setVisibility(View.VISIBLE);
							spInfraccion.setEnabled(false); 
							etSeleccion.setVisibility(View.VISIBLE);
							etSeleccion.setEnabled(false);
							seleccion=seleccion.replace("..",".");
                            seleccion=seleccion.replace(". (","(");
                            seleccion=seleccion.replace(". .",".");
							etSeleccion.setText(seleccion);
							btnSi.setEnabled(false);
							btnNo.setEnabled(false);
							etManifiesta.setEnabled(true);
							/*if(id == 2) {algo
								etInfraccion.setEnabled(true);
								etSeleccion.setEnabled(true);
							}*/
							
							int len = 0;
							String txt[];
							txt = Justificar.justifocarTexto(seleccion);
							len += txt.length;
							txt = Justificar.justifocarTexto(hech);
							len += txt.length;
							txt = Justificar.justifocarTexto(articulos);
							len += txt.length;
							txt = Justificar.justifocarTexto(DECLARA);
							len += txt.length;
							txt = Justificar.justifocarTexto(etManifiesta.getText().toString());
							len += txt.length;
							Log.i("lineas", len + "");
							/*if (len > 13) {
								AlertDialog.Builder al = new AlertDialog.Builder(InfraccionesActivity.this);
								al.setMessage("El nÔøΩmero de caracteres utilizados excede la cantidad de lineas disponibles para impresiÔøΩn; le sujerimos modificar la selecciÔøΩn de Hechos");
								al.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										modificar();
									}
								});
								AlertDialog dia = al.create();
								dia.show();
							}*/
							
							//btnmodificar.setVisibility(View.GONE);
						}
					});
					AlertDialog alert = dialog.create();
					alert.show();
				}
			}
		});
        
        btnmodificar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				modificar();
			}
		});
       
        updateDisplay();
        
        rgReincidencia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbNReincidencia:
					etfolio.setText("");
					break;
				case R.id.rbApercebimiento:
					etfolio.setText("AP/" + id + "/");
					break;
				case R.id.rbOrdenvisita:
					etfolio.setText("OV/" + id + "/");
					break;
				case R.id.rbCitatorio:
					etfolio.setText("CI/" + id + "/");
					break;
				default:
					break;
				}
				
			}
		});
        
        /*this.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				InfraccionesActivity.this.btnOrden1.setVisibility(View.GONE);
				etOrden1.setVisibility(View.GONE);
				
				switch (checkedId) {
				
				case R.id.radioApercibimiento:
					infrac = 0;
					etDiaPlazo.setText("");
					etDiaPlazo.setEnabled(true);
					rlProp.setVisibility(View.VISIBLE);
					rlTestA.setVisibility(View.VISIBLE);
					rlVisita.setVisibility(View.VISIBLE);
					llNota.setVisibility(View.VISIBLE);
					llplazo.setVisibility(View.VISIBLE);
					llreincidencia.setVisibility(View.GONE);
					ante = "AP";
					formato = "apercibimiento";
					etGiro.setVisibility(View.VISIBLE);
					etMotivo.setVisibility(View.GONE);
					break;
				case R.id.radioInfraccion:
					infrac = 1;
					etDiaPlazo.setText("20");
					etDiaPlazo.setEnabled(false);
					rlProp.setVisibility(View.VISIBLE);
					rlTestA.setVisibility(View.VISIBLE);
					rlVisita.setVisibility(View.VISIBLE);
					llNota.setVisibility(View.VISIBLE);
					llplazo.setVisibility(View.VISIBLE);
					llreincidencia.setVisibility(View.GONE);
					ante = "IN";
					formato = "infraccion";
					InfraccionesActivity.this.btnOrden1.setVisibility(View.VISIBLE);
					etOrden1.setVisibility(View.VISIBLE);
					etGiro.setVisibility(View.GONE);
					etMotivo.setVisibility(View.GONE);
					//spReglamento.setVisibility(View.VISIBLE);
					//spReglamento.setVisibility(View.GONE);
					//tvReg.setVisibility(View.GONE);
					competencias = "";
					regla = "";
					idComp = 0;
					etOrden1.setText("");
					tvActa.setText("NÔøΩmero Acta");
					break;
				case R.id.radioOrdenV:
					infrac = 2;
					if(id == 1)
						rlProp.setVisibility(View.VISIBLE);
					else
						rlProp.setVisibility(View.GONE);
					rlTestA.setVisibility(View.GONE);
					rlVisita.setVisibility(View.GONE);
					llNota.setVisibility(View.GONE);
					llplazo.setVisibility(View.GONE);
					llreincidencia.setVisibility(View.GONE);
					ante = "OV";
					formato = "orden";
					etGiro.setVisibility(View.GONE);
					etMotivo.setVisibility(View.VISIBLE);
					spReglamento.setVisibility(View.VISIBLE);
					tvReg.setVisibility(View.VISIBLE);
					tvActa.setText("NÔøΩmero Orden");
					break;
					
				case R.id.radioCitatorio:
					infrac = 3;
					rlProp.setVisibility(View.GONE);
					rlTestA.setVisibility(View.GONE);
					rlVisita.setVisibility(View.GONE);
					llNota.setVisibility(View.GONE);
					llplazo.setVisibility(View.GONE);
					llreincidencia.setVisibility(View.GONE);
					ante = "CI";
					formato = "citatorio";
					etGiro.setVisibility(View.GONE);
					etMotivo.setVisibility(View.VISIBLE);
					break;
					
				case R.id.radioHechos:
					infrac = 4;
					rlProp.setVisibility(View.GONE);
					rlTestA.setVisibility(View.GONE);
					rlVisita.setVisibility(View.VISIBLE);
					llNota.setVisibility(View.GONE);
					llplazo.setVisibility(View.GONE);
					llreincidencia.setVisibility(View.GONE);
					//ante = "CI";
					formato = "hechos";
					etGiro.setVisibility(View.GONE);
					break;
				default:
					break;
				}
				numeroA();
				Log.i("infraccion", infrac + "");
			}
		});*/
        
        this.btnSi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				spInfraccion.setEnabled(true);
				etInfraccion.setEnabled(false);
				etSeleccion.setEnabled(false);
				etInfraccion.setText("");
				etSeleccion.setText("");
				usoCatalogo = "S";
				Log.i("Uso catalogo S", usoCatalogo);
				/*if(id == 2) {
					etInfraccion.setEnabled(true);
					etSeleccion.setEnabled(true);
				}*/
			}
		});
        
        this.btnNo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				spInfraccion.setEnabled(false);
				etInfraccion.setEnabled(true);
				etSeleccion.setEnabled(true);
				etdato.setVisibility(View.GONE);
				tveliminar.setVisibility(View.GONE);
				tvuni.setVisibility(View.GONE);
				etObs.setVisibility(View.GONE);
				etDesc.setText("");
				etDesc.setVisibility(View.GONE);
				etdato1.setVisibility(View.GONE);
				tvuni1.setVisibility(View.GONE);
                etObs1.setVisibility(View.GONE);
				tveliminar1.setVisibility(View.GONE);
				etDesc1.setText("");
				etDesc1.setVisibility(View.GONE);
				tvuni2.setVisibility(View.GONE);
                etObs2.setVisibility(View.GONE);
				etdato2.setVisibility(View.GONE);
				tveliminar2.setVisibility(View.GONE);
				etDesc2.setText("");
				etDesc2.setVisibility(View.GONE);
				tvuni3.setVisibility(View.GONE);
                etObs3.setVisibility(View.GONE);
				etdato3.setVisibility(View.GONE);
				tveliminar3.setVisibility(View.GONE);
				etDesc3.setText("");
				etDesc3.setVisibility(View.GONE);
				tvuni4.setVisibility(View.GONE);
                etObs4.setVisibility(View.GONE);
				etdato4.setVisibility(View.GONE);
				tveliminar4.setVisibility(View.GONE);
				etDesc4.setText("");
				etDesc4.setVisibility(View.GONE);
				btnaceptar.setVisibility(View.GONE);
				usoCatalogo = "N";
				Log.i("Uso catalogo N", usoCatalogo);
			}
		});
        
        spuso.setSelection(1);
        
        Log.i("iddmv nv", id + " " + "id");
        /*if(id == 2) {
        	etInfraccion.setEnabled(true);
        	etSeleccion.setEnabled(true);
        }*/
        
        medidas = buscarMedida("");
        //spIdentifica.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, vIdentifica));
        for (int i = 0; i < medidas.size(); i++) {
			medida.add(medidas.get(i).getMedida());
		}
        spMedida.setAdapter(new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, reglamento));
        spReglamento.setVisibility(View.GONE);
        
        if(id == 12 || id == 4 || id == 3) {
        	adapter = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, cmedida);
        	etNombreComercial.setVisibility(View.VISIBLE);
        	spMedida.setAdapter(adapter);
        }
        
        if(id == 2) {
        	tvMotivo.setText(getResources().getString(R.string.verificar));
        	etMotivo.setHint(getResources().getString(R.string.verificar));
        	
        	tvNombreComercial.setText(getResources().getString(R.string.donde_ubica));
        	etNombreComercial.setHint(getResources().getString(R.string.donde_ubica));

        	tvNombreComercial.setVisibility(View.GONE);
            etNombreComercial.setVisibility(View.GONE);
        	
        	
        	adapter = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, cmedida);
        	spMedida.setAdapter(adapter);

        	tvMC.setVisibility(View.GONE);
            //cbDatos2.setVisibility(View.GONE);
        }

        if(id == 5) {
            tvMotivo.setText(getResources().getString(R.string.verificar));
            etMotivo.setHint(getResources().getString(R.string.verificar));

            tvNombreComercial.setText(getResources().getString(R.string.donde_ubica));
            etNombreComercial.setHint(getResources().getString(R.string.donde_ubica));

            tvNombreComercial.setVisibility(View.GONE);
            etNombreComercial.setVisibility(View.GONE);


            adapter = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, cmedida);
            spMedida.setAdapter(adapter);
            //cbDatos2.setVisibility(View.GONE);
        }
        
        if(id == 3) {
        	tvCondominio.setText(getResources().getString(R.string.coto) + " o Mercado Tianguis");
            //cbDatos2.setVisibility(View.GONE);

        }

        if(id == 4) {
            etNombreComercial.setHint("Nombre del Propietario o Representante Legal");
            tvNombreComercial.setText("Nombre del Propietario o Representante Legal");
            etReferencia.setVisibility(View.GONE);
            tvReferencia.setVisibility(View.GONE);

           /* tvfolioap.setVisibility(View.VISIBLE);
            etfolioap.setVisibility(View.VISIBLE);
            tvfechap.setVisibility(View.VISIBLE);
            etfechap.setVisibility(View.VISIBLE);*/

            etAGiro.setHint("Area");
            etAGiro.setText(direccion);
            tvgiro.setText("Area");
            etMotivo.setText("Inspeccionar físicamente que los trabajos o urbanización en proceso, cuenten y presenten los permisos correspondientes como son: ");
            llplazo.setVisibility(View.VISIBLE);
            etAlicencia.setVisibility(View.GONE);
            etLGiro.setVisibility(View.GONE);
            tvALicencia.setVisibility(View.GONE);
            tvNLicencia.setVisibility(View.GONE);
            etAGiro.setVisibility(View.GONE);
            tvgiro.setVisibility(View.GONE);
            llPla.setVisibility(View.GONE);
            etMedida.setVisibility(View.VISIBLE);
            etArticulo.setVisibility(View.GONE);
        }
        if(!reglamento.isEmpty()) {
        	iComp = new int [reglamento.size() - 1];
        	comp = new String [reglamento.size() - 1];
        }

        if(!conceptos.isEmpty()) {
            reg = new int [conceptos.size()];
        }

        medidas();

        getFundamento();

        for (int x = 0; x < fundamento.size();x++) {
            Log.e("fundamento",fundamento.get(x));
            if(!TextUtils.isEmpty(fundamento.get(x))){
                TextView tv = new TextView(this);
                tv.setId(x);
                tv.setText(fundamento.get(x));
                tv.setTextColor(getResources().getColor(R.color.black));
                tv.setTypeface(helvetica);
                tv.setTextSize(16);
                llfundamento.addView(tv);
                getFraccion(idFundamento.get(x));
                for(int y = 0;y < fraccion1.size();y++) {
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setId(idFraccion.get(y));
                    checkBox.setText(fraccion1.get(y));
                    checkBox.setTextColor(getResources().getColor(R.color.black));
                    checkBox.setTypeface(helvetica);
                    checkBox.setOnClickListener(getO(checkBox));
                    llfundamento.addView(checkBox);
                }
            }
        }
        
        for (int i = 0; i < reglamento.size(); i++) {
        	System.err.println(reglamento.get(i));
        	if(!TextUtils.isEmpty(reglamento.get(i))) {
        		comp[i-1] = "";
        		iComp[i-1] = 0;
				cb = new CheckBox(this);
				cb.setId(i);
				cb.setText(reglamento.get(i));
				cb.setTextColor(getResources().getColor(R.color.black));
				cb.setOnClickListener(getOnClick(cb));
				
				llcomp.addView(cb);
        	}
		}

        for (int i = 0; i < conceptos.size(); i++) {
            if(!TextUtils.isEmpty(conceptos.get(i))) {
                cb = new CheckBox(this);
                cb.setId(i);
                cb.setText(conceptos.get(i));
                cb.setTextColor(getResources().getColor(R.color.black));
                cb.setTextSize(16);
                cb.setTypeface(helvetica);
                cb.setOnClickListener(getOn(cb));

                llconcepto.addView(cb);
            }
        }
        
        if(id == 13) {
        	rborden.setVisibility(View.GONE);
        }
        
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, peticion);
    	spPeticion.setAdapter(adapter1);
    	
    	buscarPeticion();
        
       /* if(formato == "infraccion") { 
        	llcomp.setVisibility(View.GONE);
        }*/

       buscarCompetencia();

        if(getIntent().getExtras().getString("nLicencia") != null) {
            etPropietario.setText(getIntent().getExtras().getString("nombre").trim());
            etCalle.setText(getIntent().getExtras().getString("calle").trim());
            etNumero.setText(getIntent().getExtras().getString("exterior").trim());
            etNuemroInterior.setText(getIntent().getExtras().getString("interior").trim());
            etLGiro.setText(getIntent().getExtras().getString("nLicencia").trim() + "");
            etGiro.setText(getIntent().getExtras().getString("giro").trim());
            if(getIntent().getExtras().getInt("acta") == 1) {
                inicio();
                infrac = 1;
                etDiaPlazo.setText("20");
                etDiaPlazo.setEnabled(false);
                if(id == 4)
                    rlProp.setVisibility(View.GONE);
                else
                    rlProp.setVisibility(View.VISIBLE);
                rlTestA.setVisibility(View.VISIBLE);
                rlVisita.setVisibility(View.VISIBLE);
                llNota.setVisibility(View.VISIBLE);
                llplazo.setVisibility(View.VISIBLE);
                llreincidencia.setVisibility(View.GONE);
                ante = "IN";
                formato = "infraccion";
                InfraccionesActivity.this.btnOrden1.setVisibility(View.VISIBLE);


                etOrden1.setVisibility(View.VISIBLE);

                etGiro.setVisibility(View.GONE);
                etMotivo.setVisibility(View.GONE);
                tvMotivo.setVisibility(View.GONE);
                //spReglamento.setVisibility(View.VISIBLE);
                //spReglamento.setVisibility(View.GONE);
                //tvReg.setVisibility(View.GONE);
                competencias = "";
                regla = "";
                idComp = 0;
                etOrden1.setText("");
                tvActa.setText("Número Acta");

                btnConsultar.setVisibility(View.VISIBLE);
                radioInfraccion.setVisibility(View.GONE);
                rborden.setVisibility(View.GONE);
                radioEvento.setVisibility(View.GONE);
                radioReimprimir.setVisibility(View.GONE);

                spNombreA.setVisibility(View.GONE);
                spNombreA1.setVisibility(View.GONE);
                spNombreA2.setVisibility(View.GONE);
                spNombreA3.setVisibility(View.GONE);
                spNombreA4.setVisibility(View.GONE);

                tvAcomp.setVisibility(View.GONE);
                llconcepto.setVisibility(View.GONE);


                if(id == 4) {
                    //System.out.println("gg visible");


                    etNombreComercial.setHint("Nombre del Propietario o Representante Legal");
                    tvNombreComercial.setText("Nombre del Propietario o Representante Legal");
                    etReferencia.setVisibility(View.GONE);
                    tvReferencia.setVisibility(View.GONE);
                    etAGiro.setHint("Area");
                    etAGiro.setText(direccion);
                    tvgiro.setText("Area");
                    etMotivo.setText("Inspeccionar físicamente que los trabajos o urbanización en proceso, cuenten y presenten los permisos correspondientes como son: ");
                    llplazo.setVisibility(View.VISIBLE);
                }
                if(id == 2 | id == 5) {
                    llNota.setVisibility(View.GONE);
                    //tvCondominio.setVisibility(View.GONE);
                    //etCondominio.setVisibility(View.GONE);
                    etfolioap.setVisibility(View.VISIBLE);
                    etfecha.setVisibility(View.VISIBLE);

                }
            } else {
                inicio();
                infrac = 2;
                if(id == 2 || id == 3  || id == 5)
                    rlProp.setVisibility(View.VISIBLE);
                else
                    rlProp.setVisibility(View.GONE);
                rlTestA.setVisibility(View.GONE);
                rlVisita.setVisibility(View.GONE);
                llNota.setVisibility(View.GONE);
                llplazo.setVisibility(View.GONE);
                llreincidencia.setVisibility(View.GONE);
                ante = "OV";
                formato = "orden";
                etGiro.setVisibility(View.GONE);
                //ORDEN VISITA campos

                etMotivo.setVisibility(View.VISIBLE);
                tvMotivo.setVisibility(View.VISIBLE);
                spReglamento.setVisibility(View.GONE);
                tvReg.setVisibility(View.VISIBLE);
                tvActa.setText("Número Orden");

                radioInfraccion.setSelected(true);

                btnConsultar.setVisibility(View.VISIBLE);
                radioInfraccion.setVisibility(View.GONE);
                rborden.setVisibility(View.GONE);

                spgravedad.setSelection(1);


                if(id == 4) {
                    etNombreComercial.setHint("Nombre del Propietario o Representante Legal");
                    tvNombreComercial.setText("Nombre del Propietario o Representante Legal");
                    etReferencia.setVisibility(View.GONE);
                    tvReferencia.setVisibility(View.GONE);

                    etAGiro.setHint("Area");
                    etAGiro.setText(direccion);
                    tvgiro.setText("Area");
                    etMotivo.setText("Inspeccionar físicamente que los trabajos o urbanización en proceso, cuenten y presenten los permisos correspondientes como son: ");
                   // btnTomarF.setVisibility(View.GONE);
                    llPla.setVisibility(View.GONE);


                    etVManifiesta.setVisibility(View.GONE);
                    rgPopiedad.setVisibility(View.GONE);
                }
                if(id == 3) {
                    tvPeticion.setVisibility(View.GONE);
                    spPeticion.setVisibility(View.GONE);
                    tvReg.setVisibility(View.GONE);
                    //cbDatos2.setVisibility(View.GONE);
                }
                if(id==4){
                    tvPeticion.setVisibility(View.GONE);
                    spPeticion.setVisibility(View.GONE);
                    etfoliopeticion.setVisibility(View.GONE);
                    //tvReg.setVisibility(View.GONE);
                }
                if(id == 2 | id == 5) {
                    tvReg.setVisibility(View.GONE);
                    etManifiesta.setText("Se reserva el derecho");

                }
            }
        }
        etfechap.addTextChangedListener(new DateTextWatcher());
        etfechaClau.addTextChangedListener(new DateTextWatcher());
        etfechaOV.addTextChangedListener(new DateTextWatcher());
        spCreglamentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                if(!spCreglamentos.getItemAtPosition(position).toString().equals("Buscar en Todos los reglamentos")){
                    if(spCreglamentos.getItemAtPosition(position).toString().contains("Anuncios")){
                        System.out.println("entro a campos ggg");
                        tvfudamentoEx.setVisibility(View.VISIBLE);
                        etfudamentoEx.setVisibility(View.VISIBLE);
                    }
                    System.out.println(arregloCreglamentosx.get(position));;
                    reglamentoC =arregloCreglamentosx.get(position);
                    Axmedidas+="'"+arregloCreglamentosx.get(position)+"',";
                } else{
                    reglamentoC ="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
    }

    public void actualizarTV(int pos) {
        spselec1.setText("Hechos Seleccionados " + pos);
    }


    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    public boolean isnumeric(String cadena){
        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    public boolean isromano(String cadena){
        return cadena.matches("(^(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$)");
    }

    public boolean iscaracter(String cadena){
        return cadena.matches("[^XIVL]");
    }

    /*Codigo Esteban: Inicio*/

    // Elimina las siguientes las palabras de la cadena
    private String limpiarCadena(String cadena){
        cadena = cadena.trim();
        cadena = cadena.replace("\n","");
        cadena = cadena.replace("Artículos", "");
        cadena = cadena.replace("Articulos", "");
        cadena = cadena.replace("Artículo", "");
        cadena = cadena.replace("Articulo", "");
        cadena = cadena.replace("Artícuos", "");
        cadena = cadena.replace("Artícuo", "");
        cadena = cadena.replace(".", "");
        cadena = cadena.replace(" :", ":");
        cadena = cadena.replace("fracción","fraccion");
        return cadena;
    }

    //Limpia al final de la cadena
    private String limpiarFinalCadena(String subcadena){
        subcadena = subcadena.replaceAll(" y$", "");
        subcadena = subcadena.replaceAll(",$", "");
        subcadena = subcadena.replaceAll(", :$", "");
        return subcadena;
    }




    private boolean isReglamento(String caracter){
        switch(caracter){
            case "R":
            case "B":
            case "C":
            case "D":
            case "L":
                return true;
        }
        return false;
    }

    private boolean isReglamento2(String palabra){
        switch(palabra){
            case "Reglamento":
            case "Buscar":
            case "Código":
            case "Disposiciones":
            case "Ley":
                return true;
        }
        return false;
    }

    private int whileIsNotNumberOrReglamento(int i, String cadena){
        int tamanio = cadena.length();
        String caracter = cadena.substring(i,i+1);
        while(!isnumeric(caracter)){
            i++;
            if(i+1<=tamanio) //Comprueba que no haya llegado al final de la cadena
                caracter = cadena.substring(i,i+1);
            else
                return -1;
            if(this.isReglamento(cadena.substring(i,i+1))){
                if(this.isReglamento2(cadena.substring(i,cadena.indexOf(" ",i)))){
                    return i;
                }
            }
        }
        return i;
    }

    // Iterara la cadena hasta encontrar un carácter que no sea numero
    private int whileIsNumberOrReglamento(int i, String cadena){
        int tamanio = cadena.length();
        String caracter = cadena.substring(i,i+1);
        while(isnumeric(caracter)){
            i++;
            if(i+1<=tamanio) //Comprueba que no haya llegado al final de la cadena
                caracter = cadena.substring(i,i+1);
            else
                return -1;
        }
        return i;
    }

    public int ignorar(String cadena, int pos){
        boolean bandera=true;
        while(bandera){
            switch(cadena.substring(pos,pos+1)){
                case ":":
                case " ":
                case ",":
                case "y":
                    pos++;
                    break;
                default:
                    bandera=false;
            }
        }
        return pos;
    }


    public String algoritmoRem(String cadena){
        MapaReglamentos mapaReglamentos = new MapaReglamentos();
        ArrayList <Articulo> listaArticulos = new ArrayList<>();
        Articulo art;
        int inicio;
        int tamanio;
        int i=0;
        String subcadena;
        String numero="";
        String tipo="";

        cadena = limpiarCadena(cadena);
        tamanio = cadena.length();

        if(tamanio==0) return "";

        cadena = cadena+" ";


        String auxCadena = cadena.replaceAll("numeral \\d","numeral x");
        auxCadena = auxCadena.replaceAll("punto \\d","punto x");
        auxCadena = auxCadena.replaceAll("apartado \\d","apartado x");
        while(i<tamanio){
            System.out.println("i : "+i);
            art = new Articulo();

            if(this.isReglamento(auxCadena.substring(i,i+1))){
                if(this.isReglamento2(cadena.substring(i,auxCadena.indexOf(" ",i)))){
                    inicio=i;
                    i=cadena.indexOf(":",i);
                    tipo=cadena.substring(inicio,i);
                }
            }

            i = this.ignorar(cadena, i);
            inicio=i;

            if(isnumeric(auxCadena.substring(i, i+1))){
                i=whileIsNumberOrReglamento(i, auxCadena);
                if(i==-1) break;
                numero = cadena.substring(inicio, i);
            } else {
                numero="0";
            }

            i=whileIsNotNumberOrReglamento(i, auxCadena);
            if(i==-1) i=cadena.length()-1;

            subcadena = cadena.substring(inicio,i).trim();
            subcadena = limpiarFinalCadena(subcadena);

            art.setDescripcion(subcadena);
            art.setTipo((tipo.isEmpty())?"Articulo(s):":tipo);
            art.setArticulo(Integer.parseInt(numero));

            System.out.println(art);
            if(!buscar(art, listaArticulos)){
                listaArticulos.add(art);
            }
            System.out.println("todo bien1");

        }
        System.out.println("todo bien2");

        ordenar(listaArticulos);
        System.out.println("todo bien3");

        mapaReglamentos.cargarLista(listaArticulos);


        return mapaReglamentos.mostrar();
    }

    private boolean buscar(Articulo art, ArrayList<Articulo> al){
        for(int i=0;i<al.size(); i++){
            if(art.getDescripcion().equalsIgnoreCase(al.get(i).getDescripcion())){
                if(art.getTipo().equalsIgnoreCase(al.get(i).getTipo()))
                    return true;
            }

        }
        return false;
    }



    private void ordenar(ArrayList la){
        Collections.sort(la, new Comparator<Articulo>() {
            @Override
            public int compare(Articulo art1, Articulo art2) {
                return new Integer(art1.getArticulo()).compareTo(new Integer(art2.getArticulo()));
            }
        });
    }
    /*Codigo Esteban: Final*/

    public void algoritmo(String camp1, SQLiteDatabase db){
        String v1 = "";
        ArrayList<String> arr_art=new ArrayList<>();
        int es_fraccion = 0;
        String car1="";
        eliminarT(db);
        if(camp1!=" "){
            if(camp1.contains(".")){
                camp1=camp1.replace(".","");
            }
            System.out.println(camp1.trim().length());
            for(int w=0; w<camp1.trim().length();w++) {
                int tamaño = camp1.length();
                if (w<tamaño)
                    car1 = camp1.trim().substring(w,w+1);
                else{
                    car1 = camp1.substring(tamaño-1);
                }
                if (car1.equals("ó")) {
                    car1 = car1.replace("ó", "o");
                }
                if (es_fraccion == 0) {
                    if (isnumeric(car1)) {
                        v1 += car1;
                    } else if (car1.equals(",") || car1.equals(" ") ||  car1.equals("y")) {
                        if(isnumeric(v1)){
                            if(validarArticulo(db,v1,0)){
                                arr_art.add(v1);
                            }else{
                                arr_art.add(v1);
                                ingresarArticulos(db,Integer.valueOf(v1),"0",0);
                            }
                        }
                        v1 = "";
                    } else if (es_fraccion == 0 && !car1.equals(",") || !car1.equals(" ") && es_fraccion == 0 ) {
                        if (iscaracter(car1)) {
                            v1 += car1;
                            if(v1.contains("fraccion")||v1.contains("Fraccion")||v1.contains("fracciones")||v1.contains("Fracciones")){
                                es_fraccion=1;
                                v1="";
                            }
                            continue;
                        }
                    }
                }else{
                    if(isromano(car1)){
                        if(w<tamaño-1){
                            v1+=car1;
                        }else{
                            v1=car1;

                            int orden=transform_roman_numeral_to_number(v1);
                            if(validarArticulo(db,arr_art.get(arr_art.size()-1),orden)){

                            }else{
                                ingresarArticulos(db,Integer.valueOf(arr_art.get(arr_art.size()-1)),v1,orden);
                            }
                        }
                    }
                    if (car1.equals(",")  || car1.equals(" ")||car1.equals("y")|| !isromano(car1)|| isnumeric(car1)  ) {
                        if(v1!=""){

                            int orden=transform_roman_numeral_to_number(v1);
                            if(validarArticulo(db,arr_art.get(arr_art.size()-1),orden)){

                            }else{
                                ingresarArticulos(db,Integer.valueOf(arr_art.get(arr_art.size()-1)),v1,orden);
                            }
                        }
                        v1="";
                        if(isnumeric(car1)){
                            v1="";
                            w-=1;
                            es_fraccion=0;
                        }
                    }
                }
            }
            System.out.println("Termino");
        }
    }

    public void ingresarArticulos(SQLiteDatabase db, int art, String fraccion, int orden){

        try{
            if(db != null){

                ContentValues cv = new ContentValues();

                cv.put("art", art);
                cv.put("fraccion", fraccion);
                cv.put("orden",orden);


                db.insert("Articulos_fracciones", null, cv);

            }
        }catch (Exception e) {
            Log.i("insertar", e.getMessage());
            System.out.println("no inserto");
        }



    }
    public void eliminarT(SQLiteDatabase db){
        try{
            if(db != null){



                //db.insert("Articulos_fracciones", null, cv);
                db.delete("Articulos_fracciones","1",null);

            }
        }catch (Exception e) {
            Log.i("no elimino", e.getMessage());
            System.out.println("no elimino");
        }

    }

    public boolean validarArticulo(SQLiteDatabase db, String art, int orden){

        try{
            Cursor c=null;
            if(db != null){


                c = db.rawQuery("SELECT art, orden  FROM Articulos_fracciones where art='"+art+"'and orden='"+orden+"'", null);

            }
            return c.getCount()>0;
        }catch (Exception e) {
            Log.i("insertar", e.getMessage());
            System.out.println("no inserto");
            return false;
        }



    }

    public String ordenar(SQLiteDatabase db){
        try{
            Cursor c=null;
            String completo="";
            if(db != null){
                c = db.rawQuery("SELECT *  FROM Articulos_fracciones order by art ,orden asc ", null);
                String art="";
                String fraccion="";
                int fracciones=0;

                if(c.moveToFirst()){
                    do{
                        if(art.equalsIgnoreCase(c.getString(c.getColumnIndex("art")).trim())){
                            if(fraccion.equalsIgnoreCase(c.getString(c.getColumnIndex("fraccion"))) || c.getString(c.getColumnIndex("fraccion")).equalsIgnoreCase("0")){
                            }else{
                                fraccion=c.getString(c.getColumnIndex("fraccion")).trim();

                                if(fracciones<1){
                                    completo+=" fraccion(es) "+fraccion+",";
                                    fracciones++;
                                }else {
                                    completo+=fraccion+",";
                                }

                            }
                        }else{
                            fracciones=0;
                            fraccion="";
                            art=c.getString(c.getColumnIndex("art")).trim();
                            if(fraccion.equalsIgnoreCase(c.getString(c.getColumnIndex("fraccion")))||c.getString(c.getColumnIndex("fraccion")).equalsIgnoreCase("0")){
                            }else{
                                fraccion=c.getString(c.getColumnIndex("fraccion")).trim();
                            }
                            if(c.getPosition()>0)
                                completo += ", ";
                            if(fraccion.equalsIgnoreCase("")){
                                completo+=art + "";

                            }else{
                                completo+=art+" fracción "+fraccion;
                            }

                        }


                        //System.out.println(c.getString(c.getColumnIndex("art"))+" "+c.getString(c.getColumnIndex("fraccion"))+" "+c.getString(c.getColumnIndex("orden")));
                    }while (c.moveToNext());
                }
                return completo.replace(",,",",");
            }
        }catch (Exception e) {
            Log.i("insertar", e.getMessage());
            System.out.println("no inserto");
        }
        return "f";
    }
    public static int transform_roman_numeral_to_number(String roman_numeral) {
        Map<Character, Integer> roman_char_dict = new HashMap<Character, Integer>();
        roman_char_dict.put('I', 1);
        roman_char_dict.put('V', 5);
        roman_char_dict.put('X', 10);
        roman_char_dict.put('L', 50);
        roman_char_dict.put('C', 100);
        roman_char_dict.put('D', 500);
        roman_char_dict.put('M', 1000);
        int res = 0;
        for (int i = 0; i < roman_numeral.length(); i += 1) {
            if (i == 0 || roman_char_dict.get(roman_numeral.charAt(i)) <= roman_char_dict.get(roman_numeral.charAt(i - 1)))
                res += roman_char_dict.get(roman_numeral.charAt(i));
            else
                res += roman_char_dict.get(roman_numeral.charAt(i)) - 2 * roman_char_dict.get(roman_numeral.charAt(i - 1));
        }
        return res;
    }

    
    View.OnClickListener getOnClick(final CheckBox b) {
    	return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(b.isChecked()) {
					
					medidas(campoReg.get(v.getId()));
					/*if(id != 3)
						adapter.notifyDataSetChanged();*/
		    		
					//comp[id] = competencia.get(v.getId()) + " " + b.getText().toString();
					//iComp[id] = idCompetencia.get(v.getId());
				}
				else {
					comp[(v.getId())-1] = "";
					iComp[(v.getId())-1] = 0;
                }
				
				for (int i = 0; i < iComp.length; i++) {
					System.err.println(iComp[i] + " " + i);
				}
			}
		};
    	
    }

    View.OnClickListener getO(final CheckBox b) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fracciones = "";
                Log.e("cb",b.isChecked() + " ");
                if(b.isChecked()) {
                    fundam.add(b.getId()+"");
                }
                else {
                    fundam.remove(b.getId()+"");
                }
                for(int x = 0;x < fundam.size();x++) {
                    Log.e("fundam",fundam.get(x)+"");
                    fracciones += fundam.get(x) + ",";
                }
                if(!fracciones.trim().isEmpty())
                    fracciones = fracciones.substring(0,fracciones.length()-1);
                Log.e("fracciones",fracciones);
                articulos(fracciones);
            }
        };

    }

    View.OnClickListener getOn(final CheckBox b) {
	    return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b.isChecked()) {
                    reg[(v.getId())] = (v.getId()) + 1;
                } else {
                    reg[(v.getId())] = -1;
                }
                for(int i = 0;i < reg.length; i++) {
                    System.err.println(reg[i] + " " + i);
                    if(reg[i]==13 && i==12){
                        tvfolioap.setVisibility(View.VISIBLE);
                        tvfechap.setVisibility(View.VISIBLE);
                        etfolioap.setVisibility(View.VISIBLE);
                        etfechap.setVisibility(View.VISIBLE);
                    }else if(reg[i]==-1 && i==12){
                        tvfolioap.setVisibility(View.GONE);
                        tvfechap.setVisibility(View.GONE);
                        etfolioap.setVisibility(View.GONE);
                        etfechap.setVisibility(View.GONE);


                    }else if(reg[i]==14 && i==13){
                        tvfolioclau.setVisibility(View.VISIBLE);
                        tvfechaClau.setVisibility(View.VISIBLE);
                        etfolioclau.setVisibility(View.VISIBLE);
                        etfechaClau.setVisibility(View.VISIBLE);
                    }
                    else if(reg[i]==-1 && i==13){

                        tvfolioclau.setVisibility(View.GONE);
                        tvfechaClau.setVisibility(View.GONE);
                        etfolioclau.setVisibility(View.GONE);
                        etfechaClau.setVisibility(View.GONE);

                    }
                }
            }

        };
    }
    
    public void inicio() {
    	if (!inicio) {
			consu = false;
			Calendar calendar = Calendar.getInstance();
			String h,m;
			h = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
			m = (calendar.get(Calendar.MINUTE) < 10) ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));
			hora = h + ":" + m;
			int day = calendar.get(Calendar.MONTH);
			day += 1;
			fecha = calendar.get(Calendar.DAY_OF_MONTH) + "/" + day + "/" + calendar.get(Calendar.YEAR);
			Log.i("Fecha/Hora", fecha + "/" + hora);
			lldiv.setVisibility(View.VISIBLE);
			btnInicio.setVisibility(View.GONE);
			btnConsultar.setVisibility(View.GONE);
			etEspecificacion.setVisibility(View.GONE);
			citatorio = false;
			spconsultar.setVisibility(View.GONE);
			mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				thread = new Thread(InfraccionesActivity.this);
				thread.start();
			} else {
				builAlert();
			}
			String [] fechas = fecha.split("/");
			int dia, mes,a;
			String me;
			dia = Integer.parseInt(fechas[0]);
			mes = Integer.parseInt(fechas[1]);
			a = Integer.parseInt(fechas[2].substring(2, 4));
			me = Justificar.mes(mes);
			Log.i("fecha", dia + " " + me + "  " + a);
			//thread.start();
		}
		else {
			InfraccionesActivity.this.finish();
			consu = false;

			Intent intent = new Intent(InfraccionesActivity.this, InfraccionesActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("direccion", title);
			bundle.putString("usuario", us);
			bundle.putInt("id", id);
			intent.putExtras(bundle);
			startActivity(intent);
			
		}
		/*if(consultarActa() == 0){
			Log.i("consultar", "si");
			asignarActa();
			etNumeroActa.setText(id + "/" + id_inspector1 + "/" + fecha + "/" + infrac + "/" + "01");
		}
		else{
			Log.i("consultar", "no");
			asignarActa();
			n = Integer.valueOf(numero)+1;
			Log.i("Numero1", numero);
			if(Integer.parseInt(numero) > 0 & Integer.parseInt(numero) < 9){
				numero = "0"+String.valueOf(n);
				etNumeroActa.setText(id + "/" + id_inspector1 + "/" + fecha + "/" + infrac + "/" + numero);
			}
			else{
				numero = String.valueOf(n);
				etNumeroActa.setText(id + "/" + id_inspector1 + "/" + fecha + "/" + infrac + "/" + numero);
			}
		}*/
	}
    
    public String getMes(int mes) {
		switch (mes) {
		case 1:
			return "Enero";
			
		case 2:
			return "Febrero";
			
		case 3:
			return "Marzo";
			
		case 4:
			return "Abril";
			
		case 5:
			return "Mayo";
			
		case 6:
			return "Junio";
			
		case 7:
			return "Julio";
			
		case 8:
			return "Agosto";
			
		case 9:
			return "Septiembre";
			
		case 10:
			return "Octubre";
			
		case 11:
			return "Noviembre";

		default:
			return "Diciembre";
		}
	}
    
    public void numeroA() {
    	int n;
    	if(!citatorio){
			String [] na;
			if(consultarActa() == 0){
				Log.i("consultar", "si");
				numero = "01";
				etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
				
				buscarNumeroActa();
				if (!numero_acta.isEmpty()) {
					if (na()) {
						//aqui consultar el ultimo y asignar
						na = ultimo().split("/");
						n = Integer.parseInt(na[6]) + 1;
						numero = String.valueOf(n);
						if (numero.length() == 1)
							numero = "0" + n;
						etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
					}
				}
			}
			else{
				//Log.i("consultar", "no");
				asignarActa();
				n = Integer.valueOf(numero)+1;
				//Log.i("Numero1", numero);
				if(n > 0 & n <= 9){
					numero = "0"+String.valueOf(n);
					etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
					//Log.i("numeros", "n " + etNumeroActa.getText().toString().substring(0, 16) + " v " + s.substring(0, 16));
					if(!etNumeroActa.getText().toString().substring(0, 16).equalsIgnoreCase(s.substring(0, 16))){
						//Log.i("numero acta", "si");
						numero = "01";
						etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
						//Log.i("nueva ", InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + infrac + "/" + numero);
					}
				}
				else{
					numero = String.valueOf(n);
					etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
				}
				
				buscarNumeroActa();
				if (!numero_acta.isEmpty()) {
					if (na()) {
						//aqui consultar el ultimo y asignar
						na = ultimo().split("/");
						n = Integer.parseInt(na[6]) + 1;
						numero = String.valueOf(n);
						if (numero.length() == 1)
							numero = "0" + n;
						etNumeroActa.setText(ante + "/" + InfraccionesActivity.this.id + "/" + id_inspector1 + "/" + fecha + "/" + numero);
					}
				}
			}
		}
    }

    public int buscarFoto(String numeroActa, String archivo, String descripcion) {
        try {
            result = conn.fotografia(numeroActa, archivo, descripcion, urlP+"getNumeroActaF.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaF.php"/"http://192.168.0.15/serverSQL/getNumeroActaF.php"*/);
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        //result = conn.fotografia(numeroActa, archivo, descripcion, "http://172.16.1.21/serverSQL/getNumeroActaF.php"/*"http://pgt.no-ip.biz/serverSQL/getNumeroActaF.php""http://192.168.0.11/serverSQL/getNumeroActaF.php"*/);

        if (result!=null) {
            try {
                this.jarray = new JSONArray(result);
                return jarray.length();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return 0;
            }
        }
        return 0;
    }

    public String VerificarFoto() {
        StringBuilder sb = new StringBuilder();
        GestionBD gestionar = new GestionBD(this, "inspeccion", null, 1);
        SQLiteDatabase db = gestionar.getReadableDatabase();
        Cursor F = db.rawQuery("SELECT * FROM Fotografia where estatus = 'N' and descripcion='PDF'", null);
        if (F.getCount() == 0)
            sb.append(" Fotografia");
        db.close();
        return sb.toString();
    }
    public int idLe(String numero_acta) {
        try {
            res2 = conn.idLevantamiento(urlP+"getIdLevantamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getIdLevantamiento.php"/"http://192.168.0.15/serverSQL/getIdLevantamiento.php"*/, numero_acta);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        //res = conn.idLevantamiento("http://172.16.1.21/serverSQL/getIdLevantamiento.php"/*"http://pgt.no-ip.biz/serverSQL/getIdLevantamiento.php""http://192.168.0.11/serverSQL/getIdLevantamiento.php"*/, numero_acta);
        int id = 0;
        if (res2!=null) {

            try {
                this.jarray = new JSONArray(res);
                for (int i = 0; i < jarray.length(); i++) {
                    this.json_data = this.jarray.getJSONObject(i);
                    id = json_data.getInt("id_levantamiento");
                    return  id;
                }
            } catch (JSONException e) {
                Log.e("idl", e.getMessage());
            }
        }
        return 0;
    }
    public void descargarFotografia() {
        GestionBD gestion = new GestionBD(this, "inspeccion", null, 1);
        SQLiteDatabase db = gestion.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Fotografia where estatus1 = 'N' and descripcion='PDF'", null);

        ContentValues cv = new ContentValues();
        cv.put("estatus1", "S");
        //db.update("Levantamiento", cv, "id_levantamiento = " + c.getInt(0), null);
        try {
            if (db != null) {
                System.out.println(c.getCount() + " F");
                if (c.moveToFirst()) {
                    do {
                        System.out.println((buscarFoto(c.getString(2), c.getString(3), c.getString(4)) == 0) + " f");
                        if (buscarFoto(c.getString(2), c.getString(3), c.getString(4)) == 0) {
                            id_l = idLe(c.getString(2));
                            conn.insertFoto(id_l, c.getString(2), c.getString(3), c.getString(4), /*"http://172.16.1.21/serverSQL/insertFoto.php"*/urlP+"insertFoto.php"/*"http://pgt.no-ip.biz/serverSQL/insertFoto.php"/"http://192.168.0.15/serverSQL/insertFoto.php"*/);
                            db.update("Fotografia", cv, "id_fotografia = " + c.getInt(0), null);
                        } else {
                            db.update("Fotografia", cv, "id_fotografia = " + c.getInt(0), null);
                        }

                    } while (c.moveToNext());
                }

            }
        } catch (SQLiteException e) {
            Log.e("descargar foto", e.getMessage());
        } finally {
            db.close();
            c.close();
        }
    }
    public void fotografias() {
        boolean res;
        countF = 0;
        GestionBD gestionarBD = new GestionBD(this, "inspeccion", null, 1);
        SQLiteDatabase db = gestionarBD.getReadableDatabase();
        String s, dir, ar;
        dir = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/";
        File f;
        try {
            if (db != null) {
                Cursor c = db.rawQuery("SELECT * FROM Fotografia where estatus='N' and descripcion='PDF'", null);
                if (c.moveToFirst()) {
                    foto2.clear();
                    archivo2.clear();
                    do {
                        foto2.add(c.getString(2));
                        archivo2.add(c.getString(3));
                        s = c.getString(2).replace("/", "_");
                        ar = c.getString(3);
                        Log.i("Dato", s + " " + ar);
                        f = new File(dir + s + "/" + ar);
                        System.out.println(f.exists());
                        if (f.exists()) {
                            Log.i("Mes", "if exist");
                            File file = new File(f.getAbsolutePath());
                            MultipartEntity mpEntity = new MultipartEntity();
                            ContentBody foto = new FileBody(file, "image/jpeg");

                            mpEntity.addPart("fotoUp", foto);
                            mpEntity.addPart("foto", new StringBody(s));

                            JSONObject json = jsonParser.subirImage(urlP+"pruebaI.php", "POST", mpEntity);

                            //JSONObject json = jsonParser.subirImage("http://192.168.0.16:8080/sitios/pruebas/pruebaI.php", "POST", mpEntity);

                            int success = json.getInt("status");
                            System.out.println(success + " success");

                            ContentValues cv = new ContentValues();
                            String sql;

                            if (success == 1) {
                                System.out.println("envio movio");
                                //cv.put("estatus", "S");
                                //sql = "update Fotografia set ";
                                //db.update("Fotografia", cv, " id_fotografia = " + c.getInt(0), null);
                            } else if (success == 0)
                                System.out.println("envio no movio");
                            else if (success == 3) {
                                System.out.println("existe");
                                cv.put("estatus", "S");
                                db.update("Fotografia", cv, " id_fotografia = " + c.getInt(0), null);
                            } else
                                System.out.println("no envio f");

                        }
                        //db.delete("Fotografia", c.getColumnName(0) + " = '" + c.getString(0) + "'", null);
                    } while (c.moveToNext());
                    c.close();
                    Thread.sleep(4000);
                    if (countF > 0) {
                        try {

                            ArrayList<NameValuePair> carga = new ArrayList<NameValuePair>();

                            carga.add(new BasicNameValuePair("tableta", config));
                            carga.add(new BasicNameValuePair("registros", String.valueOf(0)));
                            carga.add(new BasicNameValuePair("fotos", String.valueOf(countF)));

                            //JSONObject json = jsonParser.realizarHttpRequest("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/insertCarga.php", "POST", carga);

                            JSONObject json = jsonParser.realizarHttpRequest(urlP+"insertCarga.php", "POST", carga);

                            int estatus = json.getInt("status");

                            if (estatus == 1)
                                System.err.println("inserto");
                            else
                                System.err.println("no inserto");

                        } catch (JSONException e) {
                            System.out.println(e.getMessage() + " mm");
                        }
                    }
                }

            }
        } catch (Exception e) {

        }
    }
    public void guardar() {
    	try {
	    		int idLevantamiento, idLevantamientoSQL = 0;
						Calendar calendar = Calendar.getInstance();
						String m,h;
						m = (calendar.get(Calendar.MINUTE) < 10) ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));
						h = (calendar.get(Calendar.HOUR_OF_DAY) < 10) ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
						hr = h + ":" + m;
						Log.i("hr", hr);
						
						String citatorio = "", orden = "";
						Formatter fmt = new Formatter();
						Formatter fmt1 = new Formatter();


							System.err.println("entro primero");
						
						if (!etCitatorio.getText().toString().equalsIgnoreCase("")) {
							int c = Integer.parseInt(etCitatorio.getText().toString());
							fmt.format("%04d", c);
							citatorio = tvC.getText().toString() + fmt + "-" + etAnoOrden.getText().toString();
							System.err.println("entro");
						}else
							System.err.println("entro else");
						if (!etNum.getText().toString().equalsIgnoreCase("")) {
							int o = Integer.parseInt(etNum.getText().toString());
							fmt1.format("%04d", o);
							orden = tvOV.getText().toString() + fmt1;
							System.err.println("entro1");
						}else
							System.err.println("entro else1");
						
						
						System.out.println("citatorio: " + fmt + " orden: " + fmt1);
						System.out.println("citatorio: " + citatorio + " orden: " + orden);
						
						if(insertarActa(etNumeroActa.getText().toString(), id, id_inspector1, fecha, infrac, numero) > 0) {
							Log.i("Inserto", "si");
						}
						else {
							Log.i("Inserto", "no");
						}
        if(id==2 || id==5 || id==4){
            for(int i=0;i<SeguimientoM1.size();i++){
                if(ingresarSeguimientoM(etNumeroActa.getText().toString(), String.valueOf(MainActivity.id_ins_sesion),SeguimientoM1.get(i),fecha)>0){
                    Log.i("Inserto", "si");
                }else {
                    Log.i("No inserto", "no");
                }
            }
        }
						
						if(ingresarNumeroActa(etNumeroActa.getText().toString(), spnombre.getSelectedItem().toString(), fecha) > 0)
							Log.i("Acta", "si");
						else
							Log.i("Acta", "no");
						
					String identifica,firmas;
					
					if(spIdentifica.getSelectedItem().toString().equalsIgnoreCase(""))
						identifica = "No";
					else 
						identifica = "Si";
					
					if(cbFirma.isChecked())
						firmas = "Si";
					else
						firmas = "No";
					
					for (int i = 0; i < iComp.length; i++) {
						if(i == 0) {
							if(iComp[i] != 0) {
								idComp = iComp[i];
							}
						}
						if(i == 1) {
							if(iComp[i] != 0) {
								idCompetencia1 = iComp[i];
							}
						}
						if(i == 2) {
							if(iComp[i] != 0) {
								idCompetencia2 = iComp[i];
							}
						}
						if(i == 3) {
							if(iComp[i] != 0) {
								idCompetencia3 = iComp[i];
							}
						}
						if(i == 4) {
							if(iComp[i] != 0) {
								idCompetencia4 = iComp[i];
							}
						}
						if(i == 5) {
							if(iComp[i] != 0) {
								idCompetencia5 = iComp[i];
							}
						}
					}
					int axo;
					if(TextUtils.isEmpty(etAlicencia.getText().toString().trim())){
						axo = 0;
					}else {
						axo = Integer.parseInt(etAlicencia.getText().toString().trim());
					}
					String reincidencia;
					if(swReincidencia.isChecked())
					    reincidencia = "SI";
					else
					    reincidencia = "NO";
        String fechaap="";
        String fechaclau="";
        if(!etfechap.getText().toString().equals("")){
            fechaap = etfechap.getText().toString();
            String[] split = fechaap.split("-");
            fechaap=split[2]+"-"+split[1]+"-"+split[0];
        }
        if(!etfechaClau.getText().toString().equals("")){
            fechaclau=etfechaClau.getText().toString();
            String [] split2= fechaclau.split("-");
            fechaclau=split2[2]+"-"+split2[1]+"-"+split2[0];
        }

        String medidasEn="";
        if(etNumeroSellos.length()>2){
            medidasEn= medidas1+" (numero sello(s):"+etNumeroSellos.getText().toString()+")";
        }else{
            medidasEn=medidas1;
        }
         String decom="";
        if(etDecomiso.getText().equals("")){
           decom="";
        }else{
            decom=etDecomiso.getText().toString();
        }

					if(id!=4 ){
                        System.out.println("entro a guardar local"+spZona.getSelectedItem().toString());
                        Log.i("levanta", ingresar(etNumeroActa.getText().toString(), tvC.getText().toString() + fmt + "-" + etAnoCitatorio.getText().toString(),infrac, tipoActa,id, fecha, hora, longitud, latitud,
                                etOrden1.getText().toString(), etFecham.getText().toString(),spZona.getSelectedItem().toString(), id_inspector1, id_inspector2,
                                etNombreV.getText().toString(), spIdentifica.getSelectedItem().toString() + ":" + etVIdentifica.getText().toString(), etVManifiesta.getText().toString(),
                                etFraccionamiento.getText().toString(), etCalle.getText().toString(), etNumero.getText().toString(),
                                etNuemroInterior.getText().toString(), etApellidoP.getText().toString(), etApellidoM.getText().toString(),
                                etPropietario.getText().toString(), etNombreT.getText().toString(), spIdentificaT.getSelectedItem().toString() + ":" + etIfeT.getText().toString(),
                                spdesignado.getSelectedItem().toString(), etNombreT1.getText().toString(), spIdentificaT1.getSelectedItem().toString() + ":" +  etIfeT2.getText().toString(),
                                spdesignado1.getSelectedItem().toString(), usoCatalogo,etSeleccion.getText().toString(), etInfraccion.getText().toString(), id_hechos,
                                spuso.getSelectedItem().toString() , spDensidad.getSelectedItem().toString()/*etDensidad.getText().toString()*/, etManifiesta.getText().toString(),
                                Integer.parseInt(spgravedad.getSelectedItem().toString()), Integer.parseInt(etDiaPlazo.getText().toString()), etfecha.getText().toString(), hr, etCondominio.getText().toString(), etLote.getText().toString(), etManzana.getText().toString(), etReferencia.getText().toString(), "", "", etConstruccion.getText().toString(),idComp,etEntreC.getText().toString(),etEntreC1.getText().toString(),etResponsable.getText().toString(),etRegistro.getText().toString(),"N",identifica,
                                spPeticion.getSelectedItem().toString(),firmas,etMotivo.getText().toString(),medidasEn,etArticulo.getText().toString(),
                                id_inspector3,id_inspector4,id_inspector5,id_inspector6,idCompetencia1,idCompetencia2,idCompetencia3,idCompetencia4,idCompetencia5,etLGiro.getText().toString().trim(),etGiro.getText().toString(),axo,etNombreComercial.getText().toString(),etSector.getText().toString(),spNE.getSelectedItem().toString(),reincidencia,tipoEntrega,etfoliopeticion.getText().toString(),etfolioap.getText().toString(),etNumeroSellos.getText().toString(),decom,fechaap,etfolioclau.getText().toString(),fechaclau,tableta,getResources().getString(R.string.version)) + "");
                        resu=true;
					}else if(id==4 && ante=="OV"){
                        System.out.println("entro a guardar local"+spZona.getSelectedItem().toString());

                            String motivo = etMotivo.getText().toString().trim() + " ";
                            String art = "";
                            int x = 1;

                            for (int i = 0; i < reg.length; i++) {
                                if (reg[i] > 0) {
                                    if(reg[i]==13){

                                        motivo+= (x) + "Dar seguimiento a lo señalado en el previo apercibimiento folio "+etfolioap.getText().toString()+" de fecha "+etfechap.getText().toString()+" en lo conducente y en concordancia con la reglamentacion aplicable.";
                                    }if(reg[i]==14){
                                        motivo+= (x) + " Verificar que el estado de Clausurado señalado en el Acta de Infraccion  folio "+ etfolioclau.getText().toString() +" del dia "+ etfechaClau.getText().toString() + "; se respete; que los sellos de Clausurado No se encuentren violentados con su retiro, ruptura, ocultamiento y/o personal al interior laborando en cualquier actividad directamente relacionada con la edificacion en proceso y acotada en el propio cuerpo del acta de Infraccion antes mencionada ";
                                    }
                                    if(reg[i]!=14 && reg[i]!=13){
                                        motivo += (x) + " " + conceptos.get(i) + ",";
                                    }

                                    if(fraccion.get(i).length()>0) {
                                        art += articulo.get(i) + " Fracción " + fraccion.get(i) + ",";
                                        x += 1;
                                    }else{
                                        art += articulo.get(i) + ",";
                                        x += 1;
                                    }
                                }
                            }
                            art = art.substring(0, art.length() - 1);
                            motivo += " Asi mismo inspeccionar, cualquier otra actividad relacionada con la normatividad aplicable y que sea regulada por el Municipio de Zapopan Jalisco, con respecto a la ejecución de trabajos de construcción, remodelación, demolición, movimiento de tierras, excavación, reparación o restauración de cualquier género, así como cualquier acto de ocupacion o utilizacion del suelo que se lleve a cabo en el Municipio de Zapopan. Con base a los articulos: 2, 3, 5, 7  Fracciones I a la VI, 34, 167, 168, 169, 171. ";
                            motivo += art + " del Reglamento de Construccion para el Municipio de Zapopan Jalisco";




                        Log.i("levanta", ingresar(etNumeroActa.getText().toString(), tvC.getText().toString() + fmt + "-" + etAnoCitatorio.getText().toString(),infrac, tipoActa,id, fecha, hora, longitud, latitud,
                                etOrden1.getText().toString(), etFecham.getText().toString(),spZona.getSelectedItem().toString(), id_inspector1, id_inspector2,
                                etNombreV.getText().toString(), spIdentifica.getSelectedItem().toString() + ":" + etVIdentifica.getText().toString(), etVManifiesta.getText().toString(),
                                etFraccionamiento.getText().toString(), etCalle.getText().toString(), etNumero.getText().toString(),
                                etNuemroInterior.getText().toString(), etApellidoP.getText().toString(), etApellidoM.getText().toString(),
                                etPropietario.getText().toString(), etNombreT.getText().toString(), spIdentificaT.getSelectedItem().toString() + ":" + etIfeT.getText().toString(),
                                spdesignado.getSelectedItem().toString(), etNombreT1.getText().toString(), spIdentificaT1.getSelectedItem().toString() + ":" +  etIfeT2.getText().toString(),
                                spdesignado1.getSelectedItem().toString(), usoCatalogo,etSeleccion.getText().toString(), etInfraccion.getText().toString(), id_hechos,
                                spuso.getSelectedItem().toString() , spDensidad.getSelectedItem().toString()/*etDensidad.getText().toString()*/, etManifiesta.getText().toString(),
                                Integer.parseInt(spgravedad.getSelectedItem().toString()), Integer.parseInt(etDiaPlazo.getText().toString()), etfecha.getText().toString(), hr, etCondominio.getText().toString(), etLote.getText().toString(), etManzana.getText().toString(), etReferencia.getText().toString(), "", "", etConstruccion.getText().toString(),idComp,etEntreC.getText().toString(),etEntreC1.getText().toString(),etResponsable.getText().toString(),etRegistro.getText().toString(),"N",identifica,
                                "",firmas,motivo,medidasEn,etArticulo.getText().toString(),
                                id_inspector3,id_inspector4,id_inspector5,id_inspector6,idCompetencia1,idCompetencia2,idCompetencia3,idCompetencia4,idCompetencia5,etLGiro.getText().toString().trim(),etGiro.getText().toString(),axo,etNombreComercial.getText().toString(),etSector.getText().toString(),spNE.getSelectedItem().toString(),reincidencia,tipoEntrega,etfoliopeticion.getText().toString(),etfolioap.getText().toString(),etNumeroSellos.getText().toString(),decom,fechaap,etfolioclau.getText().toString(),fechaclau,tableta,getResources().getString(R.string.version)) + "");
                    }else if(id==4 && ante=="IN"){
                        Log.i("levanta", ingresar(etNumeroActa.getText().toString(), tvC.getText().toString() + fmt + "-" + etAnoCitatorio.getText().toString(),infrac, tipoActa,id, fecha, hora, longitud, latitud,
                                etOrden1.getText().toString(), etFecham.getText().toString(),spZona.getSelectedItem().toString(), id_inspector1, id_inspector2,
                                etNombreV.getText().toString(), spIdentifica.getSelectedItem().toString() + ":" + etVIdentifica.getText().toString(), etVManifiesta.getText().toString(),
                                etFraccionamiento.getText().toString(), etCalle.getText().toString(), etNumero.getText().toString(),
                                etNuemroInterior.getText().toString(), etApellidoP.getText().toString(), etApellidoM.getText().toString(),
                                etPropietario.getText().toString(), etNombreT.getText().toString(), spIdentificaT.getSelectedItem().toString() + ":" + etIfeT.getText().toString(),
                                spdesignado.getSelectedItem().toString(), etNombreT1.getText().toString(), spIdentificaT1.getSelectedItem().toString() + ":" +  etIfeT2.getText().toString(),
                                spdesignado1.getSelectedItem().toString(), usoCatalogo,etSeleccion.getText().toString(), etInfraccion.getText().toString(), id_hechos,
                                spuso.getSelectedItem().toString() , spDensidad.getSelectedItem().toString()/*etDensidad.getText().toString()*/, etManifiesta.getText().toString(),
                                Integer.parseInt(spgravedad.getSelectedItem().toString()), Integer.parseInt(etDiaPlazo.getText().toString()), etfecha.getText().toString(), hr, etCondominio.getText().toString(), etLote.getText().toString(), etManzana.getText().toString(), etReferencia.getText().toString(), "", "", etConstruccion.getText().toString(),idComp,etEntreC.getText().toString(),etEntreC1.getText().toString(),etResponsable.getText().toString(),etRegistro.getText().toString(),"N",identifica,
                                spPeticion.getSelectedItem().toString(),firmas,etMotivo.getText().toString(),medidasEn,etArticulo.getText().toString(),
                                id_inspector3,id_inspector4,id_inspector5,id_inspector6,idCompetencia1,idCompetencia2,idCompetencia3,idCompetencia4,idCompetencia5,etLGiro.getText().toString().trim(),etGiro.getText().toString(),axo,etNombreComercial.getText().toString(),etSector.getText().toString(),spNE.getSelectedItem().toString(),reincidencia,tipoEntrega,etfoliopeticion.getText().toString(),etfolioap.getText().toString(),etNumeroSellos.getText().toString(),decom,fechaap,etfolioclau.getText().toString(),fechaclau,tableta,getResources().getString(R.string.version)) + "");
                    resu=true;
					}
        if (foliox > 0) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("folio", 0);
            editor.apply();
        }
					
						//String peticion,String v_firma,String motivo_orden,String medida_seguridad,String articulo_medida
						String[] iHecho = null;
						String[] iCantidad = null;
						String[] iUnidad = null;
						if(id_hechos != "") {
							iHecho = id_hechos.split(regex);
						}
						if(formato == "infraccion") {
							if(dato != "") {
								iCantidad = dato.split(regex);
							}
						}
						if(unidades != "")
						    iUnidad=unidades.split(regex);
						
						//Log.i("long", iHecho.length + "<- " + iCantidad.length);
						
						resu = false;
						
						/*archivo = "";
						archivo = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
						
						for (int j = 1; j <= foto; j++) {
							String nom = etNumeroActa.getText().toString()+"-"+j+".jpg";
							nom = nom.replace("/", "_");
							String desc = "";
							if (j == 1) {
								desc = etDFoto.getText().toString();
							}
							else if (j == 2) {
								desc = etDFoto1.getText().toString();
							}
							else if (j == 3) {
								desc = etDFoto2.getText().toString();
							}
							else {
								desc = etDFoto3.getText().toString();
							}
							archivo+=nom;
							Log.i("Archivo", nom);
							Log.i("Inserto foto", insertFotrografia(idl, etNumeroActa.getText().toString(), nom, desc,"N")+"");
							//conn.insertFoto(idLevantamientoSQL, etNumeroActa.getText().toString(),nom.replace("/", "-"), desc, "http://10.0.2.2:8080/serverSQL/insertFoto.php")
						}*/
						
						idLevantamiento = consultarLevantamientoID();
						/*Descarga c= new Descarga();
						idLevantamiento=c.idLe(etNumeroActa.getText().toString());*/
						if(formato == "infraccion") {
						    Log.e("Cantidad de hechos", String.valueOf(iHecho.length));
							for(int i = 0; i < iHecho.length; i++) {
								float can = 0;
								int iHec;
								String iUni = "";
								if(idLevantamiento == 0) {
									idLevantamiento++;
								}
								if(iHecho[i].equalsIgnoreCase("")) {
									iHec = 0;
								}
								else {
									iHec = Integer.parseInt(iHecho[i]);
								}
								for (int j = 0; j < iCantidad.length; j++) {
									if (j == i) {
										if(!iCantidad[i].equalsIgnoreCase("")) {
											can = Float.parseFloat(iCantidad[i]);
										}
									}
								}
                                if(iUnidad.length == 0)
                                    iUni = "";
                                else
                                    iUni = iUnidad[i];
								//Log.i("Detalle infacciÔøΩn", idLevantamiento + " " + etNumeroActa.getText().toString() + " " + iHec + " " + can);
								ingresarDetalleInfraccion(idLevantamiento, etNumeroActa.getText().toString(), iHec, can,"N",iUni,arrayhechosC.get(i));
							}
						}
						
						archivo = "";
						archivo = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
						File folder = new File(archivo);
						folder.mkdirs();
						System.out.println("antes");
						//imprimir();
						imprimir(formato);
						System.out.println("despues");


						//10.84.35.153
						if (conn.validarConexion(InfraccionesActivity.this)) {
                           // if (!conn.search("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
                                Log.i("sii", "internet " + id_inspector2);
                                //nv

                                Log.e("DATOSNUEVOS",etfoliopeticion.getText().toString()+"folioap"+etfolioap.getText().toString()+""+etfechap.getText().toString());
                               if(id!=4){

                                   if (Connection.inserta(etNumeroActa.getText().toString(), citatorio, infrac, tipoActa, id, fecha, fecha + " " + hora,
                                           longitud, latitud, etOrden1.getText().toString(), etFecham.getText().toString(), spZona.getSelectedItem().toString(), id_inspector1, id_inspector2,
                                           etNombreV.getText().toString(), spIdentifica.getSelectedItem().toString() + ":" + etVIdentifica.getText().toString(), etVManifiesta.getText().toString(),
                                           etFraccionamiento.getText().toString(), etCalle.getText().toString(), etNumero.getText().toString(),
                                           etNuemroInterior.getText().toString(), etApellidoP.getText().toString(), etApellidoM.getText().toString(),
                                           etPropietario.getText().toString(), etNombreT.getText().toString(), spIdentificaT.getSelectedItem().toString() + ":" + etIfeT.getText().toString(),
                                           spdesignado.getSelectedItem().toString(), etNombreT1.getText().toString(), spIdentificaT1.getSelectedItem().toString() + ":" + etIfeT2.getText().toString(),
                                           spdesignado1.getSelectedItem().toString(), usoCatalogo, etSeleccion.getText().toString(), etInfraccion.getText().toString(), id_hechos,
                                           spuso.getSelectedItem().toString().trim(), spDensidad.getSelectedItem().toString()/*etDensidad.getText().toString()*/, etManifiesta.getText().toString(),
                                           Integer.parseInt(spgravedad.getSelectedItem().toString()), Integer.parseInt(etDiaPlazo.getText().toString()), etfecha.getText().toString(),
                                           fecha + " " + hr, "POR CALIFICAR", etCondominio.getText().toString() + " ", etManzana.getText().toString(), etLote.getText().toString(), etReferencia.getText().toString(), "", /*etAlineamiento.getText().toString()*/"", etConstruccion.getText().toString(), etEntreC.getText().toString(), etEntreC1.getText().toString(), etResponsable.getText().toString(), etRegistro.getText().toString(), idComp,
                                           medidasEn, etArticulo.getText().toString().trim(), etMotivo.getText().toString().trim(), id_inspector3, id_inspector4, id_inspector5, id_inspector6,
                                           idCompetencia1, idCompetencia2, idCompetencia3, idCompetencia4, idCompetencia5
                                           , etLGiro.getText().toString().trim(), etGiro.getText().toString(), axo, etNombreComercial.getText().toString(), etSector.getText().toString(), conf, spPeticion.getSelectedItem().toString(), spNE.getSelectedItem().toString(), reincidencia,tipoEntrega,etfoliopeticion.getText().toString(),etfolioap.getText().toString(),etfechap.getText().toString(),etNumeroSellos.getText().toString(),decom,etfolioclau.getText().toString(),etfechaClau.getText().toString(),tableta,getResources().getString(R.string.version),/*"http://172.16.1.21/serverSQL/insertLevantamiento.php"*/urlP+"insertLevantamientoas.php"/*"http://pgt.no-ip.biz/serverSQL/insertLevantamiento.php"/"http://192.168.0.15/serverSQL/insertLevantamiento.php"*/).equalsIgnoreCase("S")) {

                                       resu = true;




                                       Log.i("inserto", "true");
                                   } else
                                       Log.i("inserto", "false");
                               }else {

                                   if (id == 4 && ante == "OV") {
                                       String motivo = etMotivo.getText().toString().trim() + " ";
                                       String art = "";
                                       int x = 1;

                                       for (int i = 0; i < reg.length; i++) {
                                           if (reg[i] > 0) {
                                               if(reg[i]==13){

                                                   motivo+= (x) + "Dar seguimiento a lo señalado en el previo apercibimiento folio "+etfolioap.getText().toString()+" de fecha "+etfechap.getText().toString()+" en lo conducente y en concordancia con la reglamentacion aplicable.";
                                               }if(reg[i]==14){
                                                   motivo+= (x) + " Verificar que el estado de Clausurado señalado en el Acta de Infraccion  folio "+ etfolioclau.getText().toString() +" del dia "+ etfechaClau.getText().toString() + "; se respete; que los sellos de Clausurado No se encuentren violentados con su retiro, ruptura, ocultamiento y/o personal al interior laborando en cualquier actividad directamente relacionada con la edificacion en proceso y acotada en el propio cuerpo del acta de Infraccion antes mencionada ";
                                               }
                                               if(reg[i]!=14 && reg[i]!=13){
                                                   motivo += (x) + " " + conceptos.get(i) + ",";
                                               }

                                               if(fraccion.get(i).length()>0) {
                                                   art += articulo.get(i) + " Fracción " + fraccion.get(i) + ",";
                                                   x += 1;
                                               }else{
                                                   art += articulo.get(i) + ",";
                                                   x += 1;
                                               }
                                           }
                                       }
                                       art = art.substring(0, art.length() - 1);
                                       motivo += " Asi mismo inspeccionar, cualquier otra actividad relacionada con la normatividad aplicable y que sea regulada por el Municipio de Zapopan Jalisco, con respecto a la ejecución de trabajos de construcción, remodelación, demolición, movimiento de tierras, excavación, reparación o restauración de cualquier género, así como cualquier acto de ocupacion o utilizacion del suelo que se lleve a cabo en el Municipio de Zapopan. Con base a los articulos: 2, 3, 5, 7  Fracciones I a la VI, 34, 167, 168, 169, 171. ";
                                       motivo += art + " del Reglamento de Construccion para el Municipio de Zapopan Jalisco";


                                       if (Connection.inserta(etNumeroActa.getText().toString(), citatorio, infrac, tipoActa, id, fecha, fecha + " " + hora,
                                               longitud, latitud, etOrden1.getText().toString(), etFecham.getText().toString(), spZona.getSelectedItem().toString(), id_inspector1, id_inspector2,
                                               etNombreV.getText().toString(), spIdentifica.getSelectedItem().toString() + ":" + etVIdentifica.getText().toString(), etVManifiesta.getText().toString(),
                                               etFraccionamiento.getText().toString(), etCalle.getText().toString(), etNumero.getText().toString(),
                                               etNuemroInterior.getText().toString(), etApellidoP.getText().toString(), etApellidoM.getText().toString(),
                                               etPropietario.getText().toString(), etNombreT.getText().toString(), spIdentificaT.getSelectedItem().toString() + ":" + etIfeT.getText().toString(),
                                               spdesignado.getSelectedItem().toString(), etNombreT1.getText().toString(), spIdentificaT1.getSelectedItem().toString() + ":" + etIfeT2.getText().toString(),
                                               spdesignado1.getSelectedItem().toString(), usoCatalogo, etSeleccion.getText().toString(), etInfraccion.getText().toString(), id_hechos,
                                               spuso.getSelectedItem().toString().trim(), spDensidad.getSelectedItem().toString()/*etDensidad.getText().toString()*/, etManifiesta.getText().toString(),
                                               Integer.parseInt(spgravedad.getSelectedItem().toString()), Integer.parseInt(etDiaPlazo.getText().toString()), etfecha.getText().toString(),
                                               fecha + " " + hr, "POR CALIFICAR", etCondominio.getText().toString() + " ", etManzana.getText().toString(), etLote.getText().toString(), etReferencia.getText().toString(), "", /*etAlineamiento.getText().toString()*/"", etConstruccion.getText().toString(), etEntreC.getText().toString(), etEntreC1.getText().toString(), etResponsable.getText().toString(), etRegistro.getText().toString(), idComp,
                                               medidasEn, etArticulo.getText().toString().trim(), motivo, id_inspector3, id_inspector4, id_inspector5, id_inspector6,
                                               idCompetencia1, idCompetencia2, idCompetencia3, idCompetencia4, idCompetencia5
                                               , etLGiro.getText().toString().trim(), etGiro.getText().toString(), axo, etNombreComercial.getText().toString(), etSector.getText().toString(), conf, "", spNE.getSelectedItem().toString(), reincidencia, tipoEntrega, etfoliopeticion.getText().toString(), etfolioap.getText().toString(), etfechap.getText().toString(), etNumeroSellos.getText().toString(), decom, etfolioclau.getText().toString(), etfechaClau.getText().toString(),tableta,getResources().getString(R.string.version),/*"http://172.16.1.21/serverSQL/insertLevantamiento.php"*/urlP+"insertLevantamientoas.php"/*"http://pgt.no-ip.biz/serverSQL/insertLevantamiento.php"/"http://192.168.0.15/serverSQL/insertLevantamiento.php"*/).equalsIgnoreCase("S")) {

                                           resu = true;



                                           Log.i("inserto1", motivo);
                                       } else {
                                           Log.i("inserto", "false");
                                       }
                                   } else {


                                       if (Connection.inserta(etNumeroActa.getText().toString(), citatorio, infrac, tipoActa, id, fecha, fecha + " " + hora,
                                               longitud, latitud, etOrden1.getText().toString(), etFecham.getText().toString(), spZona.getSelectedItem().toString(), id_inspector1, id_inspector2,
                                               etNombreV.getText().toString(), spIdentifica.getSelectedItem().toString() + ":" + etVIdentifica.getText().toString(), etVManifiesta.getText().toString(),
                                               etFraccionamiento.getText().toString(), etCalle.getText().toString(), etNumero.getText().toString(),
                                               etNuemroInterior.getText().toString(), etApellidoP.getText().toString(), etApellidoM.getText().toString(),
                                               etPropietario.getText().toString(), etNombreT.getText().toString(), spIdentificaT.getSelectedItem().toString() + ":" + etIfeT.getText().toString(),
                                               spdesignado.getSelectedItem().toString(), etNombreT1.getText().toString(), spIdentificaT1.getSelectedItem().toString() + ":" + etIfeT2.getText().toString(),
                                               spdesignado1.getSelectedItem().toString(), usoCatalogo, etSeleccion.getText().toString(), etInfraccion.getText().toString(), id_hechos,
                                               spuso.getSelectedItem().toString().trim(), spDensidad.getSelectedItem().toString()/*etDensidad.getText().toString()*/, etManifiesta.getText().toString(),
                                               Integer.parseInt(spgravedad.getSelectedItem().toString()), Integer.parseInt(etDiaPlazo.getText().toString()), etfecha.getText().toString(),
                                               fecha + " " + hr, "POR CALIFICAR", etCondominio.getText().toString() + " ", etManzana.getText().toString(), etLote.getText().toString(), etReferencia.getText().toString(), "", /*etAlineamiento.getText().toString()*/"", etConstruccion.getText().toString(), etEntreC.getText().toString(), etEntreC1.getText().toString(), etResponsable.getText().toString(), etRegistro.getText().toString(), idComp,
                                               medidasEn, etArticulo.getText().toString().trim(), etMotivo.getText().toString().trim(), id_inspector3, id_inspector4, id_inspector5, id_inspector6,
                                               idCompetencia1, idCompetencia2, idCompetencia3, idCompetencia4, idCompetencia5
                                               , etLGiro.getText().toString().trim(), etGiro.getText().toString(), axo, etNombreComercial.getText().toString(), etSector.getText().toString(), conf, "", spNE.getSelectedItem().toString(), reincidencia, tipoEntrega, etfoliopeticion.getText().toString(), etfolioap.getText().toString(), etfechap.getText().toString(), etNumeroSellos.getText().toString(), decom, etfolioclau.getText().toString(), etfechaClau.getText().toString(),tableta,getResources().getString(R.string.version),/*"http://172.16.1.21/serverSQL/insertLevantamiento.php"*/urlP + "insertLevantamientoas.php"/*"http://pgt.no-ip.biz/serverSQL/insertLevantamiento.php"/"http://192.168.0.15/serverSQL/insertLevantamiento.php"*/).equalsIgnoreCase("S")) {

                                           resu = true;



                                           Log.i("inserto2", "true");
                                       } else {
                                           Log.i("inserto", "false");
                                       }


                                   }
                               }

                           // }
						}

        if (conn.validarConexion(InfraccionesActivity.this)) {
            Log.i("sii", "internet " + id_inspector2);
            //nv
            for(int i=0;i<SeguimientoM1.size();i++){
                if (Connection.insertSeguimiento(etNumeroActa.getText().toString(), String.valueOf(MainActivity.id_ins_sesion),SeguimientoM1.get(i),fecha,urlP+"insertSeguimientoM.php")) {



                    Log.i("inserto SEGUIMIENTO", "true");
                }
                else
                    Log.i("NO INSERTO SEGUIMIENTO", "false");
            }

        }

						
												
						if (conn.validarConexion((getApplicationContext()))) {
                            idLevantamientoSQL = getIdLevantamiento();
                            //Descarga c=new Descarga();
                          //idLevantamientoSQL= c.idLe(etNumeroActa.getText().toString());
                        }
					
					if(formato.equalsIgnoreCase("infraccion")) {
						for(int i = 0; i < iHecho.length; i++) {
							float can = 0;
							int iHec;
							String iUni = "";
							if(idLevantamiento == 0) {
								idLevantamiento++;
							}
							if(iHecho[i].equalsIgnoreCase("")) {
								iHec = 0;
							}
							else {
								iHec = Integer.parseInt(iHecho[i]);
							}
							for (int j = 0; j < iCantidad.length; j++) {
								if (j == i) {
									if(!iCantidad[i].equalsIgnoreCase("")) {
										can = Float.parseFloat(iCantidad[i]);
									}
								}
							}
                            if(iUnidad.length == 0)
                                iUni = "";
                            else
                                iUni = iUnidad[i];
							//if (conn.validarConexion(getApplicationContext())) {
                            Log.i(TAG, "hechos: "+arrayhechosC.get(i));
                                conn.insertDetalle(idLevantamientoSQL, etNumeroActa.getText().toString(), iHec, can, iUni,arrayhechosC.get(i),/*"http://172.16.1.21/serverSQL/insertDetalle.php"*/urlP+"insertDetalle.php"/*"http://pgt.no-ip.biz/serverSQL/insertDetalle.php"/"http://192.168.0.11/serverSQL/insertDetalle.php"*/);
                            //}
                             insertFotrografia(idLevantamientoSQL,etNumeroActa.getText().toString(),etNumeroActa.getText().toString()+".pdf","PDF","N","N");
						}
					}
					//}

    	}catch (Throwable e) {
    	    //printStacktrace
            FirebaseCrashlytics.getInstance().setUserId(us.trim());
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            //System.out.println(exceptionAsString);

            FirebaseCrashlytics.getInstance().log(exceptionAsString);

			/*btnGuardar.setEnabled(false);
			btnImprimir.setEnabled(true);
			btnmodificar.setEnabled(false);
			this.tvEvidencia.setVisibility(View.VISIBLE);
	        this.btnTomarF.setVisibility(View.VISIBLE);
	        //btnFtp.setEnabled(true);
	        btnTomarF.setEnabled(true);*/
			/*Toast toast = Toast.makeText(getApplicationContext(), "UPPS!! Hubo un problema al guardar Por favor de volver a generar el documento", Toast.LENGTH_LONG);
			toast.setGravity(0, 0, 15);
			toast.show();*/
		}
    }
    public  class EFoto extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            if(VerificarFoto().equals("")){
                if (conn.search(urlP+"getC_Direccion.php").equals("No se pudo conectar con el servidor")) {
                    //if (!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
                    //if (!conn.search("http://192.168.0.15/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
                    msj = "No se pudo conectar con el servidor";

                }else{

                    if (conn.validarConexion(getApplicationContext())) {
                        //foto();

                        fotografias();
                        descargarFotografia();

                        msj = "La(s) Imagen(es) se ha(n) enviado al servidor";
                    }
                    else
                        msj = "No se encontro conexion a internet";
                }

            }
            else
                msj = "No hay datos guardados en el dispositivo";
            return msj;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pb.setVisibility(View.GONE);
            Toast toast = Toast.makeText(InfraccionesActivity.this, result, Toast.LENGTH_SHORT);
            toast.setGravity(0, 0, 15);
            toast.show();
        }

    }
    public class Descargas extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pb.setVisibility(View.GONE);
            btnGuardar.setEnabled(false);
            if(id==2 || id==5){
                btnImprimirResum.setEnabled(true);
            }

            guarda = true;
            llcomp.setEnabled(false);
            llcomp.setVisibility(View.GONE);
            //btnImprimir.setEnabled(true);
            btnmodificar.setEnabled(false);
            tvEvidencia.setVisibility(View.VISIBLE);
            btnTomarF.setVisibility(View.VISIBLE);
            //btnFtp.setEnabled(true);
            btnVista.setEnabled(false);
            btnTomarF.setEnabled(true);
            if(guarda) {

                    if(!ante.equals("OV")) {
                        if (foto >= 1)
                            btnImprimir.setEnabled(true);
                        else
                            btnImprimir.setEnabled(false);
                    }
                    else
                        btnImprimir.setEnabled(true);

            }
            msj = (conn.validarConexion(getApplicationContext()) & resu) ? "Los datos se han guardado en la base de datos local y enviados al servidor" : "Los datos e imagenes se han guardado en la base de datos local";
            /*Toast toast = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_LONG);
            toast.setGravity(0, 0, 15);
            toast.show();*/

            AlertDialog.Builder builder = new AlertDialog.Builder(InfraccionesActivity.this);
            builder.setTitle("Información");
            builder.setIcon(R.drawable.ic_baseline_check_circle_24);
            builder.setMessage(msj);
            builder.setPositiveButton("Aceptar", null);

            AlertDialog dialog = builder.create();
            dialog.show();

						/*if(foto == 0) {
                            toast = Toast.makeText(getApplicationContext(), "No ah tomado evidencia fotografica", Toast.LENGTH_LONG);
                            toast.setGravity(0, 0, 15);
                            toast.show();
                        }*/
            deshabilitar();
            /*Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
            toast.setGravity(0, 0, 15);
            toast.show();*/
        }
        @Override
        protected String doInBackground(String... params) {
           // if (!conn.search("http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
                //if (!conn.search("http://172.16.1.21/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
                //if (!conn.search("http://192.168.0.15/serverSQL/getC_Direccion.php").trim().equalsIgnoreCase("No se pudo conectar con el servidor")) {
                //if (conn.validarConexion(getApplicationContext()))
            guardar();
            //}
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnGuardar.setEnabled(false);
            pb.setVisibility(View.VISIBLE);
        }
    }
    
    public void guardarFotoLocal() {
    	//
    	archivo = "";
		archivo = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
    	
    	for (int j = 1; j <= foto; j++) {
			String nom = etNumeroActa.getText().toString()+"-"+j+".jpg";
			nom = nom.replace("/", "_");
			String desc = "";
			if (j == 1) {
				desc = etDFoto.getText().toString();
			}
			else if (j == 2) {
				desc = etDFoto1.getText().toString();
			}
			else if (j == 3) {
				desc = etDFoto2.getText().toString();
			}
			else {
				desc = etDFoto3.getText().toString();
			}
			archivo+=nom;
			Log.i("Archivo", nom);
			Log.i("Inserto foto", insertFotrografia(idl, etNumeroActa.getText().toString(), nom, desc,"N","N")+"");
			//conn.insertFoto(idLevantamientoSQL, etNumeroActa.getText().toString(),nom.replace("/", "-"), desc, "http://10.0.2.2:8080/serverSQL/insertFoto.php")
		}
    	//
    	
    }
    
    public void guardarFoto() {
    	
    	archivo = "";
		archivo = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
		File folder = new File(archivo);
		folder.mkdirs();
		//imprimir();
		int idLevantamientoSQL = 0;
		if (conn.validarConexion((getApplicationContext())) & resu) 
			idLevantamientoSQL = getIdLevantamiento();
		
		for (int j = 1; j <= foto; j++) {
			String nom = etNumeroActa.getText().toString()+"-"+j+".jpg";
			nom = nom.replace("/", "_");
			String desc = "";
			if (j == 1) {
				desc = etDFoto.getText().toString();
			}
			else if (j == 2) {
				desc = etDFoto1.getText().toString();
			}
			else if (j == 3) {
				desc = etDFoto2.getText().toString();
			}
			else {
				desc = etDFoto3.getText().toString();
			}
			archivo+=nom;
			Log.i("Archivo", nom);
			//conn.insertFoto(idLevantamientoSQL, etNumeroActa.getText().toString(),nom.replace("/", "-"), desc, "http://10.0.2.2:8080/serverSQL/insertFoto.php");
			if (conn.validarConexion(getApplicationContext()) & resu)
				conn.insertFoto(idLevantamientoSQL, etNumeroActa.getText().toString(),nom.replace("/", "-"), desc, /*"http://172.16.1.21/serverSQL/insertFoto.php"*/urlP+"insertFoto.php"/*"http://pgt.no-ip.biz/serverSQL/insertFoto.php"/"http://192.168.0.11/serverSQL/insertFoto.php"*/);
		}
		archivo = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
		System.out.println(new File(archivo + etNumeroActa.getText().toString().replace("/", "_") + ".txt").exists());
		
		///*
		pd = new ProgressDialog(InfraccionesActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Espere...");
		pd.setTitle("Subiendo FotografÔøΩa(s)");
		pd.setCancelable(true);
		pd.show();
		Thread t = new Thread(new Runnable() {
			boolean r = false;
			
			
			@Override
			public void run() {
				
				Log.i("Ento", "Run");
				
				//if (conn.validarConexion(getApplicationContext()) & resu) {
				if(r) {
				
					if (subirFoto()) {
						String dir = etNumeroActa.getText().toString();
						dir = dir.replace("/", "_");
						Log.i("Ento", "metodo");
						Looper.prepare();
						//String mFTP = "pgt.no-ip.biz";
						//String mFTP = "201.130.205.141";
						String mFTP = "172.16.1.21";
						FTPClient client = null;
						try {
							client = new FTPClient();
							//client.connect(InetAddress.getByName(mFTP));
							client.connect(InetAddress.getByName(mFTP), 21);
							showMsg("logeo: " + client.login("pgm", "pgm2012"));
							Log.i("Status", client.isConnected()+"");
							Log.i("Dir", client.printWorkingDirectory());
							Log.i("Crear dir", client.mkd(dir)+"");
							client.changeWorkingDirectory("/"+dir);
							Log.i("Dir",client.printWorkingDirectory());
							
							String n = etNumeroActa.getText().toString().replace("/", "_") + ".txt",a = archivo;
							a+=n;
							
							if (new File(a).exists()) {
								client.setFileType(FTP.BINARY_FILE_TYPE);
								BufferedInputStream buffInt=null;
								buffInt=new BufferedInputStream(new FileInputStream(a));
								client.enterLocalPassiveMode();
								System.out.println(client.storeFile(n, buffInt));
							}
						
							for (int i = 1; i <= foto; i++) {						
								String nom = etNumeroActa.getText().toString()+"-"+i+".jpg";
								String arch = archivo;
								nom = nom.replace("/", "_");
								arch+=nom;
								
								if (new File(arch).exists()) {
						
									Log.i("Archivo1", nom);
									client.setFileType(FTP.BINARY_FILE_TYPE);
									BufferedInputStream buffIn=null;
									buffIn=new BufferedInputStream(new FileInputStream(arch));
									client.enterLocalPassiveMode();
									System.out.println(client.storeFile(nom, buffIn));
								}
				        
								updateFoto(nom);
							}
							//showMsg("Deslogueo " + client.logout()+"");
							client.disconnect();
							//showMsg("Status " + client.isConnected()+"");
							Log.i("Status1", client.isConnected()+"");
							pd.dismiss();
							Toast toast = Toast.makeText(getApplicationContext(), "Las imagenes se han guardado en la base de datos local y enviados al servidor", Toast.LENGTH_LONG);
							toast.setGravity(0, 0, 15);
							toast.show();
						} catch (IOException e) {
							pd.dismiss();
							Log.e("Error en", e.getMessage());
							
						}
						Looper.loop();
						Looper.myLooper().quit();
						pd.dismiss();
					}
					else {
						pd.dismiss();
					}
				}
				pd.dismiss();
			}
			
		});
		t.start();//*/
    	
    }
    
    public void modificar() {

        rlcampo.setVisibility(View.VISIBLE);
        etEspecificacion.setEnabled(true);
        spInfraccion.setEnabled(true);
        etManifiesta.setEnabled(true);

        etSeleccion.setText("");
        etInfraccion.setText("");

        tveliminar.setEnabled(true);
        tveliminar1.setEnabled(true);
        tveliminar2.setEnabled(true);
        tveliminar3.setEnabled(true);
        tveliminar4.setEnabled(true);
        tveliminar5.setEnabled(true);
        tveliminar6.setEnabled(true);
        tveliminar7.setEnabled(true);
        tveliminar8.setEnabled(true);
        tveliminar9.setEnabled(true);
        tveliminar10.setEnabled(true);
        tveliminar11.setEnabled(true);
        tveliminar12.setEnabled(true);
        tveliminar13.setEnabled(true);
        tveliminar14.setEnabled(true);
        tveliminar15.setEnabled(true);

        text = "";
        seleccion = "";
        dato = "";
        id_hechos  = "";
        unidades = "";

        camp1 = "";
        camp2 = "";
        camp3 = "";
        camp4 = "";
        camp5 = "";
        camp6 = "";
        camp7 = "";
        camp8 = "";
        camp9 = "";
        camp0 = "";
        camp11 = "";
        camp12 = "";
        camp13 = "";
        camp14 = "";
        camp15 = "";
        camp16 = "";
        camp17 = "";
        camp18 = "";
        camp19 = "";
        camp20 = "";

        c1 = "";
        c2 = "";
        c3 = "";
        c4 = "";
        c5 = "";
        c6 = "";
        c7 = "";
        c8 = "";
        c9 = "";
        c0 = "";
        c11 = "";
        c12 = "";
        c13 = "";
        c14 = "";
        c15 = "";
        c16 = "";
        c17 = "";
        c18 = "";
        c19 = "";
        c20 = "";

        campo0 = "";

        arrayhechosC.clear();
    	/*infraccion = 0;
    	co = 0;
		rlcampo.setVisibility(View.VISIBLE);
		etEspecificacion.setEnabled(true);
		etSeleccion.setText("");
		etInfraccion.setText("");
		spInfraccion.setEnabled(true);
		etManifiesta.setEnabled(true);
		
		
		camp1 = "";
		camp2 = "";
		camp3 = "";
		camp4 = "";
		camp5 = "";
		camp6 = "";
		camp7 = "";
		camp8 = "";
		camp9 = "";
		camp0 = "";
		camp11 = "";
		camp12 = "";
		camp13 = "";
		camp14 = "";
		camp15 = "";
		camp16 = "";
		camp17 = "";
		camp18 = "";
		camp19 = "";
		camp20 = "";
		
		c1 = "";
		c2 = "";
		c3 = "";
		c4 = "";
		c5 = "";
		c6 = "";
		c7 = "";
		c8 = "";
		c9 = "";
		c0 = "";
		c11 = "";
		c12 = "";
		c13 = "";
		c14 = "";
		
		text = "";
		seleccion = "";
		dato = "";
		id_hechos  = "";
		
		etDesc.setText("");
		etDesc.setVisibility(View.GONE);
		etdato.setText("");
		etdato.setVisibility(View.GONE);
		tvuni.setVisibility(View.GONE);
		tvuni.setText("");
		tveliminar.setVisibility(View.GONE);
		tveliminar.setEnabled(true);
		
		desc = false;
		des = "";
		
		etDesc1.setText("");
		etDesc1.setVisibility(View.GONE);
		etdato1.setText("");
		etdato1.setVisibility(View.GONE);
		tvuni1.setVisibility(View.GONE);
		tvuni1.setText("");
		tveliminar1.setVisibility(View.GONE);
		tveliminar1.setEnabled(true);
		desc1 = false;
		des1 = "";
		
		etDesc2.setText("");
		etDesc2.setVisibility(View.GONE);
		etdato2.setText("");
		etdato2.setVisibility(View.GONE);
		tvuni2.setVisibility(View.GONE);
		tvuni2.setText("");
		tveliminar2.setVisibility(View.GONE);
		tveliminar2.setEnabled(true);
		desc2 = false;
		des2 = "";
		
		etDesc3.setText("");
		etDesc3.setVisibility(View.GONE);
		etdato3.setText("");
		etdato3.setVisibility(View.GONE);
		tvuni3.setVisibility(View.GONE);
		tvuni3.setText("");
		tveliminar3.setVisibility(View.GONE);
		tveliminar3.setEnabled(true);
		desc3 = false;
		des3 = "";
		
		etDesc4.setText("");
		etDesc4.setVisibility(View.GONE);
		etdato4.setText("");
		etdato4.setVisibility(View.GONE);
		tvuni4.setVisibility(View.GONE);
		tvuni4.setText("");
		tveliminar4.setVisibility(View.GONE);
		tveliminar4.setEnabled(true);
		desc4 = false;
		des4 = "";*/
		
		btnSi.setEnabled(true);
		btnNo.setEnabled(true);
		
		btnmodificar.setVisibility(View.GONE);
		btnaceptar.setVisibility(View.VISIBLE);
    }
    
    
    public void deshabilitar() {
    	cons.setVisibility(View.VISIBLE);
		btnConsultar.setVisibility(View.GONE);
		spconsultar.setVisibility(View.VISIBLE);
		lldiv.setVisibility(View.VISIBLE);
		etEspecificacion.setVisibility(View.GONE);
		citatorio = true;
		btnSi.setEnabled(false);
		btnNo.setEnabled(false);
		tvEspe.setVisibility(View.GONE);
		//btnTomarF.setEnabled(false);
		btnGuardar.setEnabled(false);
		btnMostrar.setEnabled(false);
		btnFecha.setEnabled(false);
		spPoblacion.setEnabled(false);
		spFraccionamiento.setEnabled(false);
		spnombre.setEnabled(false);
		spNombreA.setEnabled(false);
		etLongitud.setEnabled(false);
		etLatitud.setEnabled(false);
		etNum.setEnabled(false);
		etCitatorio.setEnabled(false);
		spZona.setEnabled(false);
		etFecham.setEnabled(false);
		etNombreV.setEnabled(false);
		spIdentifica.setEnabled(false);
		spManifiesta.setEnabled(false);
		etFraccionamiento.setEnabled(false);
		etCalle.setEnabled(false);
		etNumero.setEnabled(false);
		etNuemroInterior.setEnabled(false);
		etApellidoP.setEnabled(false);
		etApellidoM.setEnabled(false);
		spdesignado.setEnabled(false);
		etNombreT.setEnabled(false);
		etIfeT2.setEnabled(false);
		spdesignado1.setEnabled(false);
		etSeleccion.setEnabled(false);
		etInfraccion.setEnabled(false);
		spuso.setEnabled(false);
		//etDensidad.setEnabled(false);
		etManifiesta.setEnabled(false);
		spgravedad.setEnabled(false);
		etDiaPlazo.setEnabled(false);
		etfecha.setEnabled(false);
		etNombreT1.setEnabled(false);
		etIfeT.setEnabled(false);
		etPropietario.setEnabled(false);
		etManifiesta.setEnabled(false);
		etVManifiesta.setEnabled(false);
		etVIdentifica.setEnabled(false);
		spconsultar.setVisibility(View.GONE);
		etCondominio.setEnabled(false);
		spIdentifica.setEnabled(false);
		spIdentificaT.setEnabled(false);
		spIdentificaT1.setEnabled(false);
		btnVisualizar.setEnabled(false);
		/*etDesc.setEnabled(false);
		etDesc1.setEnabled(false);
		etDesc2.setEnabled(false);
		etDesc3.setEnabled(false);
		etDesc4.setEnabled(false);
		etDFoto.setEnabled(false);
		etDFoto1.setEnabled(false);
		etDFoto2.setEnabled(false);
		etDFoto3.setEnabled(false);*/
		etManzana.setEnabled(false);
		etNoA.setEnabled(false);
		etIfeA.setEnabled(false);
		etVigA.setEnabled(false);
		etLote.setEnabled(false);
		etReferencia.setEnabled(false);
		etNombreT.setEnabled(false);
		etOrden1.setEnabled(false);
		etConstruccion.setEnabled(false);
		etResponsable.setEnabled(false);
		etRegistro.setEnabled(false);
		etEntreC.setEnabled(false);
		etEntreC1.setEnabled(false);
		etMedida.setEnabled(false);
		etArticulo.setEnabled(false);
		
		cbFirma.setEnabled(false);
		
		spNombreA1.setEnabled(false);
		spNombreA2.setEnabled(false);
		spNombreA3.setEnabled(false);
		spNombreA4.setEnabled(false);
		
		etMotivo.setEnabled(false);
		etInspccionFue.setEnabled(false);
		
		spPeticion.setEnabled(false);
		
		spReglamento.setEnabled(false);
		
    }
    
    public String colCadena(String cadena, int col){

        

        for(int i=0;i<col;i++){

        	cadena = " "+cadena;

        }

        

        return cadena;

        

        }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	if(requestCode == 1){
    		System.err.println(requestCode + " " + resultCode);
    		if(resultCode == RESULT_OK) {
    				
    				if (foto == 1) {
    					etDFoto.setVisibility(View.VISIBLE);
    					etDFoto.requestFocusFromTouch();
    					btnver1.setVisibility(View.VISIBLE);
    					
    				}
    				else if (foto == 2) {
    					etDFoto1.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    					btnver2.setVisibility(View.VISIBLE);
    				}
    				else if (foto == 3) {
    					etDFoto2.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    					btnver3.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 4){
    					etDFoto3.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    					btnver4.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 5){
    					etDFoto4.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    					btnver5.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 6){
    					etDFoto5.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    					btnver6.setVisibility(View.VISIBLE);
    				}
    				else if(foto == 7){
    					etDFoto6.setVisibility(View.VISIBLE);
    					btnver7.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    				}
    				else if(foto == 8){
    					etDFoto7.setVisibility(View.VISIBLE);
    					btnver8.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    				}
    				else if(foto == 9){
    					etDFoto8.setVisibility(View.VISIBLE);
    					btnver9.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    				}
    				else if(foto == 10){
    					etDFoto9.setVisibility(View.VISIBLE);
    					btnver10.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    				}
    				else if(foto == 11){
    					etDFoto10.setVisibility(View.VISIBLE);
    					btnver11.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    				}
    				else if(foto == 12){
    					etDFoto11.setVisibility(View.VISIBLE);
    					btnver12.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    				}
    				else if(foto == 13){
    					etDFoto12.setVisibility(View.VISIBLE);
    					btnver13.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    				}
    				else if(foto == 14){
    					etDFoto13.setVisibility(View.VISIBLE);
    					btnver14.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    				}
    				else if(foto == 15){
    					etDFoto14.setVisibility(View.VISIBLE);
    					btnver15.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    				}
    				
    				else {
    					etDFoto15.setVisibility(View.VISIBLE);
    					btnver16.setVisibility(View.VISIBLE);
                        etDFoto.requestFocusFromTouch();
    				}
    				
    				if(foto == 15)
    					btnTomarF.setVisibility(View.GONE);
    				else
    					btnTomarF.setVisibility(View.VISIBLE);
    				
    				System.out.println(foto + " foto");
    				if(foto >= 1) {
    					System.err.println("guarda " + guarda);
    				}else {
    					btnImprimir.setEnabled(false);
    				}
					
    				if(guarda) {
                            btnImprimir.setEnabled(true);
                    }
					else
						btnImprimir.setEnabled(false);
    				
    				if(resultCode == RESULT_OK) {
    					//btnImprimir.setEnabled(true);
    					String nom = etNumeroActa.getText().toString()+"-"+foto+".jpg";
        				nom = nom.replace("/", "_");
        				String desc = "";
        				archivo+=nom;
        				Log.i("Archivo", nom);
        				Log.i("Inserto foto", insertFotrografia(idl, etNumeroActa.getText().toString(), nom, desc,"N","N")+"");
    				}
    			}else if(resultCode == RESULT_CANCELED) {
    				System.err.println(RESULT_CANCELED);
    				foto--;
    				if(id==2 |id==5){
    				    btncopiar.requestFocusFromTouch();

                    }else{

                        btncopiar.requestFocusFromTouch();
                    }
    				if(foto == 15)
    					btnTomarF.setVisibility(View.GONE);
    				else
    					btnTomarF.setVisibility(View.VISIBLE);
    				
    				if(foto > 1)
    					btnImprimir.setEnabled(true);
    				else {
    					btnImprimir.setEnabled(false);
    				}
    				
    				if(guarda)
						btnImprimir.setEnabled(true);
					else
						btnImprimir.setEnabled(false);
    			}
    	}
    	else
    		foto--;
    	if (requestCode == 0 & resultCode == RESULT_OK) {
    		String provider = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if(provider != null){
                Log.v("yes", " Location providers: "+provider);
                Thread thread = new Thread(InfraccionesActivity.this);
				thread.start();
            }
    	}
    }
    
    public boolean subirFoto() {
    	boolean r = false;
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	if (db != null) {
    		Cursor c = db.rawQuery("SELECT * FROM Fotografia WHERE enviado = '" + "N" + "'", null);
    		if (c.moveToFirst()) {
				do {
					System.out.println(c.getString(3));
					r = true;
				} while (c.moveToNext());
			}
    		c.close();
    	}
    	db.close();
    	return r;
    }
    
    public void updateFoto(String archivo) {
    	GestionBD gestionaBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionaBD.getWritableDatabase();
    	String update = "UPDATE Fotografia SET enviado = 'S' WHERE archivo = '" + archivo + "'";
    	if (db != null) {
    		try {
    			Log.i("SQL", update);
    			db.execSQL(update);
    		}catch(SQLiteException e) {
    			Log.e("Error ", e.getMessage());
    		}
    		finally {
    			db.close();
    		}
    	}
    	
    }
    
    public Calendar calcular(String fecha,int dias){
		int i = 1,dia;
		final Calendar c = Calendar.getInstance();
		Date d;
		/*String [] f = fecha.split("/");
		c.set(Integer.parseInt(f[2]), Integer.parseInt(f[1])-1, Integer.parseInt(f[0]));*/
		while (i <= dias) {
			c.add(Calendar.DATE, 1);
			d = c.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dia = c.get(Calendar.DAY_OF_WEEK);
			if (!(dia == 1 | dia == 7)) {
				if (diaNoHabiles(sdf.format(d)) == 0) {
					i++;
				}
			 }
		}
		return c;
	}
    
    public int diaNoHabiles(String fecha) {
    	int d = 0;
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1); 
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	
    	if (db != null) {
    		
    		Cursor c = db.rawQuery("select count(*) from C_dia_no_habil where dia_no_habil = '"+fecha+"'", null);/*db.query("C_dia_no_habil", null, "id_c_dia_no_habil", null, null, null, null);*/
    		if (c.moveToFirst()) {
    			do {
					d = c.getInt(0);
				} while (c.moveToNext());
    			c.close();
    		}
    	}
    	db.close();
    	return d;
    }
    
    public int getIdLevantamiento() {
    	int id = 0;
    	//this.result = conn.search("http://192.168.0.11/serverSQL/getIdLevantamientos.php");
    	//this.result = conn.search("http://pgt.no-ip.biz/serverSQL/getIdLevantamientos.php");
    	this.result = conn.search(urlP+"getIdLevantamientos.php");
    	//this.result = conn.search("http://172.16.1.21/serverSQL/getIdLevantamientos.php");
    	
    		try {
    			this.jArray = new JSONArray(result);
    			for (int i = 0; i < jArray.length(); i++) {
    				this.json_data = this.jArray.getJSONObject(i);
    				id = json_data.getInt("id_levantamiento");
    			}
			return id;
    		} catch (Exception e) {
    			Log.e("ERROR", e.getMessage());
    		}
    	
    	return id;
    }
    
    public int getIdDetalle() {
    	//this.result = conn.search("http://192.168.0.11/serverSQL/getDetalleInfaccion.php");
    	//this.result = conn.search("http://10.0.2.2/serverSQL/getDetalleInfaccion.php");
    	this.result = conn.search(urlP+"getDetalleInfraccion.php");
    	//this.result = conn.search("http://172.16.1.21/serverSQL/getDetalleInfaccion.php");
    	
    	int id = 0;
    	try {
    		this.jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				this.json_data = this.jArray.getJSONObject(i);
				id = json_data.getInt("id_levantamiento");
			}
		} catch (Exception e) {
			Log.e("ERROR", "Error en: " + e.getMessage());
		}
    	return id;
    }
    
    public void poblacion() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if (db != null) {
    		Cursor c = db.query("C_poblacion", null, "id_c_poblacion", null, "poblacion", null, "poblacion ASC");
    		try {
    			if (c.moveToFirst()){
    				poblacion.add("");
    				do {
    					poblacion.add(c.getString(1));
    				} while (c.moveToNext());
    			}
    		}catch (SQLiteException e) {
    			Log.e("Error", e.getMessage());
			}
    		finally {
    			c.close();
    			db.close();
    		}
    	}
    }
    
    public void fraccionamiento() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if (db != null) {
    		Cursor c = db.query("C_fraccionamiento", null, "id_c_fraccionamiento", null, "fraccionamiento", null, "fraccionamiento ASC");
            fraccionamiento.clear();
            zonas.clear();
    		try {
    			if (c.moveToFirst()){
    				fraccionamiento.add("Seleccionar");
    				zonas.add("");
    				do {
    					fraccionamiento.add(c.getString(1));
    					zonas.add(c.getString(c.getColumnIndex("zona")));
    				} while (c.moveToNext());
    			}
    		}catch (SQLiteException e) {
				Log.e("Error", e.getMessage());
			}
    		finally{
    			c.close();
    			db.close();
    			adapterCol.notifyDataSetChanged();
    		}
    	}
    	
    }
    
    public void medidas(String condicion) {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if(db != null) {
    		String sql = "select * from c_medida_precautoria where ";
    		if(condicion.equalsIgnoreCase("")) {
    			sql += " 1 = 1";
    		}else {
    			sql += " campo = '" + condicion + "'";
    		}
    		System.err.println(sql);
    		Cursor cursor = db.rawQuery(sql, null);
    		try {
				if(cursor.moveToFirst()) {
					campos.clear();
					cmedida.clear();
					art.clear();
					orden.clear();
					
					campos.add("");
					cmedida.add("");
					art.add("");
					orden.add("");
					
					do {
					   // Log.e("cmedida",cursor.getString(cursor.getColumnIndex("medida_precautoria")).trim());
						campos.add(cursor.getString(cursor.getColumnIndex("campo")));
						cmedida.add(cursor.getString(cursor.getColumnIndex("medida_precautoria")).trim() + " " + cursor.getString(cursor.getColumnIndex("ordenamiento")).trim());
						art.add(cursor.getString(cursor.getColumnIndex("articulos")));
						orden.add(cursor.getString(cursor.getColumnIndex("ordenamiento")));
					} while (cursor.moveToNext());
				}
			} catch (SQLiteException e) {
				System.out.println(e.getMessage());
			}finally{
				cursor.close();
				db.close();
				Log.v("change", "ok");
				adapter.notifyDataSetChanged();
			}
    	}
    }
    public void medidas2(String condicion) {
        GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarDB.getReadableDatabase();


        if(db != null) {
            String sql = "select * from c_medida_precautoria where ";
            if(condicion.equalsIgnoreCase("")) {

                //condicion=condicion.substring(0, condicion.length() - 1);
                //sql += " 1 = 1";
                //String infraccion="";
                String sqlI="";
                for(int i=0;i<arrayincaseF.size();i++){
                    if(arrayincaseF.get(i).length()>2){
                        System.out.println("holaaaaa- "+arrayincaseF.get(i).trim());
                        sqlI="SELECT cod_urbano,dis_comple,ley_bebidas,reg_alumbrado,reg_anuncion,reg_cementerio,reg_com_ind,reg_construccion,reg_fumadores,reg_gestion," +
                                "reg_inclusion,reg_medio_ambiente,reg_movilidad, reg_policia, reg_proteccion,  reg_proteccion_ambiente," +
                                "reg_proteccion_conservacion,  reg_rastro,  reg_residuos,  reg_sonido,  reg_trato_animales,  reg_urbanizacion," +
                                "reg_zonificacion,regtiancom FROM C_infraccion WHERE infraccion like '%"+arrayincaseF.get(i).trim()+"%'";

                    }


                    try {
                        Cursor cursor1 = db.rawQuery(sqlI, null);
                        Log.e("sql:",sqlI);
                        if(cursor1.moveToFirst()) {
                            if(cursor1.getString(cursor1.getColumnIndex("reg_anuncion")).length()>2){
                                condicion+="'reg_anuncion',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_gestion")).length()>2){
                                condicion+="'reg_gestion',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_cementerio")).length()>2){
                                condicion+="'reg_cementerio',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_proteccion_conservacion")).length()>2){
                                condicion+="'reg_proteccion_conservacion',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_proteccion_ambiente")).length()>2){
                                condicion+="'reg_proteccion_ambiente',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_sonido")).length()>2){
                                condicion+="'reg_sonido',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_alumbrado")).length()>2){
                                condicion+="'reg_alumbrado',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_inclusion")).length()>2){
                                condicion+="'reg_inclusion',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_rastro")).length()>2){
                                condicion+="'reg_rastro',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_policia")).length()>2){
                                condicion+="'reg_policia',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("ley_bebidas")).length()>2){
                                condicion+="'ley_bebidas',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_residuos")).length()>2){
                                condicion+="'reg_residuos',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("regtiancom")).length()>2){
                                condicion+="'regtiancom',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_com_ind")).length()>2){
                                condicion+="'reg_com_ind',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_movilidad")).length()>2){
                                condicion+="'reg_movilidad',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_trato_animales")).length()>2){
                                condicion+="'reg_trato_animales',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_fumadores")).length()>2){
                                condicion+="'reg_fumadores',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("cod_urbano")).length()>2){
                                condicion+="'cod_urbano',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("dis_comple")).length()>2){
                                condicion+="'dis_comple',";
                            }


                            if(cursor1.getString(cursor1.getColumnIndex("reg_construccion")).length()>2){
                                condicion+="'reg_construccion',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_medio_ambiente")).length()>2){
                                condicion+="'reg_medio_ambiente',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_proteccion")).length()>2){
                                condicion+="'reg_proteccion',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_urbanizacion")).length()>2){
                                condicion+="'reg_urbanizacion',";
                            }
                            if(cursor1.getString(cursor1.getColumnIndex("reg_zonificacion")).length()>2){
                                condicion+="'reg_zonificacion',";
                            }



                            do {

                            } while (cursor1.moveToNext());
                        }
                    }catch (SQLiteException e){
                        System.out.println(e.getMessage());
                    }


                }
                //Log.e("entro al if donde  ax no esta lleno","yes");
                condicion=condicion.substring(0, condicion.length() - 1);
                sql += " campo in( " + condicion + ")";
            }else {
                //Log.e("entro al if donde  ax si esta lleno","yes");
                condicion=condicion.substring(0, condicion.length() - 1);
                sql += " campo in( " + condicion + ")";
            }
            System.err.println(sql);
            Cursor cursor = db.rawQuery(sql, null);
            try {
                if(cursor.moveToFirst()) {
                    campos.clear();
                    cmedida.clear();
                    art.clear();
                    orden.clear();
                    cMedidaC.clear();

                    campos.add("");
                    cmedida.add("");
                    art.add("");
                    orden.add("");
                    cMedidaC.add("");

                    do {
                        Log.i("entro1",cursor.getString(cursor.getColumnIndex("medida_precautoria")).trim());
                        campos.add(cursor.getString(cursor.getColumnIndex("campo")));
                        cmedida.add(cursor.getString(cursor.getColumnIndex("medida_precautoria")).trim() + " " + cursor.getString(cursor.getColumnIndex("ordenamiento")).trim());
                        cMedidaC.add(cursor.getString(cursor.getColumnIndex("medida_precautoria")).trim());
                        art.add(cursor.getString(cursor.getColumnIndex("articulos")));
                        orden.add(cursor.getString(cursor.getColumnIndex("ordenamiento")));
                    } while (cursor.moveToNext());
                }
                if(id==5){
                    adapter.notifyDataSetChanged();
                    spMedida.setAdapter(new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, cmedida));
                }
                if(id==4){
                    adapter.notifyDataSetChanged();
                    spMedida.setAdapter(new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, cMedidaC));
                }
                if(id==2){
                    spMedida.setAdapter(new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, cmedida));
                }
            } catch (SQLiteException e) {
                System.out.println(e.getMessage());
            }finally{
                cursor.close();
                db.close();
                Log.v("change", "ok");
                adapter.notifyDataSetChanged();
                if(id==5) {
                    spMedida.setAdapter(new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, cmedida));
                }
                if(id==4){
                    adapter.notifyDataSetChanged();
                    spMedida.setAdapter(new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, cMedidaC));
                }
                if(id==2){
                    spMedida.setAdapter(new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, cmedida));
                }
            }
        }
    }
    public void medidas() {
        GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarDB.getReadableDatabase();
        if(db != null) {
            String sql;
            if(id == 5)
                sql = "select * from c_medida_precautoria where id_c_direccion = " + 2;
            else
                sql = "select * from c_medida_precautoria where id_c_direccion = " + id;
            System.err.println(sql);
            Cursor cursor = db.rawQuery(sql, null);
            try {
                if(cursor.moveToFirst()) {
                    campos.clear();
                    cmedida.clear();
                    art.clear();
                    orden.clear();

                    campos.add("");
                    cmedida.add("");
                    art.add("");
                    orden.add("");
                    cMedidaC.add("");

                    do {
                        Log.e("cmedida",cursor.getString(cursor.getColumnIndex("medida_precautoria")).trim());
                        campos.add(cursor.getString(cursor.getColumnIndex("campo")));
                        cmedida.add(cursor.getString(cursor.getColumnIndex("medida_precautoria")).trim() + " " + cursor.getString(cursor.getColumnIndex("ordenamiento")).trim());
                        art.add(cursor.getString(cursor.getColumnIndex("articulos")));
                        cMedidaC.add(cursor.getString(cursor.getColumnIndex("medida_precautoria")).trim());
                        orden.add(cursor.getString(cursor.getColumnIndex("ordenamiento")));
                    } while (cursor.moveToNext());
                }
            } catch (SQLiteException e) {
                System.out.println(e.getMessage());
            }finally{
                cursor.close();
                db.close();
                Log.v("change", "ok");
                //adapter.notifyDataSetChanged();
                if(id==4){
                    adapter.notifyDataSetChanged();
                    spMedida.setAdapter(new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, cMedidaC));
                }
            }
        }
    }

    public void getFundamento() {
        GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarDB.getReadableDatabase();
        if(db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM c_medida_tabla",null);
            try {
                if(cursor.moveToFirst()) {
                    idFundamento.add(0);
                    fundamento.add("");
                    do {
                        idFundamento.add(cursor.getInt(cursor.getColumnIndex("id_c_medida_tabla")));
                        fundamento.add(cursor.getString(cursor.getColumnIndex("fundamento")));
                    } while(cursor.moveToNext());
                }
            } catch (SQLiteException e) {
                Log.e("ERROR",e.getMessage() + " Mensaje");
            } finally {
                cursor.close();
                db.close();
            }
        }
    }

    public void getFraccion(int id_c_medida_tabla) {
        GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarDB.getReadableDatabase();
        if(db != null) {
            idFraccion.clear();
            fraccion1.clear();
            Cursor cursor = db.rawQuery("SELECT * FROM c_medida_tabla_fraccion where id_c_medida_tabla = " + id_c_medida_tabla,null);
            try {
                if(cursor.moveToFirst()) {
                    do {
                        idFraccion.add(cursor.getInt(cursor.getColumnIndex("id_c_medida_tabla_fraccion_id")));
                        if(id_c_medida_tabla == 1)
                            fraccion1.add(cursor.getString(cursor.getColumnIndex("fraccion")));
                        else
                            fraccion1.add("Fracción " + cursor.getString(cursor.getColumnIndex("fraccion")));
                    } while(cursor.moveToNext());
                }
            } catch (SQLiteException e) {
                Log.e("ERROR",e.getMessage() + " Mensaje");
            } finally {
                cursor.close();
                db.close();
            }
        }
    }

    public void articulos(String id) {
	    Log.e("id",id);
	    String f = "";
        GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarDB.getReadableDatabase();
        if(db != null) {
            Cursor cursor = db.rawQuery("select fundamento,fraccion from c_medida_tabla_fraccion a " +
                    "join c_medida_tabla b on a.id_c_medida_tabla = b.id_c_medida_tabla " +
                    "where a.id_c_medida_tabla_fraccion_id in (" + id + ") order by id_c_medida_tabla_fraccion_id",null);
            try {
                articulos = "";
                if(cursor.moveToFirst()) {
                    do {
                        if(!f.trim().equalsIgnoreCase(cursor.getString(cursor.getColumnIndex("fundamento")).trim()))
                            articulos += cursor.getString(cursor.getColumnIndex("fundamento"));
                        if(!cursor.getString(cursor.getColumnIndex("fraccion")).trim().contains("."))
                            articulos += " " + cursor.getString(cursor.getColumnIndex("fraccion")) + ",";
                        else {
                            Log.e("fra",cursor.getString(cursor.getColumnIndex("fraccion")).trim());
                            String fra [] = cursor.getString(cursor.getColumnIndex("fraccion")).trim().split(" ");
                            articulos += " Fracción " + fra[0].substring(0,fra[0].length()-1) + ",";
                        }
                        f=cursor.getString(cursor.getColumnIndex("fundamento"));
                    } while(cursor.moveToNext());
                    articulos = articulos.substring(0,articulos.length()-1) + " del Reglamento de Construcción para el Municipio de Zapopan, Jalisco";
                    //Log.e("articulos",articulos);
                    etArticulo.setText(articulos);
                }
            } catch (SQLiteException e) {
                Log.e("ERROR",e.getMessage() + " Mensaje");
            } finally {
                cursor.close();
                db.close();
            }
        }
    }

    public void getMeConstitui() {
        GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarDB.getReadableDatabase();
        if(db != null) {
            String sql = "select * from c_me_constitui";
            System.err.println(sql);
            Cursor cursor = db.rawQuery(sql, null);
            try {
                meConstitui.clear();
                if(cursor.moveToFirst()){
                    do {
                        meConstitui.add(cursor.getString(1));
                    }while (cursor.moveToNext());
                }
            }catch (SQLiteException e) {
                Log.e("error",e.getMessage() + "");
            }finally {
                db.close();
                cursor.close();
                adapterMeC.notifyDataSetChanged();
            }
        }
    }
    
    public void competencia() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if(db != null) {
            Cursor cursor;
    	    if(id==5 ) {
                cursor = db.rawQuery("select * from C_ordenamiento ", null);
            }else {
                cursor = db.rawQuery("select * from C_ordenamiento where id_c_direccion = " + id, null);
            }

                try {
				if(cursor.moveToFirst()) {
					reglamento.add("");
					competencia.add("");
					idCompetencia.add(0);
					campoReg.add("");
					do {
					   // Log.e("reglamento",cursor.getString(1).trim());
						reglamento.add(cursor.getString(1).trim());
						competencia.add(cursor.getString(cursor.getColumnIndex("competencia")));
						idCompetencia.add(cursor.getInt(0));
						campoReg.add(cursor.getString(2));
					} while (cursor.moveToNext());
				}
			} catch (SQLiteException e) {
				System.out.println(e.getMessage());
			}finally{
				cursor.close();
				db.close();
			}
    	}
    }



    public void conceptosOV() {
	    //select*from concepto_ov
        GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarDB.getReadableDatabase();
        if(db != null) {
            Cursor cursor = db.rawQuery("select * from concepto_ov where id_c_direccion = " + id, null);
            try {
                if(cursor.moveToFirst()) {
                    conceptos.clear();
                    fraccion.clear();
                    articulo.clear();
                    do {
                        conceptos.add(cursor.getString(2));
                         fraccion.add(cursor.getString(4));
                         articulo.add(cursor.getString(3));
                    } while (cursor.moveToNext());
                }
            } catch (SQLiteException e) {
                System.out.println(e.getMessage());
            }finally{
                cursor.close();
                db.close();
            }
        }
    }
    
    public void manifiesta() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if (db != null) {
    		System.err.println(" visitado m ");
            String sql;
    		if(id == 5)
    		    sql = "select * from C_visitado_manifiesta where id_c_direccion = " + 2 + " order by manifiesta";
    		else
                sql = "select * from C_visitado_manifiesta where id_c_direccion = " + id + " order by manifiesta";
    		Log.i("sql manidiesta ",sql);
    		//Cursor c = db.query("C_visitado_manifiesta", null, "id_c_direccion = " + id, null, null, null, "manifiesta");
            Cursor c = db.rawQuery(sql,null);
    		if (c.moveToFirst()){
    			do {
					vManifiesta.add(c.getString(2));
				} while (c.moveToNext());
    			vManifiesta.add("");
    		}
    		c.close();
    	}
    	db.close();
    }
    
    public void identifica() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if (db != null) {
    	    Cursor c;
    	    if(id == 4)
    	        c = db.query("C_visitado_identifica", null, "id_c_direccion = '" + 4 + "'", null, null, null, "identificacion");
    	    else
                c = db.query("C_visitado_identifica", null, "id_c_direccion = '" + 2 + "'", null, null, null, "identificacion");
    		if (c.moveToFirst()){
    			do {
					vIdentifica.add(c.getString(2));
				} while (c.moveToNext());
    			vIdentifica.add("");
    		}
    		c.close();
    	}
    	db.close();
    }
    
    public void usoSuelo() {
    	GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	if (db != null) {
    	    String sql = "select * from C_uso_suelo where uso_suelo like '%**%'";
    		//Cursor c = db.query("C_uso_suelo", null, "id_c_uso_suelo", null, null, null, "uso_suelo");
            Cursor c = db.rawQuery(sql,null);
            usoSuelo.add("");
    		if (c.moveToFirst()){
    			do {
					usoSuelo.add(c.getString(2));
				} while (c.moveToNext());

    		}
    		c.close();
    	}
    	db.close();
    }
    public void C_giro(){
        GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarDB.getReadableDatabase();
        if (db != null) {
            Cursor c;
            String sql="select * from c_giro2 order by giro ";
            //Log.i("entro",sql);
            c=db.rawQuery(sql,null);
            giros.clear();
            giros.add("");
            if (c.moveToFirst()){
                do {
                    //Log.i(TAG, "C_giro: entro");
                    //Log.i(TAG, "C_giro: "+c.getString(1));
                    giros.add(c.getString(1));
                } while (c.moveToNext());

            }
            c.close();
        }
        db.close();
    }

    public void usoSueloH(String uso) {
        GestionBD gestionarDB = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarDB.getReadableDatabase();
        String u = "";
        if (db != null) {
            if(uso.contains("Habitacional"))
                u = "'H1','H2','H3','H4'";
            else if (uso.contains("Mixto"))
                u = "'MB','MD','MC'";
            else if(uso.contains("Comercial") | spuso.getSelectedItem().toString().contains("CSB") | spuso.getSelectedItem().toString().contains("CSD") | spuso.getSelectedItem().toString().contains("CSC") | spuso.getSelectedItem().toString().contains("CSR") | spuso.getSelectedItem().toString().contains("SI"))
                u = "'CSV','CSB','CSD','CSC','CSR','SI'";
            else if(uso.contains("Industrial"))
                u = "'I1','I2','I3'";
            else
                u = "";
            String sql = "select * from C_uso_suelo where uso_suelo in("+u+")";
            Log.e("uso H",sql);
            //Cursor c = db.query("C_uso_suelo", null, "id_c_uso_suelo", null, null, null, "uso_suelo");
            Cursor c = db.rawQuery(sql,null);
            usoSueloH.clear();
            if (c.moveToFirst()){
                do {
                    usoSueloH.add(c.getString(2));
                } while (c.moveToNext());
            }
            c.close();
        }
        usoSueloH.add("");
        db.close();
        adapterUso.notifyDataSetChanged();
    }
    
    public void foto () {
    	GestionBD gestion = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestion.getReadableDatabase();
    	try {
			if (db != null) {
				Cursor c = db.query("Fotografia", null, "id_fotografia", null, null, null, null);
				if (c.moveToFirst()){
					do {
						Log.i("FOTOGRAFIA", c.getInt(0) + " " + c.getInt(1) + " " + c.getString(2) + " " + c.getString(3) + " " + c.getString(4));
					} while (c.moveToNext());
				}
				c.close();
			}
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
    	finally {
    		db.close();
    	}
    }
    public int validarFoto(int numero_acta) {
        GestionBD gestionarBD = new GestionBD(this, "inspeccion", null, 1);
        SQLiteDatabase db = gestionarBD.getWritableDatabase();
        int bandera = 0;
        if (db != null) {
            try {
                //validar no se guarde doble el pdf
                Cursor c = db.rawQuery("SELECT * FROM Fotografia where numero_acta='" + numero_acta+"' and descripcion='PDF'", null);
                if (c.moveToFirst()) {
                    do {
                        bandera = c.getInt(1);
                        ///Log.i("FOTOGRAFIA", c.getInt(0) + " " + c.getInt(1) + " " + c.getString(2) + " " + c.getString(3) + " " + c.getString(4));
                    } while (c.moveToNext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                db.close();
            }
            Log.i("banderafoto: ", String.valueOf(bandera));
        }
        return bandera;
    }
    public long insertFotrografia(int levantamiento, String numeroActa,String archivo, String descripcion,String com,String estatus) {
    	long n = 0;
    	GestionBD gestionarBD = new GestionBD(this, "inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getWritableDatabase();
    	int bandera=0;
    	if (db != null) {
    		try {
                ContentValues cv = new ContentValues();
                cv.put("id_levantamiento", levantamiento);
                cv.put("numero_acta", numeroActa);
                cv.put("archivo", archivo);
                cv.put("descripcion", descripcion);
                cv.put("enviado", com);
                cv.put("estatus", estatus);
                n = db.insert("Fotografia", null, cv);

    		}catch (SQLiteException e) {
    			Log.e("ERROR en:", e.getMessage());
    		}
    		finally {
    			db.close();
    		}
    	}
    	return n;
    }
    
    public long ingresarDetalleInfraccion(int idLevantamiento,String numeroActa,int idInfraccion, float cantidad,String estatus, String unidad, String especificacion) {
     
    	long n = 0;
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getWritableDatabase();

    	if(db != null) {

    		ContentValues cv = new ContentValues();
    		cv.put("id_levantamiento", idLevantamiento);
    		cv.put("numero_acta", numeroActa);
    		cv.put("id_c_infraccion", idInfraccion);
    		cv.put("cantidad",cantidad);
    		cv.put("estatus", estatus);
    		cv.put("unidad",unidad);
    		cv.put("especificacion",especificacion);

    		n = db.insert("Detalle_infraccion", null, cv);
    	}
    	db.close();
    	return n;
    }

    public long ingresarSeguimientoM(String numeroActa,String idinspector,String medida_precautoria,String fecha){
        long n = 0;
        GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarBD.getWritableDatabase();

        if(db != null) {
            ContentValues cv = new ContentValues();
            //cv.put("id_MedidaS",id_MedidaS);
            cv.put("numero_acta", numeroActa);
            cv.put("id_inspector", idinspector);
            cv.put("medida_precautoria", medida_precautoria);
            cv.put("fecha",fecha);


            n = db.insert("SeguimientoM", null, cv);
        }
        db.close();

        return n;
    }
    
    public void validarFecha(){
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	
    	try {
			if(db != null){
				Cursor c = db.rawQuery("Select * from Levantamiento ORDER BY id_levantamiento DESC LIMIT 0,1", null);
				//Cursor cursor = db.query("Levantamiento", null, "id_levantamiento", null, null, null, "id_levantamiento DESC", "0,1");
				if(c.moveToFirst()) {
					do {
						c_fecha = c.getString(6);
					} while (c.moveToNext());
				}
				c.close();
			}
		} catch (Exception e) {
			
		}
    	finally {
    		db.close();
    	}
    }
    
    public String consultarFoto(){
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	String str = "";
    	try {
			if(db != null){
				Cursor c  = db.query("Fotografia", null, "id_levantamiento", null, null, null, null);
				if(c.moveToFirst()){
					do {
						Log.i("RESULT", "Id: " + c.getInt(0) + " levantamiento: " + c.getInt(1) + " No Acta: " + c.getString(2) + " Archivo: " + c.getString(3) + " descripcion: " + c.getString(4));
						str = "Id: " + c.getInt(0) + " levantamiento: " + c.getInt(1) + " No Acta: " + c.getString(2) + " Archivo: " + c.getString(3) + " descripcion: " + c.getString(4);
					} while (c.moveToNext());
				}
			}
		} catch (SQLiteException e) {
			Log.e("ERROR", "en: " + e.getMessage());
		}
    	finally {
    		db.close();
    	}
    	return str;
    }
    
    public boolean consultarLevantamiento(){
    	boolean r = false;
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	consultar.add("");
    	
    	try {
			if(db != null){
				//Cursor c  = db.query("Levantamiento", null, "id_levantamiento", null, null, null, null);
				Cursor c = db.rawQuery("select * from Levantamiento where id_c_direccion = '" + id + "' and infraccion='"+infrac+"'", null);
				if(c.moveToFirst()){
					do {
						r = true;
						consultar.add(c.getString(1));
					} while (c.moveToNext());
				}
			}
		} catch (SQLiteException e) {
			
		}
    	finally {
    		db.close();
    	}
    	return r;
    }
    
    public String consultarUsuario(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
				Cursor c = db.rawQuery("SELECT nombre FROM C_Inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						System.err.println(nombre);
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public String consultarUsuario1(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
    			id_i2.clear();
				Cursor c = db.rawQuery("SELECT nombre,id_c_inspector FROM C_inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						System.err.println(nombre);
						id_i2.add(c.getInt(1));
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public String consultarUsuario2(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
    			id_i3.clear();
				Cursor c = db.rawQuery("SELECT nombre,id_c_inspector FROM C_Inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						System.err.println(nombre);
						id_i3.add(c.getInt(1));
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public String consultarUsuario3(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
    			id_i4.clear();
				Cursor c = db.rawQuery("SELECT nombre,id_c_inspector FROM C_Inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						System.err.println(nombre);
						id_i4.add(c.getInt(1));
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public String consultarUsuario4(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
    			id_i5.clear();
				Cursor c = db.rawQuery("SELECT nombre,id_c_inspector FROM C_Inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						id_i5.add(c.getInt(1));
						System.err.println(nombre);
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public String consultarUsuario5(int idinspector) {
    	String nombre = "";
    	GestionBD gestionBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	if(db != null) {
    		try {
    			id_i6.clear();
				Cursor c = db.rawQuery("SELECT nombre,id_c_inspector FROM C_Inspector WHERE id_c_inspector = '" + idinspector + "' and trim(vigente) = 'S'" , null);
				if (c.moveToFirst()) {
					do {
						nombre = c.getString(0);
						id_i6.add(c.getInt(1));
						System.err.println(nombre);
					} while (c.moveToNext());
				}
				c.close();
			} catch (Exception e) {
				
			}
    	}
    	db.close();
    	return nombre;
    }
    
    public void consultarLevantamiento(String numero_acta) {
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	
    	if(db != null) {
    		try {
    			ArrayList<String> array = new ArrayList<String>();
    			ArrayList<String> nombreI1 = new ArrayList<String>();
    			ArrayList<String> nombreI2 = new ArrayList<String>();
    			ArrayList<String> nombreI3 = new ArrayList<String>();
    			ArrayList<String> nombreI4 = new ArrayList<String>();
    			ArrayList<String> nombreI5 = new ArrayList<String>();
    			ArrayList<String> nombreI6 = new ArrayList<String>();
    			ArrayList<String> identifica = new ArrayList<String>();
    			ArrayList<String> manifiesta = new ArrayList<String>();
    			ArrayList<String> designado = new ArrayList<String>();
    			ArrayList<String> designado1 = new ArrayList<String>();
    			ArrayList<String> uso = new ArrayList<String>();
    			ArrayList<String> gravedad = new ArrayList<String>();
    			
				Cursor c = db.rawQuery("SELECT * FROM Levantamiento WHERE numero_acta = '" + numero_acta + "'", null);
				
				if(c.moveToFirst()) {
					for (int i = 0; i < c.getColumnCount(); i++) {
						System.out.println(c.getColumnName(i) + " " + i);
						//System.out.println("hola");
					}
					do {
						
						spInfraccion.setEnabled(false);
						btnVisualizar.setEnabled(false);
						Log.i("SI", "SI ENTRO " + c.getString(10));
						etVIdentifica.setEnabled(false);
						etVManifiesta.setEnabled(false);
						array.add(c.getString(12));
						identifica.add(c.getString(16));
						manifiesta.add(c.getString(17));
						designado.add(c.getString(27));
						designado1.add(c.getString(30));
						uso.add(c.getString(35));
						gravedad.add(c.getString(38));
						nombreI1.add(consultarUsuario(c.getInt(13)));
						nombreI2.add(consultarUsuario(c.getInt(14)));
						
						if(c.getInt(c.getColumnIndex("id_c_inspector3")) > 0) 
							nombreI3.add(consultarUsuario(c.getInt(c.getColumnIndex("id_c_inspector3"))));
						if(c.getInt(c.getColumnIndex("id_c_inspector4")) > 0) 
							nombreI4.add(consultarUsuario(c.getInt(c.getColumnIndex("id_c_inspector4"))));
						if(c.getInt(c.getColumnIndex("id_c_inspector5")) > 0) 
							nombreI5.add(consultarUsuario(c.getInt(c.getColumnIndex("id_c_inspector5"))));
						if(c.getInt(c.getColumnIndex("id_c_inspector6")) > 0) 
							nombreI6.add(consultarUsuario(c.getInt(c.getColumnIndex("id_c_inspector6"))));
						
						hora = c.getString(7);
						fecha = c.getString(6);
						hr = c.getString(41);
						
						spnombre.setAdapter(null);
						spnombre.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI1));
						spnombre.setEnabled(false);
						spNombreA.setAdapter(null);
						spNombreA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI2));
						spNombreA.setEnabled(false);
						
						spNombreA1.setAdapter(null);
						spNombreA1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI3));
						spNombreA1.setEnabled(false);
						spNombreA2.setAdapter(null);
						spNombreA2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI4));
						spNombreA2.setEnabled(false);
						
						spNombreA3.setAdapter(null);
						spNombreA3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI5));
						spNombreA3.setEnabled(false);
						spNombreA4.setAdapter(null);
						spNombreA4.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nombreI6));
						spNombreA4.setEnabled(false);
						
						
						etLongitud.setText(c.getString(8));
						etLongitud.setEnabled(false);
						etLatitud.setText(c.getString(9));
						etLatitud.setEnabled(false);
						etNum.setText(c.getString(10));
						orde = c.getString(10);
						etNum.setEnabled(false);
						etNumeroActa.setText(c.getString(1));
						etCitatorio.setText(c.getString(2));
						etCitatorio.setEnabled(false);
						spZona.setAdapter(null);
						spZona.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array));
						spZona.setEnabled(false);
						etFecham.setText(c.getString(6));
						etFecham.setEnabled(false);
						etNombreV.setText(c.getString(15));
						etNombreV.setEnabled(false);
                        //etNumeroActa.setText(numero_acta);
						spIdentifica.setAdapter(null);
						spIdentifica.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,identifica));
						spIdentifica.setEnabled(false);
						spManifiesta.setAdapter(null);
						spManifiesta.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, manifiesta));
						spManifiesta.setEnabled(false);
						etFraccionamiento.setText(c.getString(18));
						etFraccionamiento.setEnabled(false);
						etCalle.setText(c.getString(19));
						etCalle.setEnabled(false);
						etNumero.setText(c.getString(20));
						etNumero.setEnabled(false);
						etNuemroInterior.setText(c.getString(21));
						etNuemroInterior.setEnabled(false);
						etApellidoP.setText(c.getString(22));
						etApellidoP.setEnabled(false);
						etApellidoM.setText(c.getString(23));
						etApellidoM.setEnabled(false);
						etPropietario.setText(c.getString(24));
						etPropietario.setEnabled(false);
						spdesignado.setAdapter(null);
						spdesignado.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,designado));
						spdesignado.setEnabled(false);
						etNombreT1.setText(c.getString(28));
						etNombreT1.setEnabled(false);
						etIfeT2.setText(c.getString(29));
						etIfeT2.setEnabled(false);
						spdesignado1.setAdapter(null);
						spdesignado1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,designado1));
						spdesignado1.setEnabled(false);
						etSeleccion.setText(c.getString(32));
						etSeleccion.setEnabled(false);
						etInfraccion.setText(c.getString(33));
						etInfraccion.setEnabled(false);
						spuso.setAdapter(null);
						spuso.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,uso));
						spuso.setEnabled(false);
						/*aqui etDensidad.setText(c.getString(36));
						etDensidad.setEnabled(false);aqui*/
						etManifiesta.setText(c.getString(37));
						etManifiesta.setEnabled(false);
						spgravedad.setAdapter(null);
						spgravedad.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,gravedad));
						spgravedad.setEnabled(false);
						etDiaPlazo.setText(c.getString(39));
						etDiaPlazo.setEnabled(false);
						etfecha.setText(c.getString(40));
						etfecha.setEnabled(false);
						etNombreT.setText(c.getString(25));
						etNombreT.setEnabled(false);
						etIfeT.setText(c.getString(26));
						etCondominio.setText(c.getString(47));
						etIfeT.setEnabled(false);
						spIdentificaT.setEnabled(false);
						spIdentificaT1.setEnabled(false);
						etIfeA.setText(c.getString(29));
						ifeA = c.getString(29);
						etIfeA.setEnabled(false);
						etNoA.setEnabled(false);
						etVigA.setEnabled(false);
						etLote.setText(c.getString(49));
						etManzana.setText(c.getString(48));
						
						
						etResponsable.setText(c.getString(c.getColumnIndex("responsable_obra")));
						etRegistro.setText(c.getString(c.getColumnIndex("registro_responsable")));
						etConstruccion.setText(c.getString(c.getColumnIndex("l_construccion")));
						etEntreC.setText(c.getString(c.getColumnIndex("entre_calle1")));
						etEntreC1.setText(c.getString(c.getColumnIndex("entre_calle2")));
						etMedida.setText(c.getString(c.getColumnIndex("medida_seguridad")));
						etArticulo.setText(c.getString(c.getColumnIndex("articulo_medida")));
						
						etInspccionFue.setText(c.getString(c.getColumnIndex("peticion")));
						etMotivo.setText(c.getString(c.getColumnIndex("motivo_orden")));
						etMedida.setText(c.getString(c.getColumnIndex("medida_seguridad")));
						etArticulo.setText(c.getString(c.getColumnIndex("articulo_medida")));
                        etfolioap.setText(c.getString(c.getColumnIndex("folio_apercibimiento")));
                        etfechap.setText(c.getString(c.getColumnIndex("fecha_apercibimiento")));
                        etReferencia.setText(c.getString(c.getColumnIndex("referencia")));
						
						
						if (c.getString(c.getColumnIndex("v_firma")) != null) {
							System.out.println(c.getString(c.getColumnIndex("v_firma")) + " FIRMA");
							System.out.println(c.getString(c.getColumnIndex("v_firma")).equalsIgnoreCase("si") + " FIRMA");
							if(c.getString(c.getColumnIndex("v_firma")).equalsIgnoreCase("si"))
								cbFirma.setChecked(true);
							else
								cbFirma.setChecked(false);
						} else {
							cbFirma.setChecked(false);
						}
						
						etInspccionFue.setText(c.getString(c.getColumnIndex("peticion")));
						
						
						cbFirma.setEnabled(false);
						etfechap.setEnabled(false);
						etfolioap.setEnabled(false);
						spNombreA1.setEnabled(false);
						spNombreA2.setEnabled(false);
						spNombreA3.setEnabled(false);
						spNombreA4.setEnabled(false);
						
						etMotivo.setEnabled(false);
						etInspccionFue.setEnabled(false);
						
						spPeticion.setEnabled(false);
						
						
						
						
						//etReferencia.setText(c.getString(c.getColumnIndex("referencia")));
						System.out.println(c.getString(49) + " " + c.getColumnName(49));
						//Log.e("lote ", c.getString(50) + " " + c.getColumnName(50));
						Log.i("ifee", c.getString(26));
						
					} while (c.moveToNext());
					c.close();
				}
			} catch (SQLiteException e) {
				Log.e("error bd", e.getMessage().toString() + " marco nulo");
			}
    		finally {
    			db.close();
    		}
    	}
    }
    
    public int validar(){
    	int r = 0;
    	r += (etdato.getVisibility() == View.VISIBLE && validarCampos(this.etdato)) ?  1 : 0;
    	r += (etdato1.getVisibility() == View.VISIBLE && validarCampos(this.etdato1)) ? 1 : 0;
    	r += (etdato2.getVisibility() == View.VISIBLE && validarCampos(this.etdato2)) ? 1 : 0;
    	r += (etdato3.getVisibility() == View.VISIBLE && validarCampos(this.etdato3)) ? 1 : 0;
    	r += (etdato4.getVisibility() == View.VISIBLE && validarCampos(this.etdato4)) ? 1 : 0;
    	Log.i("r", String.valueOf(r));
    	return r;
    	//return 0;
    }
    
    public static boolean validarCampos(EditText et){
		return((et == null) || (et.getText().toString() == null) || et.getText().toString().equalsIgnoreCase(""));
	}
    
    public boolean validarSpinner(Spinner sp){
    	return(/*(sp.getSelectedItem().toString() == null) ||*/ (sp.getSelectedItem() == null) || sp.getSelectedItem().toString().equals(""));
    }
    
    protected boolean validarI() {
    	System.err.println(infrac);
    	boolean valid = true;
    	StringBuffer sb = new StringBuffer();
    	sb.append("Los siguientes campos son requeridos: \n");
    	if(infrac == 1) {


            if (foto == 0) {
                    sb.append("Falta tomar Fotografia. \n");
                    valid = false;
                }

            /*if(validarCampos(this.etMedida)){
                sb.append("Ingrese las medidas precautorias. \n");
                valid=false;
            }
            if(validarCampos(this.etArticulo)){
                sb.append("Ingrese las medidas precautorias. \n");
                valid=false;
            }*/
            if (validarCampos(this.etFraccionamiento)) {
                sb.append("Ingrese el fraccionamiento. \n");
                valid = false;
            }
            if (validarCampos(this.etCalle)) {
                sb.append("Ingrese la calle. \n");
                valid = false;
            }
            if (this.id == 3) {
                if(validarCampos(this.etMedida)){
                    sb.append("Ingrese las medidas precautorias. \n");
                    valid=false;
                }
                if(validarCampos(this.etArticulo)){
                    sb.append("Ingrese las medidas precautorias. \n");
                    valid=false;
                }
                if (validarCampos(this.etPropietario)) {
                    sb.append("Ingrese el propietario o razón social.\n");
                    valid = false;
                }

            }
	    	if(validarCampos(this.etNombreT)){
	    		sb.append("Ingrese el nombre del primer testigo. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etIfeT)){
	    		sb.append("Ingrese el ife del primer testigo. \n");
	    		valid = false;
	    	}

	    	if (validarCampos(this.etManifiesta)) {
	    	    sb.append("Ingrese lo que el visitado manifiesta. \n");
	    	    valid = false;
	    	}

	    	if(validarCampos(this.etDiaPlazo)) {
	    		sb.append("Ingrese los dias de plazo. \n");
	    		valid = false;
	    	}



            if(!cbDatos.isChecked()) {
                if (validarCampos(this.etNombreV)) {
                    sb.append("Ingrese en nombre del visitado. \n");
                    valid = false;
                }
                if(!spIdentifica.getSelectedItem().toString().trim().equalsIgnoreCase("No se identifica")) {
                    if (validarCampos(this.etVIdentifica)) {
                        sb.append("Ingrese la identificacion del visitado. \n");
                        valid = false;
                    }
                }
            }
            if(!cbDatos2.isChecked()) {
                if (validarCampos(this.etNombreV)) {
                    sb.append("Ingrese en nombre del visitado. \n");
                    valid = false;
                }
                if(!spIdentifica.getSelectedItem().toString().trim().equalsIgnoreCase("No se identifica")) {
                    if (validarCampos(this.etVIdentifica)) {
                        sb.append("Ingrese la identificacion del visitado. \n");
                        valid = false;
                    }
                }
            }


            if(id==5){
                if (validarCampos(this.etGiro)) {
                    sb.append("Ingrese el giro de donde se actua \n");
                    valid = false;
                }
                if(validarCampos(this.etMedida)){
                    sb.append("Ingrese las medidas precautorias. \n");
                    valid=false;
                }
                if(validarCampos(this.etArticulo)){
                    sb.append("Ingrese las medidas precautorias. \n");
                    valid=false;
                }
            }
            //Validar numero OV

            //if(et)
            if(etOrden1.getText().toString().length()>1){
                if(numeroOV.length()<1){
                    sb.append("Presione buscar para vincular la Orden de Visita \n");
                    valid = false;
                }
            }
            if(etfechaOV.getText().toString().length()>1){
                if(numeroOV.length()<1){
                    sb.append("Agregue el Folio de la Orden de Visita y Presione Buscar \n");
                    valid = false;
                }

                if(etfechaOV.length()<10){
                    sb.append("El formato de fecha es invalido  \n"+etfechaOV.length());
                    valid = false;
                }
            }

            if(id == 2 | id == 4) {
                if (medidas1.trim().contains("Clausura") | medidas1.trim().contains("clausura")) {
                    if (etNumeroSellos.getText().toString().trim().equalsIgnoreCase("")) {
                        sb.append("Ingrese los numeros de sellos. \n");
                        valid = false;
                    }
                }
                if(validarCampos(this.etMedida)){
                    sb.append("Ingrese las medidas precautorias. \n");
                    valid=false;
                }
                if(validarCampos(this.etArticulo)){
                    sb.append("Ingrese los articulos de las medidas. \n");
                    valid=false;
                }




            }
            if(validarSpinner(this.spPeticion)) {
                sb.append("Ingrese a petición de. \n");
                valid = false;
            }
            if(validarCampos(this.etNombreT)) {
                sb.append("Ingrese el nombre del primer testigo. \n");
                valid = false;
            }
	    	if(validarCampos(this.etVManifiesta)){
	    		sb.append("Ingrese como se manifiesta el visitado. \n");
	    		valid = false;
	    	}
	    	if(validarSpinner(this.spdesignado)){
	    		sb.append("Seleccione por quien fue designado el primer testigo. \n");
	    		valid = false;
	    	}
	    	if(validarSpinner(this.spdesignado1)){
	    		sb.append("Seleccione por quien fue designado el segundo testigo. \n");
	    		valid = false;
	    	}
	    	/*if(validarSpinner(this.spuso)){
	    		sb.append("Seleccione el tipo de suelo. \n");
	    		valid = false;
	    	}*/
	    	if(validarSpinner(this.spgravedad)){
	    		sb.append("Seleccione la gravedad \n");
	    		valid = false;
	    	}
            if(validarSpinner(this.spNE)){
                sb.append("Seleccione el Nivel Economico \n");
                valid = false;
            }
            if(id == 2) {
                if (validarCampos(this.etDondeActua)) {
                    sb.append("Ingrese me constituyo \n");
                    valid = false;
                }
                if (validarCampos(this.etGiro)) {
                    sb.append("Ingrese el giro de donde se actua \n");
                    valid = false;
                }
                if(validarCampos(this.etMedida)){
                    sb.append("Ingrese las medidas precautorias. \n");
                    valid=false;
                }
                if(validarCampos(this.etArticulo)){
                    sb.append("Ingrese las medidas precautorias. \n");
                    valid=false;
                }


            }
    	}
    	else {
    	    if(id != 4) {
                if (!cbDatos.isChecked()) {
                    if (validarCampos(this.etNombreV)) {
                        sb.append("Ingrese el nombre del visitado. \n");
                        valid = false;
                    }
                    if (!spIdentifica.getSelectedItem().toString().trim().equalsIgnoreCase("No se identifica")) {
                        if (validarCampos(this.etVIdentifica)) {
                            sb.append("Ingrese la identificacion del visitado. \n");
                            valid = false;
                        }
                    }

                }
                if (!cbDatos2.isChecked()) {
                    if (validarCampos(this.etNombreV)) {
                        sb.append("Ingrese el nombre del visitado. \n");
                        valid = false;
                    }
                    if (!spIdentifica.getSelectedItem().toString().trim().equalsIgnoreCase("No se identifica")) {
                        if (validarCampos(this.etVIdentifica)) {
                            sb.append("Ingrese la identificacion del visitado. \n");
                            valid = false;
                        }
                    }

                }

            }
	    	if(validarCampos(this.etFraccionamiento)){
	    		sb.append("Ingrese el fraccionamiento. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etCalle)){
	    		sb.append("Ingrese la calle. \n");
	    		valid = false;
	    	}
	    	if(validarCampos(this.etDiaPlazo)) {
	    		sb.append("Ingrese los dias de plazo. \n");
	    		valid = false;
	    	}
	    	/*if(validarCampos(this.etVIdentifica)){
	    		sb.append("Ingrese la identificacion del visitado. \n");
	    		valid = false;
	    	}*/


    	}
    	if(ante=="OV"){
    	    if(validarSpinner(this.spNombreA)){
                sb.append("Ingrese un acompañante \n");
                valid = false;
            }
    	    if(id==4){
                if(etfolioclau.isShown()){
                    if(validarCampos(this.etfolioclau)){
                        sb.append("Ingrese folio de clausura. \n");
                        valid=false;
                    }
                    if(etfechaClau.getText().toString().length()>=10) {
                        if (!etfechaClau.getText().toString().contains("-")) {
                            sb.append("La fecha no esta en el formato especificado. \n");
                            valid = false;
                        }
                        if (validarCampos(this.etfechaClau)) {
                            sb.append("Ingrese fecha de clausura. \n");
                            valid = false;
                        }
                    }else{
                        sb.append("No ingreso completamente la fecha de clausura. \n");
                        valid = false;
                    }

                }
                if(etfolioap.isShown()){

                    if(validarCampos(this.etfolioap)){
                        sb.append("Ingrese folio de apercibimiento. \n");

                        valid=false;
                    }
                    if(etfechap.getText().toString().length()>=10) {

                        if (!etfechap.getText().toString().contains("-")) {
                            sb.append("La fecha no esta en el formato especificado. \n");
                            valid = false;
                        }
                        if (validarCampos(this.etfechap)) {
                            sb.append("Ingrese fecha de apercibimiento. \n");
                            valid = false;
                        }
                    }else{
                        sb.append("No ingreso completamente la fecha de apercibimiento. \n");
                        valid = false;
                    }
                }
                //validacion
                //Log.i("cantidad de reg", String.valueOf(conceptos.size()));
                String art = "";
                int x = 1;

                for (int i = 0; i < reg.length; i++) {
                    if (reg[i] > 0) {


                        if(fraccion.get(i).length()>0) {
                            art += articulo.get(i) + " Fracción " + fraccion.get(i) + ",";
                            x += 1;
                        }else{
                            art += articulo.get(i) + ",";
                            x += 1;
                        }
                    }
                }
               // art = art.substring(0, art.length() - 1);
                if(art.length()<1){
                    sb.append("Seleccione un alcance de la OV \n");
                    valid=false;
                }




            }
        }
    	
    	if(!valid){
    		showMsg(sb.toString());
    	}
    	return valid;
    }
    
    public String ordenar(String var){
    	hs.clear();
    	array.clear();
    	String[] num = var.split(regex);
		for (int i = 0; i < num.length; i++) {
			array.add(num[i]);
		}
		hs.addAll(array);
		array.clear();
		array.addAll(hs);
		Collections.sort(array);
		var="";
		for (int j = 0; j < array.size(); j++) {
			var += array.get(j) + ","; 
		}
		return var;
    }
    
    public void infraccion(){
    	if(id_infra!=0){
    		id_hechos += id_infra + ",";
    	}
    	if(!c1.trim().equalsIgnoreCase("")) 
			camp1 += c1+",";
		if(!c2.trim().equalsIgnoreCase(""))
			camp2 += c2+",";
		if(!c3.trim().equalsIgnoreCase(""))
			camp3 += c3+",";
		if(!c4.trim().equalsIgnoreCase(""))
			camp4 += c4+",";
		if(!c5.trim().equalsIgnoreCase(""))
			camp5 += c5+",";
		
		if(!c6.trim().equalsIgnoreCase("")) 
			camp6 += c6+",";
		if(!c7.trim().equalsIgnoreCase(""))
			camp7 += c7+",";
		if(!c8.trim().equalsIgnoreCase(""))
			camp8 += c8+",";
		if(!c9.trim().equalsIgnoreCase(""))
			camp9 += c9+",";
		if(!c0.trim().equalsIgnoreCase(""))
			camp0 += c0+",";
		
		if(!c11.trim().equalsIgnoreCase(""))
			camp11 += c11+",";
		if(!c12.trim().equalsIgnoreCase(""))
			camp12 += c12+",";
		if(!c13.trim().equalsIgnoreCase(""))
			camp13 += c13+",";
		if(!c14.trim().equalsIgnoreCase(""))
			camp14 += c14+",";
		
		if(!c15.trim().equalsIgnoreCase(""))
			camp15 += c15+",";
		if(!c16.trim().equalsIgnoreCase(""))
			camp16 += c16+",";
		if(!c17.trim().equalsIgnoreCase(""))
			camp17 += c17+",";
		if(!c18.trim().equalsIgnoreCase(""))
			camp18 += c18+",";
		if(!c19.trim().equalsIgnoreCase(""))
			camp19 += c19+",";
		if(!c20.trim().equalsIgnoreCase(""))
			camp20 += c20+",";
        if(!c21.trim().equalsIgnoreCase(""))
            camp21 += c21+",";
        if(!c22.trim().equalsIgnoreCase(""))
            camp22 += c22+",";
        if(!c23.trim().equalsIgnoreCase(""))
            camp23 += c23+",";
        if(!c24.trim().equalsIgnoreCase(""))
            camp24 += c24+",";
        if(!c25.trim().equalsIgnoreCase(""))
            camp25 += c25+",";
        if(!c26.trim().equalsIgnoreCase(""))
            camp26 += c26+",";

		System.out.println("CAMPOS " + c1 + " " + c2 + " " + c3 + " " + c4 + " "  + c5 + c6 + " " + c7 + " " + c8 + " " + c9 + " "  + c0 + " " + c11 + " " + c12 + " " + c13 + " "  + c14);
		Log.e("valores",camp1);



		
    }
    
    public void listar(){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
        Cursor  c=null;
    	try{
    	    if(id==5) {
                c = db.rawQuery("SELECT * FROM C_inspector WHERE  nombre <> '" + us + "' AND nombre <> 'ADMINISTRADOR' and trim(vigente) = 'S' and clave!='' and folio!='' order by nombre", null);
                Log.e(TAG, "SELECT * FROM C_inspector WHERE  nombre <> '" + us + "' AND nombre <> 'Administrador' and trim(vigente) = 'S' order by nombre" );

            }else {
                c = db.rawQuery("SELECT * FROM C_inspector WHERE id_c_direccion = '" + id + "' AND nombre <> '" + us + "' AND nombre <> 'ADMINISTRADOR' and trim(vigente) = 'S' and clave!='' and folio!='' order by nombre", null);
            }
            //Cursor c = db.query("Inspectores", null, "id_inspector", null, null, null, null);
	    	id_i2.clear();
			id_i3.clear();
			id_i4.clear();
			id_i5.clear();
			id_i6.clear();
	    	arregloLista.add("");
	    	if(c.moveToFirst()){
	    		do{
	    			arregloLista.add(c.getString(1));
	    			id_i2.add(c.getInt(0));
	    			id_i3.add(c.getInt(0));
	    			id_i4.add(c.getInt(0));
	    			id_i5.add(c.getInt(0));
	    			id_i6.add(c.getInt(0));
	    			//Log.i("listadoinspa", "nombre: " + c.getString(1));
	    		}while(c.moveToNext());
	    	}
	    	else{
	    		//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
	    	}
	    	c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    public void listarInf() {
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	Cursor c;
    	try{
    		if(id == 1| id== 3) {
    			Log.i("consulta", "select * from C_infraccion where id_c_direccion = '" + id + "' and trim(vigente) = 'S'  order by infraccion");
    			c = db.rawQuery("select * from C_infraccion where id_c_direccion = '" + id + "' and vigente = 'S'  order by infraccion", null);
    			//c = db.rawQuery("select * from C_infraccion where id_c_direccion = '" + id + "' order by infraccion", null);
    		} else if(id == 5) {
                Log.i("consulta", "select * from C_infraccion where id_c_direccion = '" + 2+ "' and trim(vigente) = 'S' order by id_c_infraccion");
                c = db.rawQuery("select * from C_infraccion where id_c_direccion = '" + 2 + "' and vigente = 'S' order by infraccion", null);
            }
    		else {
    			Log.i("consulta", "select * from C_infraccion where id_c_direccion = '" + id + "' and trim(vigente) = 'S' order by id_c_infraccion");
    			c = db.rawQuery("select * from C_infraccion where id_c_direccion = '" + id + "' and vigente = 'S' order by infraccion", null);
    			//c = db.rawQuery("select * from C_infraccion where id_c_direccion = '" + id + "' order by infraccion", null);
    		}
    		System.out.println("select * from C_infraccion where id_c_direccion = '" + id + "'" + /* and vigente = 'S'*/ " order by id_c_infraccion");
    		
    		System.out.println(c.getCount() + " inf");
    		//Cursor c = db.query("C_infraccion", null, "id_c_infraccion", null, null, null, "infraccion");
    	//Cursor c = db.query("infracciones", null, "id_infraccion", null, null, null, null);
	    	if(c.moveToFirst()){
	    		arregloInfraccion.add("");
                arregloInfraccion1.add("");
	    		do{
	    			//System.out.println(c.getString(2) + " " + c.getString(c.getColumnIndex("id_c_direccion")));
	    			//if(c.getString(c.getColumnIndex("vigente")).trim().equalsIgnoreCase("S")) {
		    			id_hecho.add(c.getInt(0));
		    			if(id!=4) {
		    			    String recorte=c.getString(2);
                            arregloInfraccion.add(c.getString(2).substring(4,recorte.length()));
                            arregloInfraccion1.add(c.getString(2).substring(4,recorte.length()));
                        }else{
                            arregloInfraccion.add(c.getString(2));
                            arregloInfraccion1.add(c.getString(2));
                        }
		    			//System.out.println(c.getString(2) + " do " + c.getString(c.getColumnIndex("id_c_direccion")));
		    			//Log.i("listado", "Infraccion: " + c.getString(2));
	    			//}
	    		}while(c.moveToNext());
	    	}
	    	else{
	    		Toast toast = Toast.makeText(this, "No hay infracciones en la bd", Toast.LENGTH_SHORT);
	    		toast.setGravity(0, 0, 15);
				toast.show();
	    	}
	    	c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}
    	finally {
    		db.close();
    	}
    	
    }
    
    public void buscarIdInspector(String nombre){
    	GestionBD gestionBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	try {
			Cursor c = db.rawQuery("SELECT * FROM C_inspector WHERE nombre like '%" + nombre + "%'", null);
			System.err.println("SELECT * FROM C_inspector WHERE nombre = '" + nombre + "'");
			System.out.println(c.getCount() + " info");
			if(c.moveToFirst()){
				do {
					id_i1.add(c.getInt(0));
					ifeI = c.getString(2);
					clave = c.getString(c.getColumnIndex("clave"));
					folio = c.getString(c.getColumnIndex("folio"));
    				noI = c.getString(c.getColumnIndex("no_empleado"));
    				vigI = c.getString(c.getColumnIndex("vigencia"));
    				fol = c.getString(c.getColumnIndex("folio"));
					Log.i("id", c.getInt(0) + "");
				} while (c.moveToNext());
				c.close();
			}
		} catch (SQLiteException e) {
		}
    	finally {
    		db.close();
    	}
    }
    
    public void buscarIdInspector1(String nombre){
    	GestionBD gestionBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	try {
			Cursor c = db.rawQuery("SELECT * FROM C_inspector WHERE nombre like '%" + nombre + "%'", null);
			System.err.println("SELECT * FROM C_inspector WHERE nombre = '" + nombre + "'");
			System.out.println(c.getCount() + " info");
			if(c.moveToFirst()){
				do {
					id_i3.add(c.getInt(0));
					ifeA1 = c.getString(2);
    				noA1 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA1 = c.getString(c.getColumnIndex("vigencia"));
					Log.i("id", c.getInt(0) + "");
				} while (c.moveToNext());
				c.close();
			}
		} catch (SQLiteException e) {
		}
    	finally {
    		db.close();
    	}
    }
    public void buscarIdInspector2(String nombre){
    	GestionBD gestionBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	try {
			Cursor c = db.rawQuery("SELECT * FROM C_inspector WHERE nombre like '%" + nombre + "%'", null);
			System.err.println("SELECT * FROM C_inspector WHERE nombre = '" + nombre + "'");
			System.out.println(c.getCount() + " info");
			if(c.moveToFirst()){
				do {
					id_i4.add(c.getInt(0));
					ifeA2 = c.getString(2);
    				noA2 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA2 = c.getString(c.getColumnIndex("vigencia"));
					Log.i("id", c.getInt(0) + "");
				} while (c.moveToNext());
				c.close();
			}
		} catch (SQLiteException e) {
		}
    	finally {
    		db.close();
    	}
    }
    public void buscarIdInspector3(String nombre){
    	GestionBD gestionBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	try {
			Cursor c = db.rawQuery("SELECT * FROM C_inspector WHERE nombre like '%" + nombre + "%'", null);
			System.err.println("SELECT * FROM C_inspector WHERE nombre = '" + nombre + "'");
			System.out.println(c.getCount() + " info");
			if(c.moveToFirst()){
				do {
					id_i5.add(c.getInt(0));
					ifeA3 = c.getString(2);
    				noA3 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA3 = c.getString(c.getColumnIndex("vigencia"));
					Log.i("id", c.getInt(0) + "");
				} while (c.moveToNext());
				c.close();
			}
		} catch (SQLiteException e) {
		}
    	finally {
    		db.close();
    	}
    }
    
    public void buscarIdInspector5(String nombre){
    	GestionBD gestionBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
    	SQLiteDatabase db = gestionBD.getReadableDatabase();
    	try {
			Cursor c = db.rawQuery("SELECT * FROM C_inspector WHERE nombre like '%" + nombre + "%'", null);
			System.err.println("SELECT * FROM C_inspector WHERE nombre = '" + nombre + "'");
			System.out.println(c.getCount() + " info");
			if(c.moveToFirst()){
				do {
					id_i6.add(c.getInt(0));
					ifeA4 = c.getString(2);
    				noA4 = c.getString(c.getColumnIndex("no_empleado"));
    				vigA4 = c.getString(c.getColumnIndex("vigencia"));
					Log.i("id", c.getInt(0) + "");
				} while (c.moveToNext());
				c.close();
			}
		} catch (SQLiteException e) {
		}
    	finally {
    		db.close();
    	}
    }
    
    public void buscarInspector(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "'", null);
    		System.out.println("lalalala SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and (trim(vigente) = 'S' or trim(vigente) = 's') lalalalalala");
    		if(c.moveToFirst()){
    			do{
    				ifeI = c.getString(2);
    				noI = c.getString(c.getColumnIndex("no_empleado"));
    				vigI = c.getString(c.getColumnIndex("vigencia"));
                    clave = c.getString(c.getColumnIndex("clave"));
                    folio = c.getString(c.getColumnIndex("folio"));
    				//Log.i("inspector", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
    			}while(c.moveToNext());
    			c.close();
    		}
    		else{
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
    		}
    	}catch (SQLiteException e) {
			Log.i("ERROR FATAL", e.getMessage());
		}
    	finally {
    		db.close();
    	}
    	
    }
    
    public void buscarAcompanante(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
            if(nom.length()>0) {
                Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and trim(vigente) = 'S'", null);
                if (c.moveToFirst()) {
                    do {
                        ifeA = c.getString(2);
                        noA = c.getString(c.getColumnIndex("no_empleado"));
                        vigA = c.getString(c.getColumnIndex("vigencia"));
                       // Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
                        folios.set(0, c.getString(c.getColumnIndex("folio")));
                    } while (c.moveToNext());
                    c.close();
                } else {
                    //Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
                }

                c.close();
            }
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }

    
    public void buscarAcompanante1(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
            if(nom.length()>0) {
                Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and trim(vigente) = 'S'", null);
                if (c.moveToFirst()) {
                    do {
                        ifeA1 = c.getString(2);
                        noA1 = c.getString(c.getColumnIndex("no_empleado"));
                        vigA1 = c.getString(c.getColumnIndex("vigencia"));
                       // Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
                        folios.set(1, c.getString(c.getColumnIndex("folio")));
                    } while (c.moveToNext());
                    c.close();
                } else {
                    //Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
                }
                c.close();
            }
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    public void buscarAcompanante2(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
            if(nom.length()>0) {
                Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and trim(vigente) = 'S'", null);
                if (c.moveToFirst()) {
                    do {
                        ifeA2 = c.getString(2);
                        noA2 = c.getString(c.getColumnIndex("no_empleado"));
                        vigA2 = c.getString(c.getColumnIndex("vigencia"));
                       // Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
                        folios.set(2, c.getString(c.getColumnIndex("folio")));
                    } while (c.moveToNext());
                    c.close();
                } else {
                    //Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
                }
                c.close();
            }
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    public void buscarAcompanante3(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
            if(nom.length()>0) {
                Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and trim(vigente) = 'S'", null);
                if (c.moveToFirst()) {
                    do {
                        ifeA3 = c.getString(2);
                        noA3 = c.getString(c.getColumnIndex("no_empleado"));
                        vigA3 = c.getString(c.getColumnIndex("vigencia"));
                        //Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
                        folios.set(3, c.getString(c.getColumnIndex("folio")));
                    } while (c.moveToNext());
                    c.close();
                } else {
                    //Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
                }
                c.close();
            }
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    public void buscarAcompanante4(String nom){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
            if(nom.length()>0) {
                Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + nom + "' and trim(vigente) = 'S'", null);
                if (c.moveToFirst()) {
                    do {
                        ifeA4 = c.getString(2);
                        noA4 = c.getString(c.getColumnIndex("no_empleado"));
                        vigA4 = c.getString(c.getColumnIndex("vigencia"));
                      //  Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
                        folios.set(4, c.getString(c.getColumnIndex("folio")));
                    } while (c.moveToNext());
                    c.close();
                } else {
                    //Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
                }
                c.close();
            }
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }
    
    
    public void buscarInfraccion(String nom){
        System.out.println(this.reglamentoSP);
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	String cam = "";
    	int contadorgg=0;
    	
    	try{
    	Cursor c = db.rawQuery("SELECT * FROM C_infraccion WHERE vigente='S' and infraccion like '%" + nom + "%'", null);
    	Log.v("sql","SELECT * FROM C_infraccion WHERE infraccion like '" + nom + "%'");
    	if(c.moveToFirst()) {
            do {
                contadorgg++;
                id_infra = c.getInt(0);
                unidad = c.getString(3);


                for (int i = 0; i < campo.size(); i++) {
                    if (!campo.get(i).equalsIgnoreCase("")) {
                        System.out.println("-entro" + c.getColumnIndex(campo.get(i)));
                        if (c.getColumnIndex(campo.get(i)) >= 0) {
                            System.err.println("-entro" + c.getString(c.getColumnIndex(campo.get(i))));
                            if (i == 0) {
                                c1 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);

                            } else if (i == 1) {
                                c2 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 2) {
                                c3 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 3) {
                                c4 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 4) {
                                cam = campo.get(i);
                                c5 = c.getString(c.getColumnIndex(campo.get(i)));
                            } else if (i == 5) {
                                c6 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 6) {
                                c7 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 7) {
                                c8 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 8) {
                                c9 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 9) {
                                c0 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 10) {
                                c11 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 11) {
                                c12 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 12) {
                                c13 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 13) {
                                c14 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 14) {
                                c15 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 15) {
                                cam = c.getString(c.getColumnIndex(campo.get(i)));
                                c16 = c.getString(c.getColumnIndex(campo.get(i)));
                            } else if (i == 16) {
                                c17 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 17) {
                                c18 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 18) {
                                c19 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals(""))
                                    cam = campo.get(i);
                            } else if (i == 19) {
                                c20 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals("")){
                                    cam = campo.get(i);


                                }

                            }
                            else if (i == 20) {
                                c21 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals("")){
                                    cam = campo.get(i);


                                }

                            }
                            else if (i == 21) {
                                c22 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals("")){
                                    cam = campo.get(i);
                                }

                            }
                            else if (i == 22) {
                                c23 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals("")){
                                    cam = campo.get(i);
                                }

                            }
                            else if (i == 23) {
                                c24 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals("")){
                                    cam = campo.get(i);
                                }

                            }
                            else if (i == 24) {
                                c25 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals("")){
                                    cam = campo.get(i);
                                }

                            }
                            else if (i == 25) {
                                c25 = c.getString(c.getColumnIndex(campo.get(i)));
                                if (!c.getString(c.getColumnIndex(campo.get(i))).trim().equals("")){
                                    cam = campo.get(i);
                                }

                            }
                        }


                    }
                }
                Log.i("codigo", "c1 " + c1 + " c2 " + c2 + " c3 " + c3 + " c4 " + c4 + " c5 " + c5 + "c6 " + c6 + " c7 " + c7 + " c8 " + c8 + " c9 " + c9 + " c0 " + c0 + " c11 " + c11 + " c12 " + c12 + " c13 " + c13 + " c14 " + c14 + " c15 " + c15 + " c16 " + c16 + " c17 " + c17 + " c18 " + c18 + " c19 " + c19 + " c20 " + c20);
                Log.i("Info", "cod: " + c.getString(4) + " ord: " + c.getString(6) + " lap: " + c.getString(7) + " ordenamiento_ " + c.getString(8) + " n " + c.getString(9) + " l " + c.getString(10));
            } while (c.moveToNext());
            System.err.println(contadorgg);
            c.close();
            /*if (id == 5) {
                medidas(cam);
                adapter.notifyDataSetChanged();
            }*/
            /*if (id == 2) {
                medidas(cam);
                adapter.notifyDataSetChanged();
            }*/


            if (id != 5 || id != 2) {


                String sql = "";
                if (id == 4) {
                    sql = "select competencia,ordenamiento from c_ordenamiento where campo = '" + cam + "' and id_c_direccion = " + id;


                    System.err.println(sql);
                    c = db.rawQuery(sql, null);
                    if (c.moveToFirst()) {
                        do {
                            System.err.println(c.getString(0) + " " + c.getString(1));
                            competencias = c.getString(0) + " " + c.getString(1);
                        } while (c.moveToNext());
                    }
                }
            }
        }
    	else{
    		Toast toast = Toast.makeText(this, "No hay infracciones en la bd", Toast.LENGTH_SHORT);
    		toast.setGravity(0, 0, 15);
			toast.show();
    	}
    }catch (SQLiteException e) {
		Log.i("ERROR FATAL", e.getMessage());
	}
    	db.close();
    	
    }
    
    public void buscarNombreCampo() {
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
            Cursor c;
            if(id == 5)
                c = db.rawQuery("SELECT * FROM C_Ordenamiento WHERE id_c_direccion = " + id, null);
            else
                c = db.rawQuery("SELECT * FROM C_Ordenamiento WHERE id_c_direccion = " + id, null);

    	Log.i("que", "SELECT * FROM C_Ordenamiento WHERE id_c_direccion = " + id);
    	if(c.moveToFirst()){
    		Log.i("no", "no");
    		campo.clear();
    		do{
    			campo.add(c.getString(2));
    			Log.i("campo ", c.getString(2));
    		}while(c.moveToNext());
    	}
    	c.close();
    }catch (SQLiteException e) {
		Log.i("ERROR FATAL", e.getMessage());
	}
    	finally{
    		db.close();
    	}
    }
    public void buscarOrdenamientos(){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try {
	    	for (int i = 0; i < campo.size(); i++) {
				
	    		try{
                    Cursor c;
	    		    if(id == 5) { //TODO:
	    		        //c = db.rawQuery("SELECT * FROM C_Ordenamiento WHERE campo = '" + campo.get(i) , null);
                        c = db.rawQuery("SELECT * FROM C_Ordenamiento WHERE campo = '" + campo.get(i) + "' and id_c_direccion = '" + id + "'", null);
                        Log.i("quer", "SELECT * FROM C_Ordenamiento WHERE campo = '" + campo.get(i) + "' and id_c_direccion = '" + 2 + "'");
                        Log.i("i", i + "");
                    } else {
                        c = db.rawQuery("SELECT * FROM C_Ordenamiento WHERE campo = '" + campo.get(i) + "' and id_c_direccion = '" + id + "'", null);
                        Log.i("quer", "SELECT * FROM C_Ordenamiento WHERE campo = '" + campo.get(i) + "' and id_c_direccion = '" + id + "'");
                        Log.i("i", i + "");
                    }
			    	if(c.moveToFirst()){
			    		do{
			    			if (i == 0)
			    				campo1 = c.getString(1);
			    			else if (i ==1 ) 
			    				campo2 = c.getString(1);
			    			else if (i == 2)
			    				campo3 = c.getString(1);
			    			else if(i == 3)
			    				campo4 = c.getString(1);
			    			else if(i == 4)
			    				campo5 = c.getString(1);
			    			else if (i == 5) 
			    				campo6 = c.getString(1);
			    			else if (i == 6)
			    				campo7 = c.getString(1);
			    			else if(i == 7)
			    				campo8 = c.getString(1);
			    			else if(i == 8)
			    				campo9 = c.getString(1);
			    			else if(i == 9)
			    				campo0 = c.getString(1);
			    			else if (i == 10)
			    				campo11 = c.getString(1);
			    			else if(i == 11)
			    				campo12 = c.getString(1);
			    			else if(i == 12)
			    				campo13 = c.getString(1);
			    			else if(i == 13)
			    				campo14 = c.getString(1);
			    			
			    			else if(i == 14)
			    				campo15 = c.getString(1);
			    			else if(i == 15)
			    				campo16 = c.getString(1);
			    			else if (i == 16)
			    				campo17 = c.getString(1);
			    			else if(i == 17)
			    				campo18 = c.getString(1);
			    			else if(i == 18)
			    				campo19 = c.getString(1);
			    			else if(i==19)
			    				campo20 = c.getString(1);
                            else if(i==20)
                                campo21 = c.getString(1);
                            else if(i==21)
                                campo22 = c.getString(1);
                            else if(i==22)
                                campo23 = c.getString(1);
                            else if(i==23)
                                campo24 = c.getString(1);
                            else if(i==24)
                                campo25 = c.getString(1);
                            else if(i==25)
                                campo26 = c.getString(1);


			    			
			    			System.out.println(campo5);
			    		}while(c.moveToNext());
			    	}
			    	c.close();
			    }catch (SQLiteException e) {
			    	Log.i("ERROR FATAL", e.getMessage());
			    }
	    	}
    	}catch (Exception e) {
			Log.e("error", e.getMessage());
		}finally {
			db.close();
		}
    	
    }
    
    public int consultarLevantamientoID(){ 
    	int idLevanta = 0;
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		if(db != null){
			String sentencia = "select * from  Acta ORDER BY id_acta DESC LIMIT 0,1";
			Cursor c = db.rawQuery(sentencia, null);
			try {
				if(c.moveToFirst()){
					do {
						idLevanta = c.getInt(0);
					} while (c.moveToNext());
				}
			} catch (Exception e) {
				Log.e("ERROR FATAL", e.getMessage());
			}
			finally{
				db.close();
				c.close();
			}
		}
		return idLevanta;
	}
    
    public void asignarActa(){ 
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		if(db != null){
			String sentencia = "select * from  Acta where id_c_direccion = '" + id + "' and id_c_inspector = '" + id_inspector1 + "' and numero <> '0' and numero_acta like '%" + ante + "%' ORDER BY id_acta";
			Log.i("Sentencia asignar", sentencia);
			Cursor c = db.rawQuery(sentencia, null);
			try {
				if(c.moveToFirst()){
					do {
						s = c.getString(1);
						numero = c.getInt(6)+"";
						Log.i("NUMERO", numero + " n2");
					} while (c.moveToNext());
				}
			} catch (Exception e) {
				Log.e("ERROR FATAL", e.getMessage());
			}
			finally{
				db.close();
				c.close();
			}
		}
	}
    
    public int consultarActa(){
    	int numero = 0; 
		GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
		SQLiteDatabase db = gestionarBD.getReadableDatabase();
		if(db != null){
			String Sentencia = "select * from  Acta where id_c_direccion = '" + id + "' and id_c_inspector = '" + id_inspector1 + "' order by numero_acta desc ";
			Log.i("Sentencia acta", Sentencia);
			Cursor c = db.rawQuery(Sentencia, null);
			try {
				if(c.moveToFirst()){
					numero  = c.getCount();
					Log.i("NUMERO", numero + " n1");
				}
			} catch (Exception e) {
				
			}
			finally{
				c.close();
				db.close();
			}
		}
		return numero;
	}
    
    public void listarZona(int id_c){
		GestionBD gestinarBD = new GestionBD(getApplicationContext(),"inspeccion",null,1);
		SQLiteDatabase db = gestinarBD.getReadableDatabase();
		try {
			if(db != null){
				//Cursor c = db.query("C_zonas", null, "id_c_zonas", null, null, null, null);
                Cursor c;
                if(id == 4)
                    c = db.rawQuery("SELECT * FROM C_zonas WHERE id_c_direccion = '" + 5 + "'", null);
                else
                    c = db.rawQuery("SELECT * FROM C_zonas WHERE id_c_direccion = '" + 3 + "'", null);
				if(c.moveToFirst()){
					do{
						//zona.add(c.getString(2) + "     " + c.getString(3));
                        zona.add(c.getString(2));
					}while(c.moveToNext());
				}
				c.close();
			}
		} catch (Exception e) {
			
		}
		finally{
			db.close();
		}
		
		
	}
    
    private void updateDisplay() {
        etFecham.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mDay).append("/")
                    .append(mMonth + 1).append("/")
                    .append(mYear).append(" "));
    }

    
    private void updateDisplay1() {
    	m+=1;
        etfecha.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
            		.append(di).append("/")
                    .append(m).append("/")
                    .append(a).append(" "));
    }
    
 // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                	
                	mYear = year;
                	mMonth = monthOfYear;
                	mDay = dayOfMonth;
                	
                }
            };

            @Override
            protected Dialog onCreateDialog(int id) {
                switch (id) {
                case DATE_DIALOG_ID:
                	Log.i("Fecha", "dia: " + mDay + " mes: " + mMonth + " ano: " + mYear);
                    return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
                }
                return null;
            }
            
	@Override
	public void onClick(View v) {
		
		name = "";
		name = Environment.getExternalStorageDirectory()+"/Infracciones/fotografias/"+etNumeroActa.getText().toString().replace("/", "_")+"/";
		File folder = new File(name);
		folder.mkdirs();
		String nom;
		
		switch (v.getId()) {
		case R.id.btnBuscar:
			con = 0;
			Log.e("aqui","estou");
			mostrarResutados(etBuscar.getText().toString());
			break;
		case R.id.btnFecha:
			showDialog(DATE_DIALOG_ID);
			//updateDisplay1();
			updateDisplay();
			break;
		case R.id.btnBorden:
			//System.out.println("hola");
			if(!TextUtils.isEmpty(etOrden1.getText().toString())) {
				lev = buscarLevantameinto("numero_acta = '" + etOrden1.getText().toString().toUpperCase() + "'");
				mostrarCampos();
			}
			else {
				Toast toast = Toast.makeText(getApplicationContext(), "Ingrese la orden de visita a buscar", Toast.LENGTH_LONG);
				toast.setGravity(0, 0, 15);
				toast.show();
			}
			break;
			
		case R.id.radioOrdenV:
			inicio();
			infrac = 2;
			if(id == 2 || id == 3  || id == 5) {
                rlProp.setVisibility(View.VISIBLE);
                tvfechap.setVisibility(View.GONE);
                etfechap.setVisibility(View.GONE);
                radioReimprimir.setVisibility(View.GONE);
            }else
				rlProp.setVisibility(View.GONE);
			rlTestA.setVisibility(View.GONE);
			rlVisita.setVisibility(View.GONE);
			llNota.setVisibility(View.GONE);
			llplazo.setVisibility(View.GONE);
			llreincidencia.setVisibility(View.GONE);
			ante = "OV";
			formato = "orden";
			etGiro.setVisibility(View.GONE);
			
			etMotivo.setVisibility(View.VISIBLE);
			tvMotivo.setVisibility(View.VISIBLE);
			spReglamento.setVisibility(View.GONE);
			//tvReg.setVisibility(View.VISIBLE);
			tvActa.setText("Número Orden");
			
			radioInfraccion.setSelected(true);
			
			btnConsultar.setVisibility(View.VISIBLE);
			radioInfraccion.setVisibility(View.GONE);
			rborden.setVisibility(View.GONE);
			radioEvento.setVisibility(View.GONE);

            spgravedad.setSelection(1);
            tvMC.setVisibility(View.GONE);
            llfundamento.setVisibility(View.GONE);
            tvPropiedad.setVisibility(View.GONE);
            rgPopiedad.setVisibility(View.GONE);

            cbFirma.setVisibility(View.VISIBLE);
            tvfechap.setVisibility(View.GONE);
            etfechap.setVisibility(View.GONE);


			if(id == 4) {
                etNombreComercial.setHint("Nombre del Propietario o Representante Legal");
                tvNombreComercial.setText("Nombre del Propietario o Representante Legal");
			    etReferencia.setVisibility(View.GONE);
			    tvReferencia.setVisibility(View.GONE);
                btnTomarF.setVisibility(View.GONE);
                etAGiro.setHint("Area");
                etAGiro.setText(direccion);
                tvgiro.setText("Area");
                //tvgiro.setVisibility(View.GONE);
                etMotivo.setText("Inspeccionar físicamente que los trabajos o urbanización en proceso, cuenten y presenten los permisos correspondientes como son: ");
                //btnTomarF.setVisibility(View.GONE);
                llPla.setVisibility(View.GONE);
                radioReimprimir.setVisibility(View.GONE);

                etVManifiesta.setVisibility(View.VISIBLE);
                rgPopiedad.setVisibility(View.GONE);
                rlDonde_actua.setVisibility(View.GONE);
                tvPropietario.setVisibility(View.GONE);
                llfundamento.setVisibility(View.VISIBLE);
                tvReg.setVisibility(View.GONE);
                llcomp.setVisibility(View.GONE);
                btnImprimirResum.setVisibility(View.GONE);
                spMeConstitui.setVisibility(View.GONE);
                //tvPeticion.setVisibility(View.GONE);
                //spPeticion.setVisibility(View.GONE);
               //etfoliopeticion.setVisibility(View.GONE);

            }
			if(id == 3) {
			    tvPeticion.setVisibility(View.GONE);
			    spPeticion.setVisibility(View.GONE);
                tvReg.setVisibility(View.GONE);
                llfundamento.setVisibility(View.GONE);
                etDondeActua.setVisibility(View.GONE);
                tvfechap.setVisibility(View.GONE);
                etfechap.setVisibility(View.GONE);
                radioReimprimir.setVisibility(View.GONE);
            }
			if(id == 2 | id == 5) {
                System.out.println("entro a quitar componentes");
                tvReg.setVisibility(View.GONE);
                llfundamento.setVisibility(View.GONE);
                //etCondominio.setVisibility(View.GONE);
                //tvCondominio.setVisibility(View.GONE);
                tvfechap.setVisibility(View.GONE);
                etfechap.setVisibility(View.GONE);
                tvPropietario.setText("NOMBRE Y/O RAZON SOCIAL");
                etDondeActua.setVisibility(View.GONE);
                btnImprimirResum.setVisibility(View.GONE);

                rlLicencias.setVisibility(View.GONE);
                radioReimprimir.setVisibility(View.GONE);
            }
			if(id == 5) {
                rlDonde_actua.setVisibility(View.GONE);
               // rlProp.setVisibility(View.GONE);
                tvMC.setVisibility(View.GONE);
                spMeConstitui.setVisibility(View.GONE);

                tvfechap.setVisibility(View.GONE);
                etfechap.setVisibility(View.GONE);


            }
			if(id == 2) {
                tvMC.setVisibility(View.GONE);
                spMeConstitui.setVisibility(View.GONE);
                rlDonde_actua.setVisibility(View.GONE);
                tvfechap.setVisibility(View.VISIBLE);
                tvfolioap.setVisibility(View.VISIBLE);
                etfechap.setVisibility(View.VISIBLE);
                etfolioap.setVisibility(View.VISIBLE);
            }

			if(id == 2 | id == 5)
			    btnImprimirResum.setVisibility(View.GONE);
			
			break;
			
		case R.id.radioInfraccion:
			inicio();
			infrac = 1;
            rlDonde_actua.setVisibility(View.GONE);
			etDiaPlazo.setText("20");
			etDiaPlazo.setEnabled(false);
			if(id == 4)
			    rlProp.setVisibility(View.GONE);
			else
                rlProp.setVisibility(View.VISIBLE);
			rlTestA.setVisibility(View.VISIBLE);
			rlVisita.setVisibility(View.VISIBLE);
			llNota.setVisibility(View.VISIBLE);
			llplazo.setVisibility(View.VISIBLE);
			llreincidencia.setVisibility(View.GONE);
			ante = "IN";
			formato = "infraccion";
			InfraccionesActivity.this.btnOrden1.setVisibility(View.VISIBLE);
			etOrden1.setVisibility(View.VISIBLE);
			etGiro.setVisibility(View.GONE);
			etMotivo.setVisibility(View.GONE);
			tvMotivo.setVisibility(View.GONE);

            tvfechaOv.setVisibility(View.VISIBLE);
            etfechaOV.setVisibility(View.VISIBLE);
			//spReglamento.setVisibility(View.VISIBLE);
			//spReglamento.setVisibility(View.GONE);
			//tvReg.setVisibility(View.GONE);
			competencias = "";
			regla = "";
			idComp = 0;
			etOrden1.setText("");
			tvActa.setText("Número Acta");
			
			btnConsultar.setVisibility(View.VISIBLE);
			radioInfraccion.setVisibility(View.GONE);
			rborden.setVisibility(View.GONE);
			radioEvento.setVisibility(View.GONE);
			
			spNombreA.setVisibility(View.GONE);
			spNombreA1.setVisibility(View.GONE);
			spNombreA2.setVisibility(View.GONE);
			spNombreA3.setVisibility(View.GONE);
			spNombreA4.setVisibility(View.GONE);
			
			tvAcomp.setVisibility(View.GONE);
			llconcepto.setVisibility(View.GONE);
            btnImprimirResum.setVisibility(View.GONE);

            if(id == 4) {
                tvBuscar.setVisibility((View.GONE));
                tilArticulo.setVisibility(View.GONE);
                btnBusArt.setVisibility(View.GONE);
                radioReimprimir.setVisibility(View.GONE);
                etNombreComercial.setHint("Nombre del Propietario o Representante Legal");
                tvNombreComercial.setText("Nombre del Propietario o Representante Legal");
                etReferencia.setVisibility(View.GONE);
                tvReferencia.setVisibility(View.GONE);
                etAGiro.setHint("Area");
                etAGiro.setText(direccion);
                spgiro.setVisibility(View.GONE);
                tvgiro.setText("Area");
                etMotivo.setText("Inspeccionar físicamente que los trabajos o urbanización en proceso, cuenten y presenten los permisos correspondientes como son: ");
                llplazo.setVisibility(View.VISIBLE);
                tvPropietario.setVisibility(View.GONE);
                rlDonde_actua.setVisibility(View.GONE);
                llfundamento.setVisibility(View.VISIBLE);
                etMedida.setVisibility(View.GONE);
                llcomp.setVisibility(View.GONE);
                tvReg.setVisibility(View.GONE);
                tvMC.setVisibility(View.GONE);
                spMeConstitui.setVisibility(View.GONE);
                btnImprimirResum.setVisibility(View.GONE);
                tvUso.setVisibility(View.GONE);
                tvNota.setText("Uso de Suelo");
                tvfolioap.setVisibility(View.VISIBLE);
                etfolioap.setVisibility(View.VISIBLE);
                tvfechap.setVisibility(View.VISIBLE);
                etfechap.setVisibility(View.VISIBLE);
            }
            if(id == 2 | id == 5 ) {
                etDecomiso.setVisibility(View.VISIBLE);
                llNota.setVisibility(View.GONE);
                //tvCondominio.setVisibility(View.GONE);
                //etCondominio.setVisibility(View.GONE);
                tvReg.setVisibility(View.GONE);
                radioReimprimir.setVisibility(View.GONE);
                etManifiesta.setText("Se reserva el derecho");
                etPropietario.setVisibility(View.GONE);
                tvPropietario.setVisibility(View.GONE);
                rlProp.setVisibility(View.GONE);
                rlDonde_actua.setVisibility(View.VISIBLE);
                tvgiro.setVisibility(View.VISIBLE);
                etGiro.setVisibility(View.VISIBLE);
                //tvgiro.setVisibility(View.VISIBLE);
                llfundamento.setVisibility(View.GONE);
                //etCondominio.setVisibility(View.GONE);
                //tvCondominio.setVisibility(View.GONE);
                tvPropietario.setText("NOMBRE Y/O RAZON SOCIAL");
                llPla.setVisibility(View.GONE);
                tvfolioap.setVisibility(View.VISIBLE);
                tvfechap.setVisibility(View.VISIBLE);
                etfolioap.setVisibility(View.VISIBLE);
                etfechap.setVisibility(View.VISIBLE);
                btnImprimirResum.setVisibility(View.GONE);

            }
            if(id == 2) {
                tvMC.setVisibility(View.GONE);
                spMeConstitui.setVisibility(View.GONE);
            }
            if(id == 3) {

                tvReg.setVisibility(View.GONE);
                llfundamento.setVisibility(View.GONE);
                radioReimprimir.setVisibility(View.GONE);
                etDondeActua.setVisibility(View.GONE);
                rlDonde_actua.setVisibility(View.GONE);


            }
            if(id == 5)
                rlDonde_actua.setVisibility(View.GONE);
            if(id == 2 | id == 5)
                btnImprimirResum.setVisibility(View.GONE);

			
			break;
			
		case R.id.btnVista:

		    if(validarI()) {
              try {
                  imprimirPrevia(formato);
              }catch (Exception e){

                  Log.e(TAG, "onClick: ",e );
                  Toast toast = Toast.makeText(getApplicationContext(), "Verificar sus datos!! No se puede generar Vista Previa!!", (Toast.LENGTH_LONG));
                  toast.setGravity(0, 0, 15);
                  toast.show();
              }


                File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_") + ".pdf");

                if (file.exists()) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setPackage("com.dynamixsoftware.printershare");
                    if (Build.VERSION.SDK_INT < 24)
                        i.setDataAndType(Uri.fromFile(file), "application/pdf");
                    else
                        i.setDataAndType(FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID + ".provider",file), "application/pdf");
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(i);
                }
            }
			break;
			
		case R.id.btnVer1:
			nom = etNumeroActa.getText().toString()+"-1.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			//new DialogoPersonalizado();
			break;
		case R.id.btnVer2:
			nom = etNumeroActa.getText().toString()+"-2.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
		case R.id.btnVer3:
			nom = etNumeroActa.getText().toString()+"-3.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
		case R.id.btnVer4:
			nom = etNumeroActa.getText().toString()+"-4.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer5:
			nom = etNumeroActa.getText().toString()+"-5.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer6:
			nom = etNumeroActa.getText().toString()+"-6.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer7:
			nom = etNumeroActa.getText().toString()+"-7.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer8:
			nom = etNumeroActa.getText().toString()+"-8.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer9:
			nom = etNumeroActa.getText().toString()+"-9.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer10:
			nom = etNumeroActa.getText().toString()+"-10.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer11:
			nom = etNumeroActa.getText().toString()+"-11.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer12:
			nom = etNumeroActa.getText().toString()+"-12.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer13:
			nom = etNumeroActa.getText().toString()+"-13.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer14:
			nom = etNumeroActa.getText().toString()+"-14.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnVer15:
			nom = etNumeroActa.getText().toString()+"-15.jpg";
			name+=nom.replace("/", "_");
			mostrarImagen(name);
			break;
			
		case R.id.btnImprimirResum:
			mBixolonPrinter = new BixolonPrinter(this, mHandler, null);
			mBixolonPrinter.findBluetoothPrinters();
			printSampleGDL();
			break;
		case R.id.btnBCol:
            buscarCol(etBCol.getText().toString());
            adapterCol.notifyDataSetChanged();
            break;

            case R.id.btnBusArt:
                tilArticulo.setError(null);
                if(TextUtils.isEmpty(etArti.getText().toString())){
                    tilArticulo.setError("Ingrese el articulo");
                } else if(TextUtils.isEmpty(spCreglamentos.getSelectedItem().toString()) | spCreglamentos.getSelectedItem().toString().trim().equalsIgnoreCase("Buscar en Todos los reglamentos")) {
                    tilArticulo.setError("Seleccione el reglamento");
                } else {
                    Log.v("reglamento",arregloCreglamentosx.get(spCreglamentos.getSelectedItemPosition()));
                    buscarInfraccionA(arregloCreglamentosx.get(spCreglamentos.getSelectedItemPosition()),etArti.getText().toString());
                    if(!arregloInfraccion.isEmpty() && !arregloInfraccion1.isEmpty())
                        spInfraccion.setAdapter(new ArrayAdapter<>(this, R.layout.multiline_spinner_dropdown_item, arregloInfraccion1));
                    else
                        spInfraccion.setAdapter(new ArrayAdapter<>(this, R.layout.multiline_spinner_dropdown_item,arregloInfraccion));
                }
                break;
            case R.id.radioEventoEspecial:
                id=5;
                //evento=5;
                //competencia2();
                campo.clear();
                buscarNombreCampo();
                buscarOrdenamientos();



                arregloCreglamentosx.clear();
                arregloCreglamentos.clear();
                mostrarReglamentos();

                listar();
                direccion="Horarios Especiales";
                if(!arregloLista1.isEmpty()){
                    spInspectorT.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista1));
                }

                if(!arregloLista2.isEmpty()){
                    spInspectorT1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista2));
                }

                inicio();
                infrac = 1;
                rlDonde_actua.setVisibility(View.GONE);
                etDiaPlazo.setText("20");
                etDiaPlazo.setEnabled(false);
                if(id == 4)
                    rlProp.setVisibility(View.GONE);
                else
                    rlProp.setVisibility(View.VISIBLE);
                rlTestA.setVisibility(View.VISIBLE);
                rlVisita.setVisibility(View.VISIBLE);
                llNota.setVisibility(View.VISIBLE);
                llplazo.setVisibility(View.VISIBLE);
                llreincidencia.setVisibility(View.GONE);
                ante = "IN";
                formato = "infraccion";
                InfraccionesActivity.this.btnOrden1.setVisibility(View.VISIBLE);
                etOrden1.setVisibility(View.VISIBLE);
                etGiro.setVisibility(View.GONE);
                etMotivo.setVisibility(View.GONE);
                tvMotivo.setVisibility(View.GONE);
                //spReglamento.setVisibility(View.VISIBLE);
                //spReglamento.setVisibility(View.GONE);
                //tvReg.setVisibility(View.GONE);
                competencias = "";
                regla = "";
                idComp = 0;
                etOrden1.setText("");
                tvActa.setText("Número Acta");

                btnConsultar.setVisibility(View.VISIBLE);
                radioInfraccion.setVisibility(View.GONE);
                rborden.setVisibility(View.GONE);
                radioEvento.setVisibility(View.GONE);
                radioReimprimir.setVisibility(View.GONE);
                spNombreA.setVisibility(View.GONE);
                spNombreA1.setVisibility(View.GONE);
                spNombreA2.setVisibility(View.GONE);
                spNombreA3.setVisibility(View.GONE);
                spNombreA4.setVisibility(View.GONE);

                tvAcomp.setVisibility(View.GONE);
                llconcepto.setVisibility(View.GONE);

                if(id == 4) {
                    Log.e(TAG, "onClick: entro aqui" );
                    tvBuscar.setVisibility((View.GONE));
                    tilArticulo.setVisibility(View.GONE);
                    btnBusArt.setVisibility(View.GONE);

                    etNombreComercial.setHint("Nombre del Propietario o Representante Legal");
                    tvNombreComercial.setText("Nombre del Propietario o Representante Legal");
                    etReferencia.setVisibility(View.GONE);
                    tvReferencia.setVisibility(View.GONE);
                    etAGiro.setHint("Area");
                    etAGiro.setText(direccion);
                    tvgiro.setText("Area");
                    etMotivo.setText("Inspeccionar físicamente que los trabajos o urbanización en proceso, cuenten y presenten los permisos correspondientes como son: ");
                    llplazo.setVisibility(View.VISIBLE);
                    tvPropietario.setVisibility(View.GONE);
                    rlDonde_actua.setVisibility(View.GONE);
                    llfundamento.setVisibility(View.VISIBLE);
                    etMedida.setVisibility(View.GONE);
                    llcomp.setVisibility(View.GONE);
                    tvReg.setVisibility(View.GONE);
                    tvMC.setVisibility(View.GONE);
                    spMeConstitui.setVisibility(View.GONE);
                    btnImprimirResum.setVisibility(View.GONE);
                    tvUso.setVisibility(View.GONE);
                    tvNota.setText("Uso de Suelo");
                    tvfolioap.setVisibility(View.VISIBLE);
                    etfolioap.setVisibility(View.VISIBLE);
                    tvfechap.setVisibility(View.VISIBLE);
                    etfechap.setVisibility(View.VISIBLE);
                }
                if(id == 2 | id == 5 ) {
                    Log.e(TAG, "onClick: entro aqui" );
                    etDecomiso.setVisibility(View.VISIBLE);
                    llNota.setVisibility(View.GONE);
                    //tvCondominio.setVisibility(View.GONE);
                    //etCondominio.setVisibility(View.GONE);
                    tvReg.setVisibility(View.GONE);
                    etManifiesta.setText("Se reserva el derecho");
                    etPropietario.setVisibility(View.GONE);
                    tvPropietario.setVisibility(View.GONE);
                    rlProp.setVisibility(View.GONE);
                    rlDonde_actua.setVisibility(View.VISIBLE);
                    tvgiro.setVisibility(View.VISIBLE);
                    etGiro.setVisibility(View.VISIBLE);
                    llfundamento.setVisibility(View.GONE);
                    btnArticulos.setVisibility(View.VISIBLE);
                    llcomp.setVisibility(View.GONE);
                    //llconcepto.setVisibility(View.GONE);
                    //etCondominio.setVisibility(View.GONE);
                    //tvCondominio.setVisibility(View.GONE);
                    tvPropietario.setText("NOMBRE Y/O RAZON SOCIAL");
                    llPla.setVisibility(View.GONE);
                    tvfolioap.setVisibility(View.VISIBLE);
                    tvfechap.setVisibility(View.VISIBLE);
                    etfolioap.setVisibility(View.VISIBLE);
                    etfechap.setVisibility(View.VISIBLE);
                    etArticulo.setVisibility(View.VISIBLE);
                }
                if(id == 2) {
                    tvMC.setVisibility(View.GONE);
                    spMeConstitui.setVisibility(View.GONE);
                }
                if(id == 3) {

                    tvReg.setVisibility(View.GONE);
                    llfundamento.setVisibility(View.GONE);
                    etDondeActua.setVisibility(View.GONE);
                    rlDonde_actua.setVisibility(View.GONE);


                }
                if(id == 5) {
                    Log.e(TAG, "onClick: entro aqui" );
                    rlDonde_actua.setVisibility(View.GONE);
                }
                if(id == 2 | id == 5) {
                    Log.e(TAG, "onClick: entro aqui" );
                    btnImprimirResum.setVisibility(View.GONE);
                }

                break;
            case R.id.radioReimprimir:
                Intent intent;

                intent = new Intent(InfraccionesActivity.this, ReimprimirActivity.class);



                Bundle bundle = new Bundle();
                bundle.putString("usuario", us.trim());
                bundle.putInt("id", id);
                bundle.putString("direccion", direccion);
                bundle.putInt("con", con);
                intent.putExtras(bundle);
                startActivity(intent);
                break;


            default:
			break;
		}
	}

    private void buscarInfraccionA(String articulo, String search) {
        GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarBD.getReadableDatabase();
        if(search.contains("art.")||search.contains("art")||search.contains("Articulo") || search.contains("Art") || search.contains("Artículo") || search.contains("Articulos") || search.contains("Artículos") || search.contains("ARTICULOS") || search.contains("ARTÍCULOS") || search.contains("ARTICULO") || search.contains("ARTÍCULO") ){
           String cadena="";
           int position=0;
            for(int i=0; i<=search.length();i++){

               cadena+=search.substring(i,i+1);
                System.out.println(cadena);
                if(cadena.equals("art.")||cadena.equals("art")||cadena.equals("Articulos") || cadena.equals("Artículos") || cadena.equals("Artículo") || cadena.equals("Articulo") || cadena.equals("ARTICULOS") || cadena.equals("ARTÍCULOS") || cadena.equals("ARTICULO") || cadena.equals("ARTÍCULO")){
                    System.out.println("entro");
                    position=i+1;
                    break;
                }
           }
            int tamas=search.length();
         search=search.substring(position+1,tamas);


        }
        String sql="";
        if(id==5){
            sql = "SELECT distinct infraccion," + articulo + " FROM c_infraccion WHERE " + articulo + " LIKE '%" + search + "%' and trim(vigente) = 'S' order by id_c_infraccion";
        }else {
            sql = "SELECT distinct infraccion," + articulo + " FROM c_infraccion WHERE " + articulo + " LIKE '%" + search + "%'  and trim(vigente) = 'S' order by id_c_infraccion";
        }
        Cursor cursor = db.rawQuery(sql, null);
        Log.v("sql",sql);
        arregloInfraccion.clear();
        arregloInfraccion1.clear();
        if(cursor.moveToFirst()){
            arregloInfraccion.add("");
            arregloInfraccion1.add("");
            do{
                //if(cursor.getString(cursor.getColumnIndex("vigente")).trim().equalsIgnoreCase("S")) {
                //id_hecho.add(cursor.getInt(1));
                //Recorte indentificadores
                if(id!=4) {

                    /*String hechorecorte=cursor.getString(0);
                    arregloInfraccion.add(cursor.getString(0).substring(4,hechorecorte.length()));
                    arregloInfraccion1.add(cursor.getString(0).substring(4,hechorecorte.length()) + ".- " + cursor.getString(1));*/
                    arregloInfraccion.add(cursor.getString(0));
                    arregloInfraccion1.add(cursor.getString(0) + ".- " + cursor.getString(1));
                }else{
                    arregloInfraccion.add(cursor.getString(0));
                    arregloInfraccion1.add(cursor.getString(0) + ".- " + cursor.getString(1));
                }
                Log.i("listado", "Infraccion: " + cursor.getString(0));
                //}
            }while(cursor.moveToNext());
        }
        cursor.close();
        con++;
    }

    public void mostrarImagen(String foto){
		
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(InfraccionesActivity.this);
		dialog.setTitle("Imagen");
		ImageView iv = new ImageView(getApplicationContext());
		
		Bitmap bm = BitmapFactory.decodeFile(foto);
		iv.setImageBitmap(bm);
		
		dialog.setView(iv);
		dialog.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		AlertDialog alert = dialog.create();
		alert.show();

		
	}
	
	/*public class DialogoPersonalizado extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	 
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View popupWindow;
	    popupWindow = inflater.inflate(R.layout.mostrar_foto, null);
	 
	    builder.setView(popupWindow).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
	           
	    	public void onClick(DialogInterface dialog, int id) {
	                  dialog.cancel();
	    	}
	    	
	    });
	 
	    return builder.create();
	    }
	}*/
	
	private void selectValue(Spinner spinner, String val) {
	    for (int i = 0; i < spinner.getCount(); i++) {
	        if (spinner.getItemAtPosition(i).toString().equals(val.trim())) {
	            spinner.setSelection(i);
	            break;
	        }
	    }
	}
	
	public void mostrarCampos() {
		if(!lev.isEmpty()) {
			resov = true;
			
			etCalle.setText(lev.get(0).getCalle());
			etNumero.setText(lev.get(0).getNumero_ext());
			etNuemroInterior.setText(lev.get(0).getNumero_int());
			etPropietario.setText(lev.get(0).getNombre_razon());
			etApellidoM.setText(lev.get(0).getApellidom_prop());
			etApellidoP.setText(lev.get(0).getApellidop_prop());
			etFraccionamiento.setText(lev.get(0).getFraccionamiento());
            etfolioap.setText(lev.get(0).getFolio_apercibimiento());
            etfechap.setText(lev.get(0).getFecha_apercibimiento());
			numeroOV = lev.get(0).getNumeroActa();
			fechaOV = lev.get(0).getFecha();
			idComp = lev.get(0).getIdComp();
			etEntreC.setText(lev.get(0).getEntre_calle1());
			etEntreC1.setText(lev.get(0).getEntre_calle2());
			etResponsable.setText(lev.get(0).getResponsable_obra());
			etRegistro.setText(lev.get(0).getRegistro_responsable());
			ident = lev.get(0).getIdentifica();
			zon = lev.get(0).getZona();
            System.out.println("ffffff"+zon);
			//selectValue(spZona,zon);
            for (int i=0;i<spZona.getCount();i++){
                if (spZona.getItemAtPosition(i).toString().equalsIgnoreCase(zon)){
                    spZona.setSelection(i);
                    break;
                }
            }


			etNombreV.setText(lev.get(0).getNombre_visitado());
			
			etResponsable.setText(lev.get(0).getResponsable_obra());
			etReferencia.setText(lev.get(0).getReferencia());
			etRegistro.setText(lev.get(0).getRegistro_responsable());
			etCondominio.setText(lev.get(0).getCondominio());
			etConstruccion.setText(lev.get(0).getL_construccion());
			selectValue(spFraccionamiento,lev.get(0).getFraccionamiento());

			
			String [] cam;
			
			System.err.println(lev.get(0).getSe_identifica());
			
			cam = lev.get(0).getSe_identifica().split(":");
			
			selectValue(spIdentifica, cam[0]);

            if(cam.length > 1)
			    etVIdentifica.setText(cam[1]);
			
			etNombreComercial.setText(lev.get(0).getNombre_comercial());
			etSector.setText(lev.get(0).getSector());
			
			//System.err.println(lev.get(0).getNumeroCitatorio());
			
			String cita[] = lev.get(0).getNumeroCitatorio().split("-");
			
			for (int i = 0; i < cita.length; i++) {
				System.err.println(cita[i]);
			}
			
			if(cita.length > 0)
				etCitatorio.setText(cita[1]);
			
			etInspccionFue.setText(lev.get(0).getPeticion());
			etMotivo.setText(lev.get(0).getMotivo_orden());
			etMedida.setText(lev.get(0).getMedida_seguridad());
			etArticulo.setText(lev.get(0).getArticulo_medida());
			etVManifiesta.setText(lev.get(0).getManifiesta_ser());
			etfolioap.setText(lev.get(0).getFolio_apercibimiento());
			if(lev.get(0).getFecha_apercibimiento().length() > 1   ) {
                String[] fechaap2 = lev.get(0).getFecha_apercibimiento().split("-");


                etfechap.setText(fechaap2[2] + "-" + fechaap2[1] + "-" + fechaap2[0]);
            }else{

                etfechap.setText("");
            }


			
			//id_inspector1 = lev.get(0).getId_c_inspector1();
			id_inspector2 = lev.get(0).getId_c_inspector2();
			id_inspector3 = lev.get(0).getId_c_inspector3();
			id_inspector4 = lev.get(0).getId_c_inspector4();
			id_inspector5 = lev.get(0).getId_c_inspector5();
			id_inspector6 = lev.get(0).getId_c_inspector6();
			
			System.out.println(lev.get(0).getV_firma());
			
			if(lev.get(0).getV_firma() != null) {
				firma = lev.get(0).getV_firma();
				if(lev.get(0).getV_firma().equalsIgnoreCase("si"))
					cbFirma.setChecked(true);
				else
					cbFirma.setChecked(false);
			} else {
				cbFirma.setChecked(false);
				firma = "No";
			}
			
			etLGiro.setText(lev.get(0).getLicencia_giro());
			etAGiro.setText(lev.get(0).getActividad_giro());
			etAlicencia.setText(lev.get(0).getAxo_licencia() + "");
			
			System.err.println((id_inspector3 > 0) + " INSPECTOR");
			
			ArrayList<String> id2 = new ArrayList<String>();
			ArrayList<String> id3 = new ArrayList<String>();
			ArrayList<String> id4 = new ArrayList<String>();
			ArrayList<String> id5 = new ArrayList<String>();
			ArrayList<String> id6 = new ArrayList<String>();
			
			
			
			/*if(id_inspector2 > 0) {
				spNombreA.setEnabled(false);
				id2.add(consultarUsuario1(id_inspector2));
				spNombreA.setAdapter(null);
				spNombreA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, id2));
			}
			
			
			if(id_inspector3 > 0) {
				spNombreA1.setEnabled(false);
				id3.add(consultarUsuario2(id_inspector3));
				spNombreA1.setAdapter(null);
				spNombreA1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, id3));
			} 
			
			
			if(id_inspector4 > 0) {
				spNombreA2.setEnabled(false);
				id4.add(consultarUsuario3(id_inspector4));
				spNombreA2.setAdapter(null);
				spNombreA2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, id4));
			} 
			
			
			if(id_inspector5 > 0) {
				spNombreA3.setEnabled(false);
				id5.add(consultarUsuario4(id_inspector5));
				spNombreA3.setAdapter(null);
				spNombreA3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, id5));
			} 
			
			
			if(id_inspector6 > 0) {
				spNombreA4.setEnabled(false);
				id6.add(consultarUsuario5(id_inspector6));
				spNombreA4.setAdapter(null);
				spNombreA4.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, id6));
			}*/
			
			System.out.println(lev.get(0).getEntre_calle2() + " " + lev.get(0).getEntre_calle1());
			
			System.err.println(idComp + " PP");
			mostrarCompetencia(idComp);
			
			System.err.println(competencias + " COM " + regla);
			
		} else {
			resov = false;
			etCalle.setText("");
			etNumero.setText("");
			etNuemroInterior.setText("");
			etPropietario.setText("");
			etApellidoM.setText("");
			etApellidoP.setText("");
			numeroOV = etOrden1.getText().toString();
			fechaOV = "";
			idComp = 0;
			etEntreC.setText("");
			etEntreC1.setText("");
			etResponsable.setText("");
			etRegistro.setText("");
			ident = "";
			zon = "";
			
			
			etInspccionFue.setText("");
			etMotivo.setText("");
			etMedida.setText("");
			etArticulo.setText("");
			etVManifiesta.setText("");
			
			etResponsable.setText("");
			etReferencia.setText("");
			etRegistro.setText("");
			etCondominio.setText("");
			etConstruccion.setText("");
			etVIdentifica.setText("");
			
			etNombreComercial.setText("");
			etSector.setText("");
			
			cbFirma.setChecked(false);
			
			spNombreA.setEnabled(true);
			spNombreA.setAdapter(null);
			spNombreA.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arregloLista));
			
			
			spNombreA1.setEnabled(true);
			spNombreA1.setAdapter(null);
			spNombreA1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arregloLista));
		 
			spNombreA2.setEnabled(true);
			spNombreA2.setAdapter(null);
			spNombreA2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arregloLista)); 
			
			spNombreA3.setEnabled(true);
			spNombreA3.setAdapter(null);
			spNombreA3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arregloLista));
			
			
			spNombreA4.setEnabled(true);
			spNombreA4.setAdapter(null);
			spNombreA4.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arregloLista));
			
			String [] verificar=numeroOV.split("/");
            Log.i("tamaño array oV:",numeroOV+" :"+ String.valueOf(verificar.length));
			if(verificar.length==7) {

                Toast toast = Toast.makeText(getApplicationContext(), "Orden de visita se encuentra en otra tableta se activa modo manual se respeta el folio ingresado.", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 15);
                toast.show();
            }else{
                Toast toast = Toast.makeText(getApplicationContext(), "Numero de Orden de Visita Invalido ¡¡Verificar!!", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 15);
                toast.show();
            }
		}
	}
	
	public void mostrarCompetencia(int idCompetencia) {
		
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	
    	if(db != null) {
            Cursor cursor;
    	        cursor = db.rawQuery("select * from c_ordenamiento where id_c_ordenamiento = '" + idCompetencia +"'", null);

    		if(cursor.moveToFirst()) {
    			do {
					competencias = cursor.getString(cursor.getColumnIndex("competencia"));
					regla = cursor.getString(cursor.getColumnIndex("ordenamiento"));
				} while (cursor.moveToNext());
    		}
    		cursor.close();
    	}
    	db.close();
		
	}
	
	public List<Levantamiento> buscarLevantameinto(String condicion){
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	
    	if(TextUtils.isEmpty(condicion)) {
			condicion = "1=1";
		}
		List<Levantamiento> levantamiento = new ArrayList<Levantamiento>();
		String sql = "select * from Levantamiento where " + condicion;



		
		Cursor cursor = db.rawQuery(sql, null);
		
		if(db != null) {

			if(cursor.moveToFirst()) {

				do {



                    Levantamiento levantamientos = cursorToLevantamiento(cursor);
					levantamiento.add(levantamientos);
				} while (cursor.moveToNext());
			}
		}
		return levantamiento;
	}
	
	public List<MedidaSeguridad> buscarMedida(String condicion){
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getReadableDatabase();
    	
    	if(TextUtils.isEmpty(condicion)) {
			condicion = "1=1";
		}
		List<MedidaSeguridad> medida = new ArrayList<MedidaSeguridad>();
		String sql = "select * from c_medida_seguridad where " + condicion;
		System.out.println(sql + " 1");
		
		Cursor cursor = db.rawQuery(sql, null);
		
		if(db != null) {
			System.out.println(sql + " 2");
			if(cursor.moveToFirst()) {
				System.out.println(sql + " 3");
				medida.add(new MedidaSeguridad());
				do {
					System.out.println(sql + " 4");
					MedidaSeguridad medidas = cursorToMedida(cursor);
					medida.add(medidas);
				} while (cursor.moveToNext());
			}
		}
		return medida;
	}
	
	public MedidaSeguridad cursorToMedida(Cursor cursor) {
		return new MedidaSeguridad(cursor.getString(cursor.getColumnIndex("medida_seguridad")), cursor.getString(cursor.getColumnIndex("articulos")));
	}

	public Levantamiento cursorToLevantamiento(Cursor cursor) {
		//numero_citatorio TEXT,infraccion int,tipo_acta text,fecha numeric,hora_inicio time,longitud float,latitud float,orden_vista int,fecha_orden_v numeric,Zona text,nombre_visitado text,se_identifica text,manifiesta_ser text,fraccionamiento text,calle text,numero_ext text,numero_int text,apellidop_prop text,apellidom_prop text,nombre_razon text,nombre_testigo1 text,ife_testigo1 text,designado_por1 text,nombre_testigo2 text,ife_testigo2 text,designado_por2 text,uso_catalogo text,hechos text,infracciones text,id_c_infraccion text,uso_suelo text,densidad text,manifiesta text,gravedad int,dias_plazo int,fecha_plazo numeric,hora_termino time,tipo_visita TEXT,id_pago int,pago numeric, fecha_pago numeric, estatus text,condominio TEXT,manzana TEXT,lote TEXT,capturo text,fecha_atiende_juez numeric, fecha_cancelacion numeric,fecha_efectua_multa numeric, vigencia_multa int, fecha_vigencia numeric,multa text,observaciones text, referencia TEXT
		System.out.println(cursor.getString(cursor.getColumnIndex("licencia_giro")));
		System.out.println(cursor.getString(cursor.getColumnIndex("actividad_giro")));
        //System.out.println("pruebaaaa: "+ cursor.getString(cursor.getColumnIndex("fecha_apercibimiento")));

		return new Levantamiento(cursor.getInt(cursor.getColumnIndex("id_c_direccion")), cursor.getInt(cursor.getColumnIndex("id_c_inspector1")), cursor.getInt(cursor.getColumnIndex("id_c_inspector2")), cursor.getString(cursor.getColumnIndex("numero_acta")), cursor.getString(cursor.getColumnIndex("nombre_visitado")), cursor.getString(cursor.getColumnIndex("se_identifica")), cursor.getString(cursor.getColumnIndex("manifiesta_ser")), cursor.getString(cursor.getColumnIndex("fraccionamiento")), cursor.getString(cursor.getColumnIndex("calle")), cursor.getString(cursor.getColumnIndex("numero_ext")), cursor.getString(cursor.getColumnIndex("numero_int")), cursor.getString(cursor.getColumnIndex("nombre_razon")), cursor.getString(cursor.getColumnIndex("apellidop_prop")), cursor.getString(cursor.getColumnIndex("apellidom_prop")), cursor.getInt(cursor.getColumnIndex("id_c_competencia")),cursor.getString(cursor.getColumnIndex("fecha")),cursor.getString(cursor.getColumnIndex("entre_calle1")),cursor.getString(cursor.getColumnIndex("entre_calle2")),cursor.getString(cursor.getColumnIndex("responsable_obra")),cursor.getString(cursor.getColumnIndex("registro_responsable")),cursor.getString(cursor.getColumnIndex("identifica")),cursor.getString(cursor.getColumnIndex("peticion")),cursor.getString(cursor.getColumnIndex("v_firma")),cursor.getString(cursor.getColumnIndex("motivo_orden")),cursor.getString(cursor.getColumnIndex("medida_seguridad")),cursor.getString(cursor.getColumnIndex("articulo_medida")),cursor.getInt(cursor.getColumnIndex("id_c_inspector3")),cursor.getInt(cursor.getColumnIndex("id_c_inspector4")),cursor.getInt(cursor.getColumnIndex("id_c_inspector5")),cursor.getInt(cursor.getColumnIndex("id_c_inspector6")),cursor.getString(cursor.getColumnIndex("Zona")),cursor.getString(cursor.getColumnIndex("referencia")),cursor.getString(cursor.getColumnIndex("l_construccion")),cursor.getString(cursor.getColumnIndex("condominio")),cursor.getString(cursor.getColumnIndex("numero_citatorio")),cursor.getString(cursor.getColumnIndex("licencia_giro")),cursor.getString(cursor.getColumnIndex("actividad_giro")),cursor.getInt(cursor.getColumnIndex("axo_licencia")),cursor.getString(cursor.getColumnIndex("nombre_comercial")),cursor.getString(cursor.getColumnIndex("sector")),cursor.getString(cursor.getColumnIndex("folio_apercibimiento")),cursor.getString(cursor.getColumnIndex("fecha_apercibimiento")));
	}
	
	public void validarFec(){
		
	}
	
	public long insertarActa(String numero_acta, int id_c_direccion, int id_c_inspectores, String fecha, int infraccion, String numero){
		long n = 0;
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getWritableDatabase();
    	
    	if(db != null) {
    		try {
				ContentValues cv = new ContentValues();
				cv.put("numero_acta", numero_acta);
				cv.put("id_c_direccion", id_c_direccion);
				cv.put("id_c_inspector", id_c_inspectores);
				cv.put("fecha", fecha);
				cv.put("infraccion", infraccion);
				cv.put("numero", numero);
				
				n = db.insert("Acta", null, cv);
			} catch (Exception e) {
				Log.e("Error in Acta", e.getMessage());
			}
    		finally{
    			db.close();
    		}
    	}
    	
		return n;
	}
	
	public long ingresar(String numero_acta, String numero_citatorio, int infraccion, String tipo_acta, int id_c_direccion, String fechas, String hora_inicio,
			double longitud, double latitud, String orden_vista, String fecha_orden, String zona, int id_c_inspector1, int id_c_inspector2
			,String nombre_visitado, String se_identifica, String manifiesta_ser, String fraccionamiento, String calle,
			String numero_ext, String numrero_int, String apellidop_prop, String apellidom_prop, String nprop_razonsocial,
			String nombre_testigo1, String ife_testigo1, String designado_por1, String nombre_testigo2, String ife_testigo2, 
			String designado_por2, String usoC,String hechos, String infracciones, String id_c_infracciones, String uso_suelo, String densidad,
			String manifiesta, int gravedad, int dias_plazo, String fecha_plazo, String hora_termino,String condominio, String lote, String manzana, String referencia, String correo, String alineamiento, String construccion,int competencia,
			String entre_calle1,String entre_calle2,String responsable_obra,String registro_responsable,String status,
			String identifica,String peticion,String v_firma,String motivo_orden,String medida_seguridad,String articulo_medida,
			int id_c_inspector3,int id_c_inspector4,int id_c_inspector5,int id_c_inspector6,int id_competencia1,int id_competencia2,
			int id_competencia3,int id_competencia4,int id_competencia5,String licencia_giro,String actividad_giro,int axo_licencia,
			String nombre_comercial,String sector,String niec,String rei,int tipo_cedula,String etfoliopeticion,String  etfolioap,String numero_sellos,String decomiso,String  etfechap,String etfolioclau,String etfechaclau,String tableta,String version){
		
		
		
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getWritableDatabase();
		
		long n = 0;
		
		if(db!=null){
			
			try{
		
				ContentValues cv = new ContentValues();
				cv.put("numero_acta", numero_acta);
				cv.put("numero_citatorio", numero_citatorio);
				cv.put("infraccion", infraccion);
				cv.put("tipo_acta", tipo_acta);
				cv.put("id_c_direccion", id_c_direccion);
				cv.put("fecha", fechas);
				cv.put("hora_inicio", hora_inicio);
				cv.put("longitud", longitud);
				cv.put("latitud", latitud);
				cv.put("orden_vista",orden_vista);
				cv.put("fecha_orden_v", fecha_orden);
				cv.put("Zona", zona);
				cv.put("id_c_inspector1", id_c_inspector1);
				cv.put("id_c_inspector2", id_c_inspector2);
				cv.put("nombre_visitado", nombre_visitado);
				cv.put("se_identifica", se_identifica);
				cv.put("manifiesta_ser", manifiesta_ser);
				cv.put("fraccionamiento", fraccionamiento);
				cv.put("calle", calle);
				cv.put("numero_ext", numero_ext);
				cv.put("numero_int", numrero_int);
				cv.put("apellidop_prop",apellidop_prop);
				cv.put("apellidom_prop", apellidom_prop);
				cv.put("nombre_razon", nprop_razonsocial);
				cv.put("nombre_testigo1", nombre_testigo1);
				cv.put("ife_testigo1", ife_testigo1);
				cv.put("designado_por1", designado_por1);
				cv.put("nombre_testigo2", nombre_testigo2);
				cv.put("ife_testigo2",ife_testigo2);
				cv.put("designado_por2", designado_por2);
				cv.put("uso_catalogo", usoC);
				cv.put("hechos", hechos);
				cv.put("infracciones", infracciones);
				cv.put("id_c_infraccion", id_c_infracciones);
				cv.put("uso_suelo", uso_suelo);
				cv.put("densidad", densidad);
				cv.put("manifiesta", manifiesta);
				cv.put("gravedad", gravedad);
				cv.put("dias_plazo", dias_plazo);
				cv.put("fecha_plazo", fecha_plazo);
				cv.put("hora_termino", hora_termino);
				cv.put("id_pago", 0);
				cv.put("pago", 0);
				cv.put("fecha_pago", "");
				cv.put("estatus", "POR CALIFICAR");
				cv.put("capturo", "");
				cv.put("fecha_atiende_juez", "");
				cv.put("fecha_cancelacion", "");
				cv.put("fecha_efectua_multa", "");
				cv.put("vigencia_multa", 0);
				cv.put("fecha_vigencia", "");
				cv.put("multa", "");
				cv.put("observaciones", "");
				cv.put("condominio", condominio);
				cv.put("lote", lote);
				cv.put("manzana", manzana);
				cv.put("referencia", referencia);
				cv.put("correo", correo);
				cv.put("l_alineamiento", alineamiento);
				cv.put("l_construccion", construccion);
				cv.put("id_c_competencia", competencia);
				
				cv.put("entre_calle1", entre_calle1);
				cv.put("entre_calle2", entre_calle2);
				cv.put("responsable_obra", responsable_obra);
				cv.put("registro_responsable", registro_responsable);
				
				cv.put("status", status);
				
				cv.put("identifica", identifica);
				
				cv.put("peticion", peticion);
				cv.put("v_firma", v_firma);
				cv.put("motivo_orden", motivo_orden);
				cv.put("medida_seguridad", medida_seguridad);
				cv.put("articulo_medida", articulo_medida);
				
				cv.put("id_c_inspector3", id_c_inspector3);
				cv.put("id_c_inspector4", id_c_inspector4);
				cv.put("id_c_inspector5", id_c_inspector5);
				cv.put("id_c_inspector6", id_c_inspector6);
				
				cv.put("id_c_competencia1", id_competencia1);
				cv.put("id_c_competencia2", id_competencia2);
				cv.put("id_c_competencia3", id_competencia3);
				cv.put("id_c_competencia4", id_competencia4);
				cv.put("id_c_competencia5", id_competencia5);
				
				cv.put("licencia_giro",licencia_giro);
				cv.put("actividad_giro",actividad_giro);
				cv.put("axo_licencia",axo_licencia);
				
				cv.put("nombre_comercial",nombre_comercial);
				cv.put("sector",sector);

                cv.put("nivel_economico",niec);
                cv.put("reincidencia",rei);

                cv.put("tipo_cedula",tipo_cedula);
                cv.put("folio_peticion",etfoliopeticion);
                cv.put("folio_apercibimiento",etfolioclau);
                cv.put("fecha_apercibimiento",etfechaclau);

                cv.put("numero_sellos",numero_sellos);
                cv.put("decomiso",decomiso);
                cv.put("folio_clausura",etfolioap);
                cv.put("fecha_clausura",etfechap);

                cv.put("tableta",tableta);
                cv.put("version",version);



		
				n = db.insert("Levantamiento", null, cv);				
			}catch (SQLiteException e) {
				Log.e("Insert", e.getMessage());
			}
			
		}
		db.close();
		return n;
	}
	
	public void showMsg(String message) {  
	       Toast toast = Toast.makeText(getApplicationContext(), message, (Toast.LENGTH_LONG));
	       toast.setGravity(0, 0, 15);
	       toast.show();   
	}

	private void setCurrentLocation(Location loc) {
	    currentLocation = loc;
    }
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		this.finish();
	}
	
	@Override
	public void run() {
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		try{
			if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			
				Looper.prepare();
			
				mLocationListener = new MyLocationListener();

				try {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                }catch(SecurityException e) {

                }
				Looper.loop(); 
				Looper.myLooper().quit(); 
			
			} else {
				Looper.prepare();
				Toast toast = Toast.makeText(InfraccionesActivity.this,"SeÔøΩal GPS no encontrada", Toast.LENGTH_LONG);
				toast.setGravity(0, 0, 15);
				toast.show();
				builAlert();
			}
		}catch (Exception e) {
			Log.e("gps", e.getMessage());
		}
	}
	
	public void builAlert() {
		final AlertDialog.Builder alert = new AlertDialog.Builder(InfraccionesActivity.this);
		alert.setMessage("El GPS  esta deshabilitado. Entrar a configuraciÔøΩn")
		.setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),0);
				onDestroy();
			}
		}).setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog al = alert.create();
		al.show();
		
	}
	
	
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mLocationManager.removeUpdates(mLocationListener);
	    	if (currentLocation!=null) {
	    		//Toast.makeText(getBaseContext(), "Latitude: " + currentLocation.getLatitude() + " Longitude: " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
	    		Log.i("Coordenadas", "Latitude: " + currentLocation.getLatitude() * 1E6 + " Longitude: " + (int)currentLocation.getLongitude() * 1E6);
	    		latitud = currentLocation.getLatitude();
	    		longitud = currentLocation.getLongitude();
	    		etLatitud.setText(Double.toString(latitud));
	    		etLongitud.setText(Double.toString(longitud));
	    	}
		}
	};

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        etOtro.setVisibility(View.GONE);
        switch (checkedId) {
            case R.id.rbVisitado:
                propiedad = "El Visitado";
                break;
            case R.id.rbdesconoce:
                propiedad = "Se desconoce";
                break;

            case R.id.rbOtro:
                propiedad = "";
                etOtro.setVisibility(View.VISIBLE);
                break;

            case R.id.rbF2:
                tipoEntrega = 0;
                Log.v("tipoEntrega",String.valueOf(tipoEntrega));
                break;

            case R.id.rbN2:
                tipoEntrega = 1;
                Log.v("tipoEntrega",String.valueOf(tipoEntrega));
                break;

            case R.id.rbC2:
                tipoEntrega = 2;
                Log.v("tipoEntrega",String.valueOf(tipoEntrega));
                break;

                default:

                    break;
        }
    }

    private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			if (location != null) {
                Toast toast =Toast.makeText(getBaseContext(), "SeÔøΩal GPS encontrada", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 15);
				toast.show();
                setCurrentLocation(location);
                handler.sendEmptyMessage(0);
            }
		}

		public void onProviderDisabled(String provider) {
			
		}

		public void onProviderEnabled(String provider) {
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
        
    }
	
	public boolean salir() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(InfraccionesActivity.this);
		dialog.setTitle("¿Esta seguro de salir?");
		dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		dialog.setMessage("¿Esta seguro de salir del llenado de la infracción?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				res = true;
				InfraccionesActivity.this.onDestroy();
				if(thread != null)
					handler.sendEmptyMessage(0);
				
				/*if(!consu)
					almacenarFoto(etNumeroActa.getText().toString());*/
			}
		});
		AlertDialog alert = dialog.create();
		alert.show();
		return res;
	}
	
	public void almacenarFoto(String numeroActa) {
		boolean d1 = false,d2 = false,d3 = false,d4 = false;
		GestionBD gestionar = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		if(db != null) {
			Cursor c = db.rawQuery("Select * from Fotografia where numero_acta = '" + numeroActa + "'", null);
			try {
				if(c.moveToFirst())
				do {
					ContentValues cv = new ContentValues();
					
					if(etDFoto.getVisibility() == View.VISIBLE & !d1) {
						cv.put("descripcion", etDFoto.getText().toString());
						d1 = true;
					}
					else if(etDFoto1.getVisibility() == View.VISIBLE & !d2) {
						cv.put("descripcion", etDFoto1.getText().toString());
						d2 = true;
					}
					else if(etDFoto2.getVisibility() == View.VISIBLE & !d3) {
						cv.put("descripcion", etDFoto2.getText().toString());
						d3 = true;
					}
					else if(etDFoto3.getVisibility() == View.VISIBLE & !d4) {
						cv.put("descripcion", etDFoto3.getText().toString());
						d3 = true;
					}
					
					System.err.println(cv.get("descripcion"));
					
					db.update("Fotografia", cv, "id_fotografia = " + c.getInt(1), null);
				}while (c.moveToNext());
			}catch(SQLiteException e){
				Log.e("ERROR in foto", e.getMessage());
			}
			finally {
				db.close();
				c.close();
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			salir();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public ArrayList<String>  buscarNumeroActa() {
		this.numero_acta.clear();
		GestionBD gestionar = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		if(db != null) {
			Cursor c = db.rawQuery("Select * from numero_acta", null);
			try {
				if(c.moveToFirst())
				do {
					this.numero_acta.add(c.getString(1));
				}while (c.moveToNext());
			}catch(SQLiteException e){
				Log.e("ERROR in num_act", e.getMessage());
			}
			finally {
				db.close();
				c.close();
			}
		}
		return this.numero_acta;
	}
	
	public boolean na() {
		boolean r  = false;
		for (int i = 0; i < numero_acta.size(); i++) {
			if (numero_acta.get(i).equalsIgnoreCase(etNumeroActa.getText().toString())) {
					r = true;
			}
		}
		return r;
	}
	
	public String ultimo() {
		String srt = "";
		GestionBD gestionar = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		if (db != null) {
			Cursor c = db.query("numero_acta", null, "id_numero_acta", null, null, null, "id_numero_acta DESC LIMIT 0,1");
			try {
				if(c.moveToFirst()) {
					do {
						srt = c.getString(1);
					}while(c.moveToNext());
				}
			} catch (SQLiteException e) {
				Log.e("ERROR in ultimo", e.getMessage());
			}
		}
		return srt;
	}
	
	public long ingresarNumeroActa(String numero_acta, String inspector, String fecha) {
		long n = 0;
		GestionBD gestionarDB =  new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarDB.getWritableDatabase();
    	
    	if(db != null) {
    		try {
				ContentValues cv = new ContentValues();
				cv.put("numero_acta", numero_acta);
				cv.put("inspector", inspector);
				cv.put("fecha", fecha);
				
				n = db.insert("numero_acta", null, cv);
			} catch (Exception e) {
				Log.e("Error in numero_acta", e.getMessage());
			}
    		finally{
    			db.close();
    		}
    	}
    	
		return n;
	}
	
	public void imprimir() {
		int len;
		final int MITAD = 40;
		String src;
		String [] txt;
		String path = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_");
		File f = new File(path);
		f.mkdirs();
		
		try{
			
			File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");
			//file.mkdirs();
			//File f = new File(path, etNumeroActa.getText().toString().replace("/", "_")+ ".txt");
			
			
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			String  ext = file.getName().substring(file.getName().indexOf(".")+1);
			String tipo = mime.getMimeTypeFromExtension(ext);
			
			/*intent.setType(tipo);
			intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
			//intent.setDataAndType(Uri.fromFile(file), tipo);
			startActivity(Intent.createChooser(intent, "Send"));
			//startActivity(intent);*/
			String [] na = etNumeroActa.getText().toString().split("/");
			Log.i("fecha", na[3] + "/" + na[4] + "/" + na[5]);
			fecha = na[3] + "/" + na[4] + "/" + na[5];
			String [] fechas = fecha.split("/");
			int dia, mes,a;
			String me;
			for (int i = 0; i < fechas.length; i++) {
				System.out.println(fechas[i]);
			}
			
			dia = Integer.parseInt(fechas[0]);
			mes = Integer.parseInt(fechas[1]);
			a = Integer.parseInt(fechas[2].substring(2, 4));
			System.out.println("entro");
			me = Justificar.mes(mes);
			
			OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file),"cp1252");
			os.write(".");
			os.write(Justificar.PY(4));
			os.write(Justificar.PX(58, Justificar.Conversion(etNumeroActa.getText().toString())));
			os.write(Justificar.PY(2));
			os.write(Justificar.PX(33, Justificar.Conversion(hora + " horas del " +  dia + " de " + me + " del aÔøΩo 20" + a)));
			os.write(Justificar.PY(1));
			//os.write("     " + arrayfecha[0]+ "\t\t" + mes + "\t\t" + arrayfecha[2] );
			String str = (etCondominio.getText().toString().trim().equalsIgnoreCase("")) ? etFraccionamiento.getText().toString() : etFraccionamiento.getText().toString() + ", Condominio "  + etCondominio.getText().toString();
			//txt = Justificar.justifocarTexto(Justificar.Conversion(HECH));
			txt = Justificar.justifocarTexto(Justificar.Conversion(str));
			
			if(txt.length != 1)
				txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)),31);
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(Justificar.PX(31, Justificar.Conversion(txt[i]))));
				os.write(Justificar.PY(1));
			}
			
			txt = Justificar.justifocarTexto(Justificar.Conversion(etCalle.getText().toString() + " nÔøΩmero " + etNumero.getText().toString() + " " + etNuemroInterior.getText().toString() ),24);
			
			if (id == 3 | id == 4) {
				for (int i = 0; i < txt.length; i++) {
					os.write(Justificar.PX(24, Justificar.Conversion(txt[i])));
					os.write(Justificar.PY(1));
				}
			
			}else{
				String dato = "";
				dato += " calle " + Justificar.Conversion(etCalle.getText().toString()); 
				if (!etNumero.getText().toString().equalsIgnoreCase("")) 
					dato = Justificar.Conversion("nÔøΩmero " + etNumero.getText().toString()) + " " + Justificar.Conversion(etNuemroInterior.getText().toString());
				if (!etLote.getText().toString().equalsIgnoreCase(""))
					dato += " lote " + Justificar.Conversion(etLote.getText().toString());
				if (!etManzana.getText().toString().equalsIgnoreCase(""))
					dato += " manzana " + Justificar.Conversion(etManzana.getText().toString());
				if(!etReferencia.getText().toString().equalsIgnoreCase(""))
					dato += " " + Justificar.Conversion(etReferencia.getText().toString());
				
				txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)));
				if(txt.length != 1)
					txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)),24);
				for (int i = 0; i < txt.length; i++) {
					os.write(Justificar.PX(24, Justificar.Conversion(txt[i])));
					os.write(Justificar.PY(1));
				}
				
			}
			str = Justificar.Conversion(etApellidoP.getText().toString() + " " + etApellidoM.getText().toString() + " " + etPropietario.getText().toString());
			if (str.length() > 79) 
				str = str.substring(0, 79);
			txt = Justificar.justifocarTexto(str);
			System.err.println(txt.length + " completo " + str.length() + " caracteres");
			if(txt.length != 1) 
				txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(str)),20);
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.PX(20, Justificar.Conversion(txt[i])));
				os.write(Justificar.PY(1));
			}
			
			
			os.write(Justificar.PX(30, Justificar.Conversion(etNombreV.getText().toString())));
			//os.write("Nombre del Visitante");
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(18, Justificar.Conversion(etVIdentifica.getText().toString())));
			//os.write("Identifica");
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(15, Justificar.Conversion(spManifiesta .getSelectedItem().toString())));
			//os.write("Manifiesta ser");
			
			int l = 0,ln = 0,lno = 0,lt= 0,s = 0,sn = 0;
			if (/*id == 3 |*/ id == 4) {
				l = 1;
				ln = 26;
				lno = 24;
				lt = 51;
				s = 1; 
				sn = 0;
			}
			else { 
				l = 66;
				ln = 12;
				lno = 9;
				lt = 34;
				s = 0;
				sn = 1;
			}
			os.write(Justificar.PY((7+s)));
			if (!consu) {
				int orden;
				if(etNum.getText().toString().equalsIgnoreCase("")) {
					orden = 0;
				}else {
					orden = Integer.parseInt(etNum.getText().toString());
				}
				Formatter fmt = new Formatter();
				
				
				fmt.format("%04d", orden);
				//if(!String.valueOf(orden).equalsIgnoreCase("")){
				
					os.write(Justificar.PX(l, tvOV.getText().toString() + fmt));
				//}
				fmt.close();
			}
			else {
				os.write(Justificar.PX(l, orde));
			}
			os.write(Justificar.PY(sn));
			os.write(Justificar.PX(ln, Justificar.Conversion(spnombre.getSelectedItem().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(lno, Justificar.Conversion(etNoI.getText().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(lt, Justificar.Conversion(tipoActa))); 
			os.write(Justificar.PY(4));
			//os.write(Justificar.Conversion(etSeleccion.getText().toString()));
			int salto = 0;
			
			txt = Justificar.justifocarTexto(etSeleccion.getText().toString());
			len = txt.length;
			src = etSeleccion.getText().toString().trim();
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(txt[i]));
				os.write(Justificar.PY(1));
			}
			txt = Justificar.justifocarTexto(Justificar.Conversion(hech));
			len += txt.length;
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(txt[i]));
				os.write(Justificar.PY(1));
			}
			//os.write(Justificar.Conversion(etInfraccion.getText().toString()));
			src = etInfraccion.getText().toString();
			txt = Justificar.justifocarTexto(Justificar.Conversion(etInfraccion.getText().toString()));
			len += txt.length;
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(txt[i]));
				os.write(Justificar.PY(1));
			}
			
			
			txt = Justificar.justifocarTexto(Justificar.Conversion(DECLARA));
			len += txt.length;
			
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(txt[i]));
				os.write(Justificar.PY(1));
			}
			src = etManifiesta.getText().toString();
			txt = Justificar.justifocarTexto(Justificar.Conversion(etManifiesta.getText().toString() + ", Identificadores " + id_hechos.replace(",", "|") + " gravedad " + spgravedad.getSelectedItem().toString()));
			len += txt.length;
			for (int i = 0; i < txt.length; i++) {
				os.write(Justificar.Conversion(txt[i]));
				os.write(Justificar.PY(1));
			}
			salto = (13-len);
			System.out.println("salto " + salto);
			
			//os.write("MANIFFIESTACION .. .   ----> Aqui va uno de los textos grandes que hay qeu tener que justificar o alinearlos a la izquierda; aqui le escribo para haber el ejemplo de las impresiones; seguimos en contacto");
			os.write(Justificar.PY(7+salto));
			os.write(Justificar.PX(25, hr));
			
			//	FIRMAS
			os.write(Justificar.PY(3));
			os.write(Justificar.PX(1, Justificar.Conversion(spnombre.getSelectedItem().toString().trim())));
			len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			os.write(Justificar.PX(len, Justificar.Conversion(etNombreV.getText().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(1, Justificar.Conversion(etIfeI.getText().toString().trim())));
			len = MITAD - etIfeI.getText().toString().trim().length();
			os.write(Justificar.PX(len, Justificar.Conversion(etVIdentifica.getText().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(1, Justificar.Conversion("Inspector".trim())));
			len = MITAD - "Inspector".trim().length();
			os.write(Justificar.PX(len, "Visitado"));
			os.write(Justificar.PY(2));
			os.write(Justificar.PX(1, Justificar.Conversion(etNombreT.getText().toString().trim())));
			len = MITAD - etNombreT.getText().toString().trim().length();
			os.write(Justificar.PX(len, Justificar.Conversion(etNombreT1.getText().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(1, Justificar.Conversion(etIfeT.getText().toString().trim())));
			len = MITAD - etIfeT.getText().toString().trim().length();
			os.write(Justificar.PX(len, Justificar.Conversion(etIfeT2.getText().toString())));
			os.write(Justificar.PY(1));
			os.write(Justificar.PX(1, "Testigo".trim()));
			len = MITAD - "Testigo".trim().length();
			os.write(Justificar.PX(len, Justificar.Conversion("Testigo")));
			os.flush();
			os.close();
		}catch (IOException e) {
			Log.e("Error al abrir", e.getMessage());
		}
	}

    public String vigencia_inicial(String v){
        String vigencia_inicial="";
        if(v.equals("01")){
            vigencia_inicial="Enero";
        }
        if(v.equals("02")){
            vigencia_inicial="Febrero";
        }
        if(v.equals("03")){
            vigencia_inicial="Marzo";
        }
        if(v.equals("04")){
            vigencia_inicial="Abril";
        }
        if(v.equals("05")){
            vigencia_inicial="Mayo";
        }
        if(v.equals("06")){
            vigencia_inicial="Junio";
        }
        if(v.equals("07")){
            vigencia_inicial="Julio";
        }
        if(v.equals("08")){
            vigencia_inicial="Agosto";
        }
        if(v.equals("09")){
            vigencia_inicial="Septiembre";
        }
        if(v.equals("10")){
            vigencia_inicial="Octubre";
        }
        if(v.equals("11")){
            vigencia_inicial="Noviembre";
        }
        if(v.equals("12")){
            vigencia_inicial="Diciembre";
        }

        return vigencia_inicial;
    }

    public String vigencia_final(String v){
        String vigencia_inicial="";
        if(v.equals("01")){
            vigencia_inicial="Enero";
        }
        if(v.equals("02")){
            vigencia_inicial="Febrero";
        }
        if(v.equals("03")){
            vigencia_inicial="Marzo";
        }
        if(v.equals("04")){
            vigencia_inicial="Abril";
        }
        if(v.equals("05")){
            vigencia_inicial="Mayo";
        }
        if(v.equals("06")){
            vigencia_inicial="Junio";
        }
        if(v.equals("07")){
            vigencia_inicial="Julio";
        }
        if(v.equals("08")){
            vigencia_inicial="Agosto";
        }
        if(v.equals("09")){
            vigencia_inicial="Septiembre";
        }
        if(v.equals("10")){
            vigencia_inicial="Octubre";
        }
        if(v.equals("11")){
            vigencia_inicial="Noviembre";
        }
        if(v.equals("12")){
            vigencia_inicial="Diciembre";
        }

        return vigencia_inicial;
    }
    public String mes(String texto){
        String retorno="";
        if(texto.equals("01") || texto.equals("1") ){
            retorno="Enero";

        }
        if(texto.equals("02") || texto.equals("2") ){
            retorno= "Febrero";

        }
        if(texto.equals("03") || texto.equals("3") ){
            retorno="Marzo";

        }
        if(texto.equals("04") || texto.equals("4") ){
            retorno= "Abril";

        }
        if(texto.equals("05") || texto.equals("5") ){
            retorno= "Mayo";

        }
        if(texto.equals("06") || texto.equals("6") ){
            retorno= "Junio";

        }
        if(texto.equals("07") || texto.equals("7") ){
            retorno="Julio";

        }
        if(texto.equals("08") || texto.equals("8") ){
            retorno="Agosto";

        }
        if(texto.equals("09") || texto.equals("9") ){
            retorno="Septiembre";

        }
        if(texto.equals("10") ){
            retorno="Octubre";

        }
        if(texto.equals("11")){
            retorno= "Noviembre";

        }
        if(texto.equals("12")){
            retorno="Diciembre";

        }


        return retorno;
    }

    public String especialNum(int numero){
        String caracter="";

        if(numero==1){
           caracter="1";
        }
        if(numero==2){
            caracter="2";
        }
        if(numero==3){
            caracter="3";
        }
        if(numero==4){
            caracter="4";
        }
        if(numero==5){
            caracter="5";
        }
        if(numero==6){
            caracter="6";
        }
        if(numero==7){
            caracter="7";
        }
        if(numero==8){
            caracter="8";
        }
        if(numero==9){
            caracter="9";
        }
        if(numero==10){
            caracter="10";
        }
        if(numero==11){
            caracter="11";
        }
        if(numero==12){
            caracter="12";
        }
        if(numero==13){
            caracter="13";
        }
        if(numero==14){
            caracter="14";
        }
        if(numero==15){
            caracter="15";
        }


        return caracter;
    }
	public void imprimir(String formato) throws IOException, DocumentException {
		
		int len =0;
		final int MITAD = 135;
		String src;
		String [] txt;
		String path = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_");
		File f = new File(path);
		f.mkdirs();
		
		Document doc = null;
		File file = null;
		FileOutputStream ficheroPdf = null;
		PdfWriter write = null;
		BaseFont bf = null;
		int marginleft=0;
		int margingright=0;
		if(formato.equalsIgnoreCase("infraccion")){
		    marginleft=25;
		    margingright=31;
        }else{
		    marginleft=19;
		    margingright=19;

        }
		try {
			doc = new Document(PageSize.LEGAL,marginleft,margingright,20,20);
		    file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".pdf");
		    ficheroPdf = new FileOutputStream(file.getAbsoluteFile());
		    
		    write = PdfWriter.getInstance(doc, ficheroPdf);
		    
		    
		} catch (Exception e) {
			
		}

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
		Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
       String [] na = thisDate.split("/");
		Log.i("fecha", na[0] + "/" + na[1] + "/" + na[2]);
		fecha = na[0] + "/" + na[1] + "/" + na[2];
		String [] fechas = fecha.split("/");
		int dia, mes,a;
		String me;
		for (int i = 0; i < fechas.length; i++) {
			System.out.println(fechas[i]);
		}
		
		dia = Integer.parseInt(fechas[0]);
		mes = Integer.parseInt(fechas[1]);
		String an = fechas[2];
		//a = Integer.parseInt(fechas[2].substring(2, 4));
		a = Integer.parseInt(fechas[2]);
		System.out.println("entro");
		me = Justificar.mes(mes);
		String medida = etMedida.getText().toString() + " del " + medidas1;
		
		System.err.println(medida);
        //BaseFont unicode = BaseFont.createFont(Font., BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font1 = new Font(Font.HELVETICA,9.7f,Color.BLACK);
        if (formato.equalsIgnoreCase("infraccion")) {
                String cuerpoInfra="";
                String cuerpoInfra21="";

                int banderacorte=0;
					//ACTA DE INFRACCION
					try {

                        DottedLineSeparator dottedLineSeparator = new DottedLineSeparator();
                        dottedLineSeparator.setGap(7);

                        Chunk chunk = new Chunk(dottedLineSeparator);
						
						//File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");
						
						
						MimeTypeMap mime = MimeTypeMap.getSingleton();
						String  ext = file.getName().substring(file.getName().indexOf(".")+1);
						String tipo = mime.getMimeTypeFromExtension(ext);
						
						
						Paragraph p;
						
						doc.open();
						
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        Bitmap bitmap = null;
						/*if(id != 4)
						    bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.acta_1);
						else
						    bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.acta_c);*/

                        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.infraccion2023);
					    bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
					    Image img;
					    
					    try {
					    	
					    	img = Jpeg.getInstance(stream.toByteArray());
					        img.setAbsolutePosition(0, 11);
					        
					        float width = doc.getPageSize().getWidth();
					        float height = doc.getPageSize().getHeight();
					        
					        img.scaleToFit(width, height);
					        doc.add(img);
					        
						} catch (BadElementException e) {
							System.err.println(e.getMessage() + " BadElementException");
						} catch (MalformedURLException e) {
							System.err.println(e.getMessage() + " MalformedURLException");
						} catch (IOException e) {
							System.err.println(e.getMessage() + " IOException");
						} catch (DocumentException e) {
							System.err.println(e.getMessage() + " DocumentException");
						}
					    
					    doc.add(new Paragraph(" "));
					    doc.add(new Paragraph(" ",font1));
                        doc.add(new Paragraph(" ",font1));
                        doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.BOLD,9,Color.BLACK)));
					    
					    Font font = new Font(Font.BOLD,10,Color.BLACK);
					    
					    
					    PdfContentByte canvas = write.getDirectContent();
					    

					    //DIRECCION
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(30, 921);
				        canvas.showText(direccion);
				        canvas.endText();
				        canvas.restoreState();

				        //ZONA
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(200, 921);
				        canvas.showText(spZona.getSelectedItem().toString());
				        canvas.endText();
				        canvas.restoreState();

				        //NUMERO DE ACTA
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 11);
				        canvas.moveText(450, 921);
				        canvas.showText(etNumeroActa.getText().toString());
				        canvas.endText();
				        canvas.restoreState();

                        String uso = "";
                        String vig = etVigI.getText().toString();

                        String [] vig1 = vig.split("-");

                        int d,m,ax;

                        d=Integer.parseInt(vig1[2]);
                        m=Integer.parseInt(vig1[1]);
                        ax=Integer.parseInt(vig1[0]);

                        String prop = "",numero = "";

                        Log.e("PROPIEDAD",propiedad.equalsIgnoreCase("El Visitado") + "");
                        if(propiedad.isEmpty()) {
                            prop = etOtro.getText().toString().trim();
                        }else {
                            if (propiedad.equalsIgnoreCase("El Visitado"))
                                prop = etNombreV.getText().toString();
                            else
                                prop = propiedad;
                        }
                        if(prop.equals("")){
                            prop="Se desconoce";
                        }
                        if(etNuemroInterior.getText().toString().trim().equalsIgnoreCase(""))
                            numero = etNumero.getText().toString() + " " + etNuemroInterior.getText().toString();
                        else
                            numero = etNumero.getText().toString() + " Int.  " + etNuemroInterior.getText().toString();

                        String datos = "";
                        if(numeroOV.length()>2) {

                            if(fechaOV.length()>1) {
                                String[] fecha_ov = numeroOV.split("/");

                                datos = "mediante y en cumplimiento de la Orden de Visita folio número "
                                        + numeroOV +" emitida por el Director de Inspección y Vigilancia de Zapopan, Jalisco, el día " + fecha_ov[3] +" de "+mes(fecha_ov[4]) +" del año "+fecha_ov[5]+ " misma que en original exhibo y en original legible entrego " +
                                        "al visitado, " + etNombreV.getText().toString() + ",";


                            }else{
                              if(etfechaOV.getText().toString().length()>2) {
                                  String[] fecha_ov = etfechaOV.getText().toString().split("-");

                                  datos = "mediante y en cumplimiento de la Orden de Visita folio número "
                                          + numeroOV + "  emitida por el Director de Inspección y Vigilancia de Zapopan, Jalisco, el día " + fecha_ov[0] + " de " + mes(fecha_ov[1]) + " del año " + fecha_ov[2] + " misma que en original exhibo y en original legible entrego " +
                                          "al visitado, " + etNombreV.getText().toString() + ",";
                              }else{
                                  String[] fecha_ov = numeroOV.split("/");

                                  datos = "mediante y en cumplimiento de la Orden de Visita folio número "
                                          + numeroOV +"  emitida por el Director de Inspección y Vigilancia de Zapopan, Jalisco, el día " + fecha_ov[3] +" de "+mes(fecha_ov[4]) +" del año "+fecha_ov[5]+ " misma que en original exhibo y en original legible entrego " +
                                          "al visitado, " + etNombreV.getText().toString() + ",";

                              }

                            }

                        } else {
                            datos = "en términos de lo dispuesto por el artículo 73, segundo párrafo, de la Ley del Procedimiento Administrativo del Estado de Jalisco,";
                        }
                        String tipoentrega="";
                        /*if(id==2 && id==3 &&id==5){
                            if(tipoEntrega == 2){
                                tipoentrega="fijándose un tanto del acta en lugar seguro y visible del domicilio en que se actúa al encontrar cerrado el domicilio, en términos de lo dispuesto por el artículo 87 de la Ley del Procedimiento Administrativo del Estado de Jalisco.";
                            }else if(tipoEntrega == 1){
                                tipoentrega="fijándose un tanto del acta en lugar seguro y visible del domicilio en que se actúa al encontrar negativa a recibirla, en términos de lo dispuesto por el artículo 87 de la Ley del Procedimiento Administrativo del Estado de Jalisco.";
                            }else if(tipoEntrega == 0){
                                tipoentrega="quedando copia legible en poder del interesado y firmando constancia los que en ella intervinieron, quisieron y supieron hacerlo.";
                            }
                        }else{*/
                            if(cbDatos.isChecked() && !cbDatos2.isChecked()){
                                tipoentrega="el visitado no proporciono dato alguno de su identidad, por lo que se lleva a cabo la presente diligencia con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus artículos 86 y 87, con descripcion de media filiacion.";
                                tipoEntrega=0;
                            }
                            if(cbDatos2.isChecked() && !cbDatos.isChecked()){
                                tipoentrega="en ausencia de persona alguna, se llevó a cabo la presente diligencia por cédula; con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus articulos 86 y 87.";
                                tipoEntrega=2;
                            }

                            /*243, 197*/
                        /*}*/



                        String peticionb="";
                        if(spPeticion.getSelectedItem().toString().equals("Flagrancia")){
                            peticionb=spPeticion.getSelectedItem().toString();
                        }else{
                         if(etfoliopeticion.getText().length()>2){
                             peticionb=spPeticion.getSelectedItem().toString()+" con folio "+etfoliopeticion.getText().toString();

                         }else{
                             peticionb=spPeticion.getSelectedItem().toString();

                         }

                        }


                        String vigencia=MainActivity.vigencia;
                        String vigencia_inicial=MainActivity.vigencia_inicial;
                        String []recorte1=vigencia.split("-");
                        String []recorte2=vigencia_inicial.split("-");
                        String diaIni=recorte2[2];
                        String diavigen=recorte1[2];
                        vigencia_inicial=vigencia_inicial(recorte2[1]);
                        vigencia=vigencia_final(recorte1[1]);

                        Paragraph p2 = null;
                        String testigos="";
                        String nombresT="";
                        if(etNombreT.getText().toString().length()>4 && etNombreT1.getText().toString().length()>4){
                           testigos= "mismos que se identifican con "+spIdentificaT.getSelectedItem().toString().trim() + " " + etIfeT.getText().toString() + " y " + spIdentificaT1.getSelectedItem().toString().trim() + " " + etIfeT2.getText().toString()+" respectivamente; ";
                            nombresT=etNombreT.getText().toString().trim() + " y " + etNombreT1.getText().toString().trim();
                        }
                        if(etNombreT.getText().toString().length()>4 && etNombreT1.getText().toString().length()<=1){
                            testigos= "mismo que se identifica con "+spIdentificaT.getSelectedItem().toString().trim() + " " + etIfeT.getText().toString();
                           nombresT= etNombreT.getText().toString().trim();
                        }
                        if(etNombreT.getText().toString().length()<=1 && etNombreT1.getText().toString().length()>=1){
                            testigos="mismo que se identifica con "+spIdentificaT1.getSelectedItem().toString().trim() + " " + etIfeT2.getText().toString();
                            nombresT=etNombreT1.getText().toString().trim();
                        }
                        String textC="";
                        if(!etCondominio.getText().toString().equals(""))
                            textC="condominio "+etCondominio.getText().toString();

                        String decomiso = "";
                        if(!TextUtils.isEmpty(etDecomiso.getText().toString().trim()))
                            decomiso += ", decomiso: " + etDecomiso.getText().toString().trim() + " ";

				        if(id == 4) {
                            String apercibimiento="";
				            if(etCondominio.getText().toString().trim().length()>1 && etfolioap.getText().toString().length()>1 && etfechap.getText().toString().length()>1){
				                String folioa=etfolioap.getText().toString();
				                String fechap=etfechap.getText().toString();
				                apercibimiento="Dar seguimiento a lo señalado en el previo apercibimiento folio "+folioa+ " de fecha "+fechap+", ";

                            }




                            //String medidasP=etMedida.getText().toString();
                            String medidasP=medidas1;
				            /*if(etMedida.getText().toString().trim().contains("Reglamento de Construcción para el Municipio de Zapopan")){
                                String [] cortes = etMedida.getText().toString().trim().split("Reglamento de Construcción para el Municipio de Zapopan Jalisco", 0);

                                for(int i=0;i<cortes.length;i++){
                                    medidasP+=cortes[i];
                                }

                            }*/


				            String numeroS="";
				            if(etNumeroSellos.getText().toString().trim().length()>0){
				                numeroS="con número de sello(s) "+etNumeroSellos.getText().toString().trim();
                            }
                            String hechos=etSeleccion.getText().toString().trim();
				            Log.e(String.valueOf(hechos.length()),"Cantidad de texto");
                            if(!spuso.getSelectedItem().toString().contains("pública") | !spuso.getSelectedItem().toString().contains("público"))
                                uso = "el uso " + spuso.getSelectedItem().toString() + " ";
                            else
                                uso = spuso.getSelectedItem().toString();

                            cuerpoInfra="En la ciudad de Zapopan, Jalisco, siendo las " + hora + " horas del día " + dia + " de " + me + " del año " + a + ", el suscrito " + spnombre.getSelectedItem().toString() +
                                    " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, "+datos+" me constituí física y legalmente en " + uso +" marcada(o) con el número " +
                                    numero + " de la calle " + etCalle.getText().toString() + " entre las calles " + etEntreC.getText().toString() + " y " + etEntreC1.getText().toString() + " en la colonia y/o fraccionamiento " + etFraccionamiento.getText().toString().trim() + " "+textC+ ",  cerciorándome de ser este el domicilio por coincidir con la nomenclatura oficial y/o georreferencia, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número" +
                                    " "+folio.trim() + ", vigente del " +diaIni+" de " +vigencia_inicial+ " del  "+recorte2[0]+ " al "+diavigen+ " de "+ vigencia +" del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del Gobierno Municipal de Zapopan, Jalisco, ante " + etNombreV.getText().toString() + " quien se identifica con, " + spIdentifica.getSelectedItem().toString().trim() + " " + etVIdentifica.getText().toString().trim() +
                                    ", manifiesta ser " + etVManifiesta.getText().toString() + " del lugar en que se actúa, propiedad de, " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía por lo que fueron designados los C.C. " + nombresT + " por el " + spdesignado.getSelectedItem().toString().trim() +
                                    ", "+ testigos + " así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " +
                                    apercibimiento + hechos + " Los cuales constituyen infracción a lo dispuesto por los artículo(s): 2, 3, 5, 7  FRACCIONES I  a la VI, 34,  167, 168, 169, 171 ," + etInfraccion.getText().toString().trim() + "  Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados en flagrancia, se procede indistintamente con las siguientes medidas: " + medidasP.trim() + " "+ numeroS+".Lo anterior de conformidad a lo dispuesto por los artículo(s): " + etArticulo.getText().toString().trim() + ". En uso de su derecho el visitado manifiesta: " + etManifiesta.getText().toString().trim() +
                                    ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en los artículos 134 y 135 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se haga del conocimiento del o los interesados, entregándolo en la Sindicatura Municipal en la Unidad Administrativa Centro Integral de Servicios Zapopan(CISZ), piso 4(Av. Prolongación Laureles No. 300, Col. Tepeyac). Se da por concluida esta diligencia, siendo las " +
                                    hr + " horas del día " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, quedando copia legible en poder del interesado y firmando constancia los que en ella intervinieron, quisieron y supieron hacerlo.  =Fin del texto=";

                            String mocha=". En uso de su derecho el visitado manifiesta: " + etManifiesta.getText().toString().trim() +
                            ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa del H. Ayuntamiento de Zapopan, Jalisco. Se da por concluida esta diligencia, siendo las " +
                                    hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, quedando copia legible en poder del interesado y firmando constancia los que en ella intervinieron, quisieron y supieron hacerlo.  =Fin del texto=";


                            if(cuerpoInfra.length()>5000) {

                                String recrte = cuerpoInfra;
                                String texto ="";
                                int x=0;
                                if(cuerpoInfra.length()>=4900 && cuerpoInfra.length()<=5050){
                                    cuerpoInfra21 = recrte.substring(0,cuerpoInfra.length()-mocha.length()+1 ) + " (CONTINUA EN EL REVERSO)";
                                    banderacorte = cuerpoInfra.length()-mocha.length()+1;
                                }else {
                                    for (int i = 4900; i < recrte.length(); i++) {
                                        String substring = cuerpoInfra.substring(i, i + 1);
                                        //Log.i(TAG, " caracteresgg1:"+ substring);. En uso


                                        if (substring.equals(" ")) {
                                            //Log.i(TAG, " caracteresgg:"+ substring);
                                            cuerpoInfra21 = recrte.substring(0, i) + " (CONTINUA EN EL REVERSO)";
                                            banderacorte = i;
                                            break;
                                        }


                                        Log.i(TAG, "imprimir: entro al maximo caracteres");
                                    }
                                }
                            }else{
                                cuerpoInfra21=cuerpoInfra;
                            }
                            //bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

                            p2= new Paragraph(cuerpoInfra21,font1);
                            /*p2= new Paragraph("En la ciudad de Zapopan, Jalisco, siendo las " + hora + " horas del día de " + dia + " de " + me + " del año " + a + ", el suscrito " + spnombre.getSelectedItem().toString() +
                                    " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, "+datos+" me constituí física y legalmente en " + uso +" marcada con el número " +
                                    numero + " de la calle " + etCalle.getText().toString() + " entre las calles " + etEntreC.getText().toString() + " y " + etEntreC1.getText().toString() + " en la colonia y/o fraccionamiento " + etFraccionamiento.getText().toString().trim() + " "+textC+ ",  cerciorándome de ser este el domicilio por coincidir con la nomenclatura oficial y/o georreferencia, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número" +
                                    " "+folio + ", vigente del " +diaIni+" de " +vigencia_inicial+ " del "+recorte2[0]+ " a "+diavigen+ " de "+ vigencia +" del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del Gobierno Municipal de Zapopan, Jalisco, ante " + etNombreV.getText().toString() + " quien se identifica con, " + spIdentifica.getSelectedItem().toString().trim() + " " + etVIdentifica.getText().toString().trim() +
                                    " manifiesta ser " + etVManifiesta.getText().toString() + " del lugar en que se actúa, propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía por lo que fueron designados los C.C. " + nombresT + " por el " + spdesignado.getSelectedItem().toString().trim() +
                                    ", "+ testigos + "así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " +
                                    apercibimiento +", "+ hechos + ".Los cuales constituyen infracción a lo dispuesto por los artículo(s): 2, 3, 5, 7  FRACCIONES I  a la VI, 34,  167, 168, 169, 171 ," + etInfraccion.getText().toString().trim() + "  Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados , se procede indistintamente con las siguientes medidas: " + medidasP.trim() + " "+ numeroS+".Lo anterior de conformidad a lo dispuesto por los artículo(s): " + etArticulo.getText().toString().trim() + ". En uso de su derecho el visitado manifiesta: " + etManifiesta.getText().toString().trim() +
                                    ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No.151). Se da por concluida esta diligencia, siendo las " +
                                    hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, quedando copia legible en poder del interesado y firmando constancia los que en ella intervinieron, quisieron y supieron hacerlo.  =Fin del texto=",font1);*/


                        } else if(id == 2) {

                            String apercibimiento="";
                            if(etCondominio.getText().toString().trim().length()>1 && etfolioap.getText().toString().length()>1 && etfechap.getText().toString().length()>1 ){
                                String folioa=etfolioap.getText().toString();
                                String fechap=etfechap.getText().toString();
                                apercibimiento="Dar seguimiento a lo señalado en el previo apercibimiento folio "+folioa+ " de fecha "+fechap+" en lo conducente y en concordancia con la reglamentación aplicable,";

                            }
                            String numeroS="";
                            if(etNumeroSellos.getText().toString().trim().length()>3){
                                numeroS="con numero de sello(s) "+etNumeroSellos.getText().toString().trim();
                            }
                            String hechos=etSeleccion.getText().toString().trim();





                            cuerpoInfra="En la ciudad de Zapopan, Jalisco, siendo las " + hora + " horas del día " + dia + " de " + me + " del  año " + a + ", el suscrito " + spnombre.getSelectedItem().toString() +
                                    " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, "+datos+" me constituí física y legalmente en  " +
                                    etDondeActua.getText().toString().trim() + " marcada (o)  con el número  " + numero + " de  la  calle " + etCalle.getText().toString().trim() + " entre las calles " + etEntreC.getText().toString().trim() + " y " + etEntreC1.getText().toString() + " en la colonia y/o fraccionamiento " +
                                    etFraccionamiento.getText().toString().trim() + textC + ", "+apercibimiento+" cerciorándome de ser este el domicilio donde se realiza la visita de inspección y la actividad comercial, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado " +
                                    "por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número " +" "+ folio + ", vigente del "+diaIni+" de " +vigencia_inicial+ " del "+recorte2[0]+ " a "+diavigen+" de " +  vigencia +" del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del " +
                                    "Gobierno Municipal de Zapopan, Jalisco, ante " + etNombreV.getText().toString().trim() + " quien se identifica con, " + spIdentifica.getSelectedItem().toString().trim() + " " + etVIdentifica.getText().toString() + " manifiesta ser " + etVManifiesta.getText().toString().trim() + " del giro " +
                                    etGiro.getText().toString().trim() + ", propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía acto seguido fueron designados los C.C. " +
                                    nombresT + " por el " + spdesignado.getSelectedItem().toString().trim() + ", " + testigos
                                    + "  así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  " +
                                    "una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " + hechos + " Los cuales constituyen infracción a lo dispuesto por los artículo(s): " + etInfraccion.getText().toString().trim() + " Por encuadrar dichas acciones y/u omisiones en los preceptos legales " +
                                    "indicados y al haber sido detectados en "+peticionb+", se procede indistintamente con las siguientes medidas: " + medidas1 + " "+numeroS + decomiso + ". Lo anterior de conformidad a lo dispuesto por los artículo(s): " + etArticulo.getText().toString().trim() + ". En uso de su derecho el visitado manifiesta: " +
                                    etManifiesta.getText().toString().trim() + ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en los artículos 134 y 135 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, " +
                                    "Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Sindicatura Municipal en la Unidad Administrativa Centro Integral de Servicios Zapopan(CISZ), piso 4(Av. Prolongación Laureles No. 300, Col. Tepeyac). Se da por concluida esta diligencia, siendo las " +
                                    hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, "+tipoentrega+" =Fin del texto=";
                            //Log.i(TAG, "imprimir: entro al maximo caracteres: "+cuerpoInfra.length());

                            String mocha=". En uso de su derecho el visitado manifiesta: " +
                            etManifiesta.getText().toString().trim() + ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, " +
                                    "Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa del H. Ayuntamiento de Zapopan, Jalisco. Se da por concluida esta diligencia, siendo las " +
                                    hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, "+tipoentrega+" =Fin del texto=";

                            Log.e(String.valueOf(cuerpoInfra.length()),"Cantidad de texto");

                            if(cuerpoInfra.length()>5000 ) {

                                String recrte = cuerpoInfra;
                                String texto ="";
                                int x=0;
                                if(cuerpoInfra.length()>=4900 && cuerpoInfra.length()<=5050){
                                    cuerpoInfra21 = recrte.substring(0,cuerpoInfra.length()-mocha.length()+1 ) + " (CONTINUA EN EL REVERSO)";
                                    banderacorte = cuerpoInfra.length()-mocha.length()+1;
                                }else {


                                    for (int i = 4900; i < recrte.length(); i++) {
                                        String substring = cuerpoInfra.substring(i, i + 1);
                                        //Log.i(TAG, " caracteresgg1:"+ substring);. En uso


                                        if (substring.equals(" ")) {
                                            //Log.i(TAG, " caracteresgg:"+ substring);
                                            cuerpoInfra21 = recrte.substring(0, i) + " (CONTINUA EN EL REVERSO)";
                                            banderacorte = i;
                                            break;
                                        }


                                        Log.i(TAG, "imprimir: entro al maximo caracteres");
                                        Log.e("pdf:", "cortes:" + texto);
                                    }
                                }

                            }else{
                                cuerpoInfra21=cuerpoInfra;
                            }


                            p2= new Paragraph(cuerpoInfra21,font1);
                           /* p2= new Paragraph("En la ciudad de Zapopan, Jalisco, siendo las " + hora + " horas del día de " + dia + " de " + me + " del  año " + a + ", el suscrito " + spnombre.getSelectedItem().toString() +
                                    " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, "+datos+" me constituí física y legalmente en  " +
                                    etDondeActua.getText().toString().trim() + " marcada (o)  con el número  " + numero + " de  la  calle " + etCalle.getText().toString().trim() + " entre las calles " + etEntreC.getText().toString().trim() + " y " + etEntreC1.getText().toString() + " en la colonia y/o fraccionamiento " +
                                    etFraccionamiento.getText().toString().trim() + textC + ", "+apercibimiento+" cerciorándome de ser este el domicilio donde se realiza la visita de inspección y la actividad comercial, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado " +
                                    "por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número " +" "+ folio + ", vigente del "+diaIni+" de " +vigencia_inicial+ " del "+recorte2[0]+ " a "+diavigen+" de " +  vigencia +" del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del " +
                                    "Gobierno Municipal de Zapopan, Jalisco, ante " + etNombreV.getText().toString().trim() + " quien se identifica con, " + spIdentifica.getSelectedItem().toString().trim() + " " + etVIdentifica.getText().toString() + " manifiesta ser " + etVManifiesta.getText().toString().trim() + " del giro " +
                                    etGiro.getText().toString().trim() + ", propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía acto seguido fueron designados los C.C. " +
                                    nombresT + " por el " + spdesignado.getSelectedItem().toString().trim() + ", " + testigos
                                     + "  así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  " +
                                    "una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " + hechos + " Los cuales constituyen infracción a lo dispuesto por los artículo(s): " + etInfraccion.getText().toString().trim() + " Por encuadrar dichas acciones y/u omisiones en los preceptos legales " +
                                    "indicados y al haber sido detectados en "+peticionb+", se procede indistintamente con las siguientes medidas: " + etMedida.getText().toString().trim() + " "+numeroS + decomiso + ". Lo anterior de conformidad a lo dispuesto por los artículo(s): " + etArticulo.getText().toString().trim() + ". En uso de su derecho el visitado manifiesta: " +
                                    etManifiesta.getText().toString().trim() + ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, " +
                                    "Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No.151). Se da por concluida esta diligencia, siendo las " +
                                    hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, "+tipoentrega+" =Fin del texto=",font1);*/
                        }else if(id==5){
                            String numeroS="";
                            if(etNumeroSellos.getText().toString().trim().length()>3){
                                numeroS="con numero de sello(s) "+etNumeroSellos.getText().toString().trim();
                            }
                            String hechos=etSeleccion.getText().toString().trim();

                            cuerpoInfra="En la ciudad de Zapopan, Jalisco, siendo las " + hora + " horas del día  " + dia + " de " + me + " del  año " + a + ", el suscrito " + spnombre.getSelectedItem().toString() +
                                    " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, "+datos+" me constituí física y legalmente en  " +
                                    spMeConstitui.getSelectedItem().toString()+ " marcada (o)  con el número  " + numero + " de  la  calle " + etCalle.getText().toString().trim() + " entre las calles " + etEntreC.getText().toString().trim() + " y " + etEntreC1.getText().toString() + " en la colonia y/o fraccionamiento " +
                                    etFraccionamiento.getText().toString().trim() + textC+ ", cerciorándome de ser este el domicilio donde se realiza la visita de inspección y la actividad comercial, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado " +
                                    "por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número " +" "+ folio + ", vigente del "+diaIni+" de " +vigencia_inicial+ " del "+recorte2[0]+ " a "+diavigen+" de " +  vigencia +" del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del " +
                                    "Gobierno Municipal de Zapopan, Jalisco, ante " + etNombreV.getText().toString().trim() + " quien se identifica con, " + spIdentifica.getSelectedItem().toString().trim() + " " + etVIdentifica.getText().toString() + " manifiesta ser " + etVManifiesta.getText().toString().trim() + " del giro " +
                                    etGiro.getText().toString().trim() + ", propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía acto seguido fueron designados los C.C. " +
                                    nombresT + " por el " + spdesignado.getSelectedItem().toString().trim() + ", " + testigos
                                    + " así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  " +
                                    "una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " + hechos + " .Los cuales constituyen infracción a lo dispuesto por los artículo(s): " + etInfraccion.getText().toString().trim() + " Por encuadrar dichas acciones y/u omisiones en los preceptos legales " +
                                    "indicados y al haber sido detectados en "+peticionb+", se procede indistintamente con las siguientes medidas: " + medidas1 + " "+numeroS+  " " + decomiso + ".Lo anterior de conformidad a lo dispuesto por los artículo(s): " + etArticulo.getText().toString().trim() + ". En uso de su derecho el visitado manifiesta: " +
                                    etManifiesta.getText().toString().trim() + ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en los artículos 134 y 135 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, " +
                                    "Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Sindicatura Municipal en la Unidad Administrativa Centro Integral de Servicios Zapopan(CISZ), piso 4(Av. Prolongación Laureles No. 300, Col. Tepeyac). Se da por concluida esta diligencia, siendo las " +
                                    hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, "+tipoentrega+" =Fin del texto=";

                           /* p2= new Paragraph("En la ciudad de Zapopan, Jalisco, siendo las " + hora + " horas del día de " + dia + " de " + me + " del  año " + a + ", el suscrito " + spnombre.getSelectedItem().toString() +
                                    " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, "+datos+" me constituí física y legalmente en  " +
                                    spMeConstitui.getSelectedItem().toString()+ " marcada (o)  con el número  " + numero + " de  la  calle " + etCalle.getText().toString().trim() + " entre las calles " + etEntreC.getText().toString().trim() + " y " + etEntreC1.getText().toString() + " en la colonia y/o fraccionamiento " +
                                    etFraccionamiento.getText().toString().trim() + textC+ ", cerciorándome de ser este el domicilio donde se realiza la visita de inspección y la actividad comercial, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado " +
                                    "por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número " +" "+ folio + ", vigente del "+diaIni+" de " +vigencia_inicial+ " del "+recorte2[0]+ " a "+diavigen+" de " +  vigencia +" del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del " +
                                    "Gobierno Municipal de Zapopan, Jalisco, ante " + etNombreV.getText().toString().trim() + " quien se identifica con, " + spIdentifica.getSelectedItem().toString().trim() + " " + etVIdentifica.getText().toString() + " manifiesta ser " + etVManifiesta.getText().toString().trim() + " del giro " +
                                    etGiro.getText().toString().trim() + ", propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía acto seguido fueron designados los C.C. " +
                                    nombresT + " por el " + spdesignado.getSelectedItem().toString().trim() + ", " + testigos
                                    + " así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  " +
                                    "una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " + hechos + " .Los cuales constituyen infracción a lo dispuesto por los artículo(s): " + etInfraccion.getText().toString().trim() + " Por encuadrar dichas acciones y/u omisiones en los preceptos legales " +
                                    "indicados y al haber sido detectados en "+peticionb+", se procede indistintamente con las siguientes medidas: " + etMedida.getText().toString().trim() + " "+numeroS+  " " + decomiso + ".Lo anterior de conformidad a lo dispuesto por los artículo(s): " + etArticulo.getText().toString().trim() + ". En uso de su derecho el visitado manifiesta: " +
                                    etManifiesta.getText().toString().trim() + ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, " +
                                    "Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No.151). Se da por concluida esta diligencia, siendo las " +
                                    hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, "+tipoentrega+" =Fin del texto=",font1);
*/
                            String mocha=". En uso de su derecho el visitado manifiesta: " +
                            etManifiesta.getText().toString().trim() + ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, " +
                                    "Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa  del H. Ayuntamiento de Zapopan, Jalisco. Se da por concluida esta diligencia, siendo las " +
                                    hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, "+tipoentrega+" =Fin del texto=";
                            if(cuerpoInfra.length()>5000) {

                                String recrte = cuerpoInfra;
                                String texto ="";
                                int x=0;
                                if(cuerpoInfra.length()>=4900 && cuerpoInfra.length()<=5050){
                                    cuerpoInfra21 = recrte.substring(0,cuerpoInfra.length()-mocha.length()+1 ) + " (CONTINUA EN EL REVERSO)";
                                    banderacorte = cuerpoInfra.length()-mocha.length()+1;
                                }else {
                                    for (int i = 4900; i < recrte.length(); i++) {
                                        String substring = cuerpoInfra.substring(i, i + 1);
                                        //Log.i(TAG, " caracteresgg1:"+ substring);. En uso

                                        if (substring.equals(" ")) {
                                            //Log.i(TAG, " caracteresgg:"+ substring);
                                            cuerpoInfra21 = recrte.substring(0, i) + " (CONTINUA EN EL REVERSO)";
                                            banderacorte = i;
                                            break;
                                        }


                                        Log.i(TAG, "imprimir: entro al maximo caracteres");
                                    }
                                }
                            }else{
                                cuerpoInfra21=cuerpoInfra;
                            }
                            p2= new Paragraph(cuerpoInfra21,font1);

                        }

                        p2.setAlignment(Element.ALIGN_JUSTIFIED);
                        p2.add(chunk);
                        doc.add(p2);






				        //GRAVEDAD
                        if(id!=0){

                            if(!cbDatos.isChecked() && !cbDatos2.isChecked()){

                                String []imprimir = Justificar.justifocarTexto1(spnombre.getSelectedItem().toString().trim().toUpperCase(), 26);
                                float salto=248;
                                for(int i=0;i<imprimir.length;i++){
                                    canvas.saveState();
                                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                    canvas.beginText();
                                    canvas.setFontAndSize(bf, 8);
                                    canvas.moveText(33, salto);
                                    canvas.showText(imprimir[i]);
                                    canvas.endText();
                                    canvas.restoreState();

                                    salto-=9;
                                }

                                String []imprimir2 = Justificar.justifocarTexto1(etNombreV.getText().toString().trim().toUpperCase(), 26);
                                float salto2=248;
                                for(int i=0;i<imprimir2.length;i++){
                                    canvas.saveState();
                                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                    canvas.beginText();
                                    canvas.setFontAndSize(bf, 8);
                                    canvas.moveText(185, salto2);
                                    canvas.showText(imprimir2[i]);
                                    canvas.endText();
                                    canvas.restoreState();

                                    salto2-=9;


                                }

                                if(!etNombreT.getText().toString().trim().isEmpty()) {
                                    String[] imprimir3 = Justificar.justifocarTexto1(etNombreT.getText().toString().trim().toUpperCase(), 26);
                                    float salto3 = 248;
                                    for (int i = 0; i < imprimir3.length; i++) {
                                        canvas.saveState();
                                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                        canvas.beginText();
                                        canvas.setFontAndSize(bf, 8);
                                        canvas.moveText(320, salto3);
                                        canvas.showText(imprimir3[i]);
                                        canvas.endText();
                                        canvas.restoreState();

                                        salto3 -= 9;
                                    }
                                }
                                if(!etNombreT1.getText().toString().trim().isEmpty()){
                                    String[] imprimir4 = Justificar.justifocarTexto1(etNombreT1.getText().toString().trim().toUpperCase(), 26);
                                    float salto4 = 248;
                                    for (int i = 0; i < imprimir4.length; i++) {
                                        canvas.saveState();
                                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                        canvas.beginText();
                                        canvas.setFontAndSize(bf, 8);
                                        canvas.moveText(455, salto4);
                                        canvas.showText(imprimir4[i]);
                                        canvas.endText();
                                        canvas.restoreState();

                                        salto4 -= 9;
                                    }
                                }




                            }

                            if(cbDatos.isChecked() && !cbDatos2.isChecked()){

                                String []imprimir2 = Justificar.justifocarTexto1(spnombre.getSelectedItem().toString().trim().toUpperCase(), 26);
                                float salto2=248;
                                for(int i=0;i<imprimir2.length;i++){
                                    canvas.saveState();
                                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                    canvas.beginText();
                                    canvas.setFontAndSize(bf, 8);
                                    canvas.moveText(33, salto2);
                                    canvas.showText(imprimir2[i]);
                                    canvas.endText();
                                    canvas.restoreState();

                                    salto2-=9;
                                }



                                tipoentrega="El visitado no proporcionó dato alguno de su identidad, por lo que se lleva a cabo la presente diligencia con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus artículos 86 y 87, con descripción de media filiación.";
                                String []imprimir = Justificar.justifocarTexto1(tipoentrega, 35);
                                float salto=310;
                                for(int i=0;i<imprimir.length;i++){
                                    canvas.saveState();
                                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                    canvas.beginText();
                                    canvas.setFontAndSize(bf, 8);
                                    canvas.moveText(175, salto);
                                    canvas.showText(imprimir[i]);
                                    canvas.endText();
                                    canvas.restoreState();

                                    salto-=10;
                                }

                                if(!etNombreT.getText().toString().trim().isEmpty()) {
                                    String[] imprimir3 = Justificar.justifocarTexto1(etNombreT.getText().toString().trim().toUpperCase(), 26);
                                    float salto3 = 248;
                                    for (int i = 0; i < imprimir3.length; i++) {
                                        canvas.saveState();
                                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                        canvas.beginText();
                                        canvas.setFontAndSize(bf, 8);
                                        canvas.moveText(320, salto3);
                                        canvas.showText(imprimir3[i]);
                                        canvas.endText();
                                        canvas.restoreState();

                                        salto3 -= 9;
                                    }
                                }
                                if(!etNombreT1.getText().toString().trim().isEmpty()){
                                    String[] imprimir4 = Justificar.justifocarTexto1(etNombreT1.getText().toString().trim().toUpperCase(), 26);
                                    float salto4 = 248;
                                    for (int i = 0; i < imprimir4.length; i++) {
                                        canvas.saveState();
                                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                        canvas.beginText();
                                        canvas.setFontAndSize(bf, 8);
                                        canvas.moveText(455, salto4);
                                        canvas.showText(imprimir4[i]);
                                        canvas.endText();
                                        canvas.restoreState();

                                        salto4 -= 9;
                                    }
                                }


                                //tipoEntrega=0;
                            }
                            if(cbDatos2.isChecked() && !cbDatos.isChecked()){

                                String []imprimir2 = Justificar.justifocarTexto1(spnombre.getSelectedItem().toString().trim().toUpperCase(), 26);
                                float salto2=248;
                                for(int i=0;i<imprimir2.length;i++){
                                    canvas.saveState();
                                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                    canvas.beginText();
                                    canvas.setFontAndSize(bf, 8);
                                    canvas.moveText(33, salto2);
                                    canvas.showText(imprimir2[i]);
                                    canvas.endText();
                                    canvas.restoreState();

                                    salto2-=9;
                                }

                                tipoentrega="En ausencia de persona alguna, se llevó a cabo la presente diligencia por cédula; con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus articulos 86 y 87.";
                                //tipoEntrega=2;
                                String []imprimir = Justificar.justifocarTexto1(tipoentrega, 35);
                                float salto=306;
                                for(int i=0;i<imprimir.length;i++){
                                    canvas.saveState();
                                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                    canvas.beginText();
                                    canvas.setFontAndSize(bf, 8);
                                    canvas.moveText(175, salto);
                                    canvas.showText(imprimir[i]);
                                    canvas.endText();
                                    canvas.restoreState();

                                    salto-=9;
                                }

                                if(!etNombreT.getText().toString().trim().isEmpty()) {
                                    String[] imprimir3 = Justificar.justifocarTexto1(etNombreT.getText().toString().trim().toUpperCase(), 26);
                                    float salto3 = 248;
                                    for (int i = 0; i < imprimir3.length; i++) {
                                        canvas.saveState();
                                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                        canvas.beginText();
                                        canvas.setFontAndSize(bf, 8);
                                        canvas.moveText(320, salto3);
                                        canvas.showText(imprimir3[i]);
                                        canvas.endText();
                                        canvas.restoreState();

                                        salto3 -= 9;
                                    }
                                }
                                if(!etNombreT1.getText().toString().trim().isEmpty()){
                                    String[] imprimir4 = Justificar.justifocarTexto1(etNombreT1.getText().toString().trim().toUpperCase(), 26);
                                    float salto4 = 248;
                                    for (int i = 0; i < imprimir4.length; i++) {
                                        canvas.saveState();
                                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                        canvas.beginText();
                                        canvas.setFontAndSize(bf, 8);
                                        canvas.moveText(455, salto4);
                                        canvas.showText(imprimir4[i]);
                                        canvas.endText();
                                        canvas.restoreState();

                                        salto4 -= 9;
                                    }
                                }
                            }
                        }


                        /*243, 197*/


                            if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("1")) {
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(83, 212);
                                canvas.showText("X");
                                canvas.endText();
                                canvas.restoreState();
                            } else if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("2")) {
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(91, 212);
                                canvas.showText("X");
                                canvas.endText();
                                canvas.restoreState();
                            } else if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("3")) {
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(99, 212);
                                canvas.showText("X");
                                canvas.endText();
                                canvas.restoreState();
                            } else if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("4")) {
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(107, 212);
                                canvas.showText("X");
                                canvas.endText();
                                canvas.restoreState();
                            } else {
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(115, 212);
                                canvas.showText("X");
                                canvas.endText();
                                canvas.restoreState();
                            }

                            //NIVEL ECONOMICO
                            if (spNE.getSelectedItem().toString().equalsIgnoreCase("1")) {
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(228, 210);
                                canvas.showText("X");
                                canvas.endText();
                                canvas.restoreState();
                            } else if (spNE.getSelectedItem().toString().equalsIgnoreCase("2")) {
                                canvas.saveState();
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(236, 210);
                                canvas.showText("X");
                                canvas.endText();
                                canvas.restoreState();
                            } else if (spNE.getSelectedItem().toString().equalsIgnoreCase("3")) {
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(243, 210);
                                canvas.showText("X");
                                canvas.endText();
                                canvas.restoreState();
                            } else if (spNE.getSelectedItem().toString().equalsIgnoreCase("4")) {
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(251, 210);
                                canvas.showText("X");
                                canvas.endText();
                                canvas.restoreState();
                            } else {
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9);
                                canvas.moveText(259, 210);
                                canvas.showText("X");
                                canvas.endText();
                                canvas.restoreState();
                            }


                        //REINCIDENCIA
                        if(swReincidencia.isChecked()) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(370, 204);
                            canvas.showText("SI");
                            canvas.endText();
                            canvas.restoreState();
                        } else {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(385, 204);
                            canvas.showText("NO");
                            canvas.endText();
                            canvas.restoreState();
                        }







                        //NUMERO DE INFRACCION
				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(460, 201);
				        canvas.showText(etNumeroActa.getText().toString());
				        canvas.endText();
				        canvas.restoreState();

                        //linea escritura

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(117, 145);
                        canvas.showText("__________");
                        canvas.endText();
                        canvas.restoreState();

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(180, 145);
                        canvas.showText("___________________________");
                        canvas.endText();
                        canvas.restoreState();

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(351, 145);
                        canvas.showText("______________________________________________");
                        canvas.endText();
                        canvas.restoreState();

				        canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(150, 134);
				        canvas.showText(etNumeroActa.getText().toString());
				        canvas.endText();
				        canvas.restoreState();

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(148, 133);
                        canvas.showText("______________________");
                        canvas.endText();
                        canvas.restoreState();


                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(30, 112);
                        canvas.showText("______________________________________________________________________________________________________________");
                        canvas.endText();
                        canvas.restoreState();



                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(125, 101);
                        canvas.showText(String.valueOf(a));
                        canvas.endText();
                        canvas.restoreState();


                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(121, 99);
                        canvas.showText("_________");
                        canvas.endText();
                        canvas.restoreState();

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(420, 99);
                        canvas.showText("________________________________");
                        canvas.endText();
                        canvas.restoreState();

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(30, 80);
                        canvas.showText("______________________________________________________________________________________________________________");
                        canvas.endText();
                        canvas.restoreState();

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(30, 55);
                        canvas.showText("______________________________________________________________________________________________________________");
                        canvas.endText();
                        canvas.restoreState();

                        /*canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(30, 60);
                        canvas.showText("______________________________________________________________________________________________________________");
                        canvas.endText();
                        canvas.restoreState();*/

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(60, 30);
                        canvas.showText("NOTA: En atencion a :"+ peticionb);
                        canvas.endText();
                        canvas.restoreState();


                        if(cuerpoInfra.length()>5000){
                            doc.newPage();
                            int tamano=cuerpoInfra.length();
                            String cuerpo2=cuerpoInfra.substring(banderacorte,tamano);
                            //Log.i(TAG, "imprimir: entro al maximo caracteres2");

                            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                        Bitmap bitmap2 = null;


                        bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.hoja_blanca);
					    bitmap2.compress(Bitmap.CompressFormat.JPEG , 100, stream2);
					    Image img2;

					    try {

					    	img2 = Jpeg.getInstance(stream2.toByteArray());
					        img2.setAbsolutePosition(0, 10);

					        float width = doc.getPageSize().getWidth();
					        float height = doc.getPageSize().getHeight();

					        img2.scaleToFit(width, height);
					        doc.add(img2);

						} catch (BadElementException e) {
							System.err.println(e.getMessage() + " BadElementException");
						} catch (MalformedURLException e) {
							System.err.println(e.getMessage() + " MalformedURLException");
						} catch (IOException e) {
							System.err.println(e.getMessage() + " IOException");
						} catch (DocumentException e) {
							System.err.println(e.getMessage() + " DocumentException");
						}

					    doc.add(new Paragraph(" "));
					    doc.add(new Paragraph(" ",font1));
                        doc.add(new Paragraph(" ",font1));
                        doc.add(new Paragraph(" ",font1));
					    doc.add(new Paragraph(" ",new Font(Font.BOLD,21,Color.BLACK)));

					    Font font2 = new Font(Font.BOLD,10,Color.BLACK);





					    //DIRECCION
					    canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9);
				        canvas.moveText(30, 910);
				        canvas.showText("(CONTINUA DEL ANVERSO)");
				        canvas.endText();
				        canvas.restoreState();



                            p2= new Paragraph(cuerpo2.trim(),font2);
                            p2.setAlignment(Element.ALIGN_JUSTIFIED);
                            p2.add(chunk);
                            doc.add(p2);
                        }







				        

					    doc.close();
					
						
						
					}catch (DocumentException e) {
						System.err.println(e.getMessage() + " doc ");
					}catch (IOException C) {
                        System.err.println(C.getMessage() + " doc ");
                    }catch (Throwable e) {

                        FirebaseCrashlytics.getInstance().setUserId(us.trim());
                        e.printStackTrace();
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw));
                        String exceptionAsString = sw.toString();
                        //System.out.println(us.trim());

                        FirebaseCrashlytics.getInstance().log(exceptionAsString);

						Log.e("Error al abrir", e.getMessage() + " c ");
                    }
		} else if(formato.equalsIgnoreCase("orden")) {
			//construccion


                try {
					
					//ambiental
					//File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");
					
					int c = 10;
                    //doc = new Document(PageSize.LEGAL,25,35,20,20);
					MimeTypeMap mime = MimeTypeMap.getSingleton();
					String  ext = file.getName().substring(file.getName().indexOf(".")+1);
					String tipo = mime.getMimeTypeFromExtension(ext);
					System.err.println(tipo);
					
					
					Paragraph p;
					
					doc.open();
					
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
				    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.hoja_blanca);
                    //Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ov_2020_test);
				    bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
				    Image img;
				    
				    try {
				    	
				    	img = Jpeg.getInstance(stream.toByteArray());
				        img.setAbsolutePosition(0, 0);
				        
				        float width = doc.getPageSize().getWidth();
				        float height = doc.getPageSize().getHeight();
				        
				        img.scaleToFit(width, height);
				        doc.add(img);
				        
					} catch (BadElementException e) {
						System.err.println(e.getMessage() + " BadElementException");
					} catch (MalformedURLException e) {
						System.err.println(e.getMessage() + " MalformedURLException");
					} catch (IOException e) {
						System.err.println(e.getMessage() + " IOException");
					} catch (DocumentException e) {
						System.err.println(e.getMessage() + " DocumentException");
					}
				    
				    PdfContentByte canvas = write.getDirectContent();
                    //canvas.setColorFill(harmony.java.awt.Color.gray);
                    canvas.setRGBColorFill(51,51,51);


                    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(32, 893+c);
			        canvas.showText(this.direccion + "   " + spZona.getSelectedItem().toString());
			        canvas.endText();
			        canvas.restoreState();
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 12);
			        canvas.moveText(420, 893+c);
			        canvas.showText(etNumeroActa.getText().toString());
			        canvas.endText();
			        canvas.restoreState();

                    /*canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 7);
                    canvas.moveText(244, 886+c);
                    canvas.showText("FOLIO DE VISITA DE INSPECCION");
                    canvas.endText();
                    canvas.restoreState();*/

                    /*canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 7);
                    canvas.moveText(399, 892+c);
                    canvas.showText("INTERNO");
                    canvas.endText();
                    canvas.restoreState();*/

				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,10,Color.BLACK)));
				    
				    Font font = new Font(Font.BOLD,10,Color.BLACK);
				    
				    
				    p = new Paragraph("  " ,font);
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,3,Color.BLACK)));

				    if(id == 4) {

				        if(etNombreComercial.getText().toString().equalsIgnoreCase("")) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(80, 857.8f + c);
                            canvas.showText("Propietario y/o Representante Legal y/o Encargado");
                            canvas.endText();
                            canvas.restoreState();
                        } else {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(80, 857.8f  + c);
                            canvas.showText(etNombreComercial.getText().toString());
                            canvas.endText();
                            canvas.restoreState();
                        }
                    } else {
				        if(id==5) {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(80, 858f + c);
                            canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
                            canvas.endText();
                            canvas.restoreState();
                        } else {
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(80, 858f + c);
                            canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
                            canvas.endText();
                            canvas.restoreState();
                        }
                    }
				    
			        
			        p = new Paragraph("                    " ,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    String dato = ""; 
				    dato += " " + etCalle.getText().toString();
				    
					if (!etNumero.getText().toString().equalsIgnoreCase("")) 
						dato += " número exterior " + etNumero.getText().toString();
					if(!etNuemroInterior.getText().toString().equalsIgnoreCase(""))
					    dato += " interior " + etNuemroInterior.getText().toString();
					if(!etEntreC.getText().toString().equalsIgnoreCase("")) {
					    dato += " entre la calle " + etEntreC.getText().toString();
					    if(!etEntreC1.getText().toString().equalsIgnoreCase(""))
					        dato += " y la calle " + etEntreC1.getText().toString();
                    }
					if (!etLote.getText().toString().equalsIgnoreCase(""))
						dato += " lote " + etLote.getText().toString();
					if (!etManzana.getText().toString().equalsIgnoreCase(""))
						dato += " manzana " + etManzana.getText().toString();
					if(!etReferencia.getText().toString().equalsIgnoreCase(""))
						dato += " " + etReferencia.getText().toString();
					txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)));
					if(txt.length != 1)
						txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)),24);

					txt = Justificar.justifocarTexto1(dato.trim(),125);
					int x1 = 840+c;
					
					for(int y = 0;y < txt.length; y++) {
                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(80, x1);
                        canvas.showText(txt[y]);
                        canvas.endText();
                        canvas.restoreState();
                        x1-=10;
                    }
			        
			        p = new Paragraph("                        ",new Font(Font.HELVETICA,7,Color.BLACK));
					doc.add(p);
					String textC="";
					if(!etCondominio.getText().toString().equals(""))
                      textC=", condominio: "+etCondominio.getText().toString();

					
					canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(80, 823+c);
			        canvas.showText(etFraccionamiento.getText().toString() + textC);
			        canvas.endText();
			        canvas.restoreState();
					
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					
					doc.add(new Paragraph(" ",font1));
					
					int count = 0;
				    
				    double lin;
				    
				    lin = ("  " + competencias+ " " + regla).length();
			    	
			    	//System.err.println(porc);
			    	count += Math.ceil(lin/180);
			    	System.out.println(count);
			    	/*String c = "";
			    	for (int i = 0; i < comp.length; i++) {
			    		if(!TextUtils.isEmpty(comp[i].trim())) {
				    		if(i == 0)
				    			c += comp[i] + "\n";
				    		else
				    			c += ", " + comp[i];
			    		}
					}*/
			    	int l = 720;
			    	/*for (int i = 0; i < comp.length; i++) {
			    		if(!TextUtils.isEmpty(comp[i].trim())) {
			    			
					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(20, l+c);
					        canvas.showText(comp[i]);
					        canvas.endText();
					        canvas.restoreState();
					        
					        l -= 10; 
			    		}
			    	}*/
			    	
				    //doc.add(new Paragraph("  " + competencias + " " + regla,font1));
			        doc.add(new Paragraph("  ",font1));
				    
				    if(count <= 1)
				    	doc.add(new Paragraph(" ",font1));
					
				    doc.add(new Paragraph(" ",font1));
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,4,Color.BLACK)));
				    doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					
					//doc.add(new Paragraph(" ",font1));
					
					count = 0;
					lin = ("  " + spnombre.getSelectedItem().toString() + ", " + spNombreA.getSelectedItem().toString() + "," + spNombreA1.getSelectedItem().toString() + "," + spNombreA2.getSelectedItem().toString()+ "," + spNombreA3.getSelectedItem().toString()+ "," + spNombreA4.getSelectedItem().toString()).length();
				    count += Math.ceil(lin/120);
				    
				    System.out.println(count);
				    
				    String insp = spnombre.getSelectedItem().toString().trim();
				    String noInsp = etNoI.getText().toString().trim();
				    
				    if(!TextUtils.isEmpty(spNombreA.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA1.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA1.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA1.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA2.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA2.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA2.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA3.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA3.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA3.getText().toString().trim();
				    }
				    if(!TextUtils.isEmpty(spNombreA4.getSelectedItem().toString().trim())) {
				    	insp += ", " + spNombreA4.getSelectedItem().toString().trim();
				    	noInsp += ", " +etNoA4.getText().toString().trim();
				    }
					
				    doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
					doc.add(new Paragraph(" ",font1));
                    //doc.add(new Paragraph(" ",font1));
                    //canvas.setColorFill(harmony.java.awt.Color.gray);
					doc.add(new Paragraph(" ",new Font(Font.HELVETICA,8.25f,Color.GRAY)));
					
					//p = new Paragraph("  " + spnombre.getSelectedItem().toString() + ", " + spNombreA.getSelectedItem().toString() + "," + spNombreA1.getSelectedItem().toString() + "," + spNombreA2.getSelectedItem().toString()+ "," + spNombreA3.getSelectedItem().toString()+ "," + spNombreA4.getSelectedItem().toString(),font1);
                   String []txt3 = Justificar.justifocarTexto1(insp, 100);
                    float li2 = 521+c;

                    for (int i = 0; i < txt3.length; i++) {

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9f);
                        canvas.moveText(20f, li2);
                        canvas.showText(txt3[i]);
                        canvas.endText();
                        canvas.restoreState();

                        li2-=9.6;
                    }

                    //p = new Paragraph("" + insp,new Font(Font.HELVETICA,9.35f,Color.BLACK));
				    //p.setAlignment(Paragraph.ALIGN_LEFT);
				    //doc.add(p);
				    
				    if(count <= 1)
				    	doc.add(new Paragraph(" ",new Font(Font.HELVETICA,6,Color.BLACK)));
				    
				    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
				    //String f1 = "";
                    String f1 = "";
                    if(etIfeI.getText().toString().length()>0){
                        f1+=etIfeI.getText().toString()+", ";
                        Log.i(TAG, "folios1: " + f1);

                    }

                    for(int x=0;x < folios.size();x++) {
                        if(!folios.get(x).trim().equalsIgnoreCase("")) {
                            f1 += folios.get(x) + ", ";
                            Log.i(TAG, "folios2: " + folios.get(x));


                        }
                    }

				    f1=f1.substring(0,f1.length()-2);
				    p = new Paragraph("                                                          " ,font1);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    doc.add(p);
				    
				    String vig = etVigI.getText().toString();
				    
				    String [] vig1 = vig.split("-");
				    
				    int d1,m,ax;
				    
				    d1=Integer.parseInt(vig1[2]);
				    m=Integer.parseInt(vig1[1]);
				    ax=Integer.parseInt(vig1[0]);

                    String vigencia=MainActivity.vigencia;
                    String vigencia_inicial=MainActivity.vigencia_inicial;
                    String []recorte1=vigencia.split("-");
                    String []recorte2=vigencia_inicial.split("-");
                    vigencia_inicial=vigencia_inicial(recorte2[1]);
                    vigencia=vigencia_final(recorte1[1]);

                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9f);
                    canvas.moveText(22, 490f+c);
                    //canvas.showText("01 de Abril");
                    canvas.showText(recorte2[2]+" de "+ vigencia_inicial);
                    canvas.endText();
                    canvas.restoreState();

                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9f);
                    canvas.moveText(240, 490f+c);
                    canvas.showText(recorte2[0].substring(2, 4));
                    canvas.endText();
                    canvas.restoreState();

                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9f);
                    canvas.moveText(300, 490f+c);
                    canvas.showText(recorte1[2] + " de " + vigencia);
                    canvas.endText();
                    canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9.35f);
			        canvas.moveText(474, 490f+c);
			        canvas.showText(String.valueOf(ax).substring(2, 4));
			        canvas.endText();
			        canvas.restoreState();

                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9f);
                    canvas.moveText(250, 480f+c);
                    canvas.showText(f1);
                    canvas.endText();
                    canvas.restoreState();
				    
				    /*canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(150, 380+c);
			        canvas.showText(noInsp);
			        canvas.endText();
			        canvas.restoreState();*/

			        String motivo = etMotivo.getText().toString().trim() + " ";
			        if(id == 2) {
			            motivo = "Verificar y constatar que cuenta con " + etMotivo.getText().toString().trim();
                    }
			        if(id == 4) {
                        String art = "";
                        int x = 1;

                        for (int i = 0; i < reg.length; i++) {
                            if (reg[i] > 0) {
                                if(reg[i]==13){

                                    motivo+= (x) + "Dar seguimiento a lo señalado en el previo apercibimiento folio "+etfolioap.getText().toString()+" de fecha "+etfechap.getText().toString()+" en lo conducente y en concordancia con la reglamentacion aplicable.";
                                }if(reg[i]==14){
                                    motivo+= (x) + " Verificar que el estado de Clausurado señalado en el Acta de Infraccion  folio "+ etfolioclau.getText().toString() +" del dia "+ etfechaClau.getText().toString() + "; se respete; que los sellos de Clausurado No se encuentren violentados con su retiro, ruptura, ocultamiento y/o personal al interior laborando en cualquier actividad directamente relacionada con la edificacion en proceso y acotada en el propio cuerpo del acta de Infraccion antes mencionada ";
                                }
                                if(reg[i]!=14 && reg[i]!=13){
                                    motivo += (x) + " " + conceptos.get(i) + ",";
                                }

                                if(fraccion.get(i).length()>0) {
                                    art += articulo.get(i) + " Fracción " + fraccion.get(i) + ",";
                                    x += 1;
                                }else{
                                    art += articulo.get(i) + ",";
                                    x += 1;
                                }
                            }
                        }
                        art = art.substring(0, art.length() - 1);
                        motivo += " Asi mismo inspeccionar, cualquier otra actividad relacionada con la normatividad aplicable y que sea regulada por el Municipio de Zapopan Jalisco, con respecto a la ejecución de trabajos de construcción, remodelación, demolición, movimiento de tierras, excavación, reparación o restauración de cualquier género, así como cualquier acto de ocupacion o utilizacion del suelo que se lleve a cabo en el Municipio de Zapopan. Con base a los articulos: 2, 3, 5, 7  Fracciones I a la VI, 34, 167, 168, 169, 171. ";
                        motivo += art + " del Reglamento de Construccion para el Municipio de Zapopan Jalisco";

                    }

                    doc.add(new Paragraph(" ",font1));
                    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7f,Color.BLACK)));
                    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,10f,Color.BLACK)));
                    //doc.add(new Paragraph(" ",new Font(Font.HELVETICA,10f,Color.BLACK)));


                    //doc.add(new Paragraph(" ",font1));
                    //doc.setMargins(35,35,20,20);
                    Log.i(TAG, "c: "+motivo.length() );
                    if(motivo.length()>3000){
                        p = new Paragraph(motivo,new Font(Font.HELVETICA,6.5f,Color.BLACK));
                        p.setLeading(10);
                        p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                        //p.setFont(new Font(Font.HELVETICA,3));
                        doc.add(p);
                    }else{
                        p = new Paragraph(motivo,new Font(Font.HELVETICA,8f,Color.BLACK));
                        p.setLeading(10);
                        p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                        //p.setFont(new Font(Font.HELVETICA,3));
                        doc.add(p);
                    }

                    //doc.setMargins(25,35,20,20);

			        /*txt = Justificar.justifocarTexto1(motivo, 133);
				    float li = 462+c;
				    
				    for (int i = 0; i < txt.length; i++) {
				    	
				    	canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9.35f);
				        canvas.moveText(17.8f, li);
				        canvas.showText(txt[i]);
				        canvas.endText();
				        canvas.restoreState();
				        
				        li-=11;
					}*/
				    
				    int d = 5;
				    
				    canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9.35f);
			        canvas.moveText(265, 113f);
			        canvas.showText(dia + "");
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9.35f);
			        canvas.moveText(388, 113f);
			        canvas.showText(me.toUpperCase());
			        canvas.endText();
			        canvas.restoreState();
			        
			        canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9.35f);
			        canvas.moveText(513f, 114f);
			        canvas.showText(String.valueOf(a).substring(2,4) + "");
			        canvas.endText();
			        canvas.restoreState();

			        if(id == 2 && id==5) {
                        if(!cbDatos.isChecked() ) {
                            if(!cbDatos2.isChecked()){


                            /*canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9.35f);
                            canvas.moveText(86, 169f);
                            canvas.showText(etNombreV.getText().toString() + " " + spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString());
                            canvas.endText();
                            canvas.restoreState();*/
                            }
                        }else{
                            /*canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9.35f);
                            canvas.moveText(86, 138.2f);
                            canvas.showText(spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString());
                            canvas.endText();
                            canvas.restoreState();*/
                           /* String leyenda="El visitado no proporciono dato alguno de su identidad, por lo que se lleva a cabo la presente diligencia con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus articulos 86 y 87, con descripcion de su media afiliacion";


                            float brinco=250f;
                            String []txt2=Justificar.justifocarTexto1(leyenda, 40);
                            if(!cbDatos2.isChecked()) {
                                for(int i=0;i<txt2.length;i++) {

                                    canvas.saveState();

                                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                    canvas.beginText();
                                    canvas.setFontAndSize(bf, 9.35f);
                                    canvas.moveText(25, brinco);
                                    canvas.showText(txt2[i]);
                                    canvas.endText();
                                    canvas.restoreState();
                                    brinco-=9;

                                }
                                /*canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9.35f);
                                canvas.moveText(86, 169f);
                                canvas.showText(etVIdentifica.getText().toString() );
                                canvas.endText();
                                canvas.restoreState();*/
                            //}
                        }
                        if(!cbDatos2.isChecked()) {
                            if(!cbDatos.isChecked()) {
                                /*canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9.35f);
                                canvas.moveText(86, 169f);
                                canvas.showText(etNombreV.getText().toString() + " " + spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString() + " " + etVManifiesta.getText().toString());
                                canvas.endText();
                                canvas.restoreState();*/
                            }
                        }else{
                            /*String leyenda2="En ausencia de persona alguna, se llevó a cabo la presente diligencia por cedula; con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus articulos 86 y 87";
                            String []txt2=Justificar.justifocarTexto1(leyenda2, 40);
                            float brinco=250f;
                            if(!cbDatos.isChecked()){
                                for(int i=0;i<txt2.length;i++) {

                                    canvas.saveState();

                                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                    canvas.beginText();
                                    canvas.setFontAndSize(bf, 9.35f);
                                    canvas.moveText(25, brinco);
                                    canvas.showText(txt2[i]);
                                    canvas.endText();
                                    canvas.restoreState();
                                    brinco-=9;

                                }
                                canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9.35f);
                                canvas.moveText(93, 168f);
                                canvas.showText("------------------------------------------------------------------------------------------" );
                                canvas.endText();
                                canvas.restoreState();


                            }*/


                        }
                    } else {
			            if(!cbDatos.isChecked()) {
                            if(!cbDatos2.isChecked()) {
                                /*canvas.saveState();
                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9.35f);
                                canvas.moveText(86, 169f);
                                canvas.showText(etNombreV.getText().toString() + " " + spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString() + " " + etVManifiesta.getText().toString());
                                canvas.endText();
                                canvas.restoreState();*/
                            }
                        }else{
			                /*String leyenda="El visitado no proporciono dato alguno de su identidad, por lo que se lleva a cabo la presente diligencia con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus articulos 86 y 87, con descripcion de su media afiliacion";


                        float brinco=250f;
                        String []txt2=Justificar.justifocarTexto1(leyenda, 40);
                        if(!cbDatos2.isChecked()) {
                            for(int i=0;i<txt2.length;i++) {

                                canvas.saveState();

                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9.35f);
                                canvas.moveText(25, brinco);
                                canvas.showText(txt2[i]);
                                canvas.endText();
                                canvas.restoreState();
                                brinco-=9;

                            }
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9.35f);
                            canvas.moveText(93, 168f);
                            canvas.showText("------------------------------------------------------------------------------------------" );
                            canvas.endText();
                            canvas.restoreState();


                        }*/
                    }
                    if(!cbDatos2.isChecked()) {
                        if(!cbDatos.isChecked()) {

                        }
                    }else{
                        /*String leyenda2="En ausencia de persona alguna, se llevó a cabo la presente diligencia por cedula; con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus articulos 86 y 87";
                        String []txt2=Justificar.justifocarTexto1(leyenda2, 40);
                        float brinco=250f;
                        if(!cbDatos.isChecked()){
                            for(int i=0;i<txt2.length;i++) {

                                canvas.saveState();

                                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                                canvas.beginText();
                                canvas.setFontAndSize(bf, 9.35f);
                                canvas.moveText(25, brinco);
                                canvas.showText(txt2[i]);
                                canvas.endText();
                                canvas.restoreState();
                                brinco-=9;

                            }
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9.35f);
                            canvas.moveText(93, 168f);
                            canvas.showText("------------------------------------------------------------------------------------------" );
                            canvas.endText();
                            canvas.restoreState();


                        }*/

                    }
                    }
			        

			        
			        if(cbFirma.isChecked()) {

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9.25f);
                        canvas.moveText(518, 99f);
                        canvas.showText(hr);
                        canvas.endText();
                        canvas.restoreState();

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9.25f);
                        canvas.moveText(65, 85.2f);
                        canvas.showText(dia + "");
                        canvas.endText();
                        canvas.restoreState();

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9.25f);
                        canvas.moveText(156, 85.2f);
                        canvas.showText(me.toUpperCase(Locale.getDefault()));
                        canvas.endText();
                        canvas.restoreState();

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9.25f);
                        canvas.moveText(267f, 85.5f);
                        canvas.showText(a + "");
                        canvas.endText();
                        canvas.restoreState();


                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9.35f);
                        canvas.moveText(70, 98f);
                        String visitado=etNombreV.getText().toString() + " " + spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString();
                        if(visitado.length()<=85){

                            canvas.showText(etNombreV.getText().toString() + " " + spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString());

                        }else{

                            canvas.showText(spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString());

                        }
                        canvas.endText();
                        canvas.restoreState();



			        	canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9.25f);
				        canvas.moveText(155, 74f);
				        canvas.showText("Si");
				        canvas.endText();
				        canvas.restoreState();
			        } else {
			        	canvas.saveState();
				        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				        canvas.beginText();
				        canvas.setFontAndSize(bf, 9.25f);
				        canvas.moveText(155, 74f);
				        canvas.showText("No ");
				        canvas.endText();
				        canvas.restoreState();

                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9.25f);
                        canvas.moveText(440, 80f);
                        canvas.showText("");
                        canvas.endText();
                        canvas.restoreState();
			        }

                    String peticionb="";
                    if(spPeticion.getSelectedItem().toString().equals("Flagrancia")){
                        peticionb=spPeticion.getSelectedItem().toString();
                    }else{
                        if(etfoliopeticion.getText().length()>2){
                            peticionb=spPeticion.getSelectedItem().toString()+" con folio "+etfoliopeticion.getText().toString();

                        }else{
                            peticionb=spPeticion.getSelectedItem().toString();

                        }

                    }
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(60, 41);
                    canvas.showText("NOTA: En atencion a :"+ peticionb);
                    canvas.endText();
                    canvas.restoreState();





			        doc.close();	
				} catch (DocumentException e) {
					System.err.println(e.getMessage() + " doc ");
				} catch (IOException e) {
                    System.err.println(e.getMessage() + " IOE ");
                } /*catch (Exception e) {
					Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos", Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
					Log.e("Error al abrir", e.getMessage() + " c ");
					System.err.println(e.getMessage() + " n ");
				}*/
            } else if(formato.equalsIgnoreCase("citatorio")) {
			
			Paragraph p;
			
			doc.open();
			
			
			if(id == 1) {
				try {
					
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					
				    p = new Paragraph("                                                             " + hora + "                   " + dia + "              " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("          " + String.valueOf(a).substring(0, 2) + "                                      " + spnombre.getSelectedItem().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    System.out.println("citatoriocitatorio");
				    
				    p = new Paragraph("                      " + etNoI.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString() + "      ");
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                " + etCalle.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                                        " + etFraccionamiento.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                             " + etPropietario.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                                   " + etNombreV.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                            " + spManifiesta .getSelectedItem().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                                  " + etVIdentifica.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					p = new Paragraph("                                  " + hora);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph(etMotivo.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					
					doc.add(new Paragraph(" "));
				    p = new Paragraph("                                                  " + hora + "                     " + dia + "            " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    	p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeI.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					doc.add(new Paragraph("  "));
					doc.add(new Paragraph("  "));
					doc.add(new Paragraph("  "));
					
					
					len = MITAD - etNombreT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
				    doc.close();
					
				} catch(Exception e) {
					 System.out.println(e.getMessage() + " aqui");
				}
			} else {
				
				try {
					
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					doc.add(new Paragraph(" "));
					
					p = new Paragraph("                                                                                 " + hora + "                   " + dia);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                      " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
					
				} catch (Exception e) {
					
				}
				
			}
			
		} else if(formato.equalsIgnoreCase("apercibimiento")) {
			
			Paragraph p;
			
			doc.open();
			
			try {
				
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
			    p = new Paragraph(etNumeroActa.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_RIGHT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
				p = new Paragraph("                                                             " + hora + "                   " + dia + "              " + getMes(mes));
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                         " + spnombre.getSelectedItem().toString() + "                                                                       " + etNoI.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
				p = new Paragraph("                                                                      " + etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                      " + etCalle.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                                    " + etFraccionamiento.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                         " + etNombreV .getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                                    " + etVIdentifica.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                " + spManifiesta.getSelectedItem().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                " + etGiro.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				doc.add(new Paragraph(" "));
				
				p = new Paragraph("                                " + etNombreT.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                " + spnombre.getSelectedItem().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("                                               " + etIfeT2.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    
			    p = new Paragraph("   " + etSeleccion.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			    p.setFont(font1);
			    doc.add(p);
			    
			    p = new Paragraph("   " + conti + " " + etInfraccion.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    p = new Paragraph("                                                                 20");
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    String fe [] = etfecha.getText().toString().split("/");
			    
			    p = new Paragraph("                                                       " + fe[0] + "                         " + getMes(Integer.parseInt(fe[1])));
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    
			    p = new Paragraph("          " + spnombre.getSelectedItem().toString().trim() + mitad(len) + " " + etNombreV.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    doc.add(new Paragraph(" "));
			    
			    len = MITAD - etNombreT.getText().toString().trim().length();
			    
			    p = new Paragraph("          " + etNombreT.getText().toString().trim() + mitad(len) + " " + etNombreT1.getText().toString());
			    p.setAlignment(Paragraph.ALIGN_LEFT);
			    p.setFont(font1);
			    doc.add(p);
			    
			    doc.close();
				
			} catch(DocumentException e) {
				
			}
			
		} else if(formato.equalsIgnoreCase("hechos")) {
			
			Paragraph p;
			
			doc.open();
		    
			if(id == 2) {
			
				try {
					
				
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                    " + hora + "           " +  dia);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("    " + getMes(mes));
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spnombre.getSelectedItem().toString() + "                                   " + etNoI.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spNombreA.getSelectedItem().toString() + "                                   " + etNoA.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("   "  + etNum.getText().toString() + "         " + etFecham.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("             " + etCalle.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etFraccionamiento.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etNombreV.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                   " + etVIdentifica.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    
				    p = new Paragraph(etSeleccion.getText().toString() + "." + etInfraccion.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                        " + etManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                           " + hr);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("     " + fechas[0] + "        " + fechas[1]);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    	p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeI.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph("  "));
					
					len = MITAD - etNombreT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
				    doc.close();
				    
				} catch (Exception e) {
					
				}
			} else {
				
				try {
					
					
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    p = new Paragraph(etNumeroActa.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                    " + hora + "           " +  dia );
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   " + getMes(mes) + "   "  +   String.valueOf(a).substring(0,2) );
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spnombre.getSelectedItem().toString() + "                                   " + etNoI.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("   "  + spNombreA.getSelectedItem().toString() + "                                   " + etNoA.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                  "  + etNum.getText().toString() + "            " + etFecham.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_RIGHT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                    " + etCalle.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etFraccionamiento.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etPropietario.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                                 " + etNombreV.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                   " + etVIdentifica.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    
				    
				    p = new Paragraph("                          " + etVManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("                          " + etMotivo.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    
				    p = new Paragraph(etSeleccion.getText().toString() + "." + etInfraccion.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                        " + etManifiesta.getText().toString());
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    p = new Paragraph("                                                                           " + hr);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    p = new Paragraph("     " + fechas[0] + "        " + fechas[1]);
				    p.setAlignment(Paragraph.ALIGN_LEFT);
				    p.setFont(font1);
				    doc.add(p);
				    
				    doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
				    
				    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
			    	p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeI.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					doc.add(new Paragraph(" "));
				    doc.add(new Paragraph(" "));
					doc.add(new Paragraph("  "));
					
					len = MITAD - etNombreT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
					len = MITAD - etIfeT.getText().toString().trim().length();
			    	p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
			    	p.setFont(font1);
			    	doc.add(p);
					
				    doc.close();
				    
				}catch (DocumentException e) {
					System.err.println(e.getMessage() + " doc ");
				}catch (Exception e) {
					Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos", Toast.LENGTH_LONG);
					toast.setGravity(0, 0, 15);
					toast.show();
					Log.e("Error al abrir", e.getMessage() + " c ");
					System.err.println(e.getMessage() + " n ");
				}
				
			}
			
		}
		
	}

	public void imprimirPrevia(String formato) {
        int len =0;
        final int MITAD = 135;
        String src;
        String [] txt;
        String path = Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_");
        File f = new File(path);
        f.mkdirs();

        Document doc = null;
        File file = null;
        FileOutputStream ficheroPdf = null;
        PdfWriter write = null;
        BaseFont bf = null;

        try {
            doc = new Document(PageSize.LEGAL,25,35,20,20);
            file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".pdf");
            ficheroPdf = new FileOutputStream(file.getAbsoluteFile());

            write = PdfWriter.getInstance(doc, ficheroPdf);


        } catch (Exception e) {

        }

//26/02
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);
        String [] na = thisDate.split("/");
        Log.i("fecha", na[0] + "/" + na[1] + "/" + na[2]);
        fecha = na[0] + "/" + na[1] + "/" + na[2];
        String [] fechas = fecha.split("/");
        int dia, mes,a;
        String me;
        for (int i = 0; i < fechas.length; i++) {
            System.out.println(fechas[i]);
        }

        dia = Integer.parseInt(fechas[0]);
        mes = Integer.parseInt(fechas[1]);
        String an = fechas[2];
        //a = Integer.parseInt(fechas[2].substring(2, 4));
        a = Integer.parseInt(fechas[2]);
        System.out.println("entro");
        me = Justificar.mes(mes);
        String medida = etMedida.getText().toString() + " del " + medidas1;

        System.err.println(medida);

        Font font1 = new Font(Font.HELVETICA,9.5f,Color.BLACK);

        if (formato.equalsIgnoreCase("infraccion")) {
            try {

                DottedLineSeparator dottedLineSeparator = new DottedLineSeparator();
                dottedLineSeparator.setGap(7);

                Chunk chunk = new Chunk(dottedLineSeparator);

                //File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");


                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String  ext = file.getName().substring(file.getName().indexOf(".")+1);
                String tipo = mime.getMimeTypeFromExtension(ext);


                Paragraph p;

                doc.open();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = null;
						/*if(id != 4)
						    bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.acta_1);
						else
						    bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.acta_c);*/

                bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.acta_inspeccion_vista_previa);
                bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
                Image img;

                try {

                    img = Jpeg.getInstance(stream.toByteArray());
                    img.setAbsolutePosition(0, 0);

                    float width = doc.getPageSize().getWidth();
                    float height = doc.getPageSize().getHeight();

                    img.scaleToFit(width, height);
                    doc.add(img);

                } catch (BadElementException e) {
                    System.err.println(e.getMessage() + " BadElementException");
                } catch (MalformedURLException e) {
                    System.err.println(e.getMessage() + " MalformedURLException");
                } catch (IOException e) {
                    System.err.println(e.getMessage() + " IOException");
                } catch (DocumentException e) {
                    System.err.println(e.getMessage() + " DocumentException");
                }

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",new Font(Font.BOLD,21,Color.BLACK)));

                Font font = new Font(Font.BOLD,10,Color.BLACK);


                PdfContentByte canvas = write.getDirectContent();


                //DIRECCION
                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(30, 910);
                canvas.showText(direccion);
                canvas.endText();
                canvas.restoreState();

                //ZONA
                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(200, 910);
                canvas.showText(spZona.getSelectedItem().toString());
                canvas.endText();
                canvas.restoreState();

                //NUMERO DE ACTA
                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 11);
                canvas.moveText(450, 910);
                canvas.showText("");
                canvas.endText();
                canvas.restoreState();

                String uso = "";
                String vig = etVigI.getText().toString();

                String [] vig1 = vig.split("-");

                int d,m,ax;

                d=Integer.parseInt(vig1[2]);
                m=Integer.parseInt(vig1[1]);
                ax=Integer.parseInt(vig1[0]);

                String prop = "",numero = "";

                Log.e("PROPIEDAD",propiedad.equalsIgnoreCase("El Visitado") + "");
                if(propiedad.isEmpty())
                    prop = etOtro.getText().toString().trim();
                else {
                    if (propiedad.equalsIgnoreCase("El Visitado"))
                        prop = etNombreV.getText().toString();
                    else
                        prop = propiedad;
                }
                if(etNuemroInterior.getText().toString().trim().equalsIgnoreCase(""))
                    numero = etNumero.getText().toString() + " " + etNuemroInterior.getText().toString();
                else
                    numero = etNumero.getText().toString() + " Int.  " + etNuemroInterior.getText().toString();

                String datos = "";
                if(!numeroOV.equalsIgnoreCase("")) {
                    datos = "mediante y en cumplimiento de la Orden de Visita folio número "
                            + numeroOV +"  emitida por el Director de Inspección y Vigilancia de Zapopan, Jalisco, el día " + fechaOV + " misma que en original exhibo y en original legible entrego " +
                            "al visitado, " + etNombreV.getText().toString() + ",";
                } else {
                    datos = "en términos de lo dispuesto por el artículo 73, segundo párrafo, de la Ley del Procedimiento Administrativo del Estado de Jalisco,";
                }
                String tipoentrega="";

                if(cbDatos.isChecked() && !cbDatos2.isChecked()){
                    tipoentrega="el visitado no proporciono dato alguno de su identidad, por lo que se lleva a cabo la presente diligencia con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus artículos 86 y 87, con descripcion de media filiacion.";
                    tipoEntrega=0;
                }
                if(cbDatos2.isChecked() && !cbDatos.isChecked()){
                    tipoentrega="en ausencia de persona alguna, se llevó a cabo la presente diligencia por cédula; con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus articulos 86 y 87.";
                    tipoEntrega=2;
                }

                /*243, 197*/
                /*}*/



                String peticionb="";
                if(spPeticion.getSelectedItem().toString().equals("Flagrancia")){
                    peticionb=spPeticion.getSelectedItem().toString();
                }else{
                    if(etfoliopeticion.getText().length()>2){
                        peticionb=spPeticion.getSelectedItem().toString()+" con folio "+etfoliopeticion.getText().toString();

                    }else{
                        peticionb=spPeticion.getSelectedItem().toString();

                    }

                }


                String vigencia=MainActivity.vigencia;
                String vigencia_inicial=MainActivity.vigencia_inicial;
                String []recorte1=vigencia.split("-");
                String []recorte2=vigencia_inicial.split("-");
                String diaIni=recorte2[2];
                String diavigen=recorte1[2];
                vigencia_inicial=vigencia_inicial(recorte2[1]);
                vigencia=vigencia_final(recorte1[1]);

                Paragraph p2 = null;
                String testigos="";
                String nombresT="";
                if(etNombreT.getText().toString().length()>4 && etNombreT1.getText().toString().length()>4){
                    testigos= "mismos que se identifican con "+spIdentificaT.getSelectedItem().toString().trim() + " " + etIfeT.getText().toString() + " y " + spIdentificaT1.getSelectedItem().toString().trim() + " " + etIfeT2.getText().toString()+" respectivamente; ";
                    nombresT=etNombreT.getText().toString().trim() + " y " + etNombreT1.getText().toString().trim();
                }
                if(etNombreT.getText().toString().length()>4 && etNombreT1.getText().toString().length()<=1){
                    testigos= "mismo que se identifica con "+spIdentificaT.getSelectedItem().toString().trim() + " " + etIfeT.getText().toString();
                    nombresT= etNombreT.getText().toString().trim();
                }
                if(etNombreT.getText().toString().length()<=1 && etNombreT1.getText().toString().length()>=1){
                    testigos="mismo que se identifica con "+spIdentificaT1.getSelectedItem().toString().trim() + " " + etIfeT2.getText().toString();
                    nombresT=etNombreT1.getText().toString().trim();
                }
                String textC="";
                if(!etCondominio.getText().toString().equals(""))
                    textC="condominio "+etCondominio.getText().toString();

                String decomiso = "";
                if(!TextUtils.isEmpty(etDecomiso.getText().toString().trim()))
                    decomiso += "decomiso: " + etDecomiso.getText().toString().trim() + " ";

                if(id == 4) {
                    String apercibimiento="";
                    if(etCondominio.getText().toString().trim().length()>1 && etfolioap.getText().toString().length()>1 && etfechap.getText().toString().length()>1){
                        String folioa=etfolioap.getText().toString();
                        String fechap=etfechap.getText().toString();
                        apercibimiento="Dar seguimiento a lo señalado en el previo apercibimiento folio "+folioa+ " de fecha "+fechap+"";

                    }




                    //String medidasP=etMedida.getText().toString();
                    String medidasP=medidas1;
				            /*if(etMedida.getText().toString().trim().contains("Reglamento de Construcción para el Municipio de Zapopan")){
                                String [] cortes = etMedida.getText().toString().trim().split("Reglamento de Construcción para el Municipio de Zapopan Jalisco", 0);

                                for(int i=0;i<cortes.length;i++){
                                    medidasP+=cortes[i];
                                }

                            }*/


                    String numeroS="";
                    if(etNumeroSellos.getText().toString().trim().length()>0){
                        numeroS="con numero de sello(s) "+etNumeroSellos.getText().toString().trim();
                    }
                    String hechos=etSeleccion.getText().toString().trim();
                    if(!spuso.getSelectedItem().toString().contains("pública") | !spuso.getSelectedItem().toString().contains("público"))
                        uso = "el uso " + spuso.getSelectedItem().toString() + " ";
                    else
                        uso = spuso.getSelectedItem().toString();
                    p2= new Paragraph("En la ciudad de Zapopan, Jalisco, siendo las " + hora + " horas del día de " + dia + " de " + me + " del año " + a + ", el suscrito " + spnombre.getSelectedItem().toString() +
                            " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, "+datos+" me constituí física y legalmente en " + uso +" marcada con el número " +
                            numero + " de la calle " + etCalle.getText().toString() + " entre las calles " + etEntreC.getText().toString() + " y " + etEntreC1.getText().toString() + " en la colonia y/o fraccionamiento " + etFraccionamiento.getText().toString().trim() + " "+textC+ ",  cerciorándome de ser este el domicilio por coincidir con la nomenclatura oficial y/o georreferencia, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número" +
                            " "+folio + ", vigente del " +diaIni+" de " +vigencia_inicial+ " del "+recorte2[0]+ " a "+diavigen+ " de "+ vigencia +" del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del Gobierno Municipal de Zapopan, Jalisco, ante " + etNombreV.getText().toString() + " quien se identifica con, " + spIdentifica.getSelectedItem().toString().trim() + " " + etVIdentifica.getText().toString().trim() +
                            " manifiesta ser " + etVManifiesta.getText().toString() + " del lugar en que se actúa, propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía por lo que fueron designados los C.C. " + nombresT + " por el " + spdesignado.getSelectedItem().toString().trim() +
                            ", "+ testigos + "así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " +
                            apercibimiento +", "+ hechos + ".Los cuales constituyen infracción a lo dispuesto por los artículo(s): 2, 3, 5, 7  FRACCIONES I  a la VI, 34,  167, 168, 169, 171 ," + etInfraccion.getText().toString().trim() + " Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados , se procede indistintamente con las siguientes medidas: " + medidasP + " "+ numeroS+".Lo anterior de conformidad a lo dispuesto por los artículo(s): " + etArticulo.getText().toString().trim() + ". En uso de su derecho el visitado manifiesta: " + etManifiesta.getText().toString().trim() +
                            ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No.151). Se da por concluida esta diligencia, siendo las " +
                            hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, quedando copia legible en poder del interesado y firmando constancia los que en ella intervinieron, quisieron y supieron hacerlo.  =Fin del texto=",font1);
                } else if(id == 2) {




                    String apercibimiento="";
                    if(etCondominio.getText().toString().trim().length()>1 && etfolioap.getText().toString().length()>1 && etfechap.getText().toString().length()>1 ){
                        String folioa=etfolioap.getText().toString();
                        String fechap=etfechap.getText().toString();
                        apercibimiento="Dar seguimiento a lo señalado en el previo apercibimiento folio "+folioa+ " de fecha "+fechap+" en lo conducente y en concordancia con la reglamentación aplicable,";

                    }
                    String numeroS="";
                    if(etNumeroSellos.getText().toString().trim().length()>3){
                        numeroS="con numero de sello(s) "+etNumeroSellos.getText().toString().trim();
                    }
                    String hechos=etSeleccion.getText().toString().trim();
                    p2= new Paragraph("En la ciudad de Zapopan, Jalisco, siendo las " + hora + " horas del día de " + dia + " de " + me + " del  año " + a + ", el suscrito " + spnombre.getSelectedItem().toString() +
                            " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, "+datos+" me constituí física y legalmente en  " +
                            etDondeActua.getText().toString().trim() + " marcada (o)  con el número  " + numero + " de  la  calle " + etCalle.getText().toString().trim() + " entre las calles " + etEntreC.getText().toString().trim() + " y " + etEntreC1.getText().toString() + " en la colonia y/o fraccionamiento " +
                            etFraccionamiento.getText().toString().trim() + textC + ", "+apercibimiento+" cerciorándome de ser este el domicilio donde se realiza la visita de inspección y la actividad comercial, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado " +
                            "por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número " +" "+ folio + ", vigente del "+diaIni+" de " +vigencia_inicial+ " del "+recorte2[0]+ " a "+diavigen+" de " +  vigencia +" del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del " +
                            "Gobierno Municipal de Zapopan, Jalisco, ante " + etNombreV.getText().toString().trim() + " quien se identifica con, " + spIdentifica.getSelectedItem().toString().trim() + " " + etVIdentifica.getText().toString() + " manifiesta ser " + etVManifiesta.getText().toString().trim() + " del giro " +
                            etGiro.getText().toString().trim() + ", propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía acto seguido fueron designados los C.C. " +
                            nombresT + " por el " + spdesignado.getSelectedItem().toString().trim() + ", " + testigos
                            + "  así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  " +
                            "una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " + hechos + " .Los cuales constituyen infracción a lo dispuesto por los artículo(s): " + etInfraccion.getText().toString().trim() + "  Por encuadrar dichas acciones y/u omisiones en los preceptos legales " +
                            "indicados y al haber sido detectados en "+peticionb+", se procede indistintamente con las siguientes medidas: " + medidas1 + " "+numeroS+  " ," + decomiso + ". Lo anterior de conformidad a lo dispuesto por los artículo(s): " + etArticulo.getText().toString().trim() + ". En uso de su derecho el visitado manifiesta: " +
                            etManifiesta.getText().toString().trim() + ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, " +
                            "Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No.151). Se da por concluida esta diligencia, siendo las " +
                            hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, "+tipoentrega+" =Fin del texto=",font1);
                }else if(id==5){
                    String numeroS="";
                    if(etNumeroSellos.getText().toString().trim().length()>3){
                        numeroS="con numero de sello(s) "+etNumeroSellos.getText().toString().trim();
                    }
                    String hechos=etSeleccion.getText().toString().trim();
                    p2= new Paragraph("En la ciudad de Zapopan, Jalisco, siendo las " + hora + " horas del día de " + dia + " de " + me + " del  año " + a + ", el suscrito " + spnombre.getSelectedItem().toString() +
                            " Inspector Municipal con clave " + clave + ", facultado para llevar a cabo la Inspección y Vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, "+datos+" me constituí física y legalmente en  " +
                            spMeConstitui.getSelectedItem().toString()+ " marcada (o)  con el número  " + numero + " de  la  calle " + etCalle.getText().toString().trim() + " entre las calles " + etEntreC.getText().toString().trim() + " y " + etEntreC1.getText().toString() + " en la colonia y/o fraccionamiento " +
                            etFraccionamiento.getText().toString().trim() + textC+ ", cerciorándome de ser este el domicilio donde se realiza la visita de inspección y la actividad comercial, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado " +
                            "por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número " +" "+ etIfeA.getText().toString().trim() + ", vigente del "+diaIni+" de " +vigencia_inicial+ " del "+recorte2[0]+ " a "+diavigen+" de " +  vigencia +" del " + recorte1[0] + ", expedida por el Director de Inspección y Vigilancia del " +
                            "Gobierno Municipal de Zapopan, Jalisco, ante " + etNombreV.getText().toString().trim() + " quien se identifica con, " + spIdentifica.getSelectedItem().toString().trim() + " " + etVIdentifica.getText().toString() + " manifiesta ser " + etVManifiesta.getText().toString().trim() + " del giro " +
                            etGiro.getText().toString().trim() + ", propiedad de " + prop + ", le  informo  el  derecho  que  le  asiste  para  designar  a  dos  testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a  ello el suscrito lo haría en rebeldía acto seguido fueron designados los C.C. " +
                            nombresT + " por el " + spdesignado.getSelectedItem().toString().trim() + ", " + testigos
                            + " así, como de la prerrogativa que en todo momento tiene de manifestar lo que  a  su  derecho  convenga y aportar las pruebas que considere pertinentes.  Acto  seguido,  le hago  saber al visitado,  " +
                            "una  vez  practicada la diligencia, los hechos encontrados y que consisten en: " + hechos + " .Los cuales constituyen infracción a lo dispuesto por los artículo(s): " + etInfraccion.getText().toString().trim() + " Por encuadrar dichas acciones y/u omisiones en los preceptos legales " +
                            "indicados y al haber sido detectados en "+peticionb+", se procede indistintamente con las siguientes medidas: " + medidas1 + " "+numeroS+  " " + decomiso + ".Lo anterior de conformidad a lo dispuesto por los artículo(s): " + etArticulo.getText().toString().trim() + ". En uso de su derecho el visitado manifiesta: " +
                            etManifiesta.getText().toString().trim() + ". Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el articulo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, " +
                            "Jalisco dentro del plazo de 20 días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No.151). Se da por concluida esta diligencia, siendo las " +
                            hr + " horas del " + dia + " de " + me + " del " + a + " levantándose la presente acta en presencia de los  testigos  que  se  mencionan, "+tipoentrega+" =Fin del texto=",font1);



                }
                p2.setAlignment(Element.ALIGN_JUSTIFIED);
                p2.add(chunk);
                doc.add(p2);






                //GRAVEDAD
                if(id==4){
                    if(cbDatos.isChecked() && !cbDatos2.isChecked()){
                        tipoentrega="El visitado no proporciono dato alguno de su identidad, por lo que se lleva a cabo la presente diligencia con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus artículos 86 y 87, con descripcion de media filiacion.";
                        String []imprimir = Justificar.justifocarTexto1(tipoentrega, 35);
                        float salto=320;
                        for(int i=0;i<imprimir.length;i++){
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(175, salto);
                            canvas.showText(imprimir[i]);
                            canvas.endText();
                            canvas.restoreState();

                            salto-=10;
                        }
                        //tipoEntrega=0;
                    }
                    if(cbDatos2.isChecked() && !cbDatos.isChecked()){
                        tipoentrega="En ausencia de persona alguna, se llevó a cabo la presente diligencia por cédula; con base a lo señalado en la Ley del Procedimiento Administrativo del Estado de Jalisco en sus articulos 86 y 87.";
                        //tipoEntrega=2;
                        String []imprimir = Justificar.justifocarTexto1(tipoentrega, 35);
                        float salto=290;
                        for(int i=0;i<imprimir.length;i++){
                            canvas.saveState();
                            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                            canvas.beginText();
                            canvas.setFontAndSize(bf, 9);
                            canvas.moveText(175, salto);
                            canvas.showText(imprimir[i]);
                            canvas.endText();
                            canvas.restoreState();

                            salto-=10;
                        }
                    }
                }


                /*243, 197*/


                if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("1")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(83, 200);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("2")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(91, 200);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("3")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(99, 200);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("4")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(107, 200);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(115, 200);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                }

                //NIVEL ECONOMICO
                if (spNE.getSelectedItem().toString().equalsIgnoreCase("1")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(228, 197);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (spNE.getSelectedItem().toString().equalsIgnoreCase("2")) {
                    canvas.saveState();
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(236, 197);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (spNE.getSelectedItem().toString().equalsIgnoreCase("3")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(243, 197);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else if (spNE.getSelectedItem().toString().equalsIgnoreCase("4")) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(251, 197);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                } else {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(259, 197);
                    canvas.showText("X");
                    canvas.endText();
                    canvas.restoreState();
                }


                //REINCIDENCIA
                if(swReincidencia.isChecked()) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(370, 195);
                    canvas.showText("SI");
                    canvas.endText();
                    canvas.restoreState();
                } else {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(385, 195);
                    canvas.showText("NO");
                    canvas.endText();
                    canvas.restoreState();
                }

                //NUMERO DE INFRACCION
                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(460, 190);
                canvas.showText(etNumeroActa.getText().toString());
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(150, 123);
                canvas.showText(etNumeroActa.getText().toString());
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(60, 60);
                canvas.showText("NOTA: En atencion a :"+ peticionb);
                canvas.endText();
                canvas.restoreState();






                doc.close();



            }catch (DocumentException e) {
                System.err.println(e.getMessage() + " doc ");
            }catch (IOException C) {

            }

        } else if(formato.equalsIgnoreCase("orden")) {
            //construccion
            try {

                //ambiental
                //File file = new File(Environment.getExternalStorageDirectory() + "/Infracciones/fotografias/" + etNumeroActa.getText().toString().replace("/", "_") + "/" + etNumeroActa.getText().toString().replace("/", "_")+ ".txt");

                int c = 10;

                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String  ext = file.getName().substring(file.getName().indexOf(".")+1);
                String tipo = mime.getMimeTypeFromExtension(ext);
                //System.err.println(tipo);


                Paragraph p;

                doc.open();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.orden_previa);
                Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ov_2022);
                bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
                Image img;

                try {

                    img = Jpeg.getInstance(stream.toByteArray());
                    img.setAbsolutePosition(0, 0);

                    float width = doc.getPageSize().getWidth();
                    float height = doc.getPageSize().getHeight();

                    img.scaleToFit(width, height);
                    doc.add(img);

                } catch (BadElementException e) {
                    System.err.println(e.getMessage() + " BadElementException");
                } catch (MalformedURLException e) {
                    System.err.println(e.getMessage() + " MalformedURLException");
                } catch (IOException e) {
                    System.err.println(e.getMessage() + " IOException");
                } catch (DocumentException e) {
                    System.err.println(e.getMessage() + " DocumentException");
                }

                PdfContentByte canvas = write.getDirectContent();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(34, 834+c);
                canvas.showText(this.direccion + "   " + spZona.getSelectedItem().toString());
                canvas.endText();
                canvas.restoreState();

                /*canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 12);
                canvas.moveText(240, 827+c);
                canvas.showText(etNumeroActa.getText().toString());
                canvas.endText();
                canvas.restoreState();*/

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",new Font(Font.HELVETICA,10,Color.BLACK)));

                Font font = new Font(Font.BOLD,10,Color.BLACK);


                p = new Paragraph("  " ,font);
                p.setAlignment(Paragraph.ALIGN_RIGHT);
                doc.add(p);

                doc.add(new Paragraph(" ",new Font(Font.HELVETICA,3,Color.BLACK)));

                if(id == 4) {
                    if(etNombreComercial.getText().toString().equalsIgnoreCase("")) {
                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(80, 796 + c);
                        canvas.showText("Propietario o Representante Legal");
                        canvas.endText();
                        canvas.restoreState();
                    } else {
                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(80, 796 + c);
                        canvas.showText(etNombreComercial.getText().toString());
                        canvas.endText();
                        canvas.restoreState();
                    }
                } else {
                    if(id==3) {
                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(80, 796f + c);
                        canvas.showText(etNombreV.getText().toString());
                        canvas.endText();
                        canvas.restoreState();
                    } else {
                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(80, 796f + c);
                        canvas.showText(etPropietario.getText().toString() + " " + etApellidoP.getText().toString() + " " + etApellidoM.getText().toString());
                        canvas.endText();
                        canvas.restoreState();
                    }
                }


                p = new Paragraph("                    " ,font1);
                p.setAlignment(Paragraph.ALIGN_LEFT);
                doc.add(p);

                String dato = "";
                dato += " " + etCalle.getText().toString();

                if (!etNumero.getText().toString().equalsIgnoreCase(""))
                    dato += " número exterior " + etNumero.getText().toString();
                if(!etNuemroInterior.getText().toString().equalsIgnoreCase(""))
                    dato += " interior " + etNuemroInterior.getText().toString();
                if(!etEntreC.getText().toString().equalsIgnoreCase("")) {
                    dato += " entre la calle " + etEntreC.getText().toString();
                    if(!etEntreC1.getText().toString().equalsIgnoreCase(""))
                        dato += " y la calle " + etEntreC1.getText().toString();
                }
                if (!etLote.getText().toString().equalsIgnoreCase(""))
                    dato += " lote " + etLote.getText().toString();
                if (!etManzana.getText().toString().equalsIgnoreCase(""))
                    dato += " manzana " + etManzana.getText().toString();
                if(!etReferencia.getText().toString().equalsIgnoreCase(""))
                    dato += " " + etReferencia.getText().toString();
                txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)));
                if(txt.length != 1)
                    txt = Justificar.justifocarTexto(Justificar.Conversion(Justificar.Conversion(dato)),24);

                txt = Justificar.justifocarTexto1(dato.trim(),125);
                int x1 = 775+c;

                for(int y = 0;y < txt.length; y++) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(80, x1);
                    canvas.showText(txt[y]);
                    canvas.endText();
                    canvas.restoreState();
                    x1-=10;
                }

                p = new Paragraph("                        ",new Font(Font.HELVETICA,7,Color.BLACK));
                doc.add(p);

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(80, 750+c);
                canvas.showText(etFraccionamiento.getText().toString() + " " + etCondominio.getText().toString());
                canvas.endText();
                canvas.restoreState();

                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));

                doc.add(new Paragraph(" ",font1));

                int count = 0;

                double lin;

                lin = ("  " + competencias+ " " + regla).length();

                //System.err.println(porc);
                count += Math.ceil(lin/180);
                System.out.println(count+"holaaaaaa");

                Log.i(TAG, "imprimirPrevia 1: " );
			    	/*String c = "";
			    	for (int i = 0; i < comp.length; i++) {
			    		if(!TextUtils.isEmpty(comp[i].trim())) {
				    		if(i == 0)
				    			c += comp[i] + "\n";
				    		else
				    			c += ", " + comp[i];
			    		}
					}*/
                int l = 720;
			    	/*for (int i = 0; i < comp.length; i++) {
			    		if(!TextUtils.isEmpty(comp[i].trim())) {

					    	canvas.saveState();
					        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
					        canvas.beginText();
					        canvas.setFontAndSize(bf, 9);
					        canvas.moveText(20, l+c);
					        canvas.showText(comp[i]);
					        canvas.endText();
					        canvas.restoreState();

					        l -= 10;
			    		}
			    	}*/

                //doc.add(new Paragraph("  " + competencias + " " + regla,font1));
                doc.add(new Paragraph("  ",font1));

                if(count <= 1)
                    doc.add(new Paragraph(" ",font1));

                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",new Font(Font.HELVETICA,4,Color.BLACK)));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));

                //doc.add(new Paragraph(" ",font1));

                count = 0;
                lin = ("  " + spnombre.getSelectedItem().toString() + ", " + spNombreA.getSelectedItem().toString() + "," + spNombreA1.getSelectedItem().toString() + "," + spNombreA2.getSelectedItem().toString()+ "," + spNombreA3.getSelectedItem().toString()+ "," + spNombreA4.getSelectedItem().toString()).length();
                count += Math.ceil(lin/120);

                System.out.println(count);

                String insp = spnombre.getSelectedItem().toString().trim();
                String noInsp = etNoI.getText().toString().trim();

                if(!TextUtils.isEmpty(spNombreA.getSelectedItem().toString().trim())) {
                    insp += ", " + spNombreA.getSelectedItem().toString().trim();
                    noInsp += ", " +etNoA.getText().toString().trim();
                }
                if(!TextUtils.isEmpty(spNombreA1.getSelectedItem().toString().trim())) {
                    insp += ", " + spNombreA1.getSelectedItem().toString().trim();
                    noInsp += ", " +etNoA1.getText().toString().trim();
                }
                if(!TextUtils.isEmpty(spNombreA2.getSelectedItem().toString().trim())) {
                    insp += ", " + spNombreA2.getSelectedItem().toString().trim();
                    noInsp += ", " +etNoA2.getText().toString().trim();
                }
                if(!TextUtils.isEmpty(spNombreA3.getSelectedItem().toString().trim())) {
                    insp += ", " + spNombreA3.getSelectedItem().toString().trim();
                    noInsp += ", " +etNoA3.getText().toString().trim();
                }
                if(!TextUtils.isEmpty(spNombreA4.getSelectedItem().toString().trim())) {
                    insp += ", " + spNombreA4.getSelectedItem().toString().trim();
                    noInsp += ", " +etNoA4.getText().toString().trim();
                }

                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",font1));
                //doc.add(new Paragraph(" ",font1));

          // doc.add(new Paragraph(" ",font1));
                Log.i(TAG, "imprimirPrevia 2: " );

                doc.add(new Paragraph(" ",new Font(Font.HELVETICA,4f,Color.BLACK)));

                //p = new Paragraph("  " + spnombre.getSelectedItem().toString() + ", " + spNombreA.getSelectedItem().toString() + "," + spNombreA1.getSelectedItem().toString() + "," + spNombreA2.getSelectedItem().toString()+ "," + spNombreA3.getSelectedItem().toString()+ "," + spNombreA4.getSelectedItem().toString(),font1);
                p = new Paragraph("" + insp,new Font(Font.HELVETICA,8.8f,Color.BLACK));
                p.setAlignment(Paragraph.ALIGN_LEFT);
                doc.add(p);

                if(count <= 1)
                    doc.add(new Paragraph(" ",new Font(Font.HELVETICA,6,Color.BLACK)));

                doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7,Color.BLACK)));
                String f1 = "";

                for(int x=0;x < folios.size();x++) {
                    if(!folios.get(x).trim().equalsIgnoreCase(""))
                        f1+=folios.get(x) + ",";
                }
                try {
                    f1=f1.substring(0,f1.length()-1);
                }catch (Exception e){

                    Log.e(TAG, "imprimirPrevia3: ", e);
                }

                p = new Paragraph("                                                          " ,font1);
                p.setAlignment(Paragraph.ALIGN_LEFT);
                doc.add(p);

                String vig = etVigI.getText().toString();

                String [] vig1 = vig.split("-");

                int d1,m,ax;

                d1=Integer.parseInt(vig1[2]);
                m=Integer.parseInt(vig1[1]);
                ax=Integer.parseInt(vig1[0]);

                String vigencia=MainActivity.vigencia;
                String vigencia_inicial=MainActivity.vigencia_inicial;
                String []recorte1=vigencia.split("-");
                String []recorte2=vigencia_inicial.split("-");
                vigencia_inicial=vigencia_inicial(recorte2[1]);
                vigencia=vigencia_final(recorte1[1]);

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(150, 443+c);
                //canvas.showText("01 de Abril");
                canvas.showText(recorte2[2]+" de "+ vigencia_inicial);
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(284, 443f+c);
                canvas.showText(String.valueOf(ax).substring(2, 4));
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(350, 443+c);
                canvas.showText(recorte1[2] + " de " + vigencia);
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(515, 443f+c);
                canvas.showText(String.valueOf(ax).substring(2, 4));
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(208, 432.2f+c);
                canvas.showText(fol + "," + f1);
                canvas.endText();
                canvas.restoreState();

				    /*canvas.saveState();
			        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			        canvas.beginText();
			        canvas.setFontAndSize(bf, 9);
			        canvas.moveText(150, 380+c);
			        canvas.showText(noInsp);
			        canvas.endText();
			        canvas.restoreState();*/
                Log.i(TAG, "imprimirPrevia 4: " );
                String motivo = etMotivo.getText().toString().trim() + " ";
                if(id == 2) {
                    motivo = "Verificar y constatar que cuenta con " + etMotivo.getText().toString().trim();
                }
                if(id == 4) {
                    String art = "";
                    int x = 1;

                    for (int i = 0; i < reg.length; i++) {
                        if (reg[i] > 0) {
                            motivo += (x) + " " + conceptos.get(i) + ",";
                            art += articulo.get(i) + " Fracción " + fraccion.get(i) + ",";
                            x+=1;
                        }
                    }
                    art = art.substring(0, art.length() - 1);
                    motivo += " Asi mismo, cualquier otra actividad relacionada con la normatividad aplicable y que sea regulada por el Municipio de Zapopan Jalisco, con respecto a la ejecución de trabajos de construcción, remodelación, demolición, movimiento de tierras, excavación, reparación o restauración de cualquier género, así como cualquier acto de ocupacion o utilizacion del suelo que se lleve a cabo en el Municipio de Zapopan. Con base a los articulos: 2, 3, 5, 7  Fracciones I a la VI, 167, 168, 169, 171. ";
                    motivo += art + " del Reglamento de Construccion para el Municipio de Zapopan Jalisco";

                }

                doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",new Font(Font.HELVETICA,5f,Color.BLACK)));
                //doc.add(new Paragraph(" ",font1));
                //doc.add(new Paragraph(" ",font1));

                /*doc.add(new Paragraph(" ",font1));
                doc.add(new Paragraph(" ",new Font(Font.HELVETICA,7f,Color.BLACK)));
                doc.add(new Paragraph(" ",new Font(Font.HELVETICA,9f,Color.BLACK)));*/

                if(motivo.length()>1500){
                    p = new Paragraph(motivo,new Font(Font.HELVETICA,6.2f,Color.BLACK));
                    p.setLeading(10);
                    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                    //p.setFont(new Font(Font.HELVETICA,3));
                    doc.add(p);
                }else{
                    p = new Paragraph(motivo,new Font(Font.HELVETICA,9.35f,Color.BLACK));
                    p.setLeading(10);
                    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                    //p.setFont(new Font(Font.HELVETICA,3));
                    doc.add(p);
                }





                int d = 5;

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(270, 109f);
                canvas.showText(dia + "");
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(315, 109f);
                canvas.showText(me.toUpperCase());
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(448f, 109f);
                canvas.showText(String.valueOf(a).substring(2,4) + "");
                canvas.endText();
                canvas.restoreState();


                Log.i(TAG, "imprimirPrevia 5: " );
                if(id == 2) {
                    if(!cbDatos.isChecked()) {
                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(86, 91f);
                        canvas.showText(etNombreV.getText().toString() + " " + spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString());
                        canvas.endText();
                        canvas.restoreState();
                    }else{
                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(86, 91f);
                        canvas.showText(etNombreV.getText().toString() + " " + spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString());
                        canvas.endText();
                        canvas.restoreState();
                    }
                } else {
                    if(!cbDatos.isChecked()) {
                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(86, 91f);
                        canvas.showText(etNombreV.getText().toString() + " " + spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString() + " " + etVManifiesta.getText().toString());
                        canvas.endText();
                        canvas.restoreState();
                    }else{
                        canvas.saveState();
                        bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                        canvas.beginText();
                        canvas.setFontAndSize(bf, 9);
                        canvas.moveText(86, 91f);
                        canvas.showText(etNombreV.getText().toString() + " " + spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString());
                        canvas.endText();
                        canvas.restoreState();
                    }
                }

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(515, 89.5f);
                canvas.showText(hr);
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(65, 79f);
                canvas.showText(dia + "");
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(155, 79f);
                canvas.showText(me.toUpperCase(Locale.getDefault()));
                canvas.endText();
                canvas.restoreState();

                canvas.saveState();
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                canvas.beginText();
                canvas.setFontAndSize(bf, 9);
                canvas.moveText(265.2f, 79f);
                canvas.showText(a + "");
                canvas.endText();
                canvas.restoreState();

                if(cbFirma.isChecked()) {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(155, 65f);
                    canvas.showText("Si");
                    canvas.endText();
                    canvas.restoreState();
                } else {
                    canvas.saveState();
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                    canvas.beginText();
                    canvas.setFontAndSize(bf, 9);
                    canvas.moveText(155, 65f);
                    canvas.showText("No");
                    canvas.endText();
                    canvas.restoreState();
                }
                doc.close();
            } catch (DocumentException e) {
                System.err.println(e.getMessage() + " doc ");
                Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos1", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 15);
                toast.show();
            } catch (IOException e) {
                System.err.println(e.getMessage() + " IOE ");
                Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos2", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 15);
                toast.show();
            } catch (Exception e) {
                Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos3", Toast.LENGTH_LONG);
                toast.setGravity(0, 0, 15);
                toast.show();
                Log.e("Error al abrir", e.getMessage() + " c ");
                System.err.println(e.getMessage() + " n ");
            }
        } else if(formato.equalsIgnoreCase("citatorio")) {

            Paragraph p;

            doc.open();


            if(id == 1) {
                try {

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph(etNumeroActa.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));


                    p = new Paragraph("                                                             " + hora + "                   " + dia + "              " + getMes(mes));
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("          " + String.valueOf(a).substring(0, 2) + "                                      " + spnombre.getSelectedItem().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    System.out.println("citatoriocitatorio");

                    p = new Paragraph("                      " + etNoI.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString() + "      ");
                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                " + etCalle.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                                                        " + etFraccionamiento.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                                             " + etPropietario.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                                                   " + etNombreV.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                                            " + spManifiesta .getSelectedItem().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                                                  " + etVIdentifica.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                  " + hora);
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph(etMotivo.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));


                    doc.add(new Paragraph(" "));
                    p = new Paragraph("                                                  " + hora + "                     " + dia + "            " + getMes(mes));
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
                    p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    len = MITAD - etIfeI.getText().toString().trim().length();
                    p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph("  "));
                    doc.add(new Paragraph("  "));
                    doc.add(new Paragraph("  "));


                    len = MITAD - etNombreT.getText().toString().trim().length();
                    p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    len = MITAD - etIfeT.getText().toString().trim().length();
                    p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    doc.close();

                } catch(Exception e) {
                    System.out.println(e.getMessage() + " aqui");
                }
            } else {

                try {

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph(etNumeroActa.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                                                                 " + hora + "                   " + dia);
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                      " + getMes(mes));
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                } catch (Exception e) {

                }

            }

        } else if(formato.equalsIgnoreCase("apercibimiento")) {

            Paragraph p;

            doc.open();

            try {

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                p = new Paragraph(etNumeroActa.getText().toString());
                p.setAlignment(Paragraph.ALIGN_RIGHT);
                p.setFont(font1);
                doc.add(p);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                p = new Paragraph("                                                             " + hora + "                   " + dia + "              " + getMes(mes));
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                p = new Paragraph("                         " + spnombre.getSelectedItem().toString() + "                                                                       " + etNoI.getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                p = new Paragraph("                                                                      " + etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                p = new Paragraph("                      " + etCalle.getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                p = new Paragraph("                                                    " + etFraccionamiento.getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                p = new Paragraph("                         " + etNombreV .getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                p = new Paragraph("                                                    " + etVIdentifica.getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                p = new Paragraph("                                " + spManifiesta.getSelectedItem().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                p = new Paragraph("                                " + etGiro.getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                p = new Paragraph("                                " + etNombreT.getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                p = new Paragraph("                                " + spnombre.getSelectedItem().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                p = new Paragraph("                                               " + etIfeT2.getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                doc.add(new Paragraph(" "));

                p = new Paragraph("   " + etSeleccion.getText().toString());
                p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                p.setFont(font1);
                doc.add(p);

                p = new Paragraph("   " + conti + " " + etInfraccion.getText().toString());
                p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                p.setFont(font1);
                doc.add(p);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                p = new Paragraph("                                                                 20");
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                String fe [] = etfecha.getText().toString().split("/");

                p = new Paragraph("                                                       " + fe[0] + "                         " + getMes(Integer.parseInt(fe[1])));
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                len = MITAD - spnombre.getSelectedItem().toString().trim().length();

                p = new Paragraph("          " + spnombre.getSelectedItem().toString().trim() + mitad(len) + " " + etNombreV.getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));

                len = MITAD - etNombreT.getText().toString().trim().length();

                p = new Paragraph("          " + etNombreT.getText().toString().trim() + mitad(len) + " " + etNombreT1.getText().toString());
                p.setAlignment(Paragraph.ALIGN_LEFT);
                p.setFont(font1);
                doc.add(p);

                doc.close();

            } catch(DocumentException e) {

            }

        } else if(formato.equalsIgnoreCase("hechos")) {

            Paragraph p;

            doc.open();

            if(id == 2) {

                try {


                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    p = new Paragraph(etNumeroActa.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                    p.setFont(font1);
                    doc.add(p);
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                                                    " + hora + "           " +  dia);
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("    " + getMes(mes));
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("   "  + spnombre.getSelectedItem().toString() + "                                   " + etNoI.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("   "  + spNombreA.getSelectedItem().toString() + "                                   " + etNoA.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph("   "  + etNum.getText().toString() + "         " + etFecham.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("             " + etCalle.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                                 " + etFraccionamiento.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                                 " + etNombreV.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                          " + etVManifiesta.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                   " + etVIdentifica.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);



                    p = new Paragraph("                          " + etVManifiesta.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));


                    p = new Paragraph(etSeleccion.getText().toString() + "." + etInfraccion.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                        " + etManifiesta.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                                                           " + hr);
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("     " + fechas[0] + "        " + fechas[1]);
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
                    p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    len = MITAD - etIfeI.getText().toString().trim().length();
                    p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph("  "));

                    len = MITAD - etNombreT.getText().toString().trim().length();
                    p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    len = MITAD - etIfeT.getText().toString().trim().length();
                    p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    doc.close();

                } catch (Exception e) {

                }
            } else {

                try {


                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    p = new Paragraph(etNumeroActa.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                    p.setFont(font1);
                    doc.add(p);
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                                                    " + hora + "           " +  dia );
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("   " + getMes(mes) + "   "  +   String.valueOf(a).substring(0,2) );
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("   "  + spnombre.getSelectedItem().toString() + "                                   " + etNoI.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("   "  + spNombreA.getSelectedItem().toString() + "                                   " + etNoA.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                  "  + etNum.getText().toString() + "            " + etFecham.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph(etNumero.getText().toString() + " " + etNuemroInterior.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_RIGHT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                    " + etCalle.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                                 " + etFraccionamiento.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                                 " + etPropietario.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                                 " + etNombreV.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                          " + etVManifiesta.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                   " + etVIdentifica.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);



                    p = new Paragraph("                          " + etVManifiesta.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("                          " + etMotivo.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));


                    p = new Paragraph(etSeleccion.getText().toString() + "." + etInfraccion.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                        " + etManifiesta.getText().toString());
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    p = new Paragraph("                                                                           " + hr);
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    p = new Paragraph("     " + fechas[0] + "        " + fechas[1]);
                    p.setAlignment(Paragraph.ALIGN_LEFT);
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));

                    len = MITAD - spnombre.getSelectedItem().toString().trim().length();
                    p = new Paragraph(" " + spnombre.getSelectedItem().toString().trim() + mitad(len) + etNombreV.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    len = MITAD - etIfeI.getText().toString().trim().length();
                    p = new Paragraph(" " + etIfeI.getText().toString().trim() + mitad(len) + etVIdentifica.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph(" "));
                    doc.add(new Paragraph("  "));

                    len = MITAD - etNombreT.getText().toString().trim().length();
                    p = new Paragraph(" " + etNombreT.getText().toString().trim() + mitad(len) + etNombreT1.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    len = MITAD - etIfeT.getText().toString().trim().length();
                    p = new Paragraph(" " + etIfeT.getText().toString().trim() + mitad(len) + etIfeT2.getText().toString());
                    p.setFont(font1);
                    doc.add(p);

                    doc.close();

                }catch (DocumentException e) {
                    System.err.println(e.getMessage() + " doc ");
                }catch (Exception e) {
                    Toast toast  = Toast.makeText(getApplicationContext(), "Verificar los datos que esten completos", Toast.LENGTH_LONG);
                    toast.setGravity(0, 0, 15);
                    toast.show();
                    Log.e("Error al abrir", e.getMessage() + " c ");
                    System.err.println(e.getMessage() + " n ");
                }

            }

        }
    }
	
	public String mitad(int lenght) {
		String str = "";
		for (int i = 0; i < lenght; i++) {
			str += " "; 
		}
		return str;
	}
	
	public void mostrarResutados(String query) {
		arregloInfraccion.clear();
        arregloInfraccion1.clear();
		//query = conceptoAcento(query);
		buscarInfraccionL(query);
		if(!arregloInfraccion.isEmpty()){
            spInfraccion.setAdapter(new ArrayAdapter<String>(this,R.layout.multiline_spinner_dropdown_item,arregloInfraccion1));
		    //spInfraccion.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.multiline_spinner_dropdown_item,arregloInfraccion));
	    }
		else
			spInfraccion.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.multiline_spinner_dropdown_item));
		
		
	}
	
	public void buscarInfraccionL(String search) {
		GestionBD gestionar = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
		SQLiteDatabase db = gestionar.getReadableDatabase();
		Cursor cursor = null;
        String textofiltro="";
        String Vyu="";
        Log.e("campo",reglamentoC);
       if(reglamentoC.length()>3) {
           if (reglamentoC == "Buscar en Todos los reglamentos" || reglamentoC.equals("Buscar en Todos los reglamentos") || reglamentoC.contains("Buscar en Todos los reglamentos")) {
               textofiltro = " ";
           }else{
             textofiltro="length(rtrim("+reglamentoC.trim()+")) >0 and";
             Vyu=reglamentoC.trim();
           }
       }else{
            textofiltro=" ";
        }
        String[] recorte=null;
       String armado="";
        if(search.trim().contains(" ")){
            String replaceString=search.trim().replace(' ','/');
            System.out.println("asignar/ "+replaceString);
            recorte=replaceString.split("/");
            Vyu="length(rtrim("+reglamentoC.trim()+")) >0";
            
        }



        Log.e("c_reglamento",textofiltro);
        Log.e("id", String.valueOf(id));



        if(reglamentoC=="Buscar en Todos los reglamentos" && search.equals("") ||reglamentoC==" " && search.equals("") ){
            if(id==5){
                cursor = db.rawQuery("SELECT * FROM c_infraccion WHERE  vigente = 'S' order by infraccion; ", null);
                Log.i("entro 1 if:","entro");
            }else{

                cursor = db.rawQuery("SELECT * FROM c_infraccion WHERE id_c_direccion in ( '" + id + "',3) AND vigente = 'S' order by infraccion; ", null);
                Log.i("entro 1 if:","entro");
            }

        }else if(reglamentoC=="Buscar en Todos los reglamentos" && !search.equals("") || reglamentoC==" " && !search.equals("") ){

            if(id==5){
                cursor = db.rawQuery("SELECT * FROM c_infraccion where REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n')  like " + "REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER('%" + search + "%'),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') and  vigente = 'S' order by infraccion; ", null);
                Log.i("entro 2 if:","entro");
            }else{
                cursor = db.rawQuery("SELECT * FROM c_infraccion where REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n')  like " + "REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER('%" + search + "%'),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') and id_c_direccion in ( '" + id + "',3) AND vigente = 'S' order by infraccion; ", null);
                Log.i("entro 2 if:","entro");
            }

        }
        else{
            if(recorte!=null) {
                if (recorte.length > 1) {
                    for (int i = 0; i < recorte.length; i++) {
                        if (recorte.length - i == 1) {
                            armado +=   "REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like"  + "  REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER('%" + recorte[i] + "%'),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n')  ";
                        } else {
                            armado += " REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like " + "  REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER('%" + recorte[i] + "%'),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') or ";
                        }
                    }


                    if (id == 5) {
                        cursor = db.rawQuery("SELECT * FROM c_infraccion WHERE " + textofiltro + "  REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like " + armado + " AND vigente = 'S' order by infraccion ; ", null);
                        Log.i("entro else:", "entro");
                        Log.i("query:", armado);
                    } else {
                        if (textofiltro.equals("length(rtrim(reg_policia)) >2 and")) {
                            cursor = db.rawQuery("SELECT * FROM c_infraccion WHERE " + textofiltro + "  REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like " + armado + " and id_c_direccion in( '" + id + "','" + 3 + "') AND vigente = 'S' order by infraccion ; ", null);
                            Log.i("entro else:", "entro");
                            Log.i("query:", armado);
                        } else {
                            cursor = db.rawQuery("SELECT * FROM c_infraccion WHERE " + textofiltro + "  REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like " + armado + " and id_c_direccion = '" + id + "'  AND vigente = 'S' order by infraccion; ", null);
                            Log.i("entro else:", "entro");
                            Log.i("query:", armado);
                        }

                    }


                }
            }else {


                if (id == 5) {
                    cursor = db.rawQuery("SELECT * FROM c_infraccion WHERE " + textofiltro + "  REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like  " + "REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER('%" + search + "%'),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n')   AND vigente = 'S' order by infraccion; ", null);
                    Log.i("entro else:", "entro1");
                    //Log.i("query:",);
                } else {
                    if (textofiltro.equals("length(rtrim(reg_policia)) >2 and")) {
                        cursor = db.rawQuery("SELECT * FROM c_infraccion WHERE " + textofiltro + "  REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like " + "REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER('%" + search + "%'),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') and id_c_direccion in( '" + id + "','" + 3 + "') AND vigente = 'S' order by infraccion; ", null);
                        Log.i("entro else:", "entro2");
                    } else {
                        cursor = db.rawQuery("SELECT * FROM c_infraccion WHERE " + textofiltro + "  REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like " + "REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER('%" + search + "%'),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') and id_c_direccion in( '" + id + "',3)  AND vigente = 'S' order by infraccion; ", null);
                        Log.i("entro else:", "entro3");
                    }

                }
            }


        }

		if(cursor.moveToFirst()){

                arregloInfraccion.add(" ");
                arregloInfraccion1.add(" ");
//Recorte indentificadores
    		do{
    			if(cursor.getString(cursor.getColumnIndex("vigente")).trim().equalsIgnoreCase("S")) {
                    id_hecho.add(cursor.getInt(0));
                    Log.i("listado", "reglamento: " + reglamentoC);
                    if(textofiltro.length()<1 || textofiltro.isEmpty() || textofiltro==""|| reglamentoC.isEmpty() || reglamentoC=="" || reglamentoC==null ){
                        if(id!=4) {
                            /*String recorteHecho=cursor.getString(2);
                            arregloInfraccion.add(cursor.getString(2).substring(4,recorteHecho.length()));
                            arregloInfraccion1.add(cursor.getString(2).substring(4,recorteHecho.length()));*/
                            arregloInfraccion.add(cursor.getString(2));
                            arregloInfraccion1.add(cursor.getString(2));
                        }else{
                            arregloInfraccion.add(cursor.getString(2));
                            arregloInfraccion1.add(cursor.getString(2));
                        }
                        //Log.i("listado", "Infraccion: " + cursor.getString(2));
                    } else{
                        Log.i("listado", "Infraccion: " + textofiltro);
                        if(id!=4) {
                            /*String recorteHecho2=cursor.getString(2);
                            arregloInfraccion.add(cursor.getString(2).substring(4,recorteHecho2.length()));
                            arregloInfraccion1.add(cursor.getString(2).substring(4,recorteHecho2.length()) + " " + cursor.getString(cursor.getColumnIndex(reglamentoC.trim())));
*/                          arregloInfraccion.add(cursor.getString(2));
                            arregloInfraccion1.add(cursor.getString(2) + " " + cursor.getString(cursor.getColumnIndex(reglamentoC.trim())));

                        }else{
                            arregloInfraccion.add(cursor.getString(2));
                            arregloInfraccion1.add(cursor.getString(2) + " " + cursor.getString(cursor.getColumnIndex(reglamentoC.trim())));

                        }
                       // Log.i("listado", "Infraccion: " + cursor.getString(2) + " " + cursor.getString(cursor.getColumnIndex(reglamentoC.trim())));
                       // Log.i("listado", "Infraccion: " + cursor.getString(2));

                    }
    			}
    		}while(cursor.moveToNext());
    	}
		cursor.close();

		
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	    String m1 = "Inspeccionar físicamente que los trabajos o urbanización en proceso, cuenten y presenten los permisos correspondientes como son: ";
		switch (buttonView.getId()) {
		case R.id.cbFlag:
			if(isChecked) 
				hech = "los hechos antes descritos, son detectados en flagrancia y constituyen una infracciÔøΩn a lo dispuesto por los artÔøΩculos:";
			else 
				hech = "los hechos antes descritos, constituyen una infracciÔøΩn a lo dispuesto por los artÔøΩculos:";
			break;
			
		case R.id.cbFirma:
			System.err.println(cbFirma.isChecked());
			if (isChecked) {
				System.err.println("si");
			} else {
				System.err.println("no");
			}
			break;

            case R.id.cbDatos:
                if(isChecked) {
                    cbDatos2.setChecked(false);
                    etNombreV.setText("Propietario y/o Representante Legal y/o Encargado");
                    etNombreV.setEnabled(false);

                    etVManifiesta.setEnabled(true);
                    etVManifiesta.setText("No manifiesta");
                    /*if(id==2)
                        spManifiesta.setSelection(3);

                    if(id==4)
                        spManifiesta.setSelection(2);
                    if(id==5)
                        spManifiesta.setSelection(3);*/
                    for(int i=0;i<spManifiesta.getCount();i++){
                        if(spManifiesta.getItemAtPosition(i).toString().equals("Otro")){
                            spManifiesta.setSelection(i);
                        }
                    }

                    spManifiesta.setEnabled(false);

                    //etVIdentifica.setEnabled(false);
                    etVIdentifica.setEnabled(true);
                    //etVIdentifica.setHint("Indique descripcion del visitado");
                   /*if(id==2)
                       spIdentifica.setSelection(5);

                    if(id==4)
                    spIdentifica.setSelection(5);
                    if(id==5)
                        spIdentifica.setSelection(5);*/
                    for(int i=0;i<spIdentifica.getCount();i++){
                        if(spIdentifica.getItemAtPosition(i).toString().equals("Media filiacion")){
                            spIdentifica.setSelection(i);
                        }
                    }



                    spIdentifica.setEnabled(false);
                    etPropietario.setText("Se desconoce");
                    //etPropietario.setEnabled(false);
                } else {
                    etNombreV.setEnabled(true);
                    etVManifiesta.setEnabled(true);
                    spManifiesta.setEnabled(true);
                    etVIdentifica.setEnabled(true);
                    spIdentifica.setEnabled(true);
                    etPropietario.setEnabled(true);
                }
                break;
            case R.id.cbDatos2:
                if(isChecked) {
                    cbDatos.setChecked(false);

                    etNombreV.setText("Propietario y/o Representante Legal y/o Encargado");
                    etNombreV.setEnabled(false);

                    etVManifiesta.setEnabled(true);
                    etVManifiesta.setText("No manifiesta");
                    /*if(id==2)
                        spManifiesta.setSelection();

                    if(id==4)
                        spManifiesta.setSelection(2);
                    if(id==5)
                        spManifiesta.setSelection(3);*/
                    for(int i=0;i<spManifiesta.getCount();i++){
                        if(spManifiesta.getItemAtPosition(i).toString().equals("Otro")){
                            spManifiesta.setSelection(i);
                        }
                    }

                    spManifiesta.setEnabled(false);

                    //etVIdentifica.setEnabled(false);
                    etVIdentifica.setEnabled(true);
                    //etVIdentifica.setHint("Indique descripcion del visitado");
                   /* if(id==2)
                        spIdentifica.setSelection(6);

                    if(id==4)
                        spIdentifica.setSelection(6);
                    etVIdentifica.setText("persona alguna");
                    if(id==5)
                        spIdentifica.setSelection(6);*/

                    for(int i=0;i<spIdentifica.getCount();i++){
                        if(spIdentifica.getItemAtPosition(i).toString().equals("No se identifica")){
                            spIdentifica.setSelection(i);
                        }
                    }
                    etVIdentifica.setText("persona alguna");
                    spIdentifica.setEnabled(false);
                    etPropietario.setText("Se desconoce");
                    //etPropietario.setEnabled(false);
                } else {
                    etNombreV.setEnabled(true);
                    etVManifiesta.setEnabled(true);
                    spManifiesta.setEnabled(true);
                    etVIdentifica.setEnabled(true);
                    spIdentifica.setEnabled(true);
                    etPropietario.setEnabled(true);
                }
                break;

		default:
			break;
		}
		//etMotivo.setText(m1);
		System.out.println(hech);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		String sel = "";
		
		switch (arg0.getId()) {
		case R.id.spNombreAcompanante1:
			
			etIfeA1.setEnabled(false);
			etNoA1.setEnabled(false);
			etVigA1.setEnabled(false);
			etNombreT1.setEnabled(true);
			sel = spNombreA1.getItemAtPosition(arg2).toString();
			buscarAcompanante1(sel.toString());
			etIfeA1.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA1);
			etNoA1.setText(noA1);
			etVigA1.setText(vigA1);
			
			
			if(arg2 != 0){
				etIfeA1.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA1);
				etNoA1.setText(noA1);
				etVigA1.setText(vigA1);
				int p = arg2-1;
				id_inspector3 = id_i3.get(p);
			}
				
			else{
				if(resov) {
					etIfeA1.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA1);
					etNoA1.setText(noA1);
					etVigA1.setText(vigA1);
					id_inspector3 = id_i3.get(arg2);
				} else {
					etIfeA1.setText("");
					etNoA1.setText("");
					etVigA1.setText("");
					/*etIfeA1.setEnabled(true);
					etNoA1.setEnabled(true);
					etVigA1.setEnabled(true);*/
					id_inspector3 = 0;
				}
			}
			
			if(consu) {
				etIfeA1.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA1);
				etNoA1.setText(noA1);
				etVigA1.setText(vigA1);
				etIfeA1.setEnabled(false);
				etNoA1.setEnabled(false);
				etVigA1.setEnabled(false);
			}
			pos ++;
			
			Log.i("idinspector3", id_inspector3 + "");
			break;

		case R.id.spNombreAcompanante2:
			etIfeA2.setEnabled(false);
			etNoA2.setEnabled(false);
			etVigA2.setEnabled(false);
			sel = spNombreA2.getItemAtPosition(arg2).toString();
			buscarAcompanante2(sel.toString());
			etIfeA2.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA2);
			etNoA2.setText(noA2);
			etVigA2.setText(vigA2);
			
			if(arg2 != 0){
				etIfeA2.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA2);
				etNoA2.setText(noA2);
				etVigA2.setText(vigA2);
				id_inspector4 = id_i4.get(arg2-1);
			}
				
			else{
				if(resov){
					etIfeA2.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA2);
					etNoA2.setText(noA2);
					etVigA2.setText(vigA2);
					id_inspector4 = id_i4.get(arg2-1);
				} else {
					etIfeA2.setText("");
					etNoA2.setText("");
					etVigA2.setText("");
					/*etIfeA2.setEnabled(true);
					etNoA2.setEnabled(true);
					etVigA2.setEnabled(true);*/
					id_inspector4 = 0;
				}
			}
			if(consu) {
				etIfeA2.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA2);
				etNoA2.setText(noA2);
				etVigA2.setText(vigA2);
				etIfeA2.setEnabled(false);
				etNoA2.setEnabled(false);
				etVigA2.setEnabled(false);
			}
			pos ++;
			
			Log.i("idinspector4", id_inspector4 + "");
			break;
		case R.id.spNombreAcompanante3:
			etIfeA3.setEnabled(false);
			etNoA3.setEnabled(false);
			etVigA3.setEnabled(false);
			sel = spNombreA3.getItemAtPosition(arg2).toString();
			buscarAcompanante3(sel.toString());
			etIfeA3.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA3);
			etNoA3.setText(noA3);
			etVigA3.setText(vigA3);
			
			if(arg2 != 0){
				etIfeA3.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA3);
				etNoA3.setText(noA3);
				etVigA3.setText(vigA3);
				int p = arg2-1;
				id_inspector5 = id_i5.get(p);
			}
				
			else{
				if(resov) {
					etIfeA3.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA3);
					etNoA3.setText(noA3);
					etVigA3.setText(vigA3);
					id_inspector5 = id_i5.get(arg2-1);
				} else {
					etIfeA3.setText("");
					etNoA3.setText("");
					etVigA3.setText("");
					/*etIfeA3.setEnabled(true);
					etNoA3.setEnabled(true);
					etVigA3.setEnabled(true);*/
					id_inspector5 = 0;
				}
			}
			
			if(consu) {
				etIfeA3.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA3);
				etNoA3.setText(noA3);
				etVigA3.setText(vigA3);
				etIfeA3.setEnabled(false);
				etNoA3.setEnabled(false);
				etVigA3.setEnabled(false);
			}
			pos ++;
			
			Log.i("idinspector5", id_inspector5 + "");
			break;
		case R.id.spNombreAcompanante4:
			etIfeA4.setEnabled(false);
			etNoA4.setEnabled(false);
			etVigA4.setEnabled(false);
			sel = spNombreA4.getItemAtPosition(arg2).toString();
			buscarAcompanante4(sel.toString());
			etIfeA4.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" +  ifeA4);
			etNoA4.setText(noA4);
			etVigA4.setText(vigA4);
			
			if(arg2 != 0) {
				etIfeA4.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA4);
				etNoA4.setText(noA4);
				etVigA4.setText(vigA4);
				int p = arg2-1;
				id_inspector6 = id_i6.get(p);
			}
				
			else{
				if(resov) {
					etIfeA4.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA4);
					etNoA4.setText(noA4);
					etVigA4.setText(vigA4);
					id_inspector6 = id_i6.get(arg2-1);
				}else {
					etIfeA4.setText("");
					etNoA4.setText("");
					etVigA4.setText("");
					/*etIfeA4.setEnabled(true);
					etNoA4.setEnabled(true);
					etVigA4.setEnabled(true);*/
					id_inspector6 = 0;
				}
			}
			if(consu) {
				etIfeA4.setText("CREDENCIAL PARA VOTAR EXPEDIDA POR EL INSTITUTO FEDERAL ELECTORAL:" + ifeA4);
				etNoA4.setText(noA4);
				etVigA4.setText(vigA4);
				etIfeA4.setEnabled(false);
				etNoA4.setEnabled(false);
				etVigA4.setEnabled(false);
			}
			pos ++;
			
			Log.i("idinspector6", id_inspector6 + "");
			break;
			
		case R.id.spReglamento:
			if(arg2 != 0) {
				idComp = idCompetencia.get(arg2);
				competencias = competencia.get(arg2);
				regla = reglamento.get(arg2);
			}
			break;
			
		case R.id.spMedida:
            Log.e("medida","aqio con " + id);
			if(!spMedida.getSelectedItem().toString().trim().equalsIgnoreCase("")) {
				//medidas1 = spMedida.getSelectedItem().toString();
                if(id!=4) {
                    etArticulo.setText("");
                }

                concatA="";
                concatB="";
                concatM="";
                //fa="";
                norepeat.clear();
				if(contador==0) {
				    etMedida.setVisibility(View.VISIBLE);
				    etMedida.setEnabled(false);
				    btneliminarA.setVisibility(View.VISIBLE);
                    etMedida.append(spMedida.getSelectedItem().toString());

                }
				if(contador==1) {
                    etMedida1.setVisibility(View.VISIBLE);
                    etMedida1.setEnabled(false);
                    btneliminarA1.setVisibility(View.VISIBLE);
                    etMedida1.append(spMedida.getSelectedItem().toString());
                }
                if(contador==2) {
                    etMedida2.setVisibility(View.VISIBLE);
                    etMedida2.setEnabled(false);
                    btneliminarA2.setVisibility(View.VISIBLE);
                    etMedida2.append(spMedida.getSelectedItem().toString());
                }

                if(contador==3) {
                    etMedida3.setVisibility(View.VISIBLE);
                    etMedida3.setEnabled(false);
                    btneliminarA3.setVisibility(View.VISIBLE);
                    etMedida3.append(spMedida.getSelectedItem().toString());
                }
                if(contador==4) {
                    etMedida4.setVisibility(View.VISIBLE);
                    etMedida4.setEnabled(false);
                    btneliminarA4.setVisibility(View.VISIBLE);
                    etMedida4.append(spMedida.getSelectedItem().toString());
                }

                if(id == 2 | id == 5) {
                    btnArticulos.setEnabled(true);
                        /*if(spMedida.getSelectedItem().toString().trim().contains("Clausura") || spMedida.getSelectedItem().toString().contains("CLAUSURA")) {
                            Toast toast = Toast.makeText(getApplicationContext(),"Agregar sellos de clausura",Toast.LENGTH_LONG);
                            toast.setGravity(0,0,15);
                            toast.show();
                            res = true;
                        } else
                            res = false;
                        if(res)
                            etNumeroSellos.setVisibility(View.VISIBLE);
                        else
                            etNumeroSellos.setVisibility(View.GONE);*/


                        Log.e("agrego medida",orden.get(spMedida.getSelectedItemPosition()).trim());
                        Log.e("agrego articulo",art.get(spMedida.getSelectedItemPosition()).trim());
                        reglaArt.add(orden.get(spMedida.getSelectedItemPosition()).trim());
                        SeguimientoM1.add(spMedida.getSelectedItem().toString());
                        reglaArt2.add(art.get(spMedida.getSelectedItemPosition()).trim());
                        //concatM = spMedida.getSelectedItem().toString() + ",";
                        if(contador==0)
                            concatM = spMedida.getSelectedItem().toString();
                        else
                            concatM = ", "+spMedida.getSelectedItem().toString();

                        contador++;
                        // }
                    for(int i=0;i<SeguimientoM1.size();i++){
                        if(SeguimientoM1.get(i).contains("Clausura") ||  SeguimientoM1.get(i).contains("CLAUSURA")){
                            etNumeroSellos.setVisibility(View.VISIBLE);
                            break;
                        }else{
                            etNumeroSellos.setVisibility(View.GONE);

                        }
                    }

				    /*if(id==4){
                        Log.e("medida","aqio con 5");
                        etMedida.setText(spMedida.getSelectedItem().toString().trim());
                        etArticulo.setText(art.get(spMedida.getSelectedItemPosition()).trim() + " del " + orden.get(spMedida.getSelectedItemPosition()).trim());
                    }*/

				}
				if(id == 4) {
                    btnArticulos.setEnabled(true);
                    medidax.add(spMedida.getSelectedItem().toString().trim());
                    Log.e("princioidenrsdfcejrwn",medidas1 + ", hola");
				    //Log.i("i",spMedida.getSelectedItem().toString() + " " + spMedida.getSelectedItem().toString().contains("CLAU"));
				    if(spMedida.getSelectedItem().toString().trim().contains("Clausura") || spMedida.getSelectedItem().toString().contains("CLAUSURA")) {
				        Toast toast = Toast.makeText(getApplicationContext(),"Agregar sellos de clausura",Toast.LENGTH_LONG);
				        toast.setGravity(0,0,15);
				        toast.show();
				        res = true;
                    } else
				        res = false;
				    /*for(int i = 0;i<medidax.size();i++){
				        medidas1+=medidax.get(i).trim();
                    }*/

                    if(contador==0)
                        concatM = spMedida.getSelectedItem().toString();
                    else
                        concatM = ", "+spMedida.getSelectedItem().toString();

                    contador++;

                    //etMedida.append(concatM);
                    SeguimientoM1.add(spMedida.getSelectedItem().toString());
                    for(int i=0;i<SeguimientoM1.size();i++){
                        if(SeguimientoM1.get(i).contains("Clausura") ||  SeguimientoM1.get(i).contains("CLAUSURA")){
                            etNumeroSellos.setVisibility(View.VISIBLE);
                            break;
                        }else{
                            etNumeroSellos.setVisibility(View.GONE);

                        }
                    }
				    /*if(res)
                        etNumeroSellos.setVisibility(View.VISIBLE);
				    else
                        etNumeroSellos.setVisibility(View.GONE);
                        */

				    Log.e("medidas1",medidas1.trim());
				}
                Toast toast2 = Toast.makeText(InfraccionesActivity.this, "Medidas Precautorias seleccionadas"+contador, Toast.LENGTH_LONG);
                toast2.setGravity(0, 0, 15);
                toast2.show();
			}
			break;
			
		case R.id.spInspectorT:
			buscarTestigo(spInspectorT);
			
			etNombreT.setText(spInspectorT.getSelectedItem().toString().trim());
			etIfeT.setText(idT);
			//spIdentificaT.setSelection(0);
			break;
			
		case R.id.spInspectorT1:
			buscarTestigo(spInspectorT1);
			
			etNombreT1.setText(spInspectorT1.getSelectedItem().toString().trim());
			etIfeT2.setText(idT1);
			//spIdentificaT1.setSelection(0);
			break;
			
		case R.id.spdesignado1:
			if(spdesignado1.getItemAtPosition(arg2).toString().equalsIgnoreCase("inspector")) {
				//spInspectorT1.setSelection(0);
				spInspectorT1.setEnabled(true);
                //selectValue(spdesignado1,"Credencial oficial folio");
				/*etIfeA.setText(ifeA);
				etNoA.setText(noA);
				etVigA.setText(vigA);
				etNombreT.setText(spNombreA.getSelectedItem().toString());
				etIfeT.setText(ifeA);*/
			}
			else {
				spInspectorT1.setSelection(0);
				spInspectorT1.setEnabled(false);
				etIfeT2.setText("");
				/*etIfeA.setText("");
				etNoA.setText("");
				etVigA.setText("");
				etNombreT.setText("");
				etIfeT.setText("");*/
			}
			break;

            case R.id.spuso:
                usoSueloH(spuso.getSelectedItem().toString());
                break;
            case R.id.spusoH:
                Log.i("uso h",spUsoH.getSelectedItem().toString());
                break;

            case R.id.spZona:
                Log.i("zona",spZona.getSelectedItem().toString());
                break;

		default:
			break;
		}
	}

	public String setMedidas(String medidas) {
        return medidas + " HOLA MUNDO";
    }
    public void mostrarArt(int x){
        if(id!=4) {
            etArticulo.setText(" ");
        }
        //String medidas="";
        medidas1="";
        for(int i=0; i< SeguimientoM1.size(); i++ ){
            if(!SeguimientoM1.get(i).equals(" ")) {
                medidas1 += SeguimientoM1.get(i) + ",";
            }
            Log.i("nice:", "medidas: "+SeguimientoM1.get(i));
        }
        Log.i("completo:", "medidas completa: "+medidas1);
        int tamano=medidas1.length();
        Log.i("tamaño:", String.valueOf(SeguimientoM1.size()));

        String coma = medidas1.substring(tamano-1,tamano);
        if(coma.contains(",")){
           medidas1=medidas1.trim().substring(0,medidas1.length()-1);

            //etMedida.setText(medidas1);
        }

        /*for(int i=0;i<reglaArt.size();i++){
            Log.i(TAG, "reglaArt: "+reglaArt.get(i));
        }

        for(int i=0;i<reglaArt2.size();i++){
            Log.i(TAG, "reglaArt2: "+reglaArt2.get(i));
        }*/

        if(x>0) {
            if (reglaArt.size() > 0) {


                int i=0;
                int z=0;

                if ( reglaArt.size()>1) {
                    Log.i("tamaño array", String.valueOf(reglaArt.size()));
                    //Log.i("comparacion",reglaArt.get(i)+" = " +reglaArt.get(x));
                    boolean f=false;
                    boolean f2=false;
                    for (int c = 0; c < reglaArt.size(); c++) {
                        z=c+1;
                        if(z<reglaArt.size()) {
                            if (reglaArt.get(0).equals(reglaArt.get(z))) {

                                Log.i("comparacion", reglaArt.get(i) + " = " + reglaArt.get(z));
                                if(f==false){
                                    concatA += reglaArt2.get(c) + ", "+reglaArt2.get(z)+", ";
                                    concatB = reglaArt.get(i);
                                    f=true;
                                }else{
                                    concatA += reglaArt2.get(z)+ ", ";
                                }

                            } else {

                                Log.i("comparacion2", reglaArt.get(i) + " = " + reglaArt.get(z));

                                norepeat.add(z);
                                Log.i("norepeat numero", String.valueOf(z));

                                concatA += reglaArt2.get(i);
                                concatB = reglaArt.get(i);
                                concatA = concatA.substring(0, concatA.length() - 1) + " del " + concatB+", ";

                            }

                        }else{
                            break;
                        }
                    }



                }else{
                    if(reglaArt.size()==1){
                        concatA += reglaArt2.get(0) + ", ";
                        concatB = reglaArt.get(0);
                    }

                }


                //concatA=art.get(spMedida.getSelectedItemPosition()).trim()+" del " + orden.get(spMedida.getSelectedItemPosition()).trim()+",";

            }
            if (norepeat.size() > 1 ) {


                Log.i("entro a norepeat","YES");
                Log.e("tamaño de no repeat", String.valueOf(norepeat.size()));
                if(norepeat.size()==2 ){
                    for (int i = 0; i < norepeat.size(); i++) {
                        if(reglaArt.get(norepeat.get(0)).equals(reglaArt.get(norepeat.get(1)))){
                            concatA += reglaArt2.get(norepeat.get(0))+", "+reglaArt2.get(norepeat.get(1));
                            concatB = reglaArt.get(norepeat.get(0));
                            concatA = concatA + " del " + concatB;
                            Log.i("iguales repeat 1",concatA);
                            etArticulo.append(concatA);
                            break;
                        }else  if(!reglaArt.get(norepeat.get(0)).equals(reglaArt.get(norepeat.get(1)))){
                            concatA = reglaArt2.get(norepeat.get(i));
                            concatB = reglaArt.get(norepeat.get(i));
                            concatA = concatA + " del " + concatB;
                            Log.i("diferentes repeat 1",concatA);
                            etArticulo.append(concatA);
                        }


                    }
                }

                if(norepeat.size()>2){
                    int z=0;
                    for (int i = 0; i < norepeat.size(); i++) {
                        z=i+1;
                        if(z<norepeat.size()) {
                            if (reglaArt.get(norepeat.get(0)).equals(reglaArt.get(norepeat.get(i + 1)))) {
                                concatA += reglaArt2.get(norepeat.get(0).intValue()) + ", " + reglaArt2.get(norepeat.get(i+1).intValue());
                                concatB = reglaArt.get(norepeat.get(0).intValue());
                                concatA = concatA + " del " + concatB;
                                Log.i("iguales repeat 2", concatA);
                                etArticulo.append(concatA);

                            } else if (!reglaArt.get(norepeat.get(0)).equals(reglaArt.get(norepeat.get(i + 1))) ) {
                                concatA = reglaArt2.get(norepeat.get(i+1).intValue());
                                concatB = reglaArt.get(norepeat.get(i+1).intValue());
                                concatA = concatA + " del " + concatB;
                                Log.i("diferentes repeat 2 ", concatA);
                                etArticulo.append(concatA);
                            }

                        }else{
                            break;
                        }
                    }
                }



            }else{
                if(norepeat.size()==1){
                    etArticulo.append(concatA+" ");

                    Log.e("entro","norepeat==1");
                    Log.e("entro",norepeat.get(0).toString());
                    concatA = reglaArt2.get(norepeat.get(0).intValue());
                    concatB = reglaArt.get(norepeat.get(0).intValue());
                    concatA = concatA + " del " + concatB;

                    etArticulo.append(concatA);
                }
                if(norepeat.size()<=0){
                    //concatA = concatA.substring(0, concatA.length() - 1) + " del " + concatB;
                    etArticulo.append(concatA);
                    etArticulo.append(concatB);



                }

            }
        }


    }

    public void reiniciarA(int ids,String buscar){
        /*reglaArt.clear();
        reglaArt2.clear();*/

     for(int i=0;i<SeguimientoM1.size();i++) {
         if (SeguimientoM1.get(i).equals(buscar.trim())) {
             SeguimientoM1.remove(i);
             break;
         }
     }

     int poscionSp=0;
        for (int i = 0; i < spMedida.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spMedida.getItemAtPosition(i).toString().equalsIgnoreCase(buscar.trim())) {
                poscionSp=i;
                break;
            }
        }


     for(int i=0;i<reglaArt.size();i++){
         Log.e("reglaArt1",reglaArt.get(i)+"=="+orden.get(poscionSp));
         if(reglaArt.get(i).equals(orden.get(poscionSp).trim())){
             Log.e("entro eliminar1:","si");
             reglaArt.remove(i);
             break;
         }
     }

     for(int i=0;i<reglaArt2.size();i++){
         Log.e("reglaArt2",reglaArt2.get(i)+"=="+art.get(poscionSp));
         if(reglaArt2.get(i).equals(art.get(poscionSp).trim())){
             Log.e("entro eliminar2:","si");
             reglaArt2.remove(i);
             break;
         }
     }






        contador=contador-1;
        concatA="";
        concatB="";
        concatM="";
        //fa="";
        norepeat.clear();
        spMedida.setSelection(0);

        if(ids==0) {
            if(id!=4) {
                etArticulo.setText("");
            }
            etMedida.setText("");
            etMedida.setVisibility(View.GONE);
            btneliminarA.setVisibility(View.GONE);
        }
        if(ids==1) {
            if(id!=4) {
                etArticulo.setText("");
            }
            etMedida1.setText("");
            etMedida1.setVisibility(View.GONE);
            btneliminarA1.setVisibility(View.GONE);
        }
         if(ids==2) {
            if(id!=4) {
                etArticulo.setText("");
            }
            etMedida2.setText("");
            etMedida2.setVisibility(View.GONE);
            btneliminarA2.setVisibility(View.GONE);
        }
          if(ids==3) {
            if(id!=4) {
                etArticulo.setText("");
            }
            etMedida3.setText("");
            etMedida3.setVisibility(View.GONE);
            btneliminarA3.setVisibility(View.GONE);
        }
           if(ids==4) {
            if(id!=4) {
                etArticulo.setText("");
            }
            etMedida4.setText("");
            etMedida4.setVisibility(View.GONE);
            btneliminarA4.setVisibility(View.GONE);
        }
        if(id==4 || id==5 || id==2) {
            for (int i = 0; i < SeguimientoM1.size(); i++) {
                if (SeguimientoM1.get(i).contains("Clausura") || SeguimientoM1.get(i).contains("CLAUSURA")) {
                    etNumeroSellos.setVisibility(View.VISIBLE);
                    break;
                } else {
                    etNumeroSellos.setVisibility(View.GONE);

                }
            }
        }

    }
    public void buscarTestigo(Spinner sp){
    	
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
    		Cursor c = db.rawQuery("SELECT * FROM C_Inspector WHERE nombre = '" + sp.getSelectedItem().toString().trim() + "' and trim(vigente) = 'S'", null);
    		if(c.moveToFirst()){
    			do{
    				if(sp.getId() == R.id.spInspectorT) {
    					idT = c.getString(c.getColumnIndex("folio"));
    				} else {
    					idT1 = c.getString(c.getColumnIndex("folio"));
    				}
    				/*ifeA = c.getString(2);
    				noA = c.getString(c.getColumnIndex("no_empleado"));
    				vigA = c.getString(c.getColumnIndex("vigencia"));*/
    				//Log.i("listado", "nombre: " + c.getString(1) + " IFE " + c.getString(2) + " NO_e " + c.getString(3) + " vigencia " + c.getString(4));
    			}while(c.moveToNext());
    		c.close();
    		}
    		else{
    			if(sp.getId() == R.id.spInspectorT) {
					idT = "";
				} else {
					idT1 = "";
				}
    			//Toast.makeText(this, "No hay inspectores en la bd", Toast.LENGTH_SHORT).show();
    		}
    		c.close();
    	}catch (SQLiteException e) {
    		Log.i("ERROR FATAL", e.getMessage());
    	}finally {
    		db.close();
    	}
    	
    }



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	
	
	public final Handler mHandler = new Handler(new Handler.Callback() {
		
		@SuppressWarnings("unchecked")
		@Override
		public boolean handleMessage(Message msg) {
			Log.d(TAG, "mHandler.handleMessage(" + msg + ")");
			
			switch (msg.what) {
			
			case BixolonPrinter.MESSAGE_DEVICE_NAME:
				mConnectedDeviceName = msg.getData().getString(BixolonPrinter.KEY_STRING_DEVICE_NAME);
				Toast.makeText(getApplicationContext(), mConnectedDeviceName, Toast.LENGTH_LONG).show();
				return true;
				
				case BixolonPrinter.MESSAGE_BLUETOOTH_DEVICE_SET:
					/*Set<BluetoothDevice> bluetoothDevicesSet = (Set<BluetoothDevice>)msg.obj;
					for (BluetoothDevice device : bluetoothDevicesSet) {
						if(device.getName().equals("SPP-R200II")) {
							mBixolonPrinter.connect(device.getAddress());
							break;
						}
					}*/
					if (msg.obj == null) {
						Toast.makeText(getApplicationContext(), "No paired device", Toast.LENGTH_SHORT).show();
					} else {
						DialogManager.showBluetoothDialog(InfraccionesActivity.this, (Set<BluetoothDevice>) msg.obj,"comercio");
					}
					return true;
				
				case BixolonPrinter.MESSAGE_STATE_CHANGE:
					
					if(msg.arg1 == BixolonPrinter.STATE_CONNECTED) {
						//isConected = true;
						
						/*BitmapDrawable drawable = (BitmapDrawable)getResources().getDrawable(com.pgm.transito.R.drawable.logo1);
						Bitmap bitmap = drawable.getBitmap();
						mBixolonPrinter.printBitmap(bitmap, BixolonPrinter.ALIGNMENT_CENTER, 150, 50, true,true,true);
						String texto = "hola";
						mBixolonPrinter.printText(texto, BixolonPrinter.ALIGNMENT_CENTER, BixolonPrinter.TEXT_ATTRIBUTE_FONT_A | BixolonPrinter.TEXT_ATTRIBUTE_UNDERLINE1, BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, true);
						mBixolonPrinter.printText(texto, BixolonPrinter.ALIGNMENT_LEFT, BixolonPrinter.TEXT_ATTRIBUTE_FONT_B | BixolonPrinter.TEXT_ATTRIBUTE_UNDERLINE2, BixolonPrinter.TEXT_SIZE_HORIZONTAL2 | BixolonPrinter.TEXT_SIZE_VERTICAL2, true);
						mBixolonPrinter.cutPaper(true);
						System.out.println("conectado");*/
					}
					break;
				case BixolonPrinter.STATE_NONE:
					//isConected = false;
					break;
				case BixolonPrinter.MESSAGE_PRINT_COMPLETE:
					//mBixolonPrinter.disconnect();
					//mHandler.sendEmptyMessage(0);
					break;
			}
			
			
			return true;
			}
	});

    public String horaTOletra(String hora){
        String [] corteHora = hora.split(":");

        //Hora
        if(corteHora[0].trim().equals("07")){
            if(Integer.valueOf(corteHora[1])<=10){
                if(corteHora[0].trim().equals("01"))
                    return "siete horas con un minuto";
                if(corteHora[0].trim().equals("02"))
                    return "siete horas con dos minutos";
                if(corteHora[0].trim().equals("03"))
                    return "siete horas con tres minutos";
                if(corteHora[0].trim().equals("04"))
                    return "siete horas con cuatro minutos";
                if(corteHora[0].trim().equals("05"))
                    return "siete horas con cinco minutos";
                if(corteHora[0].trim().equals("06"))
                    return "siete horas con seis minutos";
                if(corteHora[0].trim().equals("07"))
                    return "siete horas con siete minutos";
                if(corteHora[0].trim().equals("08"))
                    return "siete horas con ocho minutos";
                if(corteHora[0].trim().equals("09"))
                    return "siete horas con nueve minutos";
                if(corteHora[0].trim().equals("10"))
                    return "siete horas con diez minutos";
            }
            if(Integer.valueOf(corteHora[1])>10 && Integer.valueOf(corteHora[1])<=20){
                if(corteHora[0].trim().equals("11"))
                    return "siete horas con once minutos";
                if(corteHora[0].trim().equals("12"))
                    return "siete horas con doce minutos";
                if(corteHora[0].trim().equals("13"))
                    return "siete horas con trece minutos";
                if(corteHora[0].trim().equals("14"))
                    return "siete horas con catorce minutos";
                if(corteHora[0].trim().equals("15"))
                    return "siete horas con quince minutos";
                if(corteHora[0].trim().equals("16"))
                    return "siete horas con dieciseis minutos";
                if(corteHora[0].trim().equals("17"))
                    return "siete horas con diecisiete minutos";
                if(corteHora[0].trim().equals("18"))
                    return "siete horas con dieciocho minutos";
                if(corteHora[0].trim().equals("19"))
                    return "siete horas con diecinueve minutos";
                if(corteHora[0].trim().equals("20"))
                    return "siete horas con veinte minutos";
            }
            if(Integer.valueOf(corteHora[1])>20 && Integer.valueOf(corteHora[1])<=30){
                if(corteHora[0].trim().equals("21"))
                    return "siete horas con veintiuno minutos";
                if(corteHora[0].trim().equals("22"))
                    return "siete horas con veintidós minutos";
                if(corteHora[0].trim().equals("23"))
                    return "siete horas con veintitrés minutos";
                if(corteHora[0].trim().equals("24"))
                    return "siete horas con veinticuatro minutos";
                if(corteHora[0].trim().equals("25"))
                    return "siete horas con veinticinco minutos";
                if(corteHora[0].trim().equals("26"))
                    return "siete horas con veintiséis minutos";
                if(corteHora[0].trim().equals("27"))
                    return "siete horas con veintisiete minutos";
                if(corteHora[0].trim().equals("28"))
                    return "siete horas con veintisiete minutos";
                if(corteHora[0].trim().equals("29"))
                    return "siete horas con veintinueve minutos";
                if(corteHora[0].trim().equals("30"))
                    return "siete horas con treinta minutos";
            }
            if(Integer.valueOf(corteHora[1])>30 && Integer.valueOf(corteHora[1])<=40){
                if(corteHora[0].trim().equals("21"))
                    return "siete horas con veintiuno minutos";
                if(corteHora[0].trim().equals("22"))
                    return "siete horas con veintidós minutos";
                if(corteHora[0].trim().equals("23"))
                    return "siete horas con veintitrés minutos";
                if(corteHora[0].trim().equals("24"))
                    return "siete horas con veinticuatro minutos";
                if(corteHora[0].trim().equals("25"))
                    return "siete horas con veinticinco minutos";
                if(corteHora[0].trim().equals("26"))
                    return "siete horas con veintiséis minutos";
                if(corteHora[0].trim().equals("27"))
                    return "siete horas con veintisiete minutos";
                if(corteHora[0].trim().equals("28"))
                    return "siete horas con veintisiete minutos";
                if(corteHora[0].trim().equals("29"))
                    return "siete horas con veintinueve minutos";
                if(corteHora[0].trim().equals("30"))
                    return "siete horas con treinta minutos";
            }
            if(Integer.valueOf(corteHora[1])>40 && Integer.valueOf(corteHora[1])<=50){
                if(corteHora[0].trim().equals("21"))
                    return "siete horas con veintiuno minutos";
                if(corteHora[0].trim().equals("22"))
                    return "siete horas con veintidós minutos";
                if(corteHora[0].trim().equals("23"))
                    return "siete horas con veintitrés minutos";
                if(corteHora[0].trim().equals("24"))
                    return "siete horas con veinticuatro minutos";
                if(corteHora[0].trim().equals("25"))
                    return "siete horas con veinticinco minutos";
                if(corteHora[0].trim().equals("26"))
                    return "siete horas con veintiséis minutos";
                if(corteHora[0].trim().equals("27"))
                    return "siete horas con veintisiete minutos";
                if(corteHora[0].trim().equals("28"))
                    return "siete horas con veintisiete minutos";
                if(corteHora[0].trim().equals("29"))
                    return "siete horas con veintinueve minutos";
                if(corteHora[0].trim().equals("30"))
                    return "siete horas con treinta minutos";
            }
            if(Integer.valueOf(corteHora[1])>50 && Integer.valueOf(corteHora[1])<60){
                if(corteHora[0].trim().equals("21"))
                    return "siete horas con veintiuno minutos";
                if(corteHora[0].trim().equals("22"))
                    return "siete horas con veintidós minutos";
                if(corteHora[0].trim().equals("23"))
                    return "siete horas con veintitrés minutos";
                if(corteHora[0].trim().equals("24"))
                    return "siete horas con veinticuatro minutos";
                if(corteHora[0].trim().equals("25"))
                    return "siete horas con veinticinco minutos";
                if(corteHora[0].trim().equals("26"))
                    return "siete horas con veintiséis minutos";
                if(corteHora[0].trim().equals("27"))
                    return "siete horas con veintisiete minutos";
                if(corteHora[0].trim().equals("28"))
                    return "siete horas con veintisiete minutos";
                if(corteHora[0].trim().equals("29"))
                    return "siete horas con veintinueve minutos";
                if(corteHora[0].trim().equals("30"))
                    return "siete horas con treinta minutos";
            }
        }
        if(corteHora[0].trim().equals("08")){


        }
        if(corteHora[0].trim().equals("09")){


        }
        if(corteHora[0].trim().equals("10")){


        }
        if(corteHora[0].trim().equals("11")){


        }
        if(corteHora[0].trim().equals("12")){


        }
        if(corteHora[0].trim().equals("13")){


        }
        if(corteHora[0].trim().equals("14")){


        }
        if(corteHora[0].trim().equals("15")){


        }
        if(corteHora[0].trim().equals("16")){


        }
        if(corteHora[0].trim().equals("17")){


        }
        if(corteHora[0].trim().equals("18")){


        }
        if(corteHora[0].trim().equals("19")){


        }
        if(corteHora[0].trim().equals("20")){


        }
        if(corteHora[0].trim().equals("21")){


        }
        if(corteHora[0].trim().equals("22")){


        }
        if(corteHora[0].trim().equals("23")){


        }
      return " ";
    }


	@SuppressLint({ "InflateParams", "DefaultLocale" })
	private void printSampleGDL() {
		if (mSampleDialog == null) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			final View view = inflater.inflate(R.layout.order, null);

            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            Date todayDate = new Date();
            String thisDate = currentDate.format(todayDate);
            String [] na = thisDate.split("/");
            Log.i("fecha", na[0] + "/" + na[1] + "/" + na[2]);
            fecha = na[0] + "/" + na[1] + "/" + na[2];
            String [] fechas = fecha.split("/");
            int dia, mes,a;
            String me;
            for (int i = 0; i < fechas.length; i++) {
                System.out.println(fechas[i]);
            }

            dia = Integer.parseInt(fechas[0]);
            mes = Integer.parseInt(fechas[1]);
            String an = fechas[2];
            //a = Integer.parseInt(fechas[2].substring(2, 4));
            a = Integer.parseInt(fechas[2]);
            System.out.println("entro");
			
			TextView tvMulta = (TextView)view.findViewById(R.id.tvText);
			tvMulta.setMovementMethod(new ScrollingMovementMethod());
            String vigencia=MainActivity.vigencia;
            String vigencia_inicial=MainActivity.vigencia_inicial;
            String []recorte1=vigencia.split("-");
            String []recorte2=vigencia_inicial.split("-");
            vigencia_inicial=vigencia_inicial(recorte2[1]);
            vigencia=vigencia_final(recorte1[1]);
            String hechos=etSeleccion.getText().toString().trim();
            String exterior="";
            String interior=" ";
            if(etNumero.getText().toString().length()>0){
                exterior="número exterior:"+ etNumero.getText().toString();
            }
            if(etNuemroInterior.getText().toString()!=" "){
                interior="número interior:"+ etNuemroInterior.getText().toString();
            }

            String decomiso = "";
            if(!TextUtils.isEmpty(etDecomiso.getText().toString().trim()))
                decomiso += ", decomiso: " + etDecomiso.getText().toString().trim() + " ";

            String numeroS="";
            if(etNumeroSellos.getText().toString().trim().length()>0){
                numeroS="con numero de sello(s) "+etNumeroSellos.getText().toString().trim();
            }

            String peticionb="";
            if(spPeticion.getSelectedItem().toString().equals("Flagrancia")){
                peticionb=spPeticion.getSelectedItem().toString();
            }else{
                if(etfoliopeticion.getText().length()>2){
                    peticionb=spPeticion.getSelectedItem().toString()+" con folio "+etfoliopeticion.getText().toString();

                }else{
                    peticionb=spPeticion.getSelectedItem().toString();

                }

            }

//03/05
			final String cuerpo = "Zapopan, Jalisco, a las "+hora +" "+horaTOletra(hora)+" del día "+dia+" de "+ vigencia_inicial("0"+String.valueOf(mes))+"  del año "+recorte2[0]+"." +
                    "El suscrito, "+spnombre.getSelectedItem().toString()+", Inspector Multimodal con clave operativa  "+ clave +", en términos de lo dispuesto por los artículos 70 y 73, " +
                    "segundo párrafo, de la Ley del Procedimiento Administrativo del Estado de Jalisco, me constituyo física y legalmente en la "+ etCalle.getText().toString() +
                    "  marcada(o) con el  "+ exterior+", "+interior+", entre las calles " + etEntreC.getText().toString() + " y " + etEntreC1.getText().toString() + ", Fraccionamiento: " + etFraccionamiento.getText().toString()+", cerciorado de lo anterior por haber tenido " +
                    "a la vista la placa de nomenclatura de la calle más próxima, y porque así lo corrobora quien manifiesta llamarse " + etNombreV.getText().toString() + ", " +
                    "ante quien me identifico con credencial oficial con fotografía folio número "+ folio + " , vigente del "+vigencia_inicial+" "+recorte2[0] +" a "+vigencia+ " "+recorte1[0]+" " +
                    " , expedida por el Director de Inspección y Vigilancia del Ayuntamiento de Zapopan, Jalisco; haciéndole " +
                    "saber que el motivo de mi presencia es por "+spPeticion.getSelectedItem().toString()+", y enterado de los alcances de la diligencia que por este acto se practica" +
                    " le requiero por una identificación, presentando " + spIdentifica.getSelectedItem().toString() + " " + etVIdentifica.getText().toString()+ " manifiesta ser " + etVManifiesta.getText().toString().trim() + " del giro " +
                    etGiro.getText().toString().trim() +", igualmente le hago saber el derecho que tiene de nombrar a dos personas que " +
                    "fungirán como testigos y estén presentes durante el desahogo de la visita y que de no designar a persona alguna para ello, el suscrito lo haré en" +
                    " rebeldía; en consecuencia, fueron designados por el suscrito los C.C. "+etNombreT.getText().toString()+" y " + etNombreT1.getText().toString() + ", quien se identifica con " + spIdentificaT.getSelectedItem().toString() + " " + etIfeT.getText().toString() + " , " + spIdentificaT1.getSelectedItem().toString() + " " + etIfeT2.getText().toString() + " " +
                    ", respectivamente. Acto seguido, una vez practicada la inspección, resultaron los siguientes hechos: " +hechos+
                    " Hechos que constituyen infracción a lo dispuesto por los articulo(s): "+  etInfraccion.getText().toString() + " Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados en flagrancia, " +
                    "se procede indistintamente con las siguientes medidas: "+ medidas1+ " "+numeroS + decomiso + ". Lo anterior de conformidad a lo dispuesto por los " +
                    "artículo(s): "+ etArticulo.getText().toString().trim()+". Se concede el uso de la voz al visitado para que a los hechos señalados manifieste" +
                    " lo que a su derecho convenga y aporte pruebas, enterado señala:"+  etManifiesta.getText().toString().trim()+". Finalmente, se le informa que el acta resultado de esta " +
                    "diligencia podrá ser impugnada a través del RECURSO DE REVISIÓN, previsto por el artículo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, para lo cual tendrá un plazo de 20 veinte días hábiles, contados a partir del día siguiente de la fecha en que se levante el acta correspondiente; debiendo interponer dicho recurso por escrito que presente en la oficia de la Dirección Jurídica Contenciosa dependiente de Sindicatura, en avenida Hidalgo número 151, colonia Centro de esta Ciudad. Se da por concluida esta diligencia a las "+hr+"  del " +dia +" de "+ vigencia_final("0"+ String.valueOf(mes)) +" del presente año, levantándose acta en presencia del visitado y testigos que intervinieron, firmando para constancia los que quisieron y supieron hacerlo, quedando copia legible en poder del interesado para los efectos conducentes. Lo anterior, en términos de lo dispuesto por el artículo 74 de la Ley invocada.";
			
			//extracto pruebas
			/*final String cuerpo = "En la ciudad de Zapopan, Jalisco, siendo las 16:30 horas del día 20 de Septiembre del año 2019, el suscrito FRANCISCO JAVIER VÁZQUEZ GARCÍA, Inspector Municipal con clave 0037, facultado para llevar a cabo la inspección y vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, mediante y en cumplimiento de la Orden de Visita folio número OV-2019 dictada por el Director de Inspección y Vigilancia de Zapopan, Jalisco el día 20 de Agosto, misma que en original exhibo y en copia legible entrego al visitado, Javier Contreras Gonzalez, me constituí física y legalmente en " +
					" marcada (o) con el número 2345 de la calle Estudientes, entre las calles pintores y Escultores, en la colonia y/o fraccionamiento Jardenes de Guadalupe, cerciorándome de ser este el domicilio , e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco y sus Municipios con credencial oficial con fotografía folio número 1234, vigente de Agosto de 2019 a Agosto del 2021, expedida por el Director de Inspección y Vigilancia del Gobierno municipal de Zapopan, Jalisco, ante Javier Contreras Gonzalez quien se identifica con INE , manifiesta ser  Encargado de la propiedad de Gloria Camarena Lara, le informo el derecho que le asiste para designar a dos testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a ello el suscrito lo haría en rebeldía  fueron designados los C.C. Testigo de asistencia 1 y Testigo de asistencia 2 por el Inspector, mismo que se identifican con INE 12345678, INE 07654321 respectivamente; así, como de la prerrogativa que en todo momento tiene de manifestar lo que a su derecho convenga y aportar las pruebas que considere pertinentes. Acto seguido le hago saber al visitado, una vez practicada la diligencia, los hechos encontrados y que consisten en: Falta de Licencia de Giro Comercial, industrial o de prestación de servicios los cuales constituyen infracción a lo dispuesto por los artículos: 4,1 Reglamento para el Funcionamiento de Giros Comerciales, Industriales y de Prestación de Servicios en el Municipio de Guadalajar Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados en flagrancia, se procede indistintamente con las siguientes medidas: ACTA DE VERIFICACION Lo anterior de conformidad a lo dispuesto por los artículos.12,13,15 del reglamento de pruebas En uso de su derecho al visitado: Su propio Dicho Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el artículo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco y sus municipios, el cual deberá interponerse por escrito dirigido al Presidente Municipal de Zapopan, Jalisco dentro del plazo de 20 veinte días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No. 151)."+
					"Se da por concluida esta diligencia, siendo las 16:35 horas del 20 de Septiembre del año 2019 levantándose la presente acta en presencia de los testigos que se mencionan, quedando copia legible en poder del interesado y firmando para constancia los que en ella intervinieron, quisieron y supieron hacerlo.";
			
			//extracto con el ejemplo
			final String cuerpo = "En la ciudad de Zapopan, Jalisco, siendo las 09:57 horas del día 2 de Septiembre del año 2019, el suscrito SAUL LEMUS GUERRERO, Inspector Municipal con clave IC-199, facultado para llevar a cabo la inspección y vigilancia del cumplimiento de los diversos reglamentos y leyes de aplicación municipal por parte de los particulares, misma que en original exhibo y en copia legible entrego al visitado, JACOBO MEDRANO CARDENAS, me constituí física y legalmente en EL LUGAR DONDE ILEGIBLE" +
					" marcada (o) con el número S/N de la calle LAS PALMAS, entre las calles RIO BLANCO y , en la colonia y/o fraccionamiento RINCONADA DE LOS SAUCES, cerciorándome de ser este el domicilio CORRECTO A INSPECCIONAR EN ATENCIÓN AL REPORTE ZAPMX377, e identificándome y acreditando mi personalidad en debido cumplimiento de lo señalado por el artículo 71 de la Ley del Procedimiento Administrativo del Estado de Jalisco con credencial oficial con fotografía folio número 0173, vigente de 01 AGOSTO DE 2019 a 30 SEPTIEMBRE del 2019, expedida por el Director de Inspección y Vigilancia del Gobierno municipal de Zapopan, Jalisco, ante JACOBO MEDRANO CARDENAS quien se identifica con INE 3005089789185, manifiesta ser  PROPIETARIO de JACOBO MEDRANO CARDENAS, le informo el derecho que le asiste para designar a dos testigos que estén presentes durante el desahogo de esta diligencia y que de negarse a ello el suscrito lo haría en rebeldía ACTO SEGUIDO fueron designados los C.C. MA. DEL ROSARIO SEDANO GONZALEZ por el SUSCRITO, mismo que se identifican con CREDENCIAL OFICIAL FOLIO 0062 respectivamente; así, como de la prerrogativa que en todo momento tiene de manifestar lo que a su derecho convenga y aportar las pruebas que considere pertinentes. Acto seguido le hago saber al visitado, una vez practicada la diligencia, los hechos encontrados y que consisten en: QUE AL MOMENTO DE LA INSPECCIÓN SE CONSTA DE QUE REBASA LOS METROS PERMITIDOS EN SU PERMISO QUE MOSTRO CON FOLIO131584DE FECHA 22 DE AGOSTO DE 2019POR LO QUE SE LEVANTA LA PRESENTE ACTA POR REBASAR LOS LIMITES DE MEDIDAS PERMITIDAS los cuales constituyen infracción a lo dispuesto por los artículos: 58 FRACCIÓN IV DEL REGLAMENTO DE TIANGUIS Y EN ESPACIOS PUBLICOS DEL MUNICIPIO DE ZAPOPAN JALISCO Por encuadrar dichas acciones y/u omisiones en los preceptos legales indicados y al haber sido detectados en flagrancia, se procede indistintamente con las siguientes medidas: SE LEVANTA LA PRESENTE ACTA PARA LOS FINES LEGALES Y ADMINISTRATIVOS QUE HAYA LUGAR Y SE PROCEDE CON EL INCAUTO PRECAUTORIO CONSISTENTE EN 2 CAJAS DE CARTON CUYO INTERIOR CONTIENE PLATANO Lo anterior de conformidad a lo dispuesto por los artículos. 5 FRACCIÓN IV Y V ARTICULO 10 112 FRACCIÓN II Y III, 116 , 117 ,118 , FRACCIONES II Y III Y 123 SEGUNDO PARAFO DEL REGLAMENTO DE TIANGUIS Y COMERCIO EN ESPACIOS PUBLICOS DEL MUNICIPIO DE ZAPOPAN. En uso de su derecho al visitado: SE RESERVA EL DERECHO. Finalmente, le informo que en contra de la presente acta procede el Recurso de Revisión previsto en el artículo 134 de la Ley del Procedimiento Administrativo del Estado de Jalisco, el cual deberá interponerse por escrito dirigido al Sindico Municipal de Zapopan, Jalisco dentro del plazo de 20 veinte días hábiles contados a partir del día siguiente en que la misma es notificada o se hace del conocimiento del o los interesados, entregándolo en la Dirección Jurídica Contenciosa en el edificio que ocupa la Presidencia Municipal (Av. Hidalgo No. 151)."+
					"Se da por concluida esta diligencia, siendo las 10:15 horas del 2 de Septiembre 2019 levantándose la presente acta en presencia de los testigos que se mencionan, quedando copia legible en poder del interesado y firmando para constancia los que en ella intervinieron, quisieron y supieron hacerlo.";*/
                                                           

/*PARA LA CALIFICACIÓN CORRESPONDIENTE DE LA PRESENTE ACTA DEBERÁ ACUDIR ANTE EL C. JUEZ CALIFICADOR DEL DEPARTAMENTO DE CALIFICACIÓN DE ESTE H. AYUNTAMIENTO DE ZAPOPAN, JALISCO, EN LA PLANTA BAJA DEL EDIFICIO QUE OCUPA LA UNIDAD ADMINISTRATIVA BASÍLICA.
Zapopan, Jalisco, a _________ de _______________________ del año _________.
Por recibida el Acta número ____________________________________ por la cual según consta en el cuerpo de la misma, el infractor se hace acreedor a la siguiente sanción de conformidad a lo dispuesto por los artículos __________________________________________________ de la Ley de Ingresos Vigente del Municipio de Zapopan, Jalisco, que consiste en la cantidad de: $_____________________________________________________________________";*/
			
			tvMulta.setText(cuerpo);
					
			
			mSampleDialog = new AlertDialog.Builder(InfraccionesActivity.this).setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@SuppressLint("NewApi")
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String [] str;
					
					
					mBixolonPrinter.setSingleByteFont(BixolonPrinter.CODE_PAGE_1252_LATIN1);
					
					mBixolonPrinter.setPageMode();
					InfraccionesActivity.mBixolonPrinter.setPrintArea(0, 0, 576, 200);

					Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

					mBixolonPrinter.setPrintDirection(BixolonPrinter.DIRECTION_0_DEGREE_ROTATION);
					mBixolonPrinter.setAbsoluteVerticalPrintPosition(200);
					mBixolonPrinter.setAbsolutePrintPosition(20);
					//mBixolonPrinter.printBitmap(bm, BixolonPrinter.ALIGNMENT_LEFT, 200, 65, false);
					mBixolonPrinter.printBitmap(bm, BixolonPrinter.ALIGNMENT_LEFT, 200, 65, false);
					

					

					mBixolonPrinter.formFeed(true);

					//normal mode
					
					mBixolonPrinter.lineFeed(2, false);
					
					str = Justificar.justifocarTexto(cuerpo, "a");
					
					String n = "";
					
					for (int i = 0; i < str.length; i++) {
						System.out.println(str[i]);
						n += str[i] + "\n";
						
					}
					System.err.println("n " + n);
					
					mBixolonPrinter.printText(etNumeroActa.getText().toString()+"\n\n", BixolonPrinter.ALIGNMENT_RIGHT,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.printText("ACTA DE INSPECCIÓN COMERCIO \n\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.printText(n + "\n", BixolonPrinter.ALIGNMENT_LEFT,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					/*String cuerpo2 = "Para la presente infracción se incautaron 20 sillas, 5 mesas, 1 olla, 30 botellas de refresco, un carrito y ";
					
					str = Justificar.justifocarTexto(cuerpo2, "a");
					
					n = "";
					
					for (int i = 0; i < str.length; i++) {
						System.out.println(str[i]);
						n += str[i] + "\n";
						
					}
					
					mBixolonPrinter.printText(n + " \n", BixolonPrinter.ALIGNMENT_LEFT,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);*/
					
					mBixolonPrinter.printText("El Inspector" + "\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.lineFeed(7, false);
					
					mBixolonPrinter.printText("El Visitado" + "\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.lineFeed(7, false);
					
					mBixolonPrinter.printText("Testigo" + "\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.lineFeed(7, false);
					
					mBixolonPrinter.printText("Testigo" + "\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.lineFeed(7, false);
                     String gravedad="";
                     String NE="";
                     String R="";
                    if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("1")) {
                       gravedad="1";
                    } else if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("2")) {
                        gravedad="2";
                    } else if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("3")) {
                        gravedad="3";
                    } else if (spgravedad.getSelectedItem().toString().equalsIgnoreCase("4")) {
                        gravedad="4";
                    } else {
                        gravedad="5";
                    }

                    if (spNE.getSelectedItem().toString().equalsIgnoreCase("1")) {
                       NE="1";
                    } else if (spNE.getSelectedItem().toString().equalsIgnoreCase("2")) {
                        NE="2";
                    } else if (spNE.getSelectedItem().toString().equalsIgnoreCase("3")) {
                        NE="3";
                    } else if (spNE.getSelectedItem().toString().equalsIgnoreCase("4")) {
                        NE="4";
                    } else {
                        NE="5";
                    }

                    if(swReincidencia.isChecked()) {
                        R="1";
                    } else {
                        R="0";
                    }

					/*mBixolonPrinter.printText("G 1  \t  NE 1  \t R 1" + "\n", BixolonPrinter.ALIGNMENT_CENTER,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);*/
                    mBixolonPrinter.printText("G"+gravedad+" \t "+"NE"+NE+" \t "+ "R"+R+ "\n", BixolonPrinter.ALIGNMENT_CENTER,
                            BixolonPrinter.TEXT_ATTRIBUTE_FONT_A,
                            BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					
					mBixolonPrinter.lineFeed(3, false);
					
					String cuerpo1 = "PARA LA CALIFICACIÓN CORRESPONDIENTE DE LA PRESENTE ACTA DEBERÁ ACUDIR ANTE EL C. JUEZ MUNICIPAL DEL DEPARTAMENTO DE LA UNIDAD DE JUECES CALIFICADORES DE ESTE H. AYUNTAMIENTO DE ZAPOPAN, JALISCO, EN LA PLANTA BAJA DEL EDIFICIO QUE OCUPA LA UNIDAD ADMINISTRATIVA BASÍLICA. Zapopan, Jalisco, a           de                                  del año        . Por recibida el Acta número por la cual según consta en el cuerpo de la misma, el infractor se hace acreedor a la siguiente sanción de conformidad a lo dispuesto por los artículos                                                                                                                                                              de la Ley de Ingresos Vigente del Municipio de Zapopan, Jalisco, que consiste en la cantidad de: $ ";
					
					str = Justificar.justifocarTexto(cuerpo1, "a");
					
					n = "";
					
					for (int i = 0; i < str.length; i++) {
						System.out.println(str[i]);
						n += str[i] + "\n";
						
					}
					System.err.println("n " + n);
					
					mBixolonPrinter.printText(n + " \n", BixolonPrinter.ALIGNMENT_LEFT,
							BixolonPrinter.TEXT_ATTRIBUTE_FONT_A, 
							BixolonPrinter.TEXT_SIZE_HORIZONTAL1 | BixolonPrinter.TEXT_SIZE_VERTICAL1, false);
					

					
					mBixolonPrinter.lineFeed(7, false);
					
					mBixolonPrinter.disconnect();
				}
			}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).create();
		}
		
		mSampleDialog.show();
	}
    public void mostrarReglamentos(){
        GestionBD gestionar = new GestionBD(getApplicationContext(), "inspeccion", null, 1);
        SQLiteDatabase db = gestionar.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM c_ordenamiento where  id_c_direccion='"+id+"'; ", null);


        if(cursor.moveToFirst()){
            arregloCreglamentos.add("Buscar en Todos los reglamentos");
            arregloCreglamentosx.add("");
            do{


                arregloCreglamentosx.add(cursor.getString(2));
                arregloCreglamentos.add(cursor.getString(cursor.getColumnIndex("ordenamiento")));
                //Log.i("listado", "C_reglamentos: " + cursor.getString(2));
                //Log.i("listado", "C_reglamentos: " +cursor.getString(cursor.getColumnIndex("ordenamiento")));

            }while(cursor.moveToNext());
        }
        cursor.close();

    }


	
	public void buscarPeticion() {
    	GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
    	SQLiteDatabase db = gestionarBD.getReadableDatabase();
    	try{
	    	Cursor c = db.rawQuery("SELECT * FROM c_peticion", null);
	    	Log.i("que", "SELECT * FROM c_peticion");
	    	if(c.moveToFirst()){
	    		Log.i("no", "no");
	    		peticion.clear();
	    		peticion.add("");
	    		do{
	    			peticion.add(c.getString(1));
	    			Log.i("peticion ", c.getString(1));
	    		}while(c.moveToNext());
	    		adapter1.notifyDataSetChanged();
	    	}
	    	c.close();
	    }catch (SQLiteException e) {
			Log.i("ERROR FATAL", e.getMessage());
		}finally{
	    		db.close();
		}
	}

    public void buscarCompetencia() {
	    competencias1 = "";
        GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarBD.getReadableDatabase();
        try{
            Cursor c;
                    if(id==5)
                        c = db.rawQuery("SELECT competencia FROM c_ordenamiento where id_c_direccion = " + 2, null);
                    else
                        c = db.rawQuery("SELECT competencia FROM c_ordenamiento where id_c_direccion = " + id, null);
            Log.i("que", "SELECT competencia FROM c_ordenamiento where id_c_direccion = " + id + " order by id_c_ordenamiento limit 1");
            if(c.moveToFirst()){
                Log.i("no", "no");
                do{
                    competencias1 = c.getString(c.getColumnIndex("competencia"));
                }while(c.moveToNext());
            }
            c.close();
        }catch (SQLiteException e) {
            Log.i("ERROR FATAL", e.getMessage());
        }finally{
            db.close();
        }
    }

    public void buscarCol(String condicion) {
        GestionBD gestionarBD = new GestionBD(this,"inspeccion",null,1);
        SQLiteDatabase db = gestionarBD.getReadableDatabase();
        Cursor c = null;
        fraccionamiento.clear();
        zonas.clear();
        try{
            /*
            cursor = db.rawQuery("SELECT * FROM c_infraccion WHERE REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(infraccion),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like " +
                    "REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER('%"+search+"%'),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') and id_c_direccion = '" + id + "' AND vigente = 'S' order by infraccion; ", null);
             */
            c = db.rawQuery("SELECT * FROM c_fraccionamiento where REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(fraccionamiento),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER('%"+condicion+"%'),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') order by fraccionamiento", null);
            Log.i("que", "SELECT * FROM c_fraccionamiento where REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER(fraccionamiento),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') like REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(LOWER('%"+condicion+"%'),'á','a'), 'é','e'),'í','i'),'ó','o'),'ú','u'),'ñ','n') order by fraccionamiento");
            fraccionamiento.add("Seleccionar");
            zonas.add("");
            if(c.moveToFirst()){
                Log.i("no", "no");
                do{
                    Log.i("no", c.getString(c.getColumnIndex("fraccionamiento")));
                    fraccionamiento.add(c.getString(c.getColumnIndex("fraccionamiento")));
                    zonas.add(c.getString(c.getColumnIndex("zona")));
                }while(c.moveToNext());
            }
        }catch (SQLiteException e) {
            Log.i("ERROR FATAL", e.getMessage());
        }finally{
            c.close();
            db.close();

        }
    }

    public String conceptoAcento(String concepto) {
	    concepto = concepto.replace("Á","%");
        concepto = concepto.replace("É","%");
        concepto = concepto.replace("Í","%");
        concepto = concepto.replace("Ó","%");
        concepto = concepto.replace("Ú","%");
        concepto = concepto.replace("á","%");
        concepto = concepto.replace("é","%");
        concepto = concepto.replace("í","%");
        concepto = concepto.replace("ó","%");
        concepto = concepto.replace("ú","%");
	    return concepto;
    }

    public static String addTildeOptions(String searchText) {
        return searchText.toLowerCase()
                .replaceAll("[aáàäâã]", "\\[aáàäâã\\]")
                .replaceAll("[eéèëê]", "\\[eéèëê\\]")
                .replaceAll("[iíìî]", "\\[iíìî\\]")
                .replaceAll("[oóòöôõ]", "\\[oóòöôõ\\]")
                .replaceAll("[uúùüû]", "\\[uúùüû\\]")
                .replace("*", "[*]")
                .replace("?", "[?]");
    }




}

