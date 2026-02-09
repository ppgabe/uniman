package dev.ailuruslabs.uniman.domain.ports.output;

public interface IdentityGenerator<T> {
    T nextIdentity();
}
