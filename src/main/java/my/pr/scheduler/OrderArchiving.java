
package my.pr.scheduler;

import my.pr.model.Order;
import my.pr.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderArchiving {

    @Autowired
    private OrderService service;

    @Scheduled(fixedRateString = "3600000")
    public void archivedStatus() {
        List<Order> orders = service.getOrdersForArchived();
        for (Order newOrder : orders) {
            service.archivedStatus(newOrder.getId());
        }
    }
}
