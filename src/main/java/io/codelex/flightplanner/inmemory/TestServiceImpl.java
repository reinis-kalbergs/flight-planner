package io.codelex.flightplanner.inmemory;

import io.codelex.flightplanner.services.TestService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "flight-planner", name = "store-type", havingValue = "in-memory")
public class TestServiceImpl implements TestService {
    private FlightRepository flightRepository;

    public TestServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void clear() {
        flightRepository.clear();
    }
}
