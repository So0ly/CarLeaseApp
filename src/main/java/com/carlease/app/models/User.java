package com.carlease.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "users_seq")
    private Long id;
    private String username;
    @JsonIgnore
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = setPassword(password);
    }

    public String setPassword(String password) {
        //TODO: imagine there's a hash function here
        return password;
    }
}
