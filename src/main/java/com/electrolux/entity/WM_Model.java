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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "wm_model")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WM_Model extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @NotNull
//    @Size(min = 2, max = 20, message = "...")
//    @Column(unique = true, nullable = false, name = "wm_ model_name")
//    private String wm_modelName;
//
//    @NotNull
//    @Size(min = 2, max = 20, message = "...")
//    @Column(unique = true, nullable = false, name = "user_login")
//    private String userLogin;
//
//    @ManyToMany
//    @JoinTable(name = "HAS",
//            joinColumns = @JoinColumn(name = "wm_model_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "work_mode_id", referencedColumnName = "id")
//    )
//    private Set<WorkMode> workModes;
//
//    @NotNull
//    @Size(min = 110, max = 300, message = "...")
//    @Column(name = "mains_voltage")
//    private Integer mainsVoltage;
//
//    @NotNull
//    @Size(max = 90, message = "...")
//    @Column(name = "hardness_water")
//    private Integer hardnessWater;
//
//    @NotNull
//    @Size(min = 1, max = 20, message = "...")
//    @Column
//    private Integer volume;
//
//    @NotNull(message = "...")
//    @Column(name = "hexcode_collor")
//    private String HexCodeCollor;
//
//    @NotNull(message = "...")
//    @Column
//    private Boolean isDisplay;
//
//    @Temporal(TemporalType.DATE)
//    @Column(name = "warranty_expiration_date")
//    private Date warrantyExpirationDate;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private User user;

}
