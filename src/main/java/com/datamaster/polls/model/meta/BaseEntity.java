package com.datamaster.polls.model.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Базовый класс для сущностей
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(of = {"id"})
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 3375801328729533112L;

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator"
            ,parameters = {
            @Parameter( name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")
    })
    @Column(name="id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    /**
     * Дата создания записи
     */
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

    /**
     * Дата обновления записи
     */
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
}
