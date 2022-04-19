package io.codelex.flightplanner.customerapi;

import io.codelex.flightplanner.Airport;
import io.codelex.flightplanner.Flight;
import io.codelex.flightplanner.flights.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    FlightRepository flightRepository;

    public CustomerService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Airport> searchByAirport(String search) {
        //todo: place it in Repository
        List<Airport> result = new ArrayList<>();
        for (Flight flight : flightRepository.getAllFlights()) {
            if (containsAirport(flight.getFrom(), search.trim())) {
                result.add(flight.getFrom());
            }
            if (containsAirport(flight.getTo(), search.trim())) {
                result.add(flight.getTo());
            }
        }
        return result;
    }

    private boolean containsAirport(Airport airport, String text) {
        text = text.toLowerCase();
        return airport.getAirport().toLowerCase().contains(text) || airport.getCity().toLowerCase().contains(text) || airport.getCountry().toLowerCase().contains(text);
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        //todo:move to repository
        LocalDate localDate = LocalDate.parse(searchFlightsRequest.getDepartureDate());

        List<Flight> flightList = flightRepository.getAllFlights().stream()
                .filter(flight -> flight.getFrom().getAirport().equalsIgnoreCase(searchFlightsRequest.getFrom()))
                .filter(flight -> flight.getTo().getAirport().equalsIgnoreCase(searchFlightsRequest.getTo()))
                .filter(flight -> flight.getDepartureTime().toLocalDate().equals(localDate))
                .collect(Collectors.toList());
        PageResult<Flight> pageResult = new PageResult<>();
        pageResult.setItems(flightList);
        if (!flightList.isEmpty()) {
            pageResult.setPage(1);
            pageResult.setTotalItems(flightList.size());
        }
        return pageResult;
    }
}
