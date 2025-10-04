/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoia;

import java.util.Scanner;

/**
 *
 * @author Roger
 */
public class Matrix {
    public int[][] matrix;
    public int size;
    public int totalSize;
    public String name;
    public Scanner s;

    public Matrix() {
        this.s = new Scanner(System.in);
    }

    public void setSize(int n) {
        this.matrix = new int[n][n];
        this.size = n;
        this.totalSize = (n*n);
    }
    
    public void setSizeByKeyboard(String str) {
        int newSize;
        System.out.print(str);
        newSize = Integer.parseInt(s.nextLine().trim());
        setSize(newSize);
    }
    
    public void fillByKeyboard(String str) {
        
    }
}
