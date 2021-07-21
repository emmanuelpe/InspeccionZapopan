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

    public String mostrar(){
        String cadena = "";
        ArrayList<Articulo> listaArticulos;
        for(String reglamento : reglamentos){
            listaArticulos = this.mapaReglamentos.get(reglamento);

            if(listaArticulos.size()!=0)
                cadena+=reglamento+": ";

            for(Articulo art : listaArticulos){
                if(cadena.contains(String.valueOf(art.getArticulo()))){
                    cadena = cadena.replace(" "+String.valueOf(art.getArticulo())," "+art.getDescripcion());
                }else {
                    cadena += art.getDescripcion()+", ";
                }
            }

            cadena = cadena.replaceAll(", $",".");
        }
        return cadena;
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
