package my.pr.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class Order {

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

    @Column(name = "type_order")
    private String typeOrder;

    @Column(name = "address")
    private String address;

    @Column(name = "order_status")
    private String orderStatus;
}
