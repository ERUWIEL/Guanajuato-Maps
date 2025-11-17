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
 * @author Elite
 */
public class BusquedaUnion {
    
    private Map<Ciudad, Ciudad> padre = new HashMap<>();

    public BusquedaUnion(List<Ciudad> ciudades) {
        for (Ciudad c : ciudades) {
            padre.put(c, c);
        }
    }

    public Ciudad encontrar(Ciudad c) {
        if (padre.get(c) != c) {
            padre.put(c, encontrar(padre.get(c))); 
        }
        return padre.get(c);
    }

    public void unir(Ciudad a, Ciudad b) {
        Ciudad ra = encontrar(a);
        Ciudad rb = encontrar(b);
        if (ra != rb) {
            padre.put(ra, rb);
        }
    }

    public boolean conexión(Ciudad a, Ciudad b) {
        return encontrar(a) == encontrar(b);
    }
    
    
    
    
    
    
    
}
