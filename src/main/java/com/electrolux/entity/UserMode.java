package com.electrolux.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_mode")
@Data
@NoArgsConstructor
public class UserMode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
