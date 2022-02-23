package my.pr.service;

import my.pr.email.Sender;
import my.pr.model.Email;
import my.pr.model.Order;
import my.pr.model.Status;
import my.pr.repository.OrderRepository;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public List<Order> getByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        String emailUser = token.getEmail();
        return repository.findByEmail(emailUser);
    }

    public Order get(UUID id) throws OpenApiResourceNotFoundException {
        return repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
    }

    public Order add(Order newOrder) throws MessagingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        newOrder.setEmail(token.getEmail());
        newOrder.setFirstName(token.getGivenName());
        newOrder.setLastName(token.getFamilyName());
        newOrder.setOrderStatus(Status.WAITING);
        Order order1 =  repository.save(newOrder);
        Sender sender = new Sender("homework0005@gmail.com", "homework1234");
        Email email = new Email();
        email.setTo("homework0005@gmail.com");
        email.setTitle("New order created");
        UUID uuid = order1.getId();
        email.setContent(String.format("User name: " + token.getFamilyName()
                + ", order id " + uuid.toString() +
                ".If you want to accept order,click here: http://localhost:8484/order/acceptedorder/"
                + uuid  +
                ", else click here:http://localhost:8484/order/unacceptedorder/"
                + uuid));
        sender.sendMessage(email);
        return order1;
    }

    public String delete(UUID id) throws OpenApiResourceNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
        Status str1 = order.getOrderStatus();
        if (!(str1.equals(Status.ACCEPTED) || (str1.equals(Status.REJECTED)))) {
            repository.delete(order);
            return "Order successfully deleted";
        }
        else {
            return "Order cannot deleted";
        }
    }

    public String update(UUID id, Order newOrder) throws OpenApiResourceNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
        Status str1 = order.getOrderStatus();
        if ((str1.equals(Status.ACCEPTED) || (str1.equals(Status.REJECTED)))) {
            return "Order cannot be updated";
        }
        else {
            checkOrder(newOrder, order);
            repository.save(order);
            return "Order  successfully updated";
        }
    }

    private void checkOrder(Order newOrder, Order lastOrder) {
        if (newOrder.getTypeOrder() != null) {
            lastOrder.setTypeOrder(newOrder.getTypeOrder());
        }
        if (newOrder.getAddress() != null) {
            lastOrder.setAddress(newOrder.getAddress());
        }
    }

    public Order acceptedOrder(UUID id) throws OpenApiResourceNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
        Status status = Status.ACCEPTED;
        order.setOrderStatus(status);
        return repository.save(order);
    }

    public Order unacceptedOrder(UUID id) throws OpenApiResourceNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
        Status status = Status.REJECTED;
        order.setOrderStatus(status);
        return repository.save(order);
    }
}
