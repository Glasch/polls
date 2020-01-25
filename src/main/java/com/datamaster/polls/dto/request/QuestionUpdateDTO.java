package com.datamaster.polls.dto.request;

import com.datamaster.polls.dto.request.meta.BaseQuestionRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Данные для обновления вопроса")
public class QuestionUpdateDTO extends BaseQuestionRequestDTO {

    /**
     * Идентификатор обновляемого вопроса
     */
    @ApiModelProperty(notes = "Идентификатор обновляемого вопроса")
    private UUID id;
}
