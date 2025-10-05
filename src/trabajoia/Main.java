/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabajoia;

import java.util.ArrayList;

/**
 *
 * @author Roger
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] matrix = new int[3][3];
        int[][] finalState = new int[3][3];
        matrix[0][0] = 0;
        matrix[0][1] = 1;
        matrix[0][2] = 1;
        matrix[1][0] = 1;
        matrix[1][1] = 1;
        matrix[1][2] = 1;
        matrix[2][0] = 1;
        matrix[2][1] = 1;
        matrix[2][2] = 1;        
        
        finalState[0][0] = 1;
        finalState[0][1] = 1;
        finalState[0][2] = 1;
        finalState[1][0] = 1;
        finalState[1][1] = 1;
        finalState[1][2] = 1;
        finalState[2][0] = 1;
        finalState[2][1] = 1;
        finalState[2][2] = 0;   
        
        Board b = new Board(matrix, finalState);
        ArrayList<String> rutasGanadoras = new ArrayList<>();
        b.Amplitud(rutasGanadoras);
        String bestRoute = Board.getBestRoute(rutasGanadoras);
        if (bestRoute == null) {
            System.out.println("No hay solución");
        } else {
            System.out.println("Mejor camino: " + bestRoute);
        }
        
        b = new Board(matrix, finalState);
        rutasGanadoras = new ArrayList<>();
        b.Profundidad(rutasGanadoras);
        bestRoute = Board.getBestRoute(rutasGanadoras);
        if (bestRoute == null) {
            System.out.println("No hay solución");
        } else {
            System.out.println("Mejor camino: " + bestRoute);
        }
        
        DoubleBoard b2 = new DoubleBoard(matrix, finalState);
        rutasGanadoras = new ArrayList<>();
        b2.Bidireccional(rutasGanadoras);
        bestRoute = Board.getBestRoute(rutasGanadoras);
        if (bestRoute == null) {
            System.out.println("No hay solución");
        } else {
            System.out.println("Mejor camino: " + bestRoute);
        }        
    }   
}
