package com.datamaster.polls.dto.request;

import com.datamaster.polls.dto.request.meta.BasePollRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@ApiModel(description = "Данные для создания опроса")
public class PollCreateDTO extends BasePollRequestDTO {

    private static final long serialVersionUID = -4128763340164373742L;

    /**
     * Вопросы, содержащиеся в опросе
     */
    @ApiModelProperty(notes = "Вопросы, содержащиеся в опросе", required = true)
    private List<QuestionCreateDTO> questions;
}
