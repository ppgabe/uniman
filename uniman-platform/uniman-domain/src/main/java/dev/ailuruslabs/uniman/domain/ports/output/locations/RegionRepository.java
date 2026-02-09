package dev.ailuruslabs.uniman.domain.ports.output.locations;

import dev.ailuruslabs.uniman.domain.common.locations.Region;

import java.util.List;
import java.util.Optional;

public interface RegionRepository {
    void save(Region region);

    Optional<Region> findByCode(String code);
    List<Region> findAll();
}
