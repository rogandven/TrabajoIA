/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoia;

import java.util.ArrayList;
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
    // B -> Derecha// C -> Arriba
    // A -> Abajo
    // D -> Izquierda
    // B -> Derecha
    
    
    private ArrayList<MatrixSwapPlan> getSwapList(int[][] matrix, int[] emptySpaceCoordinates) {
        return Board.getSwapList(matrix, emptySpaceCoordinates);
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
        this.emptySpaceCoordinates = findEmptySpaceCoordinates(matrix);
        this.emptySpaceCoordinates2 = findEmptySpaceCoordinates2(matrix2);
        // this.previous = null;
        this.id = id;
        this.id2 = id2;
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

    private int[] findEmptySpaceCoordinates(int[][] matrix) {
        return Board.findEmptySpaceCoordinates(matrix);
    }
    
    private int[] findEmptySpaceCoordinates2(int[][] matrix) {
        return Board.findEmptySpaceCoordinates(matrix);
    }    
    
    private boolean isFinalState(int[][] matrix) {
        return compareMatrices(matrix, finalState);
    }
    
    private boolean isBeginningState(int[][] matrix) {
        return compareMatrices(matrix, beginningState);
    }
    
    private static int[][] copyMatrixWithSwappedValues(int[][] matrix, MatrixSwapPlan msp) {
        return Board.copyMatrixWithSwappedValues(matrix, msp);
    }
    
    private static int[][] copyMatrix(int[][] matrix) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[0].length);
        }
        return newMatrix;
    }
    
    private static boolean compareMatrices(int[][] a, int[][] b) {
        return Board.compareMatrices(a, b);
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
        Board.printWinningStateAnnouncement();
    }

    private static void printVisitedStateAnnouncement() {
        Board.printVisitedStateAnnouncement();
    }        
    
    /* private static void printRoadChangeAnnouncement() {
        System.out.println("Cambio de camino");
    } */

    private static void printErrorMessage(Throwable t) {
        Board.printErrorMessage(t);
    }
    
    private static int[][] validateMatrix(int[][] matrix) {
        return Board.validateMatrix(matrix);
    }
    
    private static void validateBothMatrices(int[][] matrix1, int[][] matrix2) {
        Board.validateBothMatrices(matrix1, matrix2);
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

