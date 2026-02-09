package dev.ailuruslabs.uniman.domain.common.locations;

import java.util.Objects;
import java.util.function.Supplier;

import static dev.ailuruslabs.uniman.domain.common.validations.Validations.failIf;

public final class Province {
    private final int id;
    private final String regionCode;
    private final String code;

    @Override
    public int hashCode() {
        return Objects.hash(id, regionCode, code, name);
    }

    private final String name;

    public Province of(Supplier<Integer> idSupplier, String regionCode, String code, String name, boolean validate) {
        return new Province(idSupplier.get(), regionCode, code, name, true);
    }

    public Province reconstruct(int id, String regionCode, String code, String name, boolean validate) {
        return new Province(id, regionCode, code, name, false);
    }

    private Province(int id, String regionCode, String code, String name, boolean validate) {
        Objects.requireNonNull(regionCode, "Region code cannot be null");
        Objects.requireNonNull(code, "Code cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");

        if (validate) {
            failIf(regionCode.isBlank(), "Region code cannot be blank");
            failIf(regionCode.length() != 2, "Region code must have a length of 2");
            failIf(code.isBlank(), "Code cannot be blank");
            failIf(code.length() != 3, "Code must have a length of 3");
            failIf(name.isBlank(), "Name cannot be blank");
            failIf(name.length() > 100, "Name cannot have a length greater than 100");
        }

        this.id = id;
        this.regionCode = regionCode;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getRegionCode() {
        return regionCode;
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
        Province province = (Province) o;
        return id == province.id
               && Objects.equals(regionCode, province.regionCode)
               && Objects.equals(code, province.code)
               && Objects.equals(name, province.name);
    }
}
