package my.pr.service;

import my.pr.email.Email;
import my.pr.email.Sender;
import my.pr.model.Order;
import my.pr.status.Status;
import my.pr.repository.OrderRepository;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.keycloak.representations.AccessToken;
import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
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

    public Order get(UUID id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
    }


    public Order createOrder(Order newOrder) throws MessagingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        newOrder.setEmail(token.getEmail());
        newOrder.setFirstName(token.getGivenName());
        newOrder.setLastName(token.getFamilyName());
        newOrder.setOrderStatus(Status.WAITING);
        fullAddress(newOrder);
        repository.save(newOrder);
        emailMessage(newOrder, token);
        return newOrder;
    }

    public String delete(UUID id) throws EntityNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
        Status orderStatus = order.getOrderStatus();
        if (orderStatus.equals(Status.WAITING)) {
            repository.delete(order);
            return "Order successfully deleted";
        } else {
            return "Order cannot deleted";
        }
    }

    public String update(UUID id, Order newOrder) throws EntityNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
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

    public Order acceptOrder(UUID id) throws EntityNotFoundException, InterruptedException {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
        if (order.getOrderStatus().equals(Status.WAITING)) {
            order.setOrderStatus(Status.ACCEPTED);
            return repository.save(order);
        }
        else {
            return order;
        }
    }

    public Order rejectOrder(UUID id) throws EntityNotFoundException, InterruptedException {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found for id: " + id));
        if(order.getOrderStatus().equals(Status.WAITING)) {
            order.setOrderStatus(Status.REJECTED);
            return repository.save(order);
        }
        else {
            return order;
        }
    }

    private void setAdditionalData(Order newOrder, Order lastOrder) {
        lastOrder.setTypeOrder(newOrder.getTypeOrder());
        lastOrder.setStreet(newOrder.getStreet());
        lastOrder.setCity(newOrder.getCity());
        lastOrder.setFlat(newOrder.getFlat());
        lastOrder.setHouse(newOrder.getHouse());
        lastOrder.setInstallation(newOrder.getInstallation());
    }

    private void emailMessage(Order savedOrder, AccessToken token) throws MessagingException {
        Sender sender = new Sender("homework0005@gmail.com", "homework1234");
        Email email = new Email("homework0005@gmail.com");
        email.setTitle("New order created");
        UUID uuid = savedOrder.getId();
        email.setContent(String.format("User name: " + token.getFamilyName()
                + ", order id " + uuid.toString() +
                ".If you want to accept order,click here: http://localhost:3000/accept/"
                + uuid));
        sender.sendMessage(email);
    }

    private void fullAddress(Order order){
        StringBuilder fullAddress = new StringBuilder();
        fullAddress.append("City: ").append(order.getCity().getName()).append(", street: ").append(order.getStreet().getName()).append(", house:  ").append(order.getHouse()).append(", flat:  ").append(order.getFlat());
        order.setAddress(fullAddress.toString());
    }

}
