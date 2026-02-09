package dev.ailuruslabs.uniman.web;

import dev.ailuruslabs.uniman.domain.common.locations.Barangay;
import dev.ailuruslabs.uniman.domain.common.locations.Municipality;
import dev.ailuruslabs.uniman.domain.common.locations.Province;
import dev.ailuruslabs.uniman.domain.common.locations.Region;
import dev.ailuruslabs.uniman.domain.ports.output.locations.BarangayRepository;
import dev.ailuruslabs.uniman.domain.ports.output.locations.MunicipalityRepository;
import dev.ailuruslabs.uniman.domain.ports.output.locations.ProvinceRepository;
import dev.ailuruslabs.uniman.domain.ports.output.locations.RegionRepository;
import dev.ailuruslabs.uniman.infrastructure.persistence.locations.BarangayJooqRepository;
import dev.ailuruslabs.uniman.infrastructure.persistence.locations.MunicipalityJooqRepository;
import dev.ailuruslabs.uniman.infrastructure.persistence.locations.ProvinceJooqRepository;
import dev.ailuruslabs.uniman.infrastructure.persistence.locations.RegionJooqRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class LocationService {
    private final RegionRepository regionRepository;
    private final ProvinceRepository provinceRepository;
    private final MunicipalityRepository municipalityRepository;
    private final BarangayRepository barangayRepository;

    @Inject
    public LocationService(RegionRepository regionRepository, ProvinceRepository provinceRepository,
                           MunicipalityRepository municipalityRepository, BarangayRepository barangayRepository) {
        this.regionRepository = regionRepository;
        this.provinceRepository = provinceRepository;
        this.municipalityRepository = municipalityRepository;
        this.barangayRepository = barangayRepository;
    }

    public List<Region> getRegions() {
        return regionRepository.findAll();
    }

    public List<Province> getProvincesByRegionCode(String code) {
        return provinceRepository.findAllByRegionCode(code);
    }

    public List<Municipality> getMunicipalitesByProvinceId(int provinceId) {
        return municipalityRepository.findAllByProvinceId(provinceId);
    }

    public List<Barangay> getBarangaysByMunicipalityId(int municipalityId) {
        return barangayRepository.findAllByMunicipalityId(municipalityId);
    }
}
