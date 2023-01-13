package de.nrw.tutorial.k8s.persistence;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryReservationStorage {
    private final Map<String, ReservationEntity> storageMap = new HashMap<>();

    public void saveEntity(ReservationEntity entity) {
        storageMap.put(entity.getId(), entity);
    }

    public Optional<ReservationEntity> getEntity(String id) {
        return Optional.ofNullable(storageMap.get(id));
    }

    public List<ReservationEntity> getAllEntities() {
        return new ArrayList<ReservationEntity>(storageMap.values());
    }
}
