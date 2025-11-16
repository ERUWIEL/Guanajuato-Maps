/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectou3aalg.util;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Elite
 */
public class Grafo {
    
   private List<Ciudad> ciudades;
   private List<Arista> aristas;

    public Grafo() {
        ciudades = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    public void agregarCiudad(Ciudad ciudad) {
        ciudades.add(ciudad);
    }

    public void agregarArista(Ciudad origen, Ciudad destino, int peso) {
        aristas.add(new Arista(origen, destino, peso));
    }

    
    public List<Ciudad> getCiudades() { return ciudades; }
    
    
    
    public List<Arista> getAristas() { return aristas; }
    
    
    
    
    
    
}
