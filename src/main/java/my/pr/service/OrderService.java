package my.pr.service;

import my.pr.model.Order;
import my.pr.repository.OrderRepository;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.keycloak.representations.AccessToken;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> getAll() {
        return repository.findAll();
    }

    public List<Order> getByEmail(KeycloakAuthenticationToken authentication) {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        String emailUser = token.getEmail();
        return repository.findByEmail(emailUser);
    }

    public Order get(UUID id) throws OpenApiResourceNotFoundException {
        return repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
    }

    public Order add(Order newOrder, KeycloakAuthenticationToken authentication) {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        newOrder.setEmail(token.getEmail());
        newOrder.setFirstName(token.getName());
        newOrder.setLastName(token.getFamilyName());
        return repository.save(newOrder);
    }

    public void delete(UUID id) throws OpenApiResourceNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
        repository.delete(order);
    }

    public Order update(UUID id, Order newOrder) throws OpenApiResourceNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
        checkUpdate(newOrder, order);
        return repository.save(order);
    }

    private void checkUpdate(Order newOrder, Order lastOrder) {
        if (newOrder.getFirstName() != null) {
            lastOrder.setFirstName(newOrder.getFirstName());
        }
        if (newOrder.getLastName() != null) {
            lastOrder.setLastName(newOrder.getLastName());
        }
        if (newOrder.getEmail() != null) {
            lastOrder.setEmail(newOrder.getEmail());
        }
        if (newOrder.getTypeOrder() != null) {
            lastOrder.setTypeOrder(newOrder.getTypeOrder());
        }
        if (newOrder.getAddress() != null) {
            lastOrder.setAddress(newOrder.getAddress());
        }
        if (newOrder.getOrderStatus() != null) {
            lastOrder.setOrderStatus(newOrder.getOrderStatus());
        }
    }
}
