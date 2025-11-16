/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectou3aalg.util;

/**
 *
 * @author Elite
 */
public class Arista {
    
   private Ciudad origen;
   private Ciudad destino;
   private int peso;
    
    
    public Arista(Ciudad origen, Ciudad destino, int peso){
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }
    
    public Ciudad getOrigen(){
        return origen;
    }
    
    public Ciudad getDestino(){
        return destino;
    }
    
    public int getPeso(){
        return peso;
    }
    
    
    
}
