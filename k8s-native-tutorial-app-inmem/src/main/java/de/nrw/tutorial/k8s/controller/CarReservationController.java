package de.nrw.tutorial.k8s.controller;

import de.nrw.tutorial.k8s.service.CarReservationService;
import de.nrw.tutorial.k8s.service.ReservationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class CarReservationController {
    private final CarReservationService carReservationService;

    @Autowired
    public CarReservationController(CarReservationService carReservationService) {
        this.carReservationService = carReservationService;
    }

    @GetMapping("/reservations")
    public List<ReservationDto> getAllReservations(){
        return List.of(new ReservationDto("1", "VW", LocalDate.now(), LocalDate.now()));
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> createReservation(@RequestBody ReservationDto reservationDto) {

        String id = carReservationService.createReservation(reservationDto);
        return ResponseEntity.created(URI.create(id)).build();
    }

    @GetMapping(value="/reservations/{id}")
    public ReservationDto getReservation(@PathVariable String id) {
        return carReservationService.retrieveReservation(id);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorDescription> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorDescription(ex.getMessage()));
    }
}
