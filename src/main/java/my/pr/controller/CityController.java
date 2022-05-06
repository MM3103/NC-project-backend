package my.pr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import my.pr.model.City;
import my.pr.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/city")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearer-key")
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated schema"),
        @ApiResponse(responseCode = "404", description = "Schema not found"),
        @ApiResponse(responseCode = "401", description = "Access error"),
        @ApiResponse(responseCode = "403", description = "Access error"),
        @ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
public class CityController {

    @Autowired
    CityService service;

    @GetMapping("/getAllCity")
    @Operation(summary = "Get all cities")
    public List<City> getAllCities() {
        return service.getAllCities();
    }

    @GetMapping("/getAllCitiesNames")
    @Operation(summary = "Get all cities")
    public List<String> getAllCitiesNames() {
        return service.getCitiesNames();
    }

    @GetMapping("/getCityById/{id}")
    @Operation(summary = "Get city by id")
    public ResponseEntity<City> getCityById(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(service.getCityById(id));
    }

    @PostMapping("/add")
    @Operation(summary = "Add new city")
    public City addCity(@RequestBody City newCity) {
        return service.addCity(newCity);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete city by id")
    public void deleteCity(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        service.deleteCity(id);
    }

    @PatchMapping("/update/{id}")
    @Operation(summary = "Update city  by id")
    public void updateCity(@PathVariable(value = "id") Long id, @RequestBody City newCity) throws EntityNotFoundException {
         service.updateCity(id, newCity);
    }

}
