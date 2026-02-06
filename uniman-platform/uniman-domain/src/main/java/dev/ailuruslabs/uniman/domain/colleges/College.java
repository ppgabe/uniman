package dev.ailuruslabs.uniman.domain.colleges;

import java.time.Instant;
import java.util.UUID;

import static dev.ailuruslabs.uniman.domain.common.DomainRegistry.UUID_GENERATOR;

public final class College {
    private final int id;

    private final String code;
    private final String name;
    private final UUID addressId;
    private final Instant createdAt;

    public static College of(int id, String code, String name, UUID addressId) {
        var createdAt = Instant.now();

        return new College(id, code, name, addressId, createdAt, true);
    }

    private College(int id, String code, String name, UUID addressId, Instant createdAt, boolean validate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.addressId = addressId;
        this.createdAt = createdAt;
    }
}
