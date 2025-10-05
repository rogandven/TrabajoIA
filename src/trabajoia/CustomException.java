/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoia;

/**
 *
 * @author Roger
 */
public class CustomException extends RuntimeException {
    
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
