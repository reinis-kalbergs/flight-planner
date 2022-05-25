package io.codelex.flightplanner.database.services;

import io.codelex.flightplanner.database.repositories.AirportDatabaseRepository;
import io.codelex.flightplanner.database.repositories.FlightDatabaseRepository;
import io.codelex.flightplanner.services.TestService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public class TestDatabaseService implements TestService {

    FlightDatabaseRepository flightDatabaseRepository;
    AirportDatabaseRepository airportDatabaseRepository;

    public TestDatabaseService(FlightDatabaseRepository flightDatabaseRepository, AirportDatabaseRepository airportDatabaseRepository) {
        this.flightDatabaseRepository = flightDatabaseRepository;
        this.airportDatabaseRepository = airportDatabaseRepository;
    }

    @Override
    @Transactional
    public void clear() {
        flightDatabaseRepository.deleteAll();
        airportDatabaseRepository.deleteAll();
    }
}
