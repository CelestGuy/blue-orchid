package dev.theoduval.orchid.exceptions;

public class IllegalIdException extends IllegalArgumentException {
    public static final String negativeID = "An ID cannot be negative";
    public static final String nonExistantID = "This ID doesn't exist";

    public IllegalIdException() {
        super();
    }

    public IllegalIdException(int id, String message) {
        super("ID : " + id + ". " + message);
    }
}
