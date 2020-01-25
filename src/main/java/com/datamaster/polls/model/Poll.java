package com.datamaster.polls.model;

import com.datamaster.polls.model.meta.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Опрос
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "poll")
@EqualsAndHashCode(of = {}, callSuper = true)
@ToString(exclude = {"questions"})
public class Poll extends BaseEntity {

    private static final long serialVersionUID = 2733199904418975112L;

    /**
     * Наименование опроса
     */
    @Column(name = "name")
    private String name;

    /**
     * Дата начала
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * Дата окончания
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * Активность
     */
    @Column(name = "is_active")
    private Boolean active;

    /**
     * Вопросы, содержащиеся в опросе
     */
    @OneToMany(mappedBy = "poll", fetch = FetchType.LAZY, orphanRemoval=true)
    private List<Question> questions;
}
