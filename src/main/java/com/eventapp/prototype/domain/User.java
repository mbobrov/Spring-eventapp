package com.eventapp.prototype.domain;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(max = 64)
    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    //@NotNull
    @Size(max = 64)
    @Column(name = "firstname")
    private String firstname;

    //@NotNull
    @Size(max = 64)
    @Column(name = "lastname")
    private String lastname;

    @NotNull
    @ValidEmail
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(max = 64)
    @Column(name = "password", nullable = false)
    private String password;

    private User() {
    }

    public User(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("password", password)
                .toString();
    }
}
