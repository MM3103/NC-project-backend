package my.pr.model;

import lombok.Data;
import my.pr.status.Status;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class Order {

    public Order() {
    }

    public Order(String typeOrder, String address) {
        this.typeOrder = typeOrder;
        this.address = address;
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
}
