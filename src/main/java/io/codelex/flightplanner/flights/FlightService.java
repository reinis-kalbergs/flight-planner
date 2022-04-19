package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.AddFlightRequest;
import io.codelex.flightplanner.Flight;
import io.codelex.flightplanner.errors.FlightAlreadyExists;
import io.codelex.flightplanner.errors.ImpossibleDate;
import io.codelex.flightplanner.errors.NoFlightFound;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public synchronized Flight addFlight(AddFlightRequest addFlightRequest) throws FlightAlreadyExists, ImpossibleDate {
        long currentId = flightRepository.getId();
        Flight tempFlight = new Flight(currentId, addFlightRequest);
        if (!tempFlight.getDepartureTime().isBefore(tempFlight.getArrivalTime())) {
            throw new ImpossibleDate();
        }
        flightRepository.addFlight(tempFlight);
        return flightRepository.fetchFlight(currentId);
    }

    public AddFlightRequest fetchFlight(Long id) throws NoFlightFound {
        Flight flightRequest = flightRepository.fetchFlight(id);
        // Convert to addFlightRequest to get the necessary Time Format
        return new AddFlightRequest(flightRequest);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteFlight(id);
    }


}
