/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
/**
 *
 * @author Roger
 */

public final class Board implements MatrixTree {
    public static int MAX_STATES = 0;
    
    private int[][] matrix;
    private int[] emptySpaceCoordinates;
    private int[][] finalState;
    private ArrayList<int[][]> visitedStateListPointer; 
    // private int[][] previous;
    private String id;
    
    public void Amplitud(ArrayList<String> winningRoutesPointer) {
        if (Constants.DEBUG_PRINTING_ALLOWED) {
            System.out.println("MAX_STATES = " + MAX_STATES);
            new Scanner(System.in).nextLine();
        }
        if (winningRoutesPointer == null) {
            throw new CustomException("El puntero a las rutas ganadoras no puede ser nulo");
        }        
        Amplitud(false, winningRoutesPointer);
    }
    
    private void Amplitud(boolean hijo, ArrayList<String> winningRoutesPointer) {
        safeGuard(id);
        if (isStateVisited(matrix)) {
            printVisitedStateAnnouncement();
            if (getShallReturn(winningRoutesPointer, id)) {
                return;
            }
        }
        if (!hijo) {
            printMatrixHeader(id);
            this.printMatrix();
        }
        ArrayList<MatrixSwapPlan> swapList = Board.getSwapList(matrix, this.emptySpaceCoordinates);
        ArrayList<Board> children = new ArrayList<>();

        for (MatrixSwapPlan p : swapList) {
            try {
                if (Constants.DEBUG_PRINTING_ALLOWED) {
                    System.out.println("SWAP ID = " + p.id);
                }
                Board b = new Board(copyMatrixWithSwappedValues(matrix, p), finalState, visitedStateListPointer, id + p.id);
                printMatrixHeader(b.id);
                printMatrix(b.matrix);
                if (isFinalState(b.matrix)) {
                    winningRoutesPointer.add(b.id + Constants.FINAL_STATE);
                    printWinningStateAnnouncement();
                    // visitedStateListPointer.removeAll(visitedStateListPointer);
                } else {
                    children.add(b);
                }        
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
    
    public static boolean getShallReturn(ArrayList<String> winningRoutesPointer, String currentRoute) {
        if (Constants.SMART_RETURNING_ENABLED) {
            boolean shallReturn = true;
            if (currentRoute == null) {
                return true;
            }
            for (String route : winningRoutesPointer) {
                if (route == null) {
                    continue;
                }
                if ((currentRoute.length()) < (route.length())) {
                    shallReturn = false;
                    break;
                }
            }
            printVisitedStateAnnouncement();
            return shallReturn;
        } else {
            return true;
        }

    }

    public static void safeGuard(String id) {
        if (Constants.DEBUG_PRINTING_ALLOWED) {
            System.out.println("ID LENGTH = " + id.length());
        }
        if (id.length() >= MAX_STATES) {
            throw new TooManyStatesException("Bucle infinito detectado");
        }
    }
    
    private void Profundidad(int amount, IntegerPointer limit, ArrayList<String> winningRoutesPointer) {
        safeGuard(id);
        if (isFinalState(matrix)) {
            if (limit.getN() == -1) {
                limit.setN(amount);
            }
            if (amount < limit.getN()) {
                limit.setN(amount);
            }
            winningRoutesPointer.add(id + Constants.FINAL_STATE);
            
            Board.printMatrixHeader(id);
            this.printMatrix();
            printWinningStateAnnouncement();
            // visitedStateListPointer.removeAll(visitedStateListPointer);
            return;
        }
        
        if (isStateVisited(matrix)) {
            printVisitedStateAnnouncement();

            if (getShallReturn(winningRoutesPointer, id)) {
                return;
            }
        }        
        
        printMatrixHeader(id);
        this.printMatrix();
        
        if ((limit.getN() != -1) && amount > limit.getN()) {
            printLimitAnnouncement();
            return;
        }
        
        /* if (isStateVisited(matrix)) {
            printVisitedStateAnnouncement();
            return;
        } */
 
        ArrayList<MatrixSwapPlan> swapList = Board.getSwapList(matrix, emptySpaceCoordinates);

        for (MatrixSwapPlan p : swapList) {
            try {
                Board b = new Board(copyMatrixWithSwappedValues(this.matrix, p), this.finalState, this.visitedStateListPointer, id + p.id);
                b.Profundidad(amount + 1, limit, winningRoutesPointer);
                // printRoadChangeAnnouncement();
            } catch (StackOverflowError e) {
                printErrorMessage(e);
            }
        }
    }
    

    
    public static ArrayList<MatrixSwapPlan> getSwapList(int[][] matrix, int[] emptySpaceCoordinates) {
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
    
    private Board(int[][] matrix, int[][] finalState, ArrayList<int[][]> visitedStateListPointer, String id) {
        this.matrix = validateMatrix(matrix);
        this.finalState = validateMatrix(finalState);
        validateBothMatrices(matrix, finalState);
        this.visitedStateListPointer = visitedStateListPointer;
        this.emptySpaceCoordinates = Board.findEmptySpaceCoordinates(this.matrix);
        // this.previous = null;
        this.id = id;
        
    }
    
    public Board(int[][] matrix, int[][] finalState) {
        this(matrix, finalState, new ArrayList<>(), Constants.START_STATE);
        MAX_STATES = factorial(matrix.length * matrix[0].length);
    }
            
    public static void printMatrix(int[][] m) {
        if (Constants.MATRIX_PRINTING_ALLOWED) {
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
    }

    private void printMatrix() {
        printMatrix(this.matrix);
    }    

    public static int[] findEmptySpaceCoordinates(int[][] matrix) {
        int emptySpaceCoordinates[];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    emptySpaceCoordinates = new int[2];
                    emptySpaceCoordinates[0] = i;
                    emptySpaceCoordinates[1] = j;
                    return emptySpaceCoordinates;
                }
            }
        }
        return null;
    }
    
    private boolean isFinalState(int[][] matrix) {
        return compareMatrices(matrix, finalState);
    }
    
    public static int[][] copyMatrixWithSwappedValues(int[][] matrix, MatrixSwapPlan msp) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[0].length);
        }
        swapValuesInMatrix(newMatrix, msp);
        return newMatrix;
    }
    
    public static void swapValuesInMatrix(int[][] matrix, MatrixSwapPlan msp) {
        int temp = matrix[msp.p1X][msp.p1Y];
        matrix[msp.p1X][msp.p1Y] = matrix[msp.p2X][msp.p2Y];
        matrix[msp.p2X][msp.p2Y] = temp;
    }
    
    public static boolean compareMatrices(int[][] a, int[][] b) {
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
        if (visitedStateListPointer.size() > MAX_STATES) {
            throw new TooManyStatesException("Todos los estados ya fueron visitados");
        }
        return false;
    }
    
    private static void printMatrixHeader(String header) {
        if (Constants.MATRIX_PRINTING_ALLOWED) {
            System.out.println("Matriz: " + header);
        }
    }
    
    public static void printWinningStateAnnouncement() {
        System.out.println("Estado meta");
    }

    public static void printVisitedStateAnnouncement() {
        if (Constants.DEBUG_PRINTING_ALLOWED) {
            System.out.println("Estado ya visitado");
        }
    }        
    
    /*
    private static void printRoadChangeAnnouncement() {
        System.out.println("Cambio de camino");
    }
    */

    public static void printErrorMessage(Throwable t) {
        if (Constants.DEBUG_PRINTING_ALLOWED) {
            System.out.println(t.getClass().getSimpleName() + ": " + t.getMessage());
        }
    }
    
    public static int[][] validateMatrix(int[][] matrix) {
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
    
    
    public static void validateBothMatrices(int[][] matrix1, int[][] matrix2) {
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
    
    public static int factorial(int n) {
        n = Math.abs(n);
        int carry = 1;
        for (int i = 2; i <= n; i++) {
            carry *= i;
        }
        return Math.abs(carry);
    }
}

