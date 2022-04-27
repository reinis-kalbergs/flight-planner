package io.codelex.flightplanner.adminapi;

import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {
    private FlightRepository flightRepository;

    public AdminService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(AddFlightRequest addFlightRequest) {
        Flight flight = new Flight(flightRepository.getId(), addFlightRequest);
        checkDate(flight);
        return flightRepository.addFlight(flight);
    }

    private void checkDate(Flight flight) throws ResponseStatusException {
        if (!flight.getDepartureTime().isBefore(flight.getArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible date");
        }
    }

    public Flight fetchFlight(Long id) {
        return flightRepository.fetchFlight(id);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteFlight(id);
    }


}
