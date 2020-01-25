package com.datamaster.polls.model;

import com.datamaster.polls.model.meta.BaseEntity;
import lombok.*;

import javax.persistence.*;

/**
 * Вопрос опроса
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
@EqualsAndHashCode(of = {}, callSuper = true)
public class Question extends BaseEntity {

    private static final long serialVersionUID = -855638822058170501L;

    /**
     * Текст вопроса
     */
    @Column(name = "content")
    private String content;

    /**
     * Порядок отображения (целое число, по которому будет производиться сортировка)
     */
    @Column(name = "sortBy")
    private Integer sort;

    /**
     * Опрос в котором содержится вопрос
     */
    @ManyToOne
    @JoinColumn(name = "poll_id" )
    private Poll poll;
}
