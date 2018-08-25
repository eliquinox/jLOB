package exceptions;

public class LimitPlacementMismatchException extends RuntimeException {
    public LimitPlacementMismatchException(String msg){
        super(msg);
    }
}
