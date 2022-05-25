package io.codelex.flightplanner.database.repositories;

import io.codelex.flightplanner.models.Airport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "database")
public interface AirportDatabaseRepository extends JpaRepository<Airport, String> {

    List<Airport> findByCountryContainingIgnoreCaseOrCityContainingIgnoreCaseOrAirportContainingIgnoreCase(
            @Param("country") String country, @Param("city") String city, @Param("airport") String airport);
    //@Param is here for a temporary fix for a bug in this Hibernate version

}
