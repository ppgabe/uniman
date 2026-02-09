package dev.ailuruslabs.uniman.domain.common.locations;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static dev.ailuruslabs.uniman.domain.common.validations.Validations.*;
import static dev.ailuruslabs.uniman.domain.common.DomainRegistry.UUID_GENERATOR;

public final class Address {
    private final UUID uuid;
    private final String address;
    private final String address2;
    private final int barangayId;
    private final Instant createdAt;

    public static Address of(String address, String address2, int barangayId, String city, String province,
                             String region) {
        // Create the timestamp first
        var createdAt = Instant.now();
        // Then use that to construct the UUID
        var uuid = UUID_GENERATOR.constructWithTimestamp(createdAt.toEpochMilli());

        // This way, our database receives a consistent timestamp for both the UUID and created_at column!
        return new Address(uuid, address, address2, barangayId, city, province, region, createdAt, true);
    }

    public static Address reconstruct(UUID uuid, String address, String address2, int barangayId, String city,
                                      String province, String region, Instant createdAt) {
        // Allow "permissive reconstitution" in case of legacy data or accidental changes in the repository
        return new Address(uuid, address, address2, barangayId, city, province, region, createdAt, false);
    }

    private Address(UUID uuid, String address, String address2, int barangayId, String city, String province,
                    String region, Instant createdAt, boolean validate) {
        requireNonNull(uuid, "UUID cannot be null");
        requireNonNull(address, "Address cannot be null");
        requireNonNull(city, "City cannot be null");
        requireNonNull(province, "Province cannot be null");
        requireNonNull(region, "Region cannot be null");
        requireNonNull(createdAt, "Creation timestamp cannot be null");

        if (validate) {
            failIf(address.isBlank(), "Address cannot be blank");
            failIf(city.isBlank(), "City cannot be blank");
            failIf(province.isBlank(), "Province cannot be blank");
            failIf(region.isBlank(), "Region cannot be blank");
        }

        this.uuid = uuid;
        this.address = address;
        this.address2 = address2;
        this.barangayId = barangayId;
        this.createdAt = createdAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return Optional.ofNullable(address2).orElse("");
    }

    public int getBarangayId() {
        return barangayId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {return false;}
        Address address1 = (Address) o;
        return Objects.equals(uuid, address1.uuid)
               && Objects.equals(address, address1.address)
               && Objects.equals(address2, address1.address2)
               && Objects.equals(barangayId, address1.barangayId)
               && Objects.equals(createdAt, address1.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, address, address2, barangayId, createdAt);
    }
}