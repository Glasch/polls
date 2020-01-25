package com.datamaster.polls.dto.request;

import com.datamaster.polls.dto.request.meta.BasePollRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "Данные для обновления опроса")
public class PollUpdateDTO extends BasePollRequestDTO {

    private static final long serialVersionUID = 2077955470761334800L;

    /**
     * Идентификатор обновляемого опроса
     */
    @ApiModelProperty(notes = "Идентификатор обновляемого опроса")
    private UUID id;

    /**
     * Вопросы, содержащиеся в опросе
     */
    @ApiModelProperty(notes = "Вопросы, содержащиеся в опросе", required = true)
    private List<QuestionUpdateDTO> questions;
}
