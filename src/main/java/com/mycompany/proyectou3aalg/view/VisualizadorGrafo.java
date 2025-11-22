/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.proyectou3aalg.view;

import com.mycompany.proyectou3aalg.util.Ciudad;
import com.mycompany.proyectou3aalg.util.EstadoDFS;
import java.util.Map;

/**
 *
 * @author Héctor Alonso 252039
 * @author Erubiel Flores
 *
 * Interface que designa los métodos para modificar y restaurar la visualización del grafo
 */
public interface VisualizadorGrafo {
    void actualizarColores(Map<Ciudad, EstadoDFS> estados);
    void restaurarColores();
}
