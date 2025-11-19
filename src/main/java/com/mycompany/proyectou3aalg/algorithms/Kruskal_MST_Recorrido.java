package com.mycompany.proyectou3aalg.algorithms;

import com.mycompany.proyectou3aalg.util.Arista;
import com.mycompany.proyectou3aalg.util.BusquedaUnion;
import com.mycompany.proyectou3aalg.util.Ciudad;
import com.mycompany.proyectou3aalg.util.Grafo;
import com.mycompany.proyectou3aalg.view.GrafoPanel;

import java.awt.Font;
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
 * Implementación de Kruskal con visualización gráfica paso a paso
 * siguiendo el mismo estilo del BFS mostrado.
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

        // Copiar y ordenar aristas por peso
        List<Arista> aristas = new ArrayList<>(grafo.getAristas());
        aristas.sort(Comparator.comparingInt(Arista::getPeso));

        // Estructura de UNION-FIND
        BusquedaUnion uf = new BusquedaUnion(grafo.getCiudades());

        SwingWorker<Void, Arista> worker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() {

                // Recorrido de Kruskal
                for (Arista a : aristas) {
                    Ciudad u = a.getOrigen();
                    Ciudad v = a.getDestino();

                    // Si no forman ciclo, añádela al MST
                    if (!uf.conexión(u, v)) {
                        uf.unir(u, v);
                        mst.add(a);
                        pesoTotal += a.getPeso();

                        // Actualizar la visualización (como BFS)
                        panel.mostrarKruskal(mst);

                        esperar(400);
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

    private void esperar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void mostrarVentanaResultado() {

        JFrame ventana = new JFrame("Árbol de Expansión Mínima - Kruskal");
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        area.append("=== KRUSKAL - Árbol de Expansión Mínima ===\n\n");
        area.append("Aristas seleccionadas:\n\n");

        for (Arista a : mst) {
            area.append(String.format(
                "%s — %s   (%d km)\n",
                a.getOrigen().getNombre(),
                a.getDestino().getNombre(),
                a.getPeso()
            ));
        }

        area.append("\nPeso total del MST: " + pesoTotal + " km\n");

        ventana.add(new JScrollPane(area));
        ventana.setSize(450, 500);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                panel.restaurarKruskal();  // Igual que BFS→restaurarColores()
            }
        });
    }
}
