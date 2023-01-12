package de.nrw.tutorial.k8s.persistence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryReservationStorageTest {
    private final InMemoryReservationStorage storage = new InMemoryReservationStorage();

    @Test
    public void shouldStoreAndRetrieveReservation() {
        ReservationEntity entity = ReservationEntity.builder().id("test").build();
        storage.saveEntity(entity);

        assertThat(storage.getEntity("test")).isPresent().get().isEqualTo(entity);
    }
}