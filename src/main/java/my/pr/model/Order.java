package my.pr.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import my.pr.status.Status;
import my.pr.status.TypeOrder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
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
    @Enumerated(EnumType.STRING)
    private TypeOrder typeOrder;

    @ManyToOne
    private City city;

    @ManyToOne
    private Street street;

    @NotNull
    @Column(name = "house", nullable = false)
    private Integer house;

    @Column(name = "flat", nullable = false)
    private Integer flat;

    @Column(name = "self_installation", nullable = false)
    private Boolean selfInstallation;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private Status orderStatus;

    @Column(name = "creation_time")
    private OffsetDateTime creation_time;

    @Column(name = "modification_time")
    private OffsetDateTime modification_time;

    @Builder
    @JsonCreator
    public static Order customBuilder(@JsonProperty("typeOrder") TypeOrder typeOrder) {
        Order order = new Order();
        order.setTypeOrder(typeOrder);
        if (typeOrder.toString().trim().length() == 0) {
            throw new IllegalStateException("Type order is null'.");
        }
        return order;
    }
}
