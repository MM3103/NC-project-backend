package my.pr.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@Table(name = "streets")
public class Street {

    public Street() {
    }

    public Street(String name) {
        this.name = name;
    }

    public Street(String name, City city) {
        this.name = name;
        this.city = city;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private City city;


}
