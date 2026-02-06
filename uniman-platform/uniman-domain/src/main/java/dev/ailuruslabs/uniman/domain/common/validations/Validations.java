package dev.ailuruslabs.uniman.domain.common.validations;

public final class Validations {

    public static void failIf(boolean condition, String message) {
        if (condition) {
            throw new ValidationException(message);
        }
    }

    public static void require(boolean condition, String message) {
        if (!condition) {
            throw new ValidationException(message);
        }
    }
}
