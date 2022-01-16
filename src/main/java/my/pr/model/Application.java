package my.pr.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="applications")
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name="last_name",nullable = false)
    private String lastName;

    @Column(name="email",nullable = false)
    private String Email;

    @Column(name="type_application",nullable = false)
    private String typeApplication;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name="application_status",nullable = false)
    private Boolean applicationStatus;



}
