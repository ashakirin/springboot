package de.nrw.tutorial.k8s.service;

import de.nrw.tutorial.k8s.controller.ReservationDto;
import de.nrw.tutorial.k8s.persistence.InMemoryReservationStorage;
import de.nrw.tutorial.k8s.persistence.ReservationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CarReservationService {
    private final InMemoryReservationStorage storage;

    @Autowired
    public CarReservationService(InMemoryReservationStorage storage) {
        this.storage = storage;
    }

    public String createReservation(ReservationDto reservationDto) {
        String id = UUID.randomUUID().toString();
        ReservationEntity entity = ReservationEntity.builder()
                .id(id)
                .carType(reservationDto.getCarModel())
                .startingDate(reservationDto.getStartDate())
                .finishingDate(reservationDto.getFinishDate())
                .build();

        storage.saveEntity(entity);
        return id;
    }

    public ReservationDto retrieveReservation(String id) {
        Optional<ReservationEntity> foundEntity = storage.getEntity(id);

        return foundEntity
                .map(e -> ReservationDto.builder()
                        .id(e.getId())
                        .carModel(e.getCarType())
                        .startDate(e.getStartingDate())
                        .finishDate(e.getFinishingDate())
                        .build())
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found: " + id));
    }

    public void updateReservation(ReservationDto reservationDto) {
        Optional<ReservationEntity> foundEntity = storage.getEntity(reservationDto.getId());
        if(foundEntity.isEmpty()) {
            throw new ReservationNotFoundException("Reservation not found: " + reservationDto.getId());
        }
        ReservationEntity entity = foundEntity.get();
        entity.setCarType(reservationDto.getCarModel());
        entity.setStartingDate(reservationDto.getStartDate());
        entity.setFinishingDate(reservationDto.getFinishDate());

        storage.saveEntity(entity);
    }
}