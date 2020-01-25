package com.datamaster.polls.dto.response;

import com.datamaster.polls.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "Объект с данными вопроса")
public class QuestionDTO implements Serializable {

    private static final long serialVersionUID = -6487893887867983500L;

    public QuestionDTO(Question question) {
        this.content = question.getContent();
        this.sort = question.getSort();
    }

    /**
     * Текст вопроса
     */
    @ApiModelProperty(notes = "Текст вопроса", required = true)
    private String content;

    /**
     * Порядок отображения (целое число, по которому будет производиться сортировка)
     */
    @ApiModelProperty(notes = "Порядок отображения", required = true)
    @JsonIgnore
    private int sort;
}
