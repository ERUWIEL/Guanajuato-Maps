/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectou3aalg.algorithms;

import com.mycompany.proyectou3aalg.util.Arista;
import com.mycompany.proyectou3aalg.util.BusquedaUnion;
import com.mycompany.proyectou3aalg.util.Ciudad;
import com.mycompany.proyectou3aalg.util.Grafo;
import com.mycompany.proyectou3aalg.view.GrafoPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author Elite
 */
public class Kruskal_MST_Recorrido {
    
    private Grafo grafo;
    private GrafoPanel panel;
    private Set<Arista> mst = new HashSet<>();
    private int pesoTotal = 0;

    public Kruskal_MST_Recorrido(Grafo grafo, GrafoPanel panel) {
        this.grafo = grafo;
        this.panel = panel;
    }

    public void ejecutar() {
        List<Arista> aristas = new ArrayList<>(grafo.getAristas());
        aristas.sort(Comparator.comparingInt(Arista::getPeso));

        BusquedaUnion uf = new BusquedaUnion(grafo.getCiudades());

        SwingWorker<Void, Arista> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground(){
                for (Arista a : aristas) {
                    Ciudad u = a.getOrigen();
                    Ciudad v = a.getDestino();
                    if (!uf.conexión(u, v)) {
                        uf.unir(u, v);
                        mst.add(a);
                        pesoTotal += a.getPeso();
                        panel.mostrarKruskal(mst);
                        esperar(150);
                    }
                }
                return null;
            }
            
            @Override
            protected void done(){
                 mostrarVentanaResultado();
            }
            
        };
        
       worker.execute();

       
    }

    private void esperar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    private void mostrarVentanaResultado() {
        JFrame ventana = new JFrame("Árbol de expansión mínima (Kruskal)");
        JTextArea area = new JTextArea();
        area.setEditable(false);
        for (Arista a : mst) {
            area.append(a.getOrigen().getNombre() + " — " + a.getDestino().getNombre()
                    + " (" + a.getPeso() + " km)\n");
        }
        area.append("\nPeso total: " + pesoTotal + " km");
        ventana.add(new JScrollPane(area));
        ventana.setSize(350, 400);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                panel.restaurarKruskal();
            }
        });
    }
    
    
    
}
