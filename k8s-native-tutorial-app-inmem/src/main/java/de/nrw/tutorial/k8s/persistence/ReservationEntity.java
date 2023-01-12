package de.nrw.tutorial.k8s.persistence;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReservationEntity {
    private String id;
    private String carType;
    private LocalDate startingDate;
    private LocalDate finishingDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationEntity entity = (ReservationEntity) o;
        return Objects.equals(carType, entity.carType) && Objects.equals(startingDate, entity.startingDate) && Objects.equals(finishingDate, entity.finishingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carType, startingDate, finishingDate);
    }
}
