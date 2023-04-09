package com.guissisoftware.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity(name = "Customer")
@Table(name = "customer")
public class CustomerEntity extends PanacheEntity {
    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "email")
    @NotEmpty
    @Email
    private String email;

    @Column(name = "phone")
    private String phone;
}
