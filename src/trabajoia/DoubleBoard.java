/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
/**
 *
 * @author Roger
 */

public final class DoubleBoard {
    private int[][] matrix;
    private int[][] matrix2;
    private int[] emptySpaceCoordinates;
    private int[] emptySpaceCoordinates2;
    private int[][] finalState;
    private int[][] beginningState;
    private ArrayList<MatrixPair> visitedStateListPointer; 
    // private int[][] previous;
    private String id;
    private String id2;

    public void Bidireccional(ArrayList<String> winningRoutesPointer) {
        if (winningRoutesPointer == null) {
            throw new CustomException("El puntero a las rutas ganadoras no puede ser nulo");
        }
        Bidireccional(0, winningRoutesPointer);
    }
    
    private void Bidireccional(int amount, ArrayList<String> winningRoutesPointer) {
        if (isStateVisited(new MatrixPair(matrix, matrix2))) {
            printVisitedStateAnnouncement();
            return;
        }

        printMatrixHeader(id, id2);
        this.printMatrixPair();

        if (isFinalState(matrix)) {
            // System.out.println("M1 = FinalState");
            // throw new IllegalArgumentException("no esta bien esta comparacion xd");
            winningRoutesPointer.add(id + Constants.FINAL_STATE);
            DoubleBoard.printWinningStateAnnouncement();
            return;
        }
        if (isBeginningState(matrix2)) {
            // System.out.println("M2 = FinalState");
            // throw new IllegalArgumentException("no esta bien esta comparacion xd");
            winningRoutesPointer.add(reverseString(id2) + Constants.START_STATE);
            DoubleBoard.printWinningStateAnnouncement();
            return;
        }
        if (compareMatrices(this.matrix, this.matrix2)) {
            winningRoutesPointer.add(id + reverseString(id2));
            DoubleBoard.printWinningStateAnnouncement();
        }
        /* if (isStateVisited(new MatrixPair(matrix, matrix2))) {
            printVisitedStateAnnouncement();
            return;
        } */
 
        ArrayList<pairedMatrixSwapPlan> swapList = this.getPairedSwapList(matrix, matrix2);
        
        
        for (pairedMatrixSwapPlan p : swapList) {
            // System.out.println("PMSP: " + p.toString());
            try {
                DoubleBoard b = new DoubleBoard(copyMatrixWithSwappedValues(this.matrix, p.m1), copyMatrixWithSwappedValues(this.matrix2, p.m2), this.beginningState, this.finalState, this.visitedStateListPointer, (id + p.m1.id), (id2 + indexTranslator(p.m2.id)));
                b.Bidireccional(amount, winningRoutesPointer);
                // printRoadChangeAnnouncement();
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

        @Override
        public String toString() {
            return "MatrixSwapPlan{" + "p1X=" + p1X + ", p1Y=" + p1Y + ", p2X=" + p2X + ", p2Y=" + p2Y + ", id=" + id + '}';
        }
    }
    
    private class pairedMatrixSwapPlan {
        private final MatrixSwapPlan m1;
        private final MatrixSwapPlan m2;

        protected pairedMatrixSwapPlan(MatrixSwapPlan m1, MatrixSwapPlan m2) {
            this.m1 = m1;
            this.m2 = m2;
        }

        @Override
        public String toString() {
            return "pairedMatrixSwapPlan{" + "m1=" + m1 + ", m2=" + m2 + '}';
        }
    }
    
    // C -> Arriba
    // A -> Abajo
    // D -> Izquierda
    // B -> Derecha
    
    
    private ArrayList<MatrixSwapPlan> getSwapList(int[][] matrix, int[] emptySpaceCoordinates) {
        ArrayList<MatrixSwapPlan> swapList = new ArrayList<>();
        if (emptySpaceCoordinates[0] < (matrix.length - 1)) {
            swapList.add(new MatrixSwapPlan(emptySpaceCoordinates[0], emptySpaceCoordinates[1], emptySpaceCoordinates[0] + 1, emptySpaceCoordinates[1], Constants.DOWN_DIRECTION));
        }
        if (emptySpaceCoordinates[1] < (matrix[0].length - 1)) {
            swapList.add(new MatrixSwapPlan(emptySpaceCoordinates[0], emptySpaceCoordinates[1], emptySpaceCoordinates[0], emptySpaceCoordinates[1] + 1, Constants.RIGHT_DIRECTION));
        }
        if (emptySpaceCoordinates[0] > 0) {
            swapList.add(new MatrixSwapPlan(emptySpaceCoordinates[0], emptySpaceCoordinates[1], emptySpaceCoordinates[0] - 1, emptySpaceCoordinates[1], Constants.UP_DIRECTION));
        }
        if (emptySpaceCoordinates[1] > 0) {
            swapList.add(new MatrixSwapPlan(emptySpaceCoordinates[0], emptySpaceCoordinates[1], emptySpaceCoordinates[0], emptySpaceCoordinates[1] - 1, Constants.LEFT_DIRECTION));
        }        
        return swapList;
    }
    
    private ArrayList<pairedMatrixSwapPlan> getPairedSwapList(int[][] m1, int[][] m2) {
        ArrayList<pairedMatrixSwapPlan> swapList = new ArrayList<>();
        ArrayList<MatrixSwapPlan> swapListA = getSwapList(m1, emptySpaceCoordinates);
        ArrayList<MatrixSwapPlan> swapListB = getSwapList(m2, emptySpaceCoordinates2);
        
        for (MatrixSwapPlan l1 : swapListA) {
            for (MatrixSwapPlan l2 : swapListB) {
                swapList.add(new pairedMatrixSwapPlan(l1, l2));
            }
        }
        
        return swapList;
    }
    
    private DoubleBoard(int[][] matrix, int[][] matrix2, int[][] finalState, int[][] beginningState, ArrayList<MatrixPair> visitedStateListPointer, String id, String id2) {
        this.matrix = validateMatrix(matrix);
        this.finalState = validateMatrix(finalState);
        this.matrix2 = validateMatrix(matrix2);
        this.beginningState = validateMatrix(beginningState);
        validateBothMatrices(matrix, finalState);
        validateBothMatrices(matrix2, beginningState);
        this.visitedStateListPointer = visitedStateListPointer;
        this.emptySpaceCoordinates = new int[2];
        this.emptySpaceCoordinates2 = new int[2];
        // this.previous = null;
        this.id = id;
        this.id2 = id2;
        
        this.findEmptySpaceCoordinates();
        this.findEmptySpaceCoordinates2();
    }    
    
    public DoubleBoard(int[][] matrix, int[][] finalState) {
        this(matrix, finalState, copyMatrix(finalState), copyMatrix(matrix), new ArrayList<>(), Constants.START_STATE, Constants.FINAL_STATE);
    }
            
    public static void printMatrixPair(int[][] m, int[][] m2) {
        if (Constants.MATRIX_PRINTING_ALLOWED) {
            System.out.println("-----");
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[i].length; j++) {
                    if (m[i][j] == 0) {
                        System.out.print("[ ]");
                    } else {
                        System.out.print("[" + m[i][j] + "]");
                    }
                }
                System.out.print("  ");
                for (int j = 0; j < m2[i].length; j++) {
                    if (m2[i][j] == 0) {
                        System.out.print("[ ]");
                    } else {
                        System.out.print("[" + m2[i][j] + "]");
                    }
                }
                System.out.print('\n');
            }
        }
    }

