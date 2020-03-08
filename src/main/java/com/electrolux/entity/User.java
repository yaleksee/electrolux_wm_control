package com.electrolux.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @NotNull
    @Size(min = 2, max = 20, message = "long of login incorrect")
    @Column(unique = true, nullable = false, name = "login")
    private String login;

    @Column(name = "first_name")
    @ToString.Exclude
    private String firstName;

    @Column(name = "last_name")
    @ToString.Exclude
    private String lastName;
}
