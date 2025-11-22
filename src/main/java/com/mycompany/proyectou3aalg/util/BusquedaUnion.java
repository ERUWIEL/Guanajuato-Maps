/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectou3aalg.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Héctor Alonso 252039
 * @author Erubiel Flores
 *
 * Clase para implementar la estructura de Union implementada en Kruskal.
 */
public class BusquedaUnion {
    
    private Map<Ciudad, Ciudad> padre = new HashMap<>();
    /**
     * Constructor de la clase que inicializa el arreglo.
     * @param ciudades 
     */
    public BusquedaUnion(List<Ciudad> ciudades) {
        for (Ciudad c : ciudades) {
            padre.put(c, c);
        }
    }
    /**
     * Método para encontrar el conjunto al que pertenece un elemento.
     * @param c
     * @return representante del conjunto
     */
    public Ciudad encontrar(Ciudad c) {
        if (padre.get(c) != c) {
            padre.put(c, encontrar(padre.get(c))); 
        }
        return padre.get(c);
    }
    /**
     * Método para unir 2 conjuntos.
     * @param a Valor para encontrar el represente de a.
     * @param b Valor para encontrar el represente de b.
     */
    public void unir(Ciudad a, Ciudad b) {
        Ciudad ra = encontrar(a);
        Ciudad rb = encontrar(b);
        if (ra != rb) {
            padre.put(ra, rb);
        }
    }
    /**
     * Método para encontrar si una ciudad a y una ciudad b pertenecen al mismo conjunto
     * @param a
     * @param b
     * @return True si ambas ciudades pertenecen al mismo conjunto, falso en caso contrario
     */
    public boolean conexión(Ciudad a, Ciudad b) {
        return encontrar(a) == encontrar(b);
    }
    
    
    
    
    
    
    
}
