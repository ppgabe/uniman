package dev.ailuruslabs.uniman.domain.common.locations;

import dev.ailuruslabs.uniman.domain.ports.output.IdentityGenerator;

import java.util.Objects;
import java.util.function.Supplier;

import static dev.ailuruslabs.uniman.domain.common.validations.Validations.failIf;

public final class Barangay {
    private final int id;
    private final int municipalityId;
    private final String code;

    private final String name;

    public static Barangay of(IdentityGenerator<Integer> idSupplier, int municipalityId, String code, String name) {
        return new Barangay(idSupplier.nextIdentity(), municipalityId, code, name, true);
    }

    public static Barangay reconstruct(int id, int municipalityId, String code, String name) {
        return new Barangay(id, municipalityId, code, name, false);
    }

    private Barangay(int id, int municipalityId, String code, String name, boolean validate) {
        Objects.requireNonNull(code, "Code cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");

        if (validate) {
            failIf(code.isBlank(), "Code cannot be blank");
            failIf(code.length() != 3, "Code must have a length of 3");
            failIf(name.isBlank(), "Name cannot be blank");
            failIf(name.length() > 100, "Name cannot have a length greater than 100");
        }

        this.id = id;
        this.municipalityId = municipalityId;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getMunicipalityId() {
        return municipalityId;
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

        return id == barangay.id && municipalityId == barangay.municipalityId
               && Objects.equals(code, barangay.code)
               && Objects.equals(name, barangay.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, municipalityId, code, name);
    }
}
