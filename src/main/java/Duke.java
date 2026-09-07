import hse.HSE;

/**
 * The chat assistant exposed to the graphical interface.
 * It delegates every user message to the HSE task manager.
 */
public class Duke {
    private final HSE hse;

    /** Creates a chat assistant backed by the standard HSE data file. */
    public Duke() {
        hse = new HSE("data/duke.txt");
    }

    /** Generates a response for the user's chat message. */
    public String getResponse(String input) {
        return hse.getResponse(input);
    }
}