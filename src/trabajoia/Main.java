/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabajoia;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Roger
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] matrix = new int[2][2];
        int[][] finalState = new int[2][2];
        ArrayList<int[][]> visitedStates = new ArrayList<>();
        matrix[0][0] = 1;
        matrix[0][1] = 2;
        matrix[1][0] = 4;
        matrix[1][1] = 0;
        
        finalState[0][0] = 4;
        finalState[0][1] = 2;
        finalState[1][0] = 1;
        finalState[1][1] = 0;
        
        Board b = new Board(matrix, finalState, visitedStates);
        b.profundidadIterativa();
    }
    
}
