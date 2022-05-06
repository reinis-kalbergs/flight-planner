package io.codelex.flightplanner.customerapi;

import io.codelex.flightplanner.flights.Airport;
import io.codelex.flightplanner.flights.Flight;
import io.codelex.flightplanner.flights.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    FlightRepository flightRepository;

    public CustomerService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        return flightRepository.searchFlights(searchFlightsRequest);
    }

    public List<Airport> searchAirports(String airportSearch) {
        return flightRepository.searchAirports(airportSearch.trim());
    }
}
