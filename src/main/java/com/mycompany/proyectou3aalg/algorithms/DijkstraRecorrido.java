package com.mycompany.proyectou3aalg.algorithms;

import com.mycompany.proyectou3aalg.util.Arista;
import com.mycompany.proyectou3aalg.util.Ciudad;
import com.mycompany.proyectou3aalg.util.Grafo;
import com.mycompany.proyectou3aalg.view.GrafoPanel;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * Implementación animada del algoritmo de Dijkstra, siguiendo el mismo estilo
 * visual que BFS y Kruskal.
 *
 * @author Héctor Alonso 252039
 * @author Erubiel Flores 252389
 */
public class DijkstraRecorrido {

    private Grafo grafo;
    private GrafoPanel panel;
    private Ciudad origen;
    private Ciudad destino;

    private Map<Ciudad, Integer> distancias = new HashMap<>();
    private Map<Ciudad, Ciudad> padres = new HashMap<>();
    private Set<Ciudad> visitados = new HashSet<>();

    public DijkstraRecorrido(Grafo grafo, GrafoPanel panel, Ciudad origen, Ciudad destino) {
        this.grafo = grafo;
        this.panel = panel;
        this.origen = origen;
        this.destino = destino;
    }

    public void ejecutarDijkstra() {

        SwingWorker<Void, Map<Ciudad, Integer>> worker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() {

                inicializarDistancias();
                
                PriorityQueue<Ciudad> pq = new PriorityQueue<>(
                        Comparator.comparingInt(distancias::get)
                );
                
                pq.add(origen);

                while (!pq.isEmpty()) {

                    Ciudad actual = pq.poll();

                    if (visitados.contains(actual)) {
                        continue;
                    }

                    visitados.add(actual);

                    // Actualizar visualización
                    panel.mostrarDijkstra(distancias, actual, padres);
                    esperar(400);

                    // Si llegamos al destino, podemos detener la búsqueda
                    if (actual.equals(destino)) {
                        break;
                    }

                    for (Arista arista : grafo.getAristasDesde(actual)) {
                        Ciudad vecino = arista.getDestino();

                        int nuevoCosto = distancias.get(actual) + arista.getPeso();

                        if (nuevoCosto < distancias.get(vecino)) {
                            distancias.put(vecino, nuevoCosto);
                            padres.put(vecino, actual);
                            pq.add(vecino);

                            // Visualización del cambio de distancias
                            panel.mostrarDijkstra(distancias, vecino, padres);
                            esperar(300);
                        }
                    }
                }

                return null;
            }

            @Override
            protected void done() {
                mostrarVentanaResultado();
            }
        };

        worker.execute();
    }

    private void inicializarDistancias() {
        for (Ciudad c : grafo.getCiudades()) {
            distancias.put(c, Integer.MAX_VALUE);
            padres.put(c, null);
        }
        distancias.put(origen, 0);
    }

    private void esperar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    private List<Ciudad> reconstruirCamino() {
        List<Ciudad> camino = new ArrayList<>();

        Ciudad actual = destino;
        while (actual != null) {
            camino.add(actual);
            actual = padres.get(actual);
        }
        Collections.reverse(camino);
        return camino;
    }

    private void mostrarVentanaResultado() {

        JFrame ventana = new JFrame("Ruta más corta - Dijkstra");
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        area.append("=== DIJKSTRA - Ruta más corta ===\n\n");
        area.append("Origen : " + origen.getNombre() + "\n");
        area.append("Destino: " + destino.getNombre() + "\n\n");

        int distanciaFinal = distancias.get(destino);
        List<Ciudad> camino = reconstruirCamino();

        if (distanciaFinal == Integer.MAX_VALUE) {
            area.append("No existe camino entre ambas ciudades.\n");
        } else {
            area.append("Distancia total: " + distanciaFinal + " km\n\n");
            area.append("Camino encontrado:\n\n");

            for (int i = 0; i < camino.size(); i++) {
                area.append((i + 1) + ". " + camino.get(i).getNombre() + "\n");
            }
        }

        ventana.add(new JScrollPane(area));
        ventana.setSize(450, 500);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                panel.restaurarDijkstra(); // Similar a restaurarColores o restaurarKruskal
            }
        });
    }
}
