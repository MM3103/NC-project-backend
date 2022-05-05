
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
        for (int i = 0; i < orders.size(); i++) {
            Order newOrder = orders.get(i);
            service.archivedStatus(newOrder.getId());
        }
    }
}
