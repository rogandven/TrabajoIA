/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabajoia;

/**
 *
 * @author Roger
 */
public class TooManyStatesException extends RuntimeException {
    
    public TooManyStatesException() {
    }

    public TooManyStatesException(String s) {
        super(s);
    }

    public TooManyStatesException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyStatesException(Throwable cause) {
        super(cause);
    }
    
}
