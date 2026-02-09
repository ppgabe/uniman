package dev.ailuruslabs.uniman.domain.common.uuid;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;

import java.util.UUID;

public class UUIDv7Generator implements UUIDGenerator {

    /**
     * This uses the java_uuid_generator project by org.fasterxml because the JDK does not
     * offer UUIDv7 genenration as of JDK 25. JDK 26 has this natively!
     */
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
