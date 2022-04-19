package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.Flight;
import io.codelex.flightplanner.errors.NoFlightFound;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {

    private volatile List<Flight> allFlights = new ArrayList<>();
    private volatile Long counter = 0L;

    public List<Flight> getAllFlights() {
        return allFlights;
    }

    public long getId() {
        return counter++;
    }

    public void clear() {
        allFlights.clear();
    }

    public synchronized void addFlight(Flight flight) {
        if (allFlights.contains(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Such a flight already exists");
        }
        allFlights.add(flight);
    }

    public Flight fetchFlight(Long id) throws NoFlightFound {
        for (Flight flight : allFlights) {
            if (flight.getId() == id) {
                return flight;
            }
        }
        throw new NoFlightFound();
    }

    public synchronized void deleteFlight(Long id) {
        allFlights.removeIf(flight -> flight.getId() == id);
    }


}
