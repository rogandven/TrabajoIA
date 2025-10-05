/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabajoia;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Roger
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> validRoutes;
        MatrixTree matrixtree;
        Scanner s = new Scanner(System.in);
        int[][] initialState;
        int[][] finalState;
        int size;
        int option;
        String bestRoute;
        
        while (true) {
            option = -1;
            bestRoute = null;
            validRoutes = new ArrayList<>();
            matrixtree = null;
            initialState = null;
            finalState = null;
            size = Input.getSizeFromUser(s);
            if (Input.getYesOrNoQuestionFromUser(s, "¿El estado inicial va a ser aleatorio?")) {
                initialState = Input.getMatrixFromArray(Input.getNumberArrayFromRandomNumbers(size), size);
            } else {
                initialState = Input.getMatrixFromUser(s, size, "estado_inicial");
            }

            if (Input.getYesOrNoQuestionFromUser(s, "¿El estado final va a ser aleatorio?")) {
                finalState = Input.getMatrixFromArray(Input.randomizeExistingArray(Input.getArrayFromMatrix(initialState)), size);
            } else {
                finalState = Input.getMatrixFromUser(s, size, "estado_final");
            }

            option = Input.getUserOption(s);
            
            if (option == Constants.AMPLITUD) {
                matrixtree = new Board(initialState, finalState);
                ((Board)matrixtree).Amplitud(validRoutes);
            } else if (option == Constants.PROFUNDIDAD) {
                matrixtree = new Board(initialState, finalState);
                ((Board)matrixtree).Profundidad(validRoutes);
            } else if (option == Constants.BIDIRECCIONAL) {
                matrixtree = new DoubleBoard(initialState, finalState);
                ((DoubleBoard)matrixtree).Bidireccional(validRoutes);
            }
            
            bestRoute = Board.getBestRoute(validRoutes);
            if (bestRoute == null) {
                System.out.println("No hay solución");
            } else {
                System.out.println("Mejor camino: " + bestRoute);
            }
            
            if (Input.getYesOrNoQuestionFromUser(s, "¿Desea salir?")) {
                return;
            }
        }
    }   
}
