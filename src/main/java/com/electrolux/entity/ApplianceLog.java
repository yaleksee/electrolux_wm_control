package com.electrolux.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "appliance_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceLog extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

//    @NotNull
//    @Column
//    private String login;
//
//    @NotNull
//    @Column(name = "wm_model_name")
//    private String wm_modelName;
//
//    @NotNull
//    @Column(name = "mains_voltage")
//    private Integer mainsVoltage;
//
//    @NotNull
//    @Column(name = "hardness_water")
//    private Integer hardnessWater;
//
//    @NotNull
//    @Column
//    private Integer volume;
//
//    @Temporal(TemporalType.DATE)
//    @Column(name = "warranty_expiration_date")
//    private Date warrantyExpirationDate;
//
//    @Temporal(TemporalType.DATE)
//    @Column(name = "current_date")
//    private Date currentDate;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "wm_model_id", nullable = false)
//    @JsonIgnore
//    private WM_Model wm_model;
//
//    @NotNull
//    @Column(name = "spid_speed")
//    private Integer spidSpeed;
//
//    @NotNull
//    @Column(name = "washing_temperature")
//    private Integer washingTemperature;
//
//    @NotNull
//    @Column(name = "save_water")
//    private String saveWater;
}
