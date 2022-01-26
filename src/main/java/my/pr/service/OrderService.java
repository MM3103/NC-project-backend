package my.pr.service;

import my.pr.model.Order;
import my.pr.repository.OrderRepository;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order get(UUID id) throws OpenApiResourceNotFoundException {
        return repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
    }

    public Order add(Order newOrder) {
        return repository.save(newOrder);
    }

    public void delete(UUID id) throws OpenApiResourceNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
        repository.delete(order);
    }

    public Order update(UUID id, Order newOrder) throws OpenApiResourceNotFoundException {
        Order order = repository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Order not found for id: " + id));
        if (newOrder.getFirstName() != null) order.setFirstName(newOrder.getFirstName());
        if (newOrder.getLastName() != null) order.setLastName(newOrder.getLastName());
        if (newOrder.getEmail() != null) order.setEmail(newOrder.getEmail());
        if (newOrder.getTypeOrder() != null) order.setTypeOrder(newOrder.getTypeOrder());
        if (newOrder.getAddress() != null) order.setAddress(newOrder.getAddress());
        order.setOrderStatus(newOrder.getOrderStatus());
        return repository.save(order);
    }


}
