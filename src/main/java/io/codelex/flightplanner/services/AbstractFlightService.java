package io.codelex.flightplanner.services;

import io.codelex.flightplanner.models.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractFlightService {

    public void checkDate(Flight flight) {
        if (!flight.getDepartureTime().isBefore(flight.getArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible date");
        }
    }

}
