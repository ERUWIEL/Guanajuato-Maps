/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectou3aalg;

import GUI.GrafoPanel;
import GUI.MainUI;
import implementaciones.Ciudad;
import implementaciones.Grafo;
import java.awt.BorderLayout;
import javax.swing.UIManager;

/**
 *
 * @author Elite
 */
public class ProyectoU3AAlg {

      public static void main(String args []){
        //Estilo windows 98
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MainUI menu = new MainUI();
        
            // Arreglo con los nombres de las ciudades
            String[] nombres = {
            "León", "Irapuato", "Celaya", "Salamanca", "Silao", "San Francisco del Rincón",
            "Valle de Santiago", "San Miguel de Allende", "Guanajuato", "Cortazar",
            "Dolores Hidalgo", "Acámbaro", "Purísima del Rincón", "Uriangato", "San Luis de la Paz",
            "Juventino Rosas", "Pénjamo", "Moroleón", "Salvatierra", "Marfil",
            "San Felipe", "Apaseo el Grande", "Abasolo", "Apaseo el Alto", "Villagrán",
            "San José Iturbide", "Yuriria", "Romita", "Comonfort", "Jaral del Progreso"
            };
            
            //Matriz de adyacencias con las distancias entre ciudades 
            int[][] matrizAristas = new int[30][30];

                // León - Silao
                matrizAristas[0][4] = 20;
                matrizAristas[4][0] = 20;

                // Silao - Guanajuato
                matrizAristas[4][8] = 25;
                matrizAristas[8][4] = 25;

                // León - San Francisco del Rincón
                matrizAristas[0][5] = 15;
                matrizAristas[5][0] = 15;

                // Irapuato - Salamanca
                matrizAristas[1][3] = 18;
                matrizAristas[3][1] = 18;

                // Salamanca - Celaya
                matrizAristas[3][2] = 22;
                matrizAristas[2][3] = 22;

                // Celaya - Apaseo el Grande
                matrizAristas[2][21] = 12;
                matrizAristas[21][2] = 12;

                // Celaya - Cortazar
                matrizAristas[2][9] = 10;
                matrizAristas[9][2] = 10;

                // Cortazar - Villagrán
                matrizAristas[9][24] = 8;
                matrizAristas[24][9] = 8;

                // Villagrán - Salamanca
                matrizAristas[24][3] = 10;
                matrizAristas[3][24] = 10;

                // Irapuato - Abasolo
                matrizAristas[1][22] = 30;
                matrizAristas[22][1] = 30;

                // Abasolo - Pénjamo
                matrizAristas[22][16] = 35;
                matrizAristas[16][22] = 35;

                // Pénjamo - Valle de Santiago
                matrizAristas[16][6] = 40;
                matrizAristas[6][16] = 40;

                // Valle de Santiago - Salvatierra
                matrizAristas[6][18] = 25;
                matrizAristas[18][6] = 25;

                // Salvatierra - Uriangato
                matrizAristas[18][13] = 20;
                matrizAristas[13][18] = 20;

                // Uriangato - Moroleón
                matrizAristas[13][17] = 10;
                matrizAristas[17][13] = 10;

                // Moroleón - Yuriria
                matrizAristas[17][27] = 15;
                matrizAristas[27][17] = 15;

                // Yuriria - Acámbaro
                matrizAristas[27][11] = 45;
                matrizAristas[11][27] = 45;

                // Acámbaro - San Luis de la Paz
                matrizAristas[11][14] = 50;
                matrizAristas[14][11] = 50;

                // San Luis de la Paz - San José Iturbide
                matrizAristas[14][25] = 30;
                matrizAristas[25][14] = 30;

                // San José Iturbide - San Miguel de Allende
                matrizAristas[25][7] = 40;
                matrizAristas[7][25] = 40;

                // San Miguel de Allende - Dolores Hidalgo
                matrizAristas[7][10] = 35;
                matrizAristas[10][7] = 35;

                // Dolores Hidalgo - Guanajuato
                matrizAristas[10][8] = 30;
                matrizAristas[8][10] = 30;

                // Guanajuato - Marfil
                matrizAristas[8][19] = 5;
                matrizAristas[19][8] = 5;

                // Guanajuato - San Felipe
                matrizAristas[8][20] = 60;
                matrizAristas[20][8] = 60;

                // Celaya - Apaseo el Alto
                matrizAristas[2][23] = 10;
                matrizAristas[23][2] = 10;

                // Celaya - Comonfort
                matrizAristas[2][28] = 15;
                matrizAristas[28][2] = 15;

                // Comonfort - Jaral del Progreso
                matrizAristas[28][29] = 12;
                matrizAristas[29][28] = 12;

                // Salamanca - Jaral del Progreso
                matrizAristas[3][29] = 20;
                matrizAristas[29][3] = 20;
            
            
            //Matriz coordenadas
            double[][] coordenadas = {
                {21.1291, -101.6737}, // León de los Aldama
                {20.6768, -101.3563}, // Irapuato
                {20.5235, -100.8157}, // Celaya
                {20.5719, -101.1915}, // Salamanca
                {20.9436, -101.4270}, // Silao de la Victoria
                {21.0190, -101.7310}, // San Francisco del Rincón
                {20.3980, -101.1920}, // Valle de Santiago
                {20.9144, -100.7439}, // San Miguel de Allende
                {21.0186, -101.2591}, // Guanajuato
                {20.4080, -100.8370}, // Cortazar
                {21.1571, -100.9360}, // Dolores Hidalgo
                {20.0380, -100.7220}, // Acámbaro
                {21.0500, -101.8800}, // Purísima del Rincón
                {20.1400, -101.1800}, // Uriangato
                {21.3000, -100.5200}, // San Luis de la Paz
                {20.6430, -100.9900}, // Santa Cruz de Juventino Rosas
                {20.4300, -101.7300}, // Pénjamo
                {20.1200, -101.2600}, // Moroleón
                {20.2150, -100.8700}, // Salvatierra
                {21.0000, -101.3000}, // Guanajuato (Marfil)
                {21.4700, -101.2200}, // San Felipe
                {20.5835, -100.4947}, // Apaseo el Grande
                {20.4600, -101.0500}, // Abasolo
                {20.4626, -100.5458}, // Apaseo el Alto
                {20.5200, -100.8200}, // Villagrán
                {21.0000, -100.5200}, // San José Iturbide
                {20.2300, -101.2600}, // Yuriria
                {20.7800, -101.5000}, // Romita
                {20.7213, -100.7620}, // Comonfort
                {20.4000, -101.0000} // Jaral del Progreso
            };
            
            
            Grafo grafo = menu.inicializarCiudadesYGrafo(nombres, matrizAristas);
            int [][] posiciones = menu.generarPosiciones(coordenadas);
            GrafoPanel panel = new GrafoPanel(grafo, posiciones);
            menu.add(panel, BorderLayout.CENTER);
            menu.pack();
            
            

            //Mostrar UI
            menu.setVisible(true);
        
        } catch (Exception e) {
            e.printStackTrace();
        }    


    }    
        
}
