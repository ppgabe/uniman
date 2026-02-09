package dev.ailuruslabs.uniman.domain.ports.output.locations;

import dev.ailuruslabs.uniman.domain.common.locations.Province;

import java.util.List;
import java.util.Optional;

public interface ProvinceRepository {
    void save(Province province);

    Optional<Province> findById(int id);
    List<Province> findAllByRegionCode(String regionCode);
}