    private void printMatrixPair() {
        printMatrixPair(this.matrix, this.matrix2);
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
    
    private void findEmptySpaceCoordinates2() {
        for (int i = 0; i < matrix2.length; i++) {
            for (int j = 0; j < matrix2[i].length; j++) {
                if (matrix2[i][j] == 0) {
                    emptySpaceCoordinates2 = new int[2];
                    emptySpaceCoordinates2[0] = i;
                    emptySpaceCoordinates2[1] = j;
                    return;
                }
            }
        }
    }    
    
    private boolean isFinalState(int[][] matrix) {
        return compareMatrices(matrix, finalState);
    }
    
    private boolean isBeginningState(int[][] matrix) {
        return compareMatrices(matrix, beginningState);
    }
    
    private static int[][] copyMatrixWithSwappedValues(int[][] matrix, MatrixSwapPlan msp) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[0].length);
        }
        swapValuesInMatrix(newMatrix, msp);
        return newMatrix;
    }
    
    private static int[][] copyMatrix(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[0].length);
        }
        return newMatrix;
    }
    
    private static void swapValuesInMatrix(int[][] matrix, MatrixSwapPlan msp) {
        int temp = matrix[msp.p1X][msp.p1Y];
        matrix[msp.p1X][msp.p1Y] = matrix[msp.p2X][msp.p2Y];
        matrix[msp.p2X][msp.p2Y] = temp;
    }
    
    private static boolean compareMatrices(int[][] a, int[][] b) {
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
    
    private boolean isStateVisited(MatrixPair pair) {
        for (MatrixPair p : visitedStateListPointer) {
            if (pair.equals(p)) {
                return true;
            }
        }
        visitedStateListPointer.add(pair);
        return false;
    }
    
    private static void printMatrixHeader(String header1, String header2) {
        if (Constants.MATRIX_PRINTING_ALLOWED) {
            System.out.println("Matrices: " + header1 + " | " + header2);
        }
    }
    
    private static void printWinningStateAnnouncement() {
        System.out.println("Estado ganador");
    }

    private static void printVisitedStateAnnouncement() {
        if (Constants.DEBUG_PRINTING_ALLOWED) {
            System.out.println("Estado ya visitado");
        }
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
    
    private static class MatrixPair {
        int[][] m1;
        int[][] m2;

        private MatrixPair(int[][] m1, int[][] m2) {
            this.m1 = copyMatrix(m1);
            this.m2 = copyMatrix(m2);
        }

        @Override
        public int hashCode() {
            int hash = 7;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof MatrixPair p)) {
                return false;
            }
            return compareMatrices(m1, p.m1) && compareMatrices(m2, p.m2);
        }
    }
    
    private static String indexTranslator(String index) {
        if (index == null) {
            throw new IllegalArgumentException("null index");
        }
        if (index.equals(Constants.DOWN_DIRECTION)) {
            return Constants.UP_DIRECTION;
        }
        if (index.equals(Constants.UP_DIRECTION)) {
            return Constants.DOWN_DIRECTION;
        }
        if (index.equals(Constants.LEFT_DIRECTION)) {
            return Constants.RIGHT_DIRECTION;
        }
        if (index.equals(Constants.RIGHT_DIRECTION)) {
            return Constants.LEFT_DIRECTION;
        }
        throw new IllegalArgumentException("unknown index given");
    }
    
    private static String reverseString(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(s);
        builder.reverse();
        return builder.toString();
    }
}

