package dev.ailuruslabs.uniman.infrastructure.persistence.locations;

import dev.ailuruslabs.uniman.domain.common.locations.Province;
import dev.ailuruslabs.uniman.domain.ports.output.IdentityGenerator;
import dev.ailuruslabs.uniman.domain.ports.output.locations.ProvinceRepository;
import org.jooq.DSLContext;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import static dev.ailuruslabs.uniman.infrastructure.persistence.jooq.Sequences.*;

public class ProvinceJooqRepository implements ProvinceRepository, IdentityGenerator<Integer> {

    private final DSLContext dsl;
    private final Deque<Integer> sequenceCache;
    private final ReentrantLock lock;

    public ProvinceJooqRepository(DSLContext dsl) {
        this.dsl = dsl;
        this.sequenceCache = new ArrayDeque<>(20);
        this.lock = new ReentrantLock(true);
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
    public void save(Province province) {

    }

    @Override
    public Optional<Province> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public List<Province> findAllByRegionCode(String regionCode) {
        return List.of();
    }
}
