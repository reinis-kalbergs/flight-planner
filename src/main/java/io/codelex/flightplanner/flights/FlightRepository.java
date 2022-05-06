package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.customerapi.PageResult;
import io.codelex.flightplanner.customerapi.SearchFlightsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FlightRepository {

    private List<Flight> allFlights = new ArrayList<>();
    private Long counter = 0L;

    private Long getId() {
        return counter++;
    }

    public void clear() {
        allFlights.clear();
    }

    public synchronized Flight addFlight(Flight addFlight) {
        addFlight.setId(getId());
        for (Flight flight : allFlights) {
            if (flight.isSameFlight(addFlight)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "This flight already exists");
            }
        }
        allFlights.add(addFlight);
        return addFlight;
    }

    public Flight fetchFlight(Long id) {
        for (Flight flight : allFlights) {
            if (flight.getId() == id) {
                return flight;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        final int RESULTS_PER_PAGE = 20;
        PageResult<Flight> pageResult = new PageResult<>();

        List<Flight> flightList = filterResults(allFlights, searchFlightsRequest);
        pageResult.setItems(flightList);

        pageResult.setPage((int) Math.ceil((double) flightList.size() / RESULTS_PER_PAGE));
        pageResult.setTotalItems(flightList.size());

        return pageResult;
    }

    private List<Flight> filterResults(List<Flight> list, SearchFlightsRequest searchFlightsRequest) {
        LocalDate localDate = LocalDate.parse(searchFlightsRequest.getDepartureDate());
        return list.stream()
                .filter(flight -> flight.getFrom().getAirport().equalsIgnoreCase(searchFlightsRequest.getFrom()))
                .filter(flight -> flight.getTo().getAirport().equalsIgnoreCase(searchFlightsRequest.getTo()))
                .filter(flight -> flight.getDepartureTime().toLocalDate().equals(localDate))
                .collect(Collectors.toList());
    }

    public synchronized void deleteFlight(Long id) {
        allFlights.removeIf(flight -> flight.getId() == id);
    }

    public List<Airport> searchAirports(String airportSearch) {
        List<Airport> result = new ArrayList<>();
        for (Flight flight : allFlights) {
            if (airportContainsText(flight.getFrom(), airportSearch)) {
                result.add(flight.getFrom());
            }
            if (airportContainsText(flight.getTo(), airportSearch)) {
                result.add(flight.getTo());
            }
        }
        return result;
    }

    private boolean airportContainsText(Airport airport, String text) {
        text = text.toLowerCase();
        return airport.getAirport().toLowerCase().contains(text) || airport.getCity().toLowerCase().contains(text) || airport.getCountry().toLowerCase().contains(text);
    }
}
