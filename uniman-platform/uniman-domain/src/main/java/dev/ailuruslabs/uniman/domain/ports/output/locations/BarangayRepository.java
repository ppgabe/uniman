package dev.ailuruslabs.uniman.domain.ports.output.locations;

import dev.ailuruslabs.uniman.domain.common.locations.Barangay;

import java.util.List;
import java.util.Optional;

public interface BarangayRepository {
    void save(Barangay barangay);

    Optional<Barangay> findById(int id);

    List<Barangay> findAllByMunicipalityId(int municipalityId);
}
