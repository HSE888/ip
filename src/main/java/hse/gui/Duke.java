package hse.gui;

import hse.HSE;

/** Provides the HSE task manager to the graphical interface. */
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
