package dev.ailuruslabs.uniman.infrastructure.persistence.locations;

import dev.ailuruslabs.uniman.domain.common.locations.Barangay;
import dev.ailuruslabs.uniman.domain.ports.output.IdentityGenerator;
import dev.ailuruslabs.uniman.domain.ports.output.locations.BarangayRepository;
import org.jooq.DSLContext;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static dev.ailuruslabs.uniman.infrastructure.persistence.jooq.Sequences.SEQ_PSGC_PROVINCES_ID;
import static dev.ailuruslabs.uniman.infrastructure.persistence.jooq.Tables.PSGC_BARANGAYS;

public class BarangayJooqRepository implements BarangayRepository, IdentityGenerator<Integer> {
    private final DSLContext dsl;
    private final Deque<Integer> sequenceCache;
    private final ReentrantLock lock;

    public BarangayJooqRepository(DSLContext dsl) {
        this.dsl = dsl;
        this.sequenceCache = new ArrayDeque<>(20);
        this.lock = new ReentrantLock();
    }

    @Override
    public Integer nextIdentity() {
        lock.lock();
        try {
            if (sequenceCache.isEmpty()) {
                List<Integer> nextSequenceValues = dsl.nextvals(SEQ_PSGC_PROVINCES_ID, 20);
                sequenceCache.addAll(nextSequenceValues);
            }

            return sequenceCache.poll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void save(Barangay barangay) {
        dsl.insertInto(PSGC_BARANGAYS)
           .set(PSGC_BARANGAYS.ID, barangay.getId())
           .set(PSGC_BARANGAYS.CODE, barangay.getCode())
           .set(PSGC_BARANGAYS.NAME, barangay.getName())
           .set(PSGC_BARANGAYS.MUNICIPALITY_ID, barangay.getMunicipalityId())
           .onConflict(PSGC_BARANGAYS.ID) // Upsert instead of erroring out
           .doUpdate()
           .set(PSGC_BARANGAYS.CODE, barangay.getCode())
           .set(PSGC_BARANGAYS.NAME, barangay.getName())
           .set(PSGC_BARANGAYS.MUNICIPALITY_ID, barangay.getMunicipalityId())
           .execute();
    }

    @Override
    public Optional<Barangay> findById(int id) {
        return dsl.selectFrom(PSGC_BARANGAYS)
                  .where(PSGC_BARANGAYS.ID.equal(id))
                  .fetchOptional(record -> Barangay.reconstruct(record.getId(),
                                                                record.getMunicipalityId(), record.getCode(),
                                                                record.getName()));
    }

    @Override
    public List<Barangay> findAllByMunicipalityId(int municipalityId) {
        return dsl.selectFrom(PSGC_BARANGAYS)
                  .where(PSGC_BARANGAYS.MUNICIPALITY_ID.equal(municipalityId))
                  .fetch(record -> Barangay.reconstruct(record.getId(),
                                                        record.getMunicipalityId(), record.getCode(),
                                                        record.getName()));
    }
}
