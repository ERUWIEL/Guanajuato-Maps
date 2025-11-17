/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectou3aalg.algorithms;

import com.mycompany.proyectou3aalg.util.Ciudad;

/**
 *
 * @author Elite
 */
public class EstadoDFS {
    
    public enum Color {
        WHITE, GRAY, BLACK
    }

    public Color color = Color.WHITE;
    public Ciudad padre = null;
    public int descubrimiento = 0;
    public int finalizacion = 0;
    
    
    
}
