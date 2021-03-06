package com.electrolux.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "work_mode")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WorkMode{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private long id;

    @NotNull
    @Size(min = 2, max = 20, message = "nameMode limits must be kept")
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

    @NotNull
    @Min(30) @Max(180)
    @Column(name = "washing_timer")
    private Integer washingTimer;

    @NotNull(message = "...")
    @Column(name = "save_water")
    private String saveWater;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @ToString.Exclude
    private User user;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "workModes")
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "workModes", cascade = CascadeType.ALL)
    @Fetch(value=FetchMode.SELECT)
    @JsonIgnore
    @ToString.Exclude
    private Set<Model> models;
}
