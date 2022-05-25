package io.codelex.flightplanner.database.services;

import io.codelex.flightplanner.database.repositories.AirportDatabaseRepository;
import io.codelex.flightplanner.database.repositories.FlightDatabaseRepository;
import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.models.PageResult;
import io.codelex.flightplanner.models.SearchFlightsRequest;
import io.codelex.flightplanner.services.CustomerService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public class CustomerDatabaseService implements CustomerService {
    AirportDatabaseRepository airportDatabaseRepository;
    FlightDatabaseRepository flightDatabaseRepository;

    public CustomerDatabaseService(AirportDatabaseRepository airportDatabaseRepository, FlightDatabaseRepository flightDatabaseRepository) {
        this.airportDatabaseRepository = airportDatabaseRepository;
        this.flightDatabaseRepository = flightDatabaseRepository;
    }

    @Override
    @Transactional
    public PageResult<Flight> searchFlights(SearchFlightsRequest searchFlightsRequest) {
        final int RESULTS_PER_PAGE = 20;
        PageResult<Flight> pageResult = new PageResult<>();

        List<Flight> flightList = flightDatabaseRepository.findAll();
        pageResult.setItems(flightList);

        pageResult.setPage((int) Math.ceil((double) flightList.size() / RESULTS_PER_PAGE));
        pageResult.setTotalItems(flightList.size());

        return pageResult;
    }

    @Override
    @Transactional
    public List<Airport> searchAirports(String airportSearch) {
        return findAirportsByTerm(airportSearch.trim());
    }

    private List<Airport> findAirportsByTerm(String airportSearch) {
        return airportDatabaseRepository.findByCountryContainingIgnoreCaseOrCityContainingIgnoreCaseOrAirportContainingIgnoreCase(
                airportSearch, airportSearch, airportSearch);
    }
}
