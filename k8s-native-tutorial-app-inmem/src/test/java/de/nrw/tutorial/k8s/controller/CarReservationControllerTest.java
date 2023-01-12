package de.nrw.tutorial.k8s.controller;

import de.nrw.tutorial.k8s.service.CarReservationService;
import de.nrw.tutorial.k8s.service.ReservationNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarReservationController.class)
class CarReservationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CarReservationService carReservationService;

    @Test
    public void shouldCreateReservation() throws Exception {
        ReservationDto expectedReservation = new ReservationDto(null, "VW", LocalDate.parse("2022-12-27"), LocalDate.parse("2022-12-29"));
        when(carReservationService.createReservation(eq(expectedReservation))).thenReturn("testId");
        mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
{ "carModel":"VW",
  "startDate":"2022-12-27",
  "finishDate":"2022-12-29"
}
"""))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("location", "testId"));

        verify(carReservationService).createReservation(eq(expectedReservation));
    }

    @Test
    public void shouldRetrieveReservation() throws Exception {
        ReservationDto reservation = new ReservationDto("testId", "VW", LocalDate.parse("2022-12-27"), LocalDate.parse("2022-12-29"));
        when(carReservationService.retrieveReservation("testId")).thenReturn(reservation);

        mockMvc.perform(MockMvcRequestBuilders.get("/reservations/testId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("""
{ "id": "testId",
  "carModel":"VW",
  "startDate":"2022-12-27",
  "finishDate":"2022-12-29"
}
"""));
    }

    @Test
    public void shouldReservationNotFound() throws Exception {
        when(carReservationService.retrieveReservation("unknownId")).thenThrow(new ReservationNotFoundException("Reservation not found: unknownId"));
        mockMvc.perform(MockMvcRequestBuilders.get("/reservations/unknownId"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("""
                        {"message": "Reservation not found: unknownId"}
                        """, false));
    }
}