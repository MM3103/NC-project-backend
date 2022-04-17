package my.pr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import my.pr.model.Street;
import my.pr.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearer-key")
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated schema"),
        @ApiResponse(responseCode = "404", description = "Schema not found"),
        @ApiResponse(responseCode = "401", description = "Access error"),
        @ApiResponse(responseCode = "403", description = "Access error"),
        @ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
public class StreetController {

    @Autowired
    StreetService service;

    @GetMapping("/street/getAllStreet")
    @Operation(summary = "Get all street")
    public List<Street> getAllStreets() {
        return service.getAllStreets();
    }

    @GetMapping("/street/getStreet/{id}")
    @Operation(summary = "Get street by id")
    public ResponseEntity<Street> getStreet(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(service.getStreet(id));
    }

    @PostMapping("/street/add")
    @Operation(summary = "Add new street")
    public Street addStreet(@RequestBody Street newStreet) {
        return service.addCity(newStreet);
    }

    @DeleteMapping("/street/delete/{id}")
    @Operation(summary = "Delete street by id")
    public void deleteStreet(@PathVariable(value = "id") Long id) throws EntityNotFoundException {
        service.deleteStreet(id);
    }

    @PatchMapping("/street/update/{id}")
    @Operation(summary = "Update street  by id")
    public void updateStreet(@PathVariable(value = "id") Long id, @RequestBody Street newStreet) throws EntityNotFoundException {
        service.updateStreet(id, newStreet);
    }
}
