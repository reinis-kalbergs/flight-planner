package io.codelex.flightplanner.services;

import io.codelex.flightplanner.models.AddFlightRequest;
import io.codelex.flightplanner.models.Flight;

public interface AdminService {

    Flight addFlight(AddFlightRequest addFlightRequest);

    Flight fetchFlight(Long id);

    void deleteFlight(Long id);
}
