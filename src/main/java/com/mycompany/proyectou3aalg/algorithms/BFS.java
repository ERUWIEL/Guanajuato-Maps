
package com.mycompany.proyectou3aalg.algorithms;


import com.mycompany.proyectou3aalg.util.Arista;
import com.mycompany.proyectou3aalg.util.Ciudad;
import com.mycompany.proyectou3aalg.util.Grafo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Implementación del algoritmo BFS (Breadth-First Search)
 * para recorrer el grafo de ciudades
 */
public class BFS {
    
    private Grafo grafo;
    private Map<Ciudad, List<Ciudad>> listaAdyacencia;
    
    public BFS(Grafo grafo) {
        this.grafo = grafo;
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
    
    /**
     * Ejecuta el recorrido BFS desde una ciudad inicial
     * @param inicio Ciudad desde donde comenzar el recorrido
     * @return Lista con el orden de visita de las ciudades
     */
    public List<Ciudad> recorrer(Ciudad inicio) {
        List<Ciudad> recorrido = new ArrayList<>();
        Map<Ciudad, Boolean> visitado = new HashMap<>();
        Queue<Ciudad> cola = new LinkedList<>();
        
        // Marcar todas las ciudades como no visitadas
        for (Ciudad ciudad : grafo.getCiudades()) {
            visitado.put(ciudad, false);
        }
        
        // Comenzar desde la ciudad inicial
        cola.offer(inicio);
        visitado.put(inicio, true);
        
        while (!cola.isEmpty()) {
            Ciudad actual = cola.poll();
            recorrido.add(actual);
            
            // Visitar todas las ciudades adyacentes
            for (Ciudad vecino : listaAdyacencia.get(actual)) {
                if (!visitado.get(vecino)) {
                    visitado.put(vecino, true);
                    cola.offer(vecino);
                }
            }
        }
        
        return recorrido;
    }
    
    /**
     * Ejecuta BFS y devuelve información detallada del recorrido
     * @param inicio Ciudad inicial
     * @return ResultadoBFS con información del recorrido
     */
    public ResultadoBFS recorridoDetallado(Ciudad inicio) {
        List<Ciudad> recorrido = new ArrayList<>();
        Map<Ciudad, Boolean> visitado = new HashMap<>();
        Map<Ciudad, Ciudad> padre = new HashMap<>();
        Map<Ciudad, Integer> nivel = new HashMap<>();
        Queue<Ciudad> cola = new LinkedList<>();
        
        // Inicializar estructuras
        for (Ciudad ciudad : grafo.getCiudades()) {
            visitado.put(ciudad, false);
            padre.put(ciudad, null);
            nivel.put(ciudad, -1);
        }
        
        // Comenzar BFS
        cola.offer(inicio);
        visitado.put(inicio, true);
        nivel.put(inicio, 0);
        
        while (!cola.isEmpty()) {
            Ciudad actual = cola.poll();
            recorrido.add(actual);
            
            for (Ciudad vecino : listaAdyacencia.get(actual)) {
                if (!visitado.get(vecino)) {
                    visitado.put(vecino, true);
                    padre.put(vecino, actual);
                    nivel.put(vecino, nivel.get(actual) + 1);
                    cola.offer(vecino);
                }
            }
        }
        
        return new ResultadoBFS(recorrido, padre, nivel);
    }
    
    /**
     * Clase interna para almacenar resultados detallados del BFS
     */
    public static class ResultadoBFS {
        private List<Ciudad> recorrido;
        private Map<Ciudad, Ciudad> padre;
        private Map<Ciudad, Integer> nivel;
        
        public ResultadoBFS(List<Ciudad> recorrido, Map<Ciudad, Ciudad> padre, 
                           Map<Ciudad, Integer> nivel) {
            this.recorrido = recorrido;
            this.padre = padre;
            this.nivel = nivel;
        }
        
        public List<Ciudad> getRecorrido() {
            return recorrido;
        }
        
        public Map<Ciudad, Ciudad> getPadre() {
            return padre;
        }
        
        public Map<Ciudad, Integer> getNivel() {
            return nivel;
        }
        
        /**
         * Obtiene el camino desde el inicio hasta una ciudad específica
         */
        public List<Ciudad> obtenerCamino(Ciudad destino) {
            List<Ciudad> camino = new ArrayList<>();
            Ciudad actual = destino;
            
            while (actual != null) {
                camino.add(0, actual);
                actual = padre.get(actual);
            }
            
            return camino;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== Recorrido BFS ===\n");
            sb.append("Orden de visita:\n");
            
            for (int i = 0; i < recorrido.size(); i++) {
                Ciudad ciudad = recorrido.get(i);
                sb.append(String.format("%d. %s (Nivel: %d)\n", 
                    i + 1, ciudad.getNombre(), nivel.get(ciudad)));
            }
            
            return sb.toString();
        }
    }
}