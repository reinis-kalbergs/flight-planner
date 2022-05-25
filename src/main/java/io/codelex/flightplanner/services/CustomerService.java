package io.codelex.flightplanner.services;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.models.PageResult;
import io.codelex.flightplanner.models.SearchFlightsRequest;

import java.util.List;

public interface CustomerService {

    PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest);

    List<Airport> searchAirports(String airportSearch);

}
