package my.pr.model;

import lombok.Data;
import my.pr.status.CityAndStreetStatus;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cities")
public class City {

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "city_status")
    @Enumerated(EnumType.STRING)
    private CityAndStreetStatus cityStatus = CityAndStreetStatus.ACTIVE;
}
