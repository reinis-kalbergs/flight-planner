package io.codelex.flightplanner.inmemory;

import io.codelex.flightplanner.models.AddFlightRequest;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.services.AbstractFlightService;
import io.codelex.flightplanner.services.AdminService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "in-memory")
public class AdminServiceImpl extends AbstractFlightService implements AdminService {
    private FlightRepository flightRepository;

    public AdminServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight addFlight(AddFlightRequest addFlightRequest) {
        Flight flight = new Flight(addFlightRequest);
        checkDate(flight);
        return flightRepository.addFlight(flight);
    }

    public Flight fetchFlight(Long id) {
        return flightRepository.fetchFlight(id);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteFlight(id);
    }

}
