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
            cadena+=reglamento+": ";
            for(Articulo art : listaArticulos){
                cadena += art.getDescripcion()+", ";
            }
        }
        return cadena;
    }

    public void ordenar(ArrayList<Articulo> lista){
        for(Articulo art : lista){
            this.mapaReglamentos.get(art.getTipo()).add(art);
        }
    }
}
