package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.models.Airport;
import io.codelex.flightplanner.models.Flight;
import io.codelex.flightplanner.models.PageResult;
import io.codelex.flightplanner.models.SearchFlightsRequest;
import io.codelex.flightplanner.services.AdminService;
import io.codelex.flightplanner.services.CustomerService;
import org.springframework.http.HttpStatus;
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
    public List<Airport> searchAirports(@RequestParam String search) {
        return customerService.searchAirports(search);
    }

    @GetMapping("/flights/{id}")
    public Flight getFlight(@PathVariable("id") Long id) {
        return adminService.fetchFlight(id);
    }

    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlight(@Valid @RequestBody SearchFlightsRequest searchFlightsRequest) {
        checkIfSameAirport(searchFlightsRequest);
        return customerService.searchFlights(searchFlightsRequest);
    }

    private void checkIfSameAirport(SearchFlightsRequest searchFlightsRequest) {
        if (searchFlightsRequest.getFrom().equalsIgnoreCase(searchFlightsRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


}
