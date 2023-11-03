package dev.theoduval.orchid.exceptions;

public class IllegalIdException extends IllegalArgumentException {
    public static final String negativeID = "An ID cannot be negative";
    public static final String nonExistentID = "This ID doesn't exist";
    public static final String alreadySetID = "This ID is already set";
    public static final String notSetID = "This ID has not been set";

    public IllegalIdException() {
        super();
    }

    public IllegalIdException(int id, String message) {
        super("ID : " + id + ". " + message);
    }
}
