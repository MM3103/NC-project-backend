package my.pr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import my.pr.status.Status;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class Order {

    public Order() {
    }

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true
            , updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "type_order", nullable = false)
    private String typeOrder;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "order_status")
    private Status orderStatus;

    @Builder
    @JsonCreator
    public static Order customBuilder(@JsonProperty("typeOrder") String typeOrder,@JsonProperty("address") String address) {
        Order order = new Order();
        order.setTypeOrder(typeOrder);
        order.setAddress(address);
        if (typeOrder.trim().length() == 0 || address.trim().length() == 0) {
            throw new IllegalStateException("Cannot send 'text' and 'file'.");
        }
        return order;
    }

}
