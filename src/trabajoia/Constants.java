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
    public static final boolean SMART_RETURNING_ENABLED = false;
    public static final String UP_DIRECTION = "C";
    public static final String DOWN_DIRECTION = "A";
    public static final String LEFT_DIRECTION = "D";
    public static final String RIGHT_DIRECTION = "B";
    public static final String START_STATE = "S";
    public static final String FINAL_STATE = "F";
    
    public static final int AMPLITUD = 1;
    public static final int PROFUNDIDAD = 2;
    public static final int BIDIRECCIONAL = 3;
    
    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException("Wait... how were you able to call this?");
    }
}
