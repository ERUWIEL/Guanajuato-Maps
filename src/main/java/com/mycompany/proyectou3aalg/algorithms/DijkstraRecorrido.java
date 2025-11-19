/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectou3aalg.algorithms;

import com.mycompany.proyectou3aalg.util.Arista;
import com.mycompany.proyectou3aalg.util.Ciudad;
import com.mycompany.proyectou3aalg.util.Grafo;
import com.mycompany.proyectou3aalg.view.GrafoPanel;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Elite
 */
public class DijkstraRecorrido {
    
    private Grafo grafo;
    private GrafoPanel panel;
    private Ciudad origen, destino;
    private Map<Ciudad, Integer> distancias = new HashMap<>();
    private ArrayList<Ciudad> nodosvisitados = new ArrayList<>();
    private Map<Ciudad, Ciudad> padres = new HashMap<>();
    private Set<Ciudad> visitados = new HashSet<>();
    private List<Ciudad> caminoFinal = new ArrayList<>();
    private ArrayList<Arista> aristasCamino = new ArrayList<>();

    public DijkstraRecorrido(Grafo grafo, GrafoPanel panel, Ciudad origen, Ciudad destino) {
        this.grafo = grafo;
        this.panel = panel;
        this.origen = origen;
        this.destino = destino;
    }

    public void ejecutarDijkstra() {
        initialize_one_source(grafo, origen);
        PriorityQueue<Ciudad> cola = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        cola.add(origen);
        
        while(!cola.isEmpty()){
            
            //Extract-Min
            Ciudad u = cola.poll();
            System.out.println("Extrayendo de cola: " + u.getNombre());
            if (u.equals(destino)) {
                break;
            }
            if (!nodosvisitados.add(u)) {
                continue;
            }
            System.out.println("Procesando: " + u.getNombre());
            for(Arista a: grafo.getAdyacentes(u)){
                //Condición ? valorSiTrue : valorSiFalse
                //“Si u es el origen de la arista, entonces v es el destino; de lo contrario, v es el origen.”
                Ciudad v = a.getOrigen().equals(u)? a.getDestino():a.getOrigen();
                System.out.println("  Vecino: " + v.getNombre());
                System.out.println("  Distancia actual: " + distancias.get(v));
                System.out.println("  Distancia potencial: " + (distancias.get(u) + a.getPeso()));
                   
                
                if(v!=null){
                    if(relax(u,v,a.getPeso())){
                        System.out.println("  → Se mejora distancia. Se agrega a cola."); 
                       cola.add(v);
                    }
                        
                }
                          
                
            }
            
        }
        
        if (distancias.get(destino) == Integer.MAX_VALUE) {
            JOptionPane.showMessageDialog(panel, "No existe ruta entre " + origen.getNombre() + " y " + destino.getNombre());
        } else {
            reconstruirCamino();
            panel.mostrarDijkstra(caminoFinal, aristasCamino);
            mostrarVentanaResultado();
            System.out.println("Distancia más corta entre:"+origen.toString()+"y "+destino.toString()+" : "+distancias.get(destino).toString()+" km");
        }
        
    }   
      
    
    public void initialize_one_source(Grafo grafo, Ciudad s){
         for(Ciudad c: grafo.getCiudades()){
             distancias.put(c, Integer.MAX_VALUE);
         }
         distancias.put(s, 0);
         
         
    
    
    }
    
    public boolean relax(Ciudad u,Ciudad v, int peso){
        int distanciau = distancias.get(u);
        int distanciav = distancias.get(v);
        
        if(distanciau + peso < distanciav){
            distancias.put(v, distanciau+peso);
            padres.put(v, u);
            return true;
            
        
            
        }
        return false;
    }
    
    private void reconstruirCamino() {
        // Limpiar listas antes de reconstruir
        caminoFinal.clear();
        aristasCamino.clear();

        // Si el destino no es alcanzable, no hay camino
        if (distancias.get(destino) == Integer.MAX_VALUE) {
            return;
        }

        Ciudad actual = destino;
        while (actual != null) {
            // Insertamos la ciudad al inicio para mantener orden origen→destino
            caminoFinal.add(0, actual);

            Ciudad padre = padres.get(actual);
            if (padre != null) {
                // Buscar la arista que conecta actual con su padre
                for (Arista a : grafo.getAristas()) {
                    if ((a.getOrigen().equals(actual) && a.getDestino().equals(padre))
                            || (a.getDestino().equals(actual) && a.getOrigen().equals(padre))) {
                        // Insertamos al inicio para mantener orden origen→destino
                        aristasCamino.add(0, a);
                        break;
                    }
                }
            }
            actual = padre;
        }
    }
    
    
        
        
    private void mostrarVentanaResultado() {
        JDialog dialogo = new JDialog((Frame) null, "Ruta más corta", true);
        JTextArea area = new JTextArea();
        for (Ciudad c : caminoFinal) {
            area.append(c.getNombre() + "\n");
        }
        area.append("\nDistancia total: " + distancias.get(destino) + " km");
        dialogo.add(new JScrollPane(area));
        dialogo.setSize(300, 400);
        dialogo.setLocationRelativeTo(null);
        dialogo.setVisible(true);

        dialogo.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                panel.restaurarDijkstra();
            }
        });
    }
    
    
    
    
    
}
