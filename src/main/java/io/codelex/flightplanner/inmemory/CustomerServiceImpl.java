package io.codelex.flightplanner.inmemory;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.models.PageResult;
import io.codelex.flightplanner.models.SearchFlightsRequest;
import io.codelex.flightplanner.services.CustomerService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "in-memory")
public class CustomerServiceImpl implements CustomerService {
    FlightRepository flightRepository;

    public CustomerServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        return flightRepository.searchFlights(searchFlightsRequest);
    }

    public List<Airport> searchAirports(String airportSearch) {
        return flightRepository.searchAirports(airportSearch.trim());
    }
}
