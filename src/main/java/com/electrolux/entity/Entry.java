package com.electrolux.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Лог журнала содержит важное поле String log. Данная сущность создается автоматически при создании стиральной машины.
 * При создании в нее загружается дата создания createdAt и первичная запись в log.
 *
 * При запуске юзером любого режима в стриральной машине в лог загружается информация
 * имя пользователя
 * дата запуска режима стирки
 * название выбранного режима стирки и его характеристики
 *
 * данные стиральной машины
 * номер стирки
 * текущая дата
 */
@Entity
@Table(name = "entry")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt"},
        allowGetters = true
)
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private String log;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date currentlyDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "model_id", nullable = false)
    @JsonIgnore
    private Model model;

}
