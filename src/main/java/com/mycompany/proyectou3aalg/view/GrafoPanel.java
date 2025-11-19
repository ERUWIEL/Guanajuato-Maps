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

    // Kruskal
    private Set<Arista> aristasKruskal = null;

    // ANIMACIÓN DIJKSTRA NUEVA
    private Map<Ciudad, Integer> distanciasDijkstra = null;
    private Ciudad nodoActualDijkstra = null;
    private Map<Ciudad, Ciudad> padresDijkstra = null;
    private boolean mostrandoDijkstra = false;

    public GrafoPanel(Grafo grafo, int[][] posiciones) {
        this.grafo = grafo;
        this.posiciones = posiciones;
        setPreferredSize(new Dimension(1000, 800));
    }

    // ========================= BFS =============================
    public void actualizarColores(Map<Ciudad, EstadoDFS> estados) {
        this.estadosDFS = estados;
        repaint();
    }

    public void restaurarColores() {
        this.estadosDFS = null;
        repaint();
    }

    // ========================= KRUSKAL =========================
    public void mostrarKruskal(Set<Arista> mst) {
        this.aristasKruskal = mst;
        repaint();
    }

    public void restaurarKruskal() {
        this.aristasKruskal = null;
        repaint();
    }

    // ========================= DIJKSTRA (NUEVO) =========================
    public void mostrarDijkstra(Map<Ciudad, Integer> distancias, Ciudad actual, Map<Ciudad, Ciudad> padres) {
        this.distanciasDijkstra = distancias;
        this.nodoActualDijkstra = actual;
        this.padresDijkstra = padres;
        this.mostrandoDijkstra = true;
        repaint();
    }

    public void restaurarDijkstra() {
        distanciasDijkstra = null;
        nodoActualDijkstra = null;
        padresDijkstra = null;
        mostrandoDijkstra = false;
        repaint();
    }

    // ========================= DIBUJADO =========================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ======== DIBUJAR ARISTAS ========
        for (Arista arista : grafo.getAristas()) {

            Ciudad origen = arista.getOrigen();
            Ciudad destino = arista.getDestino();

            int i = grafo.getCiudades().indexOf(origen);
            int j = grafo.getCiudades().indexOf(destino);

            int x1 = posiciones[i][0];
            int y1 = posiciones[i][1];
            int x2 = posiciones[j][0];
            int y2 = posiciones[j][1];

            // --- Colorear MST de Kruskal ---
            if (aristasKruskal != null && aristasKruskal.contains(arista)) {
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(3));
            } // --- Colorear árbol parcial de Dijkstra ---
            else if (mostrandoDijkstra && padresDijkstra != null) {
                Ciudad p1 = padresDijkstra.get(destino);
                Ciudad p2 = padresDijkstra.get(origen);

                if (p1 == origen || p2 == destino) {
                    g2.setColor(Color.GREEN);
                    g2.setStroke(new BasicStroke(3));
                } else {
                    g2.setColor(Color.GRAY);
                    g2.setStroke(new BasicStroke(1));
                }
            } else {
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
            }

            g2.drawLine(x1, y1, x2, y2);

            // Texto del peso
            int midX = (x1 + x2) / 2;
            int midY = (y1 + y2) / 2;
            g2.setFont(new Font("SansSerif", Font.BOLD, 8));
            g2.setColor(Color.BLACK);
            g2.drawString(arista.getPeso() + " km", midX + 5, midY + 10);
        }

        // ======== DIBUJAR NODOS ========
        for (int i = 0; i < grafo.getCiudades().size(); i++) {

            Ciudad c = grafo.getCiudades().get(i);
            int x = posiciones[i][0];
            int y = posiciones[i][1];

            // colores base
            Color colorNodo = Color.BLUE;

            // --- Colores BFS/DFS ---
            if (estadosDFS != null) {
                EstadoDFS estado = estadosDFS.get(c);
                if (estado != null) {
                    switch (estado.color) {
                        case WHITE:
                            colorNodo = Color.LIGHT_GRAY;
                            break;
                        case GRAY:
                            colorNodo = Color.ORANGE;
                            break;
                        case BLACK:
                            colorNodo = Color.BLACK;
                            break;
                    }
                }
            }

            // --- Nodo actual de Dijkstra ---
            if (mostrandoDijkstra && c.equals(nodoActualDijkstra)) {
                colorNodo = Color.YELLOW;
            }

            g2.setColor(colorNodo);
            g2.fillOval(x - 10, y - 10, 20, 20);

            // Nombre de la ciudad
            g2.setColor(Color.BLUE);
            g2.drawString(c.getNombre(), x - 12, y + 20);

            // --- Mostrar distancias ---
            if (mostrandoDijkstra && distanciasDijkstra != null) {
                int d = distanciasDijkstra.getOrDefault(c, Integer.MAX_VALUE);
                String texto = (d == Integer.MAX_VALUE) ? "∞" : ("" + d);

                g2.setColor(Color.RED);
                g2.drawString(texto, x - 5, y - 12);
            }
        }
    }
}
