/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
/**
 *
 * @author Roger
 */

public final class Board {
    private int[][] matrix;
    private int[] emptySpaceCoordinates;
    private int[][] finalState;
    private ArrayList<int[][]> visitedStateListPointer; 
    // private int[][] previous;
    private String id;
    
    public void Amplitud(ArrayList<String> winningRoutesPointer) {
        if (winningRoutesPointer == null) {
            throw new CustomException("El puntero a las rutas ganadoras no puede ser nulo");
        }        
        Amplitud(false, winningRoutesPointer);
    }
    
    private void Amplitud(boolean hijo, ArrayList<String> winningRoutesPointer) {
        if (isStateVisited(matrix)) {
            return;
        }
        if (!hijo) {
            printMatrixHeader(id);
            this.printMatrix();
        }
        ArrayList<MatrixSwapPlan> swapList = this.getSwapList(matrix);
        ArrayList<Board> children = new ArrayList<>();

        for (MatrixSwapPlan p : swapList) {
            try {
                Board b = new Board(copyMatrixWithSwappedValues(matrix, p), finalState, visitedStateListPointer, id + p.id);
                printMatrixHeader(b.id);
                printMatrix(b.matrix);
                if (isFinalState(b.matrix)) {
                    winningRoutesPointer.add(id);
                    printWinningStateAnnouncement();
                    continue;
                }        
                children.add(b);
            } catch (StackOverflowError e) {
                printErrorMessage(e);
            }
        }
        for (Board child : children) {
            try {
                child.Amplitud(true, winningRoutesPointer);
            } catch (StackOverflowError e) {
                printErrorMessage(e);
            }
        }            
    }
    
    public void Profundidad(ArrayList<String> winningRoutesPointer) {
        if (winningRoutesPointer == null) {
            throw new CustomException("El puntero a las rutas ganadoras no puede ser nulo");
        }
        Profundidad(0, new IntegerPointer(-1), winningRoutesPointer);
    }
    
    private void Profundidad(int amount, IntegerPointer limit, ArrayList<String> winningRoutesPointer) {
        printMatrixHeader(id);
        this.printMatrix();
        
        if ((limit.getN() != -1) && amount > limit.getN()) {
            printLimitAnnouncement();
            return;
        }
        
        if (isFinalState(matrix)) {
            if (limit.getN() == -1) {
                limit.setN(amount);
            }
            if (amount < limit.getN()) {
                limit.setN(amount);
            }
            winningRoutesPointer.add(id);
            printWinningStateAnnouncement();
            return;
        }
        if (isStateVisited(matrix)) {
            printVisitedStateAnnouncement();
            return;
        }
 
        ArrayList<MatrixSwapPlan> swapList = this.getSwapList(matrix);

        for (MatrixSwapPlan p : swapList) {
            try {
                Board b = new Board(copyMatrixWithSwappedValues(this.matrix, p), this.finalState, this.visitedStateListPointer, id + p.id);
                b.Profundidad(amount + 1, limit, winningRoutesPointer);
                printRoadChangeAnnouncement();
            } catch (StackOverflowError e) {
                printErrorMessage(e);
            }
        }
    }
    

    private class MatrixSwapPlan {
        private final int p1X;
        private final int p1Y;
        private final int p2X;
        private final int p2Y;
        private final String id;

        protected MatrixSwapPlan(int p1X, int p1Y, int p2X, int p2Y, String id) {
            this.p1X = p1X;
            this.p1Y = p1Y;
            this.p2X = p2X;
            this.p2Y = p2Y;
            this.id = id;
        }
    }
    
    private class pairedMatrixSwapPlan {
        private final MatrixSwapPlan m1;
        private final MatrixSwapPlan m2;

        protected pairedMatrixSwapPlan(MatrixSwapPlan m1, MatrixSwapPlan m2) {
            this.m1 = m1;
            this.m2 = m2;
        }
    }
    
    private ArrayList<MatrixSwapPlan> getSwapList(int[][] matrix) {
        ArrayList<MatrixSwapPlan> swapList = new ArrayList<>();
        if (emptySpaceCoordinates[0] < (matrix.length - 1)) {
            swapList.add(new MatrixSwapPlan(emptySpaceCoordinates[0], emptySpaceCoordinates[1], emptySpaceCoordinates[0] + 1, emptySpaceCoordinates[1], "A"));
        }
        if (emptySpaceCoordinates[1] < (matrix[0].length - 1)) {
            swapList.add(new MatrixSwapPlan(emptySpaceCoordinates[0], emptySpaceCoordinates[1], emptySpaceCoordinates[0], emptySpaceCoordinates[1] + 1, "B"));
        }
        if (emptySpaceCoordinates[0] > 0) {
            swapList.add(new MatrixSwapPlan(emptySpaceCoordinates[0], emptySpaceCoordinates[1], emptySpaceCoordinates[0] - 1, emptySpaceCoordinates[1], "C"));
        }
        if (emptySpaceCoordinates[1] > 0) {
            swapList.add(new MatrixSwapPlan(emptySpaceCoordinates[0], emptySpaceCoordinates[1], emptySpaceCoordinates[0], emptySpaceCoordinates[1] - 1, "D"));
        }        
        return swapList;
    }

    
    
    private ArrayList<pairedMatrixSwapPlan> getPairedSwapList(int[][] m1, int[][] m2) {
        ArrayList<pairedMatrixSwapPlan> swapList = new ArrayList<>();
        ArrayList<MatrixSwapPlan> swapListA = getSwapList(m1);
        ArrayList<MatrixSwapPlan> swapListB = getSwapList(m2);
        
        for (MatrixSwapPlan l1 : swapListA) {
            for (MatrixSwapPlan l2 : swapListB) {
                swapList.add(new pairedMatrixSwapPlan(l1, l2));
            }
        }
        
        return swapList;
    }
    
