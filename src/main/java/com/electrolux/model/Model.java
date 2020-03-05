package com.electrolux.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "model")
@Data
@NoArgsConstructor
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "model_name", nullable = false)
    private String modelName;
    @Column(name = "mains_voltage", nullable = false)
    private Integer mainsVoltage;
    @Column(name = "hardness_water", nullable = false)
    private Integer hardnessWater;
    @Column(name = "collor", nullable = false)
    private String collor;
    @Column(name = "isDisplay", nullable = false)
    private Boolean isDisplay;


    public Model(String modelName, Integer mainsVoltage, Integer hardnessWater, String collor, Boolean isDisplay) {
        this.modelName = modelName;
        this.mainsVoltage = mainsVoltage;
        this.hardnessWater = hardnessWater;
        this.collor = collor;
        this.isDisplay = isDisplay;
    }
}
