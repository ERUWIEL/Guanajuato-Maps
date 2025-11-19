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
import java.util.Queue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * Implementación del recorrido BFS con visualización gráfica
 *
 * @author Elite
 */
public class BFS {

    private Grafo grafo;
    private VisualizadorGrafo visualizador;
    private Map<Ciudad, EstadoDFS> estados = new HashMap<>();
    private List<Ciudad> ordenDescubrimiento = new ArrayList<>();
    private Map<Ciudad, List<Ciudad>> listaAdyacencia;

    public BFS(Grafo grafo, VisualizadorGrafo visualizador) {
        this.grafo = grafo;
        this.visualizador = visualizador;
        this.listaAdyacencia = construirListaAdyacencia();
    }

    /**
     * Construye la lista de adyacencia a partir de las aristas del grafo
     */
    private Map<Ciudad, List<Ciudad>> construirListaAdyacencia() {
        Map<Ciudad, List<Ciudad>> adj = new HashMap<>();

        // Inicializar listas para cada ciudad
        for (Ciudad ciudad : grafo.getCiudades()) {
            adj.put(ciudad, new ArrayList<>());
        }

        // Agregar conexiones bidireccionales
        for (Arista arista : grafo.getAristas()) {
            adj.get(arista.getOrigen()).add(arista.getDestino());
            adj.get(arista.getDestino()).add(arista.getOrigen());
        }

        return adj;
    }

    public void ejecutarDesde(Ciudad inicio) {
        // Inicializar todos los estados
        for (Ciudad c : grafo.getCiudades()) {
            estados.put(c, new EstadoDFS());
        }

        SwingWorker<Void, Map<Ciudad, EstadoDFS>> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                bfsVisit(inicio);
                return null;
            }

            @Override
            protected void done() {
                mostrarVentanaDescubrimiento();
            }
        };

        worker.execute();
    }

    private void bfsVisit(Ciudad inicio) {
        Queue<Ciudad> cola = new LinkedList<>();

        // Marcar el nodo inicial como descubierto (GRAY)
        EstadoDFS estadoInicio = estados.get(inicio);
        estadoInicio.color = EstadoDFS.Color.GRAY;
        estadoInicio.descubrimiento = 0;
        ordenDescubrimiento.add(inicio);

        cola.offer(inicio);
        visualizador.actualizarColores(estados);
        esperar(350);

        while (!cola.isEmpty()) {
            Ciudad actual = cola.poll();
            EstadoDFS estadoActual = estados.get(actual);

            // Procesar todos los vecinos adyacentes
            for (Ciudad vecino : listaAdyacencia.get(actual)) {
                EstadoDFS estadoVecino = estados.get(vecino);

                // Si el vecino no ha sido descubierto
                if (estadoVecino.color == EstadoDFS.Color.WHITE) {
                    estadoVecino.color = EstadoDFS.Color.GRAY;
                    estadoVecino.padre = actual;
                    estadoVecino.descubrimiento = ordenDescubrimiento.size();
                    ordenDescubrimiento.add(vecino);

                    cola.offer(vecino);
                    visualizador.actualizarColores(estados);
                    esperar(350);
                }
            }

            // Marcar el nodo actual como completamente procesado (BLACK)
            estadoActual.color = EstadoDFS.Color.BLACK;
            visualizador.actualizarColores(estados);
            esperar(500);
        }
    }

    private void esperar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void mostrarVentanaDescubrimiento() {
        JFrame ventana = new JFrame("Orden de descubrimiento BFS");
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        area.append("=== Recorrido BFS ===\n");
        area.append("Orden de visita:\n\n");

        for (int i = 0; i < ordenDescubrimiento.size(); i++) {
            Ciudad c = ordenDescubrimiento.get(i);
            area.append(String.format("%d. %s\n", i + 1, c.getNombre()));
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
