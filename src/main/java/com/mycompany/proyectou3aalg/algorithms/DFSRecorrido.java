/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectou3aalg.algorithms;

import com.mycompany.proyectou3aalg.util.EstadoDFS;
import com.mycompany.proyectou3aalg.util.Arista;
import com.mycompany.proyectou3aalg.util.Ciudad;
import com.mycompany.proyectou3aalg.util.Grafo;
import com.mycompany.proyectou3aalg.view.VisualizadorGrafo;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author Héctor Alonso 252039
 * @author Erubiel Flores 252389
 *
 * Implementación de busqueda por profundidad con visualización gráfica
 */


public class DFSRecorrido {
    
    private Grafo grafo;
    private VisualizadorGrafo visualizador;
    private Map<Ciudad, EstadoDFS> estados = new HashMap<>();
    private List<Ciudad> ordenDescubrimiento = new ArrayList<>();
    private int tiempo = 0;
    private LinkedList<Ciudad> cola = new LinkedList<>();
    
    //Constructor de la clase
    public DFSRecorrido(Grafo grafo, VisualizadorGrafo visualizador) {
        this.grafo = grafo;
        this.visualizador = visualizador;
    }
    //DFS 
    public void ejecutarDesde(Ciudad semilla) {
        for (Ciudad c : grafo.getCiudades()) {
            estados.put(c, new EstadoDFS());
            cola.add(c);
        }
        
        
        SwingWorker<Void, Map<Ciudad, EstadoDFS>> worker = new SwingWorker<>() {
            //Ejecución del algoritmo en un hilo secundario
            @Override
            protected Void doInBackground() {
                dfsVisit(semilla);
                
                while (!cola.isEmpty()) {
                    Ciudad c = cola.peekFirst();
                    if (estados.get(c).color == EstadoDFS.Color.WHITE) {
                        dfsVisit(c);
                    }
                    cola.removeFirst(); 
                }
                
                return null;
            }
            
            //Regresa al hilo principal para mostrar resultados
            @Override
            protected void done() {
                mostrarVentanaDescubrimiento();
            }
        };

        worker.execute();
    }

    private void dfsVisit(Ciudad u) {
        EstadoDFS estadoU = estados.get(u);
        tiempo++;
        estadoU.descubrimiento = tiempo;
        estadoU.color = EstadoDFS.Color.GRAY;
        ordenDescubrimiento.add(u); //Lista de nodos descubiertos en orden
        visualizador.actualizarColores(estados); //GUI
        esperar(350);
        
        
            for (Arista a : grafo.getAristas()) {
                if (a.getOrigen() == u) {
                    Ciudad v = a.getDestino();
                    EstadoDFS estadoV = estados.get(v);
                    if (estadoV.color == EstadoDFS.Color.WHITE) {
                        estadoV.padre = u;
                        dfsVisit(v);
                    }
                }
            }

        
        
           
        estadoU.color = EstadoDFS.Color.BLACK;
        tiempo++;
        estadoU.finalizacion = tiempo;
        visualizador.actualizarColores(estados);
        esperar(500);
    }

    private void esperar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
    
    //Método para mostrar el orden de descubrimiento de nodos en una ventana nueva.
    private void mostrarVentanaDescubrimiento() {
        JFrame ventana = new JFrame("Orden de descubrimiento DFS");
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        for (Ciudad c : ordenDescubrimiento) {
            area.append(c.getNombre() + "\n");
        }
        ventana.add(new JScrollPane(area));
        ventana.setSize(500, 600);
        
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                visualizador.restaurarColores();
            }
        });
    }
    
}
