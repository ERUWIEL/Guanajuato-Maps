/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectou3aalg.view;

import com.mycompany.proyectou3aalg.util.EstadoDFS;
import com.mycompany.proyectou3aalg.util.Arista;
import com.mycompany.proyectou3aalg.util.Ciudad;
import static com.mycompany.proyectou3aalg.util.EstadoDFS.Color.BLACK;
import static com.mycompany.proyectou3aalg.util.EstadoDFS.Color.GRAY;
import static com.mycompany.proyectou3aalg.util.EstadoDFS.Color.WHITE;
import com.mycompany.proyectou3aalg.util.Grafo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;

/**
 *
 * @author Elite
 */
public class GrafoPanel extends JPanel implements VisualizadorGrafo {
    
    private Map<Ciudad, EstadoDFS> estadosDFS = null;
    private Grafo grafo;
    private int[][] posiciones;
    private Set<Arista> aristasKruskal = null;
    private List<Ciudad> caminoDijkstra = null;
    private ArrayList<Arista> aristasDijkstra = null;

    public GrafoPanel(Grafo grafo, int[][] posiciones) {
        this.grafo = grafo;
        this.posiciones = posiciones;
        setPreferredSize(new Dimension(1000, 800));
    }
    
    public void actualizarColores(Map<Ciudad, EstadoDFS> estados){
        this.estadosDFS = estados;
        repaint();
    }
    
    public void restaurarColores() {
        this.estadosDFS = null;
        repaint();
    }
    
    public void mostrarKruskal(Set<Arista> mst) {
        this.aristasKruskal = mst;
        repaint();
    }

    public void restaurarKruskal() {
        this.aristasKruskal = null;
        repaint();
    }
    
    public void mostrarDijkstra(List<Ciudad> camino, ArrayList<Arista> aristas) {
        this.caminoDijkstra = camino;
        this.aristasDijkstra = aristas;
        repaint();
    }

    public void restaurarDijkstra() {
        this.caminoDijkstra = null;
        this.aristasDijkstra = null;
        repaint();
    }
    

@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibujar aristas
        g2.setColor(Color.GRAY);
        for (Arista arista : grafo.getAristas()) {
            Ciudad origen = arista.getOrigen();
            Ciudad destino = arista.getDestino();
            
            int i = grafo.getCiudades().indexOf(origen);
            int j = grafo.getCiudades().indexOf(destino);

            int x1 = posiciones[i][0];
            int y1 = posiciones[i][1];
            int x2 = posiciones[j][0];
            int y2 = posiciones[j][1];
            
            //Aristas mst Kruskal
            if (aristasKruskal != null) {
         
                    if (aristasKruskal != null && aristasKruskal.contains(arista)) {
                        g2.setColor(Color.BLACK);
                        ((Graphics2D) g2).setStroke(new BasicStroke(3));
                    } else {
                        g2.setColor(Color.GRAY);
                        ((Graphics2D) g2).setStroke(new BasicStroke(1));
                    }

                    g2.drawLine(x1, y1, x2, y2);
                    
            }
            
          
            if (aristasDijkstra != null && aristasDijkstra.contains(arista)) {
                g.setColor(Color.RED);
                ((Graphics2D) g).setStroke(new BasicStroke(3));
            } else {
                g.setColor(Color.GRAY);
                ((Graphics2D) g).setStroke(new BasicStroke(1));
            }
            
            g2.drawLine(x1, y1, x2, y2);
            
            // Peso 
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;
            g2.setFont(new Font("SansSerif", Font.BOLD, 8));
            g2.setColor(Color.BLACK);
            g2.drawString(arista.getPeso() + " km", midX + 5, midY + 10);

            // setea de nuevo el color gris para lo que se dibujará después, en este caso, las aristas.
            g2.setColor(Color.GRAY);
        
        }
          
        //Dibujar Nodos
        
        for (int i = 0; i < grafo.getCiudades().size(); i++) {
            Ciudad c = grafo.getCiudades().get(i);
            int x = posiciones[i][0], y = posiciones[i][1];
            
            Color color = Color.BLUE;
            
            if (estadosDFS != null) {
                EstadoDFS estado = estadosDFS.get(c);
                if (estado != null) {
                    switch (estado.color) {
                        case WHITE:
                            color = Color.LIGHT_GRAY;
                            break;
                        case GRAY:
                            color = Color.ORANGE;
                            break;
                        case BLACK:
                            color = Color.BLACK;
                            break;
                    }
                }
            } 
            
            
        if (caminoDijkstra != null && caminoDijkstra.contains(c)) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.BLUE);
        }

        g2.setColor(color);
        g2.fillOval(x - 8, y - 8, 16, 16);
        g2.setColor(Color.BLUE);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g2.drawString(c.getNombre(), x - 10, y + 18);
        
        
        
     }    
        
