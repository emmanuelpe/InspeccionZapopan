package com.perspective.inszap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapaReglamentos {
    private ArrayList<String> reglamentos;
    private Map<String, ArrayList> mapaReglamentos;

    public MapaReglamentos(){
        this.mapaReglamentos = new HashMap<String,ArrayList>();
        this.reglamentos = Reglamento.reglamentos();
        this.cargarMap();
    }

    public void cargarMap(){
        for(String reglamento : reglamentos)
            mapaReglamentos.put(reglamento, new ArrayList<Articulo>());
    }

    public void agregarArticulo(Articulo art){
        mapaReglamentos.get(art.getTipo()).add(art);
    }

    public String fraccionRemplazar(String cad){
        cad.replace("Fracciones","");
        cad.replace("fracciones","");
        cad.replace("Fracción","");
        cad.replace("fracción","");
        cad.replace("Fraccion","");
        cad.replace("fraccion","");
        return cad;
    }

    public boolean containFraccion(String cad){
        if (cad.contains("Fracciones"))
            return true;
        if (cad.contains("fracciones"))
            return true;
        if (cad.contains("Fracción"))
            return true;
        if (cad.contains("fracción"))
            return true;
        if (cad.contains("Fraccion"))
            return true;
        if (cad.contains("fraccion"))
            return true;
        return false;
    }

    public String setFracciones(String cad){
        cad.replace("Fracciones","Fraccion(es):");
        cad.replace("fracciones","Fraccion(es):");
        cad.replace("Fracción","Fraccion(es):");
        cad.replace("fracción","Fraccion(es):");
        cad.replace("Fraccion","Fraccion(es):");
        cad.replace("fraccion","Fraccion(es):");
        return cad;
    }

    public String mostrar(){
        int inicio;
        String aux1;
        String aux2;
        String cadenaPrin = "";
        String cadena = "";
        ArrayList<Articulo> listaArticulos;
        for(String reglamento : reglamentos){
            listaArticulos = this.mapaReglamentos.get(reglamento);

            if(listaArticulos.size()!=0)
                cadena+="Articulo(s): ";

            for(Articulo art : listaArticulos){
                if(cadena.contains(String.valueOf(art.getArticulo()))){
                    inicio = cadena.indexOf(String.valueOf(art.getArticulo()));
                    try{
                        if(containFraccion(art.getDescripcion())){
                            aux1 = cadena.substring(inicio,inicio+14);
                            aux2 = fraccionRemplazar(aux1);
                            cadena = cadena.replace(aux1,aux2);
                        }
                    }catch (Exception ex){ }
                    cadena = cadena.replace(" "+String.valueOf(art.getArticulo())," "+art.getDescripcion());
                }else {
                    cadena += art.getDescripcion()+", ";
                }
            }
            cadena = cadena.replaceAll(", $",".");

            if(listaArticulos.size()!=0)
                cadena+=": "+reglamento+".";

            cadenaPrin+=cadena;
            cadena="";
        }
        cadenaPrin=setFracciones(cadenaPrin);
        return cadenaPrin;
    }

    public void ordenar(ArrayList<Articulo> lista){
        for(Articulo art : lista){
            this.mapaReglamentos.get(art.getTipo()).add(art);
        }
    }

    public int buscar(Articulo art, String reglamento){
        ArrayList<Articulo> listaArticulos = this.mapaReglamentos.get(reglamento);

        for(int i = 0; i< listaArticulos.size(); i++){
            if(listaArticulos.get(i).getDescripcion() == art.getDescripcion()){
                return i;
            }
        }
        return -1;
    }

    public String concatenar(String cad1, String cad2){
        int cont = 0;
        String aux;

        if(cad1.contains("fracción")){
            if(cad2.contains("fraccion")){

            }
        }

        return "";
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

    //Fraccion Numeral Inciso parrafo Bis

    public void cargarLista(ArrayList<Articulo> lista){
        for(Articulo art : lista){
            this.mapaReglamentos.get(art.getTipo()).add(art);
        }
    }
}
