package dev.ailuruslabs.uniman.domain.common.uuid;

import java.util.UUID;

public interface UUIDGenerator {
    UUID constructWithTimestamp(long rawTimestamp);
    UUID generate();
}
