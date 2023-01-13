package de.nrw.tutorial.k8s.persistence;

import org.springframework.data.repository.CrudRepository;

public interface JpaReservationRepository extends CrudRepository<ReservationEntity, String> {
}
