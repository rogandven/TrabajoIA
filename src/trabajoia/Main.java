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
        int[][] matrix = new int[3][3];
        int[][] finalState = new int[3][3];
        ArrayList<int[][]> visitedStates = new ArrayList<>();
        matrix[0][0] = 0;
        matrix[0][1] = 3;
        matrix[0][2] = 3;
        matrix[1][0] = 3;
        matrix[1][1] = 3;
        matrix[1][2] = 3;
        matrix[2][0] = 3;
        matrix[2][1] = 3;
        matrix[2][2] = 3;        
        
        finalState[0][0] = 3;
        finalState[0][1] = 3;
        finalState[0][2] = 3;
        finalState[1][0] = 3;
        finalState[1][1] = 3;
        finalState[1][2] = 3;
        finalState[2][0] = 3;
        finalState[2][1] = 0;
        finalState[2][2] = 3;   
        
        Board b = new Board(matrix, finalState, visitedStates, "A");
        b.Amplitud(false);
    }
    
}
