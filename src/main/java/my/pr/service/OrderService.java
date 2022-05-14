package my.pr.service;

import my.pr.model.Order;
import my.pr.status.Status;
import my.pr.repository.OrderRepository;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.keycloak.representations.AccessToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.net.ConnectException;
import java.time.OffsetDateTime;
import java.net.ConnectException;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Value("${email.url:http://localhost:8485}")
    private String emailURL;

    public List<Order> getAll() {
        return repository.findAll();
    }

    public List<Order> getOrdersForArchived() {
        return repository.findForArchived();
    }

    public List<Order> getByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        String emailUser = token.getEmail();
        return repository.findOrdersByEmail(emailUser);
    }

    public Order get(UUID id) throws EntityNotFoundException {
        return repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
    }

    @Transactional(rollbackFor = ConnectException.class)
    public Order createOrder(Order newOrder) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        newOrder.setEmail(token.getEmail());
        newOrder.setFirstName(token.getGivenName());
        newOrder.setLastName(token.getFamilyName());
        newOrder.setOrderStatus(Status.WAITING);
        newOrder.setCreation_time(OffsetDateTime.now());
        fullAddress(newOrder);
        newOrder.setCreation_time(OffsetDateTime.now());
        repository.save(newOrder);
        emailMessage(newOrder);
        return newOrder;
    }

    public String delete(UUID id) throws EntityNotFoundException {
        Order order = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
        Status orderStatus = order.getOrderStatus();
        if (orderStatus.equals(Status.WAITING)) {
            repository.delete(order);
            return "Order successfully deleted";
        } else {
            return "Order cannot deleted";
        }
    }

    public String update(UUID id, Order newOrder) throws EntityNotFoundException {
        Order order = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
        Status orderStatus = order.getOrderStatus();
        if (!(orderStatus.equals(Status.WAITING))) {
            return "Order cannot be updated";
        } else {
            setAdditionalData(newOrder, order);
            fullAddress(order);
            repository.save(order);
            return "Order  successfully updated";
        }
    }

    public void archivedStatus(UUID id) throws EntityNotFoundException {
        Order order = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
        order.setOrderStatus(Status.ARCHIVED);
        repository.save(order);
    }


    public Order acceptOrder(UUID id) throws EntityNotFoundException {
        Order order = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
        if (order.getOrderStatus().equals(Status.WAITING)) {
            order.setOrderStatus(Status.ACCEPTED);
            order.setModification_time(OffsetDateTime.now());
            return repository.save(order);
        } else {
            return order;
        }
    }

    public Order rejectOrder(UUID id) throws EntityNotFoundException {
        Order order = repository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
        if (order.getOrderStatus().equals(Status.WAITING)) {
            order.setOrderStatus(Status.REJECTED);
            order.setModification_time(OffsetDateTime.now());
            return repository.save(order);
        } else {
            return order;
        }
    }

    private void setAdditionalData(Order newOrder, Order lastOrder) {
        lastOrder.setTypeOrder(newOrder.getTypeOrder());
        lastOrder.setStreet(newOrder.getStreet());
        lastOrder.setCity(newOrder.getCity());
        lastOrder.setFlat(newOrder.getFlat());
        lastOrder.setHouse(newOrder.getHouse());
        lastOrder.setSelfInstallation(newOrder.getSelfInstallation());
    }

    private void emailMessage(Order newOrder) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(emailURL + "/" + newOrder.getId() + "/" + newOrder.getFirstName(), String.class);

    }

    private void fullAddress(Order order) {
        StringBuilder fullAddress = new StringBuilder();
        fullAddress
                .append("City: ")
                .append(order.getCity().getName())
                .append(", street: ")
                .append(order.getStreet().getName())
                .append(", house:  ")
                .append(order.getHouse())
                .append(", flat:  ")
                .append(order.getFlat());
        order.setAddress(fullAddress.toString());
    }
}
