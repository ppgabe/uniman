package dev.ailuruslabs.uniman.infrastructure.persistence.locations;

import dev.ailuruslabs.uniman.domain.common.locations.Province;
import dev.ailuruslabs.uniman.domain.ports.output.IdentityGenerator;
import dev.ailuruslabs.uniman.domain.ports.output.locations.ProvinceRepository;
import org.jooq.DSLContext;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static dev.ailuruslabs.uniman.infrastructure.persistence.jooq.Sequences.SEQ_PSGC_PROVINCES_ID;
import static dev.ailuruslabs.uniman.infrastructure.persistence.jooq.Tables.PSGC_PROVINCES;

public class ProvinceJooqRepository implements ProvinceRepository, IdentityGenerator<Integer> {

    private final DSLContext dsl;
    private final Deque<Integer> sequenceCache;
    private final ReentrantLock lock;

    public ProvinceJooqRepository(DSLContext dsl) {
        this.dsl = dsl;
        this.sequenceCache = new ArrayDeque<>(20);
        this.lock = new ReentrantLock(); // No need for fair-ordering here. Not critical.
    }

    @Override
    public Integer nextIdentity() {

        /* Acquire a lock first. I used a manual ReentrantLock over a synchronized block
         * because prior to JDK 24, synchronized blocks get pinned to the carrier thread.
         * This project uses JDK 25 as its target, but still.
         */
        lock.lock();
        try {
            // Check if the cache has values
            if (sequenceCache.isEmpty()) {
                // If it is empty, fetch the next 20 IDs from the database
                List<Integer> nextSequenceValues = dsl.nextvals(SEQ_PSGC_PROVINCES_ID, 20);
                // Then add those to the cache
                sequenceCache.addAll(nextSequenceValues);
            }

            // Return the first ID in the queue
            return sequenceCache.poll();
        } finally {
            // Release the lock
            lock.unlock();
        }
    }

    @Override
    public void save(Province province) {
        dsl.insertInto(PSGC_PROVINCES)
           .set(PSGC_PROVINCES.ID, province.getId())
           .set(PSGC_PROVINCES.CODE, province.getCode())
           .set(PSGC_PROVINCES.NAME, province.getName())
           .set(PSGC_PROVINCES.REGION_CODE, province.getRegionCode())
           .onConflict(PSGC_PROVINCES.ID) // Upsert instead of erroring out
           .doUpdate()
           .set(PSGC_PROVINCES.CODE, province.getCode())
           .set(PSGC_PROVINCES.NAME, province.getName())
           .set(PSGC_PROVINCES.REGION_CODE, province.getRegionCode())
           .execute();
    }

    @Override
    public Optional<Province> findByCode(String code) {
        return dsl.selectFrom(PSGC_PROVINCES)
                  .where(PSGC_PROVINCES.CODE.equal(code))
                  .fetchOptional(record -> Province.reconstruct(record.getId(), record.getRegionCode(),
                                                                record.getCode(), record.getName()));
    }

    @Override
    public List<Province> findAllByRegionCode(String regionCode) {
        return dsl.selectFrom(PSGC_PROVINCES)
                  .where(PSGC_PROVINCES.REGION_CODE.equal(regionCode))
                  .fetch(record -> Province.reconstruct(record.getId(), record.getRegionCode(),
                                                        record.getCode(), record.getName()));
    }
}
