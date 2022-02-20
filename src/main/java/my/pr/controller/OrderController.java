package my.pr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import my.pr.model.Order;
import my.pr.service.OrderService;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public List<Order> getAllOrders(HttpServletRequest request) {
        return service.getAll();
    }

    @GetMapping("/order/{id}")
    @Operation(summary = "Get order by id")
    public ResponseEntity<Order> getOrder(@PathVariable(value = "id") UUID id) throws OpenApiResourceNotFoundException {
        return ResponseEntity.ok().body(service.get(id));
    }

    @GetMapping("/order/acceptedorder/{id}")
    @Operation(summary = "Accepted order")
    public String acceptedOrder(@PathVariable(value = "id") UUID id,HttpServletResponse response) throws OpenApiResourceNotFoundException, IOException {
        service.acceptedOrder(id);
        response.sendRedirect("http://localhost:3000/accept");
        return   "Order accepted";
    }

    @GetMapping("/order/unacceptedorder/{id}")
    @Operation(summary = "Unaccepted order")
    public String unacceptedOrder(@PathVariable(value = "id") UUID id, HttpServletResponse response) throws OpenApiResourceNotFoundException, IOException {
        service.unacceptedOrder(id);
        response.sendRedirect("http://localhost:3000/reject");
        return   "Order unaccepted";
    }

    @GetMapping("/order/getUserOrders")
    @Operation(summary = "Get all orders by email")
    public List<Order> getOrdersByEmail() {
        return service.getByEmail();
    }

    @PostMapping("/order")
    @Operation(summary = "Add new order")
    public Order addOrder(@RequestBody Order newOrder) throws MessagingException {
        return service.add(newOrder);
    }

    @DeleteMapping("/order/{id}")
    @Operation(summary = "Delete order by id")
    public String deleteOrder(@PathVariable(value = "id") UUID id) throws OpenApiResourceNotFoundException {
        return service.delete(id);
    }

    @PatchMapping("/order/{id}")
    @Operation(summary = "Update order  by id")
    public String updateOrder(@PathVariable(value = "id") UUID id, @RequestBody Order newOrder) throws OpenApiResourceNotFoundException {
        return service.update(id, newOrder);
    }

}
