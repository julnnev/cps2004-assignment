package exceptions;

public class NoBuildingsException extends Exception{
    public NoBuildingsException(String errorMessage) {
        super(errorMessage);
    }
}
