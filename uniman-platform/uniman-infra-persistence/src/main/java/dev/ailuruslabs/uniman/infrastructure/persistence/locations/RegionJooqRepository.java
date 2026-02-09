package dev.ailuruslabs.uniman.infrastructure.persistence.locations;

import dev.ailuruslabs.uniman.domain.common.locations.Region;
import dev.ailuruslabs.uniman.domain.ports.output.IdentityGenerator;
import dev.ailuruslabs.uniman.domain.ports.output.locations.RegionRepository;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;

import static dev.ailuruslabs.uniman.infrastructure.persistence.jooq.Tables.PSGC_REGIONS;

public class RegionJooqRepository implements RegionRepository {

    private final DSLContext dsl;

    public RegionJooqRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public void save(Region region) {
        dsl.insertInto(PSGC_REGIONS)
           .set(PSGC_REGIONS.CODE, region.code())
           .set(PSGC_REGIONS.NAME, region.name())
           .onConflict(PSGC_REGIONS.CODE)
           .doUpdate()
           .set(PSGC_REGIONS.NAME, region.name())
           .execute();
    }

    @Override
    public Optional<Region> findByCode(String code) {
        return dsl.selectFrom(PSGC_REGIONS)
                  .where(PSGC_REGIONS.CODE.equal(code))
                  .fetchOptional(record -> new Region(record.getCode(), record.getName()));
    }

    @Override
    public List<Region> findAll() {
        return dsl.selectFrom(PSGC_REGIONS).fetchInto(Region.class);
    }

}
