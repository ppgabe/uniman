package dev.ailuruslabs.uniman.domain.common.locations;

import java.util.Objects;

import static dev.ailuruslabs.uniman.domain.common.validations.Validations.failIf;

public record Region(String code, String name) {

    public Region {
        Objects.requireNonNull(code, "Code cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");

        failIf(code.isBlank(), "Code cannot be blank");
        failIf(name.isBlank(), "Name cannot be blank");
        failIf(code.length() != 2, "Code must have a length of 2");
        failIf(name.length() > 100, "Code cannot have a length greater than 100");
    }

}
