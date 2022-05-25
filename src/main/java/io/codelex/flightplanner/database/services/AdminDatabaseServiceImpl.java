package io.codelex.flightplanner.database.services;

import io.codelex.flightplanner.database.repositories.AirportDatabaseRepository;
import io.codelex.flightplanner.database.repositories.FlightDatabaseRepository;
import io.codelex.flightplanner.models.AddFlightRequest;
import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.services.AbstractFlightService;
import io.codelex.flightplanner.services.AdminService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public class AdminDatabaseServiceImpl extends AbstractFlightService implements AdminService {

    private final FlightDatabaseRepository flightRepository;
    private final AirportDatabaseRepository airportRepository;

    public AdminDatabaseServiceImpl(FlightDatabaseRepository flightRepository, AirportDatabaseRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    @Transactional
    public Flight addFlight(AddFlightRequest addFlightRequest) {

        addFlightRequest.setFrom(findOrCreate(addFlightRequest.getFrom()));
        addFlightRequest.setTo(findOrCreate(addFlightRequest.getTo()));
        Flight flight = new Flight(addFlightRequest);

        checkDate(flight);
        if (flightExists(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return flightRepository.save(flight);
    }

    private Airport findOrCreate(Airport airport) {
        Optional<Airport> maybeAirport = airportRepository.findById(airport.getAirport());
        return maybeAirport.orElse(
                airportRepository.save(airport)
        );
    }

    private boolean flightExists(Flight flight) {
        return flightRepository.existsByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(
                flight.getFrom(), flight.getTo(), flight.getCarrier(), flight.getDepartureTime(), flight.getArrivalTime()
        );
    }
    
    @Override
    @Transactional
    public Flight fetchFlight(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteFlight(Long id) {
        Optional<Flight> maybeFlight = flightRepository.findById(id);
        if (maybeFlight.isPresent()) {
            flightRepository.deleteById(id);
        }
    }
}
