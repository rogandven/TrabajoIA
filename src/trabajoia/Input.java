package trabajoia;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Roger
 */
public class Input {
    private Input() throws IllegalAccessException {
        throw new IllegalAccessException("Wait... how were you able to call this?");
    }
    
    public static boolean getYesOrNoQuestionFromUser(Scanner s, String str) {
        String input;
        
        while (true) {
            try {
                System.out.print(str + " (S/N): ");
                input = s.nextLine().trim();
                switch (input) {
                    case "S":
                    case "s":
                        return true;
                    case "N":
                    case "n":
                        return false;
                    default:
                        throw new CustomException("Opcion no valida");
                }
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Algo salio mal. Ingrese su respuesta nuevamente");
            }
        }
    }
    
    public static int getSizeFromUser(Scanner s) {
        int input;
        while (true) {
            try {
                System.out.print("Ingrese el tamanio de la matriz: ");
                input = Integer.parseInt(s.nextLine().trim());
                if (input > 1) {
                    return input;
                }
                throw new CustomException("Valor fuera de rango");
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Algo salio mal. Ingrese el tamanio nuevamente");
            }
        }
    }
    
    public static int[][] getMatrixFromUser(Scanner s, int size, String name) {
        System.out.println("En este contexto, el numero 0 se usa como espacio vacio");
        int[][] matrix = new int[size][size];
        int value;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                while (true) {
                    try {
                        System.out.print(name + "[" + i + "][" + j + "]: ");
                        value = Integer.parseInt(s.nextLine().trim());
                        matrix[i][j] = value;
                        break;
                    } catch (Exception e) {
                        System.out.println("Algo salio mal. Ingrese el valor nuevamente");
                    }
                }
            }
        }
        return matrix;
    }
    
    public static int[] getNumberArrayFromRandomNumbers(int matrixSize) {
        HashSet<Integer> numbers = new HashSet<>();
        int max = matrixSize * matrixSize;
        int min = 1;
        Random r = new Random((long)(Math.random() * 2763));
        while (numbers.size() < ((matrixSize * matrixSize) - 1)) {
            numbers.add(r.nextInt(max - min + 1) + min);
        }
        numbers.add(0);
        List<Object> numbers2 = Arrays.asList(numbers.toArray());
        Collections.shuffle(numbers2);
        int[] finalArray = new int[matrixSize * matrixSize];
        int index = 0;
        for (Object o : numbers2) {
            if (o instanceof Integer integer) {
                finalArray[index] = integer;
            } else {
                finalArray[index] = 0;
            }
            index++;
        }
        return finalArray;
    }
    
    public static int[] randomizeExistingArray(int[] array) {
        int totalArraySize = array.length;
        Integer[] secondary = Arrays.stream( array ).boxed().toArray( Integer[]::new );
        List<Integer> numbers2 = Arrays.asList(secondary);
        Collections.shuffle(numbers2);
        int[] finalArray = new int[totalArraySize];
        int index = 0;
        for (Integer i : numbers2) {
            finalArray[index] = i;
            index++;
        }
        return finalArray;
    }
    
    public static int[][] getMatrixFromArray(int[] array, int matrixSize) {
        int[][] newMatrix = new int[matrixSize][matrixSize];
        int k = 0;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                newMatrix[i][j] = array[k];
                k++;
            }
        }
        return newMatrix;
    }
    
    public static int[] getArrayFromMatrix(int[][] matrix) {
        int[] newArray = new int[matrix.length * matrix[0].length];
        int k = 0;
        for (int[] array : matrix) {
            for (int number : array) {
                newArray[k] = number;
                k++;
            }
        }
        return newArray;
    }
    
    public static int getUserOption(Scanner s) {
        int option;
        
        while (true) {
            try {
                System.out.println("Seleccione un algoritmo de recorrido:");
                System.out.println(" - 1. Amplitud");
                System.out.println(" - 2. Profundidad Iterativa");
                System.out.println(" - 3. Bidireccional");
                System.out.print("Opcion seleccionada: ");
                option = Integer.parseInt(s.nextLine());
                if (option == Constants.AMPLITUD || option == Constants.BIDIRECCIONAL || option == Constants.PROFUNDIDAD) {
                    return option;
                }
                throw new CustomException("Opcion no valida");
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Algo salio mal. Ingrese el valor nuevamente");
            }
        }
    }
}
