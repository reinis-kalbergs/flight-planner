package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.AddFlightRequest;
import io.codelex.flightplanner.Flight;
import io.codelex.flightplanner.errors.FlightAlreadyExists;
import io.codelex.flightplanner.errors.ImpossibleDate;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight passFlightInfo(AddFlightRequest addFlightRequest) throws FlightAlreadyExists, ImpossibleDate {
        long currentId = flightRepository.getId();
        Flight tempFlight = new Flight(currentId, addFlightRequest);
        if (!tempFlight.getDepartureTime().isBefore(tempFlight.getArrivalTime())) {
            throw new ImpossibleDate();
        }
        flightRepository.addFlight(tempFlight);
        return flightRepository.fetchFlight(currentId);
    }

    public Flight fetchFlight(Long id) {
        return flightRepository.fetchFlight(id);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteFlight(id);
    }


}
