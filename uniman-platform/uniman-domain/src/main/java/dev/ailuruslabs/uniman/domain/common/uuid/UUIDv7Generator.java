package dev.ailuruslabs.uniman.domain.common.uuid;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import java.util.UUID;

public class UUIDv7Generator implements UUIDGenerator {

    private static final TimeBasedEpochRandomGenerator uuidGenerator = Generators.timeBasedEpochRandomGenerator();

    @Override
    public UUID constructWithTimestamp(long rawTimestamp) {
        return uuidGenerator.construct(rawTimestamp);
    }

    @Override
    public UUID generate() {
        return uuidGenerator.generate();
    }

}
