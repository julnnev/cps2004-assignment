package exceptions;

public class NoTroopsOwnedException extends Exception{
    public NoTroopsOwnedException(String errorMessage) {
        super(errorMessage);
    }
}
