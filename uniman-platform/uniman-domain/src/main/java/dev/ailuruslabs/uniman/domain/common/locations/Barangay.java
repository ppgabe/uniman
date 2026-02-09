package dev.ailuruslabs.uniman.domain.common.locations;

import dev.ailuruslabs.uniman.domain.ports.output.IdentityGenerator;

import java.util.Objects;
import java.util.function.Supplier;

import static dev.ailuruslabs.uniman.domain.common.validations.Validations.failIf;

public final class Barangay {
    private final int id;
    private final String municipalityCode;
    private final String code;

    private final String name;

    public static Barangay of(IdentityGenerator<Integer> idSupplier, String municipalityCode, String code, String name) {
        return new Barangay(idSupplier.nextIdentity(), municipalityCode, code, name, true);
    }

    public static Barangay reconstruct(int id, String municipalityCode, String code, String name) {
        return new Barangay(id, municipalityCode, code, name, false);
    }

    private Barangay(int id, String municipalityCode, String code, String name, boolean validate) {
        Objects.requireNonNull(municipalityCode, "Municipality code cannot be null");
        Objects.requireNonNull(code, "Code cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");

        if (validate) {
            failIf(municipalityCode.isBlank(), "Municipality code cannot be blank");
            failIf(municipalityCode.length() != 2, "Municipality code must have a length of 2");
            failIf(code.isBlank(), "Code cannot be blank");
            failIf(code.length() != 3, "Code must have a length of 3");
            failIf(name.isBlank(), "Name cannot be blank");
            failIf(name.length() > 100, "Name cannot have a length greater than 100");
        }

        this.id = id;
        this.municipalityCode = municipalityCode;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {return false;}

        Barangay barangay = (Barangay) o;

        return id == barangay.id && Objects.equals(municipalityCode, barangay.municipalityCode)
               && Objects.equals(code, barangay.code)
               && Objects.equals(name, barangay.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, municipalityCode, code, name);
    }
}
