package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.AddFlightRequest;
import io.codelex.flightplanner.Flight;
import io.codelex.flightplanner.errors.FlightAlreadyExists;
import io.codelex.flightplanner.errors.ImpossibleDate;
import io.codelex.flightplanner.errors.NoFlightFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class FlightController {

    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PutMapping("/flights")
    public ResponseEntity<AddFlightRequest> addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest, BindingResult result) {
        //todo: split up functions
        if (result.hasErrors() || isSameAirport(addFlightRequest)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(new AddFlightRequest(flightService.passFlightInfo(addFlightRequest)), HttpStatus.CREATED);
        } catch (FlightAlreadyExists e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (ImpossibleDate e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable("id") Long id) {
        flightService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(flightService.fetchFlight(id), HttpStatus.OK);
        } catch (NoFlightFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private boolean isSameAirport(AddFlightRequest addFlightRequest) {
        return addFlightRequest.getFrom().equals(addFlightRequest.getTo());
    }

}
