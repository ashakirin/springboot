package de.nrw.tutorial.k8s.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ReservationDto {
    private final String id;
    private final String carModel;
    private final LocalDate startDate;
    private final LocalDate finishDate;
}
