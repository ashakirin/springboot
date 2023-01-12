package de.nrw.tutorial.k8s.service;

import de.nrw.tutorial.k8s.controller.ReservationDto;
import de.nrw.tutorial.k8s.persistence.InMemoryReservationStorage;
import de.nrw.tutorial.k8s.persistence.ReservationEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarReservationServiceTest {

    @Mock
    InMemoryReservationStorage storage;

    @InjectMocks
    private CarReservationService service;

    @Test
    public void shouldCreateReservation() {
        // arrange
        LocalDate startingDate = LocalDate.parse("2022-12-01");
        LocalDate finishingDate = LocalDate.parse("2022-12-02");
        ReservationDto reservationDto = new ReservationDto("test", "vw", startingDate, finishingDate);

        // act
        service.createReservation(reservationDto);

        // assert
        ReservationEntity reservationEntity = new ReservationEntity("test", "vw", startingDate, finishingDate);
        verify(storage).saveEntity(reservationEntity);
    }

    @Test
    public void shouldRetrieveReservation() {
        // arrange
        LocalDate startingDate = LocalDate.parse("2022-12-01");
        LocalDate finishingDate = LocalDate.parse("2022-12-02");
        ReservationEntity reservationEntity = new ReservationEntity("test", "vw", startingDate, finishingDate);
        when(storage.getEntity("test")).thenReturn(Optional.of(reservationEntity));

        // act
        ReservationDto reservationDto = service.retrieveReservation("test");

        // assert
        assertThat(reservationDto.getId()).isEqualTo("test");
        assertThat(reservationDto.getCarModel()).isEqualTo("vw");
        assertThat(reservationDto.getStartDate()).isEqualTo(startingDate);
        assertThat(reservationDto.getFinishDate()).isEqualTo(finishingDate);
    }

    @Test
    public void shouldUpdateReservation() {
        // arrange
        LocalDate startingDate = LocalDate.parse("2022-12-01");
        LocalDate finishingDate = LocalDate.parse("2022-12-02");
        ReservationEntity reservationEntity = new ReservationEntity("test", "vw", startingDate, finishingDate);
        when(storage.getEntity("test")).thenReturn(Optional.of(reservationEntity));

        LocalDate newFinishDate = finishingDate.plusDays(1);
        ReservationDto reservationDto = new ReservationDto("test", "vw", startingDate, newFinishDate);

        // act
        service.updateReservation(reservationDto);

        // assert
        ReservationEntity updatedEntity = new ReservationEntity("test", "vw", startingDate, newFinishDate);
        verify(storage).saveEntity(updatedEntity);
    }

    @Test
    public void shouldThrowExceptionIfNotFound() {
        when(storage.getEntity("unknown")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.retrieveReservation("unknown")).isInstanceOf(ReservationNotFoundException.class)
                .hasMessageContaining("unknown");
    }
}
