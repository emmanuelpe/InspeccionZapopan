package com.perspective.inszap;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class navegador extends AppCompatActivity  {
 private WebView  miVisorWeb;
 String url = "http://sistemainspeccion.zapopan.gob.mx/infracciones/serverSQL/Consulta_Licencia_Construccion.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navegador);
        miVisorWeb = (WebView) findViewById(R.id.webview);
        final WebSettings ajustesVisorWeb = miVisorWeb.getSettings();
        ajustesVisorWeb.setJavaScriptEnabled(true);
        miVisorWeb.loadUrl(url);
    }

}
