package com.electrolux.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "work_mode")
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class WorkMode extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, max = 20, message = "...")
    @Column(name = "name_mode")
    private String nameMode;

    @NotNull
    @Min(200) @Max(2000)
    @Column(name = "spid_speed")
    private Integer spidSpeed;

    @NotNull
    @Min(30) @Max(90)
    @Column(name = "washing_temperature")
    private Integer washingTemperature;

    @NotNull(message = "...")
    @Column(name = "save_water")
    private String saveWater;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "workModes")
//    private Set<WM_Model> wm_models;
}
