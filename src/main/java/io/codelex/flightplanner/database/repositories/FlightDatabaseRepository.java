package io.codelex.flightplanner.database.repositories;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public interface FlightDatabaseRepository extends JpaRepository<Flight, Long> {

    boolean existsByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(
            Airport from, Airport to, String Carrier, LocalDateTime departureTime, LocalDateTime arrivalTime);

}
