package dev.ailuruslabs.uniman.infrastructure.persistence.locations;

import dev.ailuruslabs.uniman.domain.common.locations.Municipality;
import dev.ailuruslabs.uniman.domain.ports.output.IdentityGenerator;
import dev.ailuruslabs.uniman.domain.ports.output.locations.MunicipalityRepository;
import org.jooq.DSLContext;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static dev.ailuruslabs.uniman.infrastructure.persistence.jooq.Sequences.SEQ_PSGC_MUNICIPALITIES_ID;
import static dev.ailuruslabs.uniman.infrastructure.persistence.jooq.Tables.PSGC_MUNICIPALITIES;
import static dev.ailuruslabs.uniman.infrastructure.persistence.jooq.Tables.PSGC_PROVINCES;

public class MunicipalityJooqRepository implements MunicipalityRepository, IdentityGenerator<Integer> {
    private final DSLContext dsl;
    private final Deque<Integer> sequenceCache;
    private final ReentrantLock lock;

    public MunicipalityJooqRepository(DSLContext dsl) {
        this.dsl = dsl;
        this.sequenceCache = new ArrayDeque<>(20);
        this.lock = new ReentrantLock(); // No need for fair-ordering here. Not critical.
    }

    @Override
    public Integer nextIdentity() {
        lock.lock();
        try {
            if (sequenceCache.isEmpty()) {
                List<Integer> nextSequenceValues = dsl.nextvals(SEQ_PSGC_MUNICIPALITIES_ID, 20);
                sequenceCache.addAll(nextSequenceValues);
            }

            return sequenceCache.poll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void save(Municipality municipality) {
        dsl.insertInto(PSGC_MUNICIPALITIES)
           .set(PSGC_MUNICIPALITIES.ID, municipality.getId())
           .set(PSGC_MUNICIPALITIES.CODE, municipality.getCode())
           .set(PSGC_MUNICIPALITIES.NAME, municipality.getName())
           .set(PSGC_MUNICIPALITIES.PROVINCE_ID, municipality.getProvinceId())
           .onConflict(PSGC_MUNICIPALITIES.ID) // Upsert instead of erroring out
           .doUpdate()
           .set(PSGC_MUNICIPALITIES.CODE, municipality.getCode())
           .set(PSGC_MUNICIPALITIES.NAME, municipality.getName())
           .set(PSGC_MUNICIPALITIES.PROVINCE_ID, municipality.getProvinceId())
           .execute();
    }

    @Override
    public Optional<Municipality> findById(int id) {
        return dsl.selectFrom(PSGC_MUNICIPALITIES)
                  .where(PSGC_MUNICIPALITIES.ID.equal(id))
                  .fetchOptional(record -> Municipality.reconstruct(record.getId(),
                                                                    record.getProvinceId(), record.getCode(),
                                                                    record.getName()));
    }

    @Override
    public List<Municipality> findAllByProvinceId(int provinceId) {
        return dsl.selectFrom(PSGC_MUNICIPALITIES)
                  .where(PSGC_MUNICIPALITIES.PROVINCE_ID.equal(provinceId))
                  .fetch(record -> Municipality.reconstruct(record.getId(),
                                                            record.getProvinceId(), record.getCode(),
                                                            record.getName()));
    }
}
