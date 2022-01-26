package my.pr.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import my.pr.model.Order;
import my.pr.service.OrderService;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearer-key")
public class OrderController {

    @Autowired
    OrderService service;

    @GetMapping("/adm")
    @Operation(summary = "Get all orders")
    public List<Order> getAllBooks() {
        return service.getAll();
    }

    @GetMapping("/adm/{id}")
    @Operation(summary = "Get order by id")
    public ResponseEntity<Order> getBook(@PathVariable(value = "id") UUID id) throws OpenApiResourceNotFoundException {
        return ResponseEntity.ok().body(service.get(id));
    }

    @PostMapping("/all")
    @Operation(summary = "Add new order")
    public Order addApplication(@RequestBody Order newOrder) {
        return service.add(newOrder);
    }

    @DeleteMapping("/all/{id}")
    @Operation(summary = "Delete order by id")
    public Map<String, Boolean> deleteBook(@PathVariable UUID id) throws OpenApiResourceNotFoundException {
        service.delete(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @PatchMapping("/all/{id}")
    @Operation(summary = "Update order  by id")
    public Order updateBook(@PathVariable UUID id, @RequestBody Order newOrder) throws OpenApiResourceNotFoundException {
        return service.update(id, newOrder);
    }




/*    @GetMapping("/anonymous")
    public String getAnonymousInfo() {
        return "Anonymous";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String getUserInfo() {
        return "user info";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminInfo() {
        return "admin info";
    }

    @GetMapping("/me")
    public Object getMe() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }*/


}
