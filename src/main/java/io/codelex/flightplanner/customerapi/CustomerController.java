package io.codelex.flightplanner.customerapi;

import io.codelex.flightplanner.AddFlightRequest;
import io.codelex.flightplanner.Airport;
import io.codelex.flightplanner.Flight;
import io.codelex.flightplanner.errors.NoFlightFound;
import io.codelex.flightplanner.flights.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    CustomerService customerService;
    FlightService flightService;

    public CustomerController(CustomerService customerService, FlightService flightService) {
        this.customerService = customerService;
        this.flightService = flightService;
    }

    @GetMapping("/airports")
    public List<Airport> searchByAirport(@RequestParam String search) {
        return customerService.searchByAirport(search);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<AddFlightRequest> getFlight(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(flightService.fetchFlight(id), HttpStatus.OK);
        } catch (NoFlightFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/flights/search")
    public ResponseEntity<PageResult<Flight>> searchFlight(@Valid @RequestBody SearchFlightsRequest searchFlightsRequest, BindingResult result) {
        if (result.hasErrors() || isSameAirport(searchFlightsRequest)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerService.searchFlights(searchFlightsRequest), HttpStatus.OK);
    }

    private boolean isSameAirport(SearchFlightsRequest searchFlightsRequest) {
        return searchFlightsRequest.getFrom().equalsIgnoreCase(searchFlightsRequest.getTo());
    }


}
