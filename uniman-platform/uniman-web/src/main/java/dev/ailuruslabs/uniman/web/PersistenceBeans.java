package dev.ailuruslabs.uniman.web;

import dev.ailuruslabs.uniman.domain.ports.output.locations.BarangayRepository;
import dev.ailuruslabs.uniman.domain.ports.output.locations.MunicipalityRepository;
import dev.ailuruslabs.uniman.domain.ports.output.locations.ProvinceRepository;
import dev.ailuruslabs.uniman.domain.ports.output.locations.RegionRepository;
import dev.ailuruslabs.uniman.infrastructure.persistence.locations.BarangayJooqRepository;
import dev.ailuruslabs.uniman.infrastructure.persistence.locations.MunicipalityJooqRepository;
import dev.ailuruslabs.uniman.infrastructure.persistence.locations.ProvinceJooqRepository;
import dev.ailuruslabs.uniman.infrastructure.persistence.locations.RegionJooqRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.jooq.DSLContext;

@ApplicationScoped
public class PersistenceBeans {

    @Inject
    DSLContext dsl;

    @Produces
    @ApplicationScoped
    public RegionRepository regionRepository() {
        return new RegionJooqRepository(dsl);
    }

    @Produces
    @ApplicationScoped
    public ProvinceRepository provinceJooqRepository() {
        return new ProvinceJooqRepository(dsl);
    }

    @Produces
    @ApplicationScoped
    public MunicipalityRepository municipalityJooqRepository() {
        return new MunicipalityJooqRepository(dsl);
    }

    @Produces
    @ApplicationScoped
    public BarangayRepository barangayJooqRepository() {
        return new BarangayJooqRepository(dsl);
    }
}
