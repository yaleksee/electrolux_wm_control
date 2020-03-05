package com.electrolux.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "operator_request")
@Data
@NoArgsConstructor
public class OperatorRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
