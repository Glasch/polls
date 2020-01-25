package com.datamaster.polls.dto.response;

import com.datamaster.polls.model.Poll;
import com.datamaster.polls.model.Question;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Опрос
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ApiModel(description = "Объект с данными опроса")
public class PollDTO implements Serializable {

    private static final long serialVersionUID = 663272451770833980L;

    public PollDTO(Poll poll, List<Question> questions) {
        this.name = poll.getName();
        this.startDate = poll.getStartDate();
        this.endDate = poll.getEndDate();
        this.isActive = poll.getActive();
        this.questions = questions.stream().map(QuestionDTO::new).collect(Collectors.toList());
    }

    /**
     * Наименование опроса
     */
    @ApiModelProperty(notes = "Наименование опроса", required = true)
    private String name;

    /**
     * Дата начала
     */
    @ApiModelProperty(notes = "Дата начала", required = true)
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date startDate;

    /**
     * Дата окончания
     */
    @ApiModelProperty(notes = "Дата окончания", required = true)
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date endDate;

    /**
     * Активность
     */
    @ApiModelProperty(notes = "Активность", required = true)
    private boolean isActive;

    /**
     * Вопросы, содержащиеся в опросе
     */
    @ApiModelProperty(notes = "Вопросы, содержащиеся в опросе", required = true)
    private List<QuestionDTO> questions;
}
