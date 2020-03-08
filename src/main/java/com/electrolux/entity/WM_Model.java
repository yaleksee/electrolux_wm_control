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
@Table(name = "wm_model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WM_Model extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, max = 20)
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
    private User user;

    @ManyToMany
    @JoinTable(name = "wm_modes",
            joinColumns = @JoinColumn(name = "wm_model_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "work_mode_id", referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<WorkMode> workModes;

}
