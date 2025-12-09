package exceptions;

public class PlayerNameProblem extends RuntimeException {
    public PlayerNameProblem(String message) {
        super(message);
    }
}
