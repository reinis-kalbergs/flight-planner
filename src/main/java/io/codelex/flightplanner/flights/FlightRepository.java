package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.Flight;
import io.codelex.flightplanner.errors.FlightAlreadyExists;
import io.codelex.flightplanner.errors.NoFlightFound;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {

    private List<Flight> allFlights = new ArrayList<>();
    private long currentId;

    public List<Flight> getAllFlights() {
        return allFlights;
    }

    public long getId() {
        currentId++;
        return currentId - 1;
    }

    public void clear() {
        allFlights.clear();
    }

    public void addFlight(Flight flight) {
        if (allFlights.contains(flight)) {
            throw new FlightAlreadyExists();
        }
        allFlights.add(flight);
        System.out.println();
    }

    public Flight fetchFlight(long id) throws NoFlightFound {
        for (Flight flight : allFlights) {
            if (flight.getId() == id) {
                return flight;
            }
        }
        throw new NoFlightFound();
    }

    public void deleteFlight(Long id) {
        allFlights.removeIf(flight -> flight.getId() == id);
    }


}
