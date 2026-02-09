package dev.ailuruslabs.uniman.domain.ports.output.locations;

import dev.ailuruslabs.uniman.domain.common.locations.Municipality;

import java.util.List;
import java.util.Optional;

public interface MunicipalityRepository {
    void save(Municipality municipality);

    Optional<Municipality> findById(int id);
    List<Municipality> findAllByProvinceId(int provinceId);
}
