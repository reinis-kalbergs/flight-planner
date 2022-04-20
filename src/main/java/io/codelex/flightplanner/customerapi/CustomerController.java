package io.codelex.flightplanner.customerapi;

import io.codelex.flightplanner.adminapi.AdminService;
import io.codelex.flightplanner.flights.Airport;
import io.codelex.flightplanner.flights.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    CustomerService customerService;
    AdminService adminService;

    public CustomerController(CustomerService customerService, AdminService adminService) {
        this.customerService = customerService;
        //adminService is here to use fetchFlight
        this.adminService = adminService;
    }

    @GetMapping("/airports")
    public List<Airport> searchByAirport(@RequestParam String search) {
        return customerService.searchByAirport(search);
    }

    @GetMapping("/flights/{id}")
    public Flight getFlight(@PathVariable("id") Long id) {
        return adminService.fetchFlight(id);
    }

    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlight(@Valid @RequestBody SearchFlightsRequest searchFlightsRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        checkIfSameAirport(searchFlightsRequest);
        return customerService.searchFlights(searchFlightsRequest);
    }

    private void checkIfSameAirport(SearchFlightsRequest searchFlightsRequest) throws ResponseStatusException {
        if (searchFlightsRequest.getFrom().equalsIgnoreCase(searchFlightsRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


}
