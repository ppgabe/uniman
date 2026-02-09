package dev.ailuruslabs.uniman.domain.ports.output.locations;

import dev.ailuruslabs.uniman.domain.common.locations.Address;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository {
    void save(Address address);

    // TODO: Implement the Person class soon
    Address getByPersonId(UUID personId);
    Optional<Address> findByPersonId(UUID personId);

    Optional<Address> findByUUID(UUID uuid);
}
