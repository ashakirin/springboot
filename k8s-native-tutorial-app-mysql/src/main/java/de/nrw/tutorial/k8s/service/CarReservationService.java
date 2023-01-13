package de.nrw.tutorial.k8s.service;

import de.nrw.tutorial.k8s.controller.ReservationDto;
import de.nrw.tutorial.k8s.persistence.InMemoryReservationStorage;
import de.nrw.tutorial.k8s.persistence.JpaReservationRepository;
import de.nrw.tutorial.k8s.persistence.ReservationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CarReservationService {
    private final InMemoryReservationStorage storage;
    private final JpaReservationRepository repository;

    @Autowired
    public CarReservationService(InMemoryReservationStorage storage, JpaReservationRepository repository) {
        this.storage = storage;
        this.repository = repository;
    }

    public String createReservation(ReservationDto reservationDto) {
        String id = UUID.randomUUID().toString();
        ReservationEntity entity = ReservationEntity.builder()
                .id(id)
                .carType(reservationDto.getCarModel())
                .startingDate(reservationDto.getStartDate())
                .finishingDate(reservationDto.getFinishDate())
                .build();

        //storage.saveEntity(entity);
        repository.save(entity);
        return id;
    }

    public ReservationDto retrieveReservation(String id) {
        //Optional<ReservationEntity> foundEntity = storage.getEntity(id);
        Optional<ReservationEntity> foundEntity = repository.findById(id);

        return foundEntity
                .map(e -> ReservationDto.builder()
                        .id(e.getId())
                        .carModel(e.getCarType())
                        .startDate(e.getStartingDate())
                        .finishDate(e.getFinishingDate())
                        .build())
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found: " + id));
    }

    public List<ReservationDto> retrieveAllReservations() {
        //List<ReservationEntity> foundEntity = storage.getAllEntities();
        Iterable<ReservationEntity> entities = repository.findAll();

        return StreamSupport.stream(entities.spliterator(), false)
                .map(e -> ReservationDto.builder()
                        .id(e.getId())
                        .carModel(e.getCarType())
                        .startDate(e.getStartingDate())
                        .finishDate(e.getFinishingDate())
                        .build())
                .collect(Collectors.toList());
    }

    public void updateReservation(ReservationDto reservationDto) {
        //Optional<ReservationEntity> foundEntity = storage.getEntity(reservationDto.getId());
        Optional<ReservationEntity> foundEntity = repository.findById(reservationDto.getId());
        if(foundEntity.isEmpty()) {
            throw new ReservationNotFoundException("Reservation not found: " + reservationDto.getId());
        }
        ReservationEntity entity = foundEntity.get();
        entity.setCarType(reservationDto.getCarModel());
        entity.setStartingDate(reservationDto.getStartDate());
        entity.setFinishingDate(reservationDto.getFinishDate());

        //storage.saveEntity(entity);
        repository.save(entity);
    }
}
