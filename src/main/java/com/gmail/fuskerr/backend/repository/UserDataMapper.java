package com.gmail.fuskerr.backend.repository;

import javax.persistence.*;

@Entity
//"user" is a keyword in postgresql
@Table(name = "person")
public class UserDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "token",
            nullable = false,
            unique = true
    )
    private String token;

    public UserDataMapper(Long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    public UserDataMapper(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public UserDataMapper() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDataMapper user = (UserDataMapper) o;

        if (id != user.id) return false;
        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }
}
