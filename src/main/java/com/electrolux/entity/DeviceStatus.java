package com.electrolux.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "device_status")
@Data
@NoArgsConstructor
public class DeviceStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