    private Board(int[][] matrix, int[][] finalState, ArrayList<int[][]> visitedStateListPointer, String id) {
        this.matrix = validateMatrix(matrix);
        this.finalState = validateMatrix(finalState);
        validateBothMatrices(matrix, finalState);
        this.visitedStateListPointer = visitedStateListPointer;
        this.emptySpaceCoordinates = new int[2];
        // this.previous = null;
        this.id = id;
        this.findEmptySpaceCoordinates();
    }
    
    public Board(int[][] matrix, int[][] finalState) {
        this(matrix, finalState, new ArrayList<>(), "A");
    }
            
    public static void printMatrix(int[][] m) {
        System.out.println("-----");
        for (int[] matrix1 : m) {
            for (int matrix2 : matrix1) {
                if (matrix2 == 0) {
                    System.out.print("[ ]");
                } else {
                    System.out.print("[" + matrix2 + "]");
                }
            }
            System.out.print('\n');
        }
    }

    private void printMatrix() {
        this.printMatrix(this.matrix);
    }    

    private void findEmptySpaceCoordinates() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    emptySpaceCoordinates = new int[2];
                    emptySpaceCoordinates[0] = i;
                    emptySpaceCoordinates[1] = j;
                    return;
                }
            }
        }
    }
    
    private boolean isFinalState(int[][] matrix) {
        return compareMatrices(matrix, finalState);
    }
    
    private int[][] copyMatrixWithSwappedValues(int[][] matrix, MatrixSwapPlan msp) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[0].length);
        }
        swapValuesInMatrix(newMatrix, msp);
        return newMatrix;
    }
    
    private void swapValuesInMatrix(int[][] matrix, MatrixSwapPlan msp) {
        int temp = matrix[msp.p1X][msp.p1Y];
        matrix[msp.p1X][msp.p1Y] = matrix[msp.p2X][msp.p2Y];
        matrix[msp.p2X][msp.p2Y] = temp;
    }
    
    private boolean compareMatrices(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            return false;
        }
        
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isStateVisited(int[][] matrix) {
        for (int[][] m : visitedStateListPointer) {
            if (compareMatrices(m, matrix)) {
                return true;
            }
        }
        visitedStateListPointer.add(matrix);
        return false;
    }
    
    private static void printMatrixHeader(String header) {
        System.out.println("Matriz: " + header);
    }
    
    private static void printWinningStateAnnouncement() {
        System.out.println("Estado ganador");
    }

    private static void printVisitedStateAnnouncement() {
        System.out.println("Estado ya visitado");
    }        
    
    private static void printRoadChangeAnnouncement() {
        System.out.println("Cambio de camino");
    }

    private static void printErrorMessage(Throwable t) {
        if (Constants.DEBUG_PRINTING_ALLOWED) {
            System.out.println(t.getClass().getSimpleName() + ": " + t.getMessage());
        }
    }
    
    private static int[][] validateMatrix(int[][] matrix) {
        if (matrix.length <= 1) {
            throw new CustomException("Matriz invalida");
        }
        int length1 = matrix.length;
        for (int[] matrix1 : matrix) {
            if (matrix1.length != length1) {
                throw new CustomException("La matriz debe ser cuadrada");
            }
        }
        
        int emptySpaces = 0;
        for (int[] matrix1 : matrix) {
            for (int j = 0; j < matrix1.length; j++) {
                if (matrix1[j] == 0) {
                    emptySpaces++;
                }
                if (emptySpaces > 1) {
                    throw new CustomException("Todas las matrices deben tener un solo espacio vacío");
                }
            }
        }
        return matrix;
    }
    
    private static class CustomException extends RuntimeException {
        public CustomException() {
        }

        public CustomException(String s) {
            super(s);
        }

        public CustomException(String message, Throwable cause) {
            super(message, cause);
        }

        public CustomException(Throwable cause) {
            super(cause);
        }
    }
    
    private static void validateBothMatrices(int[][] matrix1, int[][] matrix2) {
        ArrayList<Integer> array1 = new ArrayList<>();
        ArrayList<Integer> array2 = new ArrayList<>();
        
        for(int[] arr : matrix1) {
            for(int i : arr) {
                array1.add(i);
            }
        }
        for(int[] arr : matrix2) {
            for(int i : arr) {
                array2.add(i);
            }
        }
        Collections.sort(array1);
        Collections.sort(array2);
        
        if (array1.size() != array2.size()) {
            throw new CustomException("Las matrices son de distinto tamanio");
        }
        
        for (int i = 0; i < array1.size(); i++) {
            if (!Objects.equals(array1.get(i), array2.get(i))) {
                throw new CustomException("Las matrices no tienen los mismos valores");
            }
        }
    }
    
    private static final class IntegerPointer {
        private int n;
        
        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }

        public IntegerPointer(int n) {
            this.setN(n);
        }
    }
    
    private static void printLimitAnnouncement() {
        System.out.println("Limite sobrepasado");
    }
    
    public static String getBestRoute(ArrayList<String> routes){
        if (routes.size() <= 0) {
            return null;
        }
        String bestRoute = routes.get(0);
        String current;
        for (int i = 1; i < routes.size(); i++) {
            current = routes.get(i);
            if (current != null && current.length() < bestRoute.length()) {
                bestRoute = current;
            }
        }
        return bestRoute;
    }
}

