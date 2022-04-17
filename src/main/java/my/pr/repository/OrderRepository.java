package my.pr.repository;

import my.pr.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findOrdersByEmail(String email);

    @Query(value = "select * from orders where order_status in ('ACCEPTED','REJECTED') and ((now() - modification_time)> '24 hours' )"
            , nativeQuery = true)
    List<Order> findForArchived();
}
