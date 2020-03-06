package com.electrolux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 20, message = "...")
    @Column(unique = true, nullable = false, name = "login")
    private String login;

    @NotNull
    @Size(min = 2, max = 20, message = "...")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20, message = "...")
    @Column(name = "last_name")
    private String lastName;
}