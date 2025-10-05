/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Roger
 */

public final class Board {

    public Board(int[][] matrix, int[][] finalState, ArrayList<int[][]> visitedStateListPointer, String id) {
        this.matrix = matrix;
        this.finalState = finalState;
        this.visitedStateListPointer = visitedStateListPointer;
        this.emptySpaceCoordinates = new int[2];
        this.findEmptySpaceCoordinates();
        this.anterior = null;
        this.id = id;
    }
    
    
    /*
    public Board(int[][] matrix, ArrayList<int[][]> visitedStateListPointer) {
        this.visitedStateListPointer = visitedStateListPointer;
        this.matrix = matrix;
        this.finalState = new int[matrix.length][matrix[0].length];
        this.generateFinalState(this.createShuffledNumberListFromMatrix());
        this.emptySpaceCoordinates = new int[2];
        this.findEmptySpaceCoordinates();
        this.anterior = null;
    }
    */
    
    public class MatrixSwapPlan {
        public int p1X;
        public int p1Y;
        public int p2X;
        public int p2Y;
        String id;

        public MatrixSwapPlan(int p1X, int p1Y, int p2X, int p2Y, String id) {
            this.p1X = p1X;
            this.p1Y = p1Y;
            this.p2X = p2X;
            this.p2Y = p2Y;
            this.id = id;
        }
    }
    
    public int[][] matrix;
    public int[] emptySpaceCoordinates;
    public int[][] finalState;
    public ArrayList<int[][]> visitedStateListPointer; 
    public int[][] anterior;
    public String id;
            
    public void printMatrix(int[][] m) {
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

    public void printMatrix() {
        this.printMatrix(this.matrix);
    }    

    public final void findEmptySpaceCoordinates() {
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
    
    public ArrayList<MatrixSwapPlan> getSwapList(int[][] matrix) {
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
    
    /*
    public final void generateFinalState(Integer numbers[]) {
        if (numbers.length != (finalState.length * finalState[0].length)) {
            throw new IllegalArgumentException("array with invalid length passed");
        }
        ArrayList<Integer> numbersNew = new ArrayList<>();
        numbersNew.addAll(Arrays.asList(numbers));
        numbers = null;
        
        // https://www.youtube.com/watch?v=V2J_G5Ngfvk
        // https://leetcode.com/problems/spiral-finalState/solutions/6986614/finalState-traversal-solution-in-java-with-v-ns10/
        int rowMin = 0;
        int rowMax = (finalState.length - 1);
        int colMin = 0;
        int colMax = (finalState[0].length - 1);
        // int total = (rowMax + 1) * (colMax + 1);
        
        while (!numbersNew.isEmpty()) {
            for (int col = colMin; col <= colMax && !numbersNew.isEmpty(); col++)
                finalState[rowMin][col] = numbersNew.removeFirst();
            rowMin++;

            // Traverse downwards
            for (int row = rowMin; row <= rowMax && !numbersNew.isEmpty(); row++)
                finalState[row][colMax] = numbersNew.removeFirst();
            colMax--;

            // Traverse from right to left
            for (int col = colMax; col >= colMin && !numbersNew.isEmpty(); col--)
                finalState[rowMax][col] = numbersNew.removeFirst();
            rowMax--;

            // Traverse upwards
            for (int row = rowMax; row >= rowMin && !numbersNew.isEmpty(); row--)
                finalState[row][colMin] = numbersNew.removeFirst();
            colMin++;
        }
    }
    */
    
    public final Integer[] createShuffledNumberListFromMatrix() {
        ArrayList<Integer> numberList = new ArrayList<>();
        for (int[] matrix1 : matrix) {
            for (int matrix2 : matrix1) {
                numberList.add((Integer)matrix2);
            }
        }
        
        Collections.shuffle(numberList);
        return numberList.toArray(Integer[]::new);
    }  
    
    /* public final Integer[] createNumberListFromMatrix() {
        ArrayList<Integer> numberList = new ArrayList<>();
        for (int[] matrix1 : matrix) {
            for (int matrix2 : matrix1) {
                numberList.add((Integer)matrix2);
            }
        }
        
        numberList.sort((a, b) -> {
            return a.compareTo(b);
        });
        numberList.addLast(numberList.removeFirst());
        return numberList.toArray(Integer[]::new);
    }*/
    
    
    public boolean isFinalState(int[][] matrix) {
        return compareMatrices(matrix, finalState);
    }
    
    public void Amplitud(boolean hijo) {
        if (isStateVisited(matrix)) {
            // System.out.println("Estado ya visitado");
            return;
        }
        if (!hijo) {
            System.out.println("Matriz " + id + ": ");
            this.printMatrix();
        }
        ArrayList<MatrixSwapPlan> swapList = this.getSwapList(matrix);
        ArrayList<Board> children = new ArrayList<>();
        // System.out.println("Matriz hijos:");
        for (MatrixSwapPlan p : swapList) {
            try {
                Board b = new Board(copyMatrixWithSwappedValues(matrix, p), finalState, visitedStateListPointer, id + p.id);
                System.out.println("Matriz " + b.id + ": ");
                printMatrix(b.matrix);
                if (isFinalState(b.matrix)) {
                    System.out.println("Estado ganador");
                    continue;
                }        
                children.add(b);
            } catch (StackOverflowError e) {
                System.out.println("StackOverflowError");
            }
        }
        for (Board child : children) {
            try {
                child.Amplitud(true);
            } catch (StackOverflowError e) {
                System.out.println("StackOverflowError");
            }
        }            
    }
    
    public void Profundidad() {
        System.out.println("Matriz " + id + ": ");
        this.printMatrix();
        if (isFinalState(matrix)) {
            System.out.println("Estado ganador");
            return;
        }
        if (isStateVisited(matrix)) {
            System.out.println("Estado ya visitado");
            return;
        }
 
        ArrayList<MatrixSwapPlan> swapList = this.getSwapList(matrix);

        for (MatrixSwapPlan p : swapList) {
            try {
                Board b = new Board(copyMatrixWithSwappedValues(this.matrix, p), this.finalState, this.visitedStateListPointer, id + p.id);
                b.Profundidad();
                System.out.println("Cambio de camino");
            } catch (StackOverflowError e) {}
        }
    }
    
    public void Bidireccional() {
        
    }
    
    public int[][] copyMatrixWithSwappedValues(int[][] matrix, MatrixSwapPlan msp) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[0].length);
        }
        swapValuesInMatrix(newMatrix, msp);
        return newMatrix;
    }
    
    public void swapValuesInMatrix(int[][] matrix, MatrixSwapPlan msp) {
        int temp = matrix[msp.p1X][msp.p1Y];
        matrix[msp.p1X][msp.p1Y] = matrix[msp.p2X][msp.p2Y];
        matrix[msp.p2X][msp.p2Y] = temp;
    }
    
    public boolean compareMatrices(int[][] a, int[][] b) {
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
    
    public boolean isStateVisited(int[][] matrix) {
        for (int[][] m : visitedStateListPointer) {
            // System.out.println("Revisar si estado esta visitado");
            if (compareMatrices(m, matrix)) {
                return true;
            }
        }
        visitedStateListPointer.add(matrix);
        return false;
    }
}
