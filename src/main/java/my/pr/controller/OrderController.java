package my.pr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import my.pr.model.Order;
import my.pr.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearer-key")
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully updated schema"),
        @ApiResponse(responseCode = "404", description = "Schema not found"),
        @ApiResponse(responseCode = "401", description = "Access error"),
        @ApiResponse(responseCode = "403", description = "Access error"),
        @ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
        @ApiResponse(responseCode = "500", description = "Internal error")})
public class OrderController {

    @Autowired
    OrderService service;

    @GetMapping("/order/getAll")
    @Operation(summary = "Get all orders")
    public List<Order> getAllOrders() {
        return service.getAll();
    }

    @GetMapping("/order/{id}")
    @Operation(summary = "Get order by id")
    public ResponseEntity<Order> getOrder(@PathVariable(value = "id") UUID id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(service.get(id));
    }

    @GetMapping("/order/acceptOrder/{id}")
    @Operation(summary = "Accepted order")
    public String acceptOrder(@PathVariable(value = "id") UUID id) throws EntityNotFoundException, InterruptedException {
        service.acceptOrder(id);
        return "Order accepted";
    }

    @GetMapping("/order/rejectOrder/{id}")
    @Operation(summary = "Rejected order")
    public String rejectOrder(@PathVariable(value = "id") UUID id) throws EntityNotFoundException, InterruptedException {
        service.rejectOrder(id);
        return "Order rejected";
    }

    @GetMapping("/order/getUserOrders")
    @Operation(summary = "Get all orders by email")
    public List<Order> getOrdersByEmail() {
        return service.getByEmail();
    }

    @PostMapping("/order")
    @Operation(summary = "Add new order")
    public Order createOrder(@RequestBody Order newOrder) throws MessagingException {
        return service.createOrder(newOrder);
    }

    @DeleteMapping("/order/{id}")
    @Operation(summary = "Delete order by id")
    public String deleteOrder(@PathVariable(value = "id") UUID id) throws EntityNotFoundException {
        return service.delete(id);
    }

    @PatchMapping("/order/{id}")
    @Operation(summary = "Update order  by id")
    public String updateOrder(@PathVariable(value = "id") UUID id, @RequestBody Order newOrder) throws EntityNotFoundException {
        return service.update(id, newOrder);
    }

}
