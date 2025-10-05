/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoia;

/**
 *
 * @author Roger
 */
public final class Constants {
    public static final boolean DEBUG_PRINTING_ALLOWED = false;
    public static final boolean MATRIX_PRINTING_ALLOWED = true;
    public static String UP_DIRECTION = "C";
    public static String DOWN_DIRECTION = "A";
    public static String LEFT_DIRECTION = "D";
    public static String RIGHT_DIRECTION = "B";
    public static String START_STATE = "S";
    public static String FINAL_STATE = "F";
    
    public static int AMPLITUD = 1;
    public static int PROFUNDIDAD = 2;
    public static int BIDIRECCIONAL = 3;
    
    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException("Wait... how were you able to call this?");
    }
}
