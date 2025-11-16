/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import implementaciones.Arista;
import implementaciones.Ciudad;
import implementaciones.Grafo;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Elite
 */
public class GrafoPanel extends JPanel {
    
    private Grafo grafo;
    private int[][] posiciones;

    public GrafoPanel(Grafo grafo, int[][] posiciones) {
        this.grafo = grafo;
        this.posiciones = posiciones;
        setPreferredSize(new Dimension(1000, 800));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        System.out.println("Dibujando grafo...");

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

        // Dibujar nodos
        for (int i = 0; i < grafo.getCiudades().size(); i++) {
            Ciudad ciudad = grafo.getCiudades().get(i);
            int x = posiciones[i][0];
            int y = posiciones[i][1];

            g2.setColor(Color.BLUE);
            //g.fillOval(x - 8, y - 8, 16, 16);
            g2.fillOval(x - 5, y - 5, 10, 10);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g2.drawString(ciudad.getNombre(), x - 10, y +18);
        }
    }
    
    
    
}
