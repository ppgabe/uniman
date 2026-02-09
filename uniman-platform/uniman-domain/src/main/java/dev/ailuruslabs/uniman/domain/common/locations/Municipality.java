package dev.ailuruslabs.uniman.domain.common.locations;

import dev.ailuruslabs.uniman.domain.ports.output.IdentityGenerator;

import java.util.Objects;
import java.util.function.Supplier;

import static dev.ailuruslabs.uniman.domain.common.validations.Validations.failIf;

public final class Municipality {
    private final int id;
    private final String provinceCode;
    private final String code;

    private final String name;

    public Municipality of(IdentityGenerator<Integer> idSupplier, String provinceCode, String code, String name,
                           boolean validate) {
        return new Municipality(idSupplier.nextIdentity(), provinceCode, code, name, true);
    }

    public Municipality reconstruct(int id, String provinceCode, String code, String name, boolean validate) {
        return new Municipality(id, provinceCode, code, name, false);
    }

    private Municipality(int id, String provinceCode, String code, String name, boolean validate) {
        Objects.requireNonNull(provinceCode, "Province code code cannot be null");
        Objects.requireNonNull(code, "Code cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");

        if (validate) {
            failIf(provinceCode.isBlank(), "Province code cannot be blank");
            failIf(provinceCode.length() != 3, "Province code must have a length of 3");
            failIf(code.isBlank(), "Code cannot be blank");
            failIf(code.length() != 2, "Code must have a length of 2");
            failIf(name.isBlank(), "Name cannot be blank");
            failIf(name.length() > 100, "Name cannot have a length greater than 100");
        }

        this.id = id;
        this.provinceCode = provinceCode;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getProvinceCode() {
        return provinceCode;
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

        return id == that.id && Objects.equals(provinceCode, that.provinceCode)
               && Objects.equals(code, that.code)
               && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provinceCode, code, name);
    }
}
