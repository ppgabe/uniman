package dev.ailuruslabs.uniman.domain.ports.output;

import dev.ailuruslabs.uniman.domain.common.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository {

    void save(Address address);

    // TODO: Implement the Person class soon
    Address findByPersonId(UUID personId);
    
    Optional<Address> findByUUID(UUID uuid);
}
