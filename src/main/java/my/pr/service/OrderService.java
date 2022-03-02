package my.pr.service;

import my.pr.model.Order;
import my.pr.model.Status;
import my.pr.repository.OrderRepository;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.keycloak.representations.AccessToken;

import javax.mail.MessagingException;
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

    public Order add(Order newOrder, KeycloakAuthenticationToken authentication) throws MessagingException {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        newOrder.setEmail(token.getEmail());
        newOrder.setFirstName(token.getGivenName());
        newOrder.setLastName(token.getFamilyName());
        newOrder.setOrderStatus(Status.WAITING);
        return repository.save(newOrder);
    }

    public void delete(UUID id) throws OpenApiResourceNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
        repository.delete(order);
    }

    public Order update(UUID id, Order newOrder) throws OpenApiResourceNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
        checkOrder(newOrder, order);
        return repository.save(order);
    }

    private void checkOrder(Order newOrder, Order lastOrder) {
        if (newOrder.getTypeOrder() != null) {
            lastOrder.setTypeOrder(newOrder.getTypeOrder());
        }
        if (newOrder.getAddress() != null) {
            lastOrder.setAddress(newOrder.getAddress());
        }
    }

}
