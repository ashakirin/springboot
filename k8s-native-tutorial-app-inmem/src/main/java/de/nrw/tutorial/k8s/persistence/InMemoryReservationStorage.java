package de.nrw.tutorial.k8s.persistence;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryReservationStorage {
    private final Map<String, ReservationEntity> storageMap = new HashMap<>();

    public void saveEntity(ReservationEntity entity) {
        storageMap.put(entity.getId(), entity);
    }

    public Optional<ReservationEntity> getEntity(String id) {
        return Optional.ofNullable(storageMap.get(id));
    }
}