//        for (int i = 0; i < grafo.getCiudades().size(); i++) {
//            Ciudad ciudad = grafo.getCiudades().get(i);
//            int x = posiciones[i][0], y = posiciones[i][1];
//
//            Color color = Color.BLUE;
//            if (estadosDFS != null) {
//                EstadoDFS estado = estadosDFS.get(ciudad);
//                if (estado != null) {
//                    switch (estado.color) {
//                        case WHITE:
//                            color = Color.LIGHT_GRAY;
//                            break;
//                        case GRAY:
//                            color = Color.ORANGE;
//                            break;
//                        case BLACK:
//                            color = Color.BLACK;
//                            break;
//                    }
//                }
//            }
//
//            g2.setColor(color);
//            g2.fillOval(x - 8, y - 8, 16, 16);
//            g2.setColor(Color.BLUE);
//            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
//            g2.drawString(ciudad.getNombre(), x - 10, y + 18);
//        }
//        
//        //Nodos Dijkstra
//        if(caminoDijkstra!=null){
//            for (int i = 0; i < grafo.getCiudades().size(); i++) {
//                Ciudad c = grafo.getCiudades().get(i);
//                int x = posiciones[i][0], y = posiciones[i][1];
//
//                if (caminoDijkstra != null && caminoDijkstra.contains(c)) {
//                    g.setColor(Color.BLACK);
//                } else {
//                    g.setColor(Color.BLUE);
//                }
//
//                g.fillOval(x - 10, y - 10, 20, 20);
//                g.setColor(Color.WHITE);
//                g.drawString(c.getNombre(), x - 15, y - 15);
//            }
//        }
        
    }    
    
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        
//        // Dibujar aristas
//        g2.setColor(Color.GRAY);
//        for (Arista arista : grafo.getAristas()) {
//            Ciudad origen = arista.getOrigen();
//            Ciudad destino = arista.getDestino();
//            
//            int i = grafo.getCiudades().indexOf(origen);
//            int j = grafo.getCiudades().indexOf(destino);
//
//            int x1 = posiciones[i][0];
//            int y1 = posiciones[i][1];
//            int x2 = posiciones[j][0];
//            int y2 = posiciones[j][1];
//            g2.drawLine(x1, y1, x2, y2);
//            
//            // Peso 
//            int midX = (x1 + x2) / 2;
//            int midY = (y1 + y2) / 2;
//            g2.setFont(new Font("SansSerif", Font.BOLD, 8));
//            g2.setColor(Color.BLACK);
//            g2.drawString(arista.getPeso() + " km", midX + 5, midY + 10);
//            
//            // setea de nuevo el color gris para lo que se dibujará después, en este caso, las aristas.
//            g2.setColor(Color.GRAY);
//        }
//
//        //Aristas mst Kruskal
//        if(aristasKruskal!=null){
//            for (Arista a : grafo.getAristas()) {
//                int i = grafo.getCiudades().indexOf(a.getOrigen());
//                int j = grafo.getCiudades().indexOf(a.getDestino());
//                int x1 = posiciones[i][0], y1 = posiciones[i][1];
//                int x2 = posiciones[j][0], y2 = posiciones[j][1];
//
//                if (aristasKruskal != null && aristasKruskal.contains(a)) {
//                    g2.setColor(Color.BLACK);
//                    ((Graphics2D) g2).setStroke(new BasicStroke(3));
//                } else {
//                    g2.setColor(Color.GRAY);
//                    ((Graphics2D) g2).setStroke(new BasicStroke(1));
//                }
//
//                g2.drawLine(x1, y1, x2, y2);
//            }
//        }
//        
//        //Aristas Dijkstra
//        if(aristasDijkstra!=null){
//            for (Arista a : grafo.getAristas()) {
//                Ciudad u = a.getOrigen();
//                Ciudad v = a.getDestino();
//                int i = grafo.getCiudades().indexOf(u);
//                int j = grafo.getCiudades().indexOf(v);
//                int x1 = posiciones[i][0], y1 = posiciones[i][1];
//                int x2 = posiciones[j][0], y2 = posiciones[j][1];
//
//                if (aristasDijkstra != null && aristasDijkstra.contains(a)) {
//                    g.setColor(Color.RED);
//                    ((Graphics2D) g).setStroke(new BasicStroke(3));
//                } else {
//                    g.setColor(Color.GRAY);
//                    ((Graphics2D) g).setStroke(new BasicStroke(1));
//                }
//
//                g.drawLine(x1, y1, x2, y2);
//            }
//        }
//       
//        
//        
//        
//        
//        //Dibujar Nodos
//        for (int i = 0; i < grafo.getCiudades().size(); i++) {
//            Ciudad ciudad = grafo.getCiudades().get(i);
//            int x = posiciones[i][0], y = posiciones[i][1];
//
//            Color color = Color.BLUE;
//            if (estadosDFS != null) {
//                EstadoDFS estado = estadosDFS.get(ciudad);
//                if (estado != null) {
//                    switch (estado.color) {
//                        case WHITE:
//                            color = Color.LIGHT_GRAY;
//                            break;
//                        case GRAY:
//                            color = Color.ORANGE;
//                            break;
//                        case BLACK:
//                            color = Color.BLACK;
//                            break;
//                    }
//                }
//            }
//
//            g2.setColor(color);
//            g2.fillOval(x - 8, y - 8, 16, 16);
//            g2.setColor(Color.BLUE);
//            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
//            g2.drawString(ciudad.getNombre(), x - 10, y + 18);
//        }
//        
//        //Nodos Dijkstra
//        if(caminoDijkstra!=null){
//            for (int i = 0; i < grafo.getCiudades().size(); i++) {
//                Ciudad c = grafo.getCiudades().get(i);
//                int x = posiciones[i][0], y = posiciones[i][1];
//
//                if (caminoDijkstra != null && caminoDijkstra.contains(c)) {
//                    g.setColor(Color.BLACK);
//                } else {
//                    g.setColor(Color.BLUE);
//                }
//
//                g.fillOval(x - 10, y - 10, 20, 20);
//                g.setColor(Color.WHITE);
//                g.drawString(c.getNombre(), x - 15, y - 15);
//            }
//        }
//        
//        
//        
//        
//        
//    }
    
    
}
