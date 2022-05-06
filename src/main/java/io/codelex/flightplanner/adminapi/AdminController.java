package io.codelex.flightplanner.adminapi;

import io.codelex.flightplanner.flights.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        checkIfSameAirport(addFlightRequest);
        return adminService.addFlight(addFlightRequest);
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable("id") Long id) {
        adminService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public Flight getFlight(@PathVariable("id") Long id) {
        return adminService.fetchFlight(id);
    }

    private void checkIfSameAirport(AddFlightRequest addFlightRequest) {
        if (addFlightRequest.getFrom().isEqualAirport(addFlightRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't have the same airport for departure and arrival.");
        }
    }

}
