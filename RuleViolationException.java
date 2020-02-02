/**
 * Thrown to indicate that the application has attempted to violate the rules in the game.
 *
 * @author  Calvin Lau
 */
public class RuleViolationException extends Exception{

    /**
     * Constructs a RuleViolationException with the specified detail message.
     * @param message the detail message
     */
    public RuleViolationException(String message) {
        super(message);
    }
}
