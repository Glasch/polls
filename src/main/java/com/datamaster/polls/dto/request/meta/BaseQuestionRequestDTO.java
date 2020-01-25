package com.datamaster.polls.dto.request.meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseQuestionRequestDTO {

    /**
     * Текст вопроса
     */
    @ApiModelProperty(notes = "Текст вопроса", required = true)
    private String content;

    /**
     * Порядок отображения (целое число, по которому будет производиться сортировка)
     */
    @ApiModelProperty(notes = "Порядок отображения", required = true)
    private int sort;
}
