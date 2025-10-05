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
        MatrixTree matrixtree = null;
        Scanner s = new Scanner(System.in);
        int[][] initialState = null;
        int[][] finalState = null;
        int size;
        int option = -1;
        String bestRoute = null;
        boolean redoSetup = true;
        
        while (true) {
            if (redoSetup) {
                size = Input.getSizeFromUser(s);
                if (Input.getYesOrNoQuestionFromUser(s, "¿El estado inicial va a ser aleatorio?")) {
                    initialState = Input.getMatrixFromArray(Input.getNumberArrayFromRandomNumbers(size), size);
                } else {
                    initialState = Input.getMatrixFromUser(s, size, "estado_inicial");
                }
                Board.printMatrix(initialState);

                if (Input.getYesOrNoQuestionFromUser(s, "¿El estado final va a ser aleatorio?")) {
                    finalState = Input.getMatrixFromArray(Input.randomizeExistingArray(Input.getArrayFromMatrix(initialState)), size);
                } else {
                    finalState = Input.getMatrixFromUser(s, size, "estado_final");
                }
                Board.printMatrix(finalState);
            }

            validRoutes = new ArrayList<>();

            option = Input.getUserOption(s);
            
            try {
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
                
                redoSetup = !(Input.getYesOrNoQuestionFromUser(s, "¿Desea probar la misma matriz con otro metodo?"));
            } catch (CustomException e) {
                System.out.println("ERROR: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("ERROR: Error desconocido");
            }
        }
    }   
}
