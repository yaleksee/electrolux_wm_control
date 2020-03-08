package com.electrolux.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "model")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private long id;

    @NotNull
    @Size(min = 2, max = 40, message = "modelName limits must be kept")
    @Column(name = "model_name")
    private String modelName;

    @NotNull
    @Min(110) @Max(300)
    @Column(name = "mains_voltage")
    private Integer mainsVoltage;

    @NotNull
    @Max(90)
    @Column(name = "hardness_water")
    private Integer hardnessWater;

    @NotNull
    @Min(1) @Max(20)
    @Column
    private Integer volume;

    @NotNull
    @Min(0) @Max(0)
    @Column (name = "washing_number")
    private Integer washingNumber;

    @Column(name = "hexcode_collor")
    private String hexCodeCollor;

    @NotNull
    @Column
    private Boolean isDisplay;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date warrantyExpirationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @ManyToMany
    @JoinTable(name = "wm_modes",
            joinColumns = @JoinColumn(name = "model_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "work_mode_id", referencedColumnName = "id")
    )
    @JsonIgnore
    @ToString.Exclude
    private Set<WorkMode> workModes;

}
