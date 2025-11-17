/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectou3aalg.view;

import com.mycompany.proyectou3aalg.util.EstadoDFS;
import com.mycompany.proyectou3aalg.util.Arista;
import com.mycompany.proyectou3aalg.util.Ciudad;
import com.mycompany.proyectou3aalg.util.Grafo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
    

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        System.out.println("Dibujando grafo...");
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
//            g2.setColor(Color.GRAY);
//        }
//
//        // Dibujar nodos
//        for (int i = 0; i < grafo.getCiudades().size(); i++) {
//            Ciudad ciudad = grafo.getCiudades().get(i);
//            int x = posiciones[i][0];
//            int y = posiciones[i][1];
//
//            g2.setColor(Color.BLUE);
//            //g.fillOval(x - 8, y - 8, 16, 16);
//            g2.fillOval(x - 5, y - 5, 10, 10);
//            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
//            g2.drawString(ciudad.getNombre(), x - 10, y +18);
//        }
//    }
    
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
            g2.drawLine(x1, y1, x2, y2);

            // Peso 
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;
            g2.setFont(new Font("SansSerif", Font.BOLD, 8));
            g2.setColor(Color.BLACK);
            g2.drawString(arista.getPeso() + " km", midX + 5, midY + 10);
            g2.setColor(Color.GRAY);
        }

        //Aristas mst Kruskal
        if(aristasKruskal!=null){
            for (Arista a : grafo.getAristas()) {
                int i = grafo.getCiudades().indexOf(a.getOrigen());
                int j = grafo.getCiudades().indexOf(a.getDestino());
                int x1 = posiciones[i][0], y1 = posiciones[i][1];
                int x2 = posiciones[j][0], y2 = posiciones[j][1];

                if (aristasKruskal != null && aristasKruskal.contains(a)) {
                    g2.setColor(Color.BLACK);
                    ((Graphics2D) g2).setStroke(new BasicStroke(3));
                } else {
                    g2.setColor(Color.GRAY);
                    ((Graphics2D) g2).setStroke(new BasicStroke(1));
                }

                g2.drawLine(x1, y1, x2, y2);
            }
        }
        
        //Dibujar Nodos
        for (int i = 0; i < grafo.getCiudades().size(); i++) {
            Ciudad ciudad = grafo.getCiudades().get(i);
            int x = posiciones[i][0], y = posiciones[i][1];

            Color color = Color.BLUE;
            if (estadosDFS != null) {
                EstadoDFS estado = estadosDFS.get(ciudad);
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

            g2.setColor(color);
            g2.fillOval(x - 8, y - 8, 16, 16);
            g2.setColor(Color.BLUE);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.drawString(ciudad.getNombre(), x - 10, y + 18);
        }
        
        
        
        
    }
    
    
}
