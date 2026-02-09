package dev.ailuruslabs.uniman.domain.common.locations;

import dev.ailuruslabs.uniman.domain.ports.output.IdentityGenerator;

import java.util.Objects;
import java.util.function.Supplier;

import static dev.ailuruslabs.uniman.domain.common.validations.Validations.failIf;

public final class Municipality {
    private final int id;
    private final int provinceId;
    private final String code;
    private final String name;

    public static Municipality of(IdentityGenerator<Integer> idSupplier, int provinceId, String code, String name) {
        return new Municipality(idSupplier.nextIdentity(), provinceId, code, name, true);
    }

    public static Municipality reconstruct(int id, int provinceId, String code, String name) {
        return new Municipality(id, provinceId, code, name, false);
    }

    private Municipality(int id, int provinceId, String code, String name, boolean validate) {
        Objects.requireNonNull(code, "Code cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");

        if (validate) {
            failIf(code.isBlank(), "Code cannot be blank");
            failIf(code.length() != 2, "Code must have a length of 2");
            failIf(name.isBlank(), "Name cannot be blank");
            failIf(name.length() > 100, "Name cannot have a length greater than 100");
        }

        this.id = id;
        this.provinceId = provinceId;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getProvinceId() {
        return provinceId;
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

        Municipality that = (Municipality) o;

        return id == that.id && provinceId == that.provinceId
               && Objects.equals(code, that.code)
               && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provinceId, code, name);
    }
}
