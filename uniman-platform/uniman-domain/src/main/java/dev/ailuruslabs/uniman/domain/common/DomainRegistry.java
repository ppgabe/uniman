package dev.ailuruslabs.uniman.domain.common;

import dev.ailuruslabs.uniman.domain.common.uuid.UUIDGenerator;
import dev.ailuruslabs.uniman.domain.common.uuid.UUIDv7Generator;

public final class DomainRegistry {
    public final static UUIDGenerator UUID_GENERATOR = new UUIDv7Generator();
}
